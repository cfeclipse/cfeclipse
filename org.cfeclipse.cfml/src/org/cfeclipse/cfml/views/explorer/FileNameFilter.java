/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.io.File;
import java.io.FileFilter;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;

/**
 * @author Stephen Milligan
 *
 * A file filter for filtering files in the explorer view.
 */
public class FileNameFilter implements FileFilter {

    
    private boolean allowFiles = true;
    private boolean allowDirectories = true;
    /**
     * 
     */
    public FileNameFilter() {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     * Determines whether or not the given file should be listed.
     */
    public boolean accept(File filename) {
        // TODO Auto-generated method stub
        if (filename.isDirectory() && !allowDirectories) {
            return false;
        }
        if (filename.isFile() && !allowFiles) {
            return false;
        }
        
        return true;
    }
    
    public boolean accept(FileObject filename) {
        // TODO Auto-generated method stub
        try {
			if (filename.getType().equals(FileType.FOLDER) && !allowDirectories) {
			    return false;
			}
			  if (filename.getType().equals(FileType.FILE)&& !allowFiles) {
		            return false;
		        }
			  if(filename.isHidden()){
				  return false;
			  }
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        
        return true;
    }

    /**
     * If this is false, files will be omitted from the result
     * @param allow
     */
    public void allowFiles(boolean allow) {
        allowFiles = allow;
    }
    
    /**
     * If this is false, directories will be omitted from the result
     * @param allow
     */
    public void allowDirectories(boolean allow) {
        allowDirectories = allow;
    }
}
