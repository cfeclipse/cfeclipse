/*
 * Created on Jun 17, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.ICFColorConstants;

/**
 * @author Rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ColorsPreferencePage extends FieldEditorPreferencePage implements 
	IWorkbenchPreferencePage, ICFMLPreferenceConstants {
	
	CFMLPreferenceManager cfmlpm;
	
	public ColorsPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("Color Preferences for CFML editor.");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors()
	{
		//addField(new BooleanFieldEditor(P_AUTO_OUTLINE, "&Automatic Outlining", getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_HTM_COMMENT_COLOR,"HTML &Comment:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_STRING_COLOR,"HTML &String:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_DEFAULT_TEXT_COLOR,"Default &Text:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_HTM_TAG_COLOR,"HTML &Tag:",getFieldEditorParent()));
		
		//addField(new ColorFieldEditor(P_HTM_FORM_TAG_COLOR,"&CFML Tag:",getFieldEditorParent()));
		//addField(new ColorFieldEditor(P_HTM_TABLE_TAG_COLOR,"&CFML Tag:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_CFTAG_COLOR,"CFML Tag:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CFSTRING_COLOR,"CFML St&ring:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CFKEYWORD_COLOR,"CFML &Keyword:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CFNUMBER_COLOR,"CFML &Number:",getFieldEditorParent()));
		
		
		addField(new ColorFieldEditor(P_CFSCRIPT_TEXT_COLOR,"CFScript Te&xt:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CFSCRIPT_KEYWORD_COLOR,"CFScript Key&word:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CFSCRIPT_FUNCTION_COLOR,"CFScript Funct&ion:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CFSCRIPT_STRING_COLOR,"CFScript Strin&g:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_JSCRIPT_TEXT_COLOR,"Javascript Text:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_JSCRIPT_FUNCTION_COLOR,"Javascript Function::",getFieldEditorParent()));
				
		addField(new ColorFieldEditor(P_CSS_COLOR,"CSS Text:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_CSS_TAG_COLOR,"CSS Tag:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_UNK_TAG_COLOR,"Other Tag:",getFieldEditorParent()));
		
		//addField(new ColorFieldEditor(P_STRING_COLOR, "&String Color:", getFieldEditorParent()));
		//addField(new ColorFieldEditor(P_KEYWORD_COLOR, "&Keyword Color:", getFieldEditorParent()));
		//addField(new ColorFieldEditor(P_DEFAULT_COLOR, "D&efault Color:", getFieldEditorParent()));
	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
        /* insightDelayField.setText(Integer.toString(preferenceManager.defaultInsightDelay()));
        tabWidthField.setText(Integer.toString(preferenceManager.defaultTabWidth()));
        tabsAsSpacesCheckBox.setSelection(preferenceManager.defaultSpacesForTabs());
        dreamweaverCompatibilityCheckBox.setSelection(preferenceManager.defaultDreamweaverCompatibility());
        homesiteCompatibilityCheckBox.setSelection(preferenceManager.defaultHomesiteCompatibility());
        snippetsPathField.setStringValue(preferenceManager.defaultSnippetsPath());
        */
    }
	
	/* public boolean performOk() 
	{
        IPreferenceStore store = getPreferenceStore();
        store.setValue(P_CFTAG_COLOR,P_CFTAG_COLOR);
        store.setValue(P_INSIGHT_DELAY, insightDelayField.getText());
        store.setValue(P_TAB_WIDTH, tabWidthField.getText());
        store.setValue(P_INSERT_SPACES_FOR_TABS, String.valueOf(tabsAsSpacesCheckBox.getSelection()));
        store.setValue(P_ENABLE_DW_COMPATIBILITY, String.valueOf(dreamweaverCompatibilityCheckBox.getSelection()));
        store.setValue(P_ENABLE_HS_COMPATIBILITY, String.valueOf(homesiteCompatibilityCheckBox.getSelection()));
        store.setValue(P_SNIPPETS_PATH, snippetsPathField.getStringValue());
        
        return true;
    } */
	 
	public void init(IWorkbench workbench){;}
}
