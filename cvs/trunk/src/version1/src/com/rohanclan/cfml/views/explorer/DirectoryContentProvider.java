/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.rohanclan.cfml.ftp.*;

import org.apache.commons.net.ftp.*;


import java.io.*;

class DirectoryContentProvider implements IStructuredContentProvider, ITreeContentProvider {
    
    
    private FileNameFilter directoryFilter = new FileNameFilter();
    private IFileProvider fileProvider = null;
    
    public DirectoryContentProvider() {
        directoryFilter.allowFiles(false);
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    	if (fileProvider != null) {
    		fileProvider.dispose();
    	}
        if (newInput instanceof IFileProvider) {
            fileProvider = (IFileProvider)newInput;
        }
        else if (newInput instanceof FtpConnectionProperties) {
        	fileProvider = new FtpConnection((FtpConnectionProperties)newInput);
        }
        else {
        	
            fileProvider = null;
        }
    }
    public void dispose() {
    	if (fileProvider != null) {
    		fileProvider.dispose();
    	}
    }
    
    
    public Object[] getElements(Object inputElement) {

        Object parent = inputElement;
        
        
        return getChildren(parent);
    }
    public Object[] getChildren(Object parentElement) {
    	try {
    		
	        if (fileProvider == null){
	            return new Object[] {IFileProvider.INVALID_FILESYSTEM};
	        }
	        if (parentElement instanceof LocalFileSystem
	        		|| parentElement instanceof FtpConnectionProperties) {
	        	
	        	return fileProvider.getRoots();
	        	
	        }
	        else {
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
    		
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
}