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
	
	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static URL iconBaseURL;
	
	protected static final ImageRegistry IMAGE_REGISTRY = new ImageRegistry();
	/** the default icon (the cf or bluedragon shield */
	public static final String ICON_DEFAULT = "default.gif";
	/** little tools that make an X */
	public static final String ICON_TOOLS   = "tools.gif";
	/** generic element icon (the E) */
	public static final String ICON_TAG     = "element.gif";
	/** generic attribute icon (the A) */
	public static final String ICON_ATTR    = "attribute.gif";
	/** the little blue square thing (denotes a function) */
	public static final String ICON_FUNC    = "function.gif";
	/** the little green circle with a 'p' inside it (denotes a function parameter) */
	public static final String ICON_PARAM    = "param.gif";
	/** the little dark green circle with a 'p' inside it (denotes a highlighted function parameter) */
	public static final String ICON_PARAM_DARK    = "param_dark.gif";
	/** the package icon (the little gift wrapped thing */
	public static final String ICON_PACKAGE = "package.gif";
	/** generic value icon the three bars with the arrow */
	public static final String ICON_VALUE	= "value.gif";
	/** generic class icon the rounded C */
	public static final String ICON_CLASS	= "class.gif";
	/** generic snip icon the plugin looking thing */
	public static final String ICON_SNIP    = "snip.gif";
	/** Insert snippet icon */
	public static final String ICON_INSERT_SNIP    = "insertsnip.gif";
	/** the D dreamweaver icon */
	public static final String ICON_DW      = "dw.gif";
	
	//toolbar dir
	/** the + sign */
	public static final String ICON_ADD		= "add.gif";
	/** a pen */
	public static final String ICON_EDIT	= "edit.gif";
	/** a minus sign */
	public static final String ICON_REMOVE	= "remove.gif";
	/** a big red X */
	public static final String ICON_DELETE	= "delete.gif";
	/** the two arrows icon */
	public static final String ICON_REFRESH	= "refresh.gif";
	/** arrow pointing left */
	public static final String ICON_BACK	= "back.gif";
	/** arrow pointing right */
	public static final String ICON_FORWARD	= "forward.gif";
	/** little gears */
	public static final String ICON_PROCESS	= "process.gif";
	/** red american stop sign looking thing */
	public static final String ICON_STOP	= "stop.gif";
	/** little house */
	public static final String ICON_HOME	= "home.gif";
	/** arrow with a white block (used in the Jump menus) */
	public static final String ICON_SHOW	= "show.gif";
	/** arrow with a blue block (used in the Jump and select menus) */
	public static final String ICON_SHOW_AND_SELECT	= "showandselect.gif";
	/** the red cfdump looking thing */
	public static final String ICON_DUMP = "dump.gif";
	/** a little pen writting on paper */
	public static final String ICON_SCRIPT = "script.gif";
	/** a database table */
	public static final String ICON_TABLE = "table.gif";
	/** a server */
	public static final String ICON_SERVER = "server.gif";
	/** a generic file */
	public static final String ICON_FILE = "file.gif";
	/** a closed folder (directory) */
	public static final String ICON_FOLDER = "dir.gif";
	/** a closed folder with a little arrow */
	public static final String ICON_IMPORT = "import.gif";
	/** coffee bean icon */
	public static final String ICON_BEAN = "bean.gif";
	/** red circle with an X. use for Bad bad things  */
	public static final String ICON_ERROR = "error.gif";
	/** yellow triangle with an ! "Could be bad things" */
	public static final String ICON_WARNING = "warning.gif";
	/** yellow diamond with an ! "This really shouldnt be but will work anyway" 
	 * errors */
	public static final String ICON_ALERT = "alert.gif";
	/** a little blue globe */
	public static final String ICON_GLOBE = "globe.gif";
	/** some books */
	public static final String ICON_LIBRARY = "library.gif";
	
	/** sorting icon */
	public static final String ICON_SORTAZ = "sortaz.gif";

	/** Pinning stuff icon */
	public static final String ICON_PIN = "pin.gif";
	
	public static final String ICON_PVIEW_FOLDER_WWW = "folder-www.png";
	public static final String ICON_PVIEW_FOLDER_CFC = "folder-cfc.png";
	public static final String ICON_PVIEW_FOLDER_CUS = "folder-custom.png";
	
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
					//CFMLPlugin.getDefault().getDescriptor().getInstallURL(),
					CFMLPlugin.getDefault().getBundle().getEntry("/"),
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
			addImageToRegistry(MODEL_OBJECTS,ICON_PARAM);
			addImageToRegistry(MODEL_OBJECTS,ICON_PARAM_DARK);
			addImageToRegistry(MODEL_OBJECTS,ICON_SNIP);
			addImageToRegistry(MODEL_OBJECTS,ICON_DW);
			addImageToRegistry(MODEL_OBJECTS,ICON_PACKAGE);
			addImageToRegistry(MODEL_OBJECTS,ICON_VALUE);
			addImageToRegistry(MODEL_OBJECTS,ICON_CLASS);
			addImageToRegistry(MODEL_OBJECTS,ICON_TABLE);
			addImageToRegistry(MODEL_OBJECTS,ICON_SERVER);
			addImageToRegistry(MODEL_OBJECTS,ICON_FILE);
			addImageToRegistry(MODEL_OBJECTS,ICON_FOLDER);
			addImageToRegistry(MODEL_OBJECTS,ICON_BEAN);
			addImageToRegistry(MODEL_OBJECTS,ICON_ERROR);
			addImageToRegistry(MODEL_OBJECTS,ICON_WARNING);
			addImageToRegistry(MODEL_OBJECTS,ICON_ALERT);
			
			addImageToRegistry(TOOLBAR,ICON_ADD);
			addImageToRegistry(TOOLBAR,ICON_EDIT);
			addImageToRegistry(TOOLBAR,ICON_REMOVE);
			addImageToRegistry(TOOLBAR,ICON_DELETE);
			addImageToRegistry(TOOLBAR,ICON_REFRESH);
			
			addImageToRegistry(TOOLBAR,ICON_BACK);
			addImageToRegistry(TOOLBAR,ICON_FORWARD);
			addImageToRegistry(TOOLBAR,ICON_PROCESS);
			addImageToRegistry(TOOLBAR,ICON_STOP);
			addImageToRegistry(TOOLBAR,ICON_HOME);
			addImageToRegistry(TOOLBAR,ICON_SHOW);
			addImageToRegistry(TOOLBAR,ICON_SHOW_AND_SELECT);
			addImageToRegistry(TOOLBAR,ICON_DUMP);
			addImageToRegistry(TOOLBAR,ICON_SCRIPT);
			addImageToRegistry(TOOLBAR,ICON_IMPORT);
			addImageToRegistry(TOOLBAR,ICON_GLOBE);
			addImageToRegistry(TOOLBAR,ICON_LIBRARY);
			addImageToRegistry(TOOLBAR,ICON_SORTAZ);
			addImageToRegistry(TOOLBAR,ICON_PIN);
			addImageToRegistry(TOOLBAR,ICON_INSERT_SNIP);
			
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