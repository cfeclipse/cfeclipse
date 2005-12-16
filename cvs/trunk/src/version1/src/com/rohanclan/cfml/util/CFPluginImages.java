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
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

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
	/** icon for CFC files */
	public static final String ICON_CFC = "cfc.gif";
	/** A plain non-cfml document icon */
	public static final String ICON_DOCUMENT = "document.gif";
	/** little tools that make an X */
	public static final String ICON_TOOLS   = "tools.gif";
	/** generic element icon (the E) */
	public static final String ICON_TAG     = "element.gif";
	/** generic attribute icon (the A) */
	public static final String ICON_ATTR    = "attribute.gif";
	/** the little blue square thing (denotes a function) */
	public static final String ICON_FUNC    = "function.gif";
	/** Indicator for unreadable items */
	public static final String ICON_PERM_DENIED    = "perm_denied.gif";
	/** Indicator for read only items */
	public static final String ICON_READ_ONLY    = "perm_read_only.gif";
	/** A public method in a CFC */
	public static final String ICON_METHOD_PUBLIC    = "method_public.gif";
	/** A package method in a CFC */
	public static final String ICON_METHOD_PACKAGE    = "method_package.gif";
	/** A private method in a CFC */
	public static final String ICON_METHOD_PRIVATE    = "method_private.gif";
	/** A remote method in a CFC */
	public static final String ICON_METHOD_REMOTE    = "method_remote.gif";
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
	/** file template snip icon the insert snippet icon */
	public static final String ICON_TEMPLATE_SNIP    = "insertsnip.gif";
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
	/** a folder repository (hard disk) */
	public static final String ICON_REPOSITORY = "repository.gif";
	/** a closed folder (directory) */
	public static final String ICON_FOLDER = "dir.gif";
	/** an open folder (directory) */
	public static final String ICON_FOLDER_OPEN = "dir_open.gif";
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

	/** Expand collapsed code icon */
	public static final String ICON_EXPAND = "expanded.gif";

	/** Collapse code icon */
	public static final String ICON_COLLAPSE = "collapsed.gif";
	
	/** Dictionary Sorting icons */
	public static final String DIC_CATEGORISE = "dic_cat.gif";
	public static final String DIC_UNCATEGORISE = "dic_uncat.gif";
	
	public static final String ICON_PVIEW_FOLDER_WWW = "folder-www.png";
	public static final String ICON_PVIEW_FOLDER_CFC = "folder-cfc.png";
	public static final String ICON_PVIEW_FOLDER_CUS = "folder-custom.png";
	
	
	
	/** Editor toolbar icons */
	public static final String T_DATABASE = "database.png";
	public static final String T_COMMENT = "comment.png";
	public static final String T_HASH = "hash.png";
	public static final String T_OUT = "out.png";
	public static final String T_SET = "set.png";
	public static final String T_COL = "cfcol.png";
	public static final String T_INCLUDE  = "cfinclude.png";
	public static final String T_TABLE = "cftable.png";
	public static final String T_INSERT = "insert.png";
	public static final String T_LOCATION = "location.png";
	public static final String T_PARAM = "param.png";
	public static final String T_SERVER = "server.png";
	public static final String T_UPDATE = "update.png";
	
	
	// directory offsets
	public static final String MODEL_OBJECTS = "obj16";
	public static final String TOOLBAR 		 = "ctool16";
	public static final String EDITORTOOLBAR   = "toolbars";
	
	
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
			addImageToRegistry(MODEL_OBJECTS,ICON_CFC);
			addImageToRegistry(MODEL_OBJECTS,ICON_DOCUMENT);
			addImageToRegistry(MODEL_OBJECTS,ICON_READ_ONLY);
			addImageToRegistry(MODEL_OBJECTS,ICON_PERM_DENIED);
			addImageToRegistry(MODEL_OBJECTS,ICON_TOOLS);
			addImageToRegistry(MODEL_OBJECTS,ICON_TAG);
			addImageToRegistry(MODEL_OBJECTS,ICON_ATTR);
			addImageToRegistry(MODEL_OBJECTS,ICON_FUNC);
			addImageToRegistry(MODEL_OBJECTS,ICON_METHOD_REMOTE);
			addImageToRegistry(MODEL_OBJECTS,ICON_METHOD_PUBLIC);
			addImageToRegistry(MODEL_OBJECTS,ICON_METHOD_PACKAGE);
			addImageToRegistry(MODEL_OBJECTS,ICON_METHOD_PRIVATE);
			addImageToRegistry(MODEL_OBJECTS,ICON_PARAM);
			addImageToRegistry(MODEL_OBJECTS,ICON_PARAM_DARK);
			addImageToRegistry(MODEL_OBJECTS,ICON_SNIP);
			addImageToRegistry(MODEL_OBJECTS,ICON_TEMPLATE_SNIP);
			addImageToRegistry(MODEL_OBJECTS,ICON_DW);
			addImageToRegistry(MODEL_OBJECTS,ICON_PACKAGE);
			addImageToRegistry(MODEL_OBJECTS,ICON_VALUE);
			addImageToRegistry(MODEL_OBJECTS,ICON_CLASS);
			addImageToRegistry(MODEL_OBJECTS,ICON_TABLE);
			addImageToRegistry(MODEL_OBJECTS,ICON_SERVER);
			addImageToRegistry(MODEL_OBJECTS,ICON_FILE);
			addImageToRegistry(MODEL_OBJECTS,ICON_FOLDER);
			addImageToRegistry(MODEL_OBJECTS,ICON_FOLDER_OPEN);
			addImageToRegistry(MODEL_OBJECTS,ICON_REPOSITORY);
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
			addImageToRegistry(TOOLBAR,ICON_EXPAND);
			addImageToRegistry(TOOLBAR,ICON_COLLAPSE);
			addImageToRegistry(TOOLBAR,ICON_INSERT_SNIP);
			
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_WWW);
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_CFC);
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_CUS);
			
			addImageToRegistry(MODEL_OBJECTS, DIC_CATEGORISE);
			addImageToRegistry(MODEL_OBJECTS, DIC_UNCATEGORISE);
			
			
			
			/** Add editor items */
			addImageToRegistry(EDITORTOOLBAR, T_DATABASE);
			addImageToRegistry(EDITORTOOLBAR, T_COMMENT);
			addImageToRegistry(EDITORTOOLBAR, T_HASH);
			addImageToRegistry(EDITORTOOLBAR, T_OUT);
			addImageToRegistry(EDITORTOOLBAR, T_SET);
			addImageToRegistry(EDITORTOOLBAR, T_COL);
			addImageToRegistry(EDITORTOOLBAR, T_INCLUDE);
			addImageToRegistry(EDITORTOOLBAR, T_TABLE);
			addImageToRegistry(EDITORTOOLBAR, T_INSERT);
			addImageToRegistry(EDITORTOOLBAR, T_LOCATION);
			addImageToRegistry(EDITORTOOLBAR, T_PARAM);
			addImageToRegistry(EDITORTOOLBAR, T_SERVER);
			addImageToRegistry(EDITORTOOLBAR, T_UPDATE);
			
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
	
	/**
	 * Creates an overlay dot at the bottom right of the given image.
	 * The dot is offset at 1px from the right-most and bottom-most non-transparent pixel of the image.
	 * Evaluation of the right-most and bottom-most non-transparent pixel is performed by reading left from the
	 * center of the image y axis and up from the center of the image x axis respectively.
	 * @param image The image that the overlay will be added to
	 * @param color The color that the overlay should be.
	 * @return
	 */
	public static Image addOverlay(Image image, RGB color) {
	    int overlayColor = image.getImageData().palette.getPixel(color);
        ImageData fullImageData = image.getImageData();
        ImageData transparency = fullImageData.getTransparencyMask();
        
        int width = fullImageData.width;
        int height = fullImageData.height;
        int midX = java.lang.Math.round(width/2);
        int midY = java.lang.Math.round(height/2);
        for (int i=width-1;i>=0;i--) {
            int pixelColor = fullImageData.getPixel(i,midX);
            int transColor = transparency.getPixel(i,midY);
            if (pixelColor != transColor) {
                width = i;
                break;
            }
        }
        for (int i = height-1;i>=0;i--) {
            int pixelColor = fullImageData.getPixel(midX,i);
            int transColor = transparency.getPixel(midY,i);
            if (pixelColor != transColor) {
                height = i;
                break;
            }
        }
        int minX = width-4;
        int maxX = width-1;
        int minY = height-4;
        int maxY = height-1;
        for (int i=minX;i<=maxX;i++) {
            for (int j=minY;j<=maxY;j++) {
                boolean repaint = true;
                if (i == minX 
                        || i == maxX) {
                    if (j == minY) {
                        repaint = false;
                    } else if (j == maxY) {
                        repaint = false;
                    }
                }
                
                if (repaint) {
                    fullImageData.setPixel(i,j,overlayColor);
                }
            }
        }
        Image fullImage = new Image(Display.getCurrent(),fullImageData);
        return fullImage;
    }
	
}