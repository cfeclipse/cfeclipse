/*
 * Created on 	: 03-Dec-2004
 * Created by 	: Mark Drew
 * File		  	: PluginImages.java
 * Description	:
 * 
 */
package org.cfeclipse.frameworks.fusebox4.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.frameworks.fusebox4.Fusebox4Plugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;


/**
 * @author Mark Drew 
 * This class stores info about the location of images for this plugin
 */
public class PluginImages {

    /** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static URL iconBaseURL;
	
	protected static final ImageRegistry IMAGE_REGISTRY = new ImageRegistry();
	/** the default icon (the cf or bluedragon shield */
	public static final String ICON_DEFAULT = "default.gif";
	/** A plain non-cfml document icon */
	public static final String ICON_DOCUMENT = "document.gif";
    
//	Fusebox 3 Icons
	/** Core Files and terminology **/
	public static final String ICON_FUSEBOX      	= "fusebox.gif";
	public static final String ICON_FBX_FUSEACTION  = "fbx_fuseaction.gif";
	public static final String ICON_FBX_FUSE      	= "fbx_fuse.gif";
	public static final String ICON_FBX_SETTINGS	= "fbx_settings.gif";
	public static final String ICON_FBX_SWITCH		= "fbx_switch.gif";
	public static final String ICON_FBX_CIRCUIT     = "fbx_circuit.gif";
	public static final String ICON_FBX_XFA      	= "fbx_xfa.gif";
	public static final String ICON_FBX_FUSEDOC    	= "fusedoc.gif";
	
	/** Use files **/
	public static final String ICON_FBX_ACT      	= "fbx_act.gif";
	public static final String ICON_FBX_DSP      	= "fbx_dsp.gif";
	public static final String ICON_FBX_FRM      	= "fbx_frm.gif";
	public static final String ICON_FBX_LAY      	= "fbx_lay.gif";
	public static final String ICON_FBX_MOD      	= "fbx_mod.gif";
	public static final String ICON_FBX_QRY      	= "fbx_qry.gif";
	public static final String ICON_FBX_UDF      	= "fbx_udf.gif";
	public static final String ICON_FBX_URL      	= "fbx_url.gif";
	
	/** Error Icons **/
	public static final String ICON_ERR_FBX_FUSEACTION  = "err_fbx_fuseaction.gif";
	public static final String ICON_ERR_FBX_CIRCUIT     = "err_fbx_circuit.gif";
	public static final String ICON_ERR_FUSEBOX      	= "err_fusebox.gif";
	
//	Fusebox 4 Icons
	/** Core Files and terminology **/
	public static final String ICON_FUSEBOX4      	= "fusebox.gif";
	public static final String ICON_FBX4_FUSEACTION  = "fbx_fuseaction.gif";
	public static final String ICON_FBX4_FUSE      	= "fusebox_fuse.gif";
	public static final String ICON_FBX4_SETTINGS	= "fbx_settings.gif";
	public static final String ICON_FBX4_SWITCH		= "fbx_switch.gif";
	public static final String ICON_FBX4_CIRCUIT     = "fbx_circuit.gif";
	public static final String ICON_FBX4_XFA      	= "fbx_xfa.gif";
	public static final String ICON_FBX4_FUSEDOC    = "fusedoc.gif";
	
	/** Use files **/
	public static final String ICON_FBX4_ACT      	= "fbx_act.gif";
	public static final String ICON_FBX4_DSP      	= "fbx_dsp.gif";
	public static final String ICON_FBX4_FRM      	= "fbx_frm.gif";
	public static final String ICON_FBX4_LAY      	= "fbx_lay.gif";
	public static final String ICON_FBX4_MOD      	= "fbx_mod.gif";
	public static final String ICON_FBX4_QRY      	= "fbx_qry.gif";
	public static final String ICON_FBX4_UDF      	= "fbx_udf.gif";
	public static final String ICON_FBX4_URL      	= "fbx_url.gif";
	public static final String ICON_FBX_DO			= "fbx_do.gif";
	public static final String ICON_FBX_IF			= "fbx_if.gif";
	public static final String ICON_FBX_FALSE		= "fbx_false.gif";
	public static final String ICON_FBX_TRUE		= "fbx_true.gif";
	
	/** Error Icons **/
	public static final String ICON_ERR_FBX4_FUSEACTION  = "err_fbx4_fuseaction.gif";
	public static final String ICON_ERR_FBX4_CIRCUIT     = "err_fbx4_circuit.gif";
	public static final String ICON_ERR_FUSEBOX4      	= "err_fusebox4.gif";
	
	public static final String ICON_PVIEW_FOLDER_WWW = "folder-www.png";
	public static final String ICON_PVIEW_FOLDER_CFC = "folder-cfc.png";
	public static final String ICON_PVIEW_FOLDER_CUS = "folder-custom.png";
	
	public static final String ICON_REFRESH = "refresh.gif";
	public static final String MODEL_OBJECTS = "fusebox";
	
	
	
	public static void initPluginImages()
	{
		if(iconBaseURL == null)
		{
			String pathSuffix = "icons/";
			try 
			{
				iconBaseURL = new URL(
					//CFMLPlugin.getDefault().getDescriptor().getInstallURL(),
					Fusebox4Plugin.getDefault().getBundle().getEntry("/"),
					pathSuffix
				);
				
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace(System.err);
			}
			//Fusebox 3 Images
			addImageToRegistry(MODEL_OBJECTS,ICON_FUSEBOX);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_FUSEACTION);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_FUSE);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_SETTINGS);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_SWITCH);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_CIRCUIT);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_XFA);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_FUSEDOC);
			
			
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_ACT);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_DSP);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_FRM);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_LAY);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_MOD);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_QRY);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_UDF);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_URL);
			addImageToRegistry(MODEL_OBJECTS,ICON_REFRESH);
			
			//Error files
			addImageToRegistry(MODEL_OBJECTS,ICON_ERR_FUSEBOX);
			addImageToRegistry(MODEL_OBJECTS,ICON_ERR_FBX_CIRCUIT);
			addImageToRegistry(MODEL_OBJECTS,ICON_ERR_FBX_FUSEACTION);
			
			//Fusebox 4 Images
			addImageToRegistry(MODEL_OBJECTS,ICON_FUSEBOX4);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_FUSEACTION);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_FUSE);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_SETTINGS);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_SWITCH);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_CIRCUIT);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_XFA);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_FUSEDOC);
			
			
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_ACT);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_DSP);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_FRM);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_LAY);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_MOD);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_QRY);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_UDF);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX4_URL);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_DO);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_IF);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_TRUE);
			addImageToRegistry(MODEL_OBJECTS,ICON_FBX_FALSE);
			
			//Error files
			addImageToRegistry(MODEL_OBJECTS,ICON_ERR_FUSEBOX4);
			addImageToRegistry(MODEL_OBJECTS,ICON_ERR_FBX4_CIRCUIT);
			addImageToRegistry(MODEL_OBJECTS,ICON_ERR_FBX4_FUSEACTION);
			
			
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_WWW);
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_CFC);
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_CUS);
		}
	}
	/**
	 * gets an image for the registry
	 */
	public static Image get(String key) {
		return IMAGE_REGISTRY.get(key);
	}

	/** 
	 * gets a handle to the registry 
	 */
	public static ImageRegistry getImageRegistry() 
	{
		return IMAGE_REGISTRY;
	}
	 
	/**
	 * add and image to the image registry
	 */
	protected static ImageDescriptor addImageToRegistry(String offset, String name) 
	{
		try 
		{
			//System.err.println(createIconFileURL(offset,name));
			ImageDescriptor result = ImageDescriptor.createFromURL(
				createIconFileURL(
					offset,
					name
				)
			);
						
			//System.err.println("adding: " + name + " " + result.toString());
			IMAGE_REGISTRY.put(name, result);
			
			return result;	
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace(System.err);
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	/** 
	 * create an image descriptor from an offset and name 
	 */
	protected static ImageDescriptor createDescriptor(String offset, String name)
	{
		try 
		{
			return ImageDescriptor.createFromURL(
				createIconFileURL(offset, name)
			);
		}
		catch (MalformedURLException e) 
		{
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	/** 
	 * creates a url to an icon using the base, offset and the name 
	 */
	protected static URL createIconFileURL(String offset, String name) throws MalformedURLException 
	{
		if(iconBaseURL == null) throw new MalformedURLException();

		if(offset != null)
		{	
			StringBuffer buffer = new StringBuffer(offset);
			buffer.append('/');
			buffer.append(name);
			return new URL(iconBaseURL, buffer.toString());
		}
		else
		{
			return new URL(iconBaseURL, name);
		}
	}
}
