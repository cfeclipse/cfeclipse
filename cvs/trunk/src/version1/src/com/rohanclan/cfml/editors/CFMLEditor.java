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

//import org.apache.log4j.Logger;


//import java.util.Iterator;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
//import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
//import org.eclipse.ui.IWorkbenchPage;
//import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.contexts.EnabledSubmission;
//import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.contexts.EnabledSubmission;
import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.editors.text.ITextEditorHelpContextIds;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.texteditor.TextOperationAction;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.actions.EditTagAction;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;
import com.rohanclan.cfml.editors.actions.GotoFileAction;
import com.rohanclan.cfml.editors.actions.JumpToDocPos;
import com.rohanclan.cfml.editors.actions.JumpToMatchingTagAction;
import com.rohanclan.cfml.editors.actions.RTrimAction;
import com.rohanclan.cfml.editors.codefolding.CodeFoldingSetter;
import com.rohanclan.cfml.editors.decoration.DecorationSupport;
import com.rohanclan.cfml.editors.dnd.CFEDragDropListener;
import com.rohanclan.cfml.editors.dnd.SelectionCursorListener;
import com.rohanclan.cfml.editors.pairs.CFMLPairMatcher;
import com.rohanclan.cfml.editors.pairs.Pair;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;
import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;
import com.rohanclan.cfml.editors.partitioner.PartitionTypes;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;
import com.rohanclan.cfml.editors.ui.CFMLEditorToolbar;
import com.rohanclan.cfml.parser.docitems.CfmlTagItem;
//import com.rohanclan.cfml.plugindebug.DebugSettings;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.EditorPreferenceConstants;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.util.CFDocUtils;
import com.rohanclan.cfml.views.contentoutline.CFContentOutlineView;

/**
 * @author Rob
 * 
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
public class CFMLEditor extends AbstractDecoratedTextEditor implements
		IPropertyChangeListener {
    /**
     * Logger for this class
     */
    //private static final Logger logger = Logger.getLogger(CFMLEditor.class);
    //private final boolean DEBUG = DebugSettings.EDITORS;
    
	protected ColorManager colorManager;

	protected CFConfiguration configuration;

	protected CFMLPairMatcher cfmlBracketMatcher;

	protected GenericEncloserAction testAction;

	final GotoFileAction gfa = new GotoFileAction();

	final JumpToDocPos jumpAction = new JumpToDocPos();

	private CodeFoldingSetter foldingSetter;

	protected LineNumberRulerColumn fLineNumberRulerColumn;

	/**
	 * The change ruler column.
	 */

	boolean fIsChangeInformationShown;

	private ProjectionSupport fProjectionSupport;

	private DragSource dragsource;


	/**
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		//On save parsing should apparently go into a builder.
		if (getPreferenceStore().getBoolean(
				EditorPreferenceConstants.P_RTRIM_ON_SAVE)) {

			((CFEUndoManager) this.configuration.getUndoManager(this.getSourceViewer()))
					.listenToTextChanges(false);
			RTrimAction trimAction = new RTrimAction();
			trimAction.setActiveEditor(null, getSite().getPage().getActiveEditor());
			trimAction.run(null);
			((CFEUndoManager) this.configuration.getUndoManager(this.getSourceViewer()))
					.listenToTextChanges(true);
		}
		try {

			super.doSave(monitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.foldingSetter.docChanged(false);

	}

	public boolean isSaveAsAllowed() {
	    return true;
	}
	
	public CFMLEditor() {
		super();

		//	this is for bracket matching
		//create the pairs for testing
		Pair parenthesis = new Pair("(", ")", 1);
		Pair curlyBraces = new Pair("{", "}", 1);
		Pair squareBraces = new Pair("[", "]", 1);

		//create the collection
		LinkedList brackets = new LinkedList();
		brackets.add(parenthesis);
		brackets.add(curlyBraces);
		brackets.add(squareBraces);

		//create the CFMLPairMatcher
		this.cfmlBracketMatcher = new CFMLPairMatcher(brackets);
		//end bracket matching stuff

		this.colorManager = new ColorManager();
		//setup color coding and the damage repair stuff

		this.configuration = new CFConfiguration(this.colorManager, this);
		setSourceViewerConfiguration(this.configuration);
		//assign the cfml document provider which does the partitioning
		//and connects it to this Edtior

		setDocumentProvider(new CFDocumentProvider());

		// The following is to enable us to listen to changes. Mainly it's used
		// for
		// getting the document filename when a new document is opened.
		IResourceChangeListener listener = new MyResourceChangeReporter();
		CFMLPlugin.getWorkspace().addResourceChangeListener(listener);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		// This ensures that we are notified when the preferences are saved
		CFMLPlugin.getDefault().getPreferenceStore()
				.addPropertyChangeListener(this);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method configures the editor but does not define a
	 * <code>SourceViewerConfiguration</code>. When only interested in
	 * providing a custom source fViewer configuration, subclasses may extend this
	 * method.
	 */
	protected void initializeEditor() {
		setEditorContextMenuId("#CFMLEditorContext"); //$NON-NLS-1$
		setRulerContextMenuId("#TextRulerContext"); //$NON-NLS-1$
		setHelpContextId(ITextEditorHelpContextIds.TEXT_EDITOR);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		configureInsertMode(SMART_INSERT, false);
		setInsertMode(INSERT);

	}

	public void createPartControl(Composite parent) {
	    
	    
		/*Check the preferences, and add a toolbar 
		if(getPreferenceStore().getBoolean(EditorPreferenceConstants.P_SHOW_EDITOR_TOOLBAR)){
			CFMLEditorToolbar editorWithToolbar = new CFMLEditorToolbar();
			parent = editorWithToolbar.getTabs(parent);
		}
		*/
		super.createPartControl(parent);
		this.setBackgroundColor();
		this.fSourceViewerDecorationSupport.install(getPreferenceStore());

		ProjectionViewer projectionViewer = (ProjectionViewer) getSourceViewer();

		this.fProjectionSupport = new ProjectionSupport(projectionViewer,
				getAnnotationAccess(), getSharedColors());
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.task");
		this.fProjectionSupport
				.addSummarizableAnnotationType("com.rohanclan.cfml.parserProblemAnnotation");
		this.fProjectionSupport
				.addSummarizableAnnotationType("com.rohanclan.cfml.parserWarningAnnotation");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning");
		this.fProjectionSupport.setHoverControlCreator(new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell shell) {

                IInformationControl returnIInformationControl = new DefaultInformationControl(
                        shell);
				return returnIInformationControl;
			}
		});
		this.fProjectionSupport.install();
		//Object lay = parent.getLayoutData();

		//System.out.println(lay.getClass());
		
		 
		    
		    //one.setToolTipText("This is tab one");
		  //  one.setControl(getTabOneControl(tabFolder));

		    
		    //two.setToolTipText("This is tab two");
		  //  two.setControl(getTabTwoControl(tabFolder));

		
		    //three.setToolTipText("This is tab three");
		  //  three.setControl(getTabThreeControl(tabFolder));

		   
		    //four.setToolTipText("This is tab four");
		
		
		
		projectionViewer.doOperation(ProjectionViewer.TOGGLE);

		this.foldingSetter = new CodeFoldingSetter(this);
		this.foldingSetter.docChanged(true);

		// TODO: If we set this directly the projection fViewer loses track of the
		// line numbers.
		// Need to create a class that extends projectionViewer so we can implement
		// wrapped
		// line tracking.
		//projectionViewer.getTextWidget().setWordWrap(true);

		createDragAndDrop(projectionViewer);

		try {
			if (isEditorInputReadOnly()
					&& getPreferenceStore().getBoolean(
							EditorPreferenceConstants.P_WARN_READ_ONLY_FILES)) {
				String[] labels = new String[1];
				labels[0] = "OK";
				MessageDialogWithToggle msg = new MessageDialogWithToggle(
						this.getEditorSite().getShell(),
						"Warning!",
						null,
						"You are opening a read only file. You will not be able to make or save any changes.",
						MessageDialog.WARNING, labels, 0,
						"Don't show this warning in future.", false);
				//MessageBox msg = new MessageBox(this.getEditorSite().getShell());
				//msg.setText("Warning!");
				//msg.setMessage("You are opening a read only file. You will not be
				// able to make or save any changes.");
				if (msg.open() == 0) {
					if (msg.getToggleState()) {
						// Don't show warning in future.
						getPreferenceStore().setValue(
								EditorPreferenceConstants.P_WARN_READ_ONLY_FILES, false);
					}
				}
			}
			
			//if (this.DEBUG) {
			    //IWorkbenchPage p = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			    //System.out.println(p);
			//}
			
		} catch (Exception e) {
            

			e.printStackTrace();
		}
		
	}

	public IVerticalRuler verticalRuler() {

		return this.getVerticalRuler();
	}

	private void createDragAndDrop(ProjectionViewer projectionViewer) {

		SelectionCursorListener cursorListener = new SelectionCursorListener(this,
				projectionViewer);
		projectionViewer.getTextWidget().addMouseMoveListener(cursorListener);
		//projectionViewer.getTextWidget().addMouseTrackListener(cursorListener);
		projectionViewer.addSelectionChangedListener(cursorListener);
		projectionViewer.getTextWidget().addMouseListener(cursorListener);
		projectionViewer.getTextWidget().addKeyListener(cursorListener);

		//Allow data to be copied or moved from the drag source
		int operations = DND.DROP_MOVE | DND.DROP_COPY;
		this.dragsource = new DragSource(this.getSourceViewer().getTextWidget(),
				operations);

		//Allow data to be copied or moved to the drop target
		operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(this.getSourceViewer().getTextWidget(),
				operations);

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		this.dragsource.setTransfer(types);

		//Receive data in Text or File format
		final TextTransfer textTransfer = TextTransfer.getInstance();
		final FileTransfer fileTransfer = FileTransfer.getInstance();

		types = new Transfer[] { fileTransfer, textTransfer };
		target.setTransfer(types);

		CFEDragDropListener ddListener = new CFEDragDropListener(this,
				(ProjectionViewer) this.getSourceViewer(), cursorListener);

		this.dragsource.addDragListener(ddListener);

		target.addDropListener(ddListener);

	}

	/**
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
	 * Add menu items based on the tag that was right clicked on... doesnt work as
	 * I have no idea how to find out what tag was just clicked on :) seems like
	 * perhaps the CFDocument could know...
	 * 
	 * @param menu
	 */
	protected void addTagSpecificMenuItems(IMenuManager menu) {
		//all this mess is really just to get the offset and a handle to the
		//CFDocument object attached to the Document...
		try {
			final IEditorPart iep = getSite().getPage().getActiveEditor();
			final ITextEditor editor = (ITextEditor) iep;
			final IDocument doc = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());

			final ICFDocument cfd = (ICFDocument) doc;
			final ITextSelection sel = (ITextSelection) editor.getSelectionProvider()
					.getSelection();


			Action act = new Action("Refresh syntax highlighting", null) {
				public void run() {
				   try {
				      
				  
					CFEPartitioner partitioner = new CFEPartitioner(
							new CFPartitionScanner(), PartitionTypes.ALL_PARTITION_TYPES);
					partitioner.connect(doc);
					doc.setDocumentPartitioner(partitioner);
				   } catch (Exception e) {
				       e.printStackTrace();
				   }
				}
			};
			menu.add(act);



			act = new Action("Show partition info", null) {
				public void run() {
				    /*
				    IEditorPart iep = getSite().getPage().getActiveEditor();
					ITextEditor editor = (ITextEditor) iep;
					IDocument doc = editor.getDocumentProvider().getDocument(
							editor.getEditorInput());

					ICFDocument cfd = (ICFDocument) doc;
					ITextSelection sel = (ITextSelection) editor.getSelectionProvider()
							.getSelection();
							*/
					int startpos = sel.getOffset();
					int len = Math.max(sel.getLength(),1);
					CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
					CFEPartition[] partitioning = partitioner.getCFEPartitions(startpos,startpos+len);
				    String info = "Partitioning info from offset " + startpos + " to " + Integer.toString(startpos + len) + "\n\n";
				    CFEPartition part = partitioner.findClosestPartition(startpos);
				    info += "(Closest partition: " + part.getType() + " = " + part.getTagName() + ")\n";
					for (int i=0;i<partitioning.length;i++) {
					    info += partitioning[i].getType(); 
					    info += " starting at ";
					    info += partitioning[i].getOffset();
					    info += " ending at "; 
					    info += Integer.toString(partitioning[i].getOffset() + partitioning[i].getLength());
					    if (partitioning[i].getTagName() != null) {
					        info += " (";
					        info += partitioning[i].getTagName();
					        info += ") ";
					    }
					    info += "\n";
					}
				    
				    String[] labels = new String[1];
					labels[0] = "OK";
				    MessageDialog msg = new MessageDialog(
							Display.getCurrent().getActiveShell(),
							"Partition info",
							null,
							info,
							MessageDialog.WARNING, 
							labels, 
							0);
				    msg.open();
				}
			};
			menu.add(act);

			
			/*
			 * Edit this tag action start
			 */
			act = new Action("Edit this tag", null){
				public void run() {
					
					SyntaxDictionary cfdic = DictionaryManager.getDictionary("CF_DICTIONARY");
					/*
					 * Since we are already in a start tag, we find the start and the end 
					 * 
					 */
					
					int startpos = sel.getOffset();
					//Find the length just in case
					int len = Math.max(sel.getLength(),1);
					
					//default start and end are at the cursor
					int startoftag = sel.getOffset();
					int endoftag = sel.getOffset();
					int lengthoftag = endoftag - startoftag;
					
					try {
						startoftag = doc.search(startpos, "<", false, true, false);
						endoftag = doc.search(startpos, ">", true, true, false);
						lengthoftag = endoftag - startoftag + 1;
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					TextSelection selection = new TextSelection(startoftag, lengthoftag);
					editor.getSelectionProvider().setSelection(selection);
					TextSelection seli = (TextSelection)editor.getSelectionProvider().getSelection();
					
					//Now we have the whole start tag, we can then pass the tagname and 
					CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
					CFEPartition part = partitioner.findClosestPartition(startpos);
					
				
					Map tagattribs = CFDocUtils.parseStartTag(part.getTagName(), seli.getText());
								 
					Tag tag = cfdic.getTag(part.getTagName());
					if(tag != null){
						EditTagAction eta = new EditTagAction(tag, Display.getCurrent().getActiveShell(),tagattribs);
					
						eta.run();
					} 
					else {
						System.out.println("No tag found " + part.getTagName());	
					}
				
				}
			};
			//Only display if you are at the start tag
			int startpos = sel.getOffset();
			CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
			CFEPartition part = partitioner.findClosestPartition(startpos);
			if((part.getType().equals(CFPartitionScanner.CF_START_TAG_BEGIN) || (part.getType().equals(CFPartitionScanner.CF_TAG_ATTRIBS)))){
				menu.add(act);
			}
			
				
			
			act = new Action("Jump to matching tag", null) {
			    public void run() {
			        JumpToMatchingTagAction matchTagAction = new JumpToMatchingTagAction();
			        matchTagAction.setActiveEditor(null, getSite().getPage().getActiveEditor());
			        matchTagAction.run(null);
			    }
			};
			menu.add(act);
			
			/*
			 * Start the logic to see if we are in a cfinclude or a cfmodule. get the tag.
			 * Added the lines below to check the partition type and thus get the cfincluded file
			 */
		
		   
		    
			CfmlTagItem cti = null; 
		    try {
		        cti = cfd.getTagAt(startpos, startpos);
		    }
			catch (Exception e) {
			    // do nothing.
			}

			if (cti != null) {

				if (cti.matchingItem != null) {

					this.jumpAction.setDocPos(cti.matchingItem.getEndPosition());
					this.jumpAction.setActiveEditor(null, getSite().getPage()
							.getActiveEditor());
					Action jumpNow = new Action("Jump to end tag", CFPluginImages
							.getImageRegistry().getDescriptor(CFPluginImages.ICON_FORWARD)) {
						public void run() {
						    CFMLEditor.this.jumpAction.run(null);
						}
					};
					menu.add(jumpNow);
				}

				
				if (part.getTagName().equals("cfinclude") || part.getTagName().equals("cfmodule")) {
					//this is a bit hokey - there has to be a way to load the
					//action in the xml file then just call it here...
				    this.gfa.setActiveEditor(null, getSite().getPage().getActiveEditor());

					Action ack = new Action("Open/Create File", CFPluginImages
							.getImageRegistry().getDescriptor(CFPluginImages.ICON_IMPORT)) {
						public void run() {

						    CFMLEditor.this.gfa.run(null);
						}
					};
					menu.add(ack);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see IAdaptable#getAdapter(java.lang.Class)
	 * @since 2.0
	 */
	public Object getAdapter(Class required) {

		if (this.fProjectionSupport != null) {
			Object adapter = this.fProjectionSupport.getAdapter(getSourceViewer(),
					required);
			if (adapter != null)
				return adapter;
		}

		//if they ask for the outline page send our implementation
		if (required.getName().trim().equals(
				"org.eclipse.ui.views.contentoutline.IContentOutlinePage")) {
			try {
				return new CFContentOutlineView();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			return super.getAdapter(required);
			//return super.getAdapter(adapter);
		}
		
		return super.getAdapter(required);
		
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
					ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS, ' ', -1,
					SWT.CTRL);

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 *  Implementation copied from org.eclipse.ui.editors.text.TextEditor.
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell= getSite().getShell();
		IEditorInput input= getEditorInput();
		String RESOURCE_BUNDLE= "org.eclipse.ui.editors.text.TextEditorMessages";//$NON-NLS-1$

		ResourceBundle fgResourceBundle= ResourceBundle.getBundle(RESOURCE_BUNDLE);

		SaveAsDialog dialog= new SaveAsDialog(shell);
		
		IFile original= (input instanceof IFileEditorInput) ? ((IFileEditorInput) input).getFile() : null;
		if (original != null)
			dialog.setOriginalFile(original);
		
		dialog.create();
			
		IDocumentProvider provider= getDocumentProvider();
		if (provider == null) {
			// editor has programmatically been  closed while the dialog was open
			return;
		}
		
		if (provider.isDeleted(input) && original != null) {
			String message= MessageFormat.format(fgResourceBundle.getString("Editor.warning.save.delete"), new Object[] { original.getName() }); //$NON-NLS-1$
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}
			
		IPath filePath= dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}
			
		IWorkspace workspace= ResourcesPlugin.getWorkspace();
		IFile file= workspace.getRoot().getFile(filePath);
		final IEditorInput newInput= new FileEditorInput(file);
				
		boolean success= false;
		try {
			
			provider.aboutToChange(newInput);
			provider.saveDocument(progressMonitor, newInput, provider.getDocument(input), true);			
			success= true;
			
		} catch (CoreException x) {
			IStatus status= x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				String title= fgResourceBundle.getString("Editor.error.save.title"); //$NON-NLS-1$
				String msg= MessageFormat.format(fgResourceBundle.getString("Editor.error.save.message"), new Object[] { x.getMessage() }); //$NON-NLS-1$
				
				if (status != null) {
					switch (status.getSeverity()) {
						case IStatus.INFO:
							MessageDialog.openInformation(shell, title, msg);
						break;
						case IStatus.WARNING:
							MessageDialog.openWarning(shell, title, msg);
						break;
						default:
							MessageDialog.openError(shell, title, msg);
					}
				} else {
					MessageDialog.openError(shell, title, msg);
				}
			}
		} finally {
			provider.changed(newInput);
			if (success)
				setInput(newInput);
		}
		
		if (progressMonitor != null)
			progressMonitor.setCanceled(!success);
	}
	
	
	
	
	public void dispose() {
	    this.colorManager.dispose();
		if (this.cfmlBracketMatcher != null) {
			this.cfmlBracketMatcher.dispose();
		}
		CFMLPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(
				this);
		CFMLPlugin.getDefault().getLastActionManager().removeAction(this);
		super.dispose();
	}

	public void propertyChange(PropertyChangeEvent event) {
		handlePreferenceStoreChanged(event);
	}

	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		if (event.getProperty().equals(EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS)
				|| event.getProperty().equals(EditorPreferenceConstants.P_TAB_WIDTH)) {

			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer != null) {
				sourceViewer.getTextWidget().setTabs(
				        this.configuration.getTabWidth(sourceViewer));
			}
		}
		setBackgroundColor();

		super.handlePreferenceStoreChanged(event);
	}

	/**
	 * Set the background color of the editor window based on the user's
	 * preferences
	 */
	private void setBackgroundColor() {
		// Only try to set the background color when the source fViewer is
		// available
		if (this.getSourceViewer() != null
				&& this.getSourceViewer().getTextWidget() != null) {
			CFMLPreferenceManager manager = new CFMLPreferenceManager();
			// Set the background color of the editor
			this.getSourceViewer().getTextWidget().setBackground(
					new org.eclipse.swt.graphics.Color(Display.getCurrent(), manager
							.getColor(EditorPreferenceConstants.P_COLOR_BACKGROUND)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#configureSourceViewerDecorationSupport(org.eclipse.ui.texteditor.SourceViewerDecorationSupport)
	 */
	protected void configureSourceViewerDecorationSupport(
			SourceViewerDecorationSupport support) {

		//register the pair matcher
		support.setCharacterPairMatcher(this.cfmlBracketMatcher);

		//register the brackets and colors
		support.setMatchingCharacterPainterPreferenceKeys(
				EditorPreferenceConstants.P_BRACKET_MATCHING_ENABLED,
				EditorPreferenceConstants.P_BRACKET_MATCHING_COLOR);

		super.configureSourceViewerDecorationSupport(support);
		Iterator e = getAnnotationPreferences().getAnnotationPreferences()
				.iterator();
		while (e.hasNext()) {
			AnnotationPreference pref = (AnnotationPreference) e.next();
			support.setAnnotationPainterPreferenceKeys(pref.getAnnotationType(), pref
					.getColorPreferenceKey(), pref.getTextPreferenceKey(), pref
					.getOverviewRulerPreferenceKey(), 4);
		}
		support.install(this.getPreferenceStore());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#createSourceViewer(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.jface.text.source.IVerticalRuler, int)
	 */
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {

		ProjectionViewer viewer = new ProjectionViewer(parent, ruler,
				getOverviewRuler(), isOverviewRulerVisible(), styles);
		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);
		
		
		return viewer;

	}

	/**
	 * Returns the source fViewer decoration support.
	 * 
	 * @param fViewer
	 *          the fViewer for which to return a decoration support
	 * @return the source fViewer decoration support
	 */
	protected SourceViewerDecorationSupport getSourceViewerDecorationSupport(
			ISourceViewer viewer) {

		if (this.fSourceViewerDecorationSupport == null) {
			this.fSourceViewerDecorationSupport = new DecorationSupport(viewer,
					getOverviewRuler(), getAnnotationAccess(), getSharedColors());
			configureSourceViewerDecorationSupport(this.fSourceViewerDecorationSupport);
		}
		return this.fSourceViewerDecorationSupport;
	}

}