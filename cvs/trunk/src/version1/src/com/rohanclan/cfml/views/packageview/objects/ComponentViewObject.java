
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

/**
 * @author markd
 * This is a the base class that is used to create component view objects
 * It has child nodes and parent nodes
 */
public class ComponentViewObject {
	private ArrayList children;
	private ComponentViewObject parent;
	
	/**
	 * @return Returns the children.
	 */
	public ArrayList getChildren() {
		return children;
	}
	/**
	 * @param children The children to set.
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}
	
	/**
	 * @param child Child to add to the collection
	 */
	public void addChild(ComponentViewObject child){
		children.add(child);
	}
	
	/**
	 * @return Returns the parent.
	 */
	public ComponentViewObject getParent() {
		return parent;
	}
	/**
	 * @param parent The parent to set.
	 */
	public void setParent(ComponentViewObject parent) {
		this.parent = parent;
	}
	
}
