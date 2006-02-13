/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;



public class TreeParent extends ComponentViewObject {
	private ArrayList children;
	
	public TreeParent(String name) {
		super(name);
		children = new ArrayList();
	}
	
	public void addChild(ComponentViewObject child) {
		if(child != null){
			children.add(child);
			child.setParent(this);
		}
	
	}

	public void removeChild(ComponentViewObject child) {
		children.remove(child);
		child.setParent(null);
	}
	
	public ArrayList getChildren() {
		return children;
	}
	
	public boolean hasChildren() {
		return children.size()>0;
	}
}