/*
 * Created on July 20, 2004
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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IViewPart;
import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.views.browser.BrowserView;

/**
 * @author Mark Drew
 *
 * Action to get Help for a tag or function from cfdocs.org
 */
public class GetHelpAction implements IEditorActionDelegate {
	protected ITextEditor editor = null;
		
	public GetHelpAction()
	{
		super();
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
		{
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run()
	{
		run(null);
	}
		
	public void run(IAction action) 
	{
		//The site thatwe are using to search
		//String urldest = "http://www.google.com/search?q=";
		String urldest = "http://www.cfdocs.org/";
		String theFullURL = ""; //this is where we go
		String keyword = "";

		
		//Get thecurrent slection, then we can go forth and back and get the info
		//get the document and selection and pass it to the word manipulator
		//so it can extract and rewrite what we want (super class)
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
		String query = sel.getText();
		//System.out.println("query["+query+"]");
		if(query.length() > 0){
			//System.out.println("selection is not empty");
			//There is some selected text, we can go and find it now
			theFullURL = urldest + query;
		} else {
			//System.out.println("selection is empty, lets find the tag");
			//so no selection.. we just go to the site at the moment (should look up if we are in a tag)
			//find the previous ocurrance of "<"
			// If there is no SELECTION, we find the tag that we are in by doing the following.. maybe a new method?
			
			try {

				int cursorOffset = sel.getOffset()-1;
				
				FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(doc);
				IRegion region = finder.find(cursorOffset,"[^a-z]",false,false,false,true);

				
				int keywordStart = 0;
				if (region != null) {
				    keywordStart = region.getOffset()+1;
				}
				
				//System.out.println("Keyword starts at: "  + keywordStart);
				
				region = finder.find(cursorOffset,"[^a-z]",true,false,false,true);
				int keywordEnd = doc.getLength()-1;
				if (region != null) {
				    keywordEnd = region.getOffset() -1;
				}
				//System.out.println("Keyword ends at: " + keywordEnd);
				
				
				if(keywordEnd < cursorOffset) {
				    keywordEnd = cursorOffset;
				}
				
				//if we have found it...
				if(keywordStart != -1 && keywordEnd > keywordStart){
						int keywordLength = keywordEnd - keywordStart + 1;
						keyword = doc.get(keywordStart, keywordLength);
					
				
				} else {
					keyword = "";
				
				}
				
				
				theFullURL = urldest + keyword;
				
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		if (theFullURL.length() == 0 || urldest.length() == theFullURL.length()) {
		    theFullURL = "http://livedocs.macromedia.com";
		}
		
		//Get thecurrent page
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		
		IViewReference ref[] = page.getViewReferences();
		
		try {
		   BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
		   browser.setUrl(theFullURL);
		}
		catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection){;}

}
