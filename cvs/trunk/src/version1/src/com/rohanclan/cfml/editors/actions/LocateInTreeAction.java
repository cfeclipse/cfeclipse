/*
 * Created on Nov 17, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.editors.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.navigator.GotoResourceAction;
import org.eclipse.ui.views.navigator.ResourceNavigator;
import org.eclipse.ui.*;
import com.rohanclan.cfml.util.AlertUtils;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LocateInTreeAction implements IEditorActionDelegate {
	ITextEditor editor = null;
	/**
	 * 
	 */
	public LocateInTreeAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
		}	

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if(editor != null)
		{	
			try {
				IResource currentFile = (IResource) ((FileEditorInput)editor.getEditorInput()).getFile();
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				ResourceNavigator navigator = (ResourceNavigator)page.showView("org.eclipse.ui.views.ResourceNavigator");
				navigator.getViewer().setSelection(new StructuredSelection(currentFile),true);
			}
			catch(ClassCastException e) {
				AlertUtils.alertUser("The current file is not part of a project in the project tree.");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
