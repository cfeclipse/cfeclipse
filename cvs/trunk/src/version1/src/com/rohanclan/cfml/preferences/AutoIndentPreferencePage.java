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
public class AutoIndentPreferencePage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage 
{

	CFMLPreferenceManager cfmlpm;
	
	public AutoIndentPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("Here you can manage the behaviour of the auto-insertion of characters such as double quotes, as well as the auto-closing of tags");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		try {
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_DOUBLE_QUOTES, "Auto close \"s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_SINGLE_QUOTES, "Auto close \'s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_HASHES, "Auto close #s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOCLOSE_TAGS, "Auto close tags (i.e. insert a closing \'>\')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOINSERT_CLOSE_TAGS, "Auto insert a closing tag", getFieldEditorParent()));
		addField(new BooleanFieldEditor(AutoIndentPreferenceConstants.P_AUTOINDENT_ONTAGCLOSE, "Auto-indent on tag close", getFieldEditorParent()));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}