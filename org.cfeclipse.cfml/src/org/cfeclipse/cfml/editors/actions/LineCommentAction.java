/*
 * Created on Feb 15, 2004
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

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
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
 *         This is just like cfcomment; however, this figures out what type of block (partition) you are editing and
 *         provides a comment type based on that. So, for example, javascript comments will be used if in a javascript
 *         block or cf comments in a cfblock.
 */
public class LineCommentAction extends Encloser implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {
	
	ITextEditor editor = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
		}	
	}

	/**
	 * Wraps the selection with the start and end string
	 * 
	 * @param doc
	 *            the document this belongs to
	 * @param sel
	 *            the selection to be wrapped
	 * @param string
	 * @param start
	 *            the string to put before the selection
	 * @param end
	 *            the string to put before the selection
	 */
	public void prependLines(IDocument doc, ITextSelection sel, String prependString, String appendString)
	{
		try
		{
			if(!sel.isEmpty())
			{
				int offset = sel.getOffset();
				int len  = sel.getLength();
				String lineDelim = doc.getLineDelimiter(doc.getLineOfOffset(offset));
				String selection = doc.get(offset, len);
				if (selection.matches("(?ms).*" + prependString + ".*" + appendString)) {
					if (selection.equals(prependString)) {
						selection = selection.replace(prependString, " ");
					} else {
						selection = selection.replaceAll("(?m)^" + prependString + "(.+)" + appendString + "$", "$1");
					}
				} else {
					selection = selection.replaceAll("(?m)^(.+)$", prependString + "$1" + appendString);
				}
				if (selection.length() == 0)
					selection = prependString;
				doc.replace(offset, len, selection);
				editor.selectAndReveal(offset, selection.length());
			}
		}
		catch(BadLocationException ble)
		{
			ble.printStackTrace(System.err);
		}
	}	
	
	public void run(IAction action) 
	{ 
		try
		{
			/*
			 * Adding checks to make sure that we can acutally edit the document
			 * Althought the method editor.isEditable() is not actually reliable. 
			 * Using the isDrity method to see if there lies the discrepancy
			 * 
			 */
				
			if(editor != null && editor.isEditable())			
			{	
				IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
				ISelection sel = editor.getSelectionProvider().getSelection();
				
				String parttype = doc.getPartition(((ITextSelection)sel).getOffset()).getType();
				String start="";
				
				if (parttype.equals(CFPartitionScanner.CF_SCRIPT) || parttype.equals(CFPartitionScanner.CF_SCRIPT_COMMENT)
						|| parttype.equals(CFPartitionScanner.CF_SCRIPT_COMMENT_BLOCK)
						|| parttype.equals(CFPartitionScanner.JAVADOC_COMMENT) || parttype.equals(CFPartitionScanner.J_SCRIPT)
						|| parttype.equals(CFPartitionScanner.CSS)) {
					prependLines(doc, (ITextSelection) sel, "//", "");
				}
				else if(
						parttype.equals(CFPartitionScanner.CF_START_TAG_BEGIN)
						|| parttype.equals(CFPartitionScanner.CF_START_TAG_END)
						|| parttype.equals(CFPartitionScanner.CF_COMMENT)
						|| parttype.equals(CFPartitionScanner.CF_TAG_ATTRIBS)
						|| parttype.equals(CFPartitionScanner.CF_END_TAG)
				)
				{
					prependLines(doc, (ITextSelection) sel, "<!--- ", " --->");
				}
				else
				{
					start = "<!-- ";
					prependLines(doc, (ITextSelection) sel, start, " -->");
				}
				
				//move the cursor to before the end of the new insert
				int offset = ((ITextSelection)sel).getOffset();
				offset += ((ITextSelection)sel).getLength();
				offset += start.length();
				// editor.setHighlightRange(offset,0,true);

				// Tell the plugin's Last Encloser Manager that this was the last one used for this editor
				CFMLPlugin.getDefault().getLastActionManager().setLastAction(editor, this);
			}
		}
		catch(BadLocationException e)
		{
			e.printStackTrace(System.err);
		}
	}

	public void selectionChanged(IAction action, ISelection selection){
		if (editor != null && editor.getSite() != null && editor.getSite().getPage() != null) {
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
