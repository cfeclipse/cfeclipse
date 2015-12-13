package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * This is the CFLint preference page
 * 
 * @author Oliver Tupman
 *
 */
public class CFLintPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	CFMLPreferenceManager cfmlpm;
	
	public CFLintPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("CFLint options.\n\n" +
						"Configure CFLint options");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		addField(new BooleanFieldEditor(CFLintPreferenceConstants.P_CFLINT_ENABLED, "Enable CFLint", getFieldEditorParent()));
	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}