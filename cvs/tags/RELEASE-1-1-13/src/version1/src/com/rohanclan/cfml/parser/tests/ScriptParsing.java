/*
 * Created on Jun 30, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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
package com.rohanclan.cfml.parser.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rohanclan.cfml.parser.cfscript.ParseException;
import com.rohanclan.cfml.parser.cfscript.SPLParser;
import com.rohanclan.cfml.parser.cfscript.SimpleNode;

import junit.framework.TestCase;

/**
 * @author Oliver Tupman
 *
 */
public class ScriptParsing extends TestCase {
	protected int countTextNodes(Node xmlNode)
	{
		int count = 0;
		NodeList children = xmlNode.getChildNodes();
		
		for(int i = 0; i < children.getLength(); i++)
		{
			if(children.item(i).getNodeName().compareToIgnoreCase("#text") == 0)
				count++;
		}
		return count;
	}
	
	public boolean compareTrees(Node xmlNode, SimpleNode parseResult) 
	{
		System.out.println("Got item \'" + parseResult.toString() + "\' ");
		System.out.println("XML Got item \'" + xmlNode.getNodeName() + "\'");
		int compResult = xmlNode.getNodeName().compareToIgnoreCase(parseResult.toString());
		boolean comparisonResult = xmlNode.getNodeName().compareToIgnoreCase(parseResult.toString()) == 0;
		assertTrue("Apparently the xml item \'" + xmlNode.getNodeName() + "\' and the parse node \'" + parseResult.toString() + "\' do not match!", 
					comparisonResult);
		
		if(!comparisonResult)
			return false;
		
		NodeList children = xmlNode.getChildNodes();
		int xmlChildCount = children.getLength();
		int docChildCount = 0;
		int xmlTextNodeCount = 0;
		
		if(parseResult.children != null)
			docChildCount = parseResult.children.length;
		
		if(xmlChildCount > 0)
		{
			xmlTextNodeCount = countTextNodes(xmlNode);
		}
		
		boolean childCountComparison = xmlChildCount - xmlTextNodeCount == docChildCount;
		assertTrue("Child count incorrect. Xml: " + (xmlChildCount - xmlTextNodeCount) + ", parser: " + docChildCount, childCountComparison);
		if(!childCountComparison)
			return false;
		
		int xmlPos = 0;
		int parsePos = 0;
		
		for(; xmlPos < children.getLength();)
		{
			if(children.item(xmlPos).getNodeName().compareToIgnoreCase("#text") != 0)
			{
				compareTrees(children.item(xmlPos), (SimpleNode)parseResult.children[parsePos]);
				parsePos++;
			}
			

			xmlPos++;
		}
		
		return true;
	}
	
	protected DocumentBuilder getDocBuilder()
	{
		DocumentBuilderFactory buildFact;
		DocumentBuilder builder;
		
		try {
			buildFact  = DocumentBuilderFactory.newInstance();
			buildFact.setIgnoringComments(true);
			buildFact.setIgnoringElementContentWhitespace(true);
			buildFact.setCoalescing(true);			
			builder = buildFact.newDocumentBuilder();
		} catch(ParserConfigurationException e) {
			fail("Could not get instance of a document builder");
			return null;
		}
		return builder;
	}
	
	protected Document loadXml(String filename)
	{
		Document retDoc = null;
		DocumentBuilder builder = getDocBuilder();
		
		try {
			 retDoc = builder.parse(this.testFileDir + filename);
		} catch(IOException e) {
			fail("Error parsing " + filename);
			e.printStackTrace();
			return null;
		} catch(SAXException saxEx) {
			fail("SAXException occurred");
			saxEx.printStackTrace();
			return null;
		}
		
		return retDoc;
	}
	
	protected SimpleNode loadAndParse(String filename)
	{
		//SPLParser parser = null;
		try {
//			parser = new SPLParser(new java.io.FileInputStream(filename));
			tempParser.ReInit(new java.io.FileInputStream(testFileDir + filename));
			
		} catch(FileNotFoundException e)
		{
			fail("Could not open the test file " + filename);
			e.printStackTrace();
			return null;
		}
		SimpleNode rootNode;
		
		try {
			tempParser.CompilationUnit();
			System.out.println("Hello world");
			rootNode = (SimpleNode)tempParser.getDocumentRoot();
			rootNode.dump("treeDump:");
			
		} catch(ParseException parseE) {
			fail("Parse error: " + parseE.getMessage());
			parseE.printStackTrace();
			System.out.println("Hello world");
			return null;
		} catch(Exception e) {
			fail("Caught unhandled exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		return rootNode;
	}
	static private SPLParser tempParser = null;
	String testFileDir = "C:\\Program Files\\Eclipse\\workspace\\CFEclipse\\src\\com\\rohanclan\\cfml\\parser\\tests\\";
	/**
	 * This needs to be here to initialise the Parser for the first time due to
	 * the fact that it's static by default. Turning STATIC=false in the JJT
	 * file simply seems to leave the generated parser still attempting to 
	 * work in a static way! 
	 */
	static {
		tempParser = new SPLParser(new StringReader(""));
	}
	
	public void test1() {
		Document docRoot;
		
		docRoot = loadXml("test1.xml");
		Node xmlRoot  = docRoot.getChildNodes().item(0);
		SimpleNode rootNode = loadAndParse("test1.cfm");
		rootNode.dump("");
		compareTrees(xmlRoot.getChildNodes().item(1), rootNode);
	}
	
	public void test2() {
		/*
		Document docRoot;
		docRoot = loadXml("test2.xml");
		Node xmlRoot = docRoot.getChildNodes().item(0);
		SimpleNode rootNode = loadAndParse(testFileDir + "\\test2.spl");
		*/
	}
		
	/*
	static private SPLParser parser = new SPLParser(new StringReader(""));
	static {
		
	}
	public void testParser() {
		try {
			parser.ReInit(new FileReader("C:\\Program Files\\Eclipse\\workspace\\CFEclipse\\src\\com\\rohanclan\\cfml\\parser\\tests\\test1.cfm"));
		} 
		catch(FileNotFoundException ex)	{
			ex.printStackTrace();
			return;
		}
		
		try {
			parser.CompilationUnit();
		} catch(ParseException parseEx) {
			assertTrue(parseEx.getMessage(), false);
			parseEx.printStackTrace();
			
			return;
		}
		
		((SimpleNode)parser.getDocumentRoot()).dump("");
		
	}
	*/
}
