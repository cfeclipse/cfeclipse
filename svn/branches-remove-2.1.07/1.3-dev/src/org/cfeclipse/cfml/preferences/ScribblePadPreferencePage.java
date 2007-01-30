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
public class ScribblePadPreferencePage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage
{

	CFMLPreferenceManager cfmlpm;
	
	public ScribblePadPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("The preferences below allow you to manage the behaviour of the scribble pad.\n\n" +
		        "This current implementation is a first cut implementation to get some user feedback and see in what direction the feature should go.");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		addField(new BooleanFieldEditor(ScribblePadPreferenceConstants.P_SCRIBBLE_CLEAR_ON_LOAD, "Clear scribble pad every time you launch it", getFieldEditorParent()));
		addField(new BooleanFieldEditor(ScribblePadPreferenceConstants.P_SCRIBBLE_LOAD_BROWSER, "Automatically load and set the URL for the browser view.", getFieldEditorParent()));
		addField(new StringFieldEditor(ScribblePadPreferenceConstants.P_SCRIBBLE_PAD_FILE,"File name to use as scribble pad.",getFieldEditorParent()));
		addField(new StringFieldEditor(ScribblePadPreferenceConstants.P_SCRIBBLE_PROJECT_NAME,"Project to use for scribble pad.",getFieldEditorParent()));
		addField(new StringFieldEditor(ScribblePadPreferenceConstants.P_SCRIBBLE_URL,"URL to scribble file.",getFieldEditorParent()));
	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}