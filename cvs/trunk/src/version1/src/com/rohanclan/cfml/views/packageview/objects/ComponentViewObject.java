
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import com.rohanclan.cfml.util.CFPluginImages;

/**
 * @author markd
 * This is a the base class that is used to create component view objects
 * It has child nodes and parent nodes
 */
public class ComponentViewObject implements IComponentViewObject {
	private IComponentViewObject parent;
	private ArrayList children;
	private String name = ""; //The name display
	private String image = CFPluginImages.ICON_PACKAGE;
	
	
	public ComponentViewObject() {
		super();
	}
	public ComponentViewObject(String name) {
		this.name = name;
	}
	
	
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getParent()
	 */
	public IComponentViewObject getParent() {
		return parent;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setParent(com.rohanclan.cfml.views.packageview.objects.IComponentViewObject)
	 */
	public void setParent(IComponentViewObject parent) {
		this.parent = parent;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getName()
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getImage()
	 */
	public String getImage() {
		return image;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setImage(java.lang.String)
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#toString()
	 */
	public String toString(){
		return getName();
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getChildren()
	 */
	public ArrayList getChildren() {
		return children;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setChildren(java.util.ArrayList)
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}
	
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#hasChildren()
	 */
	public boolean hasChildren(){
		return children.size()>0;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getPackageName()
	 */
	public String getPackageName() {
		return getName();
	}
	
}
