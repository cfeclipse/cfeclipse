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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.rohanclan.cfml.parser.CFParser;

public class CFDocumentProvider extends FileDocumentProvider {

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
			// Now begins the fun of obtaining the resource that represents the file that has
			// been opened and that this createDocument() method has been called upon.
			//
			// Opening a file should open an editor, so we check that it is and if so make the
			// appropriate cast.
			// To obtain the filename would you believe we use the editor's tooltip text!! This
			// is probably a bodge as it may be possible for an editor derived from IEditorInput
			// to change this, but it seems to work.
			// Having got the filename, it will consist of <projectName>/<path2File>. We grab
			// the project name & path2file and then get the relevant project. We then get
			// the IFile (which is implements [?] IResource) and away we go. 
			IFile file = null;
			if(element instanceof IEditorInput)
			{
				IEditorInput eleEditor = (IEditorInput)element;
				
				String filename = eleEditor.getToolTipText();
				IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
				
				String projectName = "";
				String relFilename = "";
				relFilename = filename.substring(filename.indexOf('/')+1);
				projectName = filename.substring(0, filename.indexOf('/'));
				IProject tempProject = myWorkspaceRoot.getProject(projectName);
				
				try {
					
					if(tempProject != null)
						file = tempProject.getFile(relFilename);
					else
						System.err.println("Project is null");
					
					IMarker temp = file.createMarker(IMarker.PROBLEM);
					
				}catch(Exception excep) 
				{
					System.out.println("Caught exception: " + excep.getMessage());
				}
			}			

			if(file != null)
			{
				//
				// Delete all of the problem markers for the resource 
				file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ONE);

				//
				// Parse the document.
				CFParser docParser = new CFParser(document, file);
				docParser.parseDoc();	
			}
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
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}

}