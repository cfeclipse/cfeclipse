/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.net;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;


/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RemoteFile {

    
	FileObject fileItem = null;
    String fPath = null;
    
    boolean fExists = true;
    
    boolean fReadOnly = true;
    
    boolean fDirectory = false;
    
    long fSize = -1;
    
    String fPermissions = "";
    /**
     * 
     */
    public RemoteFile(String path, boolean directory) {
        this.fPath = path;
        this.fDirectory = directory;
    }
    
    public RemoteFile(FileObject ftpFile, String path) throws FileSystemException {
        if (path.startsWith("//")) {
            path = path.substring(1);
        }
        this.fileItem = ftpFile;
        this.fPath = path;
        this.fDirectory = ftpFile.getType().equals(FileType.FOLDER);
      //  this.fPermissions = ftpFile.ggetPermissions();
        //System.out.println(ftpFile.getRaw());
        if(ftpFile.getType().equals(FileType.FILE)){
        	this.fSize = ftpFile.getContent().getSize();
        }
    }
    
    public boolean exists() {
    	
        try {
			return this.fileItem.exists();
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	return false;
		}
        return true;
    }
    
    public boolean canWrite() {
    	
    	try {
			return this.fileItem.isWriteable();
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*if (fPermissions == null) {
        	return true;
        }
    	
    	//TODO: Make sure that you can write this file, otherwise its always returning false for a person
        if (fPermissions.length() >= 6 
                && (fPermissions.charAt(5) == 'w'  || fPermissions.charAt(2) == 'w')) {
            return true;
        }
        */
        return false;
        
    }
    
    public boolean canRead() {
    	try {
			return this.fileItem.isReadable();
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
       /* if (fPermissions == null) {
        	return true;
        }
        if (fPermissions.length() >= 5 
                && fPermissions.charAt(4) == 'r') {
            return true;
        }
        return false;*/
        
    }
    
    public String getName() {
    	return this.fileItem.getName().getBaseName();
		/*if (fPath.lastIndexOf('/') >= 0)
			return fPath.substring(fPath.lastIndexOf('/') + 1);
		else
			return fPath;*/
	}
    
    public void setPermissions(String permissions) {
        
        fPermissions = permissions;
    }
    

    public String getAbsolutePath() {
        return fPath;
    }
    
    public boolean isDirectory() {
        return fDirectory;
    }
    
    public String toString() {
        return fPath;
    }
    
    public long size() {
        return fSize;
    }
    
}
