package org.cfeclipse.cfml.views.explorer.vfs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileUtil;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.util.Os;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSUtil;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSView;

public class FileOperation implements IRunnableWithProgress 
{
	// File Operations
	public static final int COPY = 0;
	public static final int MOVE = 1;
	public static final int DELETE = 2;
	public static final int MKDIR = 3;

	// Unknown progress size?
	private boolean indeterminate;
	
	// Operation type
	private int type = COPY;
	
	// Copy arguments:
	private String[] sourceNames;	// source coy URIs
	private FileObject baseFile;	// Copy location
	
	// source File objects (for a delete operation)
	private FileObject[] sourceFiles;	
	
	
	// commons VFS manager
	private FileSystemManager fsManager;
	private Object fConnections;
	private String sourceConnectionId;
	private String destConnectionId;
	private FTPConnectionProperties sourceConnection;
	private FTPConnectionProperties destFSOpts;
	
	/**
	 * File operation to be performed on a set of files: COPY, DELETE
	 * This class is to be called from the progress Jface dialog
	 * @param fsMananger Fie system manager
	 * @param type Operation type: COPY, DELETE
	 * @param indeterminate True if we don't know the number of work units, false otherwise
	 */
	public FileOperation(FileSystemManager fsMananger, int type, boolean indeterminate) {
		    this.indeterminate 	= indeterminate;
		    this.type 			= type;
		    this.fsManager		= fsMananger;
	}
	
	/**
	 * Runs the long running operation
	 * @param monitor the progress monitor
	 */
	public void run(IProgressMonitor monitor) 
		throws InvocationTargetException, InterruptedException 
	{
		switch (type) {
		case COPY:
			doCopy(monitor);
			break;
		case MOVE:
			break;
		case DELETE:
			doDelete(monitor);
			break;
		case MKDIR:
			break;

		default:
			break;
		}
		
		monitor.done();
		
	    if (monitor.isCanceled())
	        throw new InterruptedException("Copy operation cancelled");
	}

	/**
	 * Set delete operation args
	 * @param files
	 */
	public void setDeleteArgs (FileObject[] files) 
	{
		this.sourceFiles = files;
	}
	
	/*
	 * Perform the delete operation
	 */
	private void doDelete (IProgressMonitor monitor) throws InterruptedException
	{
		try {
			if ( sourceFiles == null ) {
				System.err.println("doDelete Invalid source files");
				return;
			}
			
			monitor.beginTask(VFSView.getResourceString("progress.Delete.description"),
					indeterminate ? IProgressMonitor.UNKNOWN : sourceFiles.length);	
		
			for (int i = 0; i < sourceFiles.length; i++) 
			{
				deleteFileStructure(sourceFiles[i], monitor);

				if (monitor.isCanceled())
					throw new InterruptedException(VFSView.getResourceString("progress.Delete.canceled"));

			}
			monitor.done();
		} 
		catch (FileSystemException ex) {
			throw new InterruptedException (ex.getMessage());
		}
	}

	/**
	 * Delete a file structure
	 * @param oldFile File to delete
	 * @param monitor
	 * @return
	 * @throws FileSystemException
	 * @throws InterruptedException
	 */
	private boolean deleteFileStructure(FileObject oldFile, IProgressMonitor monitor)
		throws FileSystemException, InterruptedException
	{
		if (oldFile == null) return false;
		
		if (VFSUtil.isDirectory(oldFile) ) {
			/*
			 * Delete a directory
			 */
			FileObject[] subFiles = oldFile.getChildren();
			
			if (subFiles != null) 
			{
				for (int i = 0; i < subFiles.length; i++) {
					FileObject oldSubFile = subFiles[i];
					
					if (! deleteFileStructure(oldSubFile, monitor)) 
						return false;
					
					if (monitor.isCanceled())
						throw new InterruptedException(VFSView.getResourceString("progress.Delete.canceled"));
				}
			}
		}
	
		monitor.subTask(VFSView.getResourceString("progress.Delete.operation", new Object[] {oldFile}));
		oldFile.delete();
		return true;
	}
	
	/**
	 * Set a copy operation arguments
	 * @param sourceNames source files to be copied
	 * @param baseFile destination directory
	 */
	public void setCopyArgs (String[] sourceNames, FTPConnectionProperties sourceConnection, FileObject baseFile, FTPConnectionProperties destConnection) 
	{
		this.sourceNames = sourceNames;
		this.baseFile	= baseFile;
		this.sourceConnection =  sourceConnection;
		this.destFSOpts = destConnection;
		
	}
	
	/*
	 * Perform the copy operation
	 */
	private void doCopy(IProgressMonitor monitor) throws InterruptedException
	{
		if ( sourceNames == null || baseFile == null) {
			System.err.println("FileOperation.doCopy No source files or copy location");
			return;
		}
		
		// Copy each file
//		Vector /* of FileObject */ processedFiles = new Vector();

		try 
		{
			int units;

			try {
				units = getNumberOfWorkUnits(sourceNames);
			} catch (Exception e) {
				units =sourceNames.length;
			}
			
			monitor.beginTask(VFSView.getResourceString("progress.Copy.description") ,
					indeterminate ? IProgressMonitor.UNKNOWN : units);	
			
			for (int i = 0; (i < sourceNames.length) && (! monitor.isCanceled()); i++) 
			{
				// remove fore / from UNIX files
				if ( Os.isFamily(Os.OS_FAMILY_UNIX)) {
					sourceNames[i]= sourceNames[i].substring(1);
				}

				//System.out.println("doCopy sourceNames[" + i + "]=" + sourceNames[i]);
				
				// source/destination file
				final FileObject srcFile = fsManager.resolveFile(sourceNames[i],sourceConnection.getFileSystemOptions());
				final FileObject dstFile = fsManager.resolveFile(baseFile, srcFile.getName().getBaseName());

				copyFileStructure(srcFile, dstFile, monitor) ;

				if (monitor.isCanceled())
					throw new InterruptedException(VFSView.getResourceString("progress.Copy.canceled"));
				
			}
			
		}
		catch (FileSystemException fse) {
			VFSView.debug(fse);
			throw new InterruptedException(VFSUtil.getErrorMessageStack(fse));
		}
		catch (IOException ioe) {
			throw new InterruptedException(ioe.getMessage());
		}
	}

	/**
	 * Copy a set of files
	 * @param oldFile Source file URI for example: file:///tmp/foo.txt
	 * @param newFile Destination file URI for example file://tmp/dest/foo.txt
	 * @param monitor
	 * @return
	 * @throws FileSystemException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private boolean copyFileStructure(FileObject oldFile, FileObject newFile, IProgressMonitor monitor) 
		throws FileSystemException, IOException, InterruptedException
	{
		// ensure that newFile is not a child of oldFile or a dupe
		FileObject searchFile = newFile;
		do {
			if (oldFile.equals(searchFile)) return false;
			searchFile = searchFile.getParent();
		} while (searchFile != null);

		/*
		 * Copy a directory
		 */
		if (VFSUtil.isDirectory(oldFile)) 
		{
			// create new folder if required
			newFile.createFolder();
			
			FileObject[] subFiles = oldFile.getChildren();
			
			if (subFiles != null) 
			{

				for (int i = 0; i < subFiles.length; i++) {
					FileObject oldSubFile = subFiles[i];
					//FileObject newSubFile = new FileObject(newFile, oldSubFile.getName());
					FileObject newSubFile = fsManager.resolveFile(newFile, oldSubFile.getName().getBaseName());
					
					if (! copyFileStructure(oldSubFile, newSubFile, monitor)) 
						return false;
					
					if (monitor.isCanceled())
						throw new InterruptedException(VFSView.getResourceString("progress.Copy.canceled"));
				}
			}
		} 
		else {
			/*
			 * Copy a file
			 */
			//System.out.println("copyFileStructure: Copy source file:" + oldFile + " destination:" + newFile);
			monitor.subTask(VFSView.getResourceString("progress.Copy.operation", new Object[] {oldFile.toString()}));
			
			// catch any copy errors
			// TODO: This throws java.lan.OutOfMemoryError for big files 28MB+
			try {
				FileUtil.copyContent(oldFile, newFile);
			} 
			catch (Exception e) {
				VFSView.error("copyFileStructure: Error copying " + oldFile + " " + e);
				monitor.subTask(VFSView.getResourceString("progress.Copy.error", new Object[] {oldFile.toString()}));
			}
			monitor.worked(1);
		}
		return true;
	}
	
	private int getNumberOfWorkUnits (String[] sourceNames) throws FileSystemException
	{
		int count = 0;
		FileObject fo;
		String file;
		
		for (int i = 0; i < sourceNames.length; i++) 
		{
			// remove fore / from UNIX files
			if ( Os.isFamily(Os.OS_FAMILY_UNIX)) {
				file = sourceNames[i].substring(1);
			}
			else 
				file = sourceNames[i];
			
			//files[i] = fsManager.resolveFile(sourceNames[i]);
			fo = fsManager.resolveFile(file);
			count += recurseFileStructure(fo);
		}
		return count;
	}
	
	/**
	 * Recurse a File structure counting files. Used to setup the number of work units on a file operation
	 * @param fo
	 * @return
	 * @throws FileSystemException
	 */
	private int recurseFileStructure (FileObject fo) throws FileSystemException 
	{
		int count = 0;

		if ( VFSUtil.isDirectory(fo)) {
			FileObject[] children = fo.getChildren();
			FileObject child;
			for (int i = 0; i < children.length; i++) {
				child = children[i];
				if ( VFSUtil.isDirectory(child))
					count += recurseFileStructure(child);
				else
					count++;
			}
		}
		else count ++;
		return count;
	}
}
