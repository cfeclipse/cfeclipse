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
import java.net.NoRouteToHostException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.vfs.CacheStrategy;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.operations.FileOperationProvider;
import org.apache.commons.vfs.provider.ftp.FtpFileProvider;
import org.apache.commons.vfs.provider.sftp.SftpFileProvider;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.net.RemoteFileEditorInput;
import org.cfeclipse.cfml.util.AlertUtils;
import org.cfeclipse.cfml.views.explorer.FileNameFilter;
import org.cfeclipse.cfml.views.explorer.FileSystemRoot;
import org.cfeclipse.cfml.views.explorer.IFileProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;





/**
 * @author spike
 */
public class FTPConnection implements IFileProvider {

    protected FTPConnectionProperties connectionProperties;

    protected static FTPConnection instance = null;

    protected static LogListener listener = new LogListener();

    protected IViewPart viewPart = null;

    protected int fConnectionTimeout = 15000;

    protected boolean connectFailed = false;
    
    protected static DefaultFileSystemManager manager = null;
    
    public static String CONNECT_FAILED = "Could not connect to server.";

	private String connectionString;

	private FileSystemOptions fileSystemOptions;
    
    public boolean connectFailed() {
        return connectFailed;
    }

    /**
     *  
     */
    public FTPConnection() {
    	
    }

    public static FTPConnection getInstance() {
        return instance;
    }
    
    public void setConnectionProperties(Object props) {
        if (props instanceof FTPConnectionProperties) {
        	if (manager != null) {
        		disconnect();
        	}
            this.connectionProperties = (FTPConnectionProperties)props;
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
    	System.out.println("FTPConnection.disconnect()");
        try {
        	// Close and nullify the manager object
        	if (manager != null) {
	        	FTPConnection.manager.close();
        	}
        } catch (Exception e) {
            AlertUtils.alertUser(e);
        }
    }

    public void connect(FTPConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;

        connect();
    }

    public void connect() {
    	/*
    	Job job = new Job("Loading File Viewer") {
			protected IStatus run(IProgressMonitor monitor) {

	        	return Status.OK_STATUS;
			}
		};

		job.setPriority(Job.SHORT);
		job.schedule(); // start as soon as possible
		*/
	        try {
	        	
	        	if (isConnected()) {
	        		return;
	        	}
	        	
	            /*
	             * Set caching strategy so cache is refreshed upon all calls
	             * on a FileObject
	            	((DefaultFileSystemManager) VFS.getManager()).setCacheStrategy(CacheStrategy.ON_CALL);
	             */
	
	            // Set the manager
	        	if (manager == null) {
	        		manager = (DefaultFileSystemManager) VFS.getManager();
	        	}
	
	            // Store the connection type
	            String connectionType = connectionProperties.getType().toLowerCase();
	
	            // Start building the connection string
	            connectionString = connectionType + ":";
	
				if(connectionType.equals("ftp") || connectionType.equals("sftp")) {
	            	connectionString += "//";
				}
	
				// Check if we need to add username and password
	            if(connectionProperties.getUsername().length() > 0 || connectionProperties.getPassword().length() > 0) {
	            	// Add username
	            	if(connectionProperties.getUsername().length() > 0) {
	            		connectionString += URLEncoder.encode(connectionProperties.getUsername());
	            	}
	
	            	// Add password
	            	if(connectionProperties.getPassword().length() > 0) {
	            		connectionString += ":" + URLEncoder.encode(connectionProperties.getPassword());
	            	}

	            	connectionString += "@";
	            }

	            // Add host
	            if(connectionProperties.getHost().length() > 0) {
	            	connectionString += connectionProperties.getHost();
	            }
	            
	            // Add the port if it was given and the connection type is not file
	            if(connectionProperties.getPort() > 0 && !connectionType.equals("file")) {
	            	connectionString += ":" + connectionProperties.getPort();
	            }
	
	            // Add the path to request if one was given
	            if(connectionProperties.getPath().length() > 0) {
	            	if (connectionProperties.getPath().charAt(0) != '/') {
	            		connectionString += "/";
	            	}
	            	connectionString += connectionProperties.getPath();
	            }

	            // Build a new file system options object
	            this.fileSystemOptions = new FileSystemOptions();
				SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
				SftpFileSystemConfigBuilder.getInstance().setTimeout(fileSystemOptions, new Integer(fConnectionTimeout));
				System.out.println("Connecting...Resolving Base File " + connectionString);
				//manager.init();
	
				FileObject baseFile = manager.resolveFile(connectionString, fileSystemOptions);
				manager.setBaseFile(baseFile);

	        } catch (Exception e) {
	            if (! connectFailed) {
	               AlertUtils.showStatusErrorMessage("Connect failed.",viewPart);
	               AlertUtils.alertUser(e);
	            }
	            connectFailed = true;
	        }

	        
	        
	        /*
//          TODO: Make this a job... 
        	final Job connectionJob = new Job("connecting...") {

				protected IStatus run(IProgressMonitor monitor) {
					try {
					//need to add SFTP key location
							
						object = VFS.getManager().resolveFile(connectionString, fileSystemOptions);
					   
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
			           
					}
					catch (FileSystemException e) {
						// TODO Auto-generated catch block
						AlertUtils.alertUser(e);
					}

					return Status.OK_STATUS;
				}
        		
        	};

        	connectionJob.addJobChangeListener(
    			new JobChangeAdapter() {
    				public void done(IJobChangeEvent event) {
    					if (event.getResult().isOK()) {
    						// postMessage("Job completed successfully");
    					}
    					else {
    						// postError("Job did not complete successfully");
    					}
    				}
    			}
        	);

        	// connectionJob.setSystem(true);
        	connectionJob.schedule(); // start as soon as possible
*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cfeclipse.cfml.views.explorer.IFileProvider#getRoots()
     */
    public Object[] getRoots() {
    	System.out.println("FTPConnection.getRoots()");

        if (isConnected()) {
        	System.out.println("connected");
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
        }
        else if (connectFailed) {
        	System.out.println("failoed");
            return new String[] { CONNECT_FAILED };
        }
        else {
        	System.out.println("not connected");
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
    	System.out.println("FTPConnection.getChildren()");
    	connect();

        try {
            FileObject initialItem = FTPConnection.manager.getBaseFile();

            // If a parent path was passed resolve that path
            if (parent.length() > 0 && ! parent.equalsIgnoreCase("<empty selection>")) {
            	initialItem = FTPConnection.manager.getBaseFile().resolveFile(parent);
            }

            // Get the children
            System.out.println("get chiln");
            
            FileObject[] files = initialItem.getChildren();
            System.out.println("got chiln");

            // If no children were returned, return an empty array
            if (files == null) {
                return new FileObject[0];
            }
            else {
            	/*
            	 * Children were returned.
            	 * Filter files according to the passed filter and return the
            	 * resulting array.
            	 */
	            ArrayList<RemoteFile> filteredFileList = new ArrayList<RemoteFile>();
	            for (int i = 0; i < files.length; i++) {
	               if (filter.accept(files[i])) {
	            	   RemoteFile file = new RemoteFile(files[i], files[i].getURL().toString());
	                   filteredFileList.add(file);
	                }
	            }

	            return filteredFileList.toArray();
            }
        }
        catch (FileSystemException e) {
        	/*
        	 * If a file system exception was thrown, it is likely there was 
        	 * a connection issue so alert the user accordingly.
        	 */
        	AlertUtils.alertUser("Could not connect to server. :" + e.getMessage());
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
    	return (manager == null) ? false : true;
    }

    public IEditorInput getEditorInput(String filename) {
        try {
            connect();
            if (connectFailed) {
                return null;
            }

            FileObject selFile = FTPConnection.manager.getBaseFile().resolveFile(filename);
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

/*
	public void addLogListener(FTPMessageListener listener) {
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
    }
*/

    public String toString() {
        return connectionProperties.getConnectionid();
    }

	
}
