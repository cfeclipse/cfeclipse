/*
 * Created on Nov 14, 2004
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
package org.cfeclipse.laszlo.editors;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;

public class LaszloPartitionScanner extends RuleBasedPartitionScanner {
	
	public LaszloPartitionScanner() {
		IToken xmlComment = new Token(CFPartitionScanner.HTM_COMMENT);
		IToken tag = new Token(CFPartitionScanner.ALL_TAG);
		IToken script = new Token(CFPartitionScanner.J_SCRIPT);
		
		IPredicateRule[] rules = new IPredicateRule[3];

		rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		rules[1] = new MultiLineRule("<![CDATA[","]]>",script);
		rules[2] = new TagRule(tag);
		
		setPredicateRules(rules);
	}
}
