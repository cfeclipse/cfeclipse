/*
 * Created on Feb 26, 2004
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

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.net.MalformedURLException;
import java.net.URL;
import com.rohanclan.cfml.CFMLPlugin;

import org.xml.sax.SAXException;
//import java.util.Enumeration;
import java.io.IOException;
//import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;

import java.net.URLConnection;
import java.io.BufferedInputStream;

/**
 * @author Rob
 *
 * Base class for dictionaries
 */
public abstract class SyntaxDictionary {
	/** any tag based items in the dictionary */
	protected Map syntaxelements;
	/** any function based elements */
	protected Map functions;
	
	/** the file name for this dictionary */
	protected String filename = null;
	/** the base url for this dictionary (this will be set on
	 * object creation)
	 */
	protected static URL dictionaryBaseURL;
	
	/** get a handle to the dictionary base */
	static
	{
		if(dictionaryBaseURL == null)
		{
			try 
			{
				dictionaryBaseURL = new URL(
					CFMLPlugin.getDefault().getDescriptor().getInstallURL(),
					"dictionary/"
				);
				
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace(System.err);
			}
		}
	}
	
	public SyntaxDictionary()
	{
		syntaxelements = new HashMap();
		functions = new HashMap();
	}
	
	/**
	 * loads the xml dictionary "filename" into this
	 * object. 
	 * Note: if this dictionary already has tags defined
	 * the new items will be added to this dictionary (not replaced)  
	 * @param filename
	 */
	public void loadDictionary(String filename)
	{
		setFilename(filename);
		try
		{
			loadDictionary();
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	public void setFilename(String fname)
	{
		this.filename = fname;
	}
	
	/**
	 * get all top level language elements (tags)(in lowercase)
	 * these are the keys used in the tag HashMap <b>not</b> the
	 * tag objects them selves
	 * @return set of all the tag names using the keys
	 */
	public Set getAllElements()
	{
		return syntaxelements.keySet();
	}
	
	/**
	 * gets a set that is a copy of all the tags
	 * @return a set of all the tag objects
	 */
	public Set getAllTags()
	{
		Set total = new HashSet();
		Set keys = getAllElements();
		Iterator it = keys.iterator();
		while(it.hasNext())
		{
			total.add(syntaxelements.get((String)it.next()));
		}
		
		return total;
	}
	
	/**
	 * get a set of filtered tags limited by start
	 * @param start
	 * @return
	 */
	public Set getFilteredElements(String start)
	{
		return limitSet(getAllTags(),start);
		//return limitSet(getAllElements(),start);
	}
	
	/**
	 * Get the tag "name" from the dictionary - null if not found
	 * @param name
	 * @return
	 */
	public Tag getTag(String name)
	{
		Object obj = syntaxelements.get(name.toLowerCase());
		if(obj != null)
			return (Tag)obj;
		
		return null;
	}
	/**
	 * gets the attributes(params) for a tag(or function... I think)
	 * @param tag
	 * @param attribute
	 * @param start
	 * @return
	 */
	public Set getFilteredAttributeValues(String tag, String attribute, String start)
	{
		Set attribs = getElementAttributes(tag);
		if(attribs.size() == 0)
			return null;
		Object [] tempArray = attribs.toArray(); 
		for(int i = 0; i < tempArray.length; i++)
		{
			Parameter currParam = (Parameter)tempArray[i];
			String currName = currParam.getName();
			if(currParam.getName().compareTo(attribute) == 0)
				return limitSet(currParam.getValues(), start);
		}
		return null;
	}
	
	/**
	 * get the attribtues for tag tag, limited to start
	 * @param tag
	 * @param start
	 * @return
	 */
	public Set getFilteredAttributes(String tag, String start)
	{
		return limitSet(getElementAttributes(tag),start);
	}
	
	/**
	 * gets all the functions (lowercase only)
	 * @param elementname
	 * @return
	 */
	public Set getFunctions()
	{
		return functions.keySet();
	}
	
	/**
	 * retuns a functions usage
	 * @param functionname
	 * @return
	 */
	public String getFunctionUsage(String functionname)
	{
		return (String)functions.get(functionname.toLowerCase());
	}
	
	/**
	 * get a function object by name
	 * @param name
	 * @return the function or null if it doesn't exist
	 */
	public Function getFunction(String name)
	{
		Object obj = functions.get(name.toLowerCase());
		if(obj != null)
			return (Function)obj;
		
		return null;
	}
	
	/**
	 * checks to see if the tag is in the dictionary
	 * @param name
	 * @return
	 */
	public boolean tagExists(String name)
	{
		if(syntaxelements == null)
			return false;
		
		return syntaxelements.containsKey(name.toLowerCase());
	}
	
	/**
	 * checks to see if the function is in the dictionary
	 * @param name
	 * @return
	 */
	public boolean functionExists(String name)
	{
		if(functions == null)
			return false;
		
		return functions.containsKey(name.toLowerCase());
	}
	
	/**
	 * limits a set based on a starting string. The set can
	 * either be a set of Strings, Tag, Functions, or Parameters
	 * @param st the full set
	 * @param start the string to use as a limiter
	 * @return everything in the set that starts with start in the format passed in
	 */
	protected static Set limitSet(Set st, String start)
	{
		Set filterset = new HashSet();
		Set fullset = st;
		
		if(fullset != null){
			Iterator it = fullset.iterator();
			while(it.hasNext())
			{
				Object item = it.next();
				String possible = "";
				
				if(item instanceof String)
				{
					possible = (String)item;
				}
				else if(item instanceof Tag)
				{
					possible = ((Tag)item).getName();
				}
				else if(item instanceof Function)
				{
					possible = ((Function)item).getName();
				}
				else if(item instanceof Parameter)
				{
					possible = ((Parameter)item).getName();
				}
				else if(item instanceof Value)
				{
					possible = ((Value)item).getValue();
				}
				else
				{
					throw new IllegalArgumentException(
						"The passed set must have only Strings, Procedures, or Parameters"
					);
				}
				
				if(possible.startsWith(start) || possible.toUpperCase().startsWith(start))
				{
					filterset.add(item);
				}
			}
		}
		return filterset;
	}
	
	/**
	 * Gets the Parameter objects for the passed element name  
	 * @param elementname
	 * @return a set of Parameters
	 */
	public Set getElementAttributes(String elementname)
	{
		Procedure p = (Procedure)syntaxelements.get(elementname.toLowerCase());
		if(p != null)
		{	
			Set st = p.getParameters();
			return st;
		}
		
		return null;
	}
	
	/**
	 * Loads and parses an cfeclipse xml dictionary into this dictionary object
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void loadDictionary() throws IOException, SAXException, ParserConfigurationException
	{
		//System.setProperty("javax.xml.parsers.SAXParserFactory", SAXFactory);
		System.err.println("loading dictionary: " + filename);
		if(filename == null) throw new IOException("Filename can not be null!");
		
		URLConnection urlcon = new URL(dictionaryBaseURL + "/" + filename).openConnection();
		BufferedInputStream xml = new BufferedInputStream(
			urlcon.getInputStream()
		);
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		
		//setup the content handler and give it the maps for tags and functions
		xmlReader.setContentHandler(
			new DictionaryContentHandler(syntaxelements,functions)
		);
		
		InputSource input = new InputSource(xml);
		xmlReader.parse(input);
	}
	
}
