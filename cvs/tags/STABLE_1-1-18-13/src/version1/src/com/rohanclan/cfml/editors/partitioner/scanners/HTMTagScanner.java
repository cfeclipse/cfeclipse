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

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

import com.rohanclan.cfml.editors.CFWhitespaceDetector;
import com.rohanclan.cfml.editors.ColorManager;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.HTMLColorsPreferenceConstants;
/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class HTMTagScanner extends RuleBasedScanner {
		
	public HTMTagScanner(ColorManager manager, CFMLPreferenceManager prefs)
	{
		/* IToken string =	new Token(
			new TextAttribute(manager.getColor(ICFColorConstants.STRING))
		); */
		
		Token string = new Token(
			new TextAttribute(
				manager.getColor(
					prefs.getColor(
							HTMLColorsPreferenceConstants.P_COLOR_STRING
					)
				)
			)
		);
		
		
		//IToken cfvar = new Token(new TextAttribute(
		//	manager.getColor(IXMLColorConstants.CFVARIABLE))
		//);
		
		IRule[] rules = new IRule[3];

		//I think its more important to show cfvariable then to color code the
		// attributes.
		//rules[0] = new SingleLineRule("#", "#", cfvar);
		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string);
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string);
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new CFWhitespaceDetector());
		
		
		setRules(rules);
	}
}