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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.jface.viewers.IStructuredSelection;
//import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.editors.text.TextEditor;
//import org.eclipse.jface.text.ITextSelection;
import com.rohanclan.cfml.editors.actions.GotoFileAction;
import org.eclipse.jface.action.Action;

import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;

import com.rohanclan.cfml.util.CFPluginImages;
//import com.rohanclan.cfml.views.cfcmethods.CFCMethodViewItem;
import com.rohanclan.cfml.views.contentoutline.CFContentOutlineView;
import org.eclipse.swt.SWT;

//import java.util.Iterator;

import com.rohanclan.cfml.parser.CfmlTagItem;

import org.eclipse.swt.dnd.*;

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
	public void doSave(IProgressMonitor monitor) {
		//TODO: Once we get the document outline going, we can update it from
		// here.
		//On save parsing should apparently go into a builder.
		super.doSave(monitor);
	}

	private DragSource dragsource;

	public CFMLEditor() {
		super();
		colorManager = new ColorManager();
		//setup color coding and the damage repair stuff

		configuration = new CFConfiguration(colorManager, this);
		setSourceViewerConfiguration(configuration);
		//assign the cfml document provider which does the partitioning
		//and connects it to this Edtior

		setDocumentProvider(new CFDocumentProvider());

		// The following is to enable us to listen to changes. Mainly it's used
		// for
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
		this.setBackgroundColor();
		
		//Allow data to be copied or moved from the drag source
		int operations = DND.DROP_MOVE | DND.DROP_COPY;
		dragsource = new DragSource(this.getSourceViewer().getTextWidget(), operations);
		
		//Allow data to be copied or moved to the drop target
		operations  = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget  target = new DropTarget(this.getSourceViewer().getTextWidget(), operations);
		
		Transfer[]  types = new Transfer[] {TextTransfer.getInstance()};
		dragsource.setTransfer(types);
		
		//Receive data in Text or File format
		final TextTransfer textTransfer = TextTransfer.getInstance();
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		final TextEditor thistxt = this;
		
		types = new Transfer[] {fileTransfer, textTransfer};
		target.setTransfer(types);
				
		dragsource.addDragListener(new DragSourceListener(){
			public void dragStart(DragSourceEvent event) {
			  	//Only start the drag if there is actually text in the
				//label - this text will be what is dropped on the target.
				
				//get the selected text
				ITextEditor editor = (ITextEditor)thistxt;
				IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
				ISelection sel = editor.getSelectionProvider().getSelection();
				ITextSelection its = (ITextSelection)sel;
				
				if(its.getText().length() == 0) {
					event.doit = false;
				}
				System.err.println("Drag Start");
			}

			public void dragSetData(DragSourceEvent event) {
				// Provide the data of the requested type.
				if(TextTransfer.getInstance().isSupportedType(event.dataType)) {
					//event.data = dragLabel.getText();
					
					//get the selected text
					ITextEditor editor = (ITextEditor)thistxt;
					IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
					ISelection sel = editor.getSelectionProvider().getSelection();
					ITextSelection its = (ITextSelection)sel;
					event.data = its.getText();
					
					System.err.println(event.data);
				}
				System.err.println("Drag Set Data for " + event.dataType);
			}

			public void dragFinished(DragSourceEvent event) {
				//If a move operation has been performed, remove the data
				//from the source
				if(event.detail == DND.DROP_MOVE){
					//dragLabel.setText("");
				}
				System.err.println("Drag Finished");
			}
		});
		
		target.addDropListener(new DropTargetListener() {
			
			public void dragEnter(DropTargetEvent event) {
				if(event.detail == DND.DROP_DEFAULT) {
					if ((event.operations & DND.DROP_COPY) != 0) {
						event.detail = DND.DROP_COPY;
					} else {
						event.detail = DND.DROP_NONE;
					}
				}
				
				//we'll accept text but prefer to have files dropped
				for (int i = 0; i < event.dataTypes.length; i++) {
					if(fileTransfer.isSupportedType(event.dataTypes[i])){
						event.currentDataType = event.dataTypes[i];
						// files should only be copied
						if (event.detail != DND.DROP_COPY) {
							event.detail = DND.DROP_NONE;
						}
						break;
					}
				}
			}
			
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
				if (textTransfer.isSupportedType(event.currentDataType)) {
					//NOTE: on unsupported platforms this will return null
					String t = (String)(textTransfer.nativeToJava(event.currentDataType));
					if(t != null) {
						System.out.println(t);
					}
				}
			}
			
			public void dragOperationChanged(DropTargetEvent event){ 
				if(event.detail == DND.DROP_DEFAULT) { 
					/* event.detail = (event.operations & DND.DROP_COPY) != 0 ) {
						event.detail = DND.DROP_COPY;
					} else {	
						event.detail = DND.DROP_NONE;
					} */
				}
			
				//allow text to be moved but files should only be copied
				if(fileTransfer.isSupportedType(event.currentDataType)){
					if(event.detail != DND.DROP_COPY) {
						event.detail = DND.DROP_NONE;
					}
				}
			}
			
			public void dragLeave(DropTargetEvent event){;}
			public void dropAccept(DropTargetEvent event){;}
			
			public void drop(DropTargetEvent event) {
				//if we get a text drop
				if(textTransfer.isSupportedType(event.currentDataType)) {
					String text =  (String)event.data;
					//System.out.println(text);
					
					//TODO: this just drops stuff where the cursor was last - so
					//it works kind of oddly
					ITextEditor editor = (ITextEditor)thistxt;
					IDocument doc =  editor.getDocumentProvider().getDocument(editor.getEditorInput());
					ISelection sel = editor.getSelectionProvider().getSelection();
					ITextSelection its = (ITextSelection)sel;
					
					int offset = its.getOffset();
					int len  = its.getLength();
					
					try
					{
						doc.replace(offset, len, text);
					}
					catch(BadLocationException ble)
					{
						ble.printStackTrace(System.err);
					}
				}
				
				//file drop 
				if(fileTransfer.isSupportedType(event.currentDataType)){
					String[] files = (String[]) event.data;
					
					com.rohanclan.cfml.editors.actions.GenericOpenFileAction
					gofa = new com.rohanclan.cfml.editors.actions.GenericOpenFileAction();
					
					for(int i = 0; i < files.length; i++) {
						System.out.println("File: " + files[i]);
						gofa.setFilename(files[i]);
						gofa.run();
					}
				}
			}
		});
		
		
		
		
		
	}	/**
		  * {@inheritDoc}
		  */
	protected void editorContextMenuAboutToShow(IMenuManager menu) {

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
	 * 
	 * @param menu
	 */
	protected void addTagSpecificMenuItems(IMenuManager menu) {
		//all this mess is really just to get the offset and a handle to the
		//CFDocument object attached to the Document...
		IEditorPart iep = getSite().getPage().getActiveEditor();
		ITextEditor editor = (ITextEditor) iep;
		IDocument doc = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		ICFDocument cfd = (ICFDocument) doc;
		ISelection sel = editor.getSelectionProvider().getSelection();

		//ok got our tag (or null)
		int startpos = ((ITextSelection) sel).getOffset();

		CfmlTagItem cti = cfd.getTagAt(startpos, startpos);

		if (cti != null) {
			if (cti.matchingItem != null) {
				jumpAction.setDocPos(cti.matchingItem.getEndPosition());
				jumpAction.setActiveEditor(null, getSite().getPage()
						.getActiveEditor());
				Action jumpNow = new Action("Jump to end tag", CFPluginImages
						.getImageRegistry().getDescriptor(
								CFPluginImages.ICON_FORWARD)) {
					public void run() {
						jumpAction.run(null);
					}
				};
				menu.add(jumpNow);
			}

			String n = cti.getName();
			if (n.equalsIgnoreCase("include") || n.equalsIgnoreCase("module")) {
				//this is a bit hokey - there has to be a way to load the
				// action
				//in the xml file then just call it here...
				//TODO
				gfa
						.setActiveEditor(null, getSite().getPage()
								.getActiveEditor());
				Action ack = new Action("Open File", CFPluginImages
						.getImageRegistry().getDescriptor(
								CFPluginImages.ICON_IMPORT)) {
					public void run() {
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
	public Object getAdapter(Class adapter) {
		//if they ask for the outline page send our implementation
		if (adapter.getName().trim().equals(
				"org.eclipse.ui.views.contentoutline.IContentOutlinePage")) {
			try {
				return new CFContentOutlineView();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			return super.getAdapter(adapter);
			//return super.getAdapter(adapter);
		}
		//otherwise just send our supers
		else {
			return super.getAdapter(adapter);
		}
	}

	public void createActions() {
		super.createActions();

		//this sets up the ctrl+space code insight stuff
		try {
			IAction action = new TextOperationAction(CFMLPlugin.getDefault()
					.getResourceBundle(), "ContentAssistProposal.",
			//"ContentAssistTip.",
					this, ISourceViewer.CONTENTASSIST_PROPOSALS
			//ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION
			);

			action
					.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS
					//ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION
					);

			setAction("ContentAssistAction", action);

			setActionActivationCode(
					ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS,
					' ', -1, SWT.CTRL);

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public void dispose() {
		colorManager.dispose();
		CFMLPlugin.getDefault().getPreferenceStore()
				.removePropertyChangeListener(this);
		super.dispose();
	}

	public void propertyChange(PropertyChangeEvent event) {
		/*
		 * TODO: See if there's any way to implement this without resetting the
		 * tabs for the whole document. If not then at least try to find a way
		 * to have the cursor stay at the position it was when the person went
		 * to the preferences page.
		 */
		if (event.getProperty().equals("tabsAsSpaces")
				|| event.getProperty().equals("tabWidth")) {
			//System.out.println(
			//"Tab preferences have changed. Resetting the editor."
			//);
			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer != null) {
				sourceViewer.getTextWidget().setTabs(
						configuration.getTabWidth(sourceViewer));
			}
		}
		setBackgroundColor();
	}

	/**
	 * Set the background color of the editor window based on the user's
	 * preferences
	 */
	private void setBackgroundColor() {
		// Only try to set the background color when the source viewer is
		// available
		if (this.getSourceViewer() != null) {
			CFMLPreferenceManager manager = new CFMLPreferenceManager();
			// Set the background color of the editor
			this
					.getSourceViewer()
					.getTextWidget()
					.setBackground(
							new org.eclipse.swt.graphics.Color(
									this.getSourceViewer().getTextWidget()
											.getDisplay(),
									manager
											.getColor(ICFMLPreferenceConstants.P_COLOR_BACKGROUND)));
		}
	}
}