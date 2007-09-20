/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.net.RemoteFileEditorInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

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
    	
    	String fileName = "";
    	if (element[0] instanceof RemoteFile) {
			RemoteFile remFile = (RemoteFile) element[0];
			fileName = remFile.getName();
			input = new RemoteFileEditorInput(remFile);
		}
    	
    	
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      
    //TODO: Fix this NPE 
     // Why is this null?
       //TODO: Open this up with the RIGHT editor...
        
      
		//Set the path which is project - path;
		 IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fileName);
		 System.out.println("Would normally open " + fileName + " with... " + desc);
		 //from http://wiki.eclipse.org/index.php/FAQ_How_do_I_open_an_editor_on_a_file_in_the_workspace%3F
		 //to open an editor at a marker/line number
		 //IDE.openEditor(page, marker);
		 
			try {
		           page.openEditor(input, CFMLEditor.ID);
		        }
		        catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		
        
        
        
        
        
    }

}
