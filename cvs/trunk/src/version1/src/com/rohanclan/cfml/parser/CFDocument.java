package com.rohanclan.cfml.parser;

import java.util.ArrayList;
import com.rohanclan.cfml.parser.DocItem;
import org.eclipse.jface.text.IDocument;
/** CFDocument basically is the main element for getting information about the ColdFusion document. It will contain the entire tree for the document, including CFScript'd items.
 *  
 *  CFDocument is supposed to represent the document tree, variable list and whatever else for out-of-editor and in-editor documents. So it can be used for documents that the user is editing and for background loading a file (or files).
 */
class CFDocument {
	protected String docFilename;
	public ArrayList  docRoot;	// Root elements of the document tree
	public ArrayList  docVariables;	// Array of variables

	public void addRootElement(DocItem newItem)
	{
		docRoot.add(newItem);
	}
	
	public void addVariable(Variable newVar)
	{
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