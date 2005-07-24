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
package com.rohanclan.cfml.editors.partitioner.scanners;

//import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;

import com.rohanclan.cfml.editors.CFWhitespaceDetector;
import com.rohanclan.cfml.editors.ColorManager;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.ShowWhitespaceRule;

//import org.eclipse.jface.text.rules.IRule;

/**
 * @author Rob
 *
 * This is the scanner for all text that is not a cf tag not an html tag
 * and not attributes of either ... the plain text. There are no rules as of
 * yet.
 */
public class TextScanner extends RuleBasedScanner {

	public TextScanner(ColorManager manager) 
	{
		super();
		/*
		 IToken cfvar = new Token(new TextAttribute(
		 manager.getColor(ICFColorConstants.CFVARIABLE))
		 );
		*/
		
		IRule[] rules = new IRule[1];
		rules[0] = new ShowWhitespaceRule(new CFWhitespaceDetector());
		setRules(rules);
	}
}

