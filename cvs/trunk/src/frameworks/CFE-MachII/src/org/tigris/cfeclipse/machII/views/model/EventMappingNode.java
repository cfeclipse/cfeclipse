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
public class EventMappingNode extends TreeObject {

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	private String event;
	private String mapping;
	
	public EventMappingNode() {
		super("Event Mapping");
	}

}
