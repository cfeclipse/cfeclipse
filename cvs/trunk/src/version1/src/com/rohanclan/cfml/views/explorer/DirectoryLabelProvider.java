/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rohanclan.cfml.util.CFPluginImages;

import com.rohanclan.cfml.ftp.RemoteFile;

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
                return CFPluginImages.get(CFPluginImages.ICON_FOLDER);
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
}