/*
 * Created on 27-Sep-2004
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
public class NameValueNode extends TreeObject {
	private String value;
	/**
	 * @param name
	 */
	public NameValueNode(String name, String type) {
		super(name);
		setValue(type);
	}

	/**
	 * 
	 */
	public NameValueNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setValue(String value)
	{
		Assert.isNotNull(value);
		this.value = value;
	}
	
	public String getValue()
	{
		Assert.isNotNull(this.value);
		return this.value;
	}
}
