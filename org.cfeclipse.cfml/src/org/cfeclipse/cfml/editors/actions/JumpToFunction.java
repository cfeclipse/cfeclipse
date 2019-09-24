/*
 * Created on Jul 12, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
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

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.contentassist.AssistUtils;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.util.CFDocUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * 
 * Totally cheap jump to function impl. To do this at all right we need AST for project. better than nothing though.
 * Searches inside editor for function def.
 * 
 * @author Denny Valliant
 */
public class JumpToFunction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {

	private ITextEditor editor = null;
	
	public JumpToFunction() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
		{
			editor = (ITextEditor)targetEditor;
		}
	}

	/* 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
		editor.getSelectionProvider().setSelection(CFDocUtils.selectWord(doc, sel.getOffset()));
		sel = (ITextSelection) editor.getSelectionProvider().getSelection();
		String functionName = sel.getText();
		int startOffset = sel.getOffset();
		FindReplaceDocumentAdapter search = new FindReplaceDocumentAdapter(doc);
		try {
			IRegion startPos = search.find(sel.getOffset() + functionName.length(), "name\\s?=\\s?[\"']" + functionName + "[\"']", true, false,
					false, true);
			if (startPos == null) {
				startPos = search.find(sel.getOffset() + functionName.length(), "name\\s?=\\s?[\"']" + functionName + "[\"']", false, false, false,
						true);
			}
			if (startPos == null) {
				startPos = search.find(sel.getOffset() + functionName.length(), "function\\s" + functionName + "[(\\s]", false, false, false,
						true);
			}
			if (startPos == null) {
				startPos = search.find(sel.getOffset() + functionName.length(), "function\\s" + functionName + "[(\\s]", true, false, false,
						true);
			}
			// search for assignment, backwards first
			if (startPos == null) {
				startPos = search.find(sel.getOffset() + functionName.length(), functionName + "[\\s]?=[^=]", false, false, false, true);
			}
			if (startPos == null) {
				startPos = search.find(sel.getOffset() + functionName.length(), functionName + "[\\s]?=[^=]", true, false, false, true);
			}
			if (startPos == null) {
				if (doc.getChar(startOffset - 1) == '.') {
					String cfcInstance = CFDocUtils.selectWord(doc, sel.getOffset() - 2).getText();
					IRegion assignmentPos = search.find(sel.getOffset() + cfcInstance.length(), cfcInstance + "[\\s]?=[\\s]?new\\s", false,
							false, false, true);
					if (assignmentPos == null) {
						assignmentPos = search.find(sel.getOffset() + cfcInstance.length(), cfcInstance + "[\\s]?=[\\s]?createObject",
								false, false, false, true);
					}
					if (assignmentPos == null) {
						assignmentPos = search.find(sel.getOffset() + cfcInstance.length(), cfcInstance + "[\\s]?=[\\s]?entityNew", false,
								false, false, true);
					}
					if (assignmentPos != null) {
						//MappingManager mappingManager = new MappingManager();
						CFDocument cfdoc = ((ICFDocument) doc).getCFDocument();
						String CFCName = AssistUtils.getCFCName(cfcInstance, cfdoc);
						IFile foundCFC = AssistUtils.findCFC(CFCName);
						if (foundCFC == null) {
							foundCFC = AssistUtils.findCFC(functionName);
						}
						if (foundCFC != null) {
							OpenAtMethodAction openAction = new OpenAtMethodAction(foundCFC, "");
							openAction.run();
						}

					}

				}
				startPos = search.find(sel.getOffset() + functionName.length(), functionName + "[\\s]?=[^=]", true, false, false, true);
			}
			if (startPos != null) {
				startPos = search.find(startPos.getOffset(), functionName, true, true, false, true);
				editor.getSelectionProvider().setSelection(CFDocUtils.selectWord(doc, startPos.getOffset()));
			}
			if (startPos == null || sel.getOffset() == startPos.getOffset()) {
				DocItem st = ((CFMLEditor) editor).getSelectionCursorListener().getSelectedTag();
				if (st.getName().equals("ASTComponent") || st.getName().equals("FunctionCall")) {
					CFDocument cfdoc = ((ICFDocument) doc).getCFDocument();
					String CFCName = AssistUtils.getCFCName(functionName, cfdoc);
					IFile foundCFC = AssistUtils.findCFC(CFCName);
					if (foundCFC == null) {
						foundCFC = AssistUtils.findCFC(functionName);
					}
					if (foundCFC != null) {
						OpenAtMethodAction openAction = new OpenAtMethodAction(foundCFC, "");
						openAction.run();
					}
				}
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection){
		if (editor != null && editor.getSite() != null && editor.getSite().getPage() != null && editor.getSite().getPage().getActiveEditor() != null) {
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
