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

import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;

public class TreeParentNode extends TreeNode {
	
	
	private ArrayList<TreeParentNode> children;
	private Document document;
	private String CFCPath;
	private Document virtualDocument;
	
	
	public TreeParentNode(String name) {
		super(name);
		children = new ArrayList<TreeParentNode>();
	}
	
	public TreeParentNode(Element element, String type) {
		super(element, type);
		children = new ArrayList<TreeParentNode>();
	}
	public void addChild(TreeParentNode child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(TreeParentNode child) {
		children.remove(child);
		child.setParent(null);
	}
	public TreeParentNode [] getChildren() {
		return (TreeParentNode [])children.toArray(new TreeParentNode[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
	public void setDocument(Document document) {
		this.document = document;
		
	}
	public Document getDocument() {
		return document;
	}
	public String getCFCPath() {
		return CFCPath;
	}
	public void setCFCPath(String path) {
		CFCPath = path;
	}

	public Document getVirtualDocument() {
		return virtualDocument;
	}

	public void setVirtualDocument(Document virtualDocument) {
		this.virtualDocument = virtualDocument;
	}


	
}
