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
package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * @author Rob
 */
public class SQLColorsPreferencePage extends FieldEditorPreferencePage implements 
	IWorkbenchPreferencePage {
	
	public SQLColorsPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("On this page you can change the colour preferences for CFQuery blocks.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors()
	{

		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_SQL_TEXT,"Default &Text:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_SQL_COMMENT,"SQL &Comment:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_SQL_KEYWORD,"SQL &Keyword:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_SQL_OPERATOR,"SQL &Operator:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_SQL_STRING,"SQL St&ring:",getFieldEditorParent()));
		
	}
	 
	public void init(IWorkbench workbench){;}
}
