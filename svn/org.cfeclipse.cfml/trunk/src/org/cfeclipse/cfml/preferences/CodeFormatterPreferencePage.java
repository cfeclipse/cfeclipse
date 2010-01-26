/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.cfeclipse.cfml.preferences;

import java.util.ArrayList;
import java.util.List;

import org.cfeclipse.cfml.editors.CFConfiguration;
import org.cfeclipse.cfml.editors.CFDocumentSetupParticipant;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ColorManager;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.formatters.FormattingPreferences;
import org.cfeclipse.cfml.editors.formatters.CFMLFormatter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;

/*
 * The page to configure the code formatter options.
 */
public class CodeFormatterPreferencePage extends AbstractCFEditorPreferencePage {
	
	private SourceViewer fPreviewViewer;
	private CFPreviewerUpdater fPreviewerUpdater;
	private CFMLEditor fEditor;
	
	@SuppressWarnings("unchecked")
	protected OverlayPreferenceStore createOverlayStore() {
		List overlayKeys= new ArrayList();
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_WRAP_LONG));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_ALIGN));				
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, EditorPreferenceConstants.FORMATTER_MAX_LINE_LENGTH));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, EditorPreferenceConstants.P_TAB_WIDTH));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_CLOSE_TAGS));

		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_COLLAPSE_WHITESPACE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_INDENT_ALL_ELEMENTS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_TIDY_TAGS));

		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_CHANGE_TAG_CASE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_CHANGE_TAG_CASE_UPPER));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.FORMATTER_CHANGE_TAG_CASE_LOWER));
		
		OverlayPreferenceStore.OverlayKey[] keys= new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return new OverlayPreferenceStore(getPreferenceStore(), keys);
	}
	
	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		//TODO set help
		//WorkbenchHelp.setHelp(getControl(), "ANT_FORMATTER_PREFERENCE_PAGE");
	}

	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);
		getOverlayStore().load();
		getOverlayStore().start();
		int numColumns= 2;
		Composite result= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		result.setLayout(layout);
		
//		Group indentationGroup= createGroup(numColumns, result, CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_0);
//		
//		String labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_1;
//		String[] errorMessages= new String[]{CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_2, CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_3};
//		addTextField(indentationGroup, labelText, EditorPreferenceConstants.P_TAB_WIDTH, 3, 0, errorMessages);
//		
//		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_4;
//		addCheckBox(indentationGroup, labelText, EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS, 1);
		
		Group wrappingGroup= createGroup(numColumns, result, CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_6);
		String labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_7;
		String[] errorMessages= new String[]{CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_8, CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_9};
		addTextField(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_MAX_LINE_LENGTH, 3, 0, errorMessages);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_10;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_WRAP_LONG, 1);
//		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_5;
//		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_ALIGN, 1);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_11;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_CLOSE_TAGS, 1);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_12;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_FORMAT_SQL, 1);

		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_13;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_COLLAPSE_WHITESPACE, 1);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_14;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_INDENT_ALL_ELEMENTS, 1);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_15;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_TIDY_TAGS, 1);
		
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_16;
		addCheckBox(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_CHANGE_TAG_CASE, 1);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_17;
		addRadioButton(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_CHANGE_TAG_CASE_LOWER, 2);
		labelText= CFMLPreferencesMessages.CFMLCodeFormatterPreferencePage_18;
		addRadioButton(wrappingGroup, labelText, EditorPreferenceConstants.FORMATTER_CHANGE_TAG_CASE_UPPER, 2);
		
		Label label= new Label(result, SWT.LEFT);
		label.setText(CFMLPreferencesMessages.CFMLEditorPreferencePage_9);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Control previewer= createPreviewer(result);
		GridData gd= new GridData(GridData.FILL_BOTH);
		gd.widthHint= convertWidthInCharsToPixels(20);
		gd.heightHint= convertHeightInCharsToPixels(5);
		previewer.setLayoutData(gd);
		
		initializeFields();
		
		applyDialogFont(result);
	
		return result;
	}
	
	/**
	 * Convenience method to create a group
	 */
	private Group createGroup(int numColumns, Composite parent, String text ) {
		final Group group= new Group(parent, SWT.NONE);
		GridData gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan= numColumns;
		gd.widthHint= 0;
		group.setLayoutData(gd);
		group.setFont(parent.getFont());
		
		final GridLayout layout= new GridLayout(numColumns, false);
		group.setLayout(layout);
		group.setText(text);
		return group;
	}
	
	private Control createPreviewer(Composite parent) {
		fPreviewViewer = new SourceViewer(parent, null, null, false, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        fEditor = new CFMLEditor();
        ColorManager colorMan = new ColorManager();
		CFConfiguration configuration = new CFConfiguration(colorMan,fEditor);
	
		fPreviewViewer.configure(configuration);
		fPreviewViewer.setEditable(false);	
		Font font= JFaceResources.getFont(JFaceResources.TEXT_FONT);
		fPreviewViewer.getTextWidget().setFont(font);    
		
		IPreferenceStore store= new ChainedPreferenceStore(new IPreferenceStore[] { getOverlayStore(), EditorsUI.getPreferenceStore() });
		fPreviewerUpdater= new CFPreviewerUpdater(fPreviewViewer, configuration, store);
		
		String content= loadPreviewContentFromFile("FormatPreviewCode.txt"); //$NON-NLS-1$
		content= formatContent(content, store);
		//IDocument document = new Document(content);       
		ICFDocument document = new ICFDocument(content);
		new CFDocumentSetupParticipant().setup(document);
		fPreviewViewer.setDocument(document);
		
		return fPreviewViewer.getControl();
	}

	private String formatContent(String content, IPreferenceStore preferenceStore) {
		FormattingPreferences prefs= new FormattingPreferences();
		prefs.setPreferenceStore(preferenceStore);
		return CFMLFormatter.format(content, prefs);
	}

	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.preferences.AbstractAntEditorPreferencePage#handleDefaults()
	 */
	protected void handleDefaults() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		super.dispose();
		if (fPreviewerUpdater != null) {
			fPreviewerUpdater.dispose();
		}
	}
}