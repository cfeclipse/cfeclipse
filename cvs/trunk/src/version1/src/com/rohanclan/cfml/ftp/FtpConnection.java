/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.ftp;

import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;

import org.eclipse.ui.IEditorInput;

import com.enterprisedt.net.ftp.*;

import com.rohanclan.cfml.views.explorer.IFileProvider;
import com.rohanclan.cfml.views.explorer.FileNameFilter;

/**
 * @author spike
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FtpConnection implements IFileProvider {

	//FTPClient ftpClient = null;
    FTPClient ftpClient = null;
	FtpConnectionProperties connectionProperties;
	private static FtpConnection instance = null;
	
	
	private int fConnectionTimeout = 30000;
	
	public static FtpConnection getInstance() {
	    if (instance == null) {
	        instance = new FtpConnection();
	    }
	    return instance;
	}
	
	/**
	 * 
	 */
	public FtpConnection() {
		ftpClient = null;
	}
	
	public BufferedInputStream getInputStream(String filepath) {
	    connect();
	    try {
	        
		    byte[] contents = ftpClient.get(filepath);
		    ByteArrayInputStream ins = new ByteArrayInputStream(contents);
		    BufferedInputStream bis = new BufferedInputStream(ins);
		    return bis;
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void saveFile(byte[] content, String remotefile) {
	    connect();
	    try {
	        ftpClient.put(content,remotefile);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void disconnect() {
	    try {
	        ftpClient.quit();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void connect(FtpConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
		connect();
	}
	
	private void connect() {
	    if (isConnected()) {
		    return;
		}
		try {
			ftpClient = new FTPClient(connectionProperties.getHost(),21,fConnectionTimeout);
			FTPMessageCollector listener = new FTPMessageCollector();
	        ftpClient.setMessageListener(listener);
	        
	
	        // login
	       ftpClient.login(connectionProperties.getUsername(), connectionProperties.getPassword());
	
	       ftpClient.setConnectMode(FTPConnectMode.PASV);
	       ftpClient.setType(FTPTransferType.ASCII);
		}
		catch (Exception e) {
		    
		    e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#getRoots()
	 */
	public Object[] getRoots() {

	    return new String[] {connectionProperties.getPath()};
	    
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#getChildren(java.io.File, java.io.FileFilter)
	 */
	public Object[] getChildren(String parent, FileNameFilter filter) {
		
		try {
		    
		    connect();
		    
		    FTPFile[] files = ftpClient.dirDetails(parent);
		    
		    if (files == null) {
				files = new FTPFile[0];
			}
		    
		    ArrayList filteredFileList = new ArrayList();
		    for (int i=0;i<files.length;i++) {
		        if (filter.accept(files[i])) {
		            RemoteFile file = new RemoteFile(files[i],parent + "/" + files[i].getName());
		            filteredFileList.add(file);
		        }
		    }
		    
		    Object[] filteredFiles = filteredFileList.toArray();
		    
			return filteredFiles;
		}

		catch (Exception e) {
		    e.printStackTrace();
		}
		return new String[0];
	}
	
	

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#dispose()
	 */
	public void dispose() {
		try {
			if (ftpClient != null) {			
				//System.out.println("Disconnecting FTP client.");
				ftpClient.quit();
				ftpClient = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isConnected() {
	    if (ftpClient == null) {
	        return false;
	    }
	    try {
	        ftpClient.quote("NOOP",new String[] {"200"});
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
	
	public IEditorInput getEditorInput(String filename) {
	    try {
	        connect();
		    FTPFile[] files = ftpClient.dirDetails(filename);
		    RemoteFile remoteFile = new RemoteFile(files[0],filename);
		    FtpFileEditorInput input = new FtpFileEditorInput(remoteFile);
	        return input;
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
