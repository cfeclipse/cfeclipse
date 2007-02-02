/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;


class FileLabelProvider extends LabelProvider implements ITableLabelProvider {
    
    Pattern p = Pattern.compile("(.)+(htm|html|cfc|cfm|cfml)$");
    
    public String getColumnText(Object element, int columnIndex) {
        	String nameReturn = "";
        	Object[] item = (Object[])element;
        	
    		if(columnIndex == 0){
    			String[] fullpath = item[columnIndex].toString().split("[\\\\/]");
    			nameReturn = fullpath[fullpath.length-1];
    			 if (item[columnIndex] instanceof RemoteFile) {
    				 nameReturn = ((RemoteFile)item[columnIndex]).getName();
    		       }
    		}	
    		else {
    			nameReturn = item[columnIndex].toString();
    		}
	        
	        return nameReturn;
    }
    
    
    
    public Image getColumnImage(Object element, int columnIndex) {
    	Object[] item = (Object[])element;
    		
    		if(columnIndex == 1){
    			return null;
    		}
    		
	        String filename = item[columnIndex].toString();
	        
	        if (item[columnIndex] instanceof RemoteFile) {
	            filename = ((RemoteFile)item[columnIndex]).getName();
	        }
	        Matcher m = p.matcher(filename);
	        if (m.matches()) {
	        	String extension = filename.substring(filename.length()-3, filename.length());
	        	String icon_default = CFPluginImages.ICON_DEFAULT;
	        	if(extension.equalsIgnoreCase("cfc")){
	        		icon_default = CFPluginImages.ICON_CFC;
	        	}
	            return addPermissionIcon(item[columnIndex],CFPluginImages.get(icon_default)); //cfm icon
	        }
	        else {
	            return addPermissionIcon(item[columnIndex],CFPluginImages.get(CFPluginImages.ICON_DOCUMENT));
	        }
        
    }
    
    private Image addPermissionIcon(Object element, Image image) {
        int redPixel = image.getImageData().palette.getPixel(new RGB(255,0,0));
        int greenPixel = image.getImageData().palette.getPixel(new RGB(0,255,0));
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