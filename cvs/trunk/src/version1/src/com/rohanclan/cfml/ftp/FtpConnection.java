/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.ftp;

import java.io.FileFilter;

import org.apache.commons.net.ftp.*;


import com.rohanclan.cfml.views.explorer.IFileProvider;

/**
 * @author spike
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FtpConnection implements IFileProvider {

	FTPClient ftpClient = null;
	FtpConnectionProperties connectionProperties;
	/**
	 * 
	 */
	public FtpConnection(FtpConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(connectionProperties.getHost());
			System.out.println("Connected to " + connectionProperties.getHost());
			ftpClient.login(connectionProperties.getUsername(),connectionProperties.getPassword());
			System.out.println("Logged in to  " + connectionProperties.getHost());
			
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
	public Object[] getChildren(String parent, FileFilter filter) {
		//secret code....nat loves finn...xxxoooo
		try {
			
			System.out.println("Getting children of " + parent);
			int status = ftpClient.pasv();
			System.out.println("PASV got status " + status);
			System.out.println(ftpClient.getReplyString());
			
			/*
			//ftpClient.changeWorkingDirectory(parent);
			FTPFile[] files = ftpClient.listFiles();
			System.out.println("Listing files got " + files.length + " files ");
			System.out.println(ftpClient.getReplyString());
			
			if (files == null) {
				files = new FTPFile[0];
			}
			*/
			
			String[] names = ftpClient.listNames(parent);
			System.out.println("listNames() got " + names.length + " entries.");
			System.out.println(ftpClient.getReplyString());
			
			for (int i=0;i<names.length;i++) {
				System.out.println(names[i]);
			}
			
			return names;
		}
		catch (Exception e) {
			return new String[0];
		}
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#dispose()
	 */
	public void dispose() {
		try {
			if (ftpClient != null 
					&& ftpClient.isConnected()) {			
				System.out.println("Disconnecting FTP client.");
				ftpClient.disconnect();
				System.out.println(ftpClient.getReplyString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
