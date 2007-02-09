/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.frameworks.fusebox.views.project;

import java.util.HashMap;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;


class ViewLabelProvider extends LabelProvider {
	private HashMap folderIcons = new HashMap();
	private HashMap fileIcons = new HashMap();
	/**
	 * 
	 */
	public ViewLabelProvider() {
		super();
		this.folderIcons.put(FolderTypes.WWWROOT, PluginImages.ICON_PVIEW_FOLDER_WWW);
		this.folderIcons.put(FolderTypes.CFCROOT, PluginImages.ICON_PVIEW_FOLDER_CFC);
		this.folderIcons.put(FolderTypes.CF_ROOT, PluginImages.ICON_PVIEW_FOLDER_CUS);
		//fusebox icons
		this.fileIcons.put("fbx_", PluginImages.ICON_FUSEBOX);
		this.fileIcons.put("fbx_circuits.cfm", PluginImages.ICON_FBX_CIRCUIT);
		this.fileIcons.put("fbx_settings.cfm", PluginImages.ICON_FBX_SETTINGS);
		this.fileIcons.put("fbx_switch.cfm", PluginImages.ICON_FBX_SWITCH);
			 	
		this.fileIcons.put("act_", PluginImages.ICON_FBX_ACT);
		this.fileIcons.put("dsp_", PluginImages.ICON_FBX_DSP);
		this.fileIcons.put("qry_", PluginImages.ICON_FBX_QRY);
		this.fileIcons.put("frm_", PluginImages.ICON_FBX_FRM);
		this.fileIcons.put("lay_", PluginImages.ICON_FBX_LAY);
		this.fileIcons.put("url_", PluginImages.ICON_FBX_URL);
		this.fileIcons.put("udf_", PluginImages.ICON_FBX_UDF);
		this.fileIcons.put("mod_", PluginImages.ICON_FBX_MOD);
		

	}
	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		ImageDescriptor descriptor = null;
		
		
		
		if(obj instanceof ProjectNode) {
			if(((ProjectNode)obj).getProject().isOpen()) {
				imageKey = org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT;
			}
			else
				imageKey = org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED;
		}
		else if(obj instanceof FolderNode) {
			FolderNode fNode = (FolderNode)obj;
			
			if(this.folderIcons.containsKey(((FolderNode)obj).getFolderType())) {
				return PluginImages.get((String)this.folderIcons.get(((FolderNode)obj).getFolderType()));
			}

			imageKey = ISharedImages.IMG_OBJ_FOLDER;
		}
		else if(obj instanceof FileNode) {
			ImageDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(((FileNode)obj).getName());
			String filename = getText(obj);
			String fbxtype = filename.substring(0,4);
//			selectMethod.setImageDescriptor();
			
			
			
			
			/*System.out.println("The file is: " + filename + ":" + fbxtype);
			
			if(filename.equalsIgnoreCase("fbx_circuits.cfm")){
			    System.out.println("Setting the icon for " + filename.toLowerCase());
			    return CFPluginImages.get((String)this.fileIcons.get(filename.toLowerCase()));
			}
			if(filename.equalsIgnoreCase("fbx_settings.cfm")){
			    System.out.println("Setting the icon for " + filename.toLowerCase());
			    return CFPluginImages.get((String)this.fileIcons.get(filename.toLowerCase()));
			}
			if(filename.equalsIgnoreCase("fbx_switch.cfm")){
			    System.out.println("Setting the icon for " + filename.toLowerCase());
			    return CFPluginImages.get((String)this.fileIcons.get(filename.toLowerCase()));
			}
			else */
			if(this.fileIcons.containsKey(fbxtype)){
				String fileIcon = (String)this.fileIcons.get(fbxtype);
				Image img = PluginImages.get((String)this.fileIcons.get(fbxtype.toLowerCase()));
				return img;
			}
			else{
			    System.out.println("Setting the default image");
			    return desc.createImage();
			}
			
		}
		
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}