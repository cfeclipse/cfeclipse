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
public class NotifyNode extends TreeObject {
	private String listener;
	private String method;
	private String resultKey;

	public String getListener() {
		return listener;
	}
	public void setListener(String listener) {
		this.listener = listener;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getResultKey() {
		return resultKey;
	}
	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
	/**
	 * 
	 */
	public NotifyNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return "Notify " + this.listener + ":" + this.method;
	}
}
