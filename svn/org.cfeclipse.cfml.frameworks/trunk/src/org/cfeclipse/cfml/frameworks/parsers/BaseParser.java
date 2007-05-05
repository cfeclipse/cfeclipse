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
package org.cfeclipse.cfml.frameworks.parsers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.cfeclipse.cfml.frameworks.FrameworkManager;
import org.cfeclipse.cfml.frameworks.views.TreeParentNode;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.contrib.input.LineNumberElement;
import org.jdom.contrib.input.LineNumberSAXBuilder;

public class BaseParser implements IParser {
	
	private IProject project;
	private Document document;
	private String frameworkType = FrameworkManager.GENERIC;
	private String filePath;

	public BaseParser(IProject project) {
		super();
		this.project = project;
	}

	public BaseParser(IProject project, String frameworkType) {
		super();
		this.project = project;
		this.frameworkType = frameworkType;
	
	}

	public void parse(String path, TreeParentNode parentnode) {
		this.filePath = path;
		LineNumberSAXBuilder builder = new LineNumberSAXBuilder();
		
		try {
			Document document = builder.build(path);
			Element rootElement = document.getRootElement();
			List elemList = rootElement.getChildren();
			
			parse2(elemList, parentnode);
			
		
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void parse2(List elements, TreeParentNode parentnode){
	
			
	
			for (Iterator iter = elements.iterator(); iter.hasNext();) {
				Object iterElement = iter.next();
				if (iterElement instanceof LineNumberElement) {
	
					//if this is a circuit, we will want to go into this 
					LineNumberElement lnElement = (LineNumberElement) iterElement;
					
				
					TreeParentNode node = new TreeParentNode(lnElement.getName());
					node.setElement(lnElement);
					node.setLinenumber(lnElement.getStartLine());
					node.setFrameworkType(frameworkType);
					node.setDocumentPath(filePath);
					
						
					
					if(!lnElement.getChildren().isEmpty()){
						parse2(lnElement.getChildren(), node);
					}
					parentnode.addChild(node);
				}
			}
	}

	/**
	 * Pass in a cfc id, such as org.cfeclipse.components.Component
	 * and it returns the path such as /project/org/cfeclipse/components/Component.cfc
	 * 
	 * @param cfcid
	 * @return
	 */
	public String findCFC(String cfcid){
		if(cfcid !=null){
		//Need to get the mapping(s) here.
		 QualifiedName propertyName = new QualifiedName("", "componentRoot");
		 String component_root = "";
         try {
			String pname = project.getProject().getPersistentProperty(propertyName);
			component_root = pname;
		} catch (CoreException e) {
		}
		if(component_root != null){
			if(component_root.length() > 0 && cfcid.startsWith(component_root)){
				cfcid = cfcid.substring(component_root.length());
			
			}
			
			
				String projectedPath = "/" + cfcid.replace('.', '/') + ".cfc";
				IResource resource = project.findMember(projectedPath);
				if(resource != null){
					if(resource.exists()){
						return projectedPath;
					}
				}
			}
		}
		return "";
	}


	public Document getDocument() {
		return this.document;
	}


	public void setDocument(Document document) {
		this.document = document;
	}

	public  String getFrameworkType() {
		return this.frameworkType;
	}

	public void setFrameworkType(String frameworkType) {
		this.frameworkType = frameworkType;
	}
}
