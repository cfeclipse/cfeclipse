package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;

import com.rohanclan.cfml.util.CFPluginImages;

/**
 * @author mark
 * This object represents a CFC. So we should pass in the file itself, from 
 * which we can derive the rest
 */
public class ComponentNode implements IComponentViewObject {
	private String name;
	private IFile file;
	private ArrayList children; //functions in this Component
	private String image = CFPluginImages.ICON_CLASS;
	private IComponentViewObject parent;
	
	public ComponentNode(IFile cfcfile){
		this.file = cfcfile;
		this.name = cfcfile.getName();
		this.children = new ArrayList();
	}
	
	public ComponentNode(String name) {
		this.name = name;
	}

	public String getName() {
		//TODO: Remove the .cfc
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

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
	
	public boolean hasChildren(){
		return false;
	}
	
	public String toString(){
		return getName();
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getParent()
	 */
	public IComponentViewObject getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setParent(com.rohanclan.cfml.views.packageview.objects.IComponentViewObject)
	 */
	public void setParent(IComponentViewObject parent) {
	 this.parent = parent;
		
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getImage()
	 */
	public String getImage() {
		return this.image;
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setImage(java.lang.String)
	 */
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getPackageName()
	 */
	public String getPackageName() {
		String packageName = this.parent.getPackageName() + "." + getName();
		return packageName;
	}
}
