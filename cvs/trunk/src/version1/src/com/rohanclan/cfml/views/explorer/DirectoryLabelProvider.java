/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rohanclan.cfml.util.CFPluginImages;


class DirectoryLabelProvider extends LabelProvider {
    
    
    public String getText(Object element) {
        
        String[] fullpath = element.toString().split("[\\\\/]");
        return fullpath[fullpath.length-1];
    }
    
    
    public Image getImage(Object element) {
        
        String[] fullpath = element.toString().split("[\\\\/]");
        if (fullpath.length > 1) {
            return CFPluginImages.get(CFPluginImages.ICON_FOLDER);
        }
        else {
            return CFPluginImages.get(CFPluginImages.ICON_REPOSITORY);
        }
        
    }
}