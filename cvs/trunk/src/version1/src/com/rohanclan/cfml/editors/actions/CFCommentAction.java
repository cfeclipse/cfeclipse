/*
 * Created on Jan 31, 2004
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
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.testing.ITestHarness;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;
import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;

/**
 * @author Rob
 *
 * The adds cold fusion style comments around the selected text (or just sticks
 * in the comments if no text is selected) 
 */
public class CFCommentAction extends GenericEncloserAction implements IEditorActionDelegate{
	protected ITextEditor editor = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor instanceof ITextEditor){
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run(IAction action){
		//checks to see if you can edit the document
		try {
			if(editor != null && editor.isEditable()){
				//Get the document
				IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
				ISelection sel = editor.getSelectionProvider().getSelection();
				String parttype = doc.getPartition(((ITextSelection)sel).getOffset()).getType();
			    
				
				//if we already are in a comment parition, remove it, else add it
				if(parttype.equals(CFPartitionScanner.CF_COMMENT)){
					//Now find and replace the comment strings
					
					//This doesnt actually work right if you have nested comments.
					//Maybe use the partitioner here to find the ending part
					FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(doc);
				
					finder.find(((ITextSelection)sel).getOffset(), "<!---", false, false, false, false);
					finder.replace("", false);
					
					finder.find(((ITextSelection)sel).getOffset(), "--->", true, false, false, false);
					finder.replace("", false);
					
					
					
				}
				else{
					ITextSelection selectioner = (ITextSelection)sel;
					System.out.println("selection " + selectioner.getLength());
					
					this.enclose(doc,(ITextSelection)sel,"<!--- "," --->");
					
					//move the caret somewhere.
					if(selectioner.getLength() == 0){
						editor.setHighlightRange(selectioner.getOffset() + "<!--- ".length(), 1, true);
					}
					
				}
			}
		} catch (BadLocationException e) {
			e.printStackTrace(System.err);
		}
	}
	public void selectionChanged(IAction action, ISelection selection){;}
}
