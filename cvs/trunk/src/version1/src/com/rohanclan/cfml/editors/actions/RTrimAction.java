/*
 * Created on Oct 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RTrimAction  implements IEditorActionDelegate {
    
    ITextEditor editor = null;
    
    /**
     * 
     */
    public RTrimAction() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
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
			while (currentLine < doc.getNumberOfLines()) {
			    int offset = doc.getLineOffset(currentLine);
			    int length = doc.getLineLength(currentLine);
			    String oldText = doc.get(offset,length);
			    String newText = oldText.replaceAll("[\\t ]+$","");

			    newDoc.append(newText);
			    if (offset + length <= originalCursorOffset) {
			        if(oldText.length() != newText.length()) {
				        cursorOffset -= oldText.length() - newText.length();
			        }
			    }
			    else if (offset <= originalCursorOffset + originalSelectionLength 
			            && originalSelectionLength > 0) {
			        selectionLength -= oldText.length() - newText.length();
			        
			    }
			    // Check if the cursor is at the end of the line.
			    else if (offset + length == originalCursorOffset+2) {
			        cursorOffset -= 2;
			    }
			    
			    currentLine++;
			}
			doc.set(newDoc.toString());
			TextSelection selection = new TextSelection(doc,cursorOffset,selectionLength);
			editor.getSelectionProvider().setSelection(selection);
		}
		catch (BadLocationException blx) {
		    blx.printStackTrace();
		}
	}
	
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
