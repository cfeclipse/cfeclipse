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
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;

import com.rohanclan.cfml.views.explorer.FileExplorerView;
import com.rohanclan.cfml.util.AlertUtils;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LocateInFileSystemAction implements IEditorActionDelegate {
	ITextEditor editor = null;
	/**
	 * 
	 */
	public LocateInFileSystemAction() {
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
			String filePath;
			try {
				if (editor.getEditorInput() instanceof JavaFileEditorInput) {
					filePath = ((JavaFileEditorInput)editor.getEditorInput()).getPath(editor.getEditorInput()).toString();
				}
				else {
					filePath = ((FileEditorInput)editor.getEditorInput()).getFile().getRawLocation().toString();
				}
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				FileExplorerView explorer = (FileExplorerView)page.showView(FileExplorerView.ID_FILE_EXPLORER);
				explorer.showFile(filePath);
			}
			catch(ClassCastException e) {
				AlertUtils.alertUser("This action does not currently work with remote files.");
			}
			catch(Exception e) {
				System.out.println(editor.getEditorInput().getClass().getName());
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
