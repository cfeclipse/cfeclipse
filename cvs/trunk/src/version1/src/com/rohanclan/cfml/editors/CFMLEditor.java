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
import com.rohanclan.cfml.editors.actions.JumpToDocPos;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
//import org.eclipse.jface.action.Separator;
//import org.eclipse.ui.IEditorPart;
//import org.eclipse.ui.IWorkbenchActionConstants;
//import org.eclipse.ui.texteditor.ITextEditor;
//import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
//import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
//import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.editors.text.TextEditor;
//import org.eclipse.jface.text.ITextSelection;
import com.rohanclan.cfml.editors.actions.GotoFileAction;
import org.eclipse.jface.action.Action;

import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.cfcmethods.CFCMethodViewItem;
import com.rohanclan.cfml.views.contentoutline.CFContentOutlineView;
import org.eclipse.swt.SWT;

import com.rohanclan.cfml.parser.CfmlTagItem;

/**
 * @author Rob
 *
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
public class CFMLEditor extends TextEditor implements IPropertyChangeListener {

	private ColorManager colorManager;
	
	private CFConfiguration configuration;
	
	protected GenericEncloserAction testAction;
	
	final GotoFileAction gfa = new GotoFileAction();
	private final JumpToDocPos jumpAction = new JumpToDocPos();
	/**
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) 
	{
		//TODO: Once we get the document outline going, we can update it from here.
		//On save parsing should apparently go into a builder.	
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
	 * {@inheritDoc}
	 */
	protected void editorContextMenuAboutToShow(IMenuManager menu) 
	{

		addTagSpecificMenuItems(menu);
		super.editorContextMenuAboutToShow(menu);
		
		//addAction(menu, ITextEditorActionConstants.FIND);
		//this is the right way to do lower case stuff, but how do you get it
		//on the menu?
		//addAction(menu,ITextEditorActionConstants.UPPER_CASE);
		//addAction(menu,ITextEditorActionConstants.LOWER_CASE);
	}
	
	/**
	 * Add menu items based on the tag that was right clicked on... doesnt work
	 * as I have no idea how to find out what tag was just clicked on :) seems
	 * like perhaps the CFDocument could know...
	 * @param menu
	 */
	protected void addTagSpecificMenuItems(IMenuManager menu)
	{
		//all this mess is really just to get the offset and a handle to the
		//CFDocument object attached to the Document...
		IEditorPart iep = getSite().getPage().getActiveEditor();
		ITextEditor editor = (ITextEditor)iep;
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ICFDocument cfd = (ICFDocument)doc;
		ISelection sel = editor.getSelectionProvider().getSelection();
		
		//ok got our tag (or null)
		int startpos = ((ITextSelection)sel).getOffset();
		
		CfmlTagItem cti = cfd.getTagAt(startpos,startpos);
		
		
		if(cti != null)
		    
		{
		    

			if(cti.matchingItem != null) {
				jumpAction.setDocPos(cti.matchingItem.getEndPosition());
				jumpAction.setActiveEditor(null, getSite().getPage().getActiveEditor());
				Action jumpNow = new Action("Jump to end tag", 
											CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_FORWARD)
											) { public void run() { jumpAction.run(null); } };
				menu.add(jumpNow);			
			}
			
			String n = cti.getName(); 
			if(n.equalsIgnoreCase("include") || n.equalsIgnoreCase("module"))
			{
				//this is a bit hokey - there has to be a way to load the action
				//in the xml file then just call it here...
				//TODO
				gfa.setActiveEditor(null,getSite().getPage().getActiveEditor());
				Action ack = new Action(
					"Open File",
					CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_IMPORT)
				){
					public void run()
					{
						gfa.run(null);
					}
				};
				menu.add(ack);
			}
		}
	}
		
	/**
	 * @see IAdaptable#getAdapter(java.lang.Class)
	 * @since 2.0
	 */
	public Object getAdapter(Class adapter) 
	{
		//if they ask for the outline page send our implementation
		if(adapter.getName().trim().equals(
			"org.eclipse.ui.views.contentoutline.IContentOutlinePage"
		))
		{
			try
			{
				return new CFContentOutlineView();
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
			}
			return super.getAdapter(adapter);
			//return super.getAdapter(adapter);
		}
		//otherwise just send our supers
		else
		{
			return super.getAdapter(adapter);
		}
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

			setActionActivationCode(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS,' ', -1, SWT.CTRL);

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
		 */
		if(event.getProperty().equals("tabsAsSpaces") 
			|| event.getProperty().equals("tabWidth"))
		{
		//System.out.println(
				//"Tab preferences have changed. Resetting the editor."
			//);
			ISourceViewer sourceViewer = getSourceViewer();
			sourceViewer.getTextWidget().setTabs(
				configuration.getTabWidth(sourceViewer)
			);
        }
    }
}
