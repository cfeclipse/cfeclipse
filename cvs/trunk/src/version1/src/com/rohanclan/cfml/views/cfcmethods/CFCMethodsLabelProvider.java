/*
 * Created on May 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.cfcmethods;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import com.rohanclan.cfml.util.CFPluginImages;

/**
 * @author Stephen Milligan
 *
 */
public class CFCMethodsLabelProvider  extends LabelProvider implements ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
	    CFCMethodViewItem item = (CFCMethodViewItem)obj;
		return item.toString();
	}
	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}
	public Image getImage(Object obj) {
	    try {
		    CFCMethodViewItem item = (CFCMethodViewItem)obj;
		    
		    if (item.getAccess().toLowerCase().equals("remote")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_REMOTE);
		    } else if (item.getAccess().toLowerCase().equals("public")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PUBLIC);
		    } else if (item.getAccess().toLowerCase().equals("package")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PACKAGE);
		    } else if (item.getAccess().toLowerCase().equals("private")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PRIVATE);
		    }
		    return null;
	    }
	    catch (Exception e) {
	        //e.printStackTrace();
	        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PUBLIC);
	    }

	}
}