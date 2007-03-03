/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.vfs.FileName;
import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.net.ftp.FTPConnection;
import org.cfeclipse.cfml.util.AlertUtils;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.part.ViewPart;




class DirectoryContentProvider implements IStructuredContentProvider, ITreeContentProvider {
    

    private FileNameFilter directoryFilter = new FileNameFilter();
    private IFileProvider fileProvider = null;
    private Viewer viewer = null;
    private ViewPart viewpart = null;
    
    protected static LocalFileSystem localFS = new LocalFileSystem();
    
    public DirectoryContentProvider(ViewPart viewpart) {
        directoryFilter.allowFiles(false);
        this.viewpart = viewpart;
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.viewer = viewer;
        //System.out.println("Directory Input change notified " + newInput.getClass().getName());
        try {
	    	if (fileProvider != null) {
	    		fileProvider.dispose();
	            //System.out.println("File provider disposed.");
	    	}
	        if (newInput instanceof IFileProvider) {

	            // Clear any status errors
		        AlertUtils.showStatusErrorMessage(null,viewpart);
		        
	            fileProvider = (IFileProvider)newInput;
	            fileProvider.setViewPart(this.viewpart);

	            //System.out.println("Directory provider attempting to connect.");
	            fileProvider.connect();
	        }
	        else {
	        	
	            fileProvider = null;
	        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dispose() {
    	if (fileProvider != null) {
    		fileProvider.dispose();
    	}
    }
    
   
    
    public Object[] getElements(Object inputElement) {
        
        return getChildren(inputElement);
    }
    
    public Object[] getChildren(Object parentElement) {
        
    	ArrayList children = new ArrayList();
    	Object[] results = null;
    	System.out.println("getting children of " + parentElement);
    	try {
    		
	        if (fileProvider == null){
	            results = new Object[] {IFileProvider.INVALID_FILESYSTEM};
	        }
	        if (parentElement instanceof IFileProvider) {
	        	
	        	results =  fileProvider.getRoots();
	        	
	        } else if (parentElement instanceof RemoteFile) {
	        	
	        	//TODO: Fix this, its gettting the full URL, rather than the path
	            RemoteFile file = (RemoteFile)parentElement;
	            String name = file.getFileItem().getName().getPath();
	            results = fileProvider.getChildren(name,directoryFilter);
	            
	        } else if (parentElement instanceof FileSystemRoot) {
	            FileSystemRoot file = (FileSystemRoot)parentElement;
	            results =  fileProvider.getChildren(file.getPath(),directoryFilter);
	        } 
	        
	        else {
	        	
                results = fileProvider.getChildren(parentElement.toString(),directoryFilter);
	        }
	        
	        for (int i=0;i<results.length;i++) {
            	if (!results[i].toString().endsWith("/.")          		
            			&& !results[i].toString().endsWith("/..")
            			&& !results[i].toString().endsWith("\\.")  
            			&& !results[i].toString().endsWith("\\..")
					) {
            		children.add(results[i]);
            	}
            }
            return children.toArray();
	        
    	}
        catch (Exception e) {
        	e.printStackTrace();
            return new Object[] {"Error! " + e.getMessage()};
        }
        
    }
    
    
    
    public Object getParent(Object element) {
    	try {
    		// Check if we're currently looking at a local file system. If not then ignore for now.
	    	if (element instanceof File) {
	    		File currentFile = (File)element;
	    		
	    		// Check if the parent of this element is a system root.
	    		// If so, grab the static shared copy from the localFS so that the viewer recognizes it.
	    		if (currentFile.getParent().indexOf("\\") == currentFile.getParent().length()-1
	    				|| currentFile.getParent().length() == 1) {
	    			Object[] roots = localFS.getRoots();
	    			for (int i=0;i<roots.length;i++) {
	    				if (roots[i].toString().equals(currentFile.getParent())) {
	    					return roots[i];
	    				}
	    			}
	    			// This should never happen, but make sure we return null if it does.
	    			return null;
	    		}
	    		
	    		return new File(currentFile.getParent());
	    	}

	    	
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
        return null;
    }
    
    public boolean hasChildren(Object element) {
    	try {
    		if (element instanceof RemoteFile) {
    		   return ((RemoteFile)element).isDirectory() & ((RemoteFile)element).canRead();
    		}
    		else if (element instanceof File) {
    		    return ((File)element).isDirectory();
    		}
    		else if (element instanceof String
    		        	&& element.toString().equals(FTPConnection.CONNECT_FAILED)) {
    		        return false;
    		}
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
}