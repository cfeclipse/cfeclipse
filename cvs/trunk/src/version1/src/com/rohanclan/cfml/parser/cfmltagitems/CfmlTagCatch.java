/*
 * Created on Mar 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.cfmltagitems;

import com.rohanclan.cfml.parser.CfmlTagItem;
import com.rohanclan.cfml.parser.DocItem;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CfmlTagCatch extends CfmlTagItem {
	/**
	 * @see com.rohanclan.cfml.parser.DocItem#validChildAddition(com.rohanclan.cfml.parser.DocItem)
	 */
	public boolean validChildAddition(DocItem parentItem) {
		return parentItem.getName().compareToIgnoreCase("try") == 0;
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagCatch(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
}
