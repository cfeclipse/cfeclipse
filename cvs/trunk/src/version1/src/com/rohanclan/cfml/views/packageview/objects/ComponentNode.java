package com.rohanclan.cfml.views.packageview.objects;

import org.eclipse.core.resources.IFile;

/**
 * @author mark
 * This object represents a CFC. So we should pass in the file itself, from 
 * which we can derive the rest
 */
public class ComponentNode extends ComponentViewObject {
	private String name;
	private IFile file;
	
	
	
	public ComponentNode() {
		super();
	}

	public ComponentNode(IFile cfcfile){
		this.file = cfcfile;
		this.name = cfcfile.getName();
	}
	
	public ComponentNode(String name) {
		super(name);
	}

	public String getName() {
		return name;
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
	
}
