/*
 * $Id: CfcPropertyBean.java,v 1.1 2004-05-18 14:39:26 lafollette2010 Exp $
 * 
 * Created on 11.05.2004
 * Created by Chris Queener (cq@eggs.de)
 * 
 */
package com.rohanclan.cfml.wizards;

/*
 * @author cq 
 */
public class CfcPropertyBean {
	private String name;
	private String displayName;
	private String hint;
	private String type;
	private String defaultVal;
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
