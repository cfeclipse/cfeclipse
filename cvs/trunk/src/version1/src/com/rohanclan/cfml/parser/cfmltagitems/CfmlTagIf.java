/*
 * Created on Mar 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.cfmltagitems;

import com.rohanclan.cfml.parser.CfmlTagItem;
import com.rohanclan.cfml.parser.DocItem;
import com.rohanclan.cfml.parser.exception.InvalidChildItemException;

/**
 * @author ollie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CfmlTagIf extends CfmlTagItem {
	/**
	 * Adds a child item. Unlike other tags, the &lt;cfif&gt; tag it first checks to see
	 * whether there are any cfelseif's or a cfelse.
	 * 
	 * @see com.rohanclan.cfml.parser.DocItem#addChild(com.rohanclan.cfml.parser.DocItem)
	 */
	public void addChild(DocItem newItem) throws InvalidChildItemException {
		if(docNodes.size() != 0)
		{
			DocItem mostRecentItem = (DocItem)docNodes.get(docNodes.size()-1);
			boolean lastIsElseIf = 	mostRecentItem.getName().compareToIgnoreCase("elseif") == 0;
			boolean lastIsElse = 	mostRecentItem.getName().compareToIgnoreCase("else") == 0;
			boolean itemIsElse = 	newItem.getName().compareToIgnoreCase("else") == 0;
			boolean itemIsElseIf = 	newItem.getName().compareToIgnoreCase("elseif") == 0; 
			//
			// Tests to make sure that the user isn't trying to do something stupid.
			if(newItem.getName().compareToIgnoreCase("else") == 0 && lastIsElse)
				throw new InvalidChildItemException("<cfif> already has a <cfelse>", newItem.getLineNumber(), newItem.getStartPosition());
			else if(newItem.getName().compareToIgnoreCase("elseif") == 0 && lastIsElse)
				throw new InvalidChildItemException("<cfelseif> after <cfelse>", newItem.getLineNumber(), newItem.getStartPosition());
			else if((lastIsElseIf || lastIsElse) && !(itemIsElse || itemIsElseIf) )	// Is the previous item an else/elseif and the current item isn't one?		 
				mostRecentItem.addChild(newItem); 	
			else 
				super.addChild(newItem);	// No elses if elseifs, so we add it to the if's children.
		}
		else
			super.addChild(newItem);
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagIf(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
		// TODO Auto-generated constructor stub
	}
}
