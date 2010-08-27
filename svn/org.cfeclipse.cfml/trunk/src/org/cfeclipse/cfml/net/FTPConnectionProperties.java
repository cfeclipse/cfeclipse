/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.cfeclipse.cfml.CFMLPlugin;


/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FTPConnectionProperties {

	private Properties connectionProperties;
	
	private static String ftype = "type";
	private static String fHost = "host";
	private static String fPath = "path";
	private static String fUsername = "username";
	private static String fPassword = "password";
	private static String fConnectionid = "connectionid";
	private static String fPort = "port";
	private static String fPassive = "passive";
	private static String fUserDirIsRoot = "userdirisroot";
	private static String fSecure = "secure";
	private static String fTimeoutSeconds = "timeout";
	private static String fStrictHostKeyCheck = "stricthostkey";
	private static String fHostsFile = "hostsfile";
	public static String USER_HOME = System.getProperty("user.home");
	
	
	File storageDirectory;

	private FileSystemOptions fileSystemOptions;

		
    public static String[] getConnectionIds() {
    	File storageDirectory = new File(CFMLPlugin.getDefault().getStateLocation().toString() + "/ftpconnections");
		if (!storageDirectory.exists()) {
			storageDirectory.mkdir();
		}
		
		String connections[] = storageDirectory.list();
		
		if (connections != null) {
			Arrays.sort(connections);
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
    public FTPConnectionProperties(String connectionId) {
    	storageDirectory = new File(CFMLPlugin.getDefault().getStateLocation().toString() + "/ftpconnections");
    	connectionProperties = new Properties();
        fileSystemOptions = new FileSystemOptions();
    	
    	if (connectionId != null) {
    		setConnectionid(connectionId);
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
	    	StaticUserAuthenticator auth = new StaticUserAuthenticator(connectionId, getUsername(), getPassword());
	    	try {
	    		DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, auth);
	    	} catch (FileSystemException e2) {
	    		// TODO Auto-generated catch block
	    		e2.printStackTrace();
	    	}
            if(getType().equals("ftp")) {
				//FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, connectionProperties.getPassive());
				FtpFileSystemConfigBuilder.getInstance().setDataTimeout(fileSystemOptions, getTimeoutSeconds()*100);
				FtpFileSystemConfigBuilder.getInstance().setSoTimeout(fileSystemOptions, getTimeoutSeconds() * 100);
				FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, getUserDirIsRoot());
			} else if(getType().equals("sftp")) {
				try {
					SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
					SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, getUserDirIsRoot());
				} catch (FileSystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SftpFileSystemConfigBuilder.getInstance().setTimeout(fileSystemOptions, getTimeoutSeconds() * 100);
			}
    	}
    }
    
    public FileSystemOptions getFileSystemOptions() {
		return fileSystemOptions;
	}


	public void setFileSystemOptions(FileSystemOptions fileSystemOptions) {
		this.fileSystemOptions = fileSystemOptions;
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
    
	public FTPConnectionProperties copy(String connectionid) {
		String[] connectionIds = getConnectionIds();
		
		FTPConnectionProperties connectionProperties = new FTPConnectionProperties(connectionid);
		int x = 1;
		String nextConnectionId = connectionid + " " + Integer.toString(x);
		while(Arrays.binarySearch(connectionIds, nextConnectionId)>0){
			x++;
			nextConnectionId = connectionid + " " + Integer.toString(x);
		}
		connectionProperties.setConnectionid(nextConnectionId);
		return connectionProperties;
    }
    
    public String getConnectionid() {
    	return connectionProperties.getProperty(fConnectionid,"");
    }

    public void setConnectionid(String connectionid) {
    	connectionProperties.setProperty(fConnectionid,connectionid);
    }

    public String getType() {
    	String property = connectionProperties.getProperty(ftype,"");
    	if(property == null || property.length() == 0){		//Backwards compatability
    		return "file";
    	}
    	return connectionProperties.getProperty(ftype,"");
    }
    
    public String getURI() {
        String connectionType = getType().toLowerCase();
        String connectionString = connectionType + ":";
		//if(connectionType.equals("ftp") || connectionType.equals("sftp")) {
        	connectionString += "//";
		//}
        if(getHost().length() > 0) {
        	connectionString += getHost();
        }
        if(getPort() > 0 && (connectionType.equals("ftp") || connectionType.equals("sftp") || connectionType.equals("smb"))) {
        	connectionString += ":" + getPort();
        }
        // Add the path to request if one was given
        if(getPath().length() > 0) {
        	if (getPath().charAt(0) != '/') {
        		connectionString += "/";
        	}
        	connectionString += getPath();
        }
        return connectionString;    	
    }

    
    public void setURI(String uriString) {
    	try {
			URI uri = new URI(uriString);
			setHost(uri.getHost() == null ? "" : uri.getHost());
			setPort(uri.getPort());
			setPath(uri.getPath());
			setType(uri.getScheme());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void setType(String type) {
    	connectionProperties.setProperty(ftype,type);
    }

    public String getHost() {
    	return connectionProperties.getProperty(fHost,"");
    }

    public void setHost(String host) {
    	connectionProperties.setProperty(fHost,host);
    }


    public int getPort() {
    	return Integer.parseInt(connectionProperties.getProperty(fPort, "21"));
    }

    public void setPort(int port) {
    	connectionProperties.setProperty(fPort,String.valueOf(port));
    }


    public boolean getPassive() {
    	return new Boolean(connectionProperties.getProperty(fPassive,"true")).booleanValue() ;
    }

    public void setPassive(boolean passive) {
    	connectionProperties.setProperty(fPassive,String.valueOf(passive));
    }

    public boolean getUserDirIsRoot() {
    	return new Boolean(connectionProperties.getProperty(fUserDirIsRoot,"true")).booleanValue() ;
    }

    public void setUserDirIsRoot(boolean userDirIsRoot) {
    	connectionProperties.setProperty(fUserDirIsRoot,String.valueOf(userDirIsRoot));
    }


    public boolean getSecure() {
        Boolean secure = new Boolean(connectionProperties.getProperty(fSecure,"false"));
    	return secure.booleanValue();
    }

    public void setSecure(boolean secure) {
    	connectionProperties.setProperty(fSecure,String.valueOf(secure));
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


	public void setTimeoutSeconds(String seconds) {
    	connectionProperties.setProperty(fTimeoutSeconds,seconds);
	}
    
	public int getTimeoutSeconds() {
    	return Integer.parseInt(connectionProperties.getProperty(fTimeoutSeconds, "5"));
	}

	public void setStrictHostKeyCheck(String strictHostKeyCheck) {
    	connectionProperties.setProperty(fStrictHostKeyCheck,strictHostKeyCheck);
	}

	public Boolean getStrictHostKeyCheck() {
    	return Boolean.parseBoolean(connectionProperties.getProperty(fStrictHostKeyCheck,"false"));
	}

	public void setHostsFile(String hostsFile) {
    	connectionProperties.setProperty(fHostsFile,hostsFile);
	}
	public String getHostsFile() {
    	return connectionProperties.getProperty(fHostsFile,USER_HOME + FileName.SEPARATOR + ".ssh/known_hosts");
	}
	
	
    
}
