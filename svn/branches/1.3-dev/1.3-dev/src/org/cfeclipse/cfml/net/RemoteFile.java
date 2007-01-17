/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.net;

import com.enterprisedt.net.ftp.FTPFile;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RemoteFile {

    
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
    
    public RemoteFile(FTPFile ftpFile, String path) {
        if (path.startsWith("//")) {
            path = path.substring(1);
        }
        this.fPath = path;
        this.fDirectory = ftpFile.isDir();
        this.fPermissions = ftpFile.getPermissions();
        //System.out.println(ftpFile.getRaw());
        this.fSize = ftpFile.size();
    }
    
    public boolean exists() {
        return fExists;
    }
    
    public boolean canWrite() {
    	if (fPermissions == null) {
        	return true;
        }
    	
    	//TODO: Make sure that you can write this file, otherwise its always returning false for a person
        if (fPermissions.length() >= 6 
                && (fPermissions.charAt(5) == 'w'  || fPermissions.charAt(2) == 'w')) {
            return true;
        }
        return false;
        
    }
    
    public boolean canRead() {
        if (fPermissions == null) {
        	return true;
        }
        if (fPermissions.length() >= 5 
                && fPermissions.charAt(4) == 'r') {
            return true;
        }
        return false;
        
    }
    
    public String getName() {
		if (fPath.lastIndexOf('/') >= 0)
			return fPath.substring(fPath.lastIndexOf('/') + 1);
		else
			return fPath;
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
