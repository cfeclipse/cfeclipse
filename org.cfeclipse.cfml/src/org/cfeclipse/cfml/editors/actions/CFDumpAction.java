/*
 * Created on Feb 15, 2004
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Rob
 *
 * Encloses highlighted text in a basic cfdump tag
 * 
 * 24/08/2009 markdrew: changed the functionality so that if you are in a script block you get the dump function
 * TODO: Should generalise this behaviour since we could/should do this a lot with various tags that we automatically insert. CF9 and Railo compat.
 */
public class CFDumpAction extends GenericEncloserAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {
	protected ITextEditor editor = null;
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor instanceof ITextEditor){
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run(IAction action){
		String startDump = "<cfdump var=\"#";
		String endDump = "#\">";
	
		
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
					startDump = "writedump(";
					endDump = ");\n";
					
					
				}
				this.enclose(doc,(ITextSelection)sel, startDump, endDump);
				// move the caret into the right place
				if(selectioner.getLength() == 0){
					editor.setHighlightRange(selectioner.getOffset() + startDump.length(), 1, true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
}
