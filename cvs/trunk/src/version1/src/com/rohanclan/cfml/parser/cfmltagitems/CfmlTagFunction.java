/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.cfmltagitems;

import com.rohanclan.cfml.parser.CfmlTagItem;
import com.rohanclan.cfml.parser.ParseError;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CfmlTagFunction extends CfmlTagItem {
	/**
	 * @see com.rohanclan.cfml.parser.DocItem#IsSane()
	 * @return <code>true</code> if sane, <code>false</code> otherwise
	 */
	public boolean IsSane() {
		boolean retval = true;
		
		if(!attributes.containsKey("name") || ((String)attributes.get("name")).length() == 0)
		{
			addParseMessage(new ParseError(lineNumber, startPosition, endPosition, itemData,
							"<cffunction> must have a name."));
			retval = false;
		}

		return retval;
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagFunction(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
}
