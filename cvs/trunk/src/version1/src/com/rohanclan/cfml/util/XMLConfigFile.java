/*
 * Created on Jan 30, 2004
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
 
package com.rohanclan.cfml.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import java.net.MalformedURLException;
import java.net.URL;
import com.rohanclan.cfml.CFMLPlugin;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Text;
import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
import java.io.File;

/**
 * This is used to read simple xml config documents and
 * either just get the DOM or allow access to specific
 * values. It's pretty basic and just a wrapper for DOM. 
 * (I ripped this from another one of my projects called Thoth
 * and before that treebeard hehehe)
 * @author rob rohan 
 */
public class XMLConfigFile {
	
	/** Which dom factory to use */	
	//public String DOMFactory = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
	protected static URL configBaseURL;
	public static String SNIPS = "snips";
	
	private String filename;
	private Document document=null;
	
	protected javax.xml.parsers.DocumentBuilderFactory factory;
	protected javax.xml.parsers.DocumentBuilder builder;
	
	/** Creates a new instance of XMLConfigFile */
	//public XMLConfigFile {;}
	
	/** Creates a new config file object using the passed file name
	 * @param path URI to a xml file to be used as the config
	 * file.
	 * @throws Exception io and others, covers all
	 * @throws FactoryConfigurationError if the dom string is wrong or some other xml factory error
	 */	
	public XMLConfigFile()
	{
		if(configBaseURL == null)
		{
			String pathSuffix = "config/";
			try 
			{
				configBaseURL = new URL(
					CFMLPlugin.getDefault().getDescriptor().getInstallURL(),
					pathSuffix
				);
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace(System.err);
			}
		}
		
		try
		{
			//System.setProperty("javax.xml.parsers.DocumentBuilderFactory", DOMFactory);
			factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setCoalescing(true);
			builder = factory.newDocumentBuilder();	
		}catch(ParserConfigurationException pce)
		{
			//bah!
			pce.printStackTrace(System.err);
		}
		
	}
	
	/**
	 * loads an xml file
	 * @param filename the file name 
	 * @param offset relative to config/ can be null
	 * @throws IOException
	 * @throws FactoryConfigurationError
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void loadFile(String filename, String offset) throws IOException, FactoryConfigurationError, ParserConfigurationException, SAXException
	{
		if(offset == null)
			this.filename = filename;
		else
			this.filename = offset + System.getProperty("file.separator") + filename;
		openFile();
	}
	
	/** Sets the config file URI to a new file
	 * @param path URI to the xml file to be used as the config
	 * file
	 */	
	public void setFileName(String path)
	{
		this.filename = path;
	}
	
	/** Returns the current file URI
	 * @return currrent file used in this object
	 */	
	public String getFileName()
	{
		return this.filename;
	}
	
	/** Opens the file that was set using setFile or on object creation
	 * @throws Exception io et all
	 * @throws FactoryConfigurationError if the dom string is wrong or some other xml factory error
	 */	
	public void openFile() throws IOException, FactoryConfigurationError, SAXException
	{
		File xmlfile = new File(this.filename);
		
		if(xmlfile.exists())
		{	
			//get some input streams to the file
			FileInputStream fis = new FileInputStream(xmlfile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			//for some reason we get the full file, so this has the BOM in it
			//thats odd for me. Anyway this sees if the BOM is there and adjusts
			//the input so its valid xml
			int t,p,q;
			bis.mark(0);
			if( ((char)bis.read()) != '<'){
				bis.reset();
				t = bis.read();
				p = bis.read();
				q = bis.read();	
			}else{
				bis.reset();
			}
			
			document = builder.parse(bis);
			//document = builder.parse(xmlfile);
			bis.close();
		}
		else
		{
			throw new IOException(
				"File is not there?: " + this.filename
			);
		}
		
	}
	
	/** Saves the config file if possible
	 * <b>Not Implemented</b>
	 */	
	public void saveFile(){;}
	
	/** Returns the number of times "key" is found in the config
	 * file
	 * @return The number of times key is found in config
	 * file
	 * @param key The key you want to know how many times is in
	 * the file
	 */	
	public int getLength(String key)
	{
		return document.getElementsByTagName(key).getLength();
	}
	
	/**
	 * wrapper calls getValue(String key, int iteration)
	 * with iteration of 0
	 */
	public String getValue(String key)
	{
		return getValue(key, 0);
	}
	
	/** Gets a value from the file
	 * @param key The key sought. for example using
	 * <CODE>
	 * ...
	 * &lt;thing&gt;my info&lt;/thing&gt;
	 * ...
	 * </CODE>
	 * it would be "thing"
	 * @param iteration which one you want if the key appears multi
	 * times in the file. Most often this will be
	 * set to 0.
	 * @return The value of the key
	 */	
	public String getValue(String key, int iteration)
	{
		if(document != null)
		{	
			short nodetype = document.getElementsByTagName(key).item(iteration).getFirstChild().getNodeType();
			String val = null;
			
			if(nodetype == Document.TEXT_NODE)
			{
				val = ((Text)document.getElementsByTagName(key).item(iteration).getFirstChild()).getData();	
			}
			else if(nodetype == Document.CDATA_SECTION_NODE)
			{
				CDATASection cds = (CDATASection)document.getElementsByTagName(key).item(iteration).getFirstChild();
				//System.out.println(cds.getNodeValue());
				val = cds.getNodeValue();
			}
			
			//System.err.println(val);
			return val; 
		}
		return null;
	}
	
	/** Changes the value of a key. Note this is an in memory change only
	 * @param key The key name to change
	 * @param iteration if multi times, which one. Most often set to
	 * 0
	 * @param newValue What the key should equal when this is done
	 * with it.
	 */	
	public void setValue(String key, int iteration, String newValue){
		document.getElementsByTagName(key).item(iteration).getFirstChild().setNodeValue(newValue);
	}
	
	/** Get the raw DOM
	 * @return the xml config file in a DOM object
	 */	
	public org.w3c.dom.Document getDOM(){
		return this.document;
	}
}
