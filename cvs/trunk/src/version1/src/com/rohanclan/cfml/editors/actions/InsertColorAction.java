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
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Rob
 *
 * Inserts a color
 */
public class InsertColorAction extends WordManipulator implements IEditorActionDelegate {
	
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
		if(editor != null)
		{
			//get the document and selection and pass it to the word manipulator
			//so it can extract and rewrite what we want (super class)
			IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
			ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
			this.setControler(doc,sel);
		}
	}
	
	/**
	 * override manipulate, just return the string to lower case
	 */
	public String manipulate(String highlighted)
	{
		//this could be a selected color
		String hexstr = highlighted;
		
		//Shell shell = this.editor.getSite().getShell();
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		if(shell != null)
		{
			ColorDialog colordialog = new ColorDialog(shell);
			
			colordialog.open();
						
			RGB selectedcolor = colordialog.getRGB();
			
			if(selectedcolor != null)
			{
				hexstr = Integer.toHexString(selectedcolor.red) 
					+ Integer.toHexString(selectedcolor.green) 
					+ Integer.toHexString(selectedcolor.blue);
			}
		}else{
			System.err.println("Shell is null for some stupid reason");
		}
		return hexstr;
	}
	
	public void selectionChanged(IAction action, ISelection selection) {;}
}
