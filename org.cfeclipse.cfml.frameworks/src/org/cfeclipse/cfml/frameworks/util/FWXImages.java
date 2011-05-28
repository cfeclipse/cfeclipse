/*
 * Created on Mar 27, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
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
package org.cfeclipse.cfml.frameworks.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.cfml.frameworks.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import sun.java2d.pipe.DrawImage;

public class FWXImages {
	protected static URL iconBaseURL;
	protected static final ImageRegistry IMAGE_REGISTRY = Activator.getDefault().getImageRegistry();
	
	public static final String ICON_BEAN = "bean_obj.gif";
	public static final String ICON_ERR_BEAN = "bean_err.gif";
	public static final String ICON_CONTST_OVER = "constr_ovr.gif";
	public static final String ICON_PROPERTY = "field_public_obj.gif";
	public static final String ICON_CONSTRUCTOR_ARG = "constructor_arg.gif";
	public static final String ICON_VARIABLE = "variable_tab.gif";
	
	
	//Model Glue Items 
	/**
	 * message-listener
	 */
	public static final String ICON_LISTENER = "transmit_go.png";
	/**
	 * message
	 */
	public static final String ICON_MESSAGE = "transmit.png"; //message
	public static final String ICON_ERR_MESSAGE = "transmit_error.png";
	/**
	 * broadcasts
	 */
	public static final String ICON_BROADCASTS = "transmit_blue.png"; //Broadcasts
	
	/**
	 * event-handler
	 */
	public static final String ICON_EVENT_HANDLER = "connect.png";
	public static final String ICON_EVENT_HANDLERS = "disconnect.png";
	
	public static final String ICON_VIEWS = "layers.png";
	
	public static final String ICON_TEMPLATE = "layout_content.png";
	
	public static final String ICON_RESULTS = "arrow_switch.png";
	
	public static final String ICON_RESULT = "arrow_right.png";
	
	public static final String ICON_CONTROLLER = "cog.png";
	public static final String ICON_CONTROLLERS = "cogs.png";
	
	public static final String ICON_ERR_CONTROLLER = "cog_err.png";
	
	//scaffolds
	//list,view,edit,delete
	public static final String ICON_SCAFFOLD_GENERIC = "database_connect.png";
	public static final String ICON_SCAFFOLD_LIST = "database_table.png";
	public static final String ICON_SCAFFOLD_VIEW = "database_gear.png";
	public static final String ICON_SCAFFOLD_EDIT = "database_edit.png";
	public static final String ICON_SCAFFOLD_DELETE = "database_delete.png";
	public static final String ICON_SCAFFOLD_COMMIT = "database_go.png";
	
	
	//Project Root
	public static final String ICON_PROJECT = "bricks.png";
	//Frameworks Icons
	public static final String ICON_MG = "frameworks_mg.gif";
	public static final String ICON_CS = "frameworks_cs.gif";
	public static final String ICON_RE = "frameworks_re.gif";	
	public static final String ICON_TR = "frameworks_tr.gif";
	
	public static final String ICON_FW_ERR = "brick_error.png";
	
	
	//REACTOR  Table icons
	public static final String ICON_DB_TABLE = "table.png";
	public static final String ICON_DB_TABLE_LINK = "table_link.png";
	public static final String ICON_DB_TABLE_REL = "table_relationship.png";
	public static final String ICON_DB = "database.png";
	public static final String ICON_DB_HASONE = "hasone.png";
	public static final String ICON_DB_HASMANY = "hasmany.png";
	
	
	//View Images
	public static final String ICON_XML_VIEW = "chart_organisation.png";
	
	
//	Error overlay
	public static final String ICON_OVER_ERROR = "error.gif";

/**
 * FUSEBOX icons
 */
	public static final String ICON_FBX = "fusebox.gif";
	public static final String ICON_FBX_CIRCUIT = "cog.png";
	public static final String ICON_FBX_CIRCUITS = "cogs.png";
	
/**
 * MACH-ii icons
 */	
	public static final String ICON_Mii = "mach_ii_logo.gif";
	
/**
 * Decorators
 * 
 */
	public static final String DEC_CONFIG = "dec_config.gif";
	
//	 directory offsets
	public static final String VIEW_OBJECTS = "view";
	public static final String GENERAL_OBJECTS = "general";
	public static final String OBJECTS = "objects";
	/** the two arrows icon */
	public static final String ICON_REFRESH	= "refresh.gif";
	
	
//private FWXImages(){;}
	
	public static void initImages()
	{
		initBaseURL();
		addImageToRegistry(OBJECTS, ICON_PROJECT);
		addImageToRegistry(GENERAL_OBJECTS, DEC_CONFIG);
		addImageToRegistry(OBJECTS,ICON_REFRESH);
	}

	/**
	 * 
	 */
	private static void initBaseURL() {
		if(iconBaseURL == null)
		{
			String pathSuffix = "icons/";
			try 
			{
				iconBaseURL = new URL(
					//CFMLPlugin.getDefault().getDescriptor().getInstallURL(),
					Activator.getDefault().getBundle().getEntry("/"),
					pathSuffix
				);
				
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace(System.err);
			}
			addImageToRegistry(OBJECTS,ICON_XML_VIEW);
		}
	}
	
	public static Image get(String key) {
		initBaseURL();
		Image image = IMAGE_REGISTRY.get(key);
		if(image == null){
			addImageToRegistry("objects", key);
			return IMAGE_REGISTRY.get(key);
		}
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

	public static Image addError(Image image){

		ImageDecorator decorator = new ImageDecorator(image, get(ICON_ERR_MESSAGE));
		decorator.drawCompositeImage(16,16);
		
		
		return image;
	}

	public static ImageDescriptor getImageDescriptor(String offset, String imageid) {
		return createDescriptor(offset, imageid);
	}
	
}




