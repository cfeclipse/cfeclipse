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
		
		IFBXObject fobj = (IFBXObject)obj;
		
		if(obj instanceof IFBXObject){
			return PluginImages.get(fobj.getIcon());
		}
		
		/*if (obj instanceof FBXApplication){
				
				FBXApplication app  = (FBXApplication)obj;
				if(app.getVersion() == 4){
					imageKey = PluginImages.ICON_FUSEBOX4;
					if(app.hasError()){
						imageKey = PluginImages.ICON_ERR_FUSEBOX4;
					}
				} else {
					imageKey = PluginImages.ICON_FUSEBOX;
					if(app.hasError()){
						imageKey = PluginImages.ICON_ERR_FUSEBOX;
					}
				}

			   return PluginImages.get(imageKey);
			  
			}
		else if (obj instanceof FBXCircuit){
		  
		     FBXCircuit circ  = (FBXCircuit)obj;
		     if(circ.getVersion() == 4){
		     	 imageKey = PluginImages.ICON_FBX4_CIRCUIT;
				if(circ.hasError()){
					imageKey = PluginImages.ICON_ERR_FBX4_CIRCUIT;
				}
			} else {
				 imageKey = PluginImages.ICON_FBX_CIRCUIT;
				if(circ.hasError()){
					 imageKey = PluginImages.ICON_ERR_FBX_CIRCUIT;
				}
			}
		   return PluginImages.get(imageKey);
		  
		}
		else if (obj instanceof FBXFuseAction){
			FBXFuseAction fa = (FBXFuseAction)obj;
				 if(fa.getVersion() == 4){
			     	 imageKey = PluginImages.ICON_FBX4_FUSEACTION;
					
				} else {
					 imageKey = PluginImages.ICON_FBX_FUSEACTION;
					
				}
			   
			   return PluginImages.get(imageKey);
			  
		}
		else if (obj instanceof FBXxfa){
			   imageKey = PluginImages.ICON_FBX_XFA;
			   return PluginImages.get(imageKey);
			  
		}
		else if (obj instanceof XFAFolder){
			   imageKey = PluginImages.ICON_FBX_XFA;
			   return PluginImages.get(imageKey);
		}
		else if (obj instanceof FBXDo){
			imageKey = PluginImages.ICON_FBX_DO;
			return PluginImages.get(imageKey);
		}
		else if (obj instanceof FBXFuse){
			//Here we check what it is called getTemplate or something of the sort and we parse the name
			FBXFuse fuse = (FBXFuse)obj;
			String fusetype = fuse.getFusetype().toLowerCase();
			
			if(fuse.getVersion() == 4){
				if(fusetype.equals("dsp")){
					 imageKey = PluginImages.ICON_FBX4_DSP;
				}
				else if(fusetype.equals("act")){
					 imageKey = PluginImages.ICON_FBX4_ACT;
				}
				else if(fusetype.equals("qry")){
					 imageKey = PluginImages.ICON_FBX4_QRY;
				}
				else if(fusetype.equals("mod")){
					 imageKey = PluginImages.ICON_FBX4_MOD;
				}
				else if(fusetype.equals("url")){
					 imageKey = PluginImages.ICON_FBX4_URL;
				}
				else if(fusetype.equals("udf")){
					 imageKey = PluginImages.ICON_FBX4_UDF;
				}
				else if(fusetype.equals("lay")){
					 imageKey = PluginImages.ICON_FBX4_LAY;
				}
				else if(fusetype.equals("frm")){
					 imageKey = PluginImages.ICON_FBX4_QRY;
				}
				else {
					 imageKey = PluginImages.ICON_FBX4_FUSE;
				}
			}
			else{
				if(fusetype.equals("dsp")){
					 imageKey = PluginImages.ICON_FBX_DSP;
				}
				else if(fusetype.equals("act")){
					 imageKey = PluginImages.ICON_FBX_ACT;
				}
				else if(fusetype.equals("qry")){
					 imageKey = PluginImages.ICON_FBX_QRY;
				}
				else if(fusetype.equals("mod")){
					 imageKey = PluginImages.ICON_FBX_MOD;
				}
				else if(fusetype.equals("url")){
					 imageKey = PluginImages.ICON_FBX_URL;
				}
				else if(fusetype.equals("udf")){
					 imageKey = PluginImages.ICON_FBX_UDF;
				}
				else if(fusetype.equals("lay")){
					 imageKey = PluginImages.ICON_FBX_LAY;
				}
				else if(fusetype.equals("frm")){
					 imageKey = PluginImages.ICON_FBX_QRY;
				}
				else {
					 imageKey = PluginImages.ICON_FBX_FUSE;
				}
				
			}
			
			
			   return PluginImages.get(imageKey);
			  
		}*/
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}