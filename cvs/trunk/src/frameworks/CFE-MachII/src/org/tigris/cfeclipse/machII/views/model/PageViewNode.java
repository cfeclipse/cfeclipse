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
public class PageViewNode extends TreeParent {

	public String getName() {
		return "PageView: \'" + super.getName() + "\'";
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	private String page;
	
	public PageViewNode(String name, String page)
	{
		super(name);
		setPage(page);
	}
	
	/**
	 * @param name
	 */
	public PageViewNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public PageViewNode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
