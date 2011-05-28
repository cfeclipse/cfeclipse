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
package org.cfeclipse.cfml.editors.partitioner.scanners;

//import org.eclipse.jface.text.rules.IPredicateRule;
import java.util.ArrayList;
import java.util.List;

import org.cfeclipse.cfml.editors.CFWhitespaceDetector;
import org.cfeclipse.cfml.editors.ColorManager;
import org.cfeclipse.cfml.editors.partitioner.scanners.rules.ShowWhitespaceRule;
import org.cfeclipse.cfml.preferences.CFMLColorsPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;


//import org.eclipse.jface.text.rules.IRule;

/**
 * @author Rob
 *
 * This is the scanner for all text that is not a cf tag not an html tag
 * and not attributes of either ... the plain text. There are no rules as of
 * yet.
 */
public class TextScanner extends RuleBasedScanner {


	public TextScanner(ColorManager manager, CFMLPreferenceManager prefs)
	{
		super();
		/*
		 IToken cfvar = new Token(new TextAttribute(
		 manager.getColor(ICFColorConstants.CFVARIABLE))
		 );
		*/
		IToken cfnumber = new Token(new TextAttribute(manager.getColor(prefs.getColor(CFMLColorsPreferenceConstants.P_COLOR_CFNUMBER)
				)
			));
		List rules = new ArrayList();
		/*		CFKeywordDetector cfkd = new CFKeywordDetector();
				PredicateWordRule words = new PredicateWordRule(cfkd);
				words.setCaseSensitive(false);
				IToken cfbuiltinscope = new Token(new TextAttribute(
						manager.getColor(200,200,140)
							
						)
					));
				words.addWord("application", cfbuiltinscope);
				words.addWord("arguments", cfbuiltinscope);
				words.addWord("attributes", cfbuiltinscope);
				words.addWord("caller", cfbuiltinscope);
				words.addWord("client", cfbuiltinscope);
				words.addWord("cookie", cfbuiltinscope);
				words.addWord("flash", cfbuiltinscope);
				words.addWord("form", cfbuiltinscope);
				words.addWord("request", cfbuiltinscope);
				words.addWord("server", cfbuiltinscope);
				words.addWord("session", cfbuiltinscope);
				words.addWord("this",cfbuiltinscope);
				words.addWord("thistag", cfbuiltinscope);
				words.addWord("thread", cfbuiltinscope);
				words.addWord("url", cfbuiltinscope);
				words.addWord("variables",cfbuiltinscope);

				rules.add(words);
		*/
		rules.add(new ShowWhitespaceRule(new CFWhitespaceDetector()));

		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		setRules(rulearry);
	}
}

