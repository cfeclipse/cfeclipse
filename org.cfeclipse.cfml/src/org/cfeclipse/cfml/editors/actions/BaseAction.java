package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author markdrew
 * This class should be used by all the actions so we don't have to repeat code
 */
public class BaseAction {
	protected ITextEditor editor = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof ITextEditor
				|| targetEditor instanceof CFMLEditor) {
			editor = (ITextEditor) ((CFMLEditor)targetEditor).getCFMLEditor();
		}
	}
	
}
