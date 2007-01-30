/*
 * Created on Mar 29, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.parser.cfmltagitems;

import org.cfeclipse.cfml.parser.ParseMessage;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;

public class CfmlTagIf extends CfmlTagItem {
	/**
	 * Adds a child item. Unlike other tags, the &lt;cfif&gt; tag it first checks to see
	 * whether there are any cfelseif's or a cfelse.
	 * 
	 * @see org.cfeclipse.cfml.parser.docitems.DocItem#addChild(org.cfeclipse.cfml.parser.DocItem)
	 */
	public boolean addChild(DocItem newItem) 
	{
		boolean addOkay = true;
		
		if(docNodes.size() != 0)
		{
			DocItem mostRecentItem = (DocItem)docNodes.get(docNodes.size()-1);
			boolean lastIsElseIf = 	mostRecentItem.getName().compareToIgnoreCase("elseif") == 0;
			boolean lastIsElse = 	mostRecentItem.getName().compareToIgnoreCase("else") == 0;
			boolean itemIsElse = 	newItem.getName().compareToIgnoreCase("else") == 0;
			boolean itemIsElseIf = 	newItem.getName().compareToIgnoreCase("elseif") == 0; 
			//
			// Tests to make sure that the user isn't trying to do something stupid.
			//if(newItem.getName().compareToIgnoreCase("else") == 0 && lastIsElse)
			if(itemIsElse && lastIsElse)
			{
				parseMessages.addMessage(new ParseMessage(newItem.getLineNumber(), newItem.getStartPosition(), newItem.getEndPosition(), newItem.getItemData(),
										 "<cfif> already has a <cfelse>"));
				addOkay = false;
			}				
			//else if(newItem.getName().compareToIgnoreCase("elseif") == 0 && lastIsElse)
			else if(itemIsElseIf && lastIsElse)
			{
			 	parseMessages.addMessage(new ParseMessage(newItem.getLineNumber(), newItem.getStartPosition(), newItem.getEndPosition(), newItem.getItemData(),
				 "<cfelseif> after <cfelse>"));
				addOkay = false;
			}
			else if((lastIsElseIf || lastIsElse) && !(itemIsElse || itemIsElseIf) )	// Is the previous item an else/elseif and the current item isn't one?		 
				mostRecentItem.addChild(newItem); 	
			else 
				addOkay = addOkay & super.addChild(newItem);	// No elses if elseifs, so we add it to the if's children.
		}
		else
			addOkay = addOkay & super.addChild(newItem);
		
		return addOkay;
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagIf(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
}
