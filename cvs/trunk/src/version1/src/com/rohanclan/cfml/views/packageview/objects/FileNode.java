/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;

import com.rohanclan.cfml.parser.docitems.TagItem;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FileNode extends TreeObject {
	private ArrayList functions = null;
	
	private IFile srcFile;
	
	public IFile getIFile() {
		return this.srcFile;
	}
	
	public FileNode(IFile file) {
		super(file.getName());
		this.srcFile = file;
		this.functions = new ArrayList();
	}

	public IFile getFile() {
		return this.srcFile;
	}
	
	public void addNodes(TagItem tag){
		this.functions.add(tag);
	}
	public TagItem []  getChildren() {
		return (TagItem [])functions.toArray(new TagItem[functions.size()]);
	}
	
	public boolean hasChildren() {
		return functions.size()>0;
	}
}
