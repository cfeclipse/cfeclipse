package com.rohanclan.cfml.parser;

import java.util.HashMap;
import java.util.Iterator;

import com.rohanclan.cfml.parser.docitems.DocItem;
import com.rohanclan.cfml.parser.docitems.TagItem;
import com.rohanclan.cfml.parser.cfmltagitems.CfmlTagSet;

/**
 * 
 * This class parses and looks for all the variables in a document, making a ArrayList of the variables
 * to pass back to the CFDocument.
 * 
 * Should return a Key/Value pairs such as :
 * 		qSomething = CFQUERY (or whatever naming convention we have)
 * 		Something = CFDOCUMENT
 * 		oSomething = com.something.Component (which we then need to find in the project)
 * 
 * 
 * @author mark
 *
 */
public class VariablesParser {
	private HashMap variableMap;

	public VariablesParser(CFDocument document, String docText) {
		variableMap = new HashMap();
		parseForVariables(document.getDocumentRoot());
	}
	
	private void parseForVariables(DocItem item){

			CFNodeList nodelist =  item.getChildNodes();
		
	        Iterator iter = nodelist.iterator();
	        while(iter.hasNext()){
	        	
	        	Object cfItem = iter.next();
	        	if(cfItem instanceof TagItem){
	        		addItem((TagItem)cfItem);
	        		
		        	if(((TagItem)cfItem).hasChildren()){
		        		parseForVariables((TagItem)cfItem);
		        	}
	        	}
	        	
	        	
	        }
	}

	/**
	 * This function parses a tag, and adds it, so we are looking for variables, the two cases that we need 
	 * to treat differently are cfscript and cfset 
	 * 
	 * @param tag
	 */
	private void addItem(TagItem tag){
		String tagname = tag.getName();
		if(tagname.equalsIgnoreCase("cfscript")){
			//Call the script parser
		}
		else if(tagname.equalsIgnoreCase("cfset")){
			CfmlTagSet setTag = new CfmlTagSet(tag.getLineNumber(), tag.getStartPosition(), tag.getEndPosition(), tag.getName());
			System.out.println("Parsing cfset");
			for (int i = 0; i < setTag.getAttributes().length; i++) {
				System.out.println(setTag.getAttributes()[i].getName() + " = " + setTag.getAttributes()[i].getValue());
			}
		}
		else if(tagname.equalsIgnoreCase("cfinvoke")){
			if(tag.getAttributeValue("returnvariable") != null){
				this.variableMap.put(tag.getAttributeValue("returnvariable"), tag);
			}
		}
		else if(tagname.equalsIgnoreCase("cfobject")){
			if(tag.getAttributeValue("name") != null){
				this.variableMap.put(tag.getAttributeValue("name"), tag);
			}
		}
		else if(tagname.equalsIgnoreCase("cfreturn")){
			//Ignore
		}
		else{
			//The rest of the tags, hopefully this will do the right thing, we should add
			//More cases above.
			
			if(tag.getAttributeValue("name") !=null){
				this.variableMap.put(tag.getAttributeValue("name"), tag);
			}
			
			//There are more tags that use the result scope, so we can put the same thing up there:
			if(tag.getAttributeValue("result") != null){
				this.variableMap.put(tag.getAttributeValue("result"), tag);
			}
			
		}
	}

	public HashMap getVariableMap() {
		return variableMap;
	}

}
