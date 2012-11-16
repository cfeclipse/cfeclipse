package org.cfeclipse.cfeclipsecall.plugin.preferences;

import java.util.ArrayList;
import java.util.List;

import org.cfeclipse.cfeclipsecall.core.IPreferencesChangeListener;
import org.cfeclipse.cfeclipsecall.plugin.BrowserLauncher;
import org.cfeclipse.cfeclipsecall.plugin.CFECallPlugin;
import org.cfeclipse.cfeclipsecall.plugin.Hyperlink;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class EclipseCallPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Link fCompliance;
	private Hyperlink fHyperlink;

	public EclipseCallPreferencePage() {
		super(GRID);
		setPreferenceStore(CFECallPlugin.getDefault().getPreferenceStore());
		setDescription("Settings for the EclipseCall Plugin");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new IntegerFieldEditor(PreferenceConstants.P_PORT, "&Port number:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_FOCUS, "&Focus window after opening",
				getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	protected Control createContents(Composite ancestor) {
		super.createContents(ancestor);
		fHyperlink = new Hyperlink(ancestor,SWT.BOLD);
		fHyperlink.setText("Get the cfeclipsecall application");
		fHyperlink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fHyperlink.setVisible(true);
		fHyperlink.setForeground(ancestor.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		fHyperlink.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				BrowserLauncher.openURL("http://trac.cfeclipse.org/wiki/CFEclipseCall");
			}
		});
		return ancestor;

	}

	private boolean checkSettings() {
		return true;
	}

	public boolean performOk() {
		boolean ret = super.performOk();
		if (ret) {
			for (int i = 0; i < preferenceChangeListeners.size(); i++) {
				IPreferencesChangeListener listener = (IPreferencesChangeListener) preferenceChangeListeners.get(i);
				listener.preferencesChanged();
			}
		}
		return ret;
	}

	protected static List preferenceChangeListeners = new ArrayList();

	public static void addPreferencesChangeListener(IPreferencesChangeListener listener) {
		preferenceChangeListeners.add(listener);
	}

	protected void performApply() {
		super.performApply();
		checkSettings();
	}
}