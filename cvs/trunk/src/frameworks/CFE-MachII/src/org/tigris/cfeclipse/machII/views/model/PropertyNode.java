/*
 * Created on 24-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.model;

import org.eclipse.core.internal.utils.Assert;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PropertyNode extends TreeObject {

	private String value = "";
	/**
	 * @param name
	 */
	public PropertyNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public PropertyNode(String name, String value)
	{
		super(name);
		setValue(value);
	}
	
	public void setValue(String newVal)
	{
		Assert.isNotNull(newVal);
		
		this.value = newVal;
	}
	
	public String getValue()
	{
		return this.value;
	}

	/**
	 * 
	 */
	public PropertyNode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
