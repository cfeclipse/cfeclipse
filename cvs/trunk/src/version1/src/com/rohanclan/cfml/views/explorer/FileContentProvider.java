/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import java.io.*;

class FileContentProvider implements IStructuredContentProvider {
    
    File[] contents = new File[] {};
    IFileProvider fileProvider;

    private FileNameFilter fileFilter = new FileNameFilter();
    
    public FileContentProvider() {
        fileFilter.allowDirectories(false);
    }
    
    public Object[] getElements(Object inputElement) {
        if (fileProvider == null) {
            return new Object[] {IFileProvider.INVALID_FILESYSTEM};
        }
        if (inputElement != null) {
            String directoryName = inputElement.toString();
            if (directoryName.indexOf("[") == 0) {
                directoryName = directoryName.substring(1,directoryName.length()-1);
            }
            
            File parent = new File(directoryName);
            return fileProvider.getChildren(parent,fileFilter);
            
        }
        else {
            contents = new File[] {};
        }
        return contents;
    }
    public void dispose() {
    }
    
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput instanceof IFileProvider) {
            fileProvider = (IFileProvider)newInput;
        }
        else {
            fileProvider = null;
        }
    }
    
    
    
}