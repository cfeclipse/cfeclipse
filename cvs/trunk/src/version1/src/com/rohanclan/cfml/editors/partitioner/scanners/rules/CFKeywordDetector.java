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
package com.rohanclan.cfml.editors.partitioner.scanners.rules;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * @author Rob
 *
 * I am not sure I really understand what this file is supposed to do. I made it
 * because WordRule needs it to find keywords, but something is not right or
 * I am not doing this right. It just basically returns true if the char is a
 * letter to start and a letter after that - which seems stupid.
 */
public class CFKeywordDetector implements IWordDetector {
	
	/**
	 * Sees if this could be the start of a keyword
	 */
	public boolean isWordStart(char character) 
	{
		//make sure its a valid char (keywords should start with
		//a letter or underscore ... ) TODO this needs work :-/
		return Character.isLetter(character) || (character == '_');
	}
	
	/**
	 * Sees if this could be part of a keyword
	 */
	public boolean isWordPart(char character) 
	{
		//make sure any following char is a valid one
		return Character.isLetterOrDigit(character) || (character == '-') 
			|| (character == '_');
	} 
}

