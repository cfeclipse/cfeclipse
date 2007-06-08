/**
 * This class allows you to get a url from a resource, or its parent etc
 * Its used to get the URL for a resource
 */
package org.cfeclipse.cfml.urls;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * @author markdrew
 *
 */
public class URLManager {
	
	
	
	
	public static void setURL(IResource resource, String url){
		try {
			resource.setPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL), url);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * this function will get the url for aresource, or its parent all the way up, it will return null if there isnt one! 
	 * @param resource
	 * @param resourceList (might make this into an array so that we can just convert it, rather than adding "/" each time
	 * @return
	 */
	public static String  getURL(IResource resource,  ArrayList resourceList){
		 Log logger = LogFactory.getLog(URLManager.class);
		//if a File DOESNT have a URL, then we start appending until we build the path
		try {
			
			if(resource instanceof IFile){
				
			}
			
			String url = resource.getPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL));
			
			if(url == null || url.length() == 0){
				
				//We didnt find it - go and get the parent
				IContainer parent = resource.getParent();
				
				//What type of parent are we?
				if(parent !=null){
					
					//Define the URL 
					//add the current resource to the resource list
					resourceList.add(resource.getName());
					return getURL(parent, resourceList);
				}
				
			}
			else{
				//append the items to this
				logger.debug("getting the URL, since we have found a root or something");
				
				String urlPath = "";
				
				if(!resourceList.isEmpty()){
						for (Iterator iter = resourceList.iterator(); iter.hasNext();) {
							String element = (String) iter.next();
							if(iter.hasNext()){
								urlPath = "/" + element + urlPath;
							}
							else{
								urlPath = element + urlPath;
							}
							
							
						}
				}
				
				return url + urlPath;
			}
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
