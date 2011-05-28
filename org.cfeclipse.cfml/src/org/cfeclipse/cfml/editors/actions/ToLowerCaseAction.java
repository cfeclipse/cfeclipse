/*
 * Created on Feb 26, 2004
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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Rob
 *
 * This takes the highlighted text and turns it into lower case - uses the 
 * helper class WordManipulator
 */
public class ToLowerCaseAction extends WordManipulator implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	ITextEditor editor = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
		}
	}
	
	/**
	 * this gets called for every action
	 */
	public void run(IAction action) 
	{
		//get the document and selection and pass it to the word manipulator
		//so it can extract and rewrite what we want (super class)
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
		this.setControler(doc,sel);
	}
	
	/**
	 * override manipulate, just return the string to lower case
	 */
	public String manipulate(String highlighted)
	{
		if(editor != null && editor.isEditable()) {
		return highlighted.toLowerCase();
		}
		else {
			return highlighted;
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection){
		if(editor != null){
			setActiveEditor(null,  editor.getSite().getPage().getActiveEditor());
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if(activeEditor instanceof ITextEditor){
			editor = (ITextEditor)activeEditor;
		}
		
	}
}
