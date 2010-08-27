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
import java.util.Map;

import org.apache.commons.vfs.CacheStrategy;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.operations.FileOperationProvider;
import org.apache.commons.vfs.operations.FileOperations;
import org.apache.commons.vfs.provider.ftp.FtpFileProvider;
import org.apache.commons.vfs.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.sftp.SftpFileObject;
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

    protected boolean connectFailed = false;
    
    protected static FileSystemManager manager = null;
    
    public static String CONNECT_FAILED = "Could not connect to server.";

	private String connectionString;

	private FileSystemOptions fileSystemOptions;

    private FileObject fCloseFile= null; // used for cleanup in release()


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
    
    public boolean isConnectable() {
    	connect();
        if (connectFailed) {
            return false;
        }
        disconnect();
        return true;
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
        	    FileSystem fs = null;
        	    if(fCloseFile != null) {        	    	
        	    	fCloseFile.close(); // Seems to still work even if this line is omitted
        	    	fs = fCloseFile.getFileSystem(); // This works even after the src is closed.
        	    	manager.closeFileSystem(fs);
        	    }
        	    manager = null;
        	}
        	connectFailed = false;
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
	
	
	            // Store the connection type
	            String connectionType = connectionProperties.getType().toLowerCase();
	
	            // Start building the connection string
	            connectionString = connectionProperties.getURI();

	            // Set the manager
	        	if (manager == null) {
	        		manager = VFS.getManager();
	        	}
	            // Build a new file system options object
	            this.fileSystemOptions = new FileSystemOptions();
	            StaticUserAuthenticator auth = new StaticUserAuthenticator(null, connectionProperties.getUsername(), connectionProperties.getPassword());
	            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, auth);
	            if(connectionProperties.getType().equals("ftp")) {	            	
					//FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, connectionProperties.getPassive());
					FtpFileSystemConfigBuilder.getInstance().setDataTimeout(fileSystemOptions, connectionProperties.getTimeoutSeconds() * 10);
					FtpFileSystemConfigBuilder.getInstance().setSoTimeout(fileSystemOptions, connectionProperties.getTimeoutSeconds() * 1000);
					FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, connectionProperties.getUserDirIsRoot());
				} else if(connectionProperties.getType().equals("sftp")) {
					try {
						SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
						SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, connectionProperties.getUserDirIsRoot());
					} catch (FileSystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace(); 
					}
					SftpFileSystemConfigBuilder.getInstance().setTimeout(fileSystemOptions, connectionProperties.getTimeoutSeconds() * 10);
				}
				System.out.println("Connecting...Resolving Base File " + connectionString);
				//manager.init();
	
				FileObject baseFile = manager.resolveFile(connectionString, fileSystemOptions);
				// this is a real test, we get a false positive otherwise;
				try{					
					baseFile.getChildren();
					((DefaultFileSystemManager) manager).setBaseFile(baseFile);
		        } catch (Exception e) {
		            if (! connectFailed) {
		               AlertUtils.showStatusErrorMessage("Connect failed.",viewPart);
		               AlertUtils.alertUser(e);
		            }
		            disconnect();
		            connectFailed = true;
		        }

	        } catch (Exception e) {
	            if (! connectFailed) {
	               AlertUtils.showStatusErrorMessage("Connect failed.",viewPart);
	               AlertUtils.alertUser(e);
	            }
	            disconnect();
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
    	System.out.println("FTPConnection.getRoots():"+connectionProperties.getHost());

        if (isConnected()) {
        	System.out.println("connected");
        	FileSystemRoot root = new FileSystemRoot(connectionProperties.getPath());
        	root.setPath(connectionProperties.getPath());
        	root.setType(connectionProperties.getType());
        	try {
                FileObject rootItem = manager.resolveFile(connectionString,fileSystemOptions);
				root.setFileObject(rootItem);
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
    	System.out.println("FTPConnection.getChildren() : " + connectionString);
    	connect();

        try {
            FileObject initialItem = manager.getBaseFile();

            // If a parent path was passed resolve that path
            if (parent.length() > 0 && ! parent.equalsIgnoreCase("<empty selection>")) {
            	initialItem = manager.getBaseFile().resolveFile(parent);
            }

            // Get the children
            System.out.println("getting children...");            
            FileObject[] files = initialItem.getChildren();
            System.out.println("got children");

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

            FileObject selFile = FTPConnection.manager.resolveFile(filename);
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
