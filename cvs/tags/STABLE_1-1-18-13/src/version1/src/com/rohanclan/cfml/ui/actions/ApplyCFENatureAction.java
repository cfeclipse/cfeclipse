package com.rohanclan.cfml.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.rohanclan.cfml.natures.CFENature;

public class ApplyCFENatureAction implements IObjectActionDelegate {
    //private IProject lastSelection = null;
    private ArrayList selections;
    
	/**
	 * Constructor for Action1.
	 */
	public ApplyCFENatureAction() {
		super();
		this.selections = new ArrayList();	
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	
	
	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
	    try {
	        Iterator prjIter = this.selections.iterator();
	        while(prjIter.hasNext())
	        {
	            IProject currPrj = (IProject)prjIter.next();
	           
	            if(!CFENature.hasCFENature(currPrj))
	                CFENature.applyNature(currPrj);
	            else
	                CFENature.removeNature(currPrj);
	           
	        }
	    }
	    catch(CoreException ex) 
	    {
	        showMessage("CFEclipse", "The following error occured when trying to apply the CFENature: \'" + ex.getMessage() + "\'");
	    	System.err.println("ApplyCFENatureAction::ru() Failed to apply CFENature to project.");
	    	ex.printStackTrace();
	    }
	}

	private void showMessage(String caption, String message)
	{
    	MessageDialog.openInformation(
    			new Shell(),
    			caption,
    			message);	    
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	    if(selection.isEmpty())
	        return;
	    
	    if(!(selection instanceof StructuredSelection))
	    {
	        return;
	    }
	    
	    Iterator selectionIter = ((StructuredSelection)selection).iterator();
	    
	    boolean projectWithoutNature = false;
	    
	    this.selections.clear();
	    try {
		    while(selectionIter.hasNext())
		    {
		        Object obj = selectionIter.next();
		        if(obj instanceof IProject)
		        {
		            this.selections.add(obj);
		            
		            if(!CFENature.hasCFENature((IProject)obj))
		                projectWithoutNature = true;
		        }
		    }
	    }
	    catch(CoreException ex) 
	    {
	    	System.err.println("ApplyCFENatureAction::selectionChanged() Failed to check for nature. User not told.");
	    	ex.printStackTrace();
	    }
	    boolean haveAProject = this.selections.size() > 0;
	    
	    if(!haveAProject)
	    {
	        action.setEnabled(false);
	        return;
	    }
	    
	    if(projectWithoutNature)
	        action.setText("Associate CFE nature");
	    else
	        action.setText("Disassociate CFE nature");
	}

}
