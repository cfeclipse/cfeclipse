/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LocalFileSystem implements IFileProvider {

    File[] systemroot = File.listRoots();
    
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
    
    
    public Object[] getChildren(String parent, FileFilter filter) {
    	
        File[] children = new File(parent).listFiles(filter);
        if (children == null) {
            return new File[0];
        }
        else {
            return children;
        }
    }
    
    public void dispose() {};
    
    public String toString() {
        return "Local Filesystem";
    }
}
