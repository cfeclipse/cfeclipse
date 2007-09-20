package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SnipExPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	CFMLPreferenceManager cfmlpm;
	
	public SnipExPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("These preferences will allow you to customize the what SnipEx servers will be aggregated in your snippet view.\n\n");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	protected void createFieldEditors() {
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL1,"URL 1",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL2,"URL 2",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL3,"URL 3",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL4,"URL 4",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL5,"URL 5",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL6,"URL 6",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL7,"URL 7",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL8,"URL 8",getFieldEditorParent()));

	}

	public void init(IWorkbench workbench) {}

}
