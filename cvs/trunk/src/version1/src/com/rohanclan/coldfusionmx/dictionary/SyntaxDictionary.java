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
package com.rohanclan.coldfusionmx.dictionary;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.net.MalformedURLException;
import java.net.URL;
import com.rohanclan.coldfusionmx.ColdfusionMXPlugin;

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
 * Base class for dictionaries extend this but don't create one
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
					ColdfusionMXPlugin.getDefault().getDescriptor().getInstallURL(),
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
	 * limits a set based on a starting string
	 * @param st the full set
	 * @param start the string to use as a limiter
	 * @return everything in the set that starts with start
	 */
	protected static Set limitSet(Set st, String start)
	{
		Set filterset = new HashSet();
		Set fullset = st;
		
		if(fullset != null){
			Iterator it = fullset.iterator();
			while(it.hasNext())
			{
				String possible = (String)it.next(); 
				if(possible.startsWith(start))
				{
					filterset.add(possible);
				}
			}
		}
		return filterset;
	}
	
	private void loadDictionary() throws IOException, SAXException, ParserConfigurationException
	{
		//System.setProperty("javax.xml.parsers.SAXParserFactory", SAXFactory);
		System.err.println("ldff: " + filename);
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
