/*
 * Created on 24-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.internal.utils.Assert;


/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ListenerNode extends TreeParent {

	private String type;
	private ArrayList invokers;
	//private ArrayList params;
	private TreeParent parameters;
	/**
	 * @param name
	 */
	public ListenerNode(String name) {
		super(name);
		this.invokers = new ArrayList();
		this.parameters = new TreeParent("Parameters");
	}
	
	public void setType(String newType)
	{
		Assert.isNotNull(newType);
		this.type = newType;
	}
	
	public String getType()
	{
		Assert.isNotNull(this.type);
		return this.type;
	}
	
	public boolean addInvoker(InvokerNode newInvoker)
	{
		Assert.isNotNull(newInvoker);
		this.invokers.add(newInvoker);
		return true;
	}
	
	
	public TreeObject[] getChildren() {
		Assert.isNotNull(this.invokers);
		Assert.isNotNull(this.parameters);
		
		ArrayList children = new ArrayList();
		children.addAll(this.invokers);
		children.add(this.parameters);
		
		TreeObject retArray [] = new TreeObject[children.size()];
		Iterator invokerIter = children.iterator();
		for(int i = 0; invokerIter.hasNext(); i++)
		{
			retArray[i] = (TreeObject)invokerIter.next();
		}
		return retArray;
	}
	
	public boolean hasChildren() {
		return true;	// Always have the parameters node available..
	}
	
	public void addParam(String name, String value)
	{
		Assert.isNotNull(name);
		Assert.isNotNull(value);
		Assert.isNotNull(this.parameters);
		
		//this.params.add(new NameValueNode(name, value));
		this.parameters.addChild(new NameValueNode(name, value));
	}
	
}
