/*
 * Created on Apr 25, 2004
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