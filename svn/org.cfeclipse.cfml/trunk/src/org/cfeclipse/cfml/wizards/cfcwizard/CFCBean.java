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
	private String path;
	private String extendCfc;
	private String hint;
	private String displayName;
	
	// cfc property tags
	private List propertyBeans;
	
	// Function Tags
	private List functionBeans;
	
	// Constructors
	public CFCBean(){
		propertyBeans = new ArrayList();
		functionBeans = new ArrayList();
	}
	
	public CFCBean(String name, String path, String extendCfc, String hint, String displayName)
	{
		this();
		
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
	 * @param propertyBeans The propertyBeans to set.
	 */
	public void setPropertyBeans(List propertyBeans) {
		this.propertyBeans = propertyBeans;
	}
}
