/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.net.ftp;

import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;

import com.enterprisedt.net.ftp.*;
import com.rohanclan.cfml.views.explorer.IFileProvider;
import com.rohanclan.cfml.views.explorer.FileNameFilter;
import com.rohanclan.cfml.views.explorer.FileSystemRoot;
import com.rohanclan.cfml.net.FTPConnectionProperties;
import com.rohanclan.cfml.net.RemoteFile;
import com.rohanclan.cfml.net.RemoteFileEditorInput;
import com.rohanclan.cfml.util.AlertUtils;


/**
 * @author spike
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FTPConnection implements IFileProvider {


    protected static FTPClient ftpClient = null;

    protected FTPConnectionProperties connectionProperties;

    protected static FTPConnection instance = null;

    protected static LogListener listener = new LogListener();

    protected IViewPart viewPart = null;

    protected int fConnectionTimeout = 15000;

    protected boolean connectFailed = false;
    
    
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
            ftpClient = null;
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

            byte[] contents = ftpClient.get(filepath);
            ByteArrayInputStream ins = new ByteArrayInputStream(contents);
            BufferedInputStream bis = new BufferedInputStream(ins);
            return bis;
        } catch (Exception e) {
            AlertUtils.alertUser(e);
            return null;
        }
    }

    public void saveFile(byte[] content, String remotefile) {
        connect();

        try {
            ftpClient.put(content, remotefile);
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }

    public void disconnect() {
        try {
            if (isConnected()) {
                ftpClient.quit();
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
        if (isConnected()) {
            return;
        }
        try {
            
            AlertUtils.showStatusErrorMessage(null,viewPart);

            ftpClient = new FTPClient(connectionProperties.getHost(),
                    connectionProperties.getPort(), fConnectionTimeout);
            
            ftpClient.setMessageListener(listener);

            // login
            ftpClient.login(connectionProperties.getUsername(),
                    connectionProperties.getPassword());

            ftpClient.setConnectMode(FTPConnectMode.PASV);

            /*
             * Spike:: Removed this because active mode hangs the client.
             * 
             * if (connectionProperties.getPassive()) {
             * ftpClient.setConnectMode(FTPConnectMode.PASV); } else {
             * ftpClient.setConnectMode(FTPConnectMode.ACTIVE); }
             */

            ftpClient.setType(FTPTransferType.ASCII);
            

            AlertUtils.showStatusMessage("Connected to: " + connectionProperties.getHost(), viewPart);

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
     * @see com.rohanclan.cfml.views.explorer.IFileProvider#getRoots()
     */
    public Object[] getRoots() {

        if (isConnected()) {
        	FileSystemRoot root = new FileSystemRoot(connectionProperties.getPath());
        	root.setPath(connectionProperties.getPath());
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
     * @see com.rohanclan.cfml.views.explorer.IFileProvider#getChildren(java.io.File,
     *      java.io.FileFilter)
     */
    public Object[] getChildren(String parent, FileNameFilter filter) {

        try {
            connect();

            if (connectFailed) {
                return new String[0];
            }

            FTPFile[] files = ftpClient.dirDetails(parent);

            if (files == null) {
                files = new FTPFile[0];
            }

            // Check if we've got back the directory itself.
            if (files.length == 1 && parent.endsWith("/" + files[0].getName())) {

                FTPFile[] test = ftpClient.dirDetails(parent + "/"
                        + files[0].getName());
                if (test == null || test.length == 0) {
                    files = new FTPFile[0];
                }
            }

            ArrayList filteredFileList = new ArrayList();
            for (int i = 0; i < files.length; i++) {
                if (filter.accept(files[i])) {
                    RemoteFile file = new RemoteFile(files[i], parent + "/"
                            + files[i].getName());
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
     * @see com.rohanclan.cfml.views.explorer.IFileProvider#dispose()
     */
    public void dispose() {
        disconnect();
    }

    private boolean isConnected() {
        if (ftpClient == null) {
            return false;
        }
        try {
            ftpClient.quote("NOOP", new String[] { "200" });
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
            FTPFile[] files = ftpClient.dirDetails(filename);
            RemoteFile remoteFile = new RemoteFile(files[0], filename);
            RemoteFileEditorInput input = new RemoteFileEditorInput(remoteFile);
            return input;
        } catch (Exception e) {
            AlertUtils.alertUser(e);
            return null;
        }
    }

    public void addLogListener(FTPMessageListener listener) {
        this.listener.addListener(listener);
    }

    public void removeLogListener(FTPMessageListener listener) {
        this.listener.removeListener(listener);
    }

    public String getLog() {
        if (listener == null) {
            return null;
        }
        return listener.getLog();
    }

    public String toString() {
        return connectionProperties.getConnectionid();
    }
}
