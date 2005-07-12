/*
 * $Id: CFCPropertyBean.java,v 1.2 2005-01-25 00:44:19 smilligan Exp $
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
	/** the name of the cfproperty */
	private String name = "";
	/** the displayname of the cfproperty (meaning the cfml attribute) */
	private String displayName = "";
	/** extended description */
	private String hint = "";
	/** the type of this property (string, numeric, etc) */
	private String type = "";
	/** default value (if any) */
	private String defaultVal = "";
	/** whether to write a getter */
	private boolean writeGetter = false;
	/** Getter access */
	private String getterAccess = "public";
	/** whether to write a setter */
	private boolean writeSetter = false;
	/** Setter access */
	private String setterAccess = "public";
	
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

	/**
	 * @return access for the getter
	 */
	public String getGetterAccess() {
        return getterAccess;
    }

	/**
	 * @param getterAccess
	 */
	public void setGetterAccess(String getterAccess) {
        this.getterAccess = getterAccess;
    }

	/**
	 * @return access for the setter
	 */
	public String getSetterAccess() {
        return setterAccess;
    }

	/**
	 * @param setterAccess
	 */
	public void setSetterAccess(String setterAccess) {
        this.setterAccess = setterAccess;
    }

	/**
	 * @return whether to write a getter method
	 */
	public boolean shouldWriteGetter() {
        return writeGetter;
    }

	/**
	 * @param writeGetter
	 */
	public void setShouldWriteGetter(boolean writeGetter) {
        this.writeGetter = writeGetter;
    }
    
	/**
	 * @return whether to write a setter method
	 */
	public boolean shouldWriteSetter() {
        return writeSetter;
    }

	/**
	 * @param writeSetter
	 */
	public void setShouldWriteSetter(boolean writeSetter) {
        this.writeSetter = writeSetter;
    }

}
