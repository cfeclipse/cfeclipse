/*
 * Created on Sep 19, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Matt Cristantello
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
 *
 */
package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;


/**
 * @author Matt 
 * Class to find the matching bracker for the selected/highlighted
 *         bracket.
 */
public class FindBracketAction implements IEditorActionDelegate
{
	private CFMLEditor editor;

	public FindBracketAction()
	{
		editor = null;
	}

	/*
	 * Sets the active editor, which appears to tell my action what to actually
	 * act upon.
	 * 
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor)
	{
		if (targetEditor instanceof CFMLEditor)
		{
			editor = (CFMLEditor) targetEditor;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action)
	{
//		get the document and selection and pass it to the word manipulator
		//so it can extract and rewrite what we want (super class)
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
		this.findBracket(doc,sel);
		
	}
	
	public void findBracket(IDocument doc, ITextSelection sel)
	{
		try
		{
			if(!sel.isEmpty() && !(sel.getLength()==0) )
			{
				int offset = sel.getOffset();
				int len  = sel.getLength();
				int iterator = 0;
				String searchingFor = "";
				
				
				//search forwards if it's a left bracket, backwards for right brackets
				if( doc.get(offset,len).equals("(") )
				{
					iterator = 1;
					searchingFor = ")";
				}
				else if( doc.get(offset,len).equals(")"))
				{
					iterator = -1;
					searchingFor = "(";
				}
				boolean found = false;
				//do a search in the appropriate direction for a matching bracket
				//TODO: need to implement a stack-based checking to ensure we get the
				//*right* bracket
				for( int bracketOffset = offset; !found; bracketOffset += iterator )
				{
					if( doc.get(bracketOffset,1).equals(searchingFor) )
					{
					
						//doc.replace(bracketOffset,1,"a"+doc.get(bracketOffset,1) );
						//editor.setHighlightRange(bracketOffset,1,false);
						editor.selectAndReveal(bracketOffset,1);
						
						//editor.se
						
						editor.selectAndReveal(offset,1);
						found = true;						
					}
				}
				
				
			}
		}
		catch(BadLocationException e)
		{
			e.printStackTrace(System.err);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
		// not sure anything needs to be done here
	}
}
