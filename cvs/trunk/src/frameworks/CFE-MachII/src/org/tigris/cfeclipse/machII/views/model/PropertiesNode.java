/*
 * Created on 24-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.model;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PropertiesNode extends TreeParent {

	/**
	 * @param name
	 */
	public PropertiesNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public PropertiesNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addChild(TreeObject child) {
		if(!(child instanceof PropertyNode))
			return;
		
		super.addChild(child);
	}
}
