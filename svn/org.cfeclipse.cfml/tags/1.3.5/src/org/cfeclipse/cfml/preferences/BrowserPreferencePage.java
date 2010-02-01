package org.cfeclipse.cfml.preferences;

import java.util.Map;
import java.util.Properties;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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
		
		if(System.getProperty("os.name").equals("Mac OS X")){
			addField(new DirectoryFieldEditor(BrowserPreferenceConstants.P_PRIMARY_BROWSER_PATH, "Primary Browser", getFieldEditorParent()));
			addField(new DirectoryFieldEditor(BrowserPreferenceConstants.P_SECONDARY_BROWSER_PATH, "Secondary Browser", getFieldEditorParent()));
		}
		else {
			addField(new FileFieldEditor(BrowserPreferenceConstants.P_PRIMARY_BROWSER_PATH, "Primary Browser",getFieldEditorParent()));
			addField(new FileFieldEditor(BrowserPreferenceConstants.P_SECONDARY_BROWSER_PATH, "Secondary Browser",getFieldEditorParent()));
		}
		addField(new StringFieldEditor(BrowserPreferenceConstants.P_TESTCASE_QUERYSTRING, "Querystring for TestCases",getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
