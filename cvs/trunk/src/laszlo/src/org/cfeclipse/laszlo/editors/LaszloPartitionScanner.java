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
import com.rohanclan.cfml.dictionary.SyntaxDictionaryInterface;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;
import com.rohanclan.cfml.editors.partitioner.scanners.rules.TagRule;

public class LaszloPartitionScanner extends RuleBasedPartitionScanner {
	
	public LaszloPartitionScanner() {
		IToken doctype	 	= new Token(CFPartitionScanner.DOCTYPE);
		IToken xmlComment 	= new Token(CFPartitionScanner.HTM_COMMENT);
		IToken tag 			= new Token(CFPartitionScanner.ALL_TAG);
		IToken script 		= new Token(CFPartitionScanner.J_SCRIPT);
		//
		IToken form			= new Token(CFPartitionScanner.FORM_TAG);
		IToken table			= new Token(CFPartitionScanner.TABLE_TAG);
		IToken unktag		= new Token(CFPartitionScanner.UNK_TAG);
		
		List rules = new ArrayList();
		
		//declare rule
		rules.add(new MultiLineRule("<?", "?>", doctype));
		//comments
		rules.add(new MultiLineRule("<!--", "-->", xmlComment));
		
		rules.add(new MultiLineRule("<script","</script>",script));
		rules.add(new MultiLineRule("<method","</method>",script));
				
		SyntaxDictionary sd = DictionaryManager.getDictionary(LaszloSyntaxDictionary.LASDIC);
		
		Tag tg = null;
		try
		{
			Set elements = ((SyntaxDictionaryInterface)sd).getAllElements();
			
			//this is going to be used to tell if we need to add a form, table,
			//or normal tag for the html tags
			IToken tmp = tag;
			
			//loop over all the tags in the html dictionary and try to set the 
			//partition to the correct type
			Iterator it = elements.iterator();
			while(it.hasNext())
			{
				String ename = (String)it.next();
				
				//script and style are handled above (they are special)
				if(!ename.equals("script"))
				{
					tg = sd.getTag(ename);
					
					if(tg != null){
						
						//colour (<=for ollie) form and table tags differently...
						if(tg.isTableTag()){	tmp = table; }
						else if(tg.isFormTag()){ tmp = form; }
						else { tmp = tag; }
						
						rules.add(new MultiLineRule("<" + ename,">", tmp));
						//if this is supposed to have an end tag add it too
						if(!tg.isSingle())
						{	
							rules.add(new MultiLineRule("</" + ename,">", tmp));
						}
					}else{
						System.err.println(ename + " is null?");
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
		rules.add(new TagRule(unktag));
		
		IPredicateRule[] rulearry = new IPredicateRule[rules.size()];
		rules.toArray(rulearry);
		
		setPredicateRules(rulearry);
	}
}
