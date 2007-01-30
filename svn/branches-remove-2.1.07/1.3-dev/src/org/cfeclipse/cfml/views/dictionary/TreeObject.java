/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: TreeObject.java
 * Description	:
 * 
 */
package org.cfeclipse.cfml.views.dictionary;

import org.eclipse.core.runtime.IAdaptable;


class TreeObject implements IAdaptable {
	private String name;
	private TreeParent parent;
	private String type;
	
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
    /**
     * @return Returns the type.
     */
    protected String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    protected void setType(String type) {
        this.type = type;
    }
}