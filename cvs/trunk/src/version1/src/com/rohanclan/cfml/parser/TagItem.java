/*
 * Created on Mar 21, 2004
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

import com.rohanclan.cfml.parser.DocItem;
import com.rohanclan.cfml.parser.exception.*;
import com.rohanclan.cfml.dictionary.*;

import java.util.HashMap;
import java.util.Set;

/**
 * 
 * @author Oliver Tupman
 *
 * A tag-based item in the document.
 *
 */
public class TagItem extends DocItem {
	
	protected HashMap attributes;
	/** Optional. 
	   *  
	   *  The matching item for this DocItem. For example CFML tags quite often have a matching closing tag, as do HTML tags. One example:
	   *  
	   *  <cfoutput>
	   *    #someVar#
	   *  </cfoutput>
	   *  
	   *  Therefore the DocItem representing the <cfoutput> would have a matchingItem that points (can you do this in Java???) to the closing DocItem, whereas the closing DocItem would have the opening one as the matchingItem.
	   */

	public DocItem matchingItem;

	/**
	 * Adds an attribute to the document
	 * 
	 * @param attrName - name
	 * @param attrValue - value
	 * @throws DuplicateAttributeException - attribute already exists
	 * @throws InvalidAttributeException - attribute doesn't belong to this tag
	 */
	public void addAttribute(String attrName, String attrValue) 
				throws DuplicateAttributeException, InvalidAttributeException
	{
		if(attributes.containsKey(attrName))
		{
			DuplicateAttributeException excep = new DuplicateAttributeException(attrName, attrValue, lineNumber);
			throw(excep);
		}
		
		attributes.put(attrName, attrValue);
	}

	public void addAttributes(HashMap attributes) 
				throws DuplicateAttributeException, InvalidAttributeException
	{
		Set keySet = attributes.keySet();
		Object [] keys = keySet.toArray();
		for(int i = 0; i < keys.length; i++)
		{
			String key = (String)keys[i];
			addAttribute(key, (String)attributes.get(key));
		}
	}
	
	public DocItem getMatchingItem() {
		return matchingItem;
	}
	
	public TagItem(int line, int startDocPos, int endDocPos, String name)
	{
		super(line, startDocPos, endDocPos, name);
		attributes = new HashMap();
	}
	
	public boolean hasClosingTag() throws NullPointerException 
	{
		Tag tag = syntax.getTag(itemName);
		if(tag == null)
		{
			System.err.println("TagItem::hasClosingTag() - The tag I've retrieved is null! Tag name is \'" + itemName + "\'");
			//
			// Should really raise an exception?
			
			throw new NullPointerException("Tag for " + itemName + " is null. Probably doesn't exist!");
		}
		return !tag.isSingle();
	}

}