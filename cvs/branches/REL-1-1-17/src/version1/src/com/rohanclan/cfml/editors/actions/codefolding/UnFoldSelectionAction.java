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
import org.eclipse.ui.IEditorPart;
import org.eclipse.jface.action.IAction;
import com.rohanclan.cfml.editors.codefolding.CodeFoldingSetter;


/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UnFoldSelectionAction  implements IEditorActionDelegate {
    
    ITextEditor editor = null;
    CodeFoldingSetter foldingSetter = null;
    
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
			foldingSetter = new CodeFoldingSetter(editor);
			
		}
	}
	
	/**
	 * this gets called for every action
	 */
	public void run(IAction action) 
	{
	    foldingSetter.removeFoldFromSelection();
		
	}
	
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
