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
 * 
 * @author Oliver Tupman
 *
 * Represents the current state of the parser.
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
	/*
	public void addFunction(TagItem newFunction)
	{
		String funcName = newFunction.getAttribute("name");
		if(functionNames.containsKey(funcName))
		{
			addMessage(new ParseError(newFunction.lineNumber, newFunction.startPosition,
											newFunction.endPosition, newFunction.getItemData(), 
											"Duplicate function \'" + funcName + "\' found."));
		}
		else
			functionNames.put(funcName, funcName);
	}
	*/
	public ArrayList getMatches()
	{
		return matches;
	}
	
	public State(String docFilename)
	{
		filename = docFilename;
	}
	
	public ArrayList getMessages()
	{
		return messages;
	}
	
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
	
	public void addMatch(ParseItemMatch newMatch)
	{
		matches.add(newMatch);
	}
	
	public boolean hadFatal() { return hadFatal; }
	
	/**
	 * Adds a message to the parser state.
	 * @param newMsg
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