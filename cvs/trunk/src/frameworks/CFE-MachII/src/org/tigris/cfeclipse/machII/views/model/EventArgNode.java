/*
 * Created on 27-Sep-2004
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
public class EventArgNode extends TreeObject {
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	private String value;
	private String variableName;
	
	public EventArgNode(String name, String value)
	{
		super(name);
		setValue(value);
	}
	
	/**
	 * @param name
	 */
	public EventArgNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public EventArgNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
