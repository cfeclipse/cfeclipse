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
 
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.SQLSyntaxDictionary;
import com.rohanclan.cfml.editors.HTMLSyntaxDictionary;
import com.rohanclan.cfml.editors.partitioner.scanners.jscript.JSSyntaxDictionary;

/**
 * @author Rob
 *
 * This class loads all the dictionaries and can be used to get specific
 * dictionaries. This (hopefully) well help in abstracting the dictionaries
 * not intended to be instantiated
 */
public class DictionaryManager 
{
	/** the coldfusion dictionary */
	public static final String CFDIC = "CF_DICTIONARY";
	/** the javascript dictionary */
	public static final String JSDIC = "JS_DICTIONARY";
	/** the SQL dictionary */
	public static final String SQLDIC = "SQL_DICTIONARY";
	/** the (yet to be made) html dictionary */
	public static final String HTDIC = "HT_DICTIONARY";
	
	/** all the dictionaries */
	private static Map dictionaries = new HashMap();
	/** the dictionary cache - for swtiching between grammers */
	private static Map dictionariesCache = new HashMap();
	
	/** the dictionary config file in DOM form */
	private static Document dictionaryConfig = null;
	
	private DictionaryManager(){;}
	
	/**
	 * Loads the dictionary config file. The config file lists all
	 * the dictionary files that are available to the system
	 */
	private static void loadDictionaryConfig()
	{
		URL dictionaryConfigURL = null;
		try 
		{
			dictionaryConfigURL = new URL(
				CFMLPlugin.getDefault().getBundle().getEntry("/"),
				"dictionary/"
			);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setCoalescing(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			URL local = Platform.asLocalURL(dictionaryConfigURL);
			
			URL configurl = Platform.resolve(new URL(local, "dictionaryconfig.xml"));
						
			dictionaryConfig = builder.parse(configurl.getFile());
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Tell the dictionaries to load based on the config file
	 */
	public static void initDictionaries()
	{
		long time = System.currentTimeMillis();
		System.out.println("Dictionaries initialized start");
		
		//get the dictionary config file into a DOM
		loadDictionaryConfig();
		if(dictionaryConfig == null)
			throw new IllegalArgumentException("Problem loading dictionaryconfig.xml");
		
		//load the dictionaries into the cache
		loadDictionaryByVersion("cfmx701");
		loadDictionaryByVersion("xhtml");
		loadDictionaryByVersion("ecma");
		
		//load from the cache to the live
		loadDictionaryFromCache("cfmx701",CFDIC);
		loadDictionaryFromCache("cfmx701",SQLDIC);
		loadDictionaryFromCache("xhtml",HTDIC);
		loadDictionaryFromCache("ecma",JSDIC);
				
		System.out.println("Dictionaries initialized in " + (System.currentTimeMillis() - time) + " ms");
	}
	
	/**
	 * Loads a syntax file into the cache by the key defined in the dictionary config file.
	 * for example
	 * <pre>
	 * ...
	 * &lt;version key="cfmx701" label="Coldfusion 7.0"&gt;
	 * 	&lt;grammer location="cfml.xml" /&gt;
	 * 	&lt;grammer location="user.xml" /&gt;
	 * &lt;/version&gt;
	 * ...
	 * </pre>
	 * 
	 * "key" is the version key you would pass in here to load the Coldfusion 7.0 grammer
	 * @param versionkey
	 */
	public static void loadDictionaryByVersion(String versionkey)
	{
		if(dictionaryConfig == null)
			throw new IllegalArgumentException("Problem loading dictionaryconfig.xml");
		
		//grab the cfml dictionary
		//Node n = dictionaryConfig.getElementById(CFDIC).getFirstChild();
		Node versionNode = dictionaryConfig.getElementById(versionkey);
				
		//get the dictype from the parent node
		String dicttype = versionNode.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
		
		//now, make and load the dictionary based on the type
		SyntaxDictionary dic = null;
		
		if(dicttype.equals(CFDIC))
		{
			//load the cfml into the cache
			dic = new SQLSyntaxDictionary();
			//TODO: make this not a hack
			String sqlwords = dictionaryConfig.getElementById(SQLDIC).getFirstChild().getFirstChild().getAttributes().getNamedItem("location").getNodeValue();
			((SQLSyntaxDictionary)dic).loadKeywords(sqlwords);
		}
		else if(dicttype.equals(JSDIC))
		{
			dic = new JSSyntaxDictionary();
		}
		else if(dicttype.equals(HTDIC))
		{
			dic = new HTMLSyntaxDictionary();
		}
		
		//get a list of all the grammers to load
		NodeList grammers = versionNode.getChildNodes();
		byte nlen = (byte)grammers.getLength();
		
		//loop over the grammers and add them to the dictionaries
		Node n = null;
		for(byte z=0; z<nlen; z++)
		{
			n = grammers.item(z);
			String filename = n.getAttributes().getNamedItem("location").getNodeValue().trim();
			dic.loadDictionary(filename);
		}
		
		//add finally add them to the cache
		addDictionaryToCache(versionkey, dic);
	}
	
	/**
	 * Takes a Syntax dictionary from the cache and puts it into the live dictionary
	 * @param cachekey
	 * @param livekey
	 */
	public static synchronized void loadDictionaryFromCache(String cachekey, String livekey)
	{
		if(dictionariesCache.containsKey(cachekey) && dictionaries.containsKey(livekey))
		{
			//Object tdic = dictionaries.get(livekey);
			Object tdic = dictionariesCache.get(cachekey);
						
			dictionaries.put(livekey,tdic);
		}
		else if(dictionariesCache.containsKey(cachekey))
		{
			dictionaries.put(livekey, dictionariesCache.get(cachekey));
		}
		else
		{
			throw new IllegalArgumentException("Cache key: " + cachekey +" is not in the cache" + dictionariesCache.keySet().toString());
		}
	}
	
	/**
	 * Reloads the dictionaries
	 * @param shell
	 */
	public static void initDictionaries(Shell shell) 
	{
	    initDictionaries();
	    
        MessageBox msg = new MessageBox(shell);
        msg.setText("Done!");
        msg.setMessage("Dictionaries reloaded.");
        msg.open();    
	}
	
	/**
	 * Adds a SyntaxDictionary to the cache
	 * @param key
	 * @param sd
	 */
	public static void addDictionaryToCache(String key, SyntaxDictionary sd)
	{
		dictionariesCache.put(key,sd);
	}
	
	/**
	 * Add a dictionary to the manager
	 * @param key the key to be used to get the dictionary
	 * @param sd the dictionary
	 */
	public static void addDictionary(String key, SyntaxDictionary sd)
	{
		dictionaries.put(key,sd);
	}
	
	/**
	 * Get a dictionary from the live dictionaries
	 * @param key the dictionary's key (often one of the statics above)
	 * @return the dictionary
	 */
	public static SyntaxDictionary getDictionary(String key)
	{
		return (SyntaxDictionary)dictionaries.get(key);
	}
	
    /**
     * @return Returns the live dictionaries.
     */
    public static Map getDictionaries() 
    {
        return DictionaryManager.dictionaries;
    }
}
