/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.rohanclan.cfml.ftp.*;



class DirectoryContentProvider implements IStructuredContentProvider, ITreeContentProvider {
    

    private FileNameFilter directoryFilter = new FileNameFilter();
    private IFileProvider fileProvider = null;
    
    public DirectoryContentProvider() {
        directoryFilter.allowFiles(false);
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        try {
	    	if (fileProvider != null) {
	    		fileProvider.dispose();
	    	}
	        if (newInput instanceof IFileProvider) {
	            fileProvider = (IFileProvider)newInput;
	        }
	        else if (newInput instanceof FtpConnectionProperties) {
	        	fileProvider = FtpConnection.getInstance();
	        	((FtpConnection)fileProvider).connect((FtpConnectionProperties)newInput);
	        }
	        else {
	        	
	            fileProvider = null;
	        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dispose() {
    	if (fileProvider != null) {
    		fileProvider.dispose();
    	}
    }
    
    
    public Object[] getElements(Object inputElement) {
        
        return getChildren(inputElement);
    }
    
    public Object[] getChildren(Object parentElement) {
        //System.out.println("Parent element is: " + parentElement.getClass().getName());
       
    	try {
    		
	        if (fileProvider == null){
	            return new Object[] {IFileProvider.INVALID_FILESYSTEM};
	        }
	        if (parentElement instanceof LocalFileSystem
	        		|| parentElement instanceof FtpConnectionProperties) {
	        	
	        	return fileProvider.getRoots();
	        	
	        } else if (parentElement instanceof RemoteFile) {
	            RemoteFile file = (RemoteFile)parentElement;
	            
	            return fileProvider.getChildren(((RemoteFile)parentElement).getAbsolutePath(),directoryFilter);
	            
	        } else {
                return fileProvider.getChildren(parentElement.toString(),directoryFilter);
	        }
    	}
        catch (Exception e) {
        	e.printStackTrace();
            return new Object[] {"Error! " + e.getMessage()};
        }
        
    }
    public Object getParent(Object element) {
        return null;
    }
    
    public boolean hasChildren(Object element) {
    	try {
    		if (element instanceof RemoteFile) {
    		   return ((RemoteFile)element).isDirectory();
    		}
    		else if (element instanceof File) {
    		    return ((File)element).isDirectory();
    		}
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
}