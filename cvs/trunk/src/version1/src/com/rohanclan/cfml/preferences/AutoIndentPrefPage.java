package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.rohanclan.cfml.CFMLPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */


public class AutoIndentPrefPage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage, ICFMLPreferenceConstants {

	CFMLPreferenceManager cfmlpm;
	
	public AutoIndentPrefPage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("CFML & CFScript parsing options. ");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		addField(new BooleanFieldEditor(P_PARSE_DOCFSCRIPT, "Parse CFScript", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_PARSE_REPORT_ERRORS, "Report parse errors", getFieldEditorParent()));

	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}