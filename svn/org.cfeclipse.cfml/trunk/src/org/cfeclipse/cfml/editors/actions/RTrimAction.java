/*
 * Created on Oct 15, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.editors.codefolding.CodeFoldingSetter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * @author Stephen Milligan
 */
public class RTrimAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	ITextEditor editor = null;

	CodeFoldingSetter foldingSetter = null;

	/**
	 *  
	 */
	public RTrimAction() {
		super();
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {

		if (targetEditor instanceof ITextEditor) {
			editor = (ITextEditor) targetEditor;
			foldingSetter = new CodeFoldingSetter(editor);
		}
	}

	/**
	 * this gets called for every action
	 */
	public void run(IAction action) {
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();

		// Get the current cursor position / selection so we can keep it in place
		int cursorOffset = sel.getOffset();
		int selectionLength = sel.getLength();

		// Compile the rTrim regex and create a matcher to perform the replacement
		Pattern trimPattern = Pattern.compile("[\\t ]+$", Pattern.CANON_EQ);
		Matcher trimmer = trimPattern.matcher("");

		String oldText;
		String newText;

		//We only try this if we can!
		if(editor != null && editor.isEditable()){
		try {
			// foldingSetter.takeSnapshot();

			// Loop over each line, performing the right trim
			int currentLine = 0;
			while (currentLine < doc.getNumberOfLines()) {
				int offset = doc.getLineOffset(currentLine);
				int length = doc.getLineLength(currentLine);
				oldText = doc.get(offset, length);

				trimmer.reset(oldText);
				newText = trimmer.replaceAll("");

				// Replace the old line if the length is different
				if (newText.length() != length) {
					doc.replace(offset, length, newText);

					// Update the cursor offset for the characters removed
					if (offset < cursorOffset) { 
						cursorOffset -= (length - newText.length());
					}
				}

				currentLine++;
			}

			TextSelection selection = new TextSelection(doc, cursorOffset, selectionLength);
			editor.getSelectionProvider().setSelection(selection);

			// foldingSetter.restoreSnapshot();
		} catch (Exception blx) {
			blx.printStackTrace();
		}
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