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

/**
 * @author Rob
 *
 * This scans the overall document and slices it into partitions. Then the
 * partition scanners are applied to those partitions
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.ISyntaxDictionary;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.CustomTagRule;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.NamedTagRule;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.NestableMultiLineRule;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.TagRule;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.TaglibRule;

public class CFPartitionScanner extends RuleBasedPartitionScanner {
	//public final static String CF_DEFAULT 	= "__cf_default";
	public final static String DOCTYPE	 	= "__doctype";
	public final static String CF_COMMENT		= "__cf_comment";
	public final static String HTM_COMMENT 	= "__htm_comment";
	public final static String CF_START_TAG = "__cf_start_tag";
	public final static String CF_START_TAG_BEGIN		= "__cf_start_tag_begin";
	public final static String CF_START_TAG_END		= "__cf_start_tag_end";
	public final static String CF_TAG_ATTRIBS		= "__cf_tag_attribs";
	public final static String CF_SET_STATEMENT = "__cf_set_statment";
	public final static String CF_RETURN_STATEMENT = "__cf_return_statement";
	public final static String CF_BOOLEAN_STATEMENT = "__cf_boolean_statement";
	public final static String CF_END_TAG		= "__cf_end_tag";
	public final static String HTM_START_TAG 		= "__htm_start_tag";
	public final static String HTM_END_TAG 		= "__htm_end_tag";
	public final static String HTM_START_TAG_BEGIN		= "__htm_start_tag_begin";
	public final static String HTM_START_TAG_END		= "__htm_start_tag_end";
	public final static String HTM_TAG_ATTRIBS		= "__htm_tag_attribs";
	public final static String CF_SCRIPT		= "__cf_script";
	public final static String CF_EXPRESSION		= "__cf_expression";
	public final static String J_SCRIPT		= "__jscript";
	public final static String CSS		= "__css";
	public final static String SQL		= "__sql";
	public final static String TAGLIB_TAG		= "__taglib_tag";
	public final static String UNK_TAG		= "__unk_tag";
	//form and table
	public final static String FORM_END_TAG		= "__form_end_tag";
	public final static String FORM_START_TAG		= "__form_start_tag";
	public final static String FORM_START_TAG_BEGIN		= "__form_start_tag_begin";
	public final static String FORM_TAG_ATTRIBS		= "__form_tag_attribs";
	public final static String FORM_START_TAG_END		= "__form_start_tag_end";
	public final static String TABLE_END_TAG		= "__table_end_tag";
	public final static String TABLE_START_TAG		= "__table_start_tag";
	public final static String TABLE_START_TAG_BEGIN		= "__table_start_tag_begin";
	public final static String TABLE_TAG_ATTRIBS		= "__table_tag_attribs";
	public final static String TABLE_START_TAG_END		= "__table_start_tag_end";
	
	
	public CFPartitionScanner() {
		IToken doctype	 	= new Token(DOCTYPE);
		IToken cfComment 	= new Token(CF_COMMENT);
		IToken htmComment 	= new Token(HTM_COMMENT);
		IToken taglibtag		= new Token(TAGLIB_TAG);
		IToken unktag		= new Token(UNK_TAG);
		
		
		List rules = new ArrayList();
		
		//the order here is important. It should go from specific to
		//general as the rules are applied in order
		
		// Partitions in the document will get marked in this order
		rules.add(new NestableMultiLineRule("<!---","--->",cfComment));
		//rules.add(new TagRule(htmComment));
		rules.add(new NestableMultiLineRule("<!--", "-->", htmComment));
		//doctype rule
		rules.add(new MultiLineRule("<!doctype", ">", doctype));
		
		// Handle the if/elsief/set/return tag partitioning
		rules.add(new NamedTagRule("<cfset",">", CF_START_TAG, CF_SET_STATEMENT));
		rules.add(new NamedTagRule("<cfif",">", CF_START_TAG, CF_BOOLEAN_STATEMENT));
		rules.add(new NamedTagRule("<cfelseif",">", CF_START_TAG, CF_BOOLEAN_STATEMENT));
		rules.add(new NamedTagRule("<cfreturn",">", CF_START_TAG, CF_RETURN_STATEMENT));
		
		/*
		 * TODO: Need to add some code to track the partition changes
		 * as we run the nextToken() method.
		 * 
		 * As each partition change occurs it needs to see if the 
		 * default partition needs to change.
		 * 
		 * Also need to look into some method for having <cfset***> with 
		 * the starter and ender being separate partitions but easily 
		 * identifiable as part of the same tag for parser purposes. Possibly 
		 * through some sort of __expression_wrapper_start, 
		 * __expression_wrapper_end pair that precede and follow the
		 * __cf_expression partition type. That would allow us to handle ##
		 * pairs as well, but we need to also think about syntax highlighting
		 * for those.
		 * 
		 * Need to provide simple methods in the partitioner or document to
		 * retrieve the stored partition details.
		 * 
		 */
		
		SyntaxDictionary sd = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
		
		Tag tg = null;
		try
		{
			Set elements = ((ISyntaxDictionary)sd).getAllElements();
			
			Iterator it = elements.iterator();
			while(it.hasNext())
			{
				String ename = (String)it.next();
				//System.out.println(ename);

					tg = sd.getTag(ename);
					rules.add(new NamedTagRule("<" + ename,">", CF_START_TAG, CF_TAG_ATTRIBS));
					//if(!tg.isSingle())
					//{	
						rules.add(new NamedTagRule("</" + ename,">", CF_END_TAG, CF_END_TAG));
					//}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		//these are not really handled in the dictionary because you can call 
		//normal pages as cf_'s
		rules.add(new CustomTagRule("<cf_",">", CF_START_TAG, CF_TAG_ATTRIBS));
		rules.add(new CustomTagRule("</cf_",">", CF_END_TAG, CF_END_TAG));
		
		//do the html tags now
		sd = DictionaryManager.getDictionary(DictionaryManager.HTDIC);
		
		try
		{
			Set elements = ((ISyntaxDictionary)sd).getAllElements();
			
			//this is going to be used to tell if we need to add a form, table,
			//or normal tag for the html tags
			String startTokenType = HTM_START_TAG;
			String endTokenType = HTM_END_TAG;
			String midTokenType = HTM_TAG_ATTRIBS;
			
			//loop over all the tags in the html dictionary and try to set the 
			//partition to the correct type
			Iterator it = elements.iterator();
			while(it.hasNext())
			{
				String ename = (String)it.next();
				
				
					tg = sd.getTag(ename);
					
					//colour form and table tags differently...
					if(tg.isTableTag()) {	
					    startTokenType = TABLE_START_TAG;
					    midTokenType = TABLE_TAG_ATTRIBS;
					    endTokenType = TABLE_END_TAG; 
					}
					else if(tg.isFormTag()) { 
					    startTokenType = FORM_START_TAG;
					    midTokenType = FORM_TAG_ATTRIBS;
					    endTokenType = FORM_END_TAG; 
					}
					else { 
					    startTokenType = HTM_START_TAG; 
					    midTokenType = HTM_TAG_ATTRIBS;
					    endTokenType = HTM_END_TAG;
					}
					
					rules.add(new NamedTagRule("<" + ename,">", startTokenType, midTokenType));
					rules.add(new NamedTagRule("</" + ename,">", endTokenType, endTokenType));

				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}

		// Taglibs that may have been imported
		// <foo:bar> type tags
		rules.add(new TaglibRule(taglibtag));
		
		//catch any other tags we dont know about (xml etc) and make them
		//a different color
		rules.add(new TagRule(unktag));
		
		//rules.add(new WhitespaceRule(new CFWhitespaceDetector()));
		
		IPredicateRule[] rulearry = new IPredicateRule[rules.size()];
		rules.toArray(rulearry);
		
		setPredicateRules(rulearry);
	}
	
	public int getOffset() {
	    return this.fOffset;
	}
	
	/**
	 * Runs all configured rules at the current document offset and returns a token
	 * to indicate what the partition type should be at that offset. The scanner 
	 * offset is automatically updated.
	 */
	public IToken nextToken() {
			/*
			 * If we are not currently in a partition and 
			 * there are some rules defined we run through
			 * the rules and look for a token.
			 */
			if (this.fContentType == null || this.fRules == null) {
				IToken token;
				// Keep running until we find something.
				while (true) {
					// Reinitialize the token and column markers.
				    this.fTokenOffset= this.fOffset;
				    this.fColumn= UNDEFINED;
					
					if (this.fRules != null) {

							
						int c = -1;
						/*
						 * Short-circuit the rule check if the next character
						 * isn't a '<'. This assumes that every rule has '<'
						 * as the first character of the start sequence. Currently
						 * this is the case, so it should be ok.
						 * 
						 */
						while(true) {
						    c = read();
						    if (c == EOF) {
						        return Token.EOF;
						    }
						    if (c != '<') {
						        return this.fDefaultReturnToken;
						    }
						    break;
						}
						unread();
						 
						for (int i= 0; i < this.fRules.length; i++) {
							// Get the token for the current rule.
							token= (this.fRules[i].evaluate(this));
							// Check if the rule found anything
							if (!token.isUndefined()) {
							    // Return the token.
							    return token;
							}
						}
					}
					
					// If we got to here, none of the rules found a token.
					
					// Check if we're at the end of the file. If so, return the relevant token.
					if (read() == EOF) {
						return Token.EOF;
					}
					return this.fDefaultReturnToken;
					
				}
			}
			
			// If we get to here we're already inside a partition
			
			// re-initialize column constraint
			this.fColumn= UNDEFINED;
			// Check if we are inside a partition.
			boolean resume= (this.fPartitionOffset > -1 && this.fPartitionOffset < this.fOffset);
			// Set the token offset according to whether we're in a partition or not.
			this.fTokenOffset= resume ? this.fPartitionOffset : this.fOffset;
			
			
			IPredicateRule rule;
			IToken token;
			// Run through all the rules looking for a match.
			for (int i= 0; i < this.fRules.length; i++) {
				rule= (IPredicateRule) this.fRules[i];
				/* 
				 * Get the success token for this rule so we can 
				 * check if it matches the current content type.
				 */
				token = rule.getSuccessToken();
				//System.out.println("Checking if content type - " + fContentType + " matches " + token.getData());
				if (this.fContentType.equals(token.getData().toString())
				        || this.fContentType.equals(token.getData().toString() + "_begin")
				        || this.fContentType.equals(token.getData().toString() + "_end")
				        || this.fContentType.equals(token.getData().toString() + "_attribs")) {
					// The content type matches, so we want to run the resume on the rule.
					token= rule.evaluate(this, resume);
					if (!token.isUndefined()) {
					    this.fContentType= null;
						return token;
					}
				}
			}

			// haven't found any rule for this type of partition
			this.fContentType= null;
			if (resume) {
			    this.fOffset= this.fPartitionOffset;
			}
			return super.nextToken();
		}

}
