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

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import org.eclipse.jface.text.rules.*;

public class CFPartitionScanner extends RuleBasedPartitionScanner {
	//public final static String CF_DEFAULT 	= "__cf_default";
	public final static String HTM_COMMENT 	= "__htm_comment";
	public final static String CF_TAG 		= "__cf_tag";
	public final static String CF_END_TAG 	= "__cf_end_tag";
	public final static String ALL_TAG 		= "__all_tag";
	////////////////////////////////////////////////////////////
	public final static String CF_SCRIPT	= "__cf_script";
	public final static String J_SCRIPT		= "__script_tag";
	public final static String CSS_TAG		= "__css_tag";
	
	public CFPartitionScanner() {

		IToken htmComment 	= new Token(HTM_COMMENT);
		IToken tag 			= new Token(ALL_TAG);
		IToken cftag 		= new Token(CF_TAG);
		IToken cfendtag 	= new Token(CF_END_TAG);
		//IToken cfdefault 	= new Token(CF_DEFAULT);
		IToken cfscript 	= new Token(CF_SCRIPT);
		IToken jscript 		= new Token(J_SCRIPT);
		IToken css 			= new Token(CSS_TAG);
		
		IPredicateRule[] rules = new IPredicateRule[13];

		//the order here is important. It should go from specific to
		//general as the rules are applied in order
		//
		// Partitions in the document will get marked in this order
		// so order is very importatnt
		
		rules[0] = new MultiLineRule("<!--", "-->", htmComment);
		//doctype rule
		rules[1] = new MultiLineRule("<!", ">", htmComment);
		
		//script block as its own highlighting
		rules[2] = new MultiLineRule("<cfscript>", "</cfscript>", cfscript);
		rules[3] = new MultiLineRule("<CFSCRIPT>", "</CFSCRIPT>", cfscript);
		rules[4] = new MultiLineRule("<style", "</style>", css);
		rules[5] = new MultiLineRule("<STYLE", "</STYLE>", css);
		rules[6] = new MultiLineRule("<script", "</script>", jscript);
		rules[7] = new MultiLineRule("<SCRIPT", "</SCRIPT>", jscript);
		
		//catch all cf tags
		rules[8] = new MultiLineRule("<cf",">", cftag);
		rules[9] = new MultiLineRule("<CF",">", cftag);
		rules[10] = new SingleLineRule("</cf",">", cfendtag);
		rules[11] = new SingleLineRule("</CF",">", cfendtag);
		
		//if there is a special tag rule, don't forget to check this class
		//this will try to paint everything in the html blue. Any additions
		//should be *before* this rule. Reorder if needed.
		//rules[12] = new TagRule(tag);
		rules[12] = new MultiLineRule("<", ">", tag);
		
		setPredicateRules(rules);
	}
}
