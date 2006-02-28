/*
 * Created on 23-Dec-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.views;


import org.cfeclipse.frameworks.fusebox4.objects.*;
import org.cfeclipse.frameworks.fusebox4.util.PluginImages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;


public class ViewLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		
		
		
		if(obj instanceof IFBXObject){
			IFBXObject fobj = (IFBXObject)obj;
			imageKey = fobj.getIcon();
			
		}
		return PluginImages.get(imageKey);
	
		//return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}