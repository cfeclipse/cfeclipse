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

//import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
//import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchPreferencePage;
//import org.eclipse.jface.preference.*;
//import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.rohanclan.cfml.CFMLPlugin;
//import com.rohanclan.cfml.editors.ICFColorConstants;

/**
 * @author Rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HTMLColorsPreferencePage extends FieldEditorPreferencePage implements 
	IWorkbenchPreferencePage, ICFMLPreferenceConstants {
		
	public HTMLColorsPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("On this page you can change the colour preferences for HTML tags.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors()
	{
		addField(new ColorFieldEditor(P_COLOR_HTM_COMMENT,"HTML &Comment:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_COLOR_STRING,"HTML &String:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_COLOR_HTM_TAG,"HTML &Tags:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_COLOR_HTM_FORM_TAG,"&Form Tags:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_COLOR_HTM_TABLE_TAG,"T&able Tags:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_COLOR_JSCRIPT_TEXT,"&Javascript Tag:",getFieldEditorParent()));
		addField(new ColorFieldEditor(P_COLOR_JSCRIPT_FUNCTION,"Ja&vascript Function:",getFieldEditorParent()));
				
		addField(new ColorFieldEditor(P_COLOR_CSS,"CSS Te&xt:",getFieldEditorParent()));
		//addField(new ColorFieldEditor(P_COLOR_CSS_TAG,"CSS Val&ue:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(P_COLOR_UNK_TAG,"&Other Tags:",getFieldEditorParent()));
	}
	
	
	 
	public void init(IWorkbench workbench){;}
}
