/*
 * Created on Mar 21, 2004
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
import com.rohanclan.cfml.dictionary.*;
import com.rohanclan.cfml.parser.exception.InvalidChildItemException;

/** 
 * The DocItem class is intended to be the abstract base class for parsing and representing 
 * internally the structure of a document.
 *  
 * It has a name, a item specific name for the item. Examples might be 'cfset' in the 
 * case of CFML, or "DoAQuery" in the case of a UDF.
 *  
 * DocItem is an abstract class, intended to be the base for representing an in-document item, 
 * be it a ScriptItem (like a scripted function) or a CFML tag such as <cfswitch>, <cfcase> or 
 * CFC stuff (which I'm most interested in!). DocItem stores the name, lineNumber and start & 
 * finish offsets within the document so that we can precisely locate it.
 *  
 * DocItem also stores the children, therefore it can act as a branch for part of a tree. 
 * Hence the addChild() method. Haven't added anything else for retrieving children... because I'm lazy :)
 */
public abstract class DocItem {
	/**
	 * The name of the item (i.e. <cfscript>)
	 */
	protected String itemName;
	/**
	 * The complete start-to-finish data for the item (i.e. <cffunction name="asdf" ... >)
	 */
	protected String itemData; 
	
	/**
	 * The line number for the start of the match
	 */
	protected int lineNumber;
	/**
	 * The start position in the document
	 */
	protected int startPosition;
	/**
	 * The end position in the document
	 */
	protected int endPosition;
	/**
	 * The list of variables for the document
	 */
	public ArrayList  docVariables;
	/**
	 * The children for this node.
	 */
	public ArrayList docNodes;
	
	/*
	 * Syntax dictionary for working out important things for the parser.
	 */
	protected SyntaxDictionary syntax = null;
	
	/**
	 * Initialises the dictionary
	 * @param newDict the dictionary to init with
	 */
	public void initDictionary(SyntaxDictionary newDict)
	{
		syntax = newDict;
	}
	
	/**
	 * Returns itemData
	 * @return the data for the item
	 */
	public String getItemData()
	{
		return itemData;
	}
	/**
	 * Sets the item's data. Shouldn't really be called.
	 * @param data
	 */
	public void setItemData(String data)
	{
		itemData = data;
	}
	/**
	 * Constructor. 
	 * @param line - the line that the match was made
	 * @param startDocPos - the start point in the doc
	 * @param endDocPos - the end point in the doc
	 * @param name - the name for the tag (i.e. <html> without the chevrons)
	 */
	public DocItem(int line, int startDocPos, int endDocPos, String name) 
	{
		lineNumber = line;
		startPosition = startDocPos;
		endPosition = endDocPos;
		itemName = name;
		
		docNodes = new ArrayList();
		docVariables = new ArrayList();
	}
	public String getName() 
	{
		return itemName;
	}
	public int getStartPosition() {
		return startPosition;
	}
	public int getEndPosition() {
		return endPosition;
	}
	public DocItem getMatchingItem() {
		return null;
	}
	
	public boolean hasChildren()
	{
		return docNodes.size() > 0;
	}
	
	public ArrayList getChildren()
	{
		return docNodes;
	}
	
	public int getLineNumber() { return lineNumber; }
	
	/**
	 * Adds a child to the child node list. 
	 * Before it does so it calls 
	 * @param newItem
	 */
	public void addChild(DocItem newItem) throws InvalidChildItemException 
	{
		if(!newItem.validChildAddition(this))
			throw new InvalidChildItemException("Child item of type \'" + newItem.getName() + "\' says it is not allowed to belong to this (\'" + itemName + "\') doc item");
		docNodes.add(newItem);
	}
	
	/**
	 * Intended to be called from a DocItem's addChild() method. Asks the new child item to check whether
	 * it is allowed to belong to the parent item.
	 * <b>NB:</b> The default implementation <b>ALWAYS</b> returns false. Override in any derived classes.
	 * @param parentItem
	 * @return
	 */
	public boolean validChildAddition(DocItem parentItem)
	{
		return false;
	}
}