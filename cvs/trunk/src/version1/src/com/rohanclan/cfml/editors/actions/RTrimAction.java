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
		TextSelection selection = new TextSelection(doc,sel.getOffset(),sel.getLength());
		try {
			while (currentLine < doc.getNumberOfLines()) {
			    int offset = doc.getLineOffset(currentLine);
			    int length = doc.getLineLength(currentLine);
			    String oldText = doc.get(offset,length);
			    String newText = oldText.replaceAll("[\\t ]+$","");
			    doc.replace(offset,length,newText);
			    currentLine++;
			}
			editor.getSelectionProvider().setSelection(selection);
		}
		catch (BadLocationException blx) {
		    blx.printStackTrace();
		}
	}
	
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
