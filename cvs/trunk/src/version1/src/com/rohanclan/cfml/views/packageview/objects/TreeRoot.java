package com.rohanclan.cfml.views.packageview.objects;

import org.eclipse.core.resources.IProject;

import com.rohanclan.cfml.util.CFPluginImages;

/**
 * This is the root node, this should have all the rest hanging off it
 * @author mark
 *
 */
public class TreeRoot extends ComponentViewObject {
	private String image = CFPluginImages.T_APPLICATION;
	private IProject project;
	
	public TreeRoot() {
		super();
	}

	public TreeRoot(String name) {
		super(name);
	}	
	public TreeRoot(IProject project){
		
	}
	
	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
