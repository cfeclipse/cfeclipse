/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.frameworks.fusebox.views.project;

import org.eclipse.core.runtime.IAdaptable;


class TreeObject implements IAdaptable {
	private String name;
	private TreeParent parent;
	
	public TreeObject(String name) {
		this.name = name;
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