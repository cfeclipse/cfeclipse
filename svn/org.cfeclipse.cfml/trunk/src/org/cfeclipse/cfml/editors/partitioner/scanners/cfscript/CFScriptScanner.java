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
package org.cfeclipse.cfml.editors.partitioner.scanners.cfscript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.editors.CFSyntaxDictionary;
import org.cfeclipse.cfml.editors.ColorManager;
import org.cfeclipse.cfml.editors.partitioner.scanners.rules.CFKeywordDetector;
import org.cfeclipse.cfml.editors.partitioner.scanners.rules.PredicateWordRule;
import org.cfeclipse.cfml.preferences.CFMLColorsPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

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
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFNUMBER)
			)
		));
		
		IToken cftag = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFTAG)
			)
		));
	
		IToken cfcommentBlock = new Token(new TextAttribute(manager.getColor(prefs
				.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFCOMMENT))));

		IToken cfcomment = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFCOMMENT)
			)
		));
		
		IToken string = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSTRING)
			)
		));
		
		IToken cfkeyword = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFKEYWORD)
			)
		));
		
		IToken cfscope = new Token(new TextAttribute(
				manager.getColor(
					prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSCOPE)
				)
			));
		
		IToken cfopperators = new Token(new TextAttribute(
				manager.getColor(
					prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFOPPERATOR)
				)
			));

		IToken cfbuiltinscope = new Token(new TextAttribute(
				manager.getColor(
					prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFBUILTINSCOPE)
				)
			));

		IToken cffunction = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_FUNCTION)
			)
		));

		IToken javadoc = new Token(new TextAttribute(manager.getColor(prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_JAVADOC))));
		
		IToken cfdefault = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT)
			)
		));
				
		List rules = new ArrayList();
		
		//so the script tags look correct
		rules.add(new SingleLineRule("<cfscript", ">", cftag));
		rules.add(new SingleLineRule("</cfscript", ">", cftag));
		rules.add(new SingleLineRule("<CFSCRIPT", ">", cftag));
		rules.add(new SingleLineRule("</CFSCRIPT", ">", cftag));
		
		rules.add(new MultiLineRule("\"", "\"", string));
		rules.add(new MultiLineRule("'", "'", string));
		
		rules.add(new MultiLineRule("/**", "*/", javadoc, (char) 0, true));
		rules.add(new MultiLineRule("/*", "*/", cfcommentBlock, (char) 0, true));
		rules.add(new EndOfLineRule("//", cfcomment));
		rules.add(new NumberRule(cfnumber));
		
		CFSyntaxDictionary dic = (CFSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.CFDIC);
		
		//do any keywords
		//get any needed operators (or, and et cetra)
		
		//Set set = dic.getOperators();
		
		//get any script specific keywords (if, case, while, et cetra)		
		//set.addAll(dic.getScriptKeywords());
		
		Set set = dic.getScriptKeywords();
		
		String allkeys[] = new String[set.size()];
		int i=0;
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String opname = (String)it.next(); 
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
		words.setCaseSensitive(false);

		//now do the opperators
		set = dic.getOperators();
		it = set.iterator();
		while(it.hasNext())
		{
			String opp = (String)it.next().toString().toLowerCase();
			words.addWord(opp, cfopperators);
		}

		//now do the cffuntions so they look pretty too :)
		set = dic.getFunctions();
		it = set.iterator();
		while(it.hasNext())
		{
			String fun = (String)it.next().toString().toLowerCase();
			words.addWord(fun, cffunction);
		}
		rules.add(words);
		
		//now do the scopes so they look pretty too :)
		set = dic.getAllScopes();
		it = set.iterator();
		while(it.hasNext())
		{
			String scope = (String)it.next().toString().toLowerCase();
			words.addWord(scope, cfscope);
		}
		words.addWord("this",cfbuiltinscope);
		words.addWord("form",cfbuiltinscope);
		words.addWord("url",cfbuiltinscope);
		words.addWord("variables",cfbuiltinscope);
		words.addWord("arguments",cfbuiltinscope);
		words.addWord("session",cfbuiltinscope);
		words.addWord("request",cfbuiltinscope);
		words.addWord("application",cfbuiltinscope);
		rules.add(words);

		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		
		setRules(rulearry);
	}
}

