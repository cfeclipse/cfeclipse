/*
 * Created on 28-Sep-2004
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
public class PluginNode extends TreeParent {

	private String type;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public PluginNode(String name, String type)
	{
		super(name);
		setType(type);
	}
	
	/**
	 * @param name
	 */
	public PluginNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public PluginNode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
