/*
 * Created on Jun 17, 2004
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
package com.rohanclan.cfml.editors.partitioner.scanners.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
//import org.eclipse.jface.text.IDocument;

/**
 * @author Rob
 *
 * Heavly influenced by the jseditor project and a file made by "agfitzp"
 */
public class PredicateWordRule extends WordRule implements IPredicateRule {

	/** Buffer used for pattern detection */
	private StringBuffer fBuffer= new StringBuffer();
	
	/** Is this word rule case sensitive or not */
	private boolean fCaseSensitive = true;
	
	/**
	 * @see org.eclipse.jface.text.rules.IPredicateRule#getSuccessToken()
	 */
	protected IToken successToken = Token.UNDEFINED;
	 
	public void addWords(String[] tokens, IToken token)
	{
		for(int i=0; i<tokens.length; i++) 
		{
		    if (fCaseSensitive) {
		        addWord(tokens[i], token);
		    }
		    else {
		        addWord(tokens[i].toLowerCase(),token);
		    }
		}
	}
	 
	public IToken getSuccessToken() 
	{
		return successToken;
	}
	
	/**
	 * @see org.eclipse.jface.text.rules.IPredicateRule#evaluate(
	 * org.eclipse.jface.text.rules.ICharacterScanner, boolean)
	 */
	public IToken evaluate(ICharacterScanner scanner, boolean resume) 
	{
		
		successToken = this.evaluate(scanner, resume);//true);
		return successToken;
	}
	
	
	public IToken evaluate(ICharacterScanner scanner) {
		int c= scanner.read();
		if (fDetector.isWordStart((char) c)) {
			if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {
				
				fBuffer.setLength(0);
				do {
					fBuffer.append((char) c);
					c= scanner.read();
				} while (c != ICharacterScanner.EOF && fDetector.isWordPart((char) c));
				scanner.unread();
				 IToken token = null;
				 
				if (fCaseSensitive) {
				    token= (IToken) fWords.get(fBuffer.toString());
				} else {
				    token= (IToken) fWords.get(fBuffer.toString().toLowerCase());
				}
				
				if (token != null)
					return token;
					
				if (fDefaultToken.isUndefined())
					unreadBuffer(scanner);
					
				return fDefaultToken;
			}
		}
		
		scanner.unread();
		return Token.UNDEFINED;
	}
	
	
	/**
	 * Creates a rule which, with the help of an word detector, will return the 
	 * token associated with the detected word. If no token has been associated, 
	 * the scanner will be rolled back and an undefined token will be returned 
	 * in order to allow any subsequent rules to analyze the characters.
	 *
	 * @param detector the word detector to be used by this rule, may not be <code>null</code>
	 *
	 * @see #addWord
	 */
	public PredicateWordRule(IWordDetector detector) 
	{
		super(detector);
	}

	/**
	 * Creates a rule which, with the help of an word detector, will return the 
	 * token associated with the detected word. If no token has been associated, 
	 * the specified default token will be returned.
	 *
	 * @param detector the word detector to be used by this rule, may not be 
	 * <code>null</code>
	 * 
	 * @param defaultToken the default token to be returned on success 
	 *		if nothing else is specified, may not be <code>null</code>
	 *
	 * @see #addWord
	 */
	public PredicateWordRule(IWordDetector detector, IToken defaultToken) 
	{
		super(detector, defaultToken);
	}


	public PredicateWordRule(IWordDetector detector, String tokenString, IToken tokenType) 
	{
		super(detector);
		this.addWord(tokenString, tokenType);
	}
	
	public PredicateWordRule(IWordDetector detector, String[] tokens, IToken tokenType) 
	{
		super(detector);
		this.addWords(tokens, tokenType);
	}

	public PredicateWordRule(IWordDetector detector, IToken defaultToken, String[] tokens, IToken tokenType) 
	{
		super(detector, defaultToken);
		this.addWords(tokens, tokenType);
	}
	
	public void setCaseSensitive(boolean caseSensitive) {
	    fCaseSensitive = caseSensitive;
	}
}
