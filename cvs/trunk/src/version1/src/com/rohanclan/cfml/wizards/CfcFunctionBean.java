/*
 * $Id: CfcFunctionBean.java,v 1.1 2004-05-18 14:39:26 lafollette2010 Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package com.rohanclan.cfml.wizards;

import java.util.Collection;
import java.util.Vector;

/*
 * @author cq 
 */
public class CfcFunctionBean {
	private String name;
	private String displayName;
	private String hint;
	private String access;
	private String returnType;
	private String roles;
	private boolean output;
	private Collection argumentBeans;
	
	/**
	 * Adds a new ArgumentBean to the argumentBean Array
	 * @param CfcArgumentBean
	 */
	public void addArgumentBean(CfcArgumentBean bean)
	{
		if(this.argumentBeans != null)
		{
			this.argumentBeans.add(bean);
		}else
		{
			this.argumentBeans = new Vector();
			this.argumentBeans.add(bean);
		}
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
	public Collection getArgumentBeans() {
		return argumentBeans;
	}
	/**
	 * @param arguments The arguments to set.
	 */
	public void setArgumentBeans(Collection arguments) {
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
