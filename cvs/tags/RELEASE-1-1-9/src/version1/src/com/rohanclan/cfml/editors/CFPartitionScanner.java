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

/**
 * @author Rob
 *
 * This scans the overall document and slices it into partitions. Then the
 * partition scanners are applied to those partitions
 */
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import com.rohanclan.cfml.dictionary.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


public class CFPartitionScanner extends RuleBasedPartitionScanner {
	//public final static String CF_DEFAULT 	= "__cf_default";
	public final static String HTM_COMMENT 	= "__htm_comment";
	public final static String CF_TAG 		= "__cf_tag";
	public final static String CF_END_TAG 	= "__cf_end_tag";
	public final static String ALL_TAG 		= "__all_tag";
	public final static String CF_SCRIPT	= "__cf_script";
	public final static String J_SCRIPT		= "__script_tag";
	public final static String CSS_TAG		= "__css_tag";
	public final static String UNK_TAG		= "__unk_tag";
	
	public CFPartitionScanner() {

		IToken htmComment 	= new Token(HTM_COMMENT);
		IToken tag 			= new Token(ALL_TAG);
		IToken cftag 		= new Token(CF_TAG);
		IToken cfendtag 	= new Token(CF_END_TAG);
		//IToken cfdefault 	= new Token(CF_DEFAULT);
		IToken cfscript 	= new Token(CF_SCRIPT);
		IToken jscript 		= new Token(J_SCRIPT);
		IToken css 			= new Token(CSS_TAG);
		IToken unktag		= new Token(UNK_TAG);
		
		List rules = new ArrayList();
		
		//the order here is important. It should go from specific to
		//general as the rules are applied in order
		
		// Partitions in the document will get marked in this order
		rules.add(new MultiLineRule("<!--", "-->", htmComment));
		
		//doctype rule
		rules.add(new MultiLineRule("<!", ">", htmComment));
		
		//script block as its own highlighting
		rules.add(new MultiLineRule("<cfscript>", "</cfscript>", cfscript));
		rules.add(new MultiLineRule("<CFSCRIPT>", "</CFSCRIPT>", cfscript));
		rules.add(new MultiLineRule("<style", "</style>", css));
		rules.add(new MultiLineRule("<STYLE", "</STYLE>", css));
		rules.add(new MultiLineRule("<script", "</script>", jscript));
		rules.add(new MultiLineRule("<SCRIPT", "</SCRIPT>", jscript));
		
		SyntaxDictionary sd = DictionaryManager.getDictionary(
			DictionaryManager.CFDIC
		);
		
		Tag tg = null;
		
		try
		{
			Set elements = ((SyntaxDictionaryInterface)sd).getAllElements();
			
			Iterator it = elements.iterator();
			while(it.hasNext())
			{
				String ename = (String)it.next();
				//System.out.println(ename);
				
				if(!ename.equals("script"))
				{	
					tg = sd.getTag(ename);
					rules.add(new MultiLineRule("<cf" + ename,">", cftag));
					rules.add(new MultiLineRule("<CF" + ename.toUpperCase(),">", cftag));
					if(!tg.isSingle())
					{	
						rules.add(new MultiLineRule("</cf" + ename,">", cfendtag));
						rules.add(new MultiLineRule("</CF" + ename.toUpperCase(),">", cfendtag));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		//these are not really handled in the dictionary because you can call 
		//normal pages as cf_'s
		rules.add(new MultiLineRule("<cf_",">", cftag));
		rules.add(new MultiLineRule("</cf_",">", cfendtag));
		rules.add(new MultiLineRule("<CF_",">", cftag));
		rules.add(new MultiLineRule("</CF_",">", cfendtag));
		
		//do the html tags now
		sd = DictionaryManager.getDictionary(DictionaryManager.HTDIC);
		
		try
		{
			Set elements = ((SyntaxDictionaryInterface)sd).getAllElements();
			
			Iterator it = elements.iterator();
			while(it.hasNext())
			{
				String ename = (String)it.next();
				//System.out.println(ename);
				
				if(!ename.equals("script") && !ename.equals("style"))
				{
					tg = sd.getTag(ename);
					rules.add(new MultiLineRule("<" + ename,">", tag));
					rules.add(new MultiLineRule("<" + ename.toUpperCase(),">", tag));
					if(!tg.isSingle())
					{	
						rules.add(new MultiLineRule("</" + ename,">", tag));
						rules.add(new MultiLineRule("</" + ename.toUpperCase(),">", tag));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		//catch any other tags we dont know about (xml etc) and make them
		//a different color
		rules.add(new MultiLineRule("<", ">", unktag));
		
		IPredicateRule[] rulearry = new IPredicateRule[rules.size()];
		rules.toArray(rulearry);
		
		setPredicateRules(rulearry);
	}
}
