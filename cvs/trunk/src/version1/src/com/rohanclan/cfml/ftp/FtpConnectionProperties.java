/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Properties;

import com.rohanclan.cfml.CFMLPlugin;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FtpConnectionProperties {

	private Properties connectionProperties;
	

	private static String fHost = "host";
	private static String fPath = "path";
	private static String fUsername = "username";
	private static String fPassword = "password";
	private static String fConnectionid = "connectionid";
	
	File storageDirectory;
	
	

    
    
    public static String[] getConnectionIds() {
    	File storageDirectory = new File(CFMLPlugin.getDefault().getStateLocation().toString() + "/ftpconnections");
		if (!storageDirectory.exists()) {
			storageDirectory.mkdir();
		}
		
		
		
		String connections[] = storageDirectory.list();
		
		if (connections != null) {
			return connections;
		}
		
		return new String[0];
		
    }


    public static void deleteConnection(String connectionid) {
    	File storageFile = new File(CFMLPlugin.getDefault().getStateLocation().toString() + "/ftpconnections/" + connectionid);

    	if (storageFile.exists()) {
    		storageFile.delete();
    	}
    	
    	
    }
	
	
	
	
    /**
     * 
     */
    public FtpConnectionProperties(String connectionId) {
    	storageDirectory = new File(CFMLPlugin.getDefault().getStateLocation().toString() + "/ftpconnections");
    	connectionProperties = new Properties();
    	
    	
    	if (connectionId != null) {
    		String[] connections = getConnectionIds();
	    	for (int i=0;i<connections.length;i++) {
				if (connections[i].equalsIgnoreCase(connectionId)) {
					File connectionFile = new File(storageDirectory.toString() + "/" + connections[i]);
					try {
						FileInputStream input = new FileInputStream(connectionFile);
						connectionProperties.load(input);
						input.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}    
    	}
    }
    
    public void save() {
    	File outputFile = new File(storageDirectory + "/" + connectionProperties.getProperty(fConnectionid));
    	try {
	    	FileOutputStream outputStream = new FileOutputStream(outputFile);
	    	connectionProperties.store(outputStream,"FTP Connection details.");
	    	outputStream.close();
	    	System.out.println("Ftp connection details saved to " + outputFile.toString());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    public String getConnectionid() {
    	return connectionProperties.getProperty(fConnectionid,"");
    }

    public void setConnectionid(String connectionid) {
    	connectionProperties.setProperty(fConnectionid,connectionid);
    }


    public String getHost() {
    	return connectionProperties.getProperty(fHost,"");
    }

    public void setHost(String host) {
    	connectionProperties.setProperty(fHost,host);
    }
    

    public String getPath() {
    	return connectionProperties.getProperty(fPath,"/");
    }

    public void setPath(String path) {
    	connectionProperties.setProperty(fPath,path);
    }

    

    public String getUsername() {
    	return connectionProperties.getProperty(fUsername,"");
    }

    public void setUsername(String username) {
    	connectionProperties.setProperty(fUsername,username);
    }

    

    public String getPassword() {
    	return connectionProperties.getProperty(fPassword,"");
    }

    public void setPassword(String password) {
    	connectionProperties.setProperty(fPassword,password);
    }
    
    
    public String toString() {
    	return connectionProperties.getProperty(fConnectionid);
    }
    
    
    
}
