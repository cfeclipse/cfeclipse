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
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.editors.codefolding.CodeFoldingSetter;

/**
 * @author Stephen Milligan
 */
public class RTrimAction implements IEditorActionDelegate {

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

		int currentLine = 0;
		int originalCursorOffset = sel.getOffset();
		int cursorOffset = originalCursorOffset;
		int originalSelectionLength = sel.getLength();
		int selectionLength = originalSelectionLength;
		String oldText;
		int	lineEnd;
		
		try {
			//foldingSetter.takeSnapshot();

			while (currentLine < doc.getNumberOfLines()) {
				int offset = doc.getLineOffset(currentLine);
				int length = doc.getLineLength(currentLine);
				oldText = doc.get(offset, length);
				
				//-- Starts at the end of the line, looking for the first non-first 'white space'
				//-- it then breaks out. No point in carrying on, as we have found our true line end
				for (lineEnd=oldText.length(); lineEnd > 0; --lineEnd ){
					if ( oldText.charAt(lineEnd-1) != '\t' && oldText.charAt(lineEnd-1) != ' ' ){
						break;
					}
				}
				
				//-- Only replace the line if the lengths are different
				if ( lineEnd != oldText.length() ) {
					String newText = oldText.substring(0, lineEnd);
					doc.replace(offset, length, newText);

					if (offset + length <= cursorOffset) {
						if (oldText.length() != newText.length()) {
							cursorOffset -= oldText.length() - newText.length();
						}
					} else if (offset <= cursorOffset + selectionLength	&& selectionLength > 0) {
						selectionLength -= oldText.length() - newText.length();
					}	else if (offset + length == cursorOffset + 2) { // Check if the cursor is at the end of the line.
						cursorOffset -= 2;
					}
				}
				currentLine++;
			}

			TextSelection selection = new TextSelection(doc, cursorOffset, selectionLength);
			editor.getSelectionProvider().setSelection(selection);
			//foldingSetter.restoreSnapshot();
		} catch (Exception blx) {
			blx.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		;
	}
}