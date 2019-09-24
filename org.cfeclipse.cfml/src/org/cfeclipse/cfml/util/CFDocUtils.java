/*
 * Created on Aug 29, 2004
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
package org.cfeclipse.cfml.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.dictionary.Parameter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;

/**
 * This is a helper class provides methods to manipulate, modify and generally
 * mangle stuff that you find in CF documents.
 *  
 * @author Oliver Tupman
 */
public class CFDocUtils {
	/**
	 * Removes parameters from set 1 that are named in set 2.
	 * 
	 * @param set1 The set of Parameters to remove the duplicates from
	 * @param set2 The set containing the string names of the parameters to remove.
	 * @return A set containing set1 - the intersection of set1 & set2
	 */
	public static Set eliminateDuplicateParams(Set set1, Set set2) {
		/*
		 * Very simply, for each parameter in set1 we loop around set 2.
		 * For each string parameter name in set 2 we test it with the
		 * name of the current parameter from set 1. If the two match
		 * we add it to a list of parameters to remove (can't remove inline
		 * as it buggers up the iterator).
		 * Having created the list of parameters to remove we run through
		 * it removing the relevant parameters from set1.
		 */
		//Set returnAttribs = new HashSet();
		Iterator currAttrIter = set1.iterator();
		ArrayList params2Remove = new ArrayList();
		
		while(currAttrIter.hasNext()) {
			Object paramObj = currAttrIter.next();
			//Assert.isTrue(paramObj instanceof Parameter, "A parameter proposal from a tag attribute contributor is not of type Parameter");
			
			if(!(paramObj instanceof Parameter))
				throw new IllegalArgumentException("A parameter proposal from a tag attribute contributor is not of type Parameter");
			
			Parameter currParam = (Parameter)paramObj;
			Iterator set1Iter = set2.iterator();
			while(set1Iter.hasNext()) {
				String set1Val = (String)set1Iter.next();
				if(set1Val.equalsIgnoreCase(currParam.getName())) {
					params2Remove.add(currParam);
					break;
				}
			}
		}
		Iterator removeIter = params2Remove.iterator();
		while(removeIter.hasNext()) {
			set1.remove(removeIter.next());
		}
		return set1;
	}
	
	/**
	 * Parses a string looking for attributes
	 * 
	 * @param string2Scan Says it all. The string to scan for attributes. Please note that it shouldn't contain any tags.
	 * @returns A Set of strings containing the names of the attributes found.
	 */
	public static Map parseForAttributes(String string2Scan) {
		
		/*
		 * Original regEx before escaping:
		 * \s*(\w+)\s*=\s*('(?:[^']*(?:'')?[^']*)*'|"(?:[^"]*(?:"")?[^"]*)*")
		 */
		//Old Reg-ex that breaks thigns :: String regExForAtributes = "\\s*(\\w+)\\s*=\\s*('(?:[^']*(?:'')?[^']*)*'|\"(?:[^\"]*(?:\"\")?[^\"]*)*\")";
		final String regExForAtributes = "(?s)(\\w+)[\\s=]+(((\\x22|\\x27|#)((?!\\4).|\\4{2})*\\4))";
		//String regExForAtributes = "\\s*(\\w+)\\s*=\\s*(\"([^\"](\"\")?[^\"])*\"|'([^']('')?[^'])*')";
		//Do a reg ex rather than just a split
		 Pattern pattern = Pattern.compile(regExForAtributes);
		 Matcher matcher = pattern.matcher(string2Scan);
		 
		Map attribs = new HashMap();
		 
		 //For each attribute/value pairs we find, we creaate a new attribute.
		  while (matcher.find()) {
			  String fullAttributeAndValue = matcher.group();
			  String[] strings = parseAttribute(fullAttributeAndValue);
			  
			  //System.out.println(strings[0] + strings[1]);
			  String attribName = strings[0];
			  String attribValue = strings[1];
				
			  attribs.put(attribName, attribValue);
			  
		  }	  
		 
		
		return attribs;
		
		
	
		
	}
	
	private static String[] parseAttribute(String fullAttribute){
		String[] parsedAttribute = {"",""};
		//To split a var="something" we just need to find the location of the first "=" and we split it like that
		if(fullAttribute.lastIndexOf("=") > 0) {
			
			String dirtyAttributeName = fullAttribute.substring(0, fullAttribute.lastIndexOf("="));
			String dirtyAttributeValue = fullAttribute.substring(fullAttribute.lastIndexOf("=")+1, fullAttribute.length());

			parsedAttribute[0] = dirtyAttributeName.trim();
			String cleanAttributeValue = dirtyAttributeValue.trim();

			//	remove starting " and ending "
			if (cleanAttributeValue.startsWith("\"") && cleanAttributeValue.endsWith("\"") && cleanAttributeValue.length() > 1) {
				cleanAttributeValue = cleanAttributeValue.substring(1, cleanAttributeValue.length()-1);
			}
			
			parsedAttribute[1] = cleanAttributeValue;
		}
		
		
		
		return parsedAttribute;
	}
	
	/**
	 * Parses the start tag for a given tag, might not actually require the tagname attribute
	 * TODO: This brings an index out of bounds exception
	 * @param tagname the name of the tag
	 * @param starttag the string that makes up an opener tag
	 * @return a set of strings containing the attributes and values for that start tag
	 */
	public static Map parseStartTag(String tagname, String starttag){
		//TODO: re-write the parsing
		//Set attribs = new HashSet();
		
		Map attribs = new HashMap();
		// Remove the tag name and <
		
		//top and tail the tag.
		if(starttag.startsWith("<")){
			starttag  = starttag.replaceFirst("<", "");
		}
		if(starttag.endsWith("/>")){
			starttag = starttag.substring(0, starttag.length()-2);
			//Trim 
		}
		if(starttag.endsWith(">")){
			starttag = starttag.substring(0, starttag.length()-1);
		}
		
		//remove the tagname
		if(starttag.startsWith(tagname)){
			starttag = starttag.replaceFirst(tagname, "");
			starttag = starttag.trim();
		}
		attribs = parseForAttributes(starttag);
		return attribs;
		
	}

	public static ITextSelection selectWord(IDocument doc, int offset) {
		try {
			int length = doc.getLength();
			int pos = offset;
			char c;
			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				++pos;
			}
			int endPos = pos;
			pos = offset;
			;
			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				--pos;
			}
			int startPos = pos;
			if (startPos != endPos) {
				ITextSelection newSel = new TextSelection(doc, startPos + 1, endPos - startPos - 1);
				return newSel;
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks a attribute value string to make sure it's valid.
	 * Rules for value attribute are:
	 * - Has more than two characters
	 * - First character and last characters are double quotes
	 * - Thats about it :D No further checks just yet.
	 * 
	 * @param attribValue - The attribute to check
	 * @return true/false on whether it's a valid attribute
	 */
	public static boolean isValidAttributeValue(String attribValue) {
		if(attribValue.length() < 2) {
	    	return false;
	    }
	    if(attribValue.charAt(0) != '\"') {
	    	return false;
	    }
	    else if(attribValue.charAt(attribValue.length()-1) != '\"') {
	    	return false;
	    }
	    return true;
	}
}
