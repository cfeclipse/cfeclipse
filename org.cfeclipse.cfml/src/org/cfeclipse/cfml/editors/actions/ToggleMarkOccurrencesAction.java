/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cfeclipse.cfml.editors.actions;


import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.CFMLEditorPart;
import org.cfeclipse.cfml.preferences.EditorPreferenceConstants;
import org.cfeclipse.cfml.preferences.TextSelectionPreferenceConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;


/**
 * A toolbar action which toggles the {@linkplain org.cfeclipse.cfml.preferences#EDITOR_MARK_OCCURRENCES mark occurrences preference}.
 * 
 * @since 3.1
 */
public class ToggleMarkOccurrencesAction extends TextEditorAction implements IPropertyChangeListener {
		
	private IPreferenceStore fStore;

	/**
	 * Constructs and updates the action.
	 */
	public ToggleMarkOccurrencesAction() {
		super(CFMLPlugin.getDefault().getResourceBundle(), "ToggleMarkOccurrencesAction.", null, IAction.AS_CHECK_BOX); //$NON-NLS-1$
		setImageDescriptor(PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor("icons/obj16/mark_occurrence.gif", null));
		setToolTipText("Mark Occurrences");		 //$NON-NLS-1$
		update();
	}
	
	/*
	 * @see IAction#actionPerformed
	 */
	public void run() {
		fStore.setValue(TextSelectionPreferenceConstants.P_MARK_OCCURRENCES, isChecked());
		ITextEditor editor= getTextEditor();
		if (editor instanceof CFMLEditorPart) {
			CFMLEditorPart cfe = (CFMLEditorPart) editor;
			if(isChecked()) {				
				(cfe).getSelectionCursorListener().updateOccurrenceAnnotations((ITextSelection) cfe.getSelectionProvider().getSelection(), cfe.getCFModel());
			} else {
				(cfe).getSelectionCursorListener().removeOccurrenceAnnotations();				
			}
		}
	}
	
	/*
	 * @see TextEditorAction#update
	 */
	public void update() {
		ITextEditor editor= getTextEditor();
		
		boolean checked= false;
		boolean enabled= false;
		if (editor instanceof CFMLEditorPart) {
			checked= ((CFMLEditorPart)editor).isMarkingOccurrences();
			enabled= ((CFMLEditorPart)editor).getCFModel() != null;
		}
			
		setChecked(checked);
		setEnabled(enabled);
	}
	
	/*
	 * @see TextEditorAction#setEditor(ITextEditor)
	 */
	public void setEditor(ITextEditor editor) {
		
		super.setEditor(editor);
		
		if (editor != null) {
			
			if (fStore == null) {
				fStore= CFMLPlugin.getDefault().getPreferenceStore();
				fStore.addPropertyChangeListener(this);
			}
			
		} else if (fStore != null) {
			fStore.removePropertyChangeListener(this);
			fStore= null;
		}
		
		update();
	}
	
	/*
	 * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(TextSelectionPreferenceConstants.P_MARK_OCCURRENCES))
			setChecked(Boolean.valueOf(event.getNewValue().toString()).booleanValue());
	}
}