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

import com.rohanclan.cfml.ftp.RemoteFile;

import java.util.regex.*;

import com.rohanclan.cfml.util.CFPluginImages;

class FileLabelProvider extends LabelProvider implements ITableLabelProvider {
    
    Pattern p = Pattern.compile("(.)+(htm|html|cfc|cfm|cfml)$");
    
    public String getColumnText(Object element, int columnIndex) {
        
        if (element instanceof RemoteFile) {
            return ((RemoteFile)element).getName();
        }
        
        String[] fullpath = element.toString().split("[\\\\/]");
        return fullpath[fullpath.length-1];
    }
    
    
    
    public Image getColumnImage(Object element, int columnIndex) {
        String filename = element.toString();
        if (element instanceof RemoteFile) {
            filename = ((RemoteFile)element).getName();
        }
        Matcher m = p.matcher(filename);
        if (m.matches()) {
            return CFPluginImages.get(CFPluginImages.ICON_DEFAULT);
        }
        else {
            return CFPluginImages.get(CFPluginImages.ICON_DOCUMENT);
        }
    }
}