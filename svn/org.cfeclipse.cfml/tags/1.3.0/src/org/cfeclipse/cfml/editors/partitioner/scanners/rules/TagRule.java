/*
 * Created on Feb 2, 2004
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
package org.cfeclipse.cfml.editors.partitioner.scanners.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * 
 * @author Rob
 * Rule to handle "other" tags. Not cfml not html. Also breaks out if it looks like
 * the tag is '< ' or '<=' so cfqueries are colored correctly
 */
public class TagRule extends MultiLineRule {

    private static Pattern p = Pattern.compile("[a-zA-Z]");
    
	public TagRule(IToken token) 
	{
		super("<", ">", token);
	}
	
	protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence, boolean eofAllowed) 
	{
	    try {
		//System.out.println(sequence);
		if(sequence[0] == '<') 
		{
			int c = scanner.read();
			//< or <=
			Matcher m = p.matcher(String.valueOf(c));
			
			if(!m.find())
			{
				scanner.unread();
				return false;
			}
		}
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
