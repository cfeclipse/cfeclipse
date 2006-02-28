/*
 * Created on 28-Feb-2006
 * Copyright (c) 2006 markd
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox4.util.Utils;
import org.w3c.dom.Node;

/**
 * @author markd
 *
 */
public class FBXCircuits implements IFBXObject{

	private String name = "circuits";
	private IFBXObject parent;
	private ArrayList children;
	private Node xmlNode;
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getError()
	 */
	public FBXCircuits(){
		this.children = new ArrayList();
	}
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#hasError()
	 */
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setError(java.lang.String)
	 */
	public void setError(String error) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setError(int)
	 */
	public void setError(int errorid) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getErrorid()
	 */
	public int getErrorid() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setErrorid(int)
	 */
	public void setErrorid(int errorid) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getFusedoc()
	 */
	public String getFusedoc() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setFusedoc(java.lang.String)
	 */
	public void setFusedoc(String fusedoc) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getExtension()
	 */
	public String getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setExtension(java.lang.String)
	 */
	public void setExtension(String extension) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#addListener(org.cfeclipse.frameworks.fusebox4.objects.IDeltaListener)
	 */
	public void addListener(IDeltaListener listener) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#removeListener(org.cfeclipse.frameworks.fusebox4.objects.IDeltaListener)
	 */
	public void removeListener(IDeltaListener listener) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return this.name;
	}
	public String toString(){
		Utils.println(this.xmlNode.toString());
		return getName() + "(" + this.children.size() + ")";
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
		
	}

	
	public void addChild(IFBXObject child) {
		this.children.add(child);
		
	}

	public void removeChild(IFBXObject child) {
		this.children.remove(child);
		
	}

	public Object[] getChildren() {
		
		return children.toArray();
	}

	public boolean hasChildren() {
		return this.children.size()>0;
	}

	public void setChildren(ArrayList children) {
		this.children = children;
	}

	public Node getXmlNode() {
		return this.xmlNode;
	}

	public void setXmlNode(Node xmlNode) {
		this.xmlNode = xmlNode;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getIcon()
	 */
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setIcon(java.lang.String)
	 */
	public void setIcon(String icon) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getParent()
	 */
	public IFBXObject getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setParent(org.cfeclipse.frameworks.fusebox4.objects.IFBXObject)
	 */
	public void setParent(IFBXObject parent) {
		this.parent = parent;
		
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getLabel()
	 */
	public String getLabel() {
		return this.name;
	}

}
