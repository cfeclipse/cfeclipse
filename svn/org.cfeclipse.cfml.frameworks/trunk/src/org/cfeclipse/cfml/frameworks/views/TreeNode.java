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
package org.cfeclipse.cfml.frameworks.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.jdom.Element;
import org.jdom.contrib.input.LineNumberElement;

/**
 * This is the base tree object we want
 * @author mark
 *
 */
public class TreeNode implements IAdaptable{

	private String name;
	private TreeNode parent;
	private String type = "";
	private Element element;
	private String error ="";
	private int linenumber;
	private String documentPath = "";
	private String frameworkType = "";
	private Path frameworkFilePath;
	private FrameworkFile frameworkFile;
	
	
	/** Simple constructor, remove from here
	 * @param name
	 */
	public TreeNode(String name) {
		this.name = name;
	}
	
	public TreeNode(Element element, String type){
		this.element = element;
		this.type = type;
		
	}
	
	
	public String getType() {
		if(this.element !=null){
			return this.element.getName();
		}
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		if(this.element !=null){
			/*
			//TODO: move this to the label provider
			if(this.element.getName().equalsIgnoreCase("property")){
				return this.element.getAttributeValue("name");
			}
			else if(this.element.getName().equalsIgnoreCase("constructor-arg")){
				return this.element.getAttributeValue("name");
			}
			else if(this.element.getName().equalsIgnoreCase("entry")){
				return this.element.getAttributeValue("key");
			}
			else if(this.element.getName().equalsIgnoreCase("event-handler")){
				return this.element.getAttributeValue("name");
			}
			else if(this.element.getName().equalsIgnoreCase("message")){
				return this.element.getAttributeValue("name");
			}
			else if(this.element.getName().equalsIgnoreCase("scaffold")){
				String name = this.element.getAttributeValue("object");
				//get the alias if it exists
				if(this.element.getAttributeValue("alias") != null){
					name += "(" + this.element.getAttributeValue("alias")+ ")";
				}
				if(this.element.getAttributeValue("type") != null){
					name += ": " + this.element.getAttributeValue("type");
					
				}
				
				
				return name;
			}
			else if(this.element.getName().equalsIgnoreCase("result")){
				String name = this.element.getAttributeValue("do");
				if(this.element.getAttributeValue("name") != null){
					return this.element.getAttributeValue("name") + " -> " + name;
				}
				return name;
			}
			else if(this.element.getName().equalsIgnoreCase("argument")){
				String name = this.element.getAttributeValue("name");
				if(this.element.getAttributeValue("value") != null){
					name += ": " + this.element.getAttributeValue("value");
				}
				return name;
			}
			else if(this.element.getName().equalsIgnoreCase("include")){
				String name = this.element.getAttributeValue("name");
				if(this.element.getAttributeValue("template") != null){
					name += "-> " + this.element.getAttributeValue("template");
				}
				return name;
			}
			else if(this.element.getName().equalsIgnoreCase("bean")){
				String name = this.name;
				if(this.element.getAttributeValue("class")!=null){
					name += ": " + this.element.getAttributeValue("class");
				}
				return name;
			}*/
			return this.element.getName();
		}
		else if (frameworkFile != null){
			return name + ": " + frameworkFile.getFile().getProjectRelativePath();
		}
		return name;
		//This means this is a core framework file
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	public TreeNode getParent() {
		return parent;
	}
	public String toString() {
		if(this.element != null){
			return frameworkType + "_" + element.getName();
		}
		
		return frameworkType + "_" + this.getName();
	}
	
	public Object getAdapter(Class key) {
		return null;
	}
	public Element getElement() {
		return element;
	}
	public void setElement(Element element2) {
		this.element = element2;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setError(String string) {
		this.error = string;
		
	}
	public String getError() {
		return error;
	}
	public boolean hasError(){
		return this.error.length() > 0;
	}
	public int getLinenumber() {
		return linenumber;
	}
	public void setLinenumber(int linenumber) {
		this.linenumber = linenumber;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public String getFrameworkType() {
		return frameworkType;
	}
	public void setFrameworkType(String frameworkType) {
		this.frameworkType = frameworkType;
	}

	public Path getFrameworkFilePath() {
		return frameworkFilePath;
	}

	public void setFrameworkFilePath(Path frameworkFilePath) {
		this.frameworkFilePath = frameworkFilePath;
	}

	public FrameworkFile getFrameworkFile() {
		return frameworkFile;
	}

	public void setFrameworkFile(FrameworkFile frameworkFile) {
		this.frameworkFile = frameworkFile;
	}

	public boolean equals(Object obj) {
		//System.out.println("comparing " + obj.toString() + " and me " + toString());
		if(obj.toString().equalsIgnoreCase(toString())){
			return true;
		}
		else {
			return false;
		}
		//return super.equals(obj);
	}

}
