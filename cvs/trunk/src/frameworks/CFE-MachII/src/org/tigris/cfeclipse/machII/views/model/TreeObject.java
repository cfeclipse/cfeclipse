/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.Assert;


public class TreeObject implements IAdaptable {
	private String name;
	private TreeParent parent;
	
	protected TreeObject(String name) {
		this.name = name;
	}

	public TreeObject()
	{
		this.name = "";
	}
	
	public void setName(String newName)
	{
		Assert.isNotNull(this.name);
		Assert.isNotNull(newName);
		this.name = newName;
	}
	
	public String getName() {
		return name;
	}
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	public TreeParent getParent() {
		return parent;
	}
	public String toString() {
		return getName();
	}
	public Object getAdapter(Class key) {
		return null;
	}
}