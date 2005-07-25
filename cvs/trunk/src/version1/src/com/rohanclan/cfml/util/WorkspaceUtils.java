/*
 * Created on Jul 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
//import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Oliver Tupman
 *
 * This is a workspace helper class. It provides a number of methods for
 * accessing various parts of the workspace because they can be convoluted
 * and bizarre :D Oh, and hard to find.
 */
public class WorkspaceUtils {

	/**
	 * 
	 */
	public WorkspaceUtils() {
		super();
	}

	/**
	 * Retrieves the document associated with a site (editor).
	 * 
	 * @param site - the site to obtain the document of
	 * @return The document belonging to the editor otherwise null.
	 */
	/* private IDocument getRootInput(IViewSite site) {

		
		try {
			IEditorPart iep = site.getWorkbenchWindow().getActivePage().getActiveEditor();
			ITextEditor ite = (ITextEditor)iep;
			IDocument icfd = ite.getDocumentProvider().getDocument(iep.getEditorInput());
			
			return icfd;
		}
		catch (Exception e) {
			return null;
		}
	}	*/
	
	/**
	 * Returns the project that the file open in the editor part belongs
	 * to.
	 * 
	 * @param iep the editor part to obtain the project from
	 * @return Eiether the project or null
	 */
	public static IProject getProject(IEditorPart iep) {
		try {
			ITextEditor ite = (ITextEditor)iep;
			IResource editorResource = ((FileEditorInput)ite.getEditorInput()).getFile();
			return editorResource.getProject();		
		} catch(Exception ex) {
			System.err.println("WorkspaceUtils::getProject(IEditorPart) - Caught exception");
			ex.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * Returns the project that the currently open file belongs to.
	 * 
	 * @param site - The site to obtain the project from.
	 * @return Either the project or null
	 */
	public static IProject getProject(IViewSite site) {
		try {
			IWorkbenchWindow benchSite = site.getWorkbenchWindow();
			IWorkbenchPage wrkPage = benchSite.getActivePage();
			if(wrkPage == null) {
				System.err.println("IWorkbenchpage is null!");
				return null;
			}
			
			return getProject(wrkPage.getActiveEditor());
		} catch(Exception ex) {
// System.out.println("WorkspaceUtils::getProject(IViewSite) - Caught exception.");
			ex.printStackTrace();
		}
		return null;
	}
	
}
