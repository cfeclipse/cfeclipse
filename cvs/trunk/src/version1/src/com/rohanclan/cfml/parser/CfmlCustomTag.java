/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CfmlCustomTag extends TagItem {
	/**
	 * 
	 * @see com.rohanclan.cfml.parser.DocItem#validChildAddition(com.rohanclan.cfml.parser.DocItem)
	 */
	public boolean validChildAddition(DocItem parentItem) {
		return true;
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.parser.TagItem#hasClosingTag()
	 */
	public boolean hasClosingTag() throws NullPointerException {
		return true;
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlCustomTag(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
	
	
}
