/*
 * Created on May 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.cfmltagitems;

import com.rohanclan.cfml.parser.CfmlTagItem;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CfmlTagSet extends CfmlTagItem {
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagSet(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
	/**
	 * A cfset tag can appear to have attributes due to this syntax:
	 * <cfset fred = "myString"/>
	 * This isn't an attribute really, but CFScript.
	 * @see com.rohanclan.cfml.parser.TagItem#addAttribute(java.lang.String, java.lang.String)
	 * @return true - at all times.
	 */
	public boolean addAttribute(String attrName, String attrValue) {
		return true;
	}
}
