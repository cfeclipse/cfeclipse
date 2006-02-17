/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.rohanclan.cfml.parser.docitems.TagItem;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.packageview.objects.*;



class ViewLabelProvider extends LabelProvider {
	private HashMap folderIcons = new HashMap();
	
	/**
	 * 
	 */
	public ViewLabelProvider() {
		super();
		this.folderIcons.put(FolderTypes.WWWROOT, CFPluginImages.ICON_PVIEW_FOLDER_WWW);
		this.folderIcons.put(FolderTypes.CFCROOT, CFPluginImages.ICON_PVIEW_FOLDER_CFC);
		this.folderIcons.put(FolderTypes.CF_ROOT, CFPluginImages.ICON_PVIEW_FOLDER_CUS);
	}
	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof IComponentViewObject){
			imageKey = ((IComponentViewObject)obj).getImage();
			return CFPluginImages.get(imageKey);
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}