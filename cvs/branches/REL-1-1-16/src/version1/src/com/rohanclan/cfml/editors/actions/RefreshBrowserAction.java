/*
 * Created on Jun 20, 2004
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
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
//import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.views.browser.BrowserView;

/**
 * @author Rob
 *
 * Simple action to refresh the browser view
 */
public class RefreshBrowserAction implements IEditorActionDelegate {
	protected ITextEditor editor = null;
		
	public RefreshBrowserAction()
	{
		super();
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
		{
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run()
	{
		run(null);
	}
		
	public void run(IAction action) 
	{
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		

	    try {
	        
	        //IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
			//ISelection sel = editor.getSelectionProvider().getSelection();
	        
	        BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
	        browser.refresh();
	        
	        editor.setFocus();
	        
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }

	}

	public void selectionChanged(IAction action, ISelection selection){;}

}
