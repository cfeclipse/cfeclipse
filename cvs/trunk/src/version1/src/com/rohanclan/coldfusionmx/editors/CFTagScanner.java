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
package com.rohanclan.coldfusionmx.editors;

//import java.util.Iterator;
//import java.util.Set;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;
//import com.rohanclan.coldfusionmx.editors.CFSyntaxDictionary;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFTagScanner extends RuleBasedScanner {

	public CFTagScanner(ColorManager manager) 
	{
		super();
		
		IToken cftag =	new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFTAG))
		);
		
		IToken string =	new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.STRING))
		);
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFNUMBER))
		);
		
		IToken cfkeyword = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFKEYWORD))
		);
		
		IToken cfdefault = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.DEFAULT))
		);
				
		IRule[] rules = new IRule[4];
		
		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		
		rules[2] = new NumberRule(cfnumber);
		
		//load all the keywords - this just doesnt work. See the comment below
		//for a description of the problem
		/* WordRule wr = new WordRule(new CFKeywordDetector());
		Set set = CFSyntaxDictionary.getOperators();
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			System.err.println("adding: " + op);
			wr.addWord(op,cfkeyword);
			wr.addWord(op.toUpperCase(),cfkeyword);
		}
		rules[3]  = wr;
		*/
		
		/** THIS IS A HACK
		 *  for some reason cfscript function keywords bleed all over the page
		 *  so any function name that is shared between cfscript and cftags 
		 *  (for example the word "function") is blue every where the word is
		 * 	typed (i.e. function booga() and <cffunction both are blue. This 
		 *  attempts to hack it by making all function names red
		 
		wr = new WordRule(new CFKeywordDetector());
		set = SyntaxDictionary.getScriptKeywords();
		it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			wr.addWord(op,cftag);
			wr.addWord(op.toUpperCase(),cftag);
		}
		rules[4]  = wr; 
		*/
		
		// Add generic whitespace rule.
		rules[3] = new WhitespaceRule(new CFWhitespaceDetector());
		
		setRules(rules);
	}
}