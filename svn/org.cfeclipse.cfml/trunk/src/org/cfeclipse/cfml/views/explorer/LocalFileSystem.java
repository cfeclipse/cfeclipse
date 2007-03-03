/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;

import org.cfeclipse.cfml.util.AlertUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.part.FileEditorInput;


/**
 * @author Stephen Milligan
 * 
 */
public class LocalFileSystem implements IFileProvider
{

	File[] systemroot = File.listRoots();

	private static Object[] roots = null;

	private static IViewPart viewpart = null;

	/**
	 * 
	 */
	public LocalFileSystem()
	{
		super();
	}

	public Object[] getRoots()
	{
		/*
		 * ArrayList tmpRoots = new ArrayList(); FileSystemView view =
		 * FileSystemView.getFileSystemView(); for (int i=0;i<systemroot.length;i++) {
		 * if (!view.isFloppyDrive(systemroot[i])) { String name =
		 * view.getSystemDisplayName(systemroot[i]);
		 * 
		 * if (name == null) { name = ""; } name = name.trim(); if (name == null ||
		 * name.length() < 1) { name = ""; }
		 * 
		 * 
		 * 
		 * //int index = name.lastIndexOf(" ("); //if (index > 0) { // name =
		 * name.substring(0, index); //}
		 * 
		 * if (name.length() > 0) { FileSystemRoot drive = new
		 * FileSystemRoot(name); drive.setPath(systemroot[i].toString());
		 * tmpRoots.add(drive); } else { String driveLetter =
		 * systemroot[i].toString(); int index =driveLetter.indexOf("\\"); if
		 * (index > 0) { driveLetter = driveLetter.substring(0,index); }
		 * FileSystemRoot drive = new
		 * FileSystemRoot(view.getSystemTypeDescription(systemroot[i]) + " (" +
		 * driveLetter + ")"); drive.setPath(systemroot[i].toString());
		 * tmpRoots.add(drive); }
		 *  } }
		 */
		if (roots == null)
		{
			ArrayList tmpRoots = new ArrayList();
			for (int i = 0; i < systemroot.length; i++)
			{
				String driveLetter = systemroot[i].toString();
				FileSystemRoot drive = new FileSystemRoot(driveLetter);
				drive.setType("file");
				drive.setPath(systemroot[i].toString());
				tmpRoots.add(drive);
			}
			roots = tmpRoots.toArray();
		}
		return roots;
	}

	public void connect()
	{
		// Shouldn't need to do anything here.
		AlertUtils.showStatusMessage("Connected to: Local Filesystem", viewpart);
		return;
	}

	public void disconnect()
	{

	}

	public void setViewPart(IViewPart part)
	{
		viewpart = part;
	}

	public Object[] getChildren(String parent, FileNameFilter filter)
	{

		File[] children = new File(parent).listFiles(filter);
		if (children == null)
		{
			return new File[0];
		}
		else
		{
			return children;
		}
	}

	protected Object getFileStore(File f)
	{
		Object result = null;
		try
		{
			// to make this compatible with 3.1 and 3.2, we have to do some fun
			// casting and reflection stuff
			// import org.eclipse.core.filesystem.EFS;
			// import org.eclipse.core.filesystem.IFileStore;
			Class efs = Class.forName("org.eclipse.core.filesystem.EFS");
			if (efs != null)
			{
				Method getStore = efs.getMethod("getStore", new Class[] { URI.class });
				if (getStore != null)
				{
					result = getStore.invoke(efs, new Object[] { f.toURI() });
				}
			}

		}
		catch (Throwable e)
		{
			// ClassNotFoundException or CoreException, only because of the
			// casting and reflection, we don't know that it's
			// a CoreException
			result = null;
		}
		return result;
	}

	protected Object getFileOrFileStore(File f)
	{
		Object result = getFileStore(f);
		if (result == null)
		{
			result = f;
		}
		return result;
	}

	protected Class getFileOrFileStoreClass()
	{
		Class result = null;
		try
		{
			result = Class.forName("org.eclipse.core.filesystem.IFileStore");
		}
		catch (ClassNotFoundException e)
		{
			result = java.io.File.class;
		}
		return result;
	}

	protected IEditorInput getJavaFileEditorInput(Object f)
	{		
		IEditorInput result = null;
		try
		{
			Class inputClass = Class.forName("org.eclipse.ui.internal.editors.text.JavaFileEditorInput");
			
			// the 3.1 class takes a simple File object, the 3.2 class takes an IFileStore
			// currently f is the appropriate thing already, so we just need the right class to get the right constructor
			Constructor c = inputClass.getConstructor(new Class[]{getFileOrFileStoreClass()});
			result = (IEditorInput)c.newInstance(new Object[]{f});
		} catch (Throwable t) {
			result = null;
		}
		return result;
	}

	public IEditorInput getEditorInput(String filename)
	{

		// More info can be found in the original opening of an
		// ExternalFileAction
		// org.eclipse.ui.internal.editors.text.OpenExternalFileAction

		IPath path = new Path(filename);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// This returns a null if the file isnt already in the workspace
		IFile file = root.getFileForLocation(path);
		if (file != null)
		{
			return new FileEditorInput(file);
		}

		// This is truly an external file, so lets get the store for it and open
		// it!

		// get the filestore in a eclipse patform unspecific way
		Object f = getFileOrFileStore(path.toFile());

		return getJavaFileEditorInput(f);
	}

	public void dispose()
	{
	};

	public String toString()
	{
		return "Local Filesystem";
	}
}
