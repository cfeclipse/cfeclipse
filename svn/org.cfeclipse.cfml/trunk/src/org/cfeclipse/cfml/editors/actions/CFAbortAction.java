/*
 * Created on Feb 23, 2004
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
package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Paul V
 *  
 *  24/08/2009 markdrew: changed the functionality so that if you are in a script block you get the abort function
 */
public class CFAbortAction extends GenericEncloserAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	protected ITextEditor editor = null;

	public void run(IAction action){
		String startTag = "<cfabort>\n";
		String endTag = "";
	
		
		try {
			if(editor != null && editor.isEditable()){
				
				// Get the document
				IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
				
				// Get the selection 
				ISelection sel = editor.getSelectionProvider().getSelection();
				
				// Get the partition
				ITypedRegion partition = doc.getPartition(((ITextSelection)sel).getOffset());
				
				ITextSelection selectioner = (ITextSelection)sel;
				if(partition.getType().equals(CFPartitionScanner.CF_SCRIPT)){
					startTag = "abort();\n";
					endTag = "";
					
					
				}
				this.enclose(doc,(ITextSelection)sel, startTag, endTag);
				if(selectioner.getLength() == 0){
					editor.setHighlightRange(selectioner.getOffset() + startTag.length(), 1, true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
		
}
