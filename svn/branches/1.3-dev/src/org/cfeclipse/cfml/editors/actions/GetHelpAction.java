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
package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.partitioner.PartitionHelper;
import org.cfeclipse.cfml.views.browser.BrowserView;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;

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
		/*
		 * These are the new URL's to use
		 * http://cfdocs.cfeclipse.org/index.cfm?item=cfabort
		 * http://cfdocs.cfeclipse.org/index.cfm?item=cfabort&version=5.0
		 *  CFQuickDocs doesnt do versioning?
		 *  
		 */
		
		
		/*
		 * if the selection hasnt got any characters in length, lets try and find it using our partition, if it has, use that
		 */
		
		
		String urldest = "http://www.cfeclipse.org/cfdocs/?query="; //TODO: Change this help url out.
		String query = "";
		
		IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput()); 
		ITextSelection sel = (ITextSelection)editor.getSelectionProvider().getSelection();
		
		if(sel.getLength() > 0){
			query = sel.getText(); 
		}
		else {
			PartitionHelper ph = new PartitionHelper((ICFDocument)doc, sel.getOffset());
			query = ph.getTagName();
		}
	
		String theFullURL = urldest + query;
		
		IExtensionRegistry extReg = Platform.getExtensionRegistry();
		if(extReg.getExtensions("com.adobe.coldfusion_help_7").length > 0){
			IWorkbenchHelpSystem helpsys = Workbench.getInstance().getHelpSystem();
			helpsys.search(query);
		}
		else{
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
				   BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
				   browser.setUrl(theFullURL, BrowserView.HELP_TAB);
				   browser.setFocus(BrowserView.HELP_TAB);
				}
				catch(Exception e) {
				    e.printStackTrace();
				}
		}
	}

	public void selectionChanged(IAction action, ISelection selection){;}

}
