/*
 * Created on Feb 2, 2004
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
package com.rohanclan.cfml.editors;

import org.eclipse.swt.widgets.Composite;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;

import org.eclipse.core.resources.IResourceChangeListener;

//import org.eclipse.jface.action.IAction;
//import org.eclipse.ui.texteditor.TextOperationAction;
//import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
//import org.eclipse.jface.text.source.ISourceViewer;
//import java.util.ResourceBundle;

/**
 * @author Rob
 *
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
//import org.eclipse.ui.texteditor.StatusTextEditor;
import org.eclipse.ui.editors.text.TextEditor;

public class CFMLEditor extends TextEditor {

	private ColorManager colorManager;
	
	protected GenericEncloserAction testAction;
	
	public CFMLEditor() 
	{
		super();
		colorManager = new ColorManager();
		//setup color coding and the damage repair stuff
		setSourceViewerConfiguration(new CFConfiguration(colorManager));
		//assign the cfml document provider which does the partitioning
		//and connects it to this Edtior
		
		setDocumentProvider(new CFDocumentProvider());
		
		// The following is to enable us to listen to changes. Mainly it's used for
		// getting the document filename when a new document is opened.
		IResourceChangeListener listener = new MyResourceChangeReporter();
		CFMLPlugin.getWorkspace().addResourceChangeListener(listener);
	}
	
	public void createPartControl(Composite parent) 
	{
		super.createPartControl(parent);
	}
	
	public void createActions()
	{
		super.createActions();
		
		/*
		final ResourceBundle bundle = CFMLPlugin.getDefault().getResourceBundle();
		IAction a = new TextOperationAction(
			bundle, 
			"ContentAssistProposal.", 
			this, 
			ISourceViewer.CONTENTASSIST_PROPOSALS
		);
		a.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", a); 
		
		a = new TextOperationAction(
			bundle, 
			"ContentAssistTip.", 
			this, 
			ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION
		);
		a.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
		setAction("ContentAssistTip", a);
		*/
	}
	
	public void dispose() 
	{
		colorManager.dispose();
		super.dispose();
	}
}
