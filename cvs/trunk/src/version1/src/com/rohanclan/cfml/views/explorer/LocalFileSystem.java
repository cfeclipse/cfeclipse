/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;

import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;

import com.rohanclan.cfml.util.AlertUtils;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LocalFileSystem implements IFileProvider {

    File[] systemroot = File.listRoots();
    
    private static IViewPart viewpart = null;
    
    /**
     * 
     */
    public LocalFileSystem() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Object[] getRoots() {
        return systemroot;
    }
    
    public void connect() {
        // Shouldn't need to do anything here.
        AlertUtils.showStatusMessage("Connected to: Local Filesystem",viewpart);
        return;
    }
    
    public void disconnect() {
        
    }
    
    public void setViewPart(IViewPart part) {
        viewpart = part;
    }
    
    public Object[] getChildren(String parent, FileNameFilter filter) {
    	
        File[] children = new File(parent).listFiles(filter);
        if (children == null) {
            return new File[0];
        }
        else {
            return children;
        }
    }
   
    public IEditorInput getEditorInput(String filename) {

        JavaFileEditorInput input = new JavaFileEditorInput(new File(filename));
        return input;
    }
    
    
    public void dispose() {};
    
    public String toString() {
        return "Local Filesystem";
    }
}
