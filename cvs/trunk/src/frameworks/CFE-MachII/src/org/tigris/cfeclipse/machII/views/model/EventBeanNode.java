/*
 * Created on 29-Sep-2004
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
public class EventBeanNode extends TreeObject {

	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String type;
	private String fields;
	
	public EventBeanNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public EventBeanNode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
