/*
 * Created on Oct 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.actions.codefolding;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.projection.*;


/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FoldSelectionAction  implements IEditorActionDelegate {
    
    ITextEditor editor = null;
    
    /**
     * 
     */
    public FoldSelectionAction() {
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

		ISelection selection= editor.getSelectionProvider().getSelection();
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection= (ITextSelection) selection;
			if (!textSelection.isEmpty()) {
				ProjectionAnnotationModel model= (ProjectionAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
				if (model != null) {
					int start= textSelection.getStartLine();
					int end= textSelection.getEndLine();
					try {
						IDocument document= editor.getDocumentProvider().getDocument(editor.getEditorInput());
						int offset= document.getLineOffset(start);
						int endOffset= document.getLineOffset(end + 1);
						Position position= new Position(offset, endOffset - offset);
						model.addAnnotation(new ProjectionAnnotation(true), position);
						
					} catch (BadLocationException x) {
					}
				}
			}
		}
	}
	
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
