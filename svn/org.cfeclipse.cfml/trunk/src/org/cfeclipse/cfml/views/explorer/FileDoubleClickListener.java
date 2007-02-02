/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * @author Stephen Milligan
 *
 * This class performs the double click event on a selected item in the File Explorer View
 */
public class FileDoubleClickListener implements IDoubleClickListener {

    FileContentProvider contentProvider;
    /**
     * 
     */
    public FileDoubleClickListener(FileContentProvider contentProvider) {
       this.contentProvider = contentProvider;
    }

    
    public void doubleClick(DoubleClickEvent e) {
    	ISelection selection = e.getSelection();
    	IStructuredSelection ss = (IStructuredSelection) selection;
    	
    	Object[] element = (Object[])ss.getFirstElement();
    	
    	IEditorInput input = contentProvider.getEditorInput(element[0].toString());
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      
    //TODO: Fix this NPE 
     // Why is this null?
        try {
           page.openEditor(input, CFMLEditor.ID);
        }
        catch (Exception ex) {
        //System.out.println("Oooops!");
        	ex.printStackTrace();
        }
    }

}
