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
public class InvokerNode extends TreeObject {

	private String type;
	
	public void setType(String type)
	{
		Assert.isNotNull(type);
		this.type = type;
	}
	
	public String getType()
	{
		Assert.isNotNull(this.type);
		return this.type;
	}
	
	public InvokerNode(String type)
	{
		super();
		setType(type);
	}
	
	public InvokerNode() {
		super("Invoker");
	}

}
