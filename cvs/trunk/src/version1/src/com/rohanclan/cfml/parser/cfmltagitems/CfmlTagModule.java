/*
 * Created on Apr 21, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.cfmltagitems;

import com.rohanclan.cfml.parser.CfmlTagItem;
import com.rohanclan.cfml.parser.exception.DuplicateAttributeException;
import com.rohanclan.cfml.parser.exception.InvalidAttributeException;

/**
 * @author Oliver Tupman
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CfmlTagModule extends CfmlTagItem {
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagModule(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.parser.TagItem#addAttribute(java.lang.String, java.lang.String)
	 */
	public void addAttribute(String attrName, String attrValue)
			throws DuplicateAttributeException, InvalidAttributeException {
		
	}
}
