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
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
//import java.util.ResourceBundle;
//import org.eclipse.swt.SWT;
//import org.eclipse.ui.texteditor.StatusTextEditor;
import org.eclipse.ui.editors.text.TextEditor;
//import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
//import com.rohanclan.cfml.views.contentoutline.CFContentOutlineView;
//import org.eclipse.jface.util.IPropertyChangeListener;
//import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * @author Rob
 *
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
public class CFMLEditor extends TextEditor  
implements IPropertyChangeListener {

	private ColorManager colorManager;
	
	private CFConfiguration configuration;
	
	//private Composite parent;
	
	protected GenericEncloserAction testAction;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		// 
		// TODO: Once we get the document outline going, we can update it from here.
		// On save parsing should apparently go into a builder.
		
		super.doSave(monitor);
	}
	public CFMLEditor() 
	{
		super();
		colorManager = new ColorManager();
		//setup color coding and the damage repair stuff
		
		configuration = new CFConfiguration(colorManager, this);
		setSourceViewerConfiguration(configuration);
		//assign the cfml document provider which does the partitioning
		//and connects it to this Edtior
		
		setDocumentProvider(new CFDocumentProvider());
		
		// The following is to enable us to listen to changes. Mainly it's used for
		// getting the document filename when a new document is opened.
		IResourceChangeListener listener = new MyResourceChangeReporter();
		CFMLPlugin.getWorkspace().addResourceChangeListener(listener);

		// This ensures that we are notified when the preferences are saved
		CFMLPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}
	
	public void createPartControl(Composite parent) 
	{
		//this.parent = parent;
		super.createPartControl(parent);
	}
	
	/**
	 * @see IAdaptable#getAdapter(java.lang.Class)
	 * @since 2.0
	 */
	public Object getAdapter(Class adapter) 
	{
		//if(adapter.equals(IContentOutlinePage.class)) 
		//{
			/* 
			return new GroovyContentOutline(
				(IFile)getEditorInput().getAdapter(IFile.class)
			);
			*/
		//}
		System.out.println("Give me adapter: " + adapter.getName());
		/* if(adapter.getName().trim().equals(
			"org.eclipse.ui.views.contentoutline.IContentOutlinePage"
		))
		{
			System.err.println("test");
			return new CFContentOutlineView();
		}
		else
		{
			return super.getAdapter(adapter);
		} */
		
		return super.getAdapter(adapter);
	}
	
	public void createActions()
	{
		super.createActions();
		
		//this sets up the ctrl+space code insight stuff
		try
		{
			IAction action = new TextOperationAction(
				CFMLPlugin.getDefault().getResourceBundle(), 
				"ContentAssistProposal.",
				//"ContentAssistTip.",
				this, 
				ISourceViewer.CONTENTASSIST_PROPOSALS
				//ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION
			);
	
			action.setActionDefinitionId(
				ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS
				//ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION
			);
			
			setAction("ContentAssistAction",action);
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	public void dispose() 
	{
		colorManager.dispose();
		super.dispose();
	}
	
	public void propertyChange(PropertyChangeEvent event)
    {
		/*
		 * TODO: See if there's any way to implement this without resetting the
		 * tabs for the whole document. If not then at least try to find a way
		 * to have the cursor stay at the position it was when the person went 
		 * to the preferences page.
		 * 
		 */
		if(event.getProperty().equals("tabsAsSpaces") || event.getProperty().equals("tabWidth")) {
			System.out.println("Tab preferences have changed. Resetting the editor.");
			ISourceViewer sourceViewer = getSourceViewer();
			sourceViewer.getTextWidget().setTabs(configuration.getTabWidth(sourceViewer));
        }
		
    }
}
