/*
 *
 * The MIT License
 * Copyright (c) 2004 CFE Crew
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

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import cfml.parsing.CFMLParser;
import cfml.parsing.CFMLSource;
import cfml.parsing.ParserTag;

public class ExpandTagSelectionUp implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {
	private CFMLEditor editor;

	public ExpandTagSelectionUp() {
		editor = null;
	}

	/*
	 * @see
	 * org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface
	 * .action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof CFMLEditor) {
			editor = (CFMLEditor) targetEditor;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
		this.expandTagSelection(doc, sel);

	}

	private ParserTag getNextTagLeft(int offset, CFMLSource cfmlSource) {
		ParserTag currentTag = cfmlSource.getEnclosingTag(offset);
		if (currentTag == null) {
			while (offset > 0 && currentTag == null) {
				currentTag = cfmlSource.getEnclosingTag(offset);
				offset--;
			}
		} else if (offset == currentTag.getStartTagBegin()) {
			offset = currentTag.getStartTagBegin() - 1;
			currentTag = null;
			while (offset > 0 && currentTag == null) {
				currentTag = cfmlSource.getEnclosingTag(offset);
				offset--;
			}
		}
		return currentTag;
	}

	public void expandTagSelection(IDocument doc, ITextSelection sel) {
		CFMLPropertyManager propertyManager = new CFMLPropertyManager();
		String dict = propertyManager.getCurrentDictionary(((IFileEditorInput) editor.getEditorInput()).getFile().getProject());
		CFMLParser fCfmlParser = CFMLPlugin.newCFMLParser(dict);
		ICFDocument cfd = (ICFDocument) doc;
		CFMLSource cfmlSource = fCfmlParser.addCFMLSource(cfd.getCFDocument().getFilename(), cfd.get());
		int offset = sel.getOffset();
		ParserTag currentTag = getNextTagLeft(offset, cfmlSource);
		if (currentTag == null){
			// at end of doc
			return;			
		}
		int start = currentTag.getStartTagBegin();
		//int end = currentTag.getEndTagEnd();
		int end = currentTag.getEndTagEnd() > sel.getOffset() + sel.getLength() ? currentTag.getEndTagEnd() : sel
				.getOffset()
				+ sel.getLength();
		int length = end - start;
		editor.selectAndReveal(start, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (editor != null && editor.getSite() != null && editor.getSite().getPage() != null) {
			setActiveEditor(null, editor.getSite().getPage().getActiveEditor());
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if (activeEditor instanceof CFMLEditor) {
			editor = (CFMLEditor) activeEditor;
		}

	}
}
