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

import com.rohanclan.cfml.parser.CFDocument;
import com.rohanclan.cfml.parser.CFParser;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
//import org.eclipse.jface.text.IDocument; 
import org.eclipse.jface.text.Document;

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
			docStructure = docParser.parseDoc();
			
			if(docStructure == null)
				System.err.println(
					"ICFDocument::parseDocument() - Parse result is null!"
				);
		}
	}
	
	public void setParserResource(IResource newRes)
	{
		lastRes = newRes;
		
		if(docParser == null)
		{
			docParser = new CFParser(this, lastRes);
		}
		else
		{
			docParser.parseDoc(this);
		}
	}
	
	public void clearAllMarkers()
	{
		try
		{
			lastRes.deleteMarkers(
				IMarker.PROBLEM, true, IResource.DEPTH_ONE
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
