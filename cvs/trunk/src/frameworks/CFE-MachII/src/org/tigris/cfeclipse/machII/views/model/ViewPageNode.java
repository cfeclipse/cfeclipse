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
public class ViewPageNode extends TreeObject {

	public String getName() {
		return "page-view " + super.getName();		
	}
	public String getContentKey() {
		return contentKey;
	}
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	private String contentKey;
	
	
	
	/**
	 * @param name
	 */
	public ViewPageNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public ViewPageNode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
