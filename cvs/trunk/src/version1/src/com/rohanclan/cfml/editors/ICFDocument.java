/*
 * Created on Apr 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors;

//import org.eclipse.jface.text.AbstractDocument;

import com.rohanclan.cfml.parser.CFParser;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
//import org.eclipse.jface.text.IDocument; 
import org.eclipse.jface.text.Document;

/**
 * @author Rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ICFDocument extends Document {

	private CFParser docParser = null;
	private static IResource lastRes = null;
	
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
			//System.out.println("Runnin");
			docParser.parseDoc();
		}
	}
	
	public void setParserResource(IResource newRes)
	{
		lastRes = newRes;
		docParser = new CFParser(this, lastRes);
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
}
