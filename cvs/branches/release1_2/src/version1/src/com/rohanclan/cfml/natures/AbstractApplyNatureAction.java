/*
 * Created on Nov 2, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.natures;

import java.util.ArrayList;
import java.util.Iterator;

//import org.eclipse.core.internal.utils.Assert;
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

import com.rohanclan.cfml.util.ResourceUtils;

/**
 * An abstract class for applying natures to a project.
 * 
 * Client classes should derive from this class an simply
 * implement a constructor that calls AbstractApplyNatureAction()
 * with the appropriate parameters. That will customise this
 * action for display within CFE.
 *
 * @author Oliver Tupman
 */
public abstract class AbstractApplyNatureAction   implements IObjectActionDelegate {

	/** The selections the user has made */
    private ArrayList selections;
    
    /** ID of the nature to apply to the project */
    private String natureID = null;
    
    /** The text to be displayed to the user when a project 
     * is not associated with the nature.
     */
    private String associateText = null;
    
    /** The text to be displayed to the user when a project 
     * is associated with the nature.
     */
    private String disassociateText = null;

    /**
     * Gets the text that will be displayed if the nature is not associated
     * with the selected project(s).
     * 
     * @return The association description.
     */
    public String getAssociateText() {
        return associateText;
    }
    /**
     * Sets the association text
     * 
     * @param associateText The text to display to the user if the project is not associated with the nature
     */
    public void setAssociateText(String associateText) {
        //Assert.isNotNull(associateText,"AbstractApplyNatureAction::setAssociateText()");
        if(associateText == null)
        		throw new IllegalArgumentException("AbstractApplyNatureAction::setAssociateText()");
        
        this.associateText = associateText;
    }
    
    /**
     * Gets the text to display to the user if the nature is not associated with the project
     * @return
     */
    public String getDisassociateText() {
        return disassociateText;
    }
    
    /**
     * Sets the text to display to the user if the nature is not associated with the project.
     *  
     * @param disassociateText Text to display if no association is present
     */
    public void setDisassociateText(String disassociateText) {
        //Assert.isNotNull(disassociateText,"AbstractApplyNatureAction::setDisassociateText()");
    	 	if(disassociateText == null)
     		throw new IllegalArgumentException("AbstractApplyNatureAction::setDisassociateText()");
        this.disassociateText = disassociateText;
    }
    
    /**
     * Gets the ID of the nature that will be applied by this action
     * 
     * @return The string-ID of the nature.
     */
    public String getNatureID()
    {
        return this.natureID;
    }
    
    /**
     * Sets the ID of the nature that this action will apply.
     * 
     * @param natureID The string-based ID of the nature to apply
     */
    public void setNatureID(String natureID)
    {
        //Assert.isNotNull(natureID, "Nature ID supplied to setNatureID() is null");
        if(natureID == null)
     		throw new IllegalArgumentException("Nature ID supplied to setNatureID() is null");
        this.natureID = natureID;
    }
    
	/**
	 * Constructs the nature action that will be presented to the user when they right-click
	 * upon a proejct.  
	 * 
	 * @param natureID The ID of the nature to apply
	 * @param associationText The text to display to the user if the project is not associated with this nature
	 * @param disassociationText The text to display to the user if the project is associated with this nature 
	 */
	public AbstractApplyNatureAction(String natureID, String associationText, String disassociationText) {
		super();
		this.selections = new ArrayList();
		setNatureID(natureID);
		setAssociateText(associationText);
		setDisassociateText(disassociationText);
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
	            if(!ResourceUtils.hasNature(currPrj, this.natureID))
	                ResourceUtils.applyNature(currPrj, this.natureID);
	            else
	                ResourceUtils.removeNature(currPrj, this.natureID);
	        }
	    }
	    catch(CoreException ex) 
	    {
	        showMessage("CFEclipse", "The following error occured when trying to apply the CFENature: \'" + ex.getMessage() + "\'");
	    	System.err.println("ApplyCFENatureAction::ru() Failed to apply CFENature to project.");
	    	ex.printStackTrace();
	    }
	}

	/**
	 * Message to display to the user.
	 * 
	 * @param caption Caption for the message box
	 * @param message Message to display
	 */
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
		            
		            if(!ResourceUtils.hasNature((IProject)obj, this.natureID))
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
	        action.setText(this.associateText);
	    else
	        action.setText(this.disassociateText);
	}


}
