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
public class AnnounceNode extends TreeObject {
	
	public String getCopyEventArgs() {
		return copyEventArgs;
	}
	public void setCopyEventArgs(String copyEventArgs) {
		this.copyEventArgs = copyEventArgs;
	}
	private String event;
	private String copyEventArgs;
	
	public String getName() {
		return "Announce " + this.event;
	}
	

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public AnnounceNode(String event)
	{
		super();
		setEvent(event);
	}
	/**
	 * 
	 */
	public AnnounceNode() {
		super();
		// TODO Auto-generated constructor stub
	}

}
