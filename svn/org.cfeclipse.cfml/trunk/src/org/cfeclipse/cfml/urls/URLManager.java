/**
 * This class allows you to get a url from a resource, or its parent etc
 * Its used to get the URL for a resource
 */
package org.cfeclipse.cfml.urls;

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
	 * @return
	 */
	public static String  getURL(IResource resource,  String resourceList){
			
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
					
					System.out.println(resource.getName() +  "/" + resourceList);
					return getURL(parent, resource.getName() +  "/" + resourceList);
				}
				
			}
			else{
				return url;
			}
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
