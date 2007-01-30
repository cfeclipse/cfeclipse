package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;


public class WordWrapAction implements IEditorActionDelegate {

	CFMLEditor editor;
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		editor = null;
		if (targetEditor instanceof CFMLEditor) {
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
		if (editor == null && editor == null) {
			action.setEnabled(false);
		} else {
			action.setEnabled(true);
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
