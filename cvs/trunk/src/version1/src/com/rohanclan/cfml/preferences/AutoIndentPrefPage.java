package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.rohanclan.cfml.CFMLPlugin;
import org.eclipse.jface.preference.IPreferenceStore;


/**
 * THIS CLASS IS MEANT TO BE FOR THE FILE PARSER
 * For some reason Eclipse decides to put the wrong pref page under the wrong
 * title. This is a quick fix for the next release. I'll fix it sometime soon..
 * 
 * @author Oliver Tupman
 *
 */
public class AutoIndentPrefPage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage, ICFMLPreferenceConstants {

	CFMLPreferenceManager cfmlpm;
	
	public AutoIndentPrefPage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("CFML and CFScript parsing options.\n\n" +
						"The CFScript parser is HIGHLY experimental and is really only there\n" +
						"as a convenience for CFEclipse developers and those that are brave.\n\n" +
						"CFML is parsed automatically as it is used to create the CFC Methods\n" +
						"and outline views.");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		addField(new BooleanFieldEditor(P_PARSE_DOCFSCRIPT, "Parse CFScript (WARNING: HIGHLY EXPERIMENTAL.It WILL report incorrect ERRORS.)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_PARSE_REPORT_ERRORS, "Report parse errors", getFieldEditorParent()));

	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}