/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.rohanclan.cfml.net.RemoteFile;
import com.rohanclan.cfml.util.CFPluginImages;


class DirectoryLabelProvider extends LabelProvider {
    
    
    public String getText(Object element) {

        try {
            if (element instanceof RemoteFile) {
                return ((RemoteFile)element).getName();
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
	        String[] fullpath = element.toString().split("[\\\\/]");
	        if (fullpath.length > 1) {
	            return CFPluginImages.get(CFPluginImages.ICON_FOLDER);
	        }
	        else {
	            return CFPluginImages.get(CFPluginImages.ICON_REPOSITORY);
	        }
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