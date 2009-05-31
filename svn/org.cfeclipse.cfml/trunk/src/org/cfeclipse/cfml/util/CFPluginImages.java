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
package org.cfeclipse.cfml.util;

//import org.eclipse.jface.action.IAction;
import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;


/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFPluginImages {
	
	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static URL iconBaseURL;
	
//	protected static final ImageRegistry IMAGE_REGISTRY = new ImageRegistry();
	protected static final ImageRegistry IMAGE_REGISTRY = CFMLPlugin.getDefault().getImageRegistry();
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
	public static final String ICON_PACKAGE_OFF = "package_off.gif";
	public static final String ICON_PACKAGE_SNIPEX = "package_sx.png";
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
	
	/** Menu functions */
	public static final String ICON_URL_LINK = "world_link.png";
	
	
	/** Decorators */
	public static final String ICON_DECORATOR_LINK = "decorator_link.gif";
	
	/**  File Explorer icons */
	public static final String ICON_DRIVE	= "drive.png";
	public static final String ICON_DRIVE_FTP ="drive_ftp.png";
	public static final String ICON_DRIVE_SFTP ="drive_sftp.png";
	public static final String ICON_DRIVE_ERROR ="drive_error.png";
	
	
	/** Exporting icons for the snipEx  */
	public static final String ICON_SNIP_EXPORT = "export_wiz.gif";
	public static final String ICON_SNIP_IMPORT = "import_wiz.gif";
	
	/** Editor toolbar icons */
	public static final String ICON_OUTPUT = "output.gif";
	public static final String T_INSERT = "addrepo_rep.gif";
	public static final String T_UPDATE = "update_rep.gif";
	public static final String T_SET = "set.gif";
	public static final String T_COMMENT = "comment.gif";
	public static final String T_HASH = "hash.gif";
	public static final String T_TRY = "try.gif";
	public static final String T_CATCH = "catch.gif";
	public static final String T_THROW = "throw.gif";
	public static final String T_LOCK = "lock.gif";
	public static final String T_EXIT = "exit.gif";
	public static final String T_DOCUMENT = "document.gif";
	public static final String T_DOCUMENT_ITEM = "document_item.gif";
	public static final String T_DOCUMENT_SECTION = "document_section.gif";
	public static final String T_SCHEDULE = "time.gif";
	public static final String T_SEARCH = "search.gif";
	public static final String T_SWITCH = "switch.gif";
	public static final String T_CASE = "case.gif";
	public static final String T_DEFAULTCASE = "defaultcase.gif";
	public static final String T_IF = "if.gif";
	public static final String T_ELSE = "else.gif";
	public static final String T_ELSEIF = "elseif.gif";
	public static final String T_APPLICATION = "application.gif";
	public static final String T_POP = "pop.gif";
	public static final String T_FTP = "ftp.gif";
	public static final String T_COOKIE = "cookie.gif";
	public static final String T_HEADER = "header.gif";
	public static final String T_CONTENT = "content.gif";
	public static final String T_MAIL = "mail.gif";
	public static final String T_LDAP = "ldap.gif";
	public static final String T_INDEX = "index.gif";
	public static final String T_REPORT = "report.gif";
	public static final String T_HTTPPARAM = "httpparam.gif";
	public static final String T_FILE = "cffile.gif";
	
	public static final String T_DATABASE = "database.png";
	
	
	public static final String T_OUT = "out.gif";
	public static final String T_COL = "cfcol.png";
	public static final String T_INCLUDE  = "cfinclude.png";
	public static final String T_TABLE = "cftable.png";
	
	public static final String T_LOCATION = "location.png";
	public static final String T_PARAM = "param.png";
	public static final String T_SERVER = "server.png";
	
	//Snipex Image
	public static final String ICON_SNIPEX = "snipex.png";
	
	
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
			addImageToRegistry(MODEL_OBJECTS,ICON_PACKAGE_SNIPEX);
			addImageToRegistry(MODEL_OBJECTS,ICON_PACKAGE_OFF);
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
			
			/** file explorer  */
			
			addImageToRegistry(MODEL_OBJECTS,ICON_DRIVE);
			addImageToRegistry(MODEL_OBJECTS,ICON_DRIVE_FTP);
			addImageToRegistry(MODEL_OBJECTS,ICON_DRIVE_SFTP);
			addImageToRegistry(MODEL_OBJECTS,ICON_DRIVE_ERROR);
			
			
			/** menu */
			addImageToRegistry(MODEL_OBJECTS, ICON_URL_LINK);
			
			/** decorators */
			addImageToRegistry(MODEL_OBJECTS, ICON_DECORATOR_LINK);
			
			
			/** snipex */
			addImageToRegistry(MODEL_OBJECTS, ICON_SNIP_EXPORT);
			addImageToRegistry(MODEL_OBJECTS, ICON_SNIP_IMPORT);
			addImageToRegistry(MODEL_OBJECTS, ICON_SNIPEX);
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
			addImageToRegistry(TOOLBAR, ICON_OUTPUT);
			
			
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_WWW);
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_CFC);
			addImageToRegistry(MODEL_OBJECTS, ICON_PVIEW_FOLDER_CUS);
			
			addImageToRegistry(MODEL_OBJECTS, DIC_CATEGORISE);
			addImageToRegistry(MODEL_OBJECTS, DIC_UNCATEGORISE);
			
			
			
			/** Add editor items */
			addImageToRegistry(TOOLBAR, T_INCLUDE);
			addImageToRegistry(TOOLBAR, T_INSERT);
			addImageToRegistry(TOOLBAR, T_UPDATE);
			addImageToRegistry(TOOLBAR, T_SET);
			addImageToRegistry(TOOLBAR, T_COMMENT);
			addImageToRegistry(TOOLBAR, T_HASH);
			addImageToRegistry(TOOLBAR, T_TRY);
			addImageToRegistry(TOOLBAR, T_CATCH);
			addImageToRegistry(TOOLBAR, T_THROW);
			addImageToRegistry(TOOLBAR, T_LOCK);
			addImageToRegistry(TOOLBAR, T_EXIT);
			addImageToRegistry(TOOLBAR, T_DOCUMENT);
			addImageToRegistry(TOOLBAR, T_DOCUMENT_ITEM);
			addImageToRegistry(TOOLBAR, T_DOCUMENT_SECTION);
			addImageToRegistry(TOOLBAR, T_SCHEDULE);
			addImageToRegistry(TOOLBAR, T_SEARCH);
			addImageToRegistry(TOOLBAR, T_SWITCH);
			addImageToRegistry(TOOLBAR, T_CASE);
			addImageToRegistry(TOOLBAR, T_DEFAULTCASE);
			addImageToRegistry(TOOLBAR, T_IF);
			addImageToRegistry(TOOLBAR, T_ELSE);
			addImageToRegistry(TOOLBAR, T_ELSEIF);
			addImageToRegistry(TOOLBAR, T_APPLICATION);
			addImageToRegistry(TOOLBAR, T_POP);
			addImageToRegistry(TOOLBAR, T_FTP);
			addImageToRegistry(TOOLBAR, T_COOKIE);
			addImageToRegistry(TOOLBAR, T_HEADER);
			addImageToRegistry(TOOLBAR, T_CONTENT);
			addImageToRegistry(TOOLBAR, T_MAIL);
			addImageToRegistry(TOOLBAR, T_LDAP);
			addImageToRegistry(TOOLBAR, T_INDEX);
			addImageToRegistry(TOOLBAR, T_REPORT);
			addImageToRegistry(TOOLBAR, T_HTTPPARAM);
			addImageToRegistry(TOOLBAR, T_FILE);
			
			addImageToRegistry(EDITORTOOLBAR, T_DATABASE);
			addImageToRegistry(EDITORTOOLBAR, T_LOCATION);
			addImageToRegistry(EDITORTOOLBAR, T_PARAM);
			addImageToRegistry(EDITORTOOLBAR, T_SERVER);
			
			
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
	
	public static ImageDescriptor getImageDescriptor(String offset, String imageid){
		
		return createDescriptor(offset, imageid);
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
		// on windows, using the image PaletteData is unreliable.  FIXME: colors are whacked out on windows
        PaletteData palette = new PaletteData(0xFF , 0xFF00 , 0xFF0000);
	    int overlayColor = palette.getPixel(color);
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
        int minX = width-10;
        int maxX = width-1;
        int minY = height-10;
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