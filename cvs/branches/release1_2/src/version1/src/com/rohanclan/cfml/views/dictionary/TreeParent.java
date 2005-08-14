/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: TreeParent.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.views.dictionary;

import java.util.ArrayList;



class TreeParent extends TreeObject {
	private ArrayList children;
	public TreeParent(String name) {
		super(name);
		children = new ArrayList();
	}
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	public TreeObject [] getChildren() {
		return (TreeObject [])children.toArray(new TreeObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
}