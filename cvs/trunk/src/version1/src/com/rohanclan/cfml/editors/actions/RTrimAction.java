/*
 * Created on Oct 15, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.editors.codefolding.CodeFoldingSetter;


/**
 * @author Stephen Milligan
 */
public class RTrimAction  implements IEditorActionDelegate {
    
    ITextEditor editor = null;
    CodeFoldingSetter foldingSetter = null;
    
    /**
     * 
     */
    public RTrimAction() {
        super();
    }
    public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
        
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
			foldingSetter = new CodeFoldingSetter(editor);
		}
	}
	
	/**
	 * this gets called for every action
	 */
	public void run(IAction action) 
	{
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
		
		int currentLine = 0;
		int originalCursorOffset = sel.getOffset();
		int cursorOffset = originalCursorOffset;
		int originalSelectionLength = sel.getLength();
		int selectionLength = originalSelectionLength;
		StringBuffer newDoc = new StringBuffer();
		try {
		    //foldingSetter.takeSnapshot();
		    
			while (currentLine < doc.getNumberOfLines()) {
			    int offset = doc.getLineOffset(currentLine);
			    int length = doc.getLineLength(currentLine);
			    String oldText = doc.get(offset,length);
			    String newText = oldText.replaceAll("[\\t ]+$","");
			    
			    if (!newText.equals(oldText)) {
			        doc.replace(offset,length,newText);
				    
				    if (offset + length <= cursorOffset) {
				        if(oldText.length() != newText.length()) {
					        cursorOffset -= oldText.length() - newText.length();
				        }
				    }
				    else if (offset <= cursorOffset + selectionLength 
				            && selectionLength > 0) {
				        selectionLength -= oldText.length() - newText.length();
				        
				    }
				    // Check if the cursor is at the end of the line.
				    else if (offset + length == cursorOffset+2) {
				        cursorOffset -= 2;
				    }
			    }
			    currentLine++;
			}

			TextSelection selection = new TextSelection(doc,cursorOffset,selectionLength);
			editor.getSelectionProvider().setSelection(selection);
			//foldingSetter.restoreSnapshot();
		}
		catch (Exception blx) {
		    blx.printStackTrace();
		}
	}
	
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
