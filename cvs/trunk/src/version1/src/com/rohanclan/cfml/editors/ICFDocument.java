/*
 * Created on Apr 5, 2004
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
package com.rohanclan.cfml.editors;

//import org.eclipse.jface.text.AbstractDocument;

//import java.util.Iterator;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.parser.CFDocument;
import com.rohanclan.cfml.parser.CFNodeList;
import com.rohanclan.cfml.parser.CFParser;
import com.rohanclan.cfml.parser.CommentParser;
import com.rohanclan.cfml.parser.CfmlTagItem;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;
//import com.rohanclan.cfml.parser.DocItem;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
//import org.eclipse.jface.text.IDocument; 
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;

//import org.eclipse.ui.IEditorPart;

/**
 * @author Rob
 *
 * This is mostly just a wrapper document that holds the parser and is
 * attached to a Document. So if you can get access to a document you
 * can get to this and get the parser
 */
public class ICFDocument extends Document {

	private /* static */ CFParser docParser = null;
	private /* static */ IResource lastRes = null;
	private CommentParser commentParser = new CommentParser();
	private static Thread t;
	private CFDocument docStructure = null;
	
	public ICFDocument()
	{
		super();
	}
	
	public CFParser getParser()
	{
		return docParser;
	}
	
	public void parseDocument()
	{
		if(docParser != null)
		{
			
		    IPreferenceStore prefStore = CFMLPlugin.getDefault().getPreferenceStore();
			docParser.setCFScriptParsing(prefStore.getBoolean(ICFMLPreferenceConstants.P_PARSE_DOCFSCRIPT));
			docParser.setReportErrors(prefStore.getBoolean(ICFMLPreferenceConstants.P_PARSE_REPORT_ERRORS));
			docStructure = docParser.parseDoc();
			
			commentParser.ParseDocument(this,lastRes);
			commentParser.setTaskMarkers();
			
			if(docStructure == null)
				System.err.println(
					"ICFDocument::parseDocument() - Parse result is null!"
				);
		}
	}
	
	/**
	 * Gets a tag object given a starting and ending position
	 * @param startpos
	 * @param endpos
	 * @return
	 */
	public CfmlTagItem getTagAt(int startpos, int endpos)
	{
		//build the xpath
		String attrString = "[#startpos<" + startpos + " and #endpos>" + endpos + "]";
		CFDocument docRoot = getCFDocument();
		CFNodeList matchingNodes = docRoot.getDocumentRoot().selectNodes(
			"//*" + attrString
		);
		
		//there should only be 0 or 1 nodes in any one position (unless it spans
		//more then one tag I suppose
		if(matchingNodes.size() > 0)
		{
			return (CfmlTagItem)matchingNodes.get(0);
		}
		
		return null;
	}
	
	/**
	 * Gets the tag name at the given position (i.e. include,set,module,etc)
	 * @param startpos
	 * @param endpos
	 * @return
	 */
	public String getTagNameAt(int startpos, int endpos)
	{
		String str = null;
		CfmlTagItem cti = getTagAt(startpos,endpos);
		
		if(cti != null)
		{
			return cti.getName();
		}
		
		return null;
	}
	
	public void setParserResource(IResource newRes)
	{
		lastRes = newRes;
		
		if(docParser == null)
		{
			docParser = new CFParser(this, lastRes);
			docParser.setCFScriptParsing(false);
		}
		else
		{
			docParser.parseDoc(this);
		}
	}
	
	public IResource getResource() {
		return this.lastRes;
	}
	
	public void clearAllMarkers()
	{
		try
		{
			lastRes.deleteMarkers(
					IMarker.PROBLEM, true, IResource.DEPTH_ONE
				);
			
			lastRes.deleteMarkers(
					IMarker.TASK, true, IResource.DEPTH_ONE
				);
			

		}
		catch(CoreException ce)
		{
			ce.printStackTrace(System.err);
		}
	}
	
	public CFDocument getCFDocument()
	{
		return docStructure;
	}
}
