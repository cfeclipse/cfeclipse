/*
 * Created on Feb 18, 2004
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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
//import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.jface.action.IAction;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.CFMLEditor;

/**
 * @author Rob
 *
 * This is the base class for actions that enclose stuff. Like auto adding ##
 * or quotes etc - it is also used for what other products call snippets 
 */
public class GenericEncloserAction extends Encloser implements IEditorActionDelegate {
	
	protected ITextEditor editor = null;
	protected String start = "";
	protected String end = "";
	
	public GenericEncloserAction()
	{
		super();
	}
	
	public GenericEncloserAction(String start, String end)
	{
		super();
		setEnclosingStrings(start,end);
	}
	
	public void setEnclosingStrings(String start, String end)
	{
		this.start = start;
		this.end = end;	
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		//System.err.println(targetEditor);
		//System.out.println( "Changin (" + start + ")(" + end + ")" );
		if( targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor ){
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run()
	{
		run(null);
	}
		
	public void run(IAction action) 
	{
		
		
		if(editor != null  && editor.isEditable())
		{
			/*
			 * to fix the fact that you can run this function on readonly files, we are going to check the document here
			 * The resutlts of editor.isEditable() dont seem to tally up with the method definition
			 * System.out.println("you may edit this? But I shouldnt be able to save you: " + editor.isEditable());
			 */
			
			
			
			IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
			ISelection sel = editor.getSelectionProvider().getSelection();
			this.enclose(doc,(ITextSelection)sel,start,end);
			
			//move the cursor to before the end of the new insert
			int offset = ((ITextSelection)sel).getOffset();
			offset += ((ITextSelection)sel).getLength();
			offset += start.length();
			editor.setHighlightRange(offset,0,true);

			// Tell the plugin's Last Encloser Manager that this was the last one used for this editor
			CFMLPlugin.getDefault().getLastActionManager().setLastAction(editor, this);
			
		}
	}

	public void selectionChanged(IAction action, ISelection selection){;}
}
