/*
 * Created on 27-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.model;

import java.util.ArrayList;

import org.eclipse.core.internal.utils.Assert;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EventHandlerNode extends TreeParent {

	private String access;
	
	public void setAccess(String newAccessVal)
	{
		Assert.isNotNull(newAccessVal);
		this.access = newAccessVal;
	}
	
	public String getAccess()
	{
		Assert.isNotNull(this.access);
		return this.access;
	}
	
	public EventHandlerNode(String eventName, String access)
	{
		super(eventName);
		
		Assert.isNotNull(access);
		
		this.access = access;
		
	}
	
	public EventHandlerNode(String eventName)
	{
		super(eventName);
	}
	
	/**
	 * 
	 */
	public EventHandlerNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addChild(TreeObject child) {
		super.addChild(child);
	}
}
