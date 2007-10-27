package org.cfeclipse.cfml.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.properties.MappingsPropertyPage;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;

public class CFMappings{

	/**
	 * Add sorting of the paths for searches, since things might occlude
	 */
	private static final long serialVersionUID = 356194454622215609L;
	private Map mappingsTable = new HashMap();
	
	private Log logger = LogFactory.getLog(CFMappings.class);
	
	private IProject project;
	
	public CFMappings() {
		super();
	
	}
	
	public CFMappings(IProject project) {
		this.project = project;
		
		//Load the property into the mappingsTable
		try {
			String mappingsProperty = project.getPersistentProperty(new QualifiedName("", MappingsPropertyPage.PATH_MAPPINGS));
			addMappingsFromString(mappingsProperty);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public CFMappings(String storedPaths) {
		addMappingsFromString(storedPaths);
	}
	/**
	 * @param storedPaths
	 */
	private void addMappingsFromString(String storedPaths) {
		
		//There are no mappings defined, lets make a default one if it is null
		if(storedPaths == null){
			mappingsTable.put("/", "/");
			return;
		}
		
		if(storedPaths.startsWith("{") && storedPaths.endsWith("}") && storedPaths != null){
			String trimmedPaths = storedPaths.substring(1, storedPaths.length()-1);
			 String[] strings = trimmedPaths.split(",");
			 for (int i = 0; i < strings.length; i++) {
				 String pathduo = strings[i].trim();
				 	String[] mappingInstance = pathduo.split("=");
				 	if(mappingInstance.length == 2){
				 		mappingsTable.put(mappingInstance[0], mappingInstance[1]);
				 	}
				 	
				 
			}
		}
	}
	

	public void put(String mapping, String path) {
		// TODO Auto-generated method stub
		mappingsTable.put(mapping, path);
	}

	public void remove(String mapping) {
		mappingsTable.remove(mapping);
		
	}

	public Set entrySet() {
		return mappingsTable.entrySet();
	}
	public String toString() {
		return mappingsTable.toString();
	}
	/*
	 * Finding CFC's order
	 * Specifying the CFC location

When you instantiate or invoke a component, you can specify the component name only, or you can specify a qualified path. To specify a qualified path, separate the directory names with periods, not backslashes. For example, myApp.cfcs.myComponent specifies the component defined in myApp\cfcs\myComponent.cfc. For additional information, see Saving and naming ColdFusion components.

ColdFusion uses the following rules to find the specified CFC:

If you use a cfinvoke or cfobject tag, or the CreateObject function, to access the CFC from a CFML page, ColdFusion searches directories in the following order:
Local directory of the calling CFML page
Web root
Directories specified on the Custom Tag Paths page of ColdFusion MX Administrator
If you specify only a component name, ColdFusion searches each of these directories, in turn, for the component.
If you specify a qualified path, such as myApp.cfcs.myComponent, ColdFusion MX looks for a directory matching the first element of the path in each of these directories (in this example, myApp). If ColdFusion MX finds a matching directory, it looks for a file in the specified path beneath that directory, such as myApp\cfcs\myComponent.cfc, relative to each of these directories.
Note: If ColdFusion finds a directory that matches the first path element, but does not find a CFC under that directory, ColdFusion returns a not found error and does not search for another directory.

If you invoke a CFC method remotely, using a specific URL, a form field, Flash Remoting MX, or a web service invocation, ColdFusion looks in the specified path relative to the web root. For form fields and URLs that are specified directly on local web pages, ColdFusion also searches relative to the page directory.
Note: On UNIX and Linux systems, ColdFusion MX attempts to match a CFC name or Custom tag name with a filename, as follows: First, it attempts to find a file with the name that is all lowercase. If it fails, it tries to find a file whose case matches the CFML case. For example, if you specify <cfobject name="myObject" Component="myComponent">, ColdFusion first looks for mycomponent.cfc and, if it doesn't find it, ColdFusion looks for myComponent.cfc.
	 */

	public IPath getCFCPathByPackage(String cfcpackage){
		
		return null;
	}

	public String getCFCPackageByPath(IPath cfcpath){
		
		return null;
	}
	
	public IPath getCFCPathByPackage(String cfcpackage, IPath fromPath){
		
		return null;
	}
	
	/**
	 * 
	 * This function gets a full path from a partial, this comparing it with the rest, comparing the first part of the path
	 * 
	 * @param partialPath
	 * @return
	 */
	public IResource getFullPath(String partialPath){
	//	logger.debug("starting to match " + partialPath);
	//	logger.debug("against the following paths " + mappingsTable);
		
		if(partialPath.startsWith("/")){
			//lookup the "/something" mapping, if we dont find it, we use the "/"
		}
		else{
			// we use the "/" mapping by default
		//	logger.debug("matching from the root, since there is no path defined");
			String mainPath = (String)mappingsTable.get("/");
			mainPath += "/" + partialPath;
		//	logger.debug("Now the path is: " + mainPath);
			//try and find it in the workspace
			 IWorkspace workspace = ResourcesPlugin.getWorkspace();
			   IWorkspaceRoot root = workspace.getRoot();
			   IResource resource = root.findMember(mainPath);
		//	   logger.debug("found a resource maybe " + resource.getType() + " " + IResource.FOLDER );
			   if(resource.exists()){
				   return (IResource)resource;
			   }
			
		}
		
		
		return null;
	}

	
	
}

