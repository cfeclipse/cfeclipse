/*
 * Created on Oct 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.actions.codefolding;

import java.util.LinkedList;
import java.util.Iterator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.projection.*;

import com.rohanclan.cfml.editors.CFEUndoManager;
import com.rohanclan.cfml.editors.pairs.Pair;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UnFoldSelectionAction  implements IEditorActionDelegate {
    
    ITextEditor editor = null;
    
    /**
     * 
     */
    public UnFoldSelectionAction() {
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
			int lineNumber = -1;
			try {
			    lineNumber = doc.getLineOfOffset(textSelection.getOffset());
			
				ProjectionAnnotationModel model= (ProjectionAnnotationModel) editor.getAdapter(ProjectionAnnotationModel.class);
				if (model != null) {
				    Iterator i = model.getAnnotationIterator();
				    while (i.hasNext()) {
				        Object o = i.next();
				        if (o instanceof ProjectionAnnotation) {
				            ProjectionAnnotation annotation = (ProjectionAnnotation)o;
				            Position position = model.getPosition(annotation);
				            if (position.offset >= doc.getLineOffset(lineNumber) 
				                    && position.offset <= doc.getLineOffset(lineNumber) + doc.getLineLength(lineNumber)) {
				                model.removeAnnotation(annotation);
				            }
				        }
				    }
				}
			}
			catch (BadLocationException bex) {
			    bex.printStackTrace();
			}

		}
	}
	
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
