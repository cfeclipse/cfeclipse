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
package com.rohanclan.cfml.editors.script;

import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.TextAttribute;

import com.rohanclan.cfml.editors.ColorManager;
//import com.rohanclan.cfml.editors.ICFColorConstants;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.CFKeywordDetector;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.PredicateWordRule;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.HTMLColorsPreferenceConstants;
import com.rohanclan.cfml.preferences.CFMLColorsPreferenceConstants;

/**
 * @author Rob
 *
 * This is the Javascript rule scanner (color coder rules).
 */
public class ScriptScanner extends RuleBasedScanner {
	
	public ScriptScanner(ColorManager manager, CFMLPreferenceManager prefs)
	{
		super();
		
		IToken defaulttoken = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT)
			)
		));
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFNUMBER)
			)
		));
		
		IToken scripttag = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(HTMLColorsPreferenceConstants.P_COLOR_JSCRIPT_TEXT)
			)
		));
		/*
		IToken scripttag = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.JSCRIPT))
		);
		*/
		
		IToken comment = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(HTMLColorsPreferenceConstants.P_COLOR_HTM_COMMENT)
			)
		));
		
		IToken string = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_STRING)
			)
		));
		
		IToken keyword = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_KEYWORD)
			)
		));
		
		IToken function = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(HTMLColorsPreferenceConstants.P_COLOR_JSCRIPT_FUNCTION)
			)
		));
						
		List rules = new ArrayList();
		
		//style the whole block with some default colors
		rules.add(new SingleLineRule("<script", ">", scripttag));
		rules.add(new SingleLineRule("</script", ">", scripttag));
		
		rules.add(new SingleLineRule("<SCRIPT", ">", scripttag));
		rules.add(new SingleLineRule("</SCRIPT", ">", scripttag));
		
		rules.add(new MultiLineRule("<cf",">", new Token(CFPartitionScanner.CF_START_TAG)));
		rules.add(new MultiLineRule("<CF",">", new Token(CFPartitionScanner.CF_START_TAG)));
		rules.add(new SingleLineRule("</cf",">", new Token(CFPartitionScanner.CF_END_TAG)));
		rules.add(new SingleLineRule("</CF",">", new Token(CFPartitionScanner.CF_END_TAG)));
		
		JSSyntaxDictionary jssd = (JSSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.JSDIC);
		
		///////////////////////////////////////////////////////////////////////
		//do any keywords
		//get any script specific keywords (if, case, while, et cetra)
		Set set = jssd.getScriptKeywords();
		String allkeys[] = new String[set.size()];
		int i=0;
		
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			allkeys[i++] = (String)it.next();
		}
		
		CFKeywordDetector cfkd = new CFKeywordDetector();
		PredicateWordRule words = new PredicateWordRule(
			cfkd, 
			defaulttoken, 
			allkeys, 
			keyword
		);
		
		///////////////////////////////////////////////////////////////////////
		//do any known functions
		//get any script specific functions (alert, parseInt, confirm, et cetra)
		set = jssd.getFunctions();
		i=0;
		//String allfuncs[] = new String[set.size()];
		it = set.iterator();
		while(it.hasNext())
		{
			//allfuncs[i++] = (String)it.next();
			String op = (String)it.next();
			//wr.addWord(op,function);
			words.addWord(op,function);
		}
		
		rules.add(words);
		
		///////////////////////////////////////////////////////////////////////
		//do any operatores
		WordRule wr = new WordRule(new OperatorDetector());
		//get any script specific operators (*, +, =, et cetra)
		set = jssd.getOperators();
		it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			wr.addWord(op,keyword);
		}
		rules.add(wr);
		
		///////////////////////////////////////////////////////////////////////
		rules.add(new MultiLineRule("/*", "*/", comment));
		rules.add(new EndOfLineRule("//", comment));
		
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		rules.add(new NumberRule(cfnumber));
		
		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		
		setRules(rulearry);
	}
}
