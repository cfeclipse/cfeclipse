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

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.IRule;
import com.rohanclan.cfml.editors.ColorManager;
import com.rohanclan.cfml.editors.ICFColorConstants;
//import com.rohanclan.coldfusionmx.editors.CFKeywordDetector;
import com.rohanclan.cfml.editors.CFSyntaxDictionary;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import com.rohanclan.cfml.dictionary.DictionaryManager;

/**
 * @author Rob
 *
 * This is the scanner for cfscript partitions 
 */
public class CFScriptScanner extends RuleBasedScanner {

	public CFScriptScanner(ColorManager manager)
	{
		super();
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFNUMBER))
		);
		
		IToken cftag = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFTAG))
		);
		
		IToken cfcomment = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.HTM_COMMENT))
		);
		
		IToken string = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFSCRIPT_STRING))
		);
		
		IToken cfkeyword = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFSCRIPT_KEYWORD))
		);
		
		IToken cffunction = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFSCRIPT_FUNCTION))
		);
		
		List rules = new ArrayList();
		
		//so the script tags look correct
		rules.add(new SingleLineRule("<cfscript", ">", cftag));
		rules.add(new SingleLineRule("</cfscript", ">", cftag));
		rules.add(new SingleLineRule("<CFSCRIPT", ">", cftag));
		rules.add(new SingleLineRule("</CFSCRIPT", ">", cftag));
		
		rules.add(new MultiLineRule("/*", "*/", cfcomment));
		rules.add(new EndOfLineRule("//", cfcomment));
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		rules.add(new NumberRule(cfnumber));
		
		CFSyntaxDictionary dic = (CFSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.CFDIC);
		
		//do any keywords
		WordRule wr = new WordRule(new CFScriptKeywordDetector());
		//get any needed operators (or, and et cetra)
		Set set = dic.getOperators();
		//get any script specific keywords (if, case, while, et cetra)
		set.addAll(dic.getScriptKeywords());
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			wr.addWord(op,cfkeyword);
			wr.addWord(op.toUpperCase(),cfkeyword);
		}
		
		
		//now do the cffuntions so they look pretty too :)
		set = dic.getFunctions();
		it = set.iterator();
		while(it.hasNext())
		{
			String fun = (String)it.next();
			wr.addWord(fun, cffunction);
			wr.addWord(fun.toUpperCase(), cffunction);
		}
		
		rules.add(wr);
		
		
		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		
		setRules(rulearry);
	}
}

