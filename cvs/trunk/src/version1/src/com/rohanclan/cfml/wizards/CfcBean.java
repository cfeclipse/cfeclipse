/*
 * $Id: CfcBean.java,v 1.1 2004-05-18 14:39:26 lafollette2010 Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package com.rohanclan.cfml.wizards;

/*
 * @author cq 
 */
public class CfcBean {
	
	// component properties
	private String name;
	private String path;
	private String extendCfc;
	private String hint;
	private String displayName;
	// cfc property tags
	private CfcPropertyBean[] propertyBeans;
	// Function Tags
	private CfcFunctionBean[] functionBeans;
	
	// Constructors
	public CfcBean(){};
	public CfcBean(String name,
			String path,
			String extendCfc,
			String hint,
			String displayName)
	{
		this.name = name;
		this.path = path;
		this.extendCfc = extendCfc;
		this.hint = hint;
		this.displayName = displayName;
	}
	
	
	/**
	 * Adds a new PropertyBean to the PropertyBean Array
	 * @param CfcPropertyBean
	 */
	public void addPropertyBean(CfcPropertyBean bean)
	{
		if(this.propertyBeans != null
				&& this.propertyBeans.length != 0)
		{
			this.propertyBeans[this.propertyBeans.length - 1] = bean;
		}else if(this.propertyBeans.length == 0)
		{
			this.propertyBeans[0] = bean;
		}
	}
	
	/**
	 * Adds a new FunctionBean to the FunctionBean Array
	 * @param CfcFunctionBean
	 */
	public void addFunctionBean(CfcFunctionBean bean)
	{
		if(this.functionBeans != null
				&& this.functionBeans.length != 0)
		{
			this.functionBeans[this.functionBeans.length - 1] = bean;
		}else if(this.functionBeans.length == 0)
		{
			this.functionBeans[0] = bean;
		}
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
	public String getExtendCfc() {
		return extendCfc;
	}
	/**
	 * @param extendCfc The extendCfc to set.
	 */
	public void setExtendCfc(String extendCfc) {
		this.extendCfc = extendCfc;
	}
	/**
	 * @return Returns the functionBeans.
	 */
	public CfcFunctionBean[] getFunctionBeans() {
		return functionBeans;
	}
	/**
	 * @param functionBeans The functionBeans to set.
	 */
	public void setFunctionBeans(CfcFunctionBean[] functionBeans) {
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
	public CfcPropertyBean[] getPropertyBeans() {
		return propertyBeans;
	}
	/**
	 * @param propertyBeans The propertyBeans to set.
	 */
	public void setPropertyBeans(CfcPropertyBean[] propertyBeans) {
		this.propertyBeans = propertyBeans;
	}
}
