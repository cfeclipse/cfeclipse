package com.rohanclan.cfml.parser;

import java.util.ArrayList;
import com.rohanclan.cfml.dictionary.*;

/** The DocItem class is intended to be the abstract base class for parsing and representing internally the structure of a document.
 *  
 *  It has a name, a item specific name for the item. Examples might be 'cfset' in the case of CFML, or "DoAQuery" in the case of a UDF.
 *  
 *  DocItem is an abstract class, intended to be the base for representing an in-document item, be it a ScriptItem (like a scripted function) or a CFML tag such as <cfswitch>, <cfcase> or CFC stuff (which I'm most interested in!). DocItem stores the name, lineNumber and start & finish offsets within the document so that we can precisely locate it.
 *  
 *  DocItem also stores the children, therefore it can act as a branch for part of a tree. Hence the addChild() method. Haven't added anything else for retrieving children... because I'm lazy :)
 */
public abstract class DocItem {
	protected String itemName;
	protected String itemData; 
	
	protected int lineNumber;
	protected int startPosition;
	protected int endPosition;
	public ArrayList  docVariables;
	public ArrayList docNodes;
	
	protected SyntaxDictionary syntax = null;
	
	public void initDictionary(SyntaxDictionary newDict)
	{
		syntax = newDict;
	}
	
	public String getItemData()
	{
		return itemData;
	}
	
	public void setItemData(String data)
	{
		itemData = data;
	}
	
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