package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

public class RootNode implements IComponentViewObject{

	private ArrayList children = new ArrayList();
	
	public IComponentViewObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParent(IComponentViewObject parent) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList getChildren() {
		return this.children;
	}

	public void setChildren(ArrayList children) {
		this.children = children;
		
	}

	public void addChild(ProjectNode child){
		this.children.add(child);
		
	}
	public boolean hasChildren() {
		return this.children.size()>0;
	}

	public String getPackageName() {
		// TODO Auto-generated method stub
		return null;
	}

}
