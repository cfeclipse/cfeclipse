/**
 * 
 */
package org.cfeclipse.cfml.editors.decoration;

import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @author markdrew
 *
 */
public class LinkImageDescriptor {

	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static URL iconBaseURL;
	
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
	
	protected static URL createIconFileURL(String offset, String name) throws MalformedURLException 
	{
		URL urlForIcons = getIconBaseURL();
		if(urlForIcons == null) throw new MalformedURLException();

		if(offset != null)
		{	
			StringBuffer buffer = new StringBuffer(offset);
			buffer.append('/');
			buffer.append(name);
			return new URL(urlForIcons, buffer.toString());
		}
		else
		{
			return new URL(urlForIcons, name);
		}
	}
	
	private static URL getIconBaseURL(){
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
		}
		return iconBaseURL;
	}
}
