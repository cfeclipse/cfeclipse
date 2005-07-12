/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * @author OLIVER
 *
 */
public class FolderNode extends TreeParent implements IResourceTreeObject {
	private IFolder srcFolder;
	private String folderType = FolderTypes.DEFAULT;
	private QualifiedName cachedPropTypeName = null;

	public IResourceTreeObject findResourceObj(IResource res2Find) {
		IResourceTreeObject foundObj;
		
		TreeObject children[] = this.getChildren();
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof FolderNode) {
				if(((FolderNode)children[i]).getFolder() == res2Find) {
					return (FolderNode)children[i];
				}
				else {
					return ((FolderNode)children[i]).findResourceObj(res2Find);
				}
			}
		}
		
		return null;
	}
	
	private QualifiedName getPropName() {
		if(this.cachedPropTypeName == null)
			this.cachedPropTypeName = new QualifiedName("com.rohanclan.cfml", FolderTypes.RES_PROPERTY);
		
		return this.cachedPropTypeName;
	}
	private void initFolder() {
		String propVal = null;
		try {
			propVal = this.srcFolder.getPersistentProperty(getPropName());
		}catch(CoreException ex) {
			ex.printStackTrace();
		}
		if(propVal == null) {
			setFolderType(FolderTypes.DEFAULT);
		} else {
			setFolderType(propVal);
		}
	}
	public FolderNode(IFolder folder) {
		super(folder.getName());
		this.srcFolder = folder;
		initFolder();
	}
	
	public FolderNode(IFolder folder, String folderType) {
		super(folder.getName());
		this.srcFolder = folder;
		this.folderType = folderType;
		initFolder();
	}
	
	public String getFolderType() { 
		return this.folderType;
	}
	
	public void setFolderType(String newType) {
		this.folderType = newType;
		
		try {
			this.srcFolder.setPersistentProperty(getPropName(), this.folderType);
		} catch(CoreException ex) {
			ex.printStackTrace();
		}
	}
	
	public IFolder getFolder() {
		return this.srcFolder;
	}
}
