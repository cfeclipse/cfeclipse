/*
 * $Id: CFCPropertyBean.java,v 1.1 2004-11-02 06:06:25 rohanr2 Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package com.rohanclan.cfml.wizards.cfcwizard;

/**
 * This is a bean to track a property in a CFC.
 * 
 * @author cq
 * 
 * This will eventually turn out to be like this:
 * <pre>
 * <cfproperty 
 *  name="name" 
 *  type="type"
 *  required="boolean"
 *  default="default value"
 *  displayname="descriptive name"
 *  hint="extended description"
 * >
 * </pre>
 * 
 * @author Rob 
 */
public class CFCPropertyBean {
	/** the name of the cfpropery */
	private String name = "";
	/** the displayname of the cfpropery (meaning the cfml attribute) */
	private String displayName = "";
	/** extended description */
	private String hint = "";
	/** the type of this property (string, numeric, etc) */
	private String type = "";
	/** default value (if any) */
	private String defaultVal = "";
	
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
}
