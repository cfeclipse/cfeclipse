/*
 * Created on Jan 31, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.util;

//import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import java.net.MalformedURLException;
import java.net.URL;
import com.rohanclan.cfml.CFMLPlugin;


/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFPluginImages {
	
	//protected static final String NAME_PREFIX =	"net.sourceforge.phpdt.internal.ui.";
	//protected static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();

	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static URL iconBaseURL;
	
	protected static final ImageRegistry IMAGE_REGISTRY = new ImageRegistry();
	public static final String ICON_DEFAULT = "default.gif";
	public static final String ICON_TOOLS   = "tools.gif";
	public static final String ICON_TAG     = "element.gif";
	public static final String ICON_ATTR    = "attribute.gif";
	public static final String ICON_FUNC    = "function.gif";
	public static final String ICON_PACKAGE = "package.gif";
	public static final String ICON_VALUE	= "value.gif";
	
	public static final String ICON_SNIP    = "snip.gif";
	public static final String ICON_DW      = "dw.gif";
	
	//toolbar dir
	public static final String ICON_ADD		= "add.gif";
	public static final String ICON_EDIT	= "edit.gif";
	public static final String ICON_REMOVE	= "remove.gif";
	public static final String ICON_DELETE	= "delete.gif";
	public static final String ICON_REFRESH	= "refresh.gif";
	
	// directory offsets
	public static final String MODEL_OBJECTS = "obj16";
	public static final String TOOLBAR 		 = "ctool16";
	
	
	private CFPluginImages(){;}
	
	public static void initCFPluginImages()
	{
		if(iconBaseURL == null)
		{
			String pathSuffix = "icons/";
			try 
			{
				iconBaseURL = new URL(
					CFMLPlugin.getDefault().getDescriptor().getInstallURL(),
					pathSuffix
				);
				
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace(System.err);
			}
			
			//load up images on our first go round
			addImageToRegistry(MODEL_OBJECTS,ICON_DEFAULT);
			addImageToRegistry(MODEL_OBJECTS,ICON_TOOLS);
			addImageToRegistry(MODEL_OBJECTS,ICON_TAG);
			addImageToRegistry(MODEL_OBJECTS,ICON_ATTR);
			addImageToRegistry(MODEL_OBJECTS,ICON_FUNC);
			addImageToRegistry(MODEL_OBJECTS,ICON_SNIP);
			addImageToRegistry(MODEL_OBJECTS,ICON_DW);
			addImageToRegistry(MODEL_OBJECTS,ICON_PACKAGE);
			addImageToRegistry(MODEL_OBJECTS,ICON_VALUE);
			
			addImageToRegistry(TOOLBAR,ICON_ADD);
			addImageToRegistry(TOOLBAR,ICON_EDIT);
			addImageToRegistry(TOOLBAR,ICON_REMOVE);
			addImageToRegistry(TOOLBAR,ICON_DELETE);
			addImageToRegistry(TOOLBAR,ICON_REFRESH);
		}
	}
	
	/**
	 * gets an image for the registry
	 */
	public static Image get(String key) {
		return IMAGE_REGISTRY.get(key);
	}

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an action. The actions
	 * are retrieved from the *tool16 folders.
	 
	public static void setToolImageDescriptors(
			IAction action,
			String iconName) {
		setImageDescriptors(action, "tool16", iconName);
	} */

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an action. The actions
	 * are retrieved from the *lcl16 folders.
	
	public static void setLocalImageDescriptors(
			IAction action,
			String iconName) {
		setImageDescriptors(action, "lcl16", iconName);
	} */

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