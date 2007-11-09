package org.cfeclipse.cfml.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.w3c.dom.Document;

/**
 * @author Mark Drew
 * this class generates an XML version of a CFC, it could also be used in the future to add functionality 
 * to the warnings about syntax
 */
public class CFCParser {
	protected ICFDocument cfdocument = null;
	protected Document xmlDocument = null;
	
	/**
	 * @param cfdocument
	 */
	public CFCParser(ICFDocument cfdocument) {
		this.cfdocument = cfdocument;
	
	}
	
	public Document getXML(){
		try {
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Document document = parser.parse(cfdocumen);
		
		return xmlDocument;
	}
	
}
