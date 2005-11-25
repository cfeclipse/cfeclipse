package com.rohanclan.cfml.views.dictionary;

import java.util.HashMap;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.packageview.FolderTypes;

public class ViewLabelProvider extends LabelProvider {

	private HashMap folderIcons = new HashMap();

	/**
	 * 
	 */
	public ViewLabelProvider() {
		super();
		this.folderIcons.put(FolderTypes.WWWROOT,
				CFPluginImages.ICON_PVIEW_FOLDER_WWW);
		this.folderIcons.put(FolderTypes.CFCROOT,
				CFPluginImages.ICON_PVIEW_FOLDER_CFC);
		this.folderIcons.put(FolderTypes.CF_ROOT,
				CFPluginImages.ICON_PVIEW_FOLDER_CUS);
	}

	public String getText(Object obj) {
		return obj.toString();
	}

	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;

		if (obj instanceof FunctionItem) {

			imageKey = CFPluginImages.ICON_FUNC;
			return CFPluginImages.get(imageKey);
		} else if (obj instanceof TagItem) {
			imageKey = CFPluginImages.ICON_TAG;
			return CFPluginImages.get(imageKey);

		} else if (obj instanceof ScopeItem) {
			imageKey = CFPluginImages.ICON_SERVER;
			return CFPluginImages.get(imageKey);

		} else if (obj instanceof TreeParent) {
			imageKey = ISharedImages.IMG_OBJ_FOLDER;

		} else if (obj instanceof TreeCustomTag) {
			imageKey = CFPluginImages.ICON_PVIEW_FOLDER_CUS;
			return CFPluginImages.get(imageKey);
		}

		return PlatformUI.getWorkbench().getSharedImages().getImage(
				imageKey);
	}
}


