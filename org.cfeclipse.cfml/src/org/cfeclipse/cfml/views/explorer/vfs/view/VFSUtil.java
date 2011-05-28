package org.cfeclipse.cfml.views.explorer.vfs.view;

import java.io.File;
import java.io.FileOutputStream;

import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileType;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.ftp.FTPConnection;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.explorer.vfs.FileOperation;

public class VFSUtil 
{
	public static String USER_HOME = System.getProperty("user.home");
	private static String VFS_PATH =  USER_HOME + FileName.SEPARATOR + ".vfs";

	private static Shell Shell = Display.getCurrent().getActiveShell();
	
	/*
	 * Missing from Commons VFS
	 */
	static public boolean isDirectory (FileObject f ) 
	{
		try {
			if ( f.getType() == null ) return false;
			return f.getType().equals(FileType.FOLDER);
		} catch (Exception e) {
			VFSView.error("VFSUtil.isDirectory: " + e);
			return false;
		}
	}

	/**
	 * Sorts files lexicographically by name.
	 * 
	 * @param files the array of Files to be sorted
	 */
	static void sortFiles(FileObject[] files) {
		/* Very lazy merge sort algorithm */
		sortBlock(files, 0, files.length - 1, new FileObject[files.length]);
	}
	private static void sortBlock(FileObject[] files, int start, int end, FileObject[] mergeTemp) {
		final int length = end - start + 1;
		if (length < 8) {
			for (int i = end; i > start; --i) {
				for (int j = end; j > start; --j)  {
					if (compareFiles(files[j - 1], files[j]) > 0) {
					    final FileObject temp = files[j]; 
					    files[j] = files[j-1]; 
					    files[j-1] = temp;
					}
			    }
			}
			return;
		}
		final int mid = (start + end) / 2;
		sortBlock(files, start, mid, mergeTemp);
		sortBlock(files, mid + 1, end, mergeTemp);
		int x = start;
		int y = mid + 1;
		for (int i = 0; i < length; ++i) {
			if ((x > mid) || ((y <= end) && compareFiles(files[x], files[y]) > 0)) {
				mergeTemp[i] = files[y++];
			} else {
				mergeTemp[i] = files[x++];
			}
		}
		for (int i = 0; i < length; ++i) files[i + start] = mergeTemp[i];
	}

	static int compareFiles(FileObject a, FileObject b) {

		// sort case-sensitive files in a case-insensitive manner
		int compare = a.getName().getBaseName().compareToIgnoreCase(b.getName().getBaseName());
		if (compare == 0) compare = a.getName().compareTo(b.getName());
		return compare;
	}

	/*
	 * Message Boxes
	 */
	public static void MessageBoxError(String text )
	{
		MessageDialog.openError(Shell, VFSView.getResourceString("msg.box.title") ,text);		
	}

	public static void MessageBoxInfo(String text )
	{
		MessageDialog.openInformation(Shell, VFSView.getResourceString("msg.box.title") ,text);		
		
	}

	public static boolean MessageBoxYesNo(String text )
	{
		return MessageDialog.openQuestion(Shell, VFSView.getResourceString("msg.box.title") ,text);		
		
	}
	
	/*
	 * Create a new file
	 */
	public static void newFile(FileObject parent) {
		InputDialog d = new InputDialog(Shell
				, VFSView.getResourceString("dialog.new.file.title", new Object[] {parent}) 
				, VFSView.getResourceString("dialog.new.file.label", new Object[] {parent})
				, "", new VFSUtil.LengthValidator() );
		
		if ( d.open() == Window.OK) {
			
			try {
				String newFolder = d.getValue();
				FileObject newFile = parent.resolveFile(newFolder);
				newFile.createFile();
				
			} catch (FileSystemException e) {
				MessageBoxError( e.getMessage());
			}
		}
	}

	/*
	 * Create a new Folder
	 */
	public static void newFolder(FileObject parent) {
		InputDialog d = new InputDialog(Shell
				, VFSView.getResourceString("dialog.new.folder.title", new Object[] {parent}) 
				, VFSView.getResourceString("dialog.new.folder.label", new Object[] {parent})
				, "", new VFSUtil.LengthValidator() );
		
		if ( d.open() == Window.OK) {
			String newFolder = d.getValue();
			
			try {
				FileObject newFile = parent.resolveFile(newFolder);
				newFile.createFolder();
				
			} catch (FileSystemException e) {
				MessageBoxError( e.getMessage());
			}
		}
	}

	/* Shell:
	 * Sets the title to indicate the selected directory
	 */
	public static void setUiStateIdle (Shell shell, String title) {
		
		shell.setText(VFSView.getResourceString("Title"
				, new Object[] { title }));
		
		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorDefault]);
	}
	
	public static void setUiStateListContents (Shell shell, String title) {

		shell.setText(VFSView.getResourceString("Title.Listing.files"
				, new Object[] { title }));
		
		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorWait]);
	}
	
	/**
	 * Load VFS URIs from $HOME/.vfs (1 URI per line)
	 * @param fsMananger
	 * @return
	 */
	public static TreeMap<String, FTPConnectionProperties> loadFileSystems ()  
	{
    	String[] connections = FTPConnectionProperties.getConnectionIds();
    	final TreeMap<String, FTPConnectionProperties> items = new TreeMap<String, FTPConnectionProperties>();
		for (int i=0;i<connections.length;i++) {
			FTPConnectionProperties connectionProperties = new FTPConnectionProperties(connections[i]);
			items.put(connections[i], connectionProperties);
		}
		return items;
	}

	/**
	 * Remove a file system URI from $HOME/.vfs
	 * @param uri URI to be removed
	 */
	public static boolean removeFileSystem(String uri)
	{
		if ( MessageBoxYesNo(VFSView.getResourceString("remove.remote.fs", uri)))
		{
			//System.out.println("remove uri=" + uri);
			try {
				if ( ! new File(VFS_PATH).exists() ) return false;
				
				StringBuffer buf 	= new StringBuffer();
				RandomAccessFile f 	= new RandomAccessFile(VFS_PATH, "r");
				
				String line 		= null;
				
				while ( (line = f.readLine()) != null ) {
					// ignore comments
					//if ( line.startsWith("#")) continue;
					if ( line.length() > 0  && !uri.equalsIgnoreCase(line)) {
						//System.out.println("found uri=" + uri);
						buf.append(line + "\n");
					}
				}
				f.close();
				
				// re-save stuff
				new FileOutputStream(VFS_PATH, false).write(buf.toString().getBytes());
				return true;
			} catch (Exception e) {
				VFSView.debug(e);
				return false;
			}
		}
		else 
			return false;
	}
	
	/**
	 * Add a File System object to the tree
	 * @param file
	 */
	public static void treeAddFsNode (Tree tree, FTPConnectionProperties connectionProperties)
	{
		TreeItem item = new TreeItem(tree, SWT.NULL);
		String nodeName = connectionProperties.getConnectionid() + " (" + connectionProperties.getURI() + ")";
		item.setText(nodeName); 
		String icon = connectionProperties.getType().equals("file") ? CFPluginImages.ICON_DRIVE : CFPluginImages.ICON_DRIVE_FTP;

		item.setImage(VFSView.iconCache.get(icon));
		item.setData(VFSView.TREEITEMDATA_FILE, nodeName );
		item.setData(VFSView.TREEITEMDATA_URI, connectionProperties.getURI()); //file );
		item.setData(VFSView.TREEITEMDATA_CONNECTIONID, connectionProperties.getConnectionid()); //file );
		item.setData(VFSView.TREEITEMDATA_IMAGEEXPANDED, VFSView.iconCache.get(CFPluginImages.ICON_DRIVE_SFTP));
		item.setData(VFSView.TREEITEMDATA_IMAGECOLLAPSED, VFSView.iconCache.get(CFPluginImages.ICON_DRIVE));
		new TreeItem(item, SWT.NULL); // placeholder child item to get "expand" button
	}

	/**
	 * Remove user/pwd information from a file system URI
	 * @param file
	 * @return
	 */
	static public String stripUserTokens (String file) 
	{
		try {
			if ( file.indexOf(' ') != -1)
				return file.toString();
			
			URI Uri 	= new URI(file);
			String port = (Uri.getPort() > 0) ? ":" + Uri.getPort() : "";
			String Path = (Uri.getScheme().equals("smb")) ? "/" : Uri.getPath();
			
			// remove paths from smb Uris
			
			String newUri = (Uri.getHost() != null ) 
				? Uri.getScheme() + "://" + Uri.getHost() + port + Path
				: file.toString(); 
			return newUri;
			
		} catch (URISyntaxException e) {
			//VFSView.error("stripUserTokens " + e);
			VFSView.debug(e);
			return file.toString();
		}
	}

	/**
	 * This class validates a String. It makes sure that the String is between 5 and 8
	 * characters
	 */
	static class LengthValidator implements IInputValidator {
	  /**
	   * Validates the String. Returns null for no error, or an error message
	   * 
	   * @param newText the String to validate
	   * @return String
	   */
	  public String isValid(String newText) {
	    int len = newText.length();

	    // Determine if input is too short or too long
	    if (len == 0) 
	    	return VFSView.getResourceString("dialog.new.folder.validator.label");

	    // Input must be OK
	    return null;
	  }
	}

	/*
	 * Build a String list of exception messages
	 */
	static public String getErrorMessageStack (Exception e) {
		StringBuffer buf = new StringBuffer(e.getMessage());
		Throwable t = e.getCause();
		
		while ( t != null) {
			buf.append(t.toString());
			t = t.getCause();
		}
		return buf.toString();
	}
	
	/**
	 * Compare 2 URIs using simple string comparissons. By host, scheme, and path only.
	 * This is used to avoid FileObject comparissons which attempt a server connection
	 * thus hanging the UI when one of the hosts is down
	 */
	public static boolean compareURIs(String uri1, String uri2) 
	{
		try {
			// A simple hack: if any space is present just compare the strings
			if ( uri1.indexOf(' ') != -1 || uri2.indexOf(' ') != -1)
				return uri1.equalsIgnoreCase(uri2);
			
			// This will throw a syntax error if the URI has spaces (file:// in Win32)
			final URI Uri1 = new URI(uri1);
			final URI Uri2 = new URI(uri2);
			
			final String path1 = Uri1.getPath().endsWith("/") ? Uri1.getPath().substring(0, Uri1.getPath().length() - 1 ) : Uri1.getPath() ;
			final String path2 = Uri2.getPath().endsWith("/") ? Uri2.getPath().substring(0, Uri2.getPath().length() - 1 ) : Uri2.getPath() ;
			
			boolean local = Uri1.getHost() == null || Uri2.getHost() == null;
			boolean b1 = Uri1.getScheme().equalsIgnoreCase(Uri2.getScheme());
			boolean b2 = path1.equalsIgnoreCase(path2);

//System.out.println("compareURIs uri1=" + uri1 + " uri2=" + uri2 + " Uri1=" + Uri1 + " Uri2=" + Uri2);		
//System.out.println("local="+ local + " b1=" +b1 + " b2=" + b2 + " Uri object comp=" + Uri1.equals(Uri2));

			// non local URIs must match: scheme, host,path
			if ( ! local) {
				boolean b3 = Uri1.getHost().equals(Uri2.getHost()); 
				if ( b1 && b2 && b3) {
//					System.err.println("compareURIs " + uri1 + " == " + uri2);
					return true;
				}
			}
			// local (file://) URIs must match scheme && path
			else if ( b1 && b2 ) {
//				System.err.println("compareURIs " + uri1 + " == " + uri2 );
				return true;
			}
		} 
		catch (Exception e) {
			VFSView.debug(e);
		}
		return false;
	}
	
	/**
	 * Get a file object VFS attributes as string
	 * @param file
	 * @return
	 */
	public static String getFileAttributes(FileObject file)
	{
		StringBuffer buf = new StringBuffer(file.getName().getPath());
		try {
			if ( file.getType() == null || ! file.getType().hasContent() ) 
				return buf.toString(); 
			
			FileContent c = file.getContent();

			// Get standard attributes: Size, last modified
			if ( ! isDirectory(file)) {
				buf.append("\n" + VFSView.getResourceString("table.Size.title") 
						+ ": " + c. getSize());
				buf.append("\n"+ VFSView.getResourceString("table.Modified.title")
						+ ": "	+ VFSView.dateFormat.format(new Date(c.getLastModifiedTime())));
			}
			
			// Get custom attributes
			String[] attNames = c.getAttributeNames();
			
			for (int i = 0; i < attNames.length; i++) {
				buf.append("\n" + attNames[i] + ": " +  c.getAttribute(attNames[i]) );
			}
		} catch (FileSystemException e) {
			VFSView.debug(e);
		}
		return buf.toString();
	}
	

	/**
	 * Run a commons VFS copy operation with progress monitor
	 * @param fsManager Common VFS file system manager
	 * @param sourceNames String[] of source URIs
	 * @param sourceConnectionId 
	 * @param targetFile Destination file object
	 * @param destConnectionId 
	 */
	public static void copyFiles (FileSystemManager fsManager, String[] sourceNames, FTPConnectionProperties sourceConnection, FileObject targetFile, FTPConnectionProperties destConnection)
	{
		// run a copy operation
        try {
        	// if copying 1 file, the progresss dialog will show a bouncing progress
        	boolean indeterminate = (sourceNames.length == 1);
        	
        	FileOperation copyOperation = new FileOperation(fsManager, FileOperation.COPY, indeterminate);
        	
        	// set copy op arguments
        	copyOperation.setCopyArgs(sourceNames, sourceConnection, targetFile, destConnection);
        	
        	// fire operation
        	new ProgressMonitorDialog(Shell).run(true, true, copyOperation);
        } 
        catch (InvocationTargetException e) 
        {
        	VFSView.debug(e);
        	
        	final String msg = (e.getMessage() != null) 
        		? e.getMessage() : e.getCause().getClass().toString();
        		
            VFSUtil.MessageBoxError(msg);
        } 
        catch (InterruptedException e) 
        {
        	VFSView.debug(e);
        	VFSUtil.MessageBoxInfo(VFSUtil.getErrorMessageStack(e));
        }
	}
}
