package org.cfeclipse.frameworks.fusebox4.parsers;

import org.cfeclipse.frameworks.fusebox4.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox4.objects.IFBXObject;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author markd
 * This is the new fusebox parser. Leaner and meaner
 * TODO: Use http://java.sun.com/j2se/1.4.2/docs/api/org/xml/sax/Locator.html
 *       So that we can find the file location. 
 * 	
 */
public class FBXParser implements ContentHandler{
	
	private String projectName; //The project name that we will be parsing (where we will get the parse data from)
	private IFBXObject root;
	
	public FBXParser(String projectName, IFBXObject parent){
		this.projectName = projectName;
		this.root = parent;
	}
	
	public IFBXObject parse(){
		//The parser does the hard work of getting items using a recursive loop
		
		//We create a sample application object
		FBXApplication fbxapp = new FBXApplication(this.projectName, this.root);
		
		return fbxapp;
	}

	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String arg0, String arg1) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}
}
