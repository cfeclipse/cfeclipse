/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview;

import org.eclipse.core.resources.IFile;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FileNode extends TreeObject {
	
	private IFile srcFile;
	
	public IFile getIFile() {
		return this.srcFile;
	}
	
	public FileNode(IFile file) {
		super(file.getName());
		this.srcFile = file;
	}

	public IFile getFile() {
		return this.srcFile;
	}
}
