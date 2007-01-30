/*
 * Created on July 20, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.wizards.cfmlwizard.NewCFMLWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * @author Mark Drew
 *
 * This class is invoked to create a new file
 */
public class NewFileAction implements IEditorActionDelegate, IWorkbenchWindowActionDelegate{
    	
    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose() {
       

    }
    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init(IWorkbenchWindow window) {

    }
    	protected ITextEditor editor = null;
    	protected String filename = "untitled.cfm";
    	protected IFile file;
    	
    	public NewFileAction(){
    	    super();
    	    
    	}
    	
    	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
    	{
    		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
    		{
    			editor = (ITextEditor)targetEditor;
    		}
    	}
    	
    	public void run(IAction action) 
    	{
    	    NewCFMLWizard wizard = new NewCFMLWizard();
    	    wizard.setEditor(this.editor);
    	    WizardDialog dialog = new WizardDialog(editor.getSite().getShell(),wizard);
            dialog.open();
    	}
    	
    	public void selectionChanged(IAction action, ISelection selection){}
    
    
}
