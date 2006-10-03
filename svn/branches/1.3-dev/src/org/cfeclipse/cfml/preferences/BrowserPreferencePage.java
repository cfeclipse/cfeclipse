package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class BrowserPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	

	public BrowserPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
	}

	public void createFieldEditors() {
		// TODO Auto-generated method stub
		addField(new FileFieldEditor(BrowserPreferenceConstants.P_PRIMARY_BROWSER_PATH, "Primary Browser",getFieldEditorParent()));
		addField(new FileFieldEditor(BrowserPreferenceConstants.P_SECONDARY_BROWSER_PATH, "Secondary Browser",getFieldEditorParent()));
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
