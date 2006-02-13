package com.rohanclan.cfml.views.packageview.objects;

import org.eclipse.core.resources.IResource;

import com.rohanclan.cfml.util.CFPluginImages;

public class PackageNode extends ComponentViewObject {
	private String name;
	private String image = CFPluginImages.ICON_PACKAGE;
	private IResource folder;
	
	public PackageNode(){
		super();
	}
	public PackageNode(IResource folder){
		this.folder = folder;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		String returnName = this.getParent().getName() + "." + this.name;
		return returnName;
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
