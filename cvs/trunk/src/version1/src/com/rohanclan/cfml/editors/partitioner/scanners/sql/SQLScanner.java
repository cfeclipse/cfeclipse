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
package com.rohanclan.cfml.editors.partitioner.scanners.sql;

import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;

import com.rohanclan.cfml.editors.SQLSyntaxDictionary;
import com.rohanclan.cfml.editors.ColorManager;
//import com.rohanclan.cfml.editors.ICFColorConstants;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.PredicateWordRule;
import com.rohanclan.cfml.editors.partitioner.scanners.sql.SQLKeywordDetector;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.CFMLColorsPreferenceConstants;
import com.rohanclan.cfml.dictionary.DictionaryManager;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rob
 *
 * This is the scanner for cfquery partitions 
 */
public class SQLScanner extends RuleBasedScanner {

	public SQLScanner(ColorManager manager, CFMLPreferenceManager prefs)
	{
		super();
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFNUMBER)
			)
		));
		
		IToken sqlcomment = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_SQL_COMMENT)
			)
		));
		
		IToken SQLString = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_SQL_STRING)
			)
		));
		
		IToken cfquerytext = new Token(new TextAttribute(
				manager.getColor(
					prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_SQL_TEXT)
				)
			));
			
		IToken sqlkeyword = new Token(new TextAttribute(
				manager.getColor(
					prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_SQL_KEYWORD)
				)
				,null
				,SWT.BOLD
			));
		
		IToken cffunction = new Token(new TextAttribute(
			manager.getColor(
				prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_FUNCTION)
			)
			,null
			,SWT.BOLD
		));
				
		List rules = new ArrayList();

		rules.add(new SingleLineRule("\"", "\"", SQLString));
		rules.add(new SingleLineRule("'", "'", SQLString));
		
		rules.add(new NumberRule(cfnumber));
		
	
		
		//I think the reason this doesnt work as well as the <!-- type of comment
		//is that the <! type is defined on the partition scanner where this is
		//only here... javascript has the same problem
		rules.add(new MultiLineRule("/*", "*/", sqlcomment));
		
		SQLSyntaxDictionary dic = (SQLSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.SQLDIC);
			

		//get any SQL specific keywords (select, from, where, et cetra)		
		
		Set set = dic.getSQLKeywords();
		
		String allkeys[] = new String[set.size()];
		int i=0;
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String keywd = (String)it.next();
			allkeys[i++] = keywd;
		}

		
		//build the word highlighter
		SQLKeywordDetector cfqkd = new SQLKeywordDetector();
		PredicateWordRule words = new PredicateWordRule(
			cfqkd, 
			cfquerytext, 
			allkeys, 
			sqlkeyword
		);
		
		words.setCaseSensitive(false);
		
		//now do the cffunctions so they look pretty too :)
		set = dic.getFunctions();
		it = set.iterator();
		while(it.hasNext())
		{
			String fun = (String)it.next();
			words.addWord(fun, cffunction);
		}

		rules.add(words);
		
		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		
		setRules(rulearry);
	}
}

