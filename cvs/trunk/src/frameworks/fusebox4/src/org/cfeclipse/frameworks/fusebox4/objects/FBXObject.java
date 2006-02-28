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
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;
import org.w3c.dom.Node;

/**
 * @author Mark Drew
 * 16-Jan-2005
 * fusebox3cfe2
 * Description: This is a generic object that you can use in the tree by extending it
 *  It provides methods to fields that are usually got and set so that you dont have to do it for each item
 * 
 */
public class FBXObject implements IFBXObject {
	private ArrayList children;
	private String error;
	private int version = 3;
	private int errorid = 0;
	private String fusedoc;
	private String extension = "cfm";
	//This is a Node that can be assigned (XML node) that is for Fusebox 4 +
	private Node xmlNode;
	private String icon = PluginImages.ICON_FUSEBOX4; //Default Icon
	private IFBXObject parent;

	//Change Listners
	protected IDeltaListener listener = NullDeltaListener.getSoleInstance();
	private String name;
	
	public FBXObject(){
		super();
		this.children = new ArrayList();
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
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getVersion()
	 */
	public int getVersion() {
		return version;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setVersion(int)
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getError()
	 */
	public String getError() {
		return error;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#hasError()
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
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setError(java.lang.String)
	 */
	public void setError(String error) {
		this.error = error;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setError(int)
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
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getErrorid()
	 */
	public int getErrorid() {
		return errorid;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setErrorid(int)
	 */
	public void setErrorid(int errorid) {
		this.errorid = errorid;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getFusedoc()
	 */
	public String getFusedoc() {
		if(fusedoc != null){
			return fusedoc;	
		} else {
			return "";
		}
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setFusedoc(java.lang.String)
	 */
	public void setFusedoc(String fusedoc) {
		this.fusedoc = fusedoc;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getExtension()
	 */
	public String getExtension() {
		return extension;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setExtension(java.lang.String)
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	//Methods for the listners
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#addListener(org.cfeclipse.frameworks.fusebox4.objects.IDeltaListener)
	 */
	public void addListener(IDeltaListener listener) {
		this.listener = listener;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#removeListener(org.cfeclipse.frameworks.fusebox4.objects.IDeltaListener)
	 */
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
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getName()
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#addChild(org.cfeclipse.frameworks.fusebox4.objects.FBXFuse)
	 */
	public void addChild(IFBXObject child) {
		children.add(child);
		//child.setParent(this);
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#removeChild(org.cfeclipse.frameworks.fusebox4.objects.FBXFuse)
	 */
	public void removeChild(IFBXObject child) {
		children.remove(child);
		child.setParent(null);
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getChildren()
	 */
	public Object [] getChildren() {
		return (IFBXObject [])children.toArray(new IFBXObject[children.size()]);
	}
	
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#hasChildren()
	 */
	public boolean hasChildren() {
		return this.children.size()>0;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setChildren(java.util.ArrayList)
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getXmlNode()
	 */
	public Node getXmlNode() {
		return xmlNode;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setXmlNode(org.w3c.dom.Node)
	 */
	public void setXmlNode(Node xmlNode) {
		this.xmlNode = xmlNode;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getIcon()
	 */
	public String getIcon() {
		return icon;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setIcon(java.lang.String)
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getParent()
	 */
	public IFBXObject getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setParent(org.cfeclipse.frameworks.fusebox4.objects.IFBXObject)
	 */
	public void setParent(IFBXObject parent) {
		this.parent = parent;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#toString()
	 */
	public String toString(){
		return this.name;
	}	
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getLabel()
	 */
	public String getLabel(){
		return "Fusebox Item";
	}
}
