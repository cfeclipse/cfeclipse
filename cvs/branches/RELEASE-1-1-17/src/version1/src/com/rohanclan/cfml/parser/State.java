/*
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.rohanclan.cfml.parser.docitems.TagItem;

/**
 * Represents the current state of the parser.
 * 
 * @author Oliver Tupman
 */
public class State 
{
	protected ArrayList messages = new ArrayList();
	protected String filename;
	protected int errCount = 0;
	protected boolean hadFatal = false;
	protected ArrayList matches = new ArrayList();
	
	//
	// The following is to keep track of function & variable names
	// TODO: I think the following should be a map so we can store the doc items against name for type recognition, etc.
	protected HashMap functionNames = new HashMap();
	protected HashMap variableNames = new HashMap();
	
	static public final int ADD_BEFORE = 0x01;
	static public final int ADD_AFTER =  0x02;

	/**
	 * Returns the ParseItemMatch'es that were found in a document during a parse.
	 * Intended only to be used by the parser itself.
	 * 
	 * @return ArrayList of ParseItemMatch'es
	 */
	public ArrayList getMatches()
	{
		return matches;
	}
	
	/**
	 * The document being parsed.
	 * 
	 * @param docFilename Filename of the document being parsed.
	 */
	public State(String docFilename)
	{
		filename = docFilename;
	}
	
	/**
	 * Returns the list of messages that have been reported during the 
	 * parse.
	 * 
	 * @return
	 */
	public ArrayList getMessages()
	{
		return messages;
	}
	
	/**
	 * Adds a parser match to the parser state.
	 * It's added dependant on position, values should be either ADD_BEFORE
	 * or ADD_AFTER
	 * 
	 * @param newMatch The parser match found
	 * @param position The position - before or after the most recent match
	 * @param numIndicies Number of indicies to move from.
	 */
	public void addMatch(ParseItemMatch newMatch, int position, int numIndicies)
	{
		switch(position)
		{
			case ADD_BEFORE:
				matches.add(matches.size() - numIndicies, newMatch);
				break;
			case ADD_AFTER:
				addMatch(newMatch);
				break;
			default:
				// Should this raise an exception?
				break;
		}
	}
	
	/**
	 * Simply adds a match to the current match list.
	 * 
	 * @param newMatch The new match to add
	 */
	public void addMatch(ParseItemMatch newMatch)
	{
		matches.add(newMatch);
	}
	
	/**
	 * Returns whether this parse has experienced a fatal error or not.
	 * @return True - a fatal error has occured, false - lets go onwards!
	 */
	public boolean hadFatal() { return hadFatal; }
	
	/**
	 * Adds a message to the parser state.
	 * @param newMsg The message to report to the user post-parse.
	 */
	public void addMessage(ParseMessage newMsg)
	{
		if(newMsg instanceof ParseError)
		{
			if(((ParseError)newMsg).isFatal())
				hadFatal = true;
			
			errCount++;				
		}
		
		messages.add(newMsg);
	}
	
	/**
	 * Adds a whole bunch of messages to the message list.
	 * 
	 * @param newMessages ArrayList of ParseMessage's
	 */
	public void addMessages(ArrayList newMessages)
	{
		Iterator msgIter = newMessages.iterator();
		ParseMessage currMsg = null;
		while(msgIter.hasNext())
		{
			currMsg = (ParseMessage)msgIter.next();
			if(currMsg instanceof ParseError)
			{
				if(((ParseError)currMsg).isFatal())
					hadFatal = true;
					
				errCount++;
			}
			messages.add(currMsg);
		}
	}
}