/*
 * Created on Jan 30, 2004
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
package com.rohanclan.cfml.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Rob
 *
 * This is the cftag color scanner. Looks at the tags and colors 'em as best as 
 * it can
 */
public class CFTagScanner extends RuleBasedScanner {
	
	public CFTagScanner(ColorManager manager,CFMLPreferenceManager prefManager)
	{
		super();
		
		IToken cftag = new Token(new TextAttribute(
			manager.getColor(
				prefManager.getColor(CFMLPreferenceManager.P_COLOR_CFTAG)
			)
		));
		
		IToken string =	new Token(new TextAttribute(
			manager.getColor(
				prefManager.getColor(CFMLPreferenceManager.P_COLOR_CFSTRING)
			)
		));
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(
				prefManager.getColor(CFMLPreferenceManager.P_COLOR_CFNUMBER)
			)
		));
		
		IToken cfkeyword = new Token(new TextAttribute(
			manager.getColor(
				prefManager.getColor(CFMLPreferenceManager.P_COLOR_CFKEYWORD)
			)
		));
		
		IToken cfdefault = new Token(new TextAttribute(
			manager.getColor(
				prefManager.getColor(CFMLPreferenceManager.P_COLOR_DEFAULT_TEXT)
			)
		));
		
		///////////////////////////////////////////////////////////////////////
		
		IRule[] rules = new IRule[5];
		
		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string);
		rules[1] = new SingleLineRule("'", "'", string);
		rules[2] = new NumberRule(cfnumber);
		
		CFSyntaxDictionary cfd = (CFSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.CFDIC);
		
		Set set = cfd.getOperators();
		String allkeys[] = new String[set.size()<<1];
		
		int i=0;
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			allkeys[i++] = op;
			allkeys[i++] = op.toUpperCase();
		}
		
		CFKeywordDetector cfkd = new CFKeywordDetector();
		PredicateWordRule words = new PredicateWordRule(cfkd,cftag,	allkeys,	cfkeyword);
		
		rules[3] = words;
		
		// Add generic whitespace rule.
		rules[4] = new WhitespaceRule(new CFWhitespaceDetector());
		
		setRules(rules);
	}
}