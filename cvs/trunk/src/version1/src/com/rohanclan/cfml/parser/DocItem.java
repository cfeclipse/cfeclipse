package com.rohanclan.cfml.parser;

import java.util.ArrayList;
import com.rohanclan.cfml.dictionary.*;

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
		return null;
	}
	public int getStartPosition() {
		return 0;
	}
	public int getEndPosition() {
		return 0;
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
	
	public void addChild(DocItem newItem) 
	{
		docNodes.add(newItem);
	}
}