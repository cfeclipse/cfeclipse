/*
 * $Id: CFCArgumentBean.java,v 1.2 2005/01/25 00:44:19 smilligan Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package org.cfeclipse.cfml.wizards.cfcwizard;

/**
 * @author cq 
 * 
 * this will turn into this:
 * <pre>
 * &lt;cfargument 
 *  name="string" 
 *  type="data type" 
 *  required="Yes or No" 
 *  default="default value" 
 *  displayname="descriptive name"
 *  hint="extended description"
 * &gt;
 * </pre>
 */
public class CFCArgumentBean {
	private String name = "";
	private String displayName = "";
	private String hint = "";
	private String type = "";
	private boolean required = false;
	private String defaultVal = "";
	
	/**
	 * @return Returns the defaultVal.
	 */
	public String getDefaultVal() {
		return defaultVal;
	}
	/**
	 * @param defaultVal The defaultVal to set.
	 */
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
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
	 * @return Returns the required.
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required The required to set.
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
}
