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
package com.rohanclan.cfml.parser;

import com.rohanclan.cfml.parser.exception.*;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Set;
/**
 * @author Oliver Tupman
 *
 */
public class CfmlTagItem extends TagItem {
	/**
	 * Determines whether the child is valid or not.
	 * <b>NB:</b> At present the default is <b>true</b>. Classes that derive from this class may 
	 * choose to change this behavour.
	 * 
	 * @see com.rohanclan.cfml.parser.DocItem#validChildAddition(com.rohanclan.cfml.parser.DocItem)
	 */
	public boolean validChildAddition(DocItem parentItem) {
		return true;
	}
	/**
	 * Adds a string-based name/value attribute pair to the tag
	 * 
	 * @param attrName - the name of the attribute
	 * @param attrValue - the value for the attribute
	 * @throws DuplicateAttributeException - The attribute already exists in the attr list
	 * @throws InvalidAttributeException - The attribute does not belong to this tag.
	 */
	public boolean addAttribute(String attrName, String attrValue) 
	{
		boolean addOkay = true;
		Set attributes = syntax.getFilteredAttributes(itemName, attrName);
		if(attributes.size() == 0)
		{
			parseMessages.addMessage(new ParseError(lineNumber, startPosition, endPosition, itemData,
										 "Attribute \'" + attrName + "\' is not valid."));
			addOkay = false;
		}
		
		addOkay = super.addAttribute(attrName, attrValue) && addOkay;
		
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
