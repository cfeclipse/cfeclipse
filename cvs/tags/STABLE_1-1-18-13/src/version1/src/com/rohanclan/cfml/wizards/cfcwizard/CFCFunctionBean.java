/*
 * $Id: CFCFunctionBean.java,v 1.1 2004-11-02 06:06:25 rohanr2 Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package com.rohanclan.cfml.wizards.cfcwizard;

import java.util.LinkedHashMap;

/**
 * This is a bean to represent a function in a cfc
 * @author cq 
 * 
 * This will evenutally look like this:
 * <pre>
 * &lt;cffunction
 *  name = "methodName"
 *  returnType = "dataType"
 *  roles = "securityRoles"
 *  access = "methodAccess"
 *  output = "yes" or "no"
 *  displayName = "name"
 *  Hint = "hint text"&gt;
 * ...
 * &lt;/cffunction&gt;
 * </pre>
 * 
 * @author Rob
 */
public class CFCFunctionBean {
	/** the functions name */
	private String name = "";
	/** the functions display name */
	private String displayName = "";
	/** the functions hint */
	private String hint = "";
	/** the functions access type */
	private String access = "";
	/** the functions return type */
	private String returnType = "";
	/** the functions roles */
	private String roles = "";
	/** does this function output text */
	private boolean output = false;
	
	/** the functions arguments */
	private LinkedHashMap argumentBeans;
	
	public CFCFunctionBean(){
		argumentBeans = new LinkedHashMap();
	}
	
	/**
	 * Adds a new ArgumentBean to the argumentBean Array
	 * @param CfcArgumentBean
	 */
	public void addArgumentBean(Object index, CFCArgumentBean bean)
	{
		this.argumentBeans.put(index, bean);
	}
	
	/**
	 * @return Returns the access.
	 */
	public String getAccess() {
		return access;
	}
	
	/**
	 * @param access The access to set.
	 */
	public void setAccess(String access) {
		this.access = access;
	}
	
	/**
	 * @return Returns the arguments.
	 */
	public LinkedHashMap getArgumentBeans() {
		return argumentBeans;
	}
	
	/**
	 * @param arguments The arguments to set.
	 */
	public void setArgumentBeans(LinkedHashMap arguments) {
		this.argumentBeans = arguments;
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
	public boolean isOutput() {
		return output;
	}
	
	/**
	 * @param output The output to set.
	 */
	public void setOutput(boolean output) {
		this.output = output;
	}
	
	/**
	 * @return Returns the returnType.
	 */
	public String getReturnType() {
		return returnType;
	}
	
	/**
	 * @param returnType The returnType to set.
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	/**
	 * @return Returns the roles.
	 */
	public String getRoles() {
		return roles;
	}
	
	/**
	 * @param roles The roles to set.
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}
}
