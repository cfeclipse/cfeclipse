package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import com.rohanclan.cfml.parser.docitems.TagItem;
import com.rohanclan.cfml.util.CFPluginImages;

public class ArgumentNode implements IComponentViewObject{
	private String image  = CFPluginImages.ICON_ATTR;
	private String name;
	private String argType;
	private TagItem argumentTag;
	private IComponentViewObject parent;
		
	public ArgumentNode(TagItem argumentTag){
		this.argumentTag = argumentTag;
		this.name = argumentTag.getAttributeValue("name");
		this.argType = argumentTag.getAttributeValue("type");
	}
	
	public IComponentViewObject getParent() {
		return this.parent;
	}

	public void setParent(IComponentViewObject parent) {
		this.parent = parent;
	}
	


	public String getName() {
			
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList getChildren() {
		return null;
	}

	public void setChildren(ArrayList children) {
	}

	public boolean hasChildren() {
		return false;
	}

	public String getPackageName() {
		return getName();
	}
	public String toString(){
		String retName = this.argType + " " + this.name;
		
		return retName;
	}

}
