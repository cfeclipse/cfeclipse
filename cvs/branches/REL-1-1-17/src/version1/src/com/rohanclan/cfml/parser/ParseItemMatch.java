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
 * Represents a match that is of some interest to the parser.
 * 
 * @author Oliver Tupman
 */
public class ParseItemMatch {
	
	
	/**
	 * The type of match this is. 
	 * @see CFParser
	 */
	protected int itemType;
	
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
	public ParseItemMatch(String text, int start, int end, int lineNum, int matchType)
	{
		this.match = text;
		this.startPos = start;
		this.endPos = end;
		this.lineNumber = lineNum;
		this.itemType = matchType;
	}
	
	public String toString()
	{
		return this.match;
	}

	/**
	 * Gets the start position of this match.
	 * 
	 * @return The start position
	 */
	public int getStartPos() {
		return this.startPos;
	}
	
	/**
	 * Gets the end position of this match
	 * @return The end position
	 */
	public int getEndPos() {
		return this.endPos;
	}
	
	/**
	 * Gets the <b>start</b> line number of this item.
	 * TODO: Store the end line number as well.
	 * 
	 * @return The line number on which this item starts on
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	/**
	 * Gets the data that was matched.
	 * @return The string-based data that was matched
	 */
	public String getMatch() {
		return this.match;
	}
	
	/**
	 * Gets the type of data that was matched.
	 * @see CFParser
	 * @return Returns the match type
	 */
	public int getMatchType() {
		return this.itemType;
	}
}