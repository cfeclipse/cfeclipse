/*
 * Created on Jan 30, 2004
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

/**
 * @author Rob
 *
 * You got me. This was a wizard generated file seems to do partition stuff too
 */
//import org.eclipse.core.resources.IMarker;
//import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.IMarker;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IResource;
//import org.eclipse.core.resources.IWorkspaceRoot;
//import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;
//import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.core.runtime.IProgressMonitor;

//import com.rohanclan.cfml.CFMLPlugin;
//import com.rohanclan.cfml.parser.CFParser;
//import com.rohanclan.cfml.parser.CFParser;

public class CFDocumentProvider extends FileDocumentProvider {

	//private static IPath lastFilename;
	//private static IResource lastRes = null;
	
	/* public static void setLastFilename(IPath newFilename)
	{
		lastFilename = newFilename;
		System.out.println(
			"CFDocumentProvider::setLastFilename() - <b>Last</b> filename is set to " 
			+ newFilename.toString()
		);
	}
	
	public static void setLastResource(IResource newRes)
	{
		lastRes = newRes;
	} */
	
	protected IDocument createDocument(Object element) throws CoreException 
	{
		ICFDocument document = null; 
		
		document = new ICFDocument();
		if(setDocumentContent(document, (IEditorInput)element, getEncoding(element))) 
		{
			setupDocument(element, document);
			System.out.println("Document created");
		}
		
		if (document != null) 
		{
			IDocumentPartitioner partitioner = new DefaultPartitioner(
				new CFPartitionScanner(),
				new String[] 
				{
					CFPartitionScanner.ALL_TAG,
					CFPartitionScanner.HTM_COMMENT,
					CFPartitionScanner.CF_TAG,
					CFPartitionScanner.CF_END_TAG,
					CFPartitionScanner.CF_SCRIPT,
					CFPartitionScanner.J_SCRIPT,
					CFPartitionScanner.CSS_TAG,
					CFPartitionScanner.UNK_TAG
				}
			);
			
			partitioner.connect(document);
			
			//returns an IFile which is a subclass of IResource
			try
			{
				if(element instanceof FileEditorInput)
				{
					document.setParserResource( ((FileEditorInput)element).getFile() );
					
					// Now begins the fun of obtaining the resource that represents the file that has
					// been opened and that this createDocument() method has been called upon.
					//
					// Delete all of the problem markers for the resource
					document.clearAllMarkers();
					
					//Run the parser. Nothing is done with the resultant data at present.
					//docParser.parseDoc();
					document.parseDocument();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
			}
			
			document.setDocumentPartitioner(partitioner);	
		}
		return document;
	}
	
	protected void doSaveDocument(IProgressMonitor monitor, Object element, 
			IDocument document, boolean overwrite) throws CoreException
	{
		if(document instanceof ICFDocument)
		{
			((ICFDocument)document).clearAllMarkers();
			((ICFDocument)document).parseDocument();
		}
		super.doSaveDocument(monitor,element,document,overwrite);
	}
	
}