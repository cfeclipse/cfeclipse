/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;


import org.eclipse.ui.IEditorInput;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface IFileProvider {
    
    public static String INVALID_FILESYSTEM = "Invalid file system.";
    

    public Object[] getRoots();
    
    public Object[] getChildren(String parent, FileNameFilter filter);
    
    public void dispose();
    
    public IEditorInput getEditorInput(String filename);
    
}
