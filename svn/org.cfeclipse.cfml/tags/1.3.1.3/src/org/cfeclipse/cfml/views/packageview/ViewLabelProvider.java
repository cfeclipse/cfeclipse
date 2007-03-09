/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.packageview;

import java.util.HashMap;

import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.packageview.objects.ArgumentNode;
import org.cfeclipse.cfml.views.packageview.objects.ComponentNode;
import org.cfeclipse.cfml.views.packageview.objects.FunctionNode;
import org.cfeclipse.cfml.views.packageview.objects.PackageNode;
import org.cfeclipse.cfml.views.packageview.objects.ProjectNode;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;



/**
 * The label provider for the cfc package node
 * 
 * @author markd
 * @author mike nimer
 *
 */
public class ViewLabelProvider extends LabelProvider {
	private HashMap folderIcons = new HashMap();
	
	/**
	 * 
	 */
	public ViewLabelProvider() 
    {
		super();
		//this.folderIcons.put(FolderTypes.WWWROOT, CFPluginImages.ICON_PVIEW_FOLDER_WWW);
		//this.folderIcons.put(FolderTypes.CFCROOT, CFPluginImages.ICON_PVIEW_FOLDER_CFC);
		//this.folderIcons.put(FolderTypes.CF_ROOT, CFPluginImages.ICON_PVIEW_FOLDER_CUS);
	}
	public String getText(Object obj) 
    {
		return obj.toString();
	}
	public Image getImage(Object obj) 
    {
	
        String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
        
		if (obj instanceof ProjectNode)
        {
			return CFPluginImages.get( ((ProjectNode)obj).getImage());
		}
		if (obj instanceof PackageNode)
		{
            return CFPluginImages.get( ((PackageNode)obj).getImage());
		}
		if (obj instanceof ComponentNode)
		{
		    return CFPluginImages.get( ((ComponentNode)obj).getImage());
		}
		if (obj instanceof FunctionNode)
		{
		    return CFPluginImages.get( ((FunctionNode)obj).getImage());
		}
		if (obj instanceof ArgumentNode)
		{
            return CFPluginImages.get( ((ArgumentNode)obj).getImage());
		}
       
		
        
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
    
    
}