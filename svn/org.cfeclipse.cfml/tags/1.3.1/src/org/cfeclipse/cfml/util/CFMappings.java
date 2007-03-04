package org.cfeclipse.cfml.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public class CFMappings{

	/**
	 * Add sorting of the paths for searches, since things might occlude
	 */
	private static final long serialVersionUID = 356194454622215609L;
	private Map mappingsTable = new HashMap();
	
	public CFMappings(String storedPaths) {
		if(storedPaths == null){
			return;
		}
		if(storedPaths.startsWith("{") && storedPaths.endsWith("}")){
			String trimmedPaths = storedPaths.substring(1, storedPaths.length()-1);
			 String[] strings = trimmedPaths.split(",");
			 for (int i = 0; i < strings.length; i++) {
				 String pathduo = strings[i].trim();
				 	String[] mappingInstance = pathduo.split("=");
				 	mappingsTable.put(mappingInstance[0], mappingInstance[1]);
				 
			}
		}
		System.out.println(mappingsTable);
		
		
		
		
	}
	public CFMappings(IProject project) {
		super();
	
	}
	public CFMappings() {
		super();
	
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
	

	
	
}

