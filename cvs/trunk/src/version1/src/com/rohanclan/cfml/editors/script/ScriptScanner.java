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

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
//import org.eclipse.jface.text.rules.RuleBasedScanner;
//import org.eclipse.jface.text.rules.IRule;
import com.rohanclan.cfml.editors.ColorManager;
import com.rohanclan.cfml.editors.ICFColorConstants;
//import com.rohanclan.coldfusionmx.editors.CFKeywordDetector;
//import com.rohanclan.coldfusionmx.editors.SyntaxDictionary;
//import com.rohanclan.coldfusionmx.editors.TagRule;
import com.rohanclan.cfml.editors.CFKeywordDetector;
//import com.rohanclan.coldfusionmx.editors.CFPartitionScanner;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import com.rohanclan.cfml.dictionary.DictionaryManager;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ScriptScanner extends RuleBasedScanner {
	
	public ScriptScanner(ColorManager manager)
	{
		super();
		
		//IToken cfnumber = new Token(new TextAttribute(
		//	manager.getColor(ICFColorConstants.CFNUMBER))
		//);
		
		IToken cfnumber = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFNUMBER))
		);
		
		IToken scripttag = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.JSCRIPT))
		);
		
		IToken cfcomment = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.HTM_COMMENT))
		);
		
		IToken string = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFSCRIPT_STRING))
		);
		
		IToken keyword = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.CFSCRIPT_KEYWORD))
		);
		
		IToken function = new Token(new TextAttribute(
			manager.getColor(ICFColorConstants.JSCRIPT_FUNCTION))
		);
		
		//IToken cftag 		= new Token(CFPartitionScanner.CF_TAG);
		//IToken cfendtag 	= new Token(CFPartitionScanner.CF_END_TAG);
		
		List rules = new ArrayList();
		
		//style the whole block with some default colors
		rules.add(new SingleLineRule("<script", ">", scripttag));
		rules.add(new SingleLineRule("</script", ">", scripttag));
		
		rules.add(new SingleLineRule("<SCRIPT", ">", scripttag));
		rules.add(new SingleLineRule("</SCRIPT", ">", scripttag));
		
		//rules.add(new MultiLineRule("<cf",">", cftag));
		//rules.add(new MultiLineRule("<CF",">", cftag));
		//rules.add(new SingleLineRule("</cf",">", cfendtag));
		//rules.add(new SingleLineRule("</CF",">", cfendtag));
		
		JSSyntaxDictionary jssd = (JSSyntaxDictionary)DictionaryManager.getDictionary(DictionaryManager.JSDIC);
		
		///////////////////////////////////////////////////////////////////////
		//do any keywords
		WordRule wr = new WordRule(new CFKeywordDetector());
		//get any script specific keywords (if, case, while, et cetra)
		Set set = jssd.getScriptKeywords();
		//set.addAll();
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			wr.addWord(op,keyword);
		}
		rules.add(wr);
		
		///////////////////////////////////////////////////////////////////////
		//do any operatores
		wr = new WordRule(new OperatorDetector());
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
		//do any known functions
		wr = new WordRule(new CFKeywordDetector());
		//get any script specific functions (alert, parseInt, confirm, et cetra)
		set = jssd.getFunctions();
		it = set.iterator();
		while(it.hasNext())
		{
			String op = (String)it.next();
			wr.addWord(op,function);
		}
		rules.add(wr);
		
		///////////////////////////////////////////////////////////////////////
		rules.add(new MultiLineRule("/*", "*/", cfcomment));
		rules.add(new EndOfLineRule("//", cfcomment));
		
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		rules.add(new NumberRule(cfnumber));
		
		IRule[] rulearry = new IRule[rules.size()];
		rules.toArray(rulearry);
		
		setRules(rulearry);
	}
}
