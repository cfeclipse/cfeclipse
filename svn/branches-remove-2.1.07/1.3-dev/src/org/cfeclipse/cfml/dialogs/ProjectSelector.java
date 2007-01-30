package org.cfeclipse.cfml.dialogs;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.widgets.Composite;

public class ProjectSelector extends FieldEditor {

	 public ProjectSelector(String name, String labelText, Composite parent) {
	        init(name, labelText);
	      //  setErrorMessage(JFaceResources
	      //          .getString("DirectoryFieldEditor.errorMessage"));//$NON-NLS-1$
	      //  setChangeButtonText(JFaceResources.getString("openBrowse"));//$NON-NLS-1$
	      //  setValidateStrategy(VALIDATE_ON_FOCUS_LOST);
	        createControl(parent);
	    }
	
	protected void adjustForNumColumns(int numColumns) {
		// TODO Auto-generated method stub

	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		// TODO Auto-generated method stub

	}

	protected void doLoad() {
		// TODO Auto-generated method stub

	}

	protected void doLoadDefault() {
		// TODO Auto-generated method stub

	}

	protected void doStore() {
		// TODO Auto-generated method stub

	}

	public int getNumberOfControls() {
		// TODO Auto-generated method stub
		return 0;
	}

}
