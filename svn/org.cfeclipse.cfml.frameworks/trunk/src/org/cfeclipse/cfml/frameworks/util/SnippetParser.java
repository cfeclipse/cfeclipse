/**
 * 
 */
package org.cfeclipse.cfml.frameworks.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;

/**
 * @author markdrew
 *
 */
public class SnippetParser {
	private String snippetTemplate;
	private Element element;
	
	Log logger = LogFactory.getLog(SnippetParser.class);
	public SnippetParser(String snippetTemplate, Element element) {
		super();
		this.snippetTemplate = snippetTemplate;
		this.element = element;
	}
	
	public String getParsedSnippet(){
		logger.debug("Going to parse" + this.snippetTemplate);
		
		String parsedSnippet = this.snippetTemplate;
			Pattern pattern = Pattern.compile("\\{[A-Za-z0-9-\\s]*\\}");
			 Matcher matcher = pattern.matcher(parsedSnippet);
			 //logger.debug("text attribute " + matcher);
			 String outputString = "";
			 while (matcher.find()) {
				  
				 String fullMatch = matcher.group();
				 
				 
				 
				 //logger.debug("text attribute2 " + fullMatch);
				 String attrName = fullMatch.substring(1, fullMatch.length()-1);
				 
				 //here we check if this is a XPATH, and then we an do some Xpathing:
				 //TODO: create and pass in the full (XML)Document from the tree
				 if(attrName.startsWith("/")){
					 
				 }
				 
				 String attributeValue = this.element.getAttributeValue(attrName.toLowerCase());
				 if(attributeValue != null){
					 //logger.debug("attribuve value - " + attributeValue);
					 parsedSnippet = parsedSnippet.replaceAll("." + attrName.toLowerCase() + ".", attributeValue);
				 }
				 else{
					 //logger.debug("no  value - ");
					 parsedSnippet = parsedSnippet.replaceAll("." + attrName.toLowerCase() + ".", "");
				 }
				 
			  }
	
		//By default return the unparsed snippet
		return parsedSnippet;
	}
	

}
