/*
 * Created on Feb 26, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package com.rohanclan.coldfusionmx.dictionary;

import java.util.Map;
import java.util.HashMap;

import com.rohanclan.coldfusionmx.editors.CFSyntaxDictionary;
import com.rohanclan.coldfusionmx.editors.HTMLSyntaxDictionary;
import com.rohanclan.coldfusionmx.editors.script.JSSyntaxDictionary;

/**
 * @author Rob
 *
 * This class loads all the dictionaries and can be used to get specific
 * dictionaries. This (hopefully) well help in abstracting the dictionaries
 * not intended to be instanceated
 */
public class DictionaryManager {
	/** the cold fusion dictionary */
	public static final String CFDIC = "CF_DICTIONARY";
	/** the javascript dictionary */
	public static final String JSDIC = "JS_DICTIONARY";
	/** the (yet to be made) html dictionary */
	public static final String HTDIC = "HT_DICTIONARY";
	
	/** all the dictionaries */
	private static Map dictionaries = new HashMap();
	
	private DictionaryManager();
	
	/**
	 * Tell the dictionaries to load themselves
	 */
	public static void initDictionaries()
	{
		SyntaxDictionary dic = new CFSyntaxDictionary();
		//when all is done this will actually load a dictionary
		dic.loadDictionary("coldfusion.xml");
		addDictionary(CFDIC,dic);
		
		dic = new HTMLSyntaxDictionary();
		dic.loadDictionary("html.xml");
		addDictionary(HTDIC,dic);
		
		
		dic = new JSSyntaxDictionary();
		addDictionary(JSDIC,dic);
	}
	
	/**
	 * Add a dictionary to the manager
	 * @param key the key to be used to get the dictionary
	 * @param sd the dictionary
	 */
	public static void addDictionary(String key, SyntaxDictionary sd)
	{
		dictionaries.put(key,sd);
	}
	
	/**
	 * Get a dictionary
	 * @param key the dictionary's key (often one of the statics above)
	 * @return the dictionary
	 */
	public static SyntaxDictionary getDictionary(String key)
	{
		return (SyntaxDictionary)dictionaries.get(key);
	}
}
