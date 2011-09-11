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

package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/*
 * The page to configure the code formatter options.
 */
public class DictionariesPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	DirectoryFieldEditor dictionaryDirPathField;
	CFMLPreferenceManager preferenceManager;
	
	public DictionariesPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("These preferences will allow you to customize the Dictionaries.\n\n");
	}

	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor(CFMLPreferenceConstants.P_DICTIONARIES_PATH, "Dictionary directory", getFieldEditorParent()));
	}

	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(DirectoryFieldEditor.VALUE)) {
			checkState();
		}
	}

	protected void performDefaults() {
		super.performDefaults();
	}
	
	public void init(IWorkbench workbench) {
	}

}
