/*
 * Created on 24-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.internal.utils.Assert;


/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MachIIRoot extends TreeParent {

	public TreeParent getEventFilters() {
		return eventFilters;
	}
	public void setEventFilters(TreeParent eventFilters) {
		this.eventFilters = eventFilters;
	}
	public TreeParent getEventHandlers() {
		return eventHandlers;
	}
	public void setEventHandlers(TreeParent eventHandlers) {
		this.eventHandlers = eventHandlers;
	}
	public ListenersNode getListeners() {
		return listeners;
	}
	public void setListeners(ListenersNode listeners) {
		this.listeners = listeners;
	}
	public TreeParent getPageViews() {
		return pageViews;
	}
	public void setPageViews(TreeParent pageViews) {
		this.pageViews = pageViews;
	}
	public TreeParent getPlugins() {
		return plugins;
	}
	public void setPlugins(TreeParent plugins) {
		this.plugins = plugins;
	}
	public PropertiesNode getProperties() {
		return properties;
	}
	public void setProperties(PropertiesNode properties) {
		this.properties = properties;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
	 * This is the root of the application.
	 */
	private String appRoot;
	
	private String versionNumber;
	
	private PropertiesNode properties;
	private ListenersNode listeners;
	private TreeParent eventFilters;
	private TreeParent eventHandlers;
	private TreeParent pageViews;
	private TreeParent plugins;
	
	public void setAppRoot(String root)
	{
		this.appRoot = root;
	}
	
	public String getAppRoot()
	{
		return this.appRoot;
	}
	
	/**
	 * @param name
	 */
	public MachIIRoot() {
		super();
		this.properties = new PropertiesNode("Properties");
		this.listeners = new ListenersNode("Listeners");
		this.eventFilters = new TreeParent("Event Filters");
		this.eventHandlers = new TreeParent("Event Handlers");
		this.pageViews = new TreeParent("Page views");
		this.plugins = new TreeParent("Plugins");
		
		this.eventHandlers.setParent(this);
		this.properties.setParent(this);
		this.listeners.setParent(this);
		this.eventFilters.setParent(this);
		this.pageViews.setParent(this);
		this.plugins.setParent(this);
		this.appRoot = "";
	}

	public void setVersion(String ver)
	{
		Assert.isNotNull(ver);
		
		this.versionNumber = ver;
	}
	
	public String getVersion()
	{
		Assert.isNotNull(this.versionNumber);
		return this.versionNumber;
	}

	public void addProperty(PropertyNode newProp)
	{
		if(newProp.getName().equals("applicationRoot"))
		{
			this.appRoot = newProp.getValue();
		}
		this.properties.addChild(newProp);
	}
	
	public void addChild(TreeObject child) {
		if(child instanceof PropertiesNode)
		{
			//this.properties = (PropertiesNode)child;
		}
		else if(child instanceof ListenersNode)
		{
			this.listeners = (ListenersNode)child;
			this.listeners.setParent(this);
		}
		else if(child instanceof EventHandlerNode)
		{
			this.eventHandlers.addChild(child);
			
		}
		else if(child instanceof PluginNode)
		{
			this.plugins.addChild(child);
		}
		else if(child instanceof PageViewNode)
		{
			this.pageViews.addChild(child);
		}
		else
		{
			System.err.println("Invalid child item of type \'" + child.getClass().getName() + "\'");
		}
	}
	
	public void addEventFilter(EventFilterNode newFilter)
	{
		this.eventFilters.addChild(newFilter);
	}
	
	public TreeObject [] getChildren()
	{
		TreeObject children [] = new TreeObject[6];
		children[0] = this.properties;
		children[1] = this.listeners;
		children[2] = this.eventFilters;
		children[3] = this.eventHandlers;
		children[4] = this.pageViews;
		children[5] = this.plugins;
		return children;
	}
	
	public boolean hasChildren() {
		return true;
	}
}
