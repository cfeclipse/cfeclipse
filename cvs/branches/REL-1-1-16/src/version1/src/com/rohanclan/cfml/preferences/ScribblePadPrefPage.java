package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.*;
/* import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
*/
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.rohanclan.cfml.CFMLPlugin;
//import org.eclipse.jface.preference.IPreferenceStore;

/**
 * THIS CLASS IS MEANT TO BE FOR THE AUTO-INSERTION/COMPLETION
 * For some reason Eclipse decides to put the wrong pref page under the wrong
 * title. This is a quick fix for the next release. I'll fix it sometime soon..
 * 
 * @author Oliver Tupman
 *
 */
public class ScribblePadPrefPage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage, ICFMLPreferenceConstants 
{

	CFMLPreferenceManager cfmlpm;
	
	public ScribblePadPrefPage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("The preferences below allow you to manage the behaviour of the scribble pad.\n\n" +
		        "This current implementation is a first cut implementation to get some user feedback and see in what direction the feature should go.");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		addField(new BooleanFieldEditor(P_SCRIBBLE_CLEAR_ON_LOAD, "Clear scribble pad every time you launch it", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_SCRIBBLE_LOAD_BROWSER, "Automatically load and set the URL for the browser view.", getFieldEditorParent()));
		addField(new StringFieldEditor(P_SCRIBBLE_PAD_FILE,"File name to use as scribble pad.",getFieldEditorParent()));
		addField(new StringFieldEditor(P_SCRIBBLE_PROJECT_NAME,"Project to use for scribble pad.",getFieldEditorParent()));
		addField(new StringFieldEditor(P_SCRIBBLE_URL,"URL to scribble file.",getFieldEditorParent()));
	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}