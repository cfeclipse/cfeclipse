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

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.views.snips.SnipKeyCombos;
import com.rohanclan.cfml.views.snips.SnipReader;
import com.rohanclan.cfml.views.snips.SnipVarParser;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IRegion;


/**
 * @author Stephen Milligan
 *
 */
public class InsertSnippetAction extends Encloser implements IEditorActionDelegate {
	
	protected ITextEditor editor = null;
	protected String start = "";
	protected String end = "";
    
    
	public InsertSnippetAction()
	{

	}
	

	
	public void run()
	{
		run(null);
	}
		
	public void run(IAction action) 
	{
		if(editor != null)
		{
		    SnipKeyCombos keyCombos = new SnipKeyCombos();
		    String sequence = "";
			IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
			ISelection sel = editor.getSelectionProvider().getSelection();
			
			int cursorOffset = ((ITextSelection)sel).getOffset()-1;
			int lastSpaceOffset = -1;
			int nextSpaceOffset = -1;
			FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(doc);
			try {
			    IRegion lastSpace = finder.find(cursorOffset,"\\s",false,false,false,true);
			    
			    if (lastSpace == null) {
			        lastSpaceOffset = 0;
			    }
			    else {
			        lastSpaceOffset = lastSpace.getOffset()+1;
			    }
			    
			    if (cursorOffset > lastSpaceOffset) {
			        // ok, it could be valid, but we need to check what comes after the cursor.
			        if (cursorOffset != doc.getLength()-1) {
			            IRegion nextSpace = finder.find(cursorOffset,"\\s",true,false,false,true);
			            if (nextSpace != null
			                    && nextSpace.getOffset() == cursorOffset+1) {
			                sequence = doc.get().substring(lastSpaceOffset,cursorOffset+1);
			            }
			            
			        }
			        else {
			            sequence = doc.get().substring(lastSpaceOffset,cursorOffset+1);
			        }
			    }
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
			
			if (sequence.length() > 0) {
			    String fileName = keyCombos.getKeyCombo(sequence);
			   
			    SnipReader snipReader = new SnipReader();
			    
			    IFile activeFile = null;
				if (this.editor.getEditorInput() instanceof IFileEditorInput) {
					activeFile = ((IFileEditorInput) this.editor.getEditorInput()).getFile();
				}
				
			    snipReader.read(keyCombos.getSnippetFolder() + fileName);
			    
			    start = SnipVarParser.parse(snipReader.getSnipStartBlock(),activeFile,this.editor.getSite().getShell());
			    end = SnipVarParser.parse(snipReader.getSnipEndBlock(),activeFile,this.editor.getSite().getShell());
			    
			    if (start != null && end != null && (start.length() > 0 || end.length() > 0)) {
			    
					this.enclose(doc,(ITextSelection)sel,start,end);
					
					//move the cursor to before the end of the new insert
					int offset = ((ITextSelection)sel).getOffset();
					offset += ((ITextSelection)sel).getLength();
					offset += start.length();
					editor.setHighlightRange(offset,0,true);
					try {
					    doc.replace(lastSpaceOffset,sequence.length(),"");
					}
					catch (Exception e) {
					    e.printStackTrace();
					}
			    }
			}
		}
	}
	
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		//System.err.println(targetEditor);
		//System.out.println( "Changin (" + start + ")(" + end + ")" );
		if( targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor ){
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection){;}
	
}
