/*
 * Created on Jun 25, 2004
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
package com.rohanclan.cfml.parser.xpath;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rohanclan.cfml.parser.xpath.expressions.*;


/**
 * Supply a <strong>basic</strong> search string (i.e. a single node name).
 * 
 * Currently supported are the following syntaxes:
 * "aTagName", "//aTagName"
 *
 * Please note that this WILL be changing sooner or later. If it does change
 * it will be completely different.
 *
 * @author Oliver Tupman
 */
public class XPathSearch {
	public static final String	ATTR_STARTPOS 	= "startpos";
	public static final String ATTR_ENDPOS		= "endpos";
	private HashMap validAttrs = new HashMap();
	private int matchesRequired = 0;
	
	public int getMatchesRequired() { return matchesRequired; }
	
	public XPathSearch() {
		validAttrs.put(ATTR_STARTPOS, ATTR_STARTPOS);
		validAttrs.put(ATTR_ENDPOS, ATTR_ENDPOS);
	}
	
	public boolean searchForTag = false;
	public String tagName = "";
	
	public boolean posSearch = false;
	public boolean doChildNodes = false;
	public HashMap attrSearch = new HashMap();
	
	public void setTagSearch(String tagName) {
		this.searchForTag = true;
		this.tagName = tagName;
		this.matchesRequired++;
	}
	
	private String findOperator(String inStr) {
		int opPos = -1;
		//
		// Longer operators go first so we don't accidentally match on '=' from '>='
		if((opPos = inStr.indexOf(">=")) != -1) return ComparisonType.COMP_GTE;
		if((opPos = inStr.indexOf("<=")) != -1) return ComparisonType.COMP_LTE;
		if((opPos = inStr.indexOf("!=")) != -1) return ComparisonType.COMP_NEQ;
		//
		// Shorter operators
		if((opPos = inStr.indexOf(">")) != -1) return ComparisonType.COMP_GT;
		if((opPos = inStr.indexOf("<")) != -1) return ComparisonType.COMP_LT;
		if((opPos = inStr.indexOf('=')) != -1) return ComparisonType.COMP_EQ;
		return "";
	}
	
	/**
	 * Gets the name/value pair out of an XPath attribute search fragment.
	 * I.e.: "@startPosition = 3" will return name = "startPosition" and value = "3"
	 * 
	 * @param inStr - the string to scan
	 * @param name - the name of the name/value pair
	 * @param value - the value
	 * @return <code>true</code> - success, <code>false</code> - failed to find name/value pair
	 */
	private ComparisonType getAttribute(String inStr, String name, String value) {

		int attrNameStart = inStr.indexOf('#');
		
		String operator = findOperator(inStr);
		
		if(operator.length() == 0) return null;
		
		int opStart = inStr.indexOf(operator);
		
		if(attrNameStart == -1) return null;
		attrNameStart++;
		if(opStart == -1) return null;
		
		name = inStr.substring(attrNameStart, opStart);
		value = inStr.substring(opStart+operator.length());
		name = name.trim();
		value = value.trim();
		
		return new ComparisonType(name,value,operator);
	}
	
	private boolean validAttribute(String attrName) {
		return this.validAttrs.containsKey(attrName);
	}
	
	public void parseAttributeStr(String attrsIn) {
		int andPos = attrsIn.indexOf("and");
		
		if(andPos == -1)
			return;
		
		String left = attrsIn.substring(0, andPos);
		String right = attrsIn.substring(andPos + 3);
		String name = ""; 
		String value = "";
		
		ComparisonType comp = getAttribute(left,name,value);
		
		if(comp == null) return;
		if(!validAttribute(comp.getName())) return;
		
		this.attrSearch.put(comp.getName(),comp);
		
		comp = getAttribute(right,name,value);
		if(comp == null) return;
		if(!validAttribute(comp.getName())) return;
		
		this.attrSearch.put(comp.getName(), comp);
		this.matchesRequired+=2;
	}
	
	/**
	 * Finds the end of a string of alphabetical characters
	 * r2: added the * selection
	 * @param inStr the string to search
	 * @return the character after the chracter string or -1 if not found.
	 */
	private static Pattern charStrRegEx = Pattern.compile("([a-zA-Z0-9\\*])*");
	
	private int findEndOfString(String inStr) {
		Matcher matcher = charStrRegEx.matcher(inStr);
		if(matcher.find()) {
			int retval = matcher.group(0).length();
			int groupCount = matcher.groupCount();
			return retval;
		}
		return -1;
	}
	
	/**
	 * Parses an XPath string and prepares the search.
	 * 
	 * @param xPathStr The XPath string to parse
	 * @return True every damn time.
	 */
	public boolean parseXPath(String xPathStr) {
		this.doChildNodes = false;
		String tagName = xPathStr; 
		
		if(xPathStr.length() > 2 &&
				xPathStr.charAt(0) == '/' && xPathStr.charAt(1) == '/')
		{
			this.doChildNodes = true;
			tagName = xPathStr.substring(2);
			tagName = tagName.substring(0, findEndOfString(tagName));
			//
			// Handle any tags that come in starting with 'cf'
			/*
			if(tagName.startsWith("cf")) {
				tagName = tagName.substring(2);
			}
			*/
			this.setTagSearch(tagName);
		}
		
		int bracketOpen = xPathStr.indexOf('[');
		boolean posSearch = bracketOpen != -1;
		
		if(posSearch) {
			String attributeSearch = xPathStr.substring(bracketOpen+1);
			int bracketEnd = attributeSearch.lastIndexOf(']');
			attributeSearch = attributeSearch.substring(0, bracketEnd);
			this.parseAttributeStr(attributeSearch);
		}
		
		return true;
	}
	
	/**
	 * Constructs an XPath searcher and parses the supplied XPath.
	 * 
	 * @param xPathStr The XPath string (nb: should be basic)
	 */
	public XPathSearch(String xPathStr) {
		this();
		this.parseXPath(xPathStr);
	}
	
}