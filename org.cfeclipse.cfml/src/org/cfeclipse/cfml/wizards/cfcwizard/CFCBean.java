/*
 * $Id: CFCBean.java,v 1.1 2004/11/02 06:06:25 rohanr2 Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package org.cfeclipse.cfml.wizards.cfcwizard;

import java.util.ArrayList;
import java.util.List;

/*
 * @author cq 
 */
public class CFCBean {
	
	// component properties
	private String name;
	private String style;
	private String path;
	private String extendCfc;
	private String hint;
	private String displayName;
	private String output;
	private String persistent;
	private String accessors;
	
	// cfc property tags
	private List propertyBeans;
	
	// Function Tags
	private List functionBeans;
	
	// Constructors
	public CFCBean(){
		this("", "", "", "", "", "", "", "");
	}
	
	/**
	 * 
	 * @param name
	 * @param path
	 */
	public CFCBean(String name, String path) {
		this(name, path, "", "", "", "", "", "");
	}

	/**
	 * 
	 * @param name
	 * @param path
	 * @param extendCfc
	 * @param hint
	 * @param displayName
	 * @param output
	 * @param accessors
	 */
	public CFCBean(String name, String path, String extendCfc, String hint, String displayName, String output, String persistent,
			String accessors)
	{
		propertyBeans = new ArrayList();
		functionBeans = new ArrayList();
		
		this.name			= name;
		this.path			= path;
		this.extendCfc		= extendCfc;
		this.hint			= hint;
		this.displayName	= displayName;
		this.output			= output;
		this.accessors		= accessors;
	}
		
	/**
	 * Adds a new PropertyBean to the PropertyBean Array
	 * @param CfcPropertyBean
	 */
	public void addPropertyBean(CFCPropertyBean bean)
	{
		propertyBeans.add(bean);
	}
	
	/**
	 * Adds a new FunctionBean to the FunctionBean Array
	 * @param CfcFunctionBean
	 */
	public void addFunctionBean(CFCFunctionBean bean)
	{
		functionBeans.add(bean);
	}
	
	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName The displayName to set.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return Returns the extendCfc.
	 */
	public String getExtendCFC() {
		return extendCfc;
	}
	
	/**
	 * @param extendCfc The extendCfc to set.
	 */
	public void setExtendCFC(String extendCfc) {
		this.extendCfc = extendCfc;
	}
	
	/**
	 * @return Returns the functionBeans.
	 */
	public List getFunctionBeans() {
		return functionBeans;
	}
	
	/**
	 * @param functionBeans The functionBeans to set.
	 */
	public void setFunctionBeans(List functionBeans) {
		this.functionBeans = functionBeans;
	}
	
	/**
	 * @return Returns the hint.
	 */
	public String getHint() {
		return hint;
	}
	
	/**
	 * @param hint The hint to set.
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return Returns the output.
	 */
	public String getOutput() {
		return output;
	}
	
	/**
	 * @param output The output to set.
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * @return Returns the propertyBeans.
	 */
	public List getPropertyBeans() {
		return propertyBeans;
	}
	
	/**
	 * @return Returns the style type (cfscript or tags).
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Set the style type (cfscript or tags).
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return Returns the persistence setting.
	 */
	public String getPersistent() {
		return persistent;
	}

	/**
	 * Set persistence (string "true" or "false")
	 * 
	 * @param persistent
	 */
	public void setPersistent(String persistent) {
		this.persistent = persistent;
	}

	/**
	 * Set persistence (boolean)
	 * 
	 * @param persistent
	 */
	public void setPersistent(boolean persistent) {
		this.persistent = Boolean.toString(persistent);
	}

	/**
	 * @return Returns the accessors setting.
	 */
	public String getAccessors() {
		return accessors;
	}

	/**
	 * Set accessors (string "true" or "false")
	 * 
	 * @param accessors
	 */
	public void setAccessors(String accessors) {
		this.accessors = accessors;
	}

	/**
	 * Set accessors (boolean)
	 * 
	 * @param accessors
	 */
	public void setAccessors(boolean accessors) {
		this.accessors = Boolean.toString(accessors);
	}

	/**
	 * @param propertyBeans
	 *            The propertyBeans to set.
	 */
	public void setPropertyBeans(List propertyBeans) {
		this.propertyBeans = propertyBeans;
	}

	public boolean hasProperties() {
		return !propertyBeans.isEmpty();
	}

	public boolean hasFunctions() {
		return !functionBeans.isEmpty();
	}

}
