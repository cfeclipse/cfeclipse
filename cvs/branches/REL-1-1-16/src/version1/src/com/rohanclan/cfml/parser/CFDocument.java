/*
 * Created on Mar 21, 2004
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
package com.rohanclan.cfml.parser;

//import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import com.rohanclan.cfml.parser.DocItem;
//import com.rohanclan.cfml.parser.exception.InvalidChildItemException;

import org.eclipse.jface.text.IDocument;
/** 
 * CFDocument basically is the main element for getting information about the 
 * ColdFusion document. It will contain the entire tree for the document, 
 * including CFScript'd items.
 *  
 * CFDocument is supposed to represent the document tree, variable list and 
 * whatever else for out-of-editor and in-editor documents. So it can be used 
 * for documents that the user is editing and for background loading a file 
 * (or files).
 */
public class CFDocument {
	/**
	 * Filename that this CFDocument is associated with. Don't think it's needed.
	 */
	protected String docFilename; 
	/**
	 * The root elements of the document. A CF document does NOT have to have a root element and
	 * so one is not inserted by default.
	 * @deprecated Use getDocumentRoot() to get the DocItem representing the root of the document
	 */
	public ArrayList docRoot;
	/**
	 * List of variables in the document. Not used at the moment.
	 */
	public ArrayList docVariables;	

	/**
	 * Not sure what this is for!
	 * @deprecated Do not use. Use getDocumentRoot()
	 */
	public Stack docTree = null;
	
	protected DocItem treeRoot = null;
	
	public void addRootElement(DocItem newItem)
	{
		if(docRoot == null)
		{
			docRoot = new ArrayList();
		}
		docRoot.add(newItem);
	}

	public void setDocumentRoot(DocItem newRootItem)
	{
		treeRoot = newRootItem;
	}
	
	public DocItem getDocumentRoot()
	{
		if(treeRoot == null)
		{
			System.err.println(
				"CFDocument::getDocumentRoot() - WARNING: treeRoot is null, have you run the parser yet?"
			);
		}
		return treeRoot;
	}
	
	public void addVariable(Variable newVar)
	{
		if(docVariables == null)
		{
			docVariables = new ArrayList();
		}
		docVariables.add(newVar);
	}
	
	public String getFilename() {
		return docFilename;
	}

	public CFDocument()
	{
		docFilename = "";
	}
	
	public CFDocument(String filename) {
		docFilename = filename;
	}

	public CFDocument(IDocument eclipseDocument) {
		
	}
}