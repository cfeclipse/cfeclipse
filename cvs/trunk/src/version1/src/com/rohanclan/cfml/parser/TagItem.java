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
	
	public boolean hasClosingTag()
	{
		Tag tag = syntax.getTag(itemName);
		return !tag.isSingle();
	}

}