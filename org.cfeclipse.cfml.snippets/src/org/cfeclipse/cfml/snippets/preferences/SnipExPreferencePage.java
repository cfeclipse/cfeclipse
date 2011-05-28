package org.cfeclipse.cfml.snippets.preferences;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SnipExPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	CFMLPreferenceManager cfmlpm;
	DirectoryFieldEditor snippetsPathField;
	CFMLPreferenceManager preferenceManager;
	
	public SnipExPreferencePage() {
		super(GRID);
		setPreferenceStore(SnippetPlugin.getDefault().getPreferenceStore());
		setDescription("These preferences will allow you to customize the what SnipEx servers will be aggregated in your snippet view.\n\n");
		cfmlpm = SnippetPlugin.getDefault().getPreferenceManager();
	}

	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor(CFMLPreferenceConstants.P_SNIPPETS_PATH, "Default snippets directory", getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL1,"URL 1",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL2,"URL 2",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL3,"URL 3",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL4,"URL 4",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL5,"URL 5",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL6,"URL 6",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL7,"URL 7",getFieldEditorParent()));
		addField(new StringFieldEditor(SnipExPreferenceConstants.P_SNIPEX_URL8,"URL 8",getFieldEditorParent()));

	}

    protected void performDefaults() {
        super.performDefaults();
        snippetsPathField.setStringValue(preferenceManager.getPluginStateLocation());
    }
	
	public void init(IWorkbench workbench) {}

}
