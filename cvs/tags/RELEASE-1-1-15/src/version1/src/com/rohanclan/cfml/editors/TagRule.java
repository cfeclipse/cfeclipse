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
package com.rohanclan.cfml.editors;

import org.eclipse.jface.text.rules.*;

/**
 * 
 * @author Rob
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * @deprecated
 */
public class TagRule extends MultiLineRule {

	public TagRule(IToken token) {
		super("<!--", "-->", token);
	}
	
	protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence,
		boolean eofAllowed) 
	{
		System.out.println(sequence);
		
		if(sequence[0] == '<') 
		{
			int c = scanner.read();
			//<!
			if(c == '!')
			{
				c = scanner.read();
				//<!-
				if(c == '-')
				{
					c = scanner.read();
					
					//<!--
					if(c == '-')
					{
						//html comment
						c = scanner.read();
						
						//<!---
						if(c == '-')
						{
							//a cfcomment!
							System.err.println("cfcomment!");
							return true;
						}
						else
						{
							scanner.unread();
							scanner.unread();
							scanner.unread();
							scanner.unread();
							return false;
						}
					}
					else
					{
						scanner.unread();
						scanner.unread();
						scanner.unread();
						return false;
					}
				}
				else
				{
					scanner.unread();
					scanner.unread();
					return false;
				}
			}
			else
			{
				scanner.unread();
				return false;
			}
			
			//scanner.unread();
			//System.out.println("::" + (char)c);
			//if(c == '-')
			//{
				//cfcomment
			//	System.err.println("cfcomment!");
			//	scanner.unread();
			//	return true;
			//}
			//else
			//{
				//html comment
				//scanner.unread();
				//return false;
			//}
						
			//System.err.println((char)c);
			//if (c == '!') 
			//{
			//	scanner.unread();
				// comment - abort
			//	return false;
			//}
			////////////////////////////////////////////////////////////////////
			//ALL CFTAGS
			//we need to skip here if its a cf tag
			//for some reason - else the code completion
			//wont get invoked
			//if ( (c == '/' && f == 'c') || (c == 'c' && f == 'f') )
			/* if(c == 'c' || c == 'C')
			{
				c = scanner.read();
				//System.err.println(" " + (char)c);
				if(c == 'f' || c == 'F')
				{
					//probably a cf tag - abort
					scanner.unread();
					return false;
				}
			}
			
			if(c == '/')
			{
				c = scanner.read();
				//System.err.println(" " + (char)c);
				if(c == 'c' || c == 'C')
				{
					c = scanner.read();
					//System.err.println("  " + (char)c);
					if(c == 'f' || c == 'F')
					{
						scanner.unread();
						return false;
					}
				}
			}
			
			////////////////////////////////////////////////////////////////////
			//STYLE / SCRIPT tags
			if(c == 's' || c == 'S')
			{
				c = scanner.read();
				//Could be "style" but could be "strong"
				if(c == 't' || c == 'T')
				{
					c = scanner.read();
					//its probably style - so skip
					if(c == 'y' || c == 'Y')
					{
						scanner.unread();
						return false;
					}
				}
				else if(c == 'c' || c == 'C')
				{
					//probably "script" - skip
					scanner.unread();
					return false;
				}
			}
			// now for the closing parts
			if(c == '/')
			{
				c = scanner.read();
				if(c == 's' || c == 'S')
				{
					c = scanner.read();
					//Could be "style" but could be "strong"
					if(c == 't' || c == 'T')
					{
						c = scanner.read();
						//its probably style - so skip
						if(c == 'y' || c == 'Y')
						{
							scanner.unread();
							return false;
						}
					}
					else if(c == 'c' || c == 'C')
					{
						//probably "script" - skip
						System.err.println("I hit /sc");
						scanner.unread();
						return false;
					}
				}
			}
			
			 */
		}
		else if (sequence[0] == '>') 
		{
			scanner.unread();
		}
		
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
