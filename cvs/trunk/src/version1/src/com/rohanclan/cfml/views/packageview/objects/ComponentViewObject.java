
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import com.rohanclan.cfml.util.CFPluginImages;

/**
 * @author markd
 * This is a the base class that is used to create component view objects
 * It has child nodes and parent nodes
 */
public class ComponentViewObject {
	private ComponentViewObject parent;
	private String name; //The name display
	private String image = CFPluginImages.ICON_PACKAGE;
	
	
	public ComponentViewObject() {
		super();
	}
	public ComponentViewObject(String name) {
		this.name = name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String toString(){
		return getName();
	}
	
}
