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
package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.jface.action.IAction;

import com.rohanclan.cfml.editors.CFPartitionScanner;

import org.eclipse.jface.text.BadLocationException;
//import org.eclipse.ui.IFileEditorInput;
//import org.eclipse.jface.text.source.ISourceViewer;
//import org.eclipse.jface.text.ITextViewer;
//import org.eclipse.jface.text.source.SourceViewer;
//import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * @author Rob
 *
 * This is just like cfcomment; however, this figures out what type of 
 * block (partition) you are editing and provides a comment type based on that.
 * So, for example, javascript comments will be used if in a javascript block
 * or cf comments in a cfblock.
 */
public class AutoCommentAction extends Encloser implements IEditorActionDelegate {
	
	ITextEditor editor = null;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if( targetEditor instanceof ITextEditor )
		{
			editor = (ITextEditor)targetEditor;
		}	
	}

	public void run(IAction action) 
	{ 
		try
		{
			if(editor != null)
			{	
				IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
				ISelection sel = editor.getSelectionProvider().getSelection();
				
				String parttype = doc.getPartition(((ITextSelection)sel).getOffset()).getType();
				String start="";
				
				if(
					parttype.equals(CFPartitionScanner.CF_SCRIPT)
					|| parttype.equals(CFPartitionScanner.J_SCRIPT)
					|| parttype.equals(CFPartitionScanner.CSS_TAG))
				{
					start = "/* ";
					this.enclose(doc,(ITextSelection)sel,start," */");
				}
				else if(
					parttype.equals(CFPartitionScanner.CF_TAG)
					|| parttype.equals(CFPartitionScanner.CF_END_TAG)
				)
				{
					start = "<!--- ";
					this.enclose(doc,(ITextSelection)sel,start," --->");
				}
				else
				{
					start = "<!-- ";
					this.enclose(doc,(ITextSelection)sel,start," -->");
				}
				
				//move the cursor to before the end of the new insert
				int offset = ((ITextSelection)sel).getOffset();
				offset += ((ITextSelection)sel).getLength();
				offset += start.length();
				editor.setHighlightRange(offset,0,true);
			}
		}
		catch(BadLocationException e)
		{
			e.printStackTrace(System.err);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {;}	
}
