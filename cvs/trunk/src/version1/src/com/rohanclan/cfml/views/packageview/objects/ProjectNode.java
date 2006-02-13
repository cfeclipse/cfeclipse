package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.rohanclan.cfml.util.CFPluginImages;

public class ProjectNode extends ComponentViewObject {
	private String image = CFPluginImages.T_APPLICATION;
	private IProject project;
	private ArrayList children;
	
	public ProjectNode(IProject project){
		this.project = project;
		//Setup the Children
		initChildren();
	}
	private void initChildren(){
		//go through the first root
		ArrayList projectchildren = new ArrayList();
		
			try {
				IResource children[] = this.project.members();
				for(int i = 0; i < children.length; i++) {
					//Now we create the packages and then add them
					//There might be some children that arent packages
					
					PackageNode packageNode = new PackageNode(children[i]);
					projectchildren.add(packageNode);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		this.children = projectchildren;
		
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
	
	public ArrayList getChildren(){
		return children;
	}
	
}
