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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.parser.CFParser;

public class CFDocumentProvider extends FileDocumentProvider {

	private static IPath lastFilename;
	private static IResource lastRes = null;
	public static void setLastFilename(IPath newFilename)
	{
		lastFilename = newFilename;
		System.out.println("CFDocumentProvider::setLastFilename() - <b>Last</b> filename is set to " + newFilename.toString());
	}
	
	public static void setLastResource(IResource newRes)
	{
		lastRes = newRes;
	}
	
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			
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
			//
			// Save the document to trigger an event for the change listener. The change listener
			// will then call our Delta Visitor which will call out setLastFilename() method.
			// TODO: Work out how to properly obtain a filename from the IDocument! (this is soooo a bodge job!)
			
			saveDocument(null, element, document, true);
			CFParser docParser = new CFParser(document, lastRes);
			//
			// Delete all of the problem markers for the resource 
			lastRes.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ONE);
			

 			// 
 			// Uncomment the following code to remove ALL markers from the Problem list. Useful if we've
 			// stuck in a non-transient marker that won't go away!
/*			
			IWorkspaceRoot myWorkspaceRoot = CFMLPlugin.getWorkspace().getRoot();
			IMarker [] markers = myWorkspaceRoot.findMarkers(null, true, IResource.DEPTH_INFINITE);
			for(int i = 0; i < markers.length; i++)
			{
				markers[i].delete();
			}
*/
			
			docParser.parseDoc();	// Run the parser. Nothing is done with the resultant data at present.

			document.setDocumentPartitioner(partitioner);
			
			
		}
		return document;
	}

}