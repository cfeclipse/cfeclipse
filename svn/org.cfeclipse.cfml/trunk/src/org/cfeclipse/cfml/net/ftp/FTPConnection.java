/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.net.ftp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.operations.FileOperationProvider;
import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.net.RemoteFileEditorInput;
import org.cfeclipse.cfml.util.AlertUtils;
import org.cfeclipse.cfml.views.explorer.FileNameFilter;
import org.cfeclipse.cfml.views.explorer.FileSystemRoot;
import org.cfeclipse.cfml.views.explorer.IFileProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;





/**
 * @author spike
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FTPConnection implements IFileProvider {


    //protected static FTPClient ftpClient = null;

    protected FTPConnectionProperties connectionProperties;

    protected static FTPConnection instance = null;

    protected static LogListener listener = new LogListener();

    protected IViewPart viewPart = null;

    protected int fConnectionTimeout = 15000;

    protected boolean connectFailed = false;
    
    protected static DefaultFileSystemManager manager = null;
    
    public static String CONNECT_FAILED = "Could not connect to server.";
    
    
    public boolean connectFailed() {
        return connectFailed;
    }

    /**
     *  
     */
    public FTPConnection() {
        //ftpClient = null;
    	
    }

    public static FTPConnection getInstance() {
        return instance;
    }
    
    public void setConnectionProperties(Object props) {
        if (props instanceof FTPConnectionProperties) {
            this.connectionProperties = (FTPConnectionProperties)props;
            manager = null;
        }
    }
    /**
     * This allows the file provider to add status messages to the view status bar.
     * @param viewPart
     */
    public void setViewPart(IViewPart viewPart) {
        this.viewPart = viewPart;
    }

    public BufferedInputStream getInputStream(String filepath) {

        connect();

        if (connectFailed) {
            return null;
        }
        try {
        	FileObject object = manager.getBaseFile().resolveFile(filepath);
        	InputStream inStrm = object.getContent().getInputStream();
           // byte[] contents = inStrm..
           // ByteArrayInputStream ins = new ByteArrayInputStream(contents);
            BufferedInputStream bis = new BufferedInputStream(inStrm);
            return bis;
        } catch (Exception e) {
            AlertUtils.alertUser(e);
            return null;
        }
    }

    public void saveFile(byte[] content, String remotefile) {
        connect();

        try {
          //  ftpClient.put(content, remotefile);
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }
    
    public void mkdir(String remoteFolder) {
        connect();

        try {
           // ftpClient.mkdir(remoteFolder);
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }

    public void delete(String remotefile) {
        connect();

        try {
           // ftpClient.delete(remotefile);
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }
    public void rmdir(String remotefile) {
        connect();

        try {
           // ftpClient.rmdir(remotefile);
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }
    public void disconnect() {
        try {
            if (isConnected()) {
               // ftpClient.quit();
                connectFailed = false;
            }
            AlertUtils.showStatusMessage("Disconnected", viewPart);
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }

    public void connect(FTPConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
        connectFailed = false;

        connect();
    }

    public void connect() {
        /*if (isConnected()) {
            return;
        }*/
        try {
            
            AlertUtils.showStatusErrorMessage(null,viewPart);

            this.manager = (DefaultFileSystemManager)VFS.getManager();
        	
            
            //Set up a URL
            String connectionString = connectionProperties.getType() + ":";
            
            if(connectionProperties.getType().equalsIgnoreCase("ftp") || connectionProperties.getType().equalsIgnoreCase("sftp"))
            	connectionString += "//";
            
            
            
            
            if(connectionProperties.getUsername().length() > 0 || connectionProperties.getPassword().length() >0){
            	if(connectionProperties.getUsername().length()>0)
            		connectionString += connectionProperties.getUsername();
            	if(connectionProperties.getPassword().length()>0)
            		connectionString += ":" + connectionProperties.getPassword();
            	connectionString += "@";
            }
            
            if(connectionProperties.getHost().length()>0)
            	connectionString += connectionProperties.getHost();
            
            if(connectionProperties.getPort()> 0 && !connectionProperties.getType().equals("file"))
            	connectionString += ":" + connectionProperties.getPort() + "/";
            
            if(connectionProperties.getPath().length() >  0)
            	connectionString += connectionProperties.getPath();
            
      
            if(connectionProperties.getType().equalsIgnoreCase("sftp")){
            	//need to add SFTP key location
            }
    		//FileObject basefile = this.manager.resolveFile(connectionString);
    		FileObject object = this.manager.resolveFile(connectionString);
    		this.manager.setBaseFile(object);
    		
    		
            /*ftpClient = new FTPClient(connectionProperties.getHost(),
                    connectionProperties.getPort(), fConnectionTimeout);
            
            ftpClient.setMessageListener(listener);

            // login
            ftpClient.login(connectionProperties.getUsername(),
                    connectionProperties.getPassword());

            ftpClient.setConnectMode(FTPConnectMode.PASV);

            
             * Spike:: Removed this because active mode hangs the client.
             * 
             * if (connectionProperties.getPassive()) {
             * ftpClient.setConnectMode(FTPConnectMode.PASV); } else {
             * ftpClient.setConnectMode(FTPConnectMode.ACTIVE); }
             

            ftpClient.setType(FTPTransferType.ASCII);*/

            AlertUtils.showStatusMessage("Connected to: " + this.manager.getBaseFile().getName().getFriendlyURI(), viewPart);

        } catch (Exception e) {
            if (!connectFailed) {
               AlertUtils.showStatusErrorMessage("Connect failed.",viewPart);
               AlertUtils.alertUser(e);
            }
            connectFailed = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cfeclipse.cfml.views.explorer.IFileProvider#getRoots()
     */
    public Object[] getRoots() {

        if (isConnected()) {
        	FileSystemRoot root = new FileSystemRoot(connectionProperties.getPath());
        	root.setPath(connectionProperties.getPath());
        	root.setType(connectionProperties.getType());
        	try {
				root.setFileObject(manager.getBaseFile());
			} catch (FileSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return new FileSystemRoot[] { root };
        } else if (connectFailed) {
            return new String[] { CONNECT_FAILED };
        } else {
            return new String[] { "Not connected" };
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cfeclipse.cfml.views.explorer.IFileProvider#getChildren(java.io.File,
     *      java.io.FileFilter)
     */
    public Object[] getChildren(String parent, FileNameFilter filter) {

        try {
            connect(this.connectionProperties);

            if (connectFailed) {
                return new String[0];
            }
            FileObject initialItem =null;
            if(parent.length() == 0 || parent.equalsIgnoreCase("<empty selection>")){
            	initialItem =  this.manager.getBaseFile();
            }
            else{
            	initialItem = this.manager.getBaseFile().resolveFile(parent); //basefile.getChildren();
            }
            
           //Must check if this is a folder!
    		if(initialItem.getType().equals(FileType.FILE)){
    		//	initialItem =  this.manager.getBaseFile();
    		}
            FileObject[] files = initialItem.getChildren(); //.dirDetails(parent);

            if (files == null) {
                files = new FileObject[0];
            }
            
      
            
            /*// Check if we've got back the directory itself.
            if (files.length == 1 && parent.endsWith("/" + files[0].getName())) {
            	
                FTPFile[] test = ftpClient.dirDetails(parent + "/"
                        + files[0].getName());
                if (test == null || test.length == 0) {
                    files = new FTPFile[0];
                }
            }*/
            
            
            
            ArrayList filteredFileList = new ArrayList();
            for (int i = 0; i < files.length; i++) {
               if (filter.accept(files[i])) {
            	   RemoteFile file = new RemoteFile(files[i], files[i].getURL().toString());
                   filteredFileList.add(file);
                }
            }

            Object[] filteredFiles = filteredFileList.toArray();

            return filteredFiles;
        }

        catch (Exception e) {
        	AlertUtils.alertUser(e);
        }
        return new String[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cfeclipse.cfml.views.explorer.IFileProvider#dispose()
     */
    public void dispose() {
        disconnect();
    }

    private boolean isConnected() {
        if (manager == null) {
            return false;
        }
        try {
        	//manager.
        	//ftpClient.quote("NOOP", new String[] { "200" });
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public IEditorInput getEditorInput(String filename) {
        try {
            connect();
            if (connectFailed) {
                return null;
            }
           
            FileObject selFile = this.manager.getBaseFile().resolveFile(filename);
           // FTPFile[] files = ftpClient.dirDetails(filename);
            RemoteFile remoteFile = new RemoteFile(selFile, filename);
               RemoteFileEditorInput input = new RemoteFileEditorInput(remoteFile);
            return input;
        } catch (Exception e) {
            AlertUtils.alertUser(e);
            return null;
        }
    }
    public IEditorInput getEditorInput(RemoteFile remFile) {
    	 RemoteFileEditorInput input = new RemoteFileEditorInput(remFile);
    	
    	return input;
	}
  /*  public void addLogListener(FTPMessageListener listener) {
        FTPConnection.listener.addListener(listener);
    		//this.listener.addListener(listener);
    }

    public void removeLogListener(FTPMessageListener listener) {
    		FTPConnection.listener.removeListener(listener);
        //this.listener.removeListener(listener);
    }

    public String getLog() {
        if (listener == null) {
            return null;
        }
        return listener.getLog();
    }*/

    public String toString() {
        return connectionProperties.getConnectionid();
    }

	
}
