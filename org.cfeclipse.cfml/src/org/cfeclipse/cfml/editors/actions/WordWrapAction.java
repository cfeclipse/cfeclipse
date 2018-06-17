package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;


public class WordWrapAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	CFMLEditor editor;
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if( targetEditor instanceof ITextEditor )
		{
			editor = (CFMLEditor)targetEditor;
		}
	}

	public void run(IAction action) {
		StyledText textWidget = null;
		if (editor != null) {
			textWidget = editor.getTextWidget(); 
			
		}
		if (textWidget != null) {
			textWidget.setWordWrap(!textWidget.getWordWrap());
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (editor != null && editor.getSite().getPage().getActiveEditor() instanceof CFMLEditor) {
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if(activeEditor instanceof CFMLEditor){
			editor = (CFMLEditor)activeEditor;
		}
		
	}
	
}
