package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * THIS CLASS IS MEANT TO BE FOR THE AUTO-INSERTION/COMPLETION
 * For some reason Eclipse decides to put the wrong pref page under the wrong
 * title. This is a quick fix for the next release. I'll fix it sometime soon..
 * 
 * @author Oliver Tupman
 *
 */
public class CodeStylePreferencePage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage {

	CFMLPreferenceManager cfmlpm;
	
	public CodeStylePreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("See the Formatter sub-category for configuration");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors() {
		try {
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void performDefaults() {
		super.performDefaults();
	}

	public void init(IWorkbench workbench) {
	}
}