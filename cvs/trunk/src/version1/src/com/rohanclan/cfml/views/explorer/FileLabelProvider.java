/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

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
	            return addPermissionIcon(element,CFPluginImages.get(CFPluginImages.ICON_DEFAULT));
	        }
	        else {
	            return addPermissionIcon(element,CFPluginImages.get(CFPluginImages.ICON_DOCUMENT));
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
            return addOverlay(image,redPixel);
        }
        else if (!canWrite) {
            return addOverlay(image,greenPixel);
        }
        else {
            return image;
        }
    }
    
    private Image addOverlay(Image image,int color) {
        ImageData fullImageData = image.getImageData();
        ImageData transparency = fullImageData.getTransparencyMask();
        
        int width = fullImageData.width;
        int height = fullImageData.height;
        for (int i=width-1;i>=0;i--) {
            int pixelColor = fullImageData.getPixel(i,8);
            int transColor = transparency.getPixel(i,8);
            if (pixelColor != transColor) {
                width = i;
                break;
            }
        }
        for (int i = height-1;i>=0;i--) {
            int pixelColor = fullImageData.getPixel(8,i);
            int transColor = transparency.getPixel(8,i);
            if (pixelColor != transColor) {
                height = i;
                break;
            }
        }
        int minX = width-4;
        int maxX = width-1;
        int minY = height-4;
        int maxY = height-1;
        for (int i=minX;i<=maxX;i++) {
            for (int j=minY;j<=maxY;j++) {
                boolean repaint = true;
                if (i == minX 
                        || i == maxX) {
                    if (j == minY) {
                        repaint = false;
                    } else if (j == maxY) {
                        repaint = false;
                    }
                }
                
                if (repaint) {
                    fullImageData.setPixel(i,j,color);
                }
            }
        }
        Image fullImage = new Image(Display.getCurrent(),fullImageData);
        return fullImage;
    }
    
}