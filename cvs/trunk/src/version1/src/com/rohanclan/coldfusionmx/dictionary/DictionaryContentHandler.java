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
package com.rohanclan.coldfusionmx.dictionary;

import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import java.util.Map;

/**
 * @author Rob
 *
 * Handles the Sax events against a dictionary. This was ripped from Treebeard
 * with permission
 */
public class DictionaryContentHandler implements ContentHandler {
	//keep track of the location
	private Locator locator;
	private Map namespaceMappings;
	
	private Map dtags;
	private Map dfunctions;	
	
	public DictionaryContentHandler(Map tags, Map functions)
	{
		dtags = tags;
		dfunctions = functions;
		namespaceMappings = new java.util.HashMap();
	}
	
	/** sets the documnet locator
	 * @param locator the document locator
	 */	
	public void setDocumentLocator(Locator locator) 
	{
		this.locator = locator;
	}
	
	/** process a start element */
	public void startElement(String namespace, String localName, String str2, org.xml.sax.Attributes attributes) 
		throws SAXException 
	{
		//just so we know its a parsin'
		System.out.print(attributes.getLength());
		
		/* String prefix=new String("");
		if(namespace.length() > 0){
			prefix = (String)namespaceMappings.get(namespace);
			if(prefix != null && !prefix.equals("")){
				prefix += ":";
			}else{
				prefix="";
			}
		} */
		
		//DefaultMutableTreeNode element = new DefaultMutableTreeNode(prefix  + localName);
		//current.add(element);
		//current = element;
		
		/* for(int x=0; x<attributes.getLength(); x++){
			//give a little hint to this nodes value
			String value = attributes.getValue(x);
			if(value.length() > 15) value = value.substring(0,14) + "...";
			
			current.add(new DefaultMutableTreeNode('@' + attributes.getQName(x) + " =>[" + value + "]"));
		} */
	}
	
	/** process an end element */
	public void endElement(String str, String str1, String str2) throws SAXException {
		//current = (DefaultMutableTreeNode)current.getParent();
	}
	/** process the start prefix */	
	public void startPrefixMapping(String str, String str1) throws SAXException {
		//save the mappings for later use
		namespaceMappings.put(str1,str);
		
		//if there is a prefix add a : to the begining
		/* if(str != null && str.length() > 0){
			str=":"+str;
		}
		current.add(new DefaultMutableTreeNode("xmlns" + str + "=" + "\"" + str1 + "\"" ));
		*/
	}
	/** process the end prefix */
	public void endPrefixMapping(String str) throws SAXException {
		/* for(java.util.Iterator i = namespaceMappings.keySet().iterator(); i.hasNext();) {
			String uri = (String)i.next();
			String thisPrefix = (String)namespaceMappings.get(uri);
			if(str.equals(thisPrefix)){
				namespaceMappings.remove(uri);
				break;
			}
		} */
	}
	
	StringBuffer resvalue = new StringBuffer();
	/** process characters */
	public void characters(char[] values, int start, int length) throws SAXException {
		//Object obj = current.getUserObject();
		//if the current object is not null, there is a help value, and it doesnt
		//look like a help value has been added at one
		/* if(obj != null && obj.toString().indexOf(" =>[") < 0){
			
			//give a hint to the nodes contents
			int orglen = length;
			if(length > 15) length = 15;
			for(int x=start; x<(start+length); x++){
				resvalue.append(values[x]);
			}
			
			if(resvalue.length() > 0){
				if(orglen != length) resvalue.append("...");
				current.setUserObject(( (String)obj) + " =>[" + resvalue.toString() + "]");
			}
			
			resvalue.delete(0,resvalue.length());
		} */
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
