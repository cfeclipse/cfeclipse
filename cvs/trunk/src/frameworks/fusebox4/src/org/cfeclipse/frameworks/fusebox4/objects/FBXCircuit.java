/*
 * Created on Dec 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox4.util.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXCircuit extends FBXObject{
	private ArrayList children;
	private IProject project;
	private String circuitName;
	private String circuitValue;
	private FBXApplication parent;
	private IFile circuitFile;
	private IFile switchFile;
	private boolean isRoot;
	private boolean createFolders = false;
	private String icon = PluginImages.ICON_FBX4_CIRCUIT;
	

	
	
	public FBXCircuit(String name) {
		children = new ArrayList();
	}
	public FBXCircuit(String name, String value) {
		setCircuitName(name);
		setCircuitValue(value);
		children = new ArrayList();
	}
	
	
	
	
	
	public String getIcon() {
		// TODO Auto-generated method stub
		return this.icon;
	}
	public void addFuseaction(String fuseactionname){
		Utils.println("Create a fuseaction with name " + fuseactionname + " in " + this.circuitFile.getFullPath());
		
	}
	
	
	
	
	/*
	 * Most of the actions below are just to add the tree
	 */
	//Here we can add an item to it
	public void addChild(FBXFuseAction child) {
		children.add(child);
		child.setParent(this);
	}
	
	
	public void addChild(XFAFolder child){
		children.add(child);
		child.setParent(this);
	}
	
	public void removeChild(FBXFuseAction child) {
		children.remove(child);
		child.setParent(null);
	}
	
	public Object [] getChildren() {
		return children.toArray();
	}
	
	public boolean hasChildren() {
			return children.size()>0;
	}
	/**
	 * @param parent The parent to set.
	 */
	public void setParent(FBXApplication parent) {
		this.parent = parent;
	}
	/**
	 * @return Returns the circuitNalue.
	 */
	public String getCircuitValue() {
		return circuitValue;
	}
	/**
	 * @param circuitNalue The circuitNalue to set.
	 */
	public void setCircuitValue(String circuitNalue) {
		this.circuitValue = circuitNalue;
	}
	/**
	 * @return Returns the circuitName.
	 */
	public String getCircuitName() {
		return circuitName;
	}
	/**
	 * @return Returns the circuitName.
	 */
	public String getName() {
		if(hasError()){
			return circuitName + " " + getError();
		} else {
			return circuitName;
		}
		
	}
	public String toString(){
		return getName();
	}
	/**
	 * @param circuitName The circuitName to set.
	 */
	public void setCircuitName(String circuitName) {
		this.circuitName = circuitName;
	}
	/**
	 * @return Returns the isRoot.
	 */
	public boolean isRoot() {
		return isRoot;
	}
	/**
	 * @param isRoot The isRoot to set.
	 */
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	/**
	 * @param children The children to set.
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}
	public void addChildren(ArrayList children){
		this.children.addAll(children);
	}
	/**
	 * @return Returns the project.
	 */
	public IProject getProject() {
		return project;
	}
	/**
	 * @param project The project to set.
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * @return Returns the circuitFile.
	 */
	public IFile getCircuitFile() {
		return circuitFile;
	}
	/**
	 * @param circuitFile The circuitFile to set.
	 */
	public void setCircuitFile(IFile circuitFile) {
		this.circuitFile = circuitFile;
	}
	
	/**
	 * @return Returns the switchFile.
	 */
	public IFile getSwitchFile() {
		
	
		return switchFile;
	}
	/**
	 * @param switchFile The switchFile to set.
	 */
	public void setSwitchFile(IFile switchFile) {
		this.switchFile = switchFile;
	}
	/**
	 * @return Returns the version.
	 */
	/**
	 * @return Returns the createFolders.
	 */
	public boolean isCreateFolders() {
		return createFolders;
	}
	/**
	 * @param createFolders The createFolders to set.
	 */
	public void setCreateFolders(boolean createFolders) {
		this.createFolders = createFolders;
	}
}
