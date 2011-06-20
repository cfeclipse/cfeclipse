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
package org.cfeclipse.cfml.snippets.commands;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.cfeclipse.cfml.snippets.util.Encloser;
import org.cfeclipse.cfml.snippets.views.snips.SnipKeyCombos;
import org.cfeclipse.cfml.snippets.views.snips.SnipReader;
import org.cfeclipse.cfml.snippets.views.snips.SnipVarParser;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.part.MultiPageEditor;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 * 
 */
public class InsertSnippetCommand extends AbstractHandler {

	protected String start = "";
	protected String end = "";
	
	public InsertSnippetCommand() {
		super();
	}

	// used from the toolbars
	public InsertSnippetCommand(String triggerText, Shell shell) {
		SnipKeyCombos keyCombos = new SnipKeyCombos();
		ITextEditor editor = (ITextEditor) Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ISelection sel = editor.getSelectionProvider().getSelection();

		String fileName = keyCombos.getKeyCombo(triggerText);

		SnipReader snipReader = new SnipReader();
		IFile activeFile = null;
		if (editor.getEditorInput() instanceof IFileEditorInput) {
			activeFile = ((IFileEditorInput) editor.getEditorInput()).getFile();
		}

		snipReader.read(keyCombos.getSnippetFolder() + fileName);

		start = SnipVarParser.parse(snipReader.getSnipStartBlock(), activeFile, shell);
		end = SnipVarParser.parse(snipReader.getSnipEndBlock(), activeFile, shell);

		Encloser encloser = new Encloser();
		encloser.enclose(doc, (ITextSelection) sel, start, end);

	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		Shell shell = editorPart.getSite().getShell();
		ITextEditor editor = null;
		
		if(editorPart instanceof ITextEditor){
			editor = (ITextEditor)editorPart;
		}else if( editorPart instanceof MultiPageEditorPart){
			editor = (ITextEditor)editorPart.getAdapter(ITextEditor.class);
		}
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();		
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		
		
		if (editor != null && editor.isEditable()) {
			SnipKeyCombos keyCombos = new SnipKeyCombos();

			String sequence = "";


			int cursorOffset = ((ITextSelection) sel).getOffset();
			int lastSpaceOffset = -1;
			// int nextSpaceOffset = -1;
			FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(doc);

			try {
				IRegion lastSpace = finder.find(cursorOffset - 1, "[^\\*0-9a-zA-Z_-]", false, false, false, true);

				if (lastSpace == null) {
					lastSpaceOffset = 0;
				} else {
					lastSpaceOffset = lastSpace.getOffset() + 1;
				}

				// System.out.println("Last Space at" + lastSpaceOffset);
				// System.out.println("Cursot at" + cursorOffset);

				if (cursorOffset > lastSpaceOffset) {
					// ok, it could be valid, but we need to check what comes
					// after the cursor.
					if (cursorOffset != doc.getLength()) {
						//System.out.println("yep");
						IRegion nextSpace = finder
								.find(cursorOffset - 1, "[^\\*0-9a-zA-Z_-]", true, false, false, true);
						if (nextSpace != null && nextSpace.getOffset() == cursorOffset) {
							// System.out.println("Next space bit");
							sequence = doc.get().substring(lastSpaceOffset, cursorOffset);
						}

					} else {
						sequence = doc.get().substring(lastSpaceOffset, cursorOffset);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (sequence.length() == 0) {
				System.out.println("no trigger text has been passed in");

			}

			if (sequence.length() > 0) {

				String[] stringArray = sequence.split("\\*");
				String trigger = stringArray[0];
				int loopcount = 1;
				if (stringArray.length > 1) {
					loopcount = Integer.parseInt(stringArray[1].trim());
				}

				// Here starts the actual triggering of a snippet using the
				// trigger text

				String fileName = keyCombos.getKeyCombo(trigger);

				SnipReader snipReader = new SnipReader();

				IFile activeFile = null;
				if (editor.getEditorInput() instanceof IFileEditorInput) {
					activeFile = ((IFileEditorInput) editor.getEditorInput()).getFile();
				}
				if(fileName == null) {
					// nasty nasty hack to tell the user no snippet found
		    		MessageBox dialog = new MessageBox(shell,SWT.ICON_ERROR);
		    		dialog.setMessage("No snippet found for : "+sequence);
		    		dialog.open();
		    		return null;
				}

				snipReader.read(fileName);

				String indentString = "";
				try {
					int lineNumber = doc.getLineOfOffset(lastSpaceOffset);
					int lineOffset = doc.getLineOffset(lineNumber);
					indentString = doc.get().substring(lineOffset, lastSpaceOffset);
				} catch (Exception e) {
					System.err.println(e);
					// do nothing
					// System.err.println("Insert snippet failed to get insert string.");
				}

				if (indentString.length() > 0) {
					snipReader.performIndent(indentString);
				}

				String snippet = "";

				int finalCursorOffset = -1;

				for (int i = 0; i < loopcount; i++) {
					start = SnipVarParser.parse(snipReader.getSnipStartBlock(), activeFile, shell);
					end = SnipVarParser.parse(snipReader.getSnipEndBlock(), activeFile, shell);
					if (start == null || end == null) {
						snippet = null;
						break;
					} else {
						snippet = start + end;
					}

					if (snippet != null && snippet.length() > 0) {
						Encloser encloser = new Encloser();
						encloser.enclose(doc, (ITextSelection) sel, snippet, "");
						// move the cursor to before the end of the new insert
						int offset = ((ITextSelection) sel).getOffset();
						offset += ((ITextSelection) sel).getLength();
						offset += snippet.length();
						if (i == 0) {
							try {
								doc.replace(lastSpaceOffset, sequence.length(), "");
								sel = new TextSelection(doc, offset - sequence.length(), 0);
								// We only want the cursor coming back if there
								// is something in the end block
								if (end.length() > 0) {
									finalCursorOffset = lastSpaceOffset + start.length();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							sel = new TextSelection(doc, offset, 0);
						}

						editor.setHighlightRange(offset, 0, true);
					} else {
			    		MessageBox dialog = new MessageBox(shell,SWT.ICON_ERROR);
			    		dialog.setMessage("No key combo specified for : "+trigger + "in " + keyCombos.getKeyCombosFilePath());
			    		dialog.open();
						
					}
				}
				if (finalCursorOffset > 0) {
					editor.setHighlightRange(finalCursorOffset, 0, true);
				}
			}
		}
		return null;
	}
	

}
