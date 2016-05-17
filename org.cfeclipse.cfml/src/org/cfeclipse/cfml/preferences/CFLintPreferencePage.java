package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * This is the CFLint preference page
 * 
 * @author Oliver Tupman
 *
 */
public class CFLintPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	CFMLPreferenceManager cfmlpm;

	public CFLintPreferencePage() {
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("CFLint default options.\n\n" + "Configure default CFLint options for new projects");
		cfmlpm = new CFMLPreferenceManager();
	}

	public void createFieldEditors() {
		addField(new BooleanFieldEditor(CFLintPreferenceConstants.P_CFLINT_ENABLED,
				"Enable CFLint by default for new projects", getFieldEditorParent()));
		addField(new BooleanFieldEditor(CFLintPreferenceConstants.P_CFLINT_STOREINPROJECT,
				"Store CFLint config in project by default for new projects", getFieldEditorParent()));
		final Button button = new Button(getFieldEditorParent(), SWT.BORDER);
		button.setText("Open Project Specific Properties");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Shell shell = getShell();
				MessageDialog.openInformation(shell, "Project Proeperties", "Properties window will open next");
				String propertyPageId = "org.cfeclipse.cfml.properties.ProjectPropertyPage";
				try {
					PreferenceDialog dialog = PreferencesUtil.createPropertyDialogOn(shell, getSelectedProject(),
							propertyPageId, null, null);
					dialog.open();
				} catch (Exception ex) {
					MessageDialog.openInformation(shell, "Couldn't",
							"Could not determine active project, you're gonna have to laborously context-click on the project manually and then select the properties menu option");
				}
			}
		});

	}

	protected IProject getSelectedProject() {
		ISelectionService ss = CFMLPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection sel = ss.getSelection();
		IProject project = null;
		Object selectedObject = sel;
		if (sel instanceof IStructuredSelection) {
			selectedObject = ((IStructuredSelection) sel).getFirstElement();
		}
		if (selectedObject instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) selectedObject).getAdapter(IResource.class);
			project = res.getProject();
		}
		if (project == null) {
			IResource res = org.eclipse.ui.ide.ResourceUtil.getResource(CFMLPlugin.getDefault().getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput());
			project = res.getProject();
		}
		return project;
	}

	protected void performDefaults() {
		super.performDefaults();
	}

	public void init(IWorkbench workbench) {
		;
	}
}