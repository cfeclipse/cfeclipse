/*
 * Created on May 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;



import org.w3c.dom.CDATASection;
import org.w3c.dom.Text;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SnipReader {
	
	
	protected javax.xml.parsers.DocumentBuilderFactory factory;
	protected javax.xml.parsers.DocumentBuilder builder;

	private Document document=null;
	
	private String snipDescription, snipStartBlock, snipEndBlock;
	
	File snippetFile;
	/**
	 * 
	 */
	public SnipReader() {
		super();

		try
		{
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
	
	
	public void read(String fileName) {
		this.snippetFile = new File(fileName);
		
		if (snippetFile.exists()) {

			try {
				FileInputStream fis = new FileInputStream(snippetFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				// Clear up to the first '<'
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
				
				bis.mark(0);
				try {
					document = builder.parse(bis);
					parseDocument();
				}
				catch (SAXException saxx){
					saxx.printStackTrace(System.err);
					this.snipDescription = saxx.getMessage();
				}
				bis.close();
			}
			catch (IOException iox) {
				iox.printStackTrace(System.err);
				this.snipDescription = iox.getMessage();
			}
			
		}
		
		
		
		
	}
	
	
	private void parseDocument() {
		
		// Make sure the document has been initialized
		if (document == null) {
			return;
		}
		
		parseSnipDescription();
		parseSnipStartBlock();
		parseSnipEndBlock();	
		
	}
	
	
	
	private void parseSnipDescription() {
		
		this.snipDescription = "";
		
		this.snipDescription = getValue("help",0);
		
		if (this.snipDescription.length() == 0) {
		
			NodeList nodes = document.getElementsByTagName("snippet");
			
			
			if (nodes.getLength() == 0) {
				return;
			}
			
			Node workingNode = nodes.item(0);
			
	
			if (workingNode.getNodeName().equalsIgnoreCase("snippet")) {
				NamedNodeMap attributes = workingNode.getAttributes();
				workingNode = attributes.getNamedItem("name");
				if (workingNode != null) {
					this.snipDescription = workingNode.getNodeValue();
				}
			}
		}

	}
	
	private void parseSnipStartBlock() {
		
		this.snipStartBlock = getValue("starttext",0);
		
		if (this.snipStartBlock.length() == 0) {
			this.snipStartBlock = getValue("insertText",0);
		}
		
	}
	
	private void parseSnipEndBlock() {
		
		this.snipEndBlock = getValue("endtext",0);
		
		if (this.snipEndBlock.length() == 0) {
			this.snipEndBlock = getValue("insertText",1);
		}
	}
	
	
	
	private String getValue(String key, int iteration) {
		
			short nodetype = 0;
			
			try {
				nodetype = document.getElementsByTagName(key).item(iteration).getFirstChild().getNodeType();
			}
			catch (Exception e) {
				return "";
			}
			
			
			String val = null;
			
			if(nodetype == Document.TEXT_NODE)
			{
				val = ((Text)document.getElementsByTagName(key).item(iteration).getFirstChild()).getData();	
			}
			else if(nodetype == Document.CDATA_SECTION_NODE)
			{
				CDATASection cds = (CDATASection)document.getElementsByTagName(key).item(iteration).getFirstChild();
				val = cds.getNodeValue();
			}
			else {
				val = "";
			}
			return val; 
		}

	
	
	
	public String getSnipDescription() {
		if (this.snipDescription == null) {
			this.snipDescription = "";
		}
		return this.snipDescription;
	}
	
	public String getSnipStartBlock() {
		if (this.snipStartBlock == null) {
			this.snipStartBlock = "";
		}
		return this.snipStartBlock;
	}
	
	public String getSnipEndBlock() {
		if (this.snipEndBlock == null) {
			this.snipEndBlock = "";
		}
		return this.snipEndBlock;
	}
	
	
}
