/*
 * Created on 16-Jan-2005
 *
 * The MIT License
 * Copyright (c) 2004 Mark Drew
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
 **/
package org.cfeclipse.frameworks.fusebox.objects;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;
import org.w3c.dom.Node;

/**
 * @author Mark Drew
 * 16-Jan-2005
 * fusebox3cfe2
 * Description: This is a generic object that you can use in the tree by extending it
 *  It provides methods to fields that are usually got and set so that you dont have to do it for each item
 * 
 */
public class FBXObject {
	private ArrayList children;
	private String error;
	private int version = 3;
	private int errorid = 0;
	private String fusedoc;
	private String extension = "cfm";
	//This is a Node that can be assigned (XML node) that is for Fusebox 4 +
	private Node xmlNode;
	private String icon = PluginImages.ICON_FUSEBOX4; //Default Icon
	private FBXObject parent;

	//Change Listners
	protected IDeltaListener listener = NullDeltaListener.getSoleInstance();
	private String name;
	
	public FBXObject(){
		super();
	}
	
	public FBXObject(String itemname){
		name = itemname;
		
	}
	
	
	/*
	 * Here are some error ID's
	 *  0 = no error
	 *  1 = Circuits File Not Found
	 *  2 = Switch File Not Found
	 */
	
	//Not sure if this needs a constructor
	/**
	 * @return Returns the version.
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version The version to set.
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * @return Returns the error.
	 */
	public String getError() {
		return error;
	}
	/**
	 * @return Returns the error.
	 */
	public boolean hasError() {
		if(this.error != null){
			if(this.error.length() == 0){
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
		
	}
	/**
	 * @param error The error to set.
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * Pass in an error ID, this makes it easier to define what errors there are
	 * <li>
	 * 	<ul>0 = no error</ul>
	 *  <ul>1 = Circuits File Not Found</ul>
	 *  <ul>2 = Switch File Not Found</ul>
	 * </li>
	 * @param errorid
	 */
	public void setError(int errorid){
		switch (errorid) {
		case 1:
			this.errorid = errorid;
			this.error = "Circuits File Not Found";
			break;
		case 2:
			this.errorid = errorid;
			this.error = "Switch File Not Found";
			break;
		default:
			this.errorid = 0;
		}
		
	}
	/**
	 * @return Returns the errorid.
	 */
	public int getErrorid() {
		return errorid;
	}
	/**
	 * @param errorid The errorid to set.
	 */
	public void setErrorid(int errorid) {
		this.errorid = errorid;
	}
	/**
	 * @return Returns the fusedoc.
	 */
	public String getFusedoc() {
		if(fusedoc != null){
			return fusedoc;	
		} else {
			return "";
		}
		
	}
	/**
	 * @param fusedoc The fusedoc to set.
	 */
	public void setFusedoc(String fusedoc) {
		this.fusedoc = fusedoc;
	}
	/**
	 * @return Returns the extension.
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension The extension to set.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	//Methods for the listners
	public void addListener(IDeltaListener listener) {
		this.listener = listener;
	}
	public void removeListener(IDeltaListener listener) {
		if(this.listener.equals(listener)) {
			this.listener = NullDeltaListener.getSoleInstance();
		}
	}
	protected void fireAdd(Object added) {
		listener.add(new DeltaEvent(added));
	}

	protected void fireRemove(Object removed) {
		listener.remove(new DeltaEvent(removed));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addChild(FBXFuse child) {
		children.add(child);
		//child.setParent(this);
	}
	public void removeChild(FBXFuse child) {
		children.remove(child);
		child.setParent(null);
	}
	public Object [] getChildren() {
		return (FBXObject [])children.toArray(new FBXObject[children.size()]);
	}
	
	public boolean hasChildren() {
		return children.size()>0;
	}

	public void setChildren(ArrayList children) {
		this.children = children;
	}

	public Node getXmlNode() {
		return xmlNode;
	}

	public void setXmlNode(Node xmlNode) {
		this.xmlNode = xmlNode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public FBXObject getParent() {
		return parent;
	}

	public void setParent(FBXObject parent) {
		this.parent = parent;
	}
	public String toString(){
		return "un_named item";
	}	
	public String getLabel(){
		return "Fusebox Item";
	}
}
