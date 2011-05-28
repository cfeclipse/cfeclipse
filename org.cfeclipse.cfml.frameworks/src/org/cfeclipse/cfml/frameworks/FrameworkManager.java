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
package org.cfeclipse.cfml.frameworks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class FrameworkManager {
	// The parsed frameworks configuration file.
	private Document frameworksDoc;

	// Framework Types
	public static final String GENERIC = "frameworks_generic";
	public static String COLDSPRING = "framework_cs";
	public static String MODELGLUE = "framework_mg";
	public static String REACTOR = "framework_re";
	public static String TRANSFER = "framework_tr";
	public static String FUSEBOX = "framework_fbx";
	public static String MACHII = "framework_mii";

	// Logger for this class
	private Log logger = LogFactory.getLog(FrameworkManager.class);
	
	/**
	 * TODO: Probably need to do a get instance and make this a singleton, or add it to the plugin, with a reset switch 
	 * for those happy campers that want to change stuff in it!
	 */
	public FrameworkManager() {
		super();

		// Parse the frameworks document, as per usual
		this.frameworksDoc = ConfigLoader.loadConfig("frameworks.xml");
		logger.debug("Loaded the frameworks document" + frameworksDoc);
	}

	public String getFrameworkBaseFile(String frameworkType){
		
		//Get the framework
		////framework[@id='framework_tr']/files/file[1]
		
		if(frameworkType.equals(COLDSPRING)){
			return "coldspring.xml";
		}
		else if(frameworkType.equals(MODELGLUE)){
			return "modelglue.xml";
		}
		else if(frameworkType.equals(REACTOR)){
			return "reactor.xml";
		}
		else if(frameworkType.equals(TRANSFER)){
			return "transfer.xml";
		}
		else if(frameworkType.equals(FUSEBOX)){
			return "fusebox.xml";
		}
		return null;
	}

	public String getAltFrameworkBaseFile(String frameworkType){
		if(frameworkType.equals(FUSEBOX)){
			return "fusebox.xml.cfm";
		}
		return null;
	}
	
	public String getSuppFrameworkFile(String frameworkType){
		if(frameworkType.equals(FUSEBOX)){
			return "circuit.xml";
		}
		return null;
	}
	public String getAltSuppFrameworkFile(String frameworkType){
		if(frameworkType.equals(FUSEBOX)){
			return "circuit.xml.cfm";
		}
		return null;
	}
	
	
	public FrameworkType[] getAllFrameworks(){
		ArrayList aFrameworks = new ArrayList();
		
		Element rootElement = this.frameworksDoc.getRootElement();
		List frameworkNodes = rootElement.getChildren();
		for (Iterator iter = frameworkNodes.iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			if(element.getName().equalsIgnoreCase("framework")){
				FrameworkType type = new FrameworkType(element.getAttributeValue("id"), element.getAttributeValue("name"));
				aFrameworks.add(type);
			}
			
		}
		return (FrameworkType[]) aFrameworks.toArray(new FrameworkType[aFrameworks.size()]);
	}
	
	public String getFrameworkId(String frameworkName){
		try {
			XPath x = XPath.newInstance("//framework[@name='"+ frameworkName+ "'");
			Element element = (Element)x.selectSingleNode(frameworksDoc);
			return element.getAttributeValue("id");
			
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public boolean isFrameworkFile(String fileName){
		
		if(fileName.equalsIgnoreCase("ColdSpring.xml")){
			return true;
		}
		else if(fileName.equalsIgnoreCase("modelglue.xml")){
			return true;
		}
		else if(fileName.equalsIgnoreCase("reactor.xml")){
			return true;
		}
		else if(fileName.equalsIgnoreCase("transfer.xml")){
			return true;
		}
		else if(fileName.equalsIgnoreCase("fusebox.xml") || fileName.equalsIgnoreCase("fusebox.xml.cfm")){
			return true;
		}
		else if(fileName.equalsIgnoreCase("mach-ii.xml")){
			return true;
		}
		
		
		
		return false;
	}
	
	public boolean isFrameworkSubFile(String fileName){
		
		if(fileName.equalsIgnoreCase("circuit.xml") || fileName.equalsIgnoreCase("circuit.xml.cfm")){
			return true;
		}
			
		
		return false;
	}
	
	public String getFrameworkType(IResource resource){
		//do a matching thing on the name of the file
		//
		XPath x;
		
		try {
			x = XPath.newInstance("//framework/files/file");
			List list = x.selectNodes(frameworksDoc);
			
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				if(resource.getName().equalsIgnoreCase(element.getText())){
					//Get the id of the parent
					String attributeValue = element.getParentElement().getParentElement().getAttributeValue("id");
					return attributeValue;
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean canOverride(String frameworkType) {
		if(frameworkType.equals(COLDSPRING)){
			return true;
		}
		else if(frameworkType.equals(MODELGLUE)){
			return false;
		}
		else if(frameworkType.equals(REACTOR)){
			return true;
		}
		else if(frameworkType.equals(TRANSFER)){
			return true;
		}
		else if(frameworkType.equals(FUSEBOX)){
			return false;
		}
		else if(frameworkType.equals(MACHII)){
			return false;
		}
		
		return false;
	}

	
	public IResource[] getDefaultFrameworkDirectories(IProject project){
		ArrayList resources = new ArrayList();
		
		try {
			XPath x = XPath.newInstance("/frameworks/config/folders/folder");
			List list = x.selectNodes(frameworksDoc);

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Element configFolder = (Element) iter.next();
				
				IResource resource = project.findMember(configFolder.getText());
				if(resource != null && resource.exists() && (resource instanceof IFolder || resource instanceof IProject)){
					resources.add(resource);
				}
			}
		} catch (JDOMException e) {
			logger.error(e.getStackTrace());
		}

		return (IResource[]) resources.toArray(new IResource[resources.size()]);
	}
	
	
	public IResource[] getFrameworkFiles(IProject project){
		//Initially just get all items that are tagged as framework files
		
		//This goes to the Project Parser
		
		return null;
	}
	
	
	//This is the iterator
	
	
	/**
	 * @param resource
	 * @return
	 */
	public boolean isFrameworkFile(IResource resource) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
