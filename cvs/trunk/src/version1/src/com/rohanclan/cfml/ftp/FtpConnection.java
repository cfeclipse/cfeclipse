/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.ftp;

import java.io.File;
import java.io.FileFilter;

import com.rohanclan.cfml.views.explorer.IFileProvider;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FtpConnection implements IFileProvider {

    /**
     * 
     */
    public FtpConnection() {
        
    }

    public File[] getRoots() {
        return new File[0];
    }
    
    
    public File[] getChildren(File parent, FileFilter filter) {
        return new File[0];
    }
    
    public void dispose() {
        
    }
    
    public String toString() {
        return "New FTP connnection...";
    }
}
