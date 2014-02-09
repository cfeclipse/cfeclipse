package org.cfeclipse.cfml.preferences;

import java.io.File;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class BrowserPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private FileFieldEditorSpecial primaryBrowserField;
	private FileFieldEditorSpecial secondaryBrowserField;

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

	public BrowserPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
	}

	public void createFieldEditors() {
		primaryBrowserField = new FileFieldEditorSpecial(BrowserPreferenceConstants.P_PRIMARY_BROWSER_PATH,
				"Primary Browser", getFieldEditorParent());
		secondaryBrowserField = new FileFieldEditorSpecial(BrowserPreferenceConstants.P_SECONDARY_BROWSER_PATH,
				"Secondary Browser", getFieldEditorParent());
		addField(primaryBrowserField);
		addField(secondaryBrowserField);
		addField(new StringFieldEditor(BrowserPreferenceConstants.P_TESTCASE_QUERYSTRING, "Querystring for TestCases",
				getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange
	 * (org.eclipse.jface.util.PropertyChangeEvent)
	 * 
	 * We override this because OS X "applications" are really app "bundles", so
	 * resolve to a Directory vs a File.
	 * 
	 * All the craziness below is to override the default validation stuff.
	 * Crazy.
	 */
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {
			checkState();
		}
	}

	public class FileFieldEditorSpecial extends FileFieldEditor {
		public FileFieldEditorSpecial(String name, String labelText, Composite parent) {
			super(name, labelText, true, StringButtonFieldEditor.VALIDATE_ON_KEY_STROKE, parent);
		}

		@Override
		protected void refreshValidState() {
			// TODO Auto-generated method stub
			setErrorMessage(null);
			super.refreshValidState();
			showErrorMessage();
		}

		@Override
		protected boolean checkState() {
			// TODO Auto-generated method stub
			return isValid();
		}

		public boolean isValid() {

			if (getStringValue().length() == 0) {
				return true;
			} else if (new File(getStringValue()).exists()) {
				clearErrorMessage();
				return true;
			}
			setErrorMessage("Cannot find the specified application");
			showErrorMessage();
			return false;
		}
	}

}
