/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.io.File;

import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;



class DirectoryLabelProvider extends LabelProvider {
    
    
    public String getText(Object element) {

        try {
            if (element instanceof RemoteFile) {
                return ((RemoteFile)element).getName();
            }
            if (element instanceof FileSystemRoot) {
            	return element.toString(); 
            }
	        if (element.toString().length() == 1) {
	        	return element.toString();
	        }
	        else {
	        	String[] fullpath = element.toString().split("[\\\\/]");
	        	return fullpath[fullpath.length-1];
	        }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    
    public Image getImage(Object element) {
        try {
            if (element instanceof RemoteFile) {
                return addPermissionIcon(element,CFPluginImages.get(CFPluginImages.ICON_FOLDER));
            }
            if (element instanceof FileSystemRoot) {
            	FileSystemRoot root = (FileSystemRoot)element;
            	if(root.getType().equalsIgnoreCase("file")){
            		return CFPluginImages.get(CFPluginImages.ICON_DRIVE);
            	}
            	else if(root.getType().equalsIgnoreCase("ftp")){
            		return CFPluginImages.get(CFPluginImages.ICON_DRIVE_FTP);
            	}
            	else if(root.getType().equalsIgnoreCase("sftp")){
            		return CFPluginImages.get(CFPluginImages.ICON_DRIVE_SFTP);
            	}
            	else{
            		return CFPluginImages.get(CFPluginImages.ICON_REPOSITORY);
            	}
            }
	        String[] fullpath = element.toString().split("[\\\\/]");
	        if (fullpath.length > 1) {
	            return CFPluginImages.get(CFPluginImages.ICON_FOLDER);
	        }
	        return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    

    private Image addPermissionIcon(Object element, Image image) {

        boolean canRead = true;
        boolean canWrite = true;
        if (element instanceof RemoteFile) {
            RemoteFile file = (RemoteFile)element;
            canRead = file.canRead();
            canWrite = file.canWrite();
        }
        else if (element instanceof File) {
            File file = (File)element;
            canRead = file.canRead();
            canWrite = file.canWrite();
        }
        else {
            return image;
        }
        
        if (!canRead) {
            return CFPluginImages.addOverlay(image,new RGB(255,0,0));
        }
        else if (!canWrite) {
            return CFPluginImages.addOverlay(image,new RGB(0,255,0));
        }
        else {
            return image;
        }
    }
    
    
    
}