/*
 * Created on Apr 25, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
package com.rohanclan.cfml.parser;
/**
 * @author Oliver Tupman
 *
 * <code>TagMatch</code> is a class to represent a match within a document. 
 */
public class TagMatch {
	/**
	 * <code>match</code> - The full text of the tag match made.
	 */
	public String match;
	/**
	 * <code>startPos</code> - The document offset where the match began
	 */
	public int startPos;
	/**
	 * <code>endPos</code> - The document offset where the match ended.
	 */
	public int endPos;
	
	/**
	 * <code>lineNumber</code> - the line number on which this occured.
	 * TODO: Actually set the line number.
	 */
	public int lineNumber;
	
	/**
	 * <code>TagMatch</code> - Constructor for the TagMatch class. 
	 * @param text
	 * @param start
	 * @param end
	 */
	public TagMatch(String text, int start, int end, int lineNum)
	{
		match = text;
		startPos = start;
		endPos = end;
		lineNumber = lineNum;
	}
	
	public String toString()
	{
		return this.match;
	}
}