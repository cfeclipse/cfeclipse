/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;

import com.rohanclan.cfml.util.CFPluginImages;
import org.tigris.cfeclipse.machII.views.model.TreeParent;


class ViewLabelProvider extends LabelProvider {
	private HashMap folderIcons = new HashMap();
	private HashMap fileIcons = new HashMap();
	/**
	 * 
	 */
	public ViewLabelProvider() {
		super();
		//fusebox icons

	}
	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		
		if(obj instanceof TreeParent)
			imageKey = ISharedImages.IMG_OBJ_FILE;
		
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}