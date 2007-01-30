/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.cfeclipse.cfml.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IIdentifier;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * Standard workbench wizard that creates a new project resource in the
 * workspace.
 * <p>
 * This class may be instantiated and used without further configuration; this
 * class is not intended to be subclassed.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>
 * IWorkbenchWizard wizard = new BasicNewProjectResourceWizard();
 * wizard.init(workbench, selection);
 * WizardDialog dialog = new WizardDialog(shell, wizard);
 * dialog.open();
 * </pre>
 * 
 * During the call to <code>open</code>, the wizard dialog is presented to
 * the user. When the user hits Finish, a project resource with the
 * user-specified name is created, the dialog closes, and the call to
 * <code>open</code> returns.
 * </p>
 */
public class NewCFEclipseProject extends BasicNewResourceWizard	implements IExecutableExtension {
	
	private WizardNewProjectCreationPage mainPage;
	private WizardNewProjectReferencePage referencePage;

	// cache of newly-created project
	private IProject newProject;

	/**
	 * The config element which declares this wizard.
	 */
	private IConfigurationElement configElement;

	private static String WINDOW_PROBLEMS_TITLE = ResourceMessages.getString("NewProject.errorOpeningWindow");

	/**
	 * Extension attribute name for final perspective.
	 */
	private static final String FINAL_PERSPECTIVE = "finalPerspective";

	/**
	 * Extension attribute name for preferred perspectives.
	 */
	private static final String PREFERRED_PERSPECTIVES = "preferredPerspectives";

	/**
	 * Creates a wizard for creating a new project resource in the workspace.
	 */
	public NewCFEclipseProject() {
		IDialogSettings workbenchSettings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("NewCFEclipseProject");
		if (section == null)
			section = workbenchSettings.addNewSection("NewCFEclipseProject");
		
		setDialogSettings(section);
	}
	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	public void addPages() {
		super.addPages();

		mainPage = new WizardNewProjectCreationPage("basicNewProjectPage");
		mainPage.setTitle(ResourceMessages.getString("NewProject.title"));
		mainPage.setDescription(ResourceMessages.getString("NewProject.description"));
		this.addPage(mainPage);

		// only add page if there are already projects in the workspace
		if (ResourcesPlugin.getWorkspace().getRoot().getProjects().length > 0) {
			referencePage = new WizardNewProjectReferencePage("basicReferenceProjectPage");
			referencePage.setTitle(ResourceMessages.getString("NewProject.referenceTitle"));
			referencePage.setDescription(ResourceMessages.getString("NewProject.referenceDescription"));
			this.addPage(referencePage);
		}
		
		
	}
	/**
	 * Creates a new project resource with the selected name.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish
	 * on the wizard; the enablement of the Finish button implies that all
	 * controls on the pages currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this wizard caches the new project once it has been
	 * successfully created; subsequent invocations of this method will answer
	 * the same project resource without attempting to create it again.
	 * </p>
	 * 
	 * @return the created project resource, or <code>null</code> if the
	 *         project was not created
	 */
	private IProject createNewProject() {
		if (newProject != null)
			return newProject;

		// get a project handle
		final IProject newProjectHandle = mainPage.getProjectHandle();

		// get a project descriptor
		IPath newPath = null;
		if (!mainPage.useDefaults())
			newPath = mainPage.getLocationPath();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
		description.setLocation(newPath);

		// update the referenced project if provided
		if (referencePage != null) {
			IProject[] refProjects = referencePage.getReferencedProjects();
			if (refProjects.length > 0)
				description.setReferencedProjects(refProjects);
		}

		// create the new project operation
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor)
					throws CoreException {
				createProject(description, newProjectHandle, monitor);
			}
		};

		// run the new project creation operation
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			// ie.- one of the steps resulted in a core exception
			Throwable t = e.getTargetException();
			if (t instanceof CoreException) {
				if (((CoreException) t).getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
					MessageDialog.openError(
						getShell(),
						ResourceMessages.getString("NewProject.errorMessage"),
						ResourceMessages.format(
							"NewProject.caseVariantExistsError", new String[]{newProjectHandle.getName()}
						)
					);
				} else {
					ErrorDialog.openError(
						getShell(), ResourceMessages.getString("NewProject.errorMessage"),
						null,
						((CoreException) t).getStatus()
					);
				}
			} else {
				// CoreExceptions are handled above, but unexpected runtime
				// exceptions and errors may still occur.
				IDEWorkbenchPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR,	IDEWorkbenchPlugin.IDE_WORKBENCH, 0, t.toString(), t)
				);
				MessageDialog.openError(
					getShell(),
					ResourceMessages.getString("NewProject.errorMessage"),
					ResourceMessages.format(
						"NewProject.internalError", new Object[]{t.getMessage()}
					)
				);
			}
			return null;
		}

		newProject = newProjectHandle;

		return newProject;
	}
	/**
	 * Creates a project resource given the project handle and description.
	 * 
	 * @param description the project description to create a project resource for
	 * @param projectHandle the project handle to create a project resource for
	 * @param monitor the progress monitor to show visual progress with
	 * @exception CoreException if the operation fails
	 * @exception OperationCanceledException if the operation is canceled
	 */
	protected void createProject(IProjectDescription description, IProject projectHandle, IProgressMonitor monitor) 
		throws CoreException, OperationCanceledException {
		
		try {
			monitor.beginTask("", 2000);
			projectHandle.create(description, new SubProgressMonitor(monitor,1000));

			if (monitor.isCanceled())
				throw new OperationCanceledException();

			projectHandle.open(new SubProgressMonitor(monitor, 1000));

		} finally {
			monitor.done();
		}
		
		setProjectNature(description, projectHandle);
	}
	/**
     * @param description
     * @param projectHandle
     */
    public void setProjectNature(IProjectDescription description, IProject projectHandle) {
        /**
		 * Set the nature of this project when we look at it
		 **/
        	try {
        		System.out.println("Call of nature");
        		IProjectDescription pdesc = projectHandle.getDescription();
        		String[] natures = pdesc.getNatureIds();
		        //System.out.println("natids: "+natures.);
		     /*   
		        IProjectDescription Pdesc = projectHandle.getDescription();
		        String[] natures = Pdesc.getNatureIds();
		        String[] newNatures = new String[natures.length + 1];
		        System.arraycopy(natures, 0, newNatures, 0, natures.length);
		        newNatures[natures.length] = "org.cfeclipse.cfml.natures.CFENature";
		        description.setNatureIds(newNatures);
		        
		        projectHandle.setDescription(description, null);*/
		
        	} catch (CoreException e) {
        		// Something went wrong
		    System.out.println("Call to project " + "nature failed because: " + e.getMessage());
        	}
    }
    
    /**
	 * Returns the newly created project.
	 * 
	 * @return the created project, or <code>null</code> if project not created
	 */
	public IProject getNewProject() {
		return newProject;
	}
	
	/*
	 * (non-Javadoc) Method declared on IWorkbenchWizard.
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		setNeedsProgressMonitor(true);
		setWindowTitle(ResourceMessages.getString("NewProject.windowTitle"));
	}
	
	/*
	 * (non-Javadoc) Method declared on BasicNewResourceWizard.
	 */
	protected void initializeDefaultPageImageDescriptor() {
		String iconPath = "icons/full/";		
		try {
			URL installURL = IDEWorkbenchPlugin.getDefault().getDescriptor().getInstallURL();
			URL url = new URL(installURL, iconPath + "wizban/newprj_wiz.gif");
			ImageDescriptor desc = ImageDescriptor.createFromURL(url);
			setDefaultPageImageDescriptor(desc);
		} catch (MalformedURLException e) {
			// Should not happen. Ignore.
		}
	}
	
	/*
	 * (non-Javadoc) Opens a new window with a particular perspective and input.
	 */
	private static void openInNewWindow(IPerspectiveDescriptor desc) {

		// Open the page.
		try {
			PlatformUI.getWorkbench().openWorkbenchWindow(
				desc.getId(),
				ResourcesPlugin.getWorkspace().getRoot()
			);
		} catch (WorkbenchException e) {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			
			if (window != null) {
				ErrorDialog.openError(
					window.getShell(), WINDOW_PROBLEMS_TITLE,	e.getMessage(), e.getStatus()
				);
			}
		}
	}
	
	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	public boolean performFinish() {
		createNewProject();

		if (newProject == null)
			return false;

		updatePerspective();
		selectAndReveal(newProject);

		return true;
	}
	
	/*
	 * (non-Javadoc) Replaces the current perspective with the new one.
	 */
	private static void replaceCurrentPerspective(IPerspectiveDescriptor persp) {

		//Get the active page.
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return;
		
		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			return;

		// Set the perspective.
		page.setPerspective(persp);
	}
	
	/**
	 * Stores the configuration element for the wizard. The config element will
	 * be used in <code>performFinish</code> to set the result perspective.
	 */
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
		configElement = cfig;
	}
	
	/**
	 * Updates the perspective for the active page within the window.
	 */
	protected void updatePerspective() {
		updatePerspective(configElement);
	}
	
	/**
	 * Updates the perspective based on the current settings in the
	 * Workbench/Perspectives preference page.
	 * 
	 * Use the setting for the new perspective opening if we
	 * are set to open in a new perspective.
	 * <p>
	 * A new project wizard class will need to implement the
	 * <code>IExecutableExtension</code> interface so as to gain access to the
	 * wizard's <code>IConfigurationElement</code>. That is the configuration
	 * element to pass into this method.
	 * </p>
	 * @param configElement - the element we are updating with
	 * 
	 * @see IPreferenceConstants#OPM_NEW_WINDOW
	 * @see IPreferenceConstants#OPM_ACTIVE_PAGE
	 * @see IWorkbenchPreferenceConstants#NO_NEW_PERSPECTIVE
	 */
	public static void updatePerspective(IConfigurationElement configElement) {
		// Do not change perspective if the configuration element is
		// not specified.
		if (configElement == null)
			return;

		// Retrieve the new project open perspective preference setting
		String perspSetting = PrefUtil.getAPIPreferenceStore().getString(
			IDE.Preferences.PROJECT_OPEN_NEW_PERSPECTIVE
		);

		String promptSetting = IDEWorkbenchPlugin.getDefault().getPreferenceStore().getString(
			IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE
		);

		// Return if do not switch perspective setting and are not prompting
		if (!(promptSetting.equals(MessageDialogWithToggle.PROMPT))
			&& perspSetting.equals(IWorkbenchPreferenceConstants.NO_NEW_PERSPECTIVE))
			return;

		// Read the requested perspective id to be opened.
		String finalPerspId = configElement.getAttribute(FINAL_PERSPECTIVE);
		if (finalPerspId == null)
			return;

		// Map perspective id to descriptor.
		IPerspectiveRegistry reg = PlatformUI.getWorkbench().getPerspectiveRegistry();

		// leave this code in - the perspective of a given project may map to
		// activities other than those that the wizard itself maps to.
		IPerspectiveDescriptor finalPersp = reg.findPerspectiveWithId(finalPerspId);
		
		if (finalPersp != null && finalPersp instanceof IPluginContribution) {
			IPluginContribution contribution = (IPluginContribution) finalPersp;
			
			if (contribution.getPluginId() != null) {
				IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI.getWorkbench().getActivitySupport();
				IActivityManager activityManager = workbenchActivitySupport	.getActivityManager();
				IIdentifier identifier = activityManager.getIdentifier(
					WorkbenchActivityHelper.createUnifiedId(contribution)
				);
				
				Set idActivities = identifier.getActivityIds();

				if (!idActivities.isEmpty()) {
					Set enabledIds = new HashSet(activityManager.getEnabledActivityIds());

					if (enabledIds.addAll(idActivities))
						workbenchActivitySupport.setEnabledActivityIds(enabledIds);
				}
			}
		} else {
			IDEWorkbenchPlugin.log("Unable to find persective " 
					+ finalPerspId
					+ " in BasicNewProjectResourceWizard.updatePerspective");
			return;
		}

		// gather the preferred perspectives
		// always consider the final perspective (and those derived from it)
		// to be preferred
		ArrayList preferredPerspIds = new ArrayList();
		addPerspectiveAndDescendants(preferredPerspIds, finalPerspId);
		String preferred = configElement.getAttribute(PREFERRED_PERSPECTIVES);
		if (preferred != null) {
			StringTokenizer tok = new StringTokenizer(preferred, " \t\n\r\f,");
			while (tok.hasMoreTokens()) {
				addPerspectiveAndDescendants(preferredPerspIds, tok.nextToken());
			}
		}

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IPerspectiveDescriptor currentPersp = page.getPerspective();

				// don't switch if the current perspective is a preferred
				// perspective
				if (currentPersp != null && preferredPerspIds.contains(currentPersp.getId())) {
					return;
				}
			}

			// prompt the user to switch
			if (!confirmPerspectiveSwitch(window, finalPersp)) {
				return;
			}
		}

		int workbenchPerspectiveSetting =	WorkbenchPlugin.getDefault().getPreferenceStore().getInt(
			IPreferenceConstants.OPEN_PERSP_MODE
		);
		
		// open perspective in new window setting
		if (workbenchPerspectiveSetting == IPreferenceConstants.OPM_NEW_WINDOW) {
			openInNewWindow(finalPersp);
			return;
		}

		// replace active perspective setting otherwise
		replaceCurrentPerspective(finalPersp);
	}
	
	/**
	 * Adds to the list all perspective IDs in the Workbench who's original ID
	 * matches the given ID.
	 * 
	 * @param perspectiveIds
	 *            the list of perspective IDs to supplement.
	 * @param id
	 *            the id to query.
	 * @since 3.0
	 */
	private static void addPerspectiveAndDescendants(List perspectiveIds, String id) {
		IPerspectiveRegistry registry = PlatformUI.getWorkbench().getPerspectiveRegistry();
		IPerspectiveDescriptor[] perspectives = registry.getPerspectives();
		
		for (int i = 0; i < perspectives.length; i++) {
			// @issue illegal ref to workbench internal class;
			// consider adding getOriginalId() as API on IPerspectiveDescriptor
			PerspectiveDescriptor descriptor = ((PerspectiveDescriptor) perspectives[i]);
			if (descriptor.getOriginalId().equals(id)) {
				perspectiveIds.add(descriptor.getId());
			}
		}
	}

	/**
	 * Prompts the user for whether to switch perspectives.
	 * 
	 * @param window
	 *            The workbench window in which to switch perspectives; must not
	 *            be <code>null</code>
	 * @param finalPersp
	 *            The perspective to switch to; must not be <code>null</code>.
	 * 
	 * @return <code>true</code> if it's OK to switch, <code>false</code>
	 *         otherwise
	 */
	private static boolean confirmPerspectiveSwitch(IWorkbenchWindow window, IPerspectiveDescriptor finalPersp) {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		String pspm = store.getString(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE);
		
		if (!IDEInternalPreferences.PSPM_PROMPT.equals(pspm)) {
			// Return whether or not we should always switch
			return IDEInternalPreferences.PSPM_ALWAYS.equals(pspm);
		}

		MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(
			window.getShell(), 
			ResourceMessages.getString("NewProject.perspSwitchTitle"),
			ResourceMessages.format("NewProject.perspSwitchMessage", new Object[]{finalPersp.getLabel()}),
			null /* use the default message for the toggle */,
			false /* toggle is initially unchecked */, store,
			IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE
		);
		
		int result = dialog.getReturnCode();

		//If we are not going to prompt anymore propogate the choice.
		if (dialog.getToggleState()) {
			String preferenceValue;
			if (result == IDialogConstants.YES_ID) 
				//Doesn't matter if it is replace or new window
				//as we are going to use the open perspective setting
				preferenceValue = IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE;
			else
				preferenceValue = IWorkbenchPreferenceConstants.NO_NEW_PERSPECTIVE;

			// update PROJECT_OPEN_NEW_PERSPECTIVE to correspond
			PrefUtil.getAPIPreferenceStore().setValue(
				IDE.Preferences.PROJECT_OPEN_NEW_PERSPECTIVE,
				preferenceValue
			);
		}
		
		return result == IDialogConstants.YES_ID;
	}
}
