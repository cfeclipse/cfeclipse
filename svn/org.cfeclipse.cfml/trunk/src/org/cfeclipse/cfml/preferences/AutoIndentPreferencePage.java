package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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
public class AutoIndentPreferencePage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage {

	CFMLPreferenceManager cfmlpm;
	
	public AutoIndentPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("Here you can manage the behaviour of the auto-insertion of characters such as double quotes, as well as the auto-closing of tags");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors() {
		try {
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES, "Auto close \"s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES, "Auto close \'s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_HASHES, "Auto close #s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_PARENS, "Auto close parenthesis (i.e insert a closing \')\')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACKETS, "Auto close brackets (i.e insert a closing \']\')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_BRACES, "Auto close braces (i.e insert a closing \'}\')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_TAGS, "Auto close tags (i.e. insert a closing \'>\')", getFieldEditorParent()));
			addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_USE_SMART_COMMENTS,
					"Smart comments (i.e. insert a closing **/)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS, "Auto insert a closing tag", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_USE_SMART_INDENT, "Use smart indenting", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOINDENT_ONTAGCLOSE, "Auto-indent on tag close", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_USE_SMART_PASTE, "Auto-indent pasted tags", getFieldEditorParent()));

			addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOINSERT, "Automatically insert suggestion if only one",
					getFieldEditorParent()));
			addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOACTIVATION, "Automatically activate when chars detected",
					getFieldEditorParent()));
		addField(new StringFieldEditor(AutoIndentPreferenceConstants.P_AUTOACTIVATION_CHARS, "Auto-activation chars", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_TAGS, "Suggest CFML Tags", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_FUNCTIONS, "Suggest Functions", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_SCOPES, "Suggest Scopes", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_VARIABLES, "Suggest Variables", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_HTML, "Suggest HMTL Tags", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_TEMPLATES, "Suggest Templates", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_SUGGEST_TEMPLATES, "Suggest Templates", getFieldEditorParent()));
		
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