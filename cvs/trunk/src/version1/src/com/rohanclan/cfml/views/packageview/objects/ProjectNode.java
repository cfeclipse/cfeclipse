package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

import com.rohanclan.cfml.properties.CFMLPropertyManager;
import com.rohanclan.cfml.properties.ProjectPropertyStore;
import com.rohanclan.cfml.util.CFPluginImages;

public class ProjectNode implements IComponentViewObject  {
	private String image = CFPluginImages.T_APPLICATION;
	private IProject project;
	private String packageName = "testingpath"; //This is the name of the package, set in the Project prefs
	private ArrayList children;
	
	public ProjectNode(IProject project){
		this.project = project;

		//Set up the packagename
		try {
			QualifiedName propertyName = new QualifiedName("", "componentRoot");
			packageName = project.getProject().getPersistentProperty(propertyName);
		} catch (CoreException e) {}
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
					if(children[i] instanceof IFolder){
					PackageNode packageNode = new PackageNode((IFolder)children[i]);
					packageNode.setParent(this);
					projectchildren.add(packageNode);
					}
					else if(children[i] instanceof IFile){
						IFile file = (IFile)children[i];
						if(file.getFileExtension().equalsIgnoreCase("cfc")){
							ComponentNode component = new ComponentNode(file);
							component.setParent(this);
							projectchildren.add(component);
						}
						
					}
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
		//initialise the children?
		initChildren();
		System.out.println("Finished initalising the children");
		return children;
	}
	
	public boolean hasChildren(){
		return children.size()>0;
		
	}
	
	public String toString(){
		
		return getName();
		
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getParent()
	 */
	public IComponentViewObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setParent(com.rohanclan.cfml.views.packageview.objects.IComponentViewObject)
	 */
	public void setParent(IComponentViewObject parent) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#getName()
	 */
	public String getName() {
		if(getPackageName() != null){
			return this.getPackageName();
		}
		return this.project.getName();
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setName(java.lang.String)
	 */
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setImage(java.lang.String)
	 */
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.packageview.objects.IComponentViewObject#setChildren(java.util.ArrayList)
	 */
	public void setChildren(ArrayList children) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return Returns the packageName.
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * @param packageName The packageName to set.
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}
