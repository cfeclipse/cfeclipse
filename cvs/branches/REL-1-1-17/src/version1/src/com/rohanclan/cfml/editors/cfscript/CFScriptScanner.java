/*
 * Created on Jan 31, 2004
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
package com.rohanclan.cfml.editors.cfscript;

import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.TextAttribute;

import com.rohanclan.cfml.editors.CFSyntaxDictionary;
import com.rohanclan.cfml.editors.ColorManager;
//import com.rohanclan.cfml.editors.ICFColorConstants;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.CFKeywordDetector;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.PredicateWordRule;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.dictionary.DictionaryManager;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rob
 *
 * This is the scanner for cfscript partitions 
 */
public class CFScriptScanner extends RuleBasedScanner {

	public CFScriptScanner(ColorManager manager, CFMLPreferenceManager prefs)
	{
		super();
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_CFNUMBER)
			)
		));
		
		IToken cftag = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_CFTAG)
			)
		));
	
		IToken cfcomment = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_CFCOMMENT)
			)
		));
		
		IToken string = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_CFSTRING)
			)
		));
		
		IToken cfkeyword = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_CFKEYWORD)
			)
		));
		
		IToken cffunction = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_CFSCRIPT_FUNCTION)
			)
		));
		
		IToken cfdefault = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLPreferenceManager.P_COLOR_DEFAULT_TEXT)
			)
		));
				
		List rules = new ArrayList();
		
		//so the script tags look correct
		rules.add(new SingleLineRule("<cfscript", ">", cftag));
		rules.add(new SingleLineRule("</cfscript", ">", cftag));
		rules.add(new SingleLineRule("<CFSCRIPT", ">", cftag));
		rules.add(new SingleLineRule("</CFSCRIPT", ">", cftag));
		
		//I think the reason this doesnt work as well as the <!-- type of comment
		//is that the <! type is defined on the partition scanner where this is
		//only here... javascript has the same problem 
		//TODO: can the bad commment coloring be fixed?
		rules.add(new MultiLineRule("/*", "*/", cfcomment));
		rules.add(new EndOfLineRule("//", cfcomment));
		
		rules.add(new SingleLineRule("\"", "\"", string));
		rules.add(new SingleLineRule("'", "'", string));
		
		rules.add(new NumberRule(cfnumber));
		
		CFSyntaxDictionary dic = (CFSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.CFDIC);
		
		//do any keywords
		//get any needed operators (or, and et cetra)
		
		//Set set = dic.getOperators();
		
		//get any script specific keywords (if, case, while, et cetra)		
		//set.addAll(dic.getScriptKeywords());
		
		Set set = dic.getScriptKeywords();
		
		String allkeys[] = new String[set.size()<<1];
		int i=0;
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String opname = (String)it.next(); 
			allkeys[i++] = opname;
			allkeys[i++] = opname.toUpperCase();
		}
		
		//build the word highlighter
		CFKeywordDetector cfkd = new CFKeywordDetector();
		PredicateWordRule words = new PredicateWordRule(
			cfkd, 
			cfdefault, 
			allkeys, 
			cfkeyword
		);
		
		//now do the cffuntions so they look pretty too :)
		set = dic.getFunctions();
		it = set.iterator();
		while(it.hasNext())
		{
			String fun = (String)it.next();
			words.addWord(fun, cffunction);
			words.addWord(fun.toUpperCase(), cffunction);
		}
		rules.add(words);
		
		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		
		setRules(rulearry);
	}
}

