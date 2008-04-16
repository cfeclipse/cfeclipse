package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
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
public class MarkOccurrencesPreferencePage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage {

	CFMLPreferenceManager cfmlpm;
	
	public MarkOccurrencesPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("Here you can manage the behaviour of the occurrence marking of selections in the editor");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors() {
		try {
		addField(new BooleanFieldEditor(MarkOccurrencesPreferenceConstants.P_MARK_OCCURRENCES, "Enable Occurrence Marking", getFieldEditorParent()));
		addField(new StringFieldEditor(MarkOccurrencesPreferenceConstants.P_PART_OF_WORD_CHARS, "Default \"Part of word\" chars when double-clicking on text", getFieldEditorParent()));
		addField(new StringFieldEditor(MarkOccurrencesPreferenceConstants.P_BREAK_WORD_CHARS, "Default \"Break word at\" chars when double-clicking on text", getFieldEditorParent()));
		addField(new StringFieldEditor(MarkOccurrencesPreferenceConstants.P_PART_OF_WORD_CHARS_ALT, "\"Part of word\" chars when double-clicking on text holding alt", getFieldEditorParent()));
		addField(new StringFieldEditor(MarkOccurrencesPreferenceConstants.P_BREAK_WORD_CHARS_ALT, "\"Break word at\" chars when double-clicking on text holding alt", getFieldEditorParent()));
		addField(new StringFieldEditor(MarkOccurrencesPreferenceConstants.P_PART_OF_WORD_CHARS_SHIFT, "\"Part of word\" chars when double-clicking on text holding shift", getFieldEditorParent()));
		addField(new StringFieldEditor(MarkOccurrencesPreferenceConstants.P_BREAK_WORD_CHARS_SHIFT, "\"Break word at\" chars when double-clicking on text holding shift", getFieldEditorParent()));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void init(IWorkbench workbench) {
	}
}