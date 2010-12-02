/*
 * Created on Jan 31, 2004
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Rob
 *
 *
 * The adds cold fusion style comments around the selected text (or just sticks
 * in the comments if no text is selected) 
 */
public class CFCommentAction extends GenericEncloserAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {
	protected ITextEditor editor = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor instanceof ITextEditor){
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run(IAction action){
		//checks to see if you can edit the document
		try {
			if(editor != null && editor.isEditable()){
				// make the edit atomic so one undo undos
				IRewriteTarget target = (IRewriteTarget) editor.getAdapter(IRewriteTarget.class);
				if (target != null)
					target.beginCompoundChange();
				String openComment = "<!--- ";
				String closeComment = " --->";
				int selectionLength = 0;
				// Get the document
				IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
				String lineEnding = doc.getLineDelimiter(0);
				
				// Get the selection 
				ISelection sel = editor.getSelectionProvider().getSelection();
				ITextSelection selectioner = (ITextSelection) sel;
				// Get the partition
				ITypedRegion partition = doc.getPartition(((ITextSelection)sel).getOffset());
				String partType = partition.getType();
				if (partType.equals(CFPartitionScanner.CF_SCRIPT) || partType.equals(CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK)
						|| partType.equals(CFPartitionScanner.JAVADOC_COMMENT) || partType.equals(CFPartitionScanner.CF_SCRIPT_COMMENT)) {
					openComment = "/*";
					closeComment = "*/";
				}
				
				// if we already are in a comment partition, remove it, else add it
				if (partType.equals(CFPartitionScanner.CF_COMMENT) || partType.equals(CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK)
						|| partType.equals(CFPartitionScanner.JAVADOC_COMMENT) || partType.equals(CFPartitionScanner.CF_SCRIPT_COMMENT)) {
					// Track the position in the document that the actual comments blocks are
					int openCommentStart = partition.getOffset();
					int closeCommentStart = partition.getOffset();
					
					// Find the full partition selection string
					String selection = doc.get().substring(partition.getOffset(), partition.getOffset() + partition.getLength());
					
					// Find the opening comment information with optional space at the end
					Pattern pattern = Pattern.compile("^(" + openComment.replace("*", "[*]") + "[ ]?[" + lineEnding + "]?)");
					Matcher matcher = pattern.matcher(selection);
					
					if(matcher.find()) {
						openComment = matcher.group();
						openCommentStart += matcher.start();
					}
					
					// Find the closing comment information with optional space at the beginning
					pattern = Pattern.compile("([" + lineEnding + "]?[ ]?" + closeComment.replace("*", "[*]") + ")$");
					matcher = pattern.matcher(selection);
					
					if(matcher.find()) {
						closeComment = matcher.group();
						closeCommentStart += matcher.start();
					}
					
					// Only get rid of the newline characters if the open and close both have them
					// Check to see if the open has a newline but the close doesn't
					if (selectioner.getText().endsWith(lineEnding) && selectioner.getText().startsWith(openComment + lineEnding)) {
						openComment = openComment.substring(lineEnding.length() - 1, openComment.length() - lineEnding.length());
					}
					
					FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(doc);
					
					// Find and replace the closing comment first so that it doesn't get messed up with positions
					finder.find(closeCommentStart, closeComment, true, false, false, false);
					finder.replace("", false);
					
					// Find and replace the opening comment so that it doesn't affect the closing comment replace
					finder.find(openCommentStart, openComment, true, false, false, false);
					finder.replace("", false);
					selectionLength = selection.length() - openComment.length() - closeComment.length() + 1;
				} else {
					if(selectioner.getText().endsWith("\n") && !selectioner.getText().startsWith("\n")){						
						// add newlines if this looks like a newline-to-newline comment, to be pretty
						openComment = openComment.concat("\n");
						closeComment = closeComment.concat("\n");
					}
					selectionLength = this.enclose(doc,(ITextSelection)sel, openComment, closeComment);
					
					// move the caret somewhere
				}
				if (selectioner.getLength() == 0) {
					editor.setHighlightRange(selectioner.getOffset() + openComment.length(), 1, true);
				} else {
					editor.selectAndReveal(selectioner.getOffset(), selectionLength);
				}
				if (target != null)
					target.endCompoundChange();

			}
		} catch (BadLocationException e) {
			e.printStackTrace(System.err);
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
