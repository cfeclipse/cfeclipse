/*
 * Created on Feb 27, 2004
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
package com.rohanclan.cfml.dictionary;

import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import java.util.Map;
//import java.util.HashMap;

/**
 * @author Rob
 *
 * Handles the Sax events against a dictionary. This was ripped from Treebeard
 * with permission
 */
public class DictionaryContentHandler implements ContentHandler {
	//keep track of the location
	private Locator locator;
	//private Map namespaceMappings;
	
	private Map dtags;
	private Map dfunctions;	
	private Map dscopeVars;
	
	/** used to mark which part of the xml doc we are in */
	private String currenttag = "";
	/** current tag/function being built */
	private Procedure currentitem = null;
	private Parameter paramitem = null;
	private Function methoditem = null;
	
	public DictionaryContentHandler(Map tags, Map functions, Map scopeVars)
	{
		dtags = tags;
		dfunctions = functions;
		dscopeVars = scopeVars;
		//namespaceMappings = new java.util.HashMap();
	}
	
	/** sets the documnet locator
	 * @param locator the document locator
	 */	
	public void setDocumentLocator(Locator locator) 
	{
		this.locator = locator;
	}
	
	/**
	 * guesses if the passed string is "true" or "false"
	 * @param bstring the "true" "false" string
	 * @return the string in a boolean form
	 */
	private boolean parseBoolean(String bstring)
	{
		if(bstring.equalsIgnoreCase("true") || bstring.equalsIgnoreCase("yes"))
		{
			return true;
		}
		
		return false;
	}
	
	/** process a start element */
	public void startElement(String namespace, String localName, String str2, org.xml.sax.Attributes attributes) 
		throws SAXException 
	{
		//save the current tag so we can see where we were
		currenttag = str2;
				
		if(str2.equals("tag"))
		{
			handleTagStart(attributes);
		}
		else if(str2.equals("function"))
		{
			handleFunctionStart(attributes);
		}
		else if(str2.equals("parameter"))
		{
			handleParameterStart(attributes);
		}
		else if(str2.equals("value"))
		{
			handleValueStart(attributes);
		}
		else if (str2.equals("help"))
		{
			//adds help
		}
		else if(str2.equals("component"))
		{
		    handleComponentStart(attributes);
		}
		else if(str2.equals("scope"))
		{
		    handleScopeStart(attributes);
		}
	}
	
	
	private void handleTagStart(org.xml.sax.Attributes attributes) {
	    //all the attribtues we are going to need to make a tag
		byte creator = 0;
		String name = "";
		boolean single = false;
		boolean xmlstyle = false;
		
		//get all the attributes needed for the tag
		for(int x=0; x< attributes.getLength(); x++)
		{
			String attrname = attributes.getQName(x);
			if(attrname.equals("creator"))
			{
				creator = Byte.parseByte(attributes.getValue(x));
			}
			else if(attrname.equals("name"))
			{
				name = attributes.getValue(x);
			}
			else if(attrname.equals("single"))
			{
				single = parseBoolean(attributes.getValue(x));
			}
			else if(attrname.equals("xmlstyle"))
			{
				xmlstyle = parseBoolean(attributes.getValue(x));
			}
		}
		
		//System.out.println("Tag: " + creator + " " + name + " " + single + " " + xmlstyle);
		//create a new tag
		this.currentitem = new Tag(name,single,xmlstyle,creator);
	}
	
	
	
	private void handleFunctionStart(org.xml.sax.Attributes attributes) {
	    //create a new function
		byte creator = 0;
		String name = "";
		String returns = "";
		
		//get all the attributes needed for the tag
		for(int x=0; x< attributes.getLength(); x++)
		{
			String attrname = attributes.getQName(x);
			if(attrname.equals("creator"))
			{
				creator = Byte.parseByte(attributes.getValue(x));
			}
			else if(attrname.equals("name"))
			{
				name = attributes.getValue(x);
			}
			else if(attrname.equals("returns"))
			{
				returns = attributes.getValue(x);
			}
		}
		//System.out.println("Fun: " + creator + " " + name + " " + returns);
		//create a new function
		if (this.currentitem instanceof Component) 
		{
		    this.methoditem = new Function(name, returns, creator);
		}
		else
		{
		    this.currentitem = new Function(name, returns, creator);
		}
	}
	
	
	private void handleParameterStart(org.xml.sax.Attributes attributes) {
	    //get the name and type
		String name = "";
		String type = "";
		boolean required = false;
		
		for(int x=0; x< attributes.getLength(); x++)
		{
			String attrname = attributes.getQName(x);
			if(attrname.equals("type"))
			{
				type = attributes.getValue(x);
			}
			else if(attrname.equals("name"))
			{
				name = attributes.getValue(x);
			}
			else if(attrname.equals("required"))
			{
				required = parseBoolean(attributes.getValue(x));
			}
		}
		
		//System.out.println("Param: " + name + " " + type + " " + required);
		//create a new parameter
		this.paramitem = new Parameter(name,type,required);
	}
	
	
	private void handleValueStart(org.xml.sax.Attributes attributes) {
	    //create a new value and assign it to the current parameter
		String option = attributes.getValue(0);
		//System.out.println("Value: " + option);
		if(option != null && paramitem != null)
			paramitem.addValue(new Value(option));
	}
	
	
	private void handleComponentStart(org.xml.sax.Attributes attributes) {
//	  all the attribtues we are going to need to make a tag
		byte creator = 0;
		String path = "";
		String name = "";
		String framework = "";;
		
		//get all the attributes needed for the tag
		for(int x=0; x< attributes.getLength(); x++)
		{
			String attrname = attributes.getQName(x);
			if(attrname.equals("creator"))
			{
				creator = Byte.parseByte(attributes.getValue(x));
			}
			else if(attrname.equals("path"))
			{
				path = attributes.getValue(x);
				String[] tmp = path.split("\\.");
				name = tmp[tmp.length-1];
			}
			else if(attrname.equals("framework"))
			{
				framework = attributes.getValue(x);
			}
		}
		
		//System.out.println("Tag: " + creator + " " + name + " " + single + " " + xmlstyle);
		//create a new tag
		this.currentitem = new Component(name,path,framework,creator);
	}
	

	
	
	private void handleScopeStart(org.xml.sax.Attributes attributes) {
	    //create a new value and assign it to the current parameter
		String scope = attributes.getValue(0);
		//System.out.println("Value: " + option);
		if(currentitem instanceof Component) {
			((Component)currentitem).addScope(scope);
			dscopeVars.put(scope,currentitem);
		}
		else {
		    dscopeVars.put(scope,new ScopeVar(scope));
		}
	}
	
	
	
	
	/** process an end element */
	public void endElement(String str, String str1, String str2) throws SAXException {
		if(str2.equals("tag"))
		{
			//add the current item to the tag map
			if(this.currentitem != null)
				dtags.put(currentitem.getName(),(Tag)currentitem);
			//System.err.println(this.currentitem);
		}
		else if(str2.equals("function"))
		{
			//add the current item to the function map
			if(this.currentitem instanceof Component)
			{
				((Component)currentitem).addMethod(methoditem);
				methoditem = null;
			}
			else if (this.currentitem instanceof Function) {
			    dfunctions.put(currentitem.getName(),(Function)currentitem);
			}
		}
		else if(str2.equals("parameter"))
		{
			//attact the finished parameter to the
			//current item
			if((currentitem instanceof Function || currentitem instanceof Tag )&& paramitem != null)
			{
				currentitem.addParameter(paramitem);
			}
			else if (methoditem != null && paramitem != null) {
			    methoditem.addParameter(paramitem);
			}
			
			//reset the paramitem
			paramitem = null;
		}
		else if(str2.equals("value"))
		{
			//nothing?
		}
		else if(str2.equals("help"))
		{
			//nothing?
		}
		else if(str2.equals("component"))
		{
		    if (this.currentitem instanceof Component) 
		    {
		        this.currentitem = null;
		    }
		   
		}
		else if(str2.equals("scope"))
		{
		    // Do nothing.
		}
		currenttag = "";
	}
	
	
	/** process the start prefix */	
	public void startPrefixMapping(String str, String str1) throws SAXException {
		//save the mappings for later use
		//namespaceMappings.put(str1,str);
	}
	
	
	/** process the end prefix */
	public void endPrefixMapping(String str) throws SAXException {;}
		
	/** process characters */
	public void characters(char[] values, int start, int length) throws SAXException {
			
		if(currenttag.equals("help"))
		{
			StringBuffer resvalue = new StringBuffer();
			
			for(int x=start; x<(start+length); x++){
				resvalue.append(values[x]);
			}
			
			//if the current item is not null and the prams are its help for the
			//current item
			if(currentitem != null && paramitem == null)
			{
				//for some reason M8 calls this a bunch of times, but only with
				//the cfml dictionary. So this is kind of a hack to get it to
				//load the help right... this slows it down a bit
				//TODO figure out whats up
				if(resvalue.toString().trim().length() > 0)
				{
					currentitem.setHelp(
						currentitem.getHelp() + " " +
						resvalue.toString().trim().replace('\t',' ') + "\n"
					);
				}
			}
			//if the param is not null its help for the param
			else if(currentitem != null && paramitem != null)
			{
				//TODO here too
				//paramitem.setHelp(resvalue.toString().trim().replace('\t',' '));
				if(resvalue.toString().trim().length() > 0)
				{
					paramitem.setHelp(
						paramitem.getHelp() + " " +
						resvalue.toString().trim().replace('\t',' ') + "\n"
					);
				}
			}
		}
	}
	
	/** process the end of the document */
	public void endDocument() throws SAXException {;}
	public void ignorableWhitespace(char[] values, int param, int param2) throws SAXException {;}
	/** process the processing instructions */
	public void processingInstruction(String str, String str1) throws SAXException {;}
	public void skippedEntity(String str) throws SAXException {;}
	/** process the start of the documnet */
	public void startDocument() throws SAXException {;}
}
