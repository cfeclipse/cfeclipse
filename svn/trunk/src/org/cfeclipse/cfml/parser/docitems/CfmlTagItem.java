/*
 * Created on Mar 18, 2004
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
package org.cfeclipse.cfml.parser.docitems;


//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Iterator;
import java.util.Set;

import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.parser.ParseError;
import org.cfeclipse.cfml.parser.exception.DuplicateAttributeException;
import org.cfeclipse.cfml.parser.exception.InvalidAttributeException;
/**
 * @author Oliver Tupman
 *
 */
public class CfmlTagItem extends TagItem {
	/**
	 * 
	 * @see org.cfeclipse.cfml.parser.docitems.DocItem#IsSane()
	 */
	public boolean IsSane() {
		Set attributes = syntax.getElementAttributes(this.itemName);
		//TODO: Warning This keeps throwing a null pointer exception with cffile, cfdirectory and cflocation. 
		
		if (attributes == null) {
			return super.IsSane();
		}
		Object[] params = attributes.toArray();
		for(int i = 0; i < params.length; i++)
		{
			
			/*
			 * Some test cases 
			 * <cffile action="read" addnewline="true" />
			 * 
			 * 
			 * 
			 * 
			 */
			
			Parameter currParam = (Parameter)params[i];
			System.out.println("attr " + currParam.getName() + " triggered?" + (currParam.isTriggered(itemAttributes) & Parameter.PARAM_REQUIRED) + " required string " + Parameter.PARAM_REQUIRED);
				
			if( ( (currParam.isTriggered(itemAttributes) & Parameter.PARAM_REQUIRED) == Parameter.PARAM_REQUIRED) && 
						!itemAttributes.containsKey(currParam.getName().toLowerCase()) )
			{
				this.parseMessages.addMessage(new ParseError(lineNumber, startPosition, endPosition, itemData,
						 "The attribute \'" + currParam.getName() + "\' is compulsory for the <" + itemName + "> tag."));
			}
		}
		return super.IsSane();
	}
	/**
	 * Determines whether the child is valid or not.
	 * <b>NB:</b> At present the default is <b>true</b>. Classes that derive from this class may 
	 * choose to change this behavour.
	 * 
	 * @see org.cfeclipse.cfml.parser.docitems.DocItem#validChildAddition(org.cfeclipse.cfml.parser.DocItem)
	 */
	public boolean validChildAddition(DocItem parentItem) {
		return true;
	}
	/**
	 * Adds a string-based name/value attribute pair to the tag
	 *
	 * @param newAttr The new attribute to add to this CFML tag item 
	 * @throws DuplicateAttributeException The attribute already exists in the attr list
	 * @throws InvalidAttributeException The attribute does not belong to this tag.
	 */
	public boolean addAttribute(AttributeItem newAttr) 
	{
		boolean addOkay = true;
		
		Tag tag = syntax.getTag(itemName);
		if (tag == null 
		        || !tag.allowsAnyAttribute()) {
			Set attributes = syntax.getFilteredAttributes(this.itemName.toLowerCase(), newAttr.getName());
			if(attributes.size() == 0)
			{
				this.parseMessages.addMessage(new ParseError(lineNumber, startPosition, endPosition, itemData,
											 "Attribute \'" + newAttr.getName() + "\' is not valid."));
				addOkay = false;	// While it's incorrect we still wish to add it to the item
			}
		}
		addOkay = super.addAttribute(newAttr) && addOkay;
		
		return addOkay;
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagItem(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
	
	
}