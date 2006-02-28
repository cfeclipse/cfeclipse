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
import org.w3c.dom.Node;

/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXCircuit implements IFBXObject{
	private ArrayList children;
	private IProject project;
	private String circuitName;
	private String circuitValue;
	private IFBXObject parent;
	private IFile circuitFile;
	private IFile switchFile;
	private boolean isRoot;
	private boolean createFolders = false;
	private String icon = PluginImages.ICON_FBX4_CIRCUIT;
	private Node xmlNode;
	

	public FBXCircuit(){
		children = new ArrayList();
	}
	
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
		if(this.xmlNode!=null){
			return this.xmlNode.getAttributes().getNamedItem("alias").getNodeValue();
		}
		return "default";
		
		//We try and return the alias
		
		
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
		this.children.add(children);
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
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getError()
	 */
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#hasError()
	 */
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setError(java.lang.String)
	 */
	public void setError(String error) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setError(int)
	 */
	public void setError(int errorid) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getErrorid()
	 */
	public int getErrorid() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setErrorid(int)
	 */
	public void setErrorid(int errorid) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getFusedoc()
	 */
	public String getFusedoc() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setFusedoc(java.lang.String)
	 */
	public void setFusedoc(String fusedoc) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getExtension()
	 */
	public String getExtension() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setExtension(java.lang.String)
	 */
	public void setExtension(String extension) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#addListener(org.cfeclipse.frameworks.fusebox4.objects.IDeltaListener)
	 */
	public void addListener(IDeltaListener listener) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#removeListener(org.cfeclipse.frameworks.fusebox4.objects.IDeltaListener)
	 */
	public void removeListener(IDeltaListener listener) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setName(java.lang.String)
	 */
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#addChild(org.cfeclipse.frameworks.fusebox4.objects.IFBXObject)
	 */
	public void addChild(IFBXObject child) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#removeChild(org.cfeclipse.frameworks.fusebox4.objects.IFBXObject)
	 */
	public void removeChild(IFBXObject child) {
		// TODO Auto-generated method stub
		
	}
	public Node getXmlNode() {
		return this.xmlNode;
	}

	public void setXmlNode(Node xmlNode) {
		this.xmlNode = xmlNode;
	}
	
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#setIcon(java.lang.String)
	 */
	public void setIcon(String icon) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox4.objects.IFBXObject#getParent()
	 */
	public IFBXObject getParent() {
		return this.parent;
	}
	public void setParent(IFBXObject parent) {
		this.parent = parent;
	}

	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
