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
import java.util.HashMap;

import com.rohanclan.cfml.dictionary.*;
import com.rohanclan.cfml.parser.exception.InvalidChildItemException;
import com.rohanclan.cfml.parser.exception.NodeNotFound;
import com.rohanclan.cfml.parser.xpath.XPathSearch;
import com.rohanclan.cfml.parser.xpath.expressions.ComparisonType;

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
public abstract class DocItem implements Comparable {
	/** The name of the item (i.e. &lt;cfscript&gt;) */
	protected String itemName; 
	/** The complete start-to-finish data for the item (i.e. &lt;cffunction name="asdf" ... &gt;)	 */
	protected String itemData; 
	
	/** The line number for the start of the match */
	protected int lineNumber;
	/** The start position in the document  */
	protected int startPosition;
	/** The end position in the document	 */
	protected int endPosition;
	/** The list of variables for the document	 */
	public ArrayList  docVariables;
	/** The children for this node.	 */
	protected CFNodeList docNodes;
	/** The parent of this node. */
	protected DocItem parentNode;
	/** The previous sibling node. Null if there isn't one.*/
	protected DocItem prevSiblingNode = null;
	/** The next sibling node. Null if there isn't one.*/
	protected DocItem nextSiblingNode = null;
	/** Syntax dictionary for working out important things for the parser. */
	protected SyntaxDictionary syntax = null;
	/** */
	protected State parseMessages = null;
	
	
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
		
		docNodes = new CFNodeList();
		docVariables = new ArrayList();
		parseMessages = new State("");
	}
	
	/**
	 * Constructor for a DocItem.
	 * <strong>WARNING:</warning> This is only intended to be used by the CFScript parser.
	 *
	 */
	public DocItem() {
		
	}
	
	protected State getParseState()
	{
		return parseMessages;
	}
	
	protected void addParseMessage(ParseMessage newMsg)
	{
	//System.out.println("DocItem::addParseMessage() - Adding message " + newMsg.getMessage());
		parseMessages.addMessage(newMsg);
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
	
	public DocItem getFirstChild()
	{
		return (DocItem)docNodes.get(0);
	}
	
	public DocItem getLastChild()
	{
		return (DocItem)docNodes.get(docNodes.size());
	}
	
	/**
	 * gets the parent of this docitem
	 * @return
	 */
	public DocItem getParent()
	{
		return parentNode;
	}
	
	public void setParent(DocItem newParent)
	{
		parentNode = newParent;
	}
	
	public void setPrevSibling(DocItem newPrevSibling)
	{
		prevSiblingNode = newPrevSibling;
	}
	
	public void setNextSibling(DocItem newNextSibling)
	{
		nextSiblingNode = newNextSibling;
	}
	
	public CFNodeList getChildNodes()
	{
		return docNodes;
	}
	/**
	 * 
	 * @deprecated Please use getChildNodes() instead
	 * @see com.rohanclan.cfml.parser.DocItem::getChildNodes()
	 */
	public ArrayList getChildren()
	{
		return docNodes;
	}
	
	public int getLineNumber() { return lineNumber; }
	
	/**
	 * Adds a child to this item's child node list. 
	 * It first asks the new item whether it's allowed to belong to this
	 * item, for example &lt;cfelse&gt; tags must only be a child of an &lt;cfif&gt; tag.
	 * @param newItem The new document item to add.
	 * @return true - child added, false - error with child.
	 */
	public boolean addChild(DocItem newItem) 
	{
		boolean addOkay = true;
		
		if(!newItem.validChildAddition(this))
		{
			parseMessages.addMessage(new ParseError(newItem.getLineNumber(), newItem.getStartPosition(), newItem.getEndPosition(), newItem.getItemData(),
										"Invalid child \'" + newItem.getName() + "\' for parent \'" + getName() + "\'"));
			addOkay = false;
		}
		//
		// Set the item's parent & sibling
		newItem.setParent(newItem);
		if(docNodes.size() == 0)
			newItem.setPrevSibling(null);
		else 
			newItem.setPrevSibling((DocItem)docNodes.get(docNodes.size()-1));
		
		docNodes.add(newItem);
		
		return addOkay;
	}
	
	/**
	 * Inserts the node newChild before existing node refChild. If refChild is null, insert newChild at end of the list of children.
	 * @param newChild The new child node to insert
	 * @param refChild The reference node, i.e. the node before which the new node must be inserted.
	 * @throws InvalidChildItemException Raised if <code>newChild</code> being added is not valid for this node type.
	 * @throws NodeNotFound Raised if the <code>refChild</code> node is not found in the node's children. 
	 */
	public void insertBefore(DocItem newChild, DocItem refChild) 
				throws InvalidChildItemException, NodeNotFound
	{
		if(!newChild.validChildAddition(this))
			throw new InvalidChildItemException("Child item of type \'" + newChild.getName() + "\' says it is not allowed to belong to this (\'" + itemName + "\') doc item");
		
		int insertPos = docNodes.size();
		
		//
		// Does the refChild exist and exists in this node's children?
		if(refChild != null && docNodes.contains(refChild))
			insertPos = docNodes.indexOf(refChild);
		else if(refChild != null)	// Isn't null & doesn't belong to this node. Argh!
			throw new NodeNotFound("Cannot find node \'" + refChild.getName() +"\'");
		
		docNodes.add(insertPos, newChild);
	}
	
	/**
	 * Removes the specified child from the list of nodes.
	 * @param oldChild The node to remove
	 * @return The node removed.
	 * @throws NodeNotFound Raised if the <code>refChild</code> node is not found in the node's children.
	 */
	public DocItem removeChild(DocItem oldChild) throws NodeNotFound
	{
		if(!docNodes.remove(oldChild))
			throw new NodeNotFound("Cannot find node \'" + oldChild.getName() +"\'");
		
		return oldChild;
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
	
	private CFNodeList selectNodes(XPathSearch search) {
		CFNodeList result = new CFNodeList();		
		
		for(int i = 0; i < docNodes.size(); i++)
		{
			DocItem currItem = (DocItem)docNodes.get(i);
			int matches = 0;
			
			if(search.doChildNodes)
				result.addAll(currItem.selectNodes(search));
			
			if(search.searchForTag && currItem.getName().compareToIgnoreCase(search.tagName) == 0)
				matches++;
	//System.out.print("DocItem::selectNodes() - Testing \'" + currItem.getName() + "\'");
			if(search.attrSearch.containsKey(XPathSearch.ATTR_STARTPOS)) {
				ComparisonType comp = (ComparisonType)search.attrSearch.get(XPathSearch.ATTR_STARTPOS);
		//System.out.println(XPathSearch.ATTR_STARTPOS + ": ");
				if(comp.performComparison(currItem.startPosition))
				{
					matches++;
			//System.out.print(" success ");
				}
				else {
			//System.out.println(" failed ");
				}
					
			}
			if(search.attrSearch.containsKey(XPathSearch.ATTR_ENDPOS)) {
		//System.out.print(XPathSearch.ATTR_ENDPOS + ":");
				ComparisonType comp = (ComparisonType)search.attrSearch.get(XPathSearch.ATTR_ENDPOS);
				if(comp.performComparison(currItem.endPosition)) {
					matches++;
			//System.out.print(" success ");
				}
				else {
			//System.out.println(" failed ");
				}
			}
			
			if(matches == search.getMatchesRequired())
			{
			//System.out.println("DocItem::selectNodes(XPathSearch) - Got match for " + currItem.itemName);
				result.add(currItem);
		//System.out.print(" name match success");
			}
			else {
		//System.out.print(" name match failed with ");
			}
	//System.out.println("");
		}				
		
		return result;
	}
	
	public CFNodeList selectNodes(String searchString)/* throws Exception */
	{
		CFNodeList result = new CFNodeList();
		XPathSearch search = new XPathSearch();
		if(!search.parseXPath(searchString)) {
			//throw new Exception("XPath string \'" + searchString + "\' was invalid");
		}
		return selectNodes(search); 
	}
	
	/**
	 * The final parse check. This is to be run just before the object's
	 * parse messages are retrieved. Each document object will run a sanity
	 * test to ensure that it is valid. For example a CfmlTagFunction will check
	 * to make sure that it has the 'name' attribute.
	 * @return true - item is sane, false - item is not sane.
	 */
	public boolean IsSane()
	{
		return true;
	}
	
	/**
	 * This is my sad attempt to make the content outline faster
	 * it doesnt seem to do much. We can probably remove it but it's here
	 * so - eh
	 * @author Rob
	 */
	public int compareTo(Object o)
	{
		if(o == null) throw new NullPointerException("DocItem compareTo got a null");
		
		if(o instanceof DocItem)
		{
			return o.toString().compareTo(this.toString());
		}
		
		return 0;
	}
	
	/**
	 * This is my sad attempt to make the content outline faster
	 * it doesnt seem to do much. We can probably remove it but it's here
	 * so - eh
	 * @author Rob
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof DocItem)
		{
			//if it has the same name and number of parameters assume its
			//the same (this may need to be adjusted in the future)
			if( ((DocItem)obj).toString().equals(toString()) )
			{
				//System.err.println("we are equal: " + toString());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * override the toString so we can compare (might need to move this to a 
	 * less obvious method)
	 * @author Rob
	 */
	public String toString()
	{
		//weak, but should work, unique id
		return itemName + ":" + lineNumber + ":" + startPosition + ":" + endPosition;
	}
}