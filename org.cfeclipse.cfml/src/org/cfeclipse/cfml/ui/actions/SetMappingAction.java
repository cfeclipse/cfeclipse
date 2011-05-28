package org.cfeclipse.cfml.ui.actions;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.dialogs.SetMappingDialog;
import org.cfeclipse.cfml.mappings.MappingManager;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class SetMappingAction implements IObjectActionDelegate {

	private IResource resource;
	private Shell shell;
	private Log logger = LogFactory.getLog(SetMappingAction.class);

	/**
	 *
	 */
	public SetMappingAction() {
		super();
	}

	/**
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		// Create a mapping dialog
		SetMappingDialog dialog = new SetMappingDialog(this.shell, this.resource);

		if(dialog.open() == IDialogConstants.OK_ID) {
			logger.trace("Setting mapping (" + dialog.getMappingName() + ") on resource (" + resource.getName() + ")");
			MappingManager.setMapping(this.resource, dialog.getMappingName());
			logger.trace("Mapping for " + resource.getName() + ": " + MappingManager.getMapping(resource));
		}
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		 Iterator selectionIter = ((StructuredSelection)selection).iterator();
		 while(selectionIter.hasNext()){
			 this.resource = (IResource)selectionIter.next();
		 }
	}

}
