/*
 * Created on November 5, 2005
 *
 * The MIT License
 * Copyright (c) 2005 Mark Drew
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
package org.cfeclipse.cfml.views.dictionary;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.ScopeVar;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.dictionary.Tag;
import org.eclipse.core.runtime.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author markd
 *
 */
public class CategoryLoader {
	/*	This class will return a language  tree with the categories, and tags etc
	 * 	Depending which method is called it returns either the categorised or full language tags for coldfusion
	 * 	This means that can be called from a function
	 * 
	 * */
	
	private TreeParent cattags = null;
	private TreeParent fulltags = null;
	
	private Document categoryXML = null;
	private String cfmlversion;
	
	/* get the dictionary so its ready for us to grab items out of it 
	 * TODO: Change it so it gets the current file's dictionary... we might have to have a listener when 
	 * we change a document, and an error if there isnt one.
	 * 
	 * */
	SyntaxDictionary cfdic = DictionaryManager.getDictionary("CF_DICTIONARY");
	
	
	public CategoryLoader(String name){
		//Create the root 
		this.cattags = new TreeParent(name);
		this.fulltags = new TreeParent(name);
		initialize();
	}
	
	public CategoryLoader(String name, String Dictionary){
		this.cattags = new TreeParent(name);
		this.fulltags = new TreeParent(name);
		this.cfmlversion = Dictionary;
		
		SyntaxDictionary dictionaryByVersion = DictionaryManager.getDictionaryByVersionAlt(Dictionary);
		
		if(dictionaryByVersion == null){
			System.err.println("Error getting " + Dictionary);
		}
		else{
			cfdic = dictionaryByVersion;
		}
		
		initialize();
	}
	
	/**
	 * This method is called internally to prepare the cattags and fulltags trees
	 */
	private void initialize(){
		loadCategories();
		parseCategories();
		parseDictionary();
	}
	
	/* This method initializes the library */
	private void loadCategories(){
		URL categoryConfigURL = null;
		try 
		{
			categoryConfigURL = new URL(
				CFMLPlugin.getDefault().getBundle().getEntry("/"),
				"dictionary/"
			);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setCoalescing(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			URL local = Platform.asLocalURL(categoryConfigURL);
			
			URL configurl = Platform.resolve(new URL(local, "cfml_cats.xml"));
						
			categoryXML = builder.parse(configurl.getFile());
		} 
		catch (Exception e) 
		{
			System.out.println("---- Error Loading the CFML Categories ----");
			e.printStackTrace(System.err);
		}	
		
	}
	
	/**
	 * 
	 */
	private void parseCategories(){
		/* now that it is loaded, lets parse the categories */
		cattags = new TreeParent("Coldfusion"); 
		
		
		
		
		NodeList types = categoryXML.getElementsByTagName("categories");
		
			
		for(int t=0; t < types.getLength(); t++){
			Node catgroup = types.item(t);
			String type = catgroup.getAttributes().getNamedItem("type").getNodeValue();
			
			TreeParent item = new TreeParent(catgroup.getAttributes().getNamedItem("label").getNodeValue());
			//*** This strangely only works if you loop through 1,3,5... etc. I dont know why this happens
			for(int c=1; c < catgroup.getChildNodes().getLength();c = c + 2){
					Node categoryItem = catgroup.getChildNodes().item(c);
					
					TreeParent catItem = new TreeParent(categoryItem.getAttributes().getNamedItem("label").getNodeValue());
					//Now we go and get the actual tags/functions
					for(int i=1; i < categoryItem.getChildNodes().getLength(); i = i + 2){
						
						Node thisItem = categoryItem.getChildNodes().item(i);
						if(type.equals("tag")){
								//Now we can get the tag
								Tag thistag = cfdic.getTag(thisItem.getAttributes().getNamedItem("label").getNodeValue());
								
								if(thistag != null){
									TagItem tag = new TagItem(thistag);
									tag.setDictionary(cfdic);
									catItem.addChild(tag);
								} else{
									//System.out.println("Tag " + thisItem.getAttributes().getNamedItem("label").getNodeValue() +" not found in dictionary");
									
								}
						}
						else {
							Function thisfunction = cfdic.getFunction(thisItem.getAttributes().getNamedItem("label").getNodeValue());
							
							if(thisfunction != null){
								FunctionItem tag = new FunctionItem(thisfunction);
								tag.setDictionary(cfdic);
								catItem.addChild(tag);
							} else {
							//	System.out.println("function " + thisItem.getAttributes().getNamedItem("label").getNodeValue() +" not found in dictionary");
								
							}
						}
						
						
						
						
					}
					
					item.addChild(catItem);
			}
			cattags.addChild(item);
		}	
	}
	
	/**
	 * this parses the coldfusion dictionary and returns the tags
	 */
	private void parseDictionary(){
		fulltags = new TreeParent("Coldfusion"); 
		
		TreeParent tags = new TreeParent("Tags");
		TreeParent functions = new TreeParent("Functions");
		TreeParent scopes = new TreeParent("Scopes");
		

		//Get all the tags from the dictionary and add them
		Iterator tagIter = cfdic.getAllTags().iterator();
		while (tagIter.hasNext()) {
			Tag currTag = (Tag) tagIter.next();
			TagItem tag = new TagItem(currTag);
			tag.setDictionary(cfdic);
			tags.addChild(tag);
		}
		
		//Get all the functions from the dictionary and add them
		Iterator funcIter = cfdic.getAllFunctions().iterator();
		while(funcIter.hasNext()){
			Function currFunc = (Function)funcIter.next();
			FunctionItem func = new FunctionItem(currFunc);
			func.setDictionary(cfdic);
			functions.addChild(func);
			
			
		}
		Object[] scopeIter = cfdic.getAllScopeVars().toArray();
		for (int i = 0; i < scopeIter.length; i++) {
			ScopeVar scope = (ScopeVar)scopeIter[i];
			ScopeItem sItem = new ScopeItem(scope);
			sItem.setDictionary(cfdic);
			scopes.addChild(sItem);
			
		}
		
		
		fulltags.addChild(tags);
		fulltags.addChild(functions);
		fulltags.addChild(scopes);
	}
	
	public TreeParent getCategories(){
		return this.cattags;
	}
	
	public TreeParent getUnsortedCategories(){
		return this.fulltags;
	}

}
