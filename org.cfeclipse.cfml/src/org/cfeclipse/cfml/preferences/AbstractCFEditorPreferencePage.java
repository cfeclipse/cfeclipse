/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cfeclipse.cfml.preferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ibm.icu.text.MessageFormat;

public abstract class AbstractCFEditorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private OverlayPreferenceStore fOverlayStore;
	protected List fStatusList;
	private boolean fInitialized = false;
	
	private Map fCheckBoxes = new HashMap();
	private SelectionListener fCheckBoxListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.widget;
			fOverlayStore.setValue((String) fCheckBoxes.get(button), button.getSelection());
		}
	};

	private Map fTextFields = new HashMap();
	private ModifyListener fTextFieldListener = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			if (fInitialized) {
				Text text = (Text) e.widget;
				fOverlayStore.setValue((String) fTextFields.get(text), text.getText());
			}
		}
	};

	private Map fNumberFields = new HashMap();
	private ModifyListener fNumberFieldListener = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			if (fInitialized) {
				numberFieldChanged((Text) e.widget);
			}
		}
	};
	private Map fRadioFields = new HashMap();
	private SelectionListener fRadioFieldListener = new SelectionListener() {

		public void widgetDefaultSelected(SelectionEvent e) {
			if (fInitialized) {
			}
		}

		public void widgetSelected(SelectionEvent e) {
			if (fInitialized) {
				Button button = (Button) e.widget;
				fOverlayStore.setValue((String) fRadioFields.get(button), button.getSelection());
			}

		}
	};

	public AbstractCFEditorPreferencePage() {
		super();
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		fOverlayStore = createOverlayStore();
	}

	protected abstract OverlayPreferenceStore createOverlayStore();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	protected void initializeFields() {
		Map checkBoxes = getCheckBoxes();
		Map textFields = getTextFields();
		Map radioFields = getRadioFields();
		Iterator e = checkBoxes.keySet().iterator();
		while (e.hasNext()) {
			Button b = (Button) e.next();
			String key = (String) checkBoxes.get(b);
			b.setSelection(getOverlayStore().getBoolean(key));
		}

		e = textFields.keySet().iterator();
		while (e.hasNext()) {
			Text t = (Text) e.next();
			String key = (String) textFields.get(t);
			t.setText(getOverlayStore().getString(key));
		}

		e = radioFields.keySet().iterator();
		while (e.hasNext()) {
			Button t = (Button) e.next();
			String key = (String) radioFields.get(t);
			t.setSelection(getOverlayStore().getBoolean(key));
		}

		fInitialized = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		getOverlayStore().propagate();
		CFMLPlugin.getDefault().savePluginPreferences();
		return true;
	}

	protected OverlayPreferenceStore getOverlayStore() {
		return fOverlayStore;
	}

	protected OverlayPreferenceStore setOverlayStore() {
		return fOverlayStore;
	}

	protected Map getCheckBoxes() {
		return fCheckBoxes;
	}

	protected Map getTextFields() {
		return fTextFields;
	}

	protected Map getNumberFields() {
		return fNumberFields;
	}

	protected Map getRadioFields() {
		return fRadioFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		getOverlayStore().loadDefaults();
		initializeFields();
		handleDefaults();
		super.performDefaults();
	}

	protected abstract void handleDefaults();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		if (getOverlayStore() != null) {
			getOverlayStore().stop();
			fOverlayStore = null;
		}
		super.dispose();
	}

	/**
	 * Convenience method to create a group
	 */
	protected Group createGroup(int numColumns, Composite parent, String text ) {
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
	
	protected void toggleGroup(Group group) {
		if(group.isVisible()) {
			setGroupVisible(group, false);
		} else {
			setGroupVisible(group, true);
		}
	}

	protected void setGroupVisible(Group group, boolean visible) {
		GridData gd= new GridData();
		if(visible) {
//			gd.heightHint=convertVerticalDLUsToPixels(3);
//			group.setLayoutData(gd);
			group.setVisible(true);
		} else {
			group.setVisible(false);
//			gd.heightHint=1;
//			group.setLayoutData(gd);
			//group.getParent().pack(true);
		}
	}

	protected Button addCheckBox(Composite parent, String labelText, String key, int indentation) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(labelText);
		checkBox.setFont(parent.getFont());

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = 2;
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(fCheckBoxListener);

		getCheckBoxes().put(checkBox, key);

		return checkBox;
	}

	protected Text addTextField(Composite composite, String labelText, String key, int textLimit, int indentation,
			String[] errorMessages) {
		Font font = composite.getFont();

		Label label = new Label(composite, SWT.NONE);
		label.setText(labelText);
		label.setFont(font);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		label.setLayoutData(gd);

		Text textControl = new Text(composite, SWT.BORDER | SWT.SINGLE);
		textControl.setFont(font);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = convertWidthInCharsToPixels(textLimit + 1);
		textControl.setLayoutData(gd);
		textControl.setTextLimit(textLimit);
		getTextFields().put(textControl, key);
		if (errorMessages != null) {
			getNumberFields().put(textControl, errorMessages);
			textControl.addModifyListener(fNumberFieldListener);
		} else {
			textControl.addModifyListener(fTextFieldListener);
		}

		return textControl;
	}

	protected Text addExpandableTextField(Composite composite, String labelText, String key, int length, int indentation,
			String[] errorMessages) {

		Label label = new Label(composite, SWT.NONE);
		label.setText(labelText);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		Text textControl = new Text(composite, SWT.BORDER | SWT.WRAP);
		GC gc = new GC(textControl);
		FontMetrics fm = gc.getFontMetrics();
		int width = length * fm.getAverageCharWidth();
		int height = fm.getHeight();
		gd.horizontalSpan = 2;
		gd.widthHint = width;
		gd.heightHint = height;
		gc.dispose();
		textControl.setLayoutData(gd);
		getTextFields().put(textControl, key);
		if (errorMessages != null) {
			getNumberFields().put(textControl, errorMessages);
			textControl.addModifyListener(fNumberFieldListener);
		} else {
			textControl.addModifyListener(fTextFieldListener);
		}

		return textControl;

	}

	protected Button addRadioButton(Composite parent, String labelText, String key, int indentation) {
		Button checkBox = new Button(parent, SWT.RADIO);
		checkBox.setText(labelText);
		checkBox.setFont(parent.getFont());

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = 2;
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(fRadioFieldListener);

		getRadioFields().put(checkBox, key);

		return checkBox;
	}

	protected Button addButton(Composite parent, String labelText, int indentation, int horizontalSpan) {
		Button checkBox = new Button(parent, SWT.PUSH);
		checkBox.setText(labelText);
		checkBox.setFont(parent.getFont());

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = horizontalSpan;
		checkBox.setLayoutData(gd);

		return checkBox;
	}

	private void numberFieldChanged(Text textControl) {
		String number = textControl.getText();
		IStatus status = validatePositiveNumber(number, (String[]) getNumberFields().get(textControl));
		if (!status.matches(IStatus.ERROR)) {
			getOverlayStore().setValue((String) getTextFields().get(textControl), number);
		}
		updateStatus(status);
	}

	private IStatus validatePositiveNumber(String number, String[] errorMessages) {
		StatusInfo status = new StatusInfo();
		if (number.length() == 0) {
			status.setError(errorMessages[0]);
		} else {
			try {
				int value = Integer.parseInt(number);
				if (value < 0)
					status.setError(MessageFormat.format(errorMessages[1], new String[] { number }));
			} catch (NumberFormatException e) {
				status.setError(MessageFormat.format(errorMessages[1], new String[] { number }));
			}
		}
		return status;
	}

	protected void updateStatus(IStatus status) {
		if (!status.matches(IStatus.ERROR)) {
			Set keys = getNumberFields().keySet();
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				Text text = (Text) iter.next();
				IStatus s = validatePositiveNumber(text.getText(), (String[]) getNumberFields().get(text));
				status = s.getSeverity() > status.getSeverity() ? s : status;
			}
		}

		List statusList = getStatusList();
		if (statusList != null) {
			List temp = new ArrayList(statusList.size() + 1);
			temp.add(status);
			temp.addAll(statusList);
			status = getMostSevere(temp);
		}
		setValid(!status.matches(IStatus.ERROR));
		applyToStatusLine(this, status);
	}

	protected List getStatusList() {
		return fStatusList;
	}

	/**
	 * Finds the most severe status from a array of stati. An error is more
	 * severe than a warning, and a warning is more severe than ok.
	 */
	private IStatus getMostSevere(List statusList) {
		IStatus max = null;
		for (int i = 0; i < statusList.size(); i++) {
			IStatus curr = (IStatus) statusList.get(i);
			if (curr.matches(IStatus.ERROR)) {
				return curr;
			}
			if (max == null || curr.getSeverity() > max.getSeverity()) {
				max = curr;
			}
		}
		return max;
	}

	/*
	 * Applies the status to the status line of a dialog page.
	 */
	private void applyToStatusLine(DialogPage page, IStatus status) {
		String message = status.getMessage();
		switch (status.getSeverity()) {
		case IStatus.OK:
			page.setMessage(message, IMessageProvider.NONE);
			page.setErrorMessage(null);
			break;
		case IStatus.WARNING:
			page.setMessage(message, IMessageProvider.WARNING);
			page.setErrorMessage(null);
			break;
		case IStatus.INFO:
			page.setMessage(message, IMessageProvider.INFORMATION);
			page.setErrorMessage(null);
			break;
		default:
			if (message.length() == 0) {
				message = null;
			}
			page.setMessage(null);
			page.setErrorMessage(message);
			break;
		}
	}

	/**
	 * Returns an array of size 2: - first element is of type <code>Label</code>
	 * - second element is of type <code>Text</code> Use
	 * <code>getLabelControl</code> and <code>getTextControl</code> to get the 2
	 * controls.
	 */
	protected Control[] addLabelledTextField(Composite composite, String label, String key, int textLimit,
			int indentation, String[] errorMessages) {
		Label labelControl = new Label(composite, SWT.NONE);
		labelControl.setText(label);
		labelControl.setFont(composite.getFont());
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		labelControl.setLayoutData(gd);

		Text textControl = new Text(composite, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		if (textLimit > -1) {
			gd.widthHint = convertWidthInCharsToPixels(textLimit + 1);
			textControl.setTextLimit(textLimit);
		} else {
			gd.widthHint = convertWidthInCharsToPixels(50);
		}
		textControl.setLayoutData(gd);
		textControl.setFont(composite.getFont());
		fTextFields.put(textControl, key);
		if (errorMessages != null) {
			fNumberFields.put(textControl, errorMessages);
			textControl.addModifyListener(fNumberFieldListener);
		} else {
			textControl.addModifyListener(fTextFieldListener);
		}

		return new Control[] { labelControl, textControl };
	}

	protected String loadPreviewContentFromFile(String filename) {
		String line;
		String separator = System.getProperty("line.separator"); //$NON-NLS-1$
		StringBuffer buffer = new StringBuffer(512);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append(separator);
			}
		} catch (IOException io) {
			io.printStackTrace();
			// CFMLPlugin.log(io);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return buffer.toString();
	}

	protected Label getLabelControl(Control[] labelledTextField) {
		return (Label) labelledTextField[0];
	}

	protected Text getTextControl(Control[] labelledTextField) {
		return (Text) labelledTextField[1];
	}
}
