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
package org.cfeclipse.cfml.editors;

//import org.apache.log4j.Logger;


//import java.util.Iterator;
import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.UrlViewer;
import org.cfeclipse.cfml.editors.actions.EditTagAction;
import org.cfeclipse.cfml.editors.actions.GenericEncloserAction;
import org.cfeclipse.cfml.editors.actions.GotoFileAction;
import org.cfeclipse.cfml.editors.actions.InsertGetAndSetAction;
import org.cfeclipse.cfml.editors.actions.JumpToDocPos;
import org.cfeclipse.cfml.editors.actions.JumpToMatchingTagAction;
import org.cfeclipse.cfml.editors.actions.LocateInFileSystemAction;
import org.cfeclipse.cfml.editors.actions.LocateInTreeAction;
import org.cfeclipse.cfml.editors.actions.RTrimAction;
import org.cfeclipse.cfml.editors.actions.RenameInFileAction;
import org.cfeclipse.cfml.editors.actions.TextEditorWordNavigationAction;
import org.cfeclipse.cfml.editors.codefolding.CodeFoldingSetter;
import org.cfeclipse.cfml.editors.decoration.DecorationSupport;
import org.cfeclipse.cfml.editors.dnd.CFEDragDropListener;
import org.cfeclipse.cfml.editors.dnd.SelectionCursorListener;
import org.cfeclipse.cfml.editors.indentstrategies.CFEIndentStrategy;
import org.cfeclipse.cfml.editors.pairs.CFMLPairMatcher;
import org.cfeclipse.cfml.editors.pairs.Pair;
import org.cfeclipse.cfml.editors.partitioner.CFEPartition;
import org.cfeclipse.cfml.editors.partitioner.CFEPartitioner;
import org.cfeclipse.cfml.editors.partitioner.PartitionTypes;
import org.cfeclipse.cfml.editors.partitioner.scanners.CFPartitionScanner;
import org.cfeclipse.cfml.editors.text.IReconcilingParticipant;
import org.cfeclipse.cfml.editors.ui.CFMLEditorToolbar;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.preferences.EditorPreferenceConstants;
import org.cfeclipse.cfml.preferences.ParserPreferenceConstants;
import org.cfeclipse.cfml.preferences.TextSelectionPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.contentoutline.CFContentOutlineView;
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
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.source.projection.IProjectionListener;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.dnd.IDragAndDropService;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.ITextEditorHelpContextIds;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.EditorManager;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.texteditor.rulers.IColumnSupport;
import org.eclipse.ui.texteditor.rulers.RulerColumnDescriptor;
import org.eclipse.ui.texteditor.rulers.RulerColumnRegistry;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;


/**
 * @author Rob
 * 
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
public class CFMLEditor extends TextEditor implements
IReconcilingParticipant, IProjectionListener, IPropertyChangeListener, IShowInSource {
	/*
	 * 
	 *Need to add mouse listeners
	 * private MouseListener fMouseListener;

        ...
        public void createPartControl(Composite parent) {
            super.createPartControl(parent);
            fMouseListener= new MouseAdaper() {
                <your implementation>
            });
            StyledText t= getSourceViewer().getTextWidget();
            t.addMouseListener(fMouseListener);
        }

        public void dispose() {
            if (fMouseListener != null) {
                <cleanup fMouseListener related resources>
                fMouseListener= null;
            }
            super.dispose();
        }
	 * 
	 */

	private boolean fInitialReconcile;
	private CFDocument fCFDocument;

	/**
	 * Returns the offset of the given source viewer's document that corresponds
	 * to the given widget offset or <code>-1</code> if there is no such offset.
	 *
	 * @param viewer the source viewer
	 * @param widgetOffset the widget offset
	 * @return the corresponding offset in the source viewer's document or <code>-1</code>
	 * @since 2.1
	 */
	public static final int getWidgetOffset2ModelOffset(ISourceViewer viewer, int widgetOffset) {
		return widgetOffset2ModelOffset(viewer, widgetOffset);
	}

	/**
	 * Returns the cf model for the current editor input of this editor.
	 * @return the cf model for this editor or <code>null</code>
	 */
	public CFDocument getCFModel() {
		if (fCFDocument == null) {
            IDocumentProvider provider= getDocumentProvider();
            if (provider instanceof CFDocumentProvider) {
                CFDocumentProvider documentProvider= (CFDocumentProvider) provider;
                fCFDocument= documentProvider.getCFDocument(getEditorInput());
            }
        }
		return fCFDocument;
	}	
	
	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.editors.text.IReconcilingParticipant#reconciled()
	 * 
	 * this runs a parse 500ms after user stops changing stuff
	 */
	public void reconciled() {
		if (fInitialReconcile) {
			updateForInitialReconcile();
		}
		
		SourceViewerConfiguration config= getSourceViewerConfiguration();
		if (config == null || getViewer() == null) {
			return; //editor has been disposed.
		}
		IAutoEditStrategy[] strategies= config.getAutoEditStrategies(getViewer(), null);
		for (int i = 0; i < strategies.length; i++) {
			IAutoEditStrategy strategy = strategies[i];
			if (strategy instanceof CFEIndentStrategy) {
				((CFEIndentStrategy)strategy).reconciled();
			}
		}
		if(getPreferenceStore().getBoolean(ParserPreferenceConstants.P_AUTO_RECONCILE)){			
			Shell shell= getSite().getShell();
			if (shell != null && !shell.isDisposed()) {
				shell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (getSite().getShell() == null || getSite().getShell().isDisposed()) {
							return;
						}
						ICFDocument document = (ICFDocument) getDocumentProvider().getDocument(getEditorInput());
						document.clearAllMarkers();
						(document).parseDocument();
					}
				});
			}
		}
	}

	private void updateForInitialReconcile() {
		IDocumentProvider provider=  getDocumentProvider();
		if (provider == null) {//disposed
			return;
		}
		if (getCFModel() == null) {
			return;
		}
        IDocument doc= provider.getDocument(getEditorInput());
        if (doc == null) {
            return; //disposed
        }
        Object lock= getLockObject(doc);
		//ensure to synchronize so that the AntModel is not nulled out underneath in the AntEditorDocumentProvider
		//when the editor/doc provider are disposed
        if (lock == null) {
            updateModelForInitialReconcile();
        } else {
            synchronized (lock) {
                updateModelForInitialReconcile();
            }
        }
	}
    
    private void updateModelForInitialReconcile() {
        CFDocument model= getCFModel();
        if (model == null) {
            return;
        }

        fInitialReconcile= false;
        //model.updateForInitialReconcile();
    }
    
    public Object getLockObject(IDocument doc) {
        Object lock= null;
        if (doc instanceof ISynchronizable) {
            lock= ((ISynchronizable) doc).getLockObject();
        } else {
            lock= getCFModel();
        }
        return lock;
    }

	public ShowInContext getShowInContext() {
		// TODO Auto-generated method stub
    //getEditorInput()
    	
    	ShowInContext context = new ShowInContext(getEditorInput(), getSelectionProvider().getSelection());
    	
		return context;
	}

	/**
     * Logger for this class
     */
    //private static final Logger logger = Logger.getLogger(CFMLEditor.class);
    //private final boolean DEBUG = DebugSettings.EDITORS;
    
	public static final String EDITOR_CONTEXT = "org.cfeclipse.cfml.cfmleditorcontext";
	public static final String EDITOR_HYPERLINKS_ENABLED = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_HYPERLINKS_ENABLED;
	
	public static final String ID = "org.cfeclipse.cfml.editors.CFMLEditor";
	
	private static final String TEMPLATE_PROPOSALS= "template_proposals_action"; //$NON-NLS-1$
	
	protected ColorManager colorManager;

	protected CFConfiguration configuration;

	protected CFMLPairMatcher cfmlBracketMatcher;

	protected GenericEncloserAction testAction;

	final GotoFileAction gfa = new GotoFileAction();

	final JumpToDocPos jumpAction = new JumpToDocPos();

	private CodeFoldingSetter foldingSetter;


	/**
	 * The change ruler column.
	 */

	boolean fIsChangeInformationShown;

	private ProjectionSupport fProjectionSupport;

	private DragSource dragsource;
	private Object columnSupport;
	private CFContentOutlineView cfcontentOutlineView;
	private SelectionCursorListener SelectionCursorListener;
	private boolean isMarkOccurrenceEnabled;

	

	/**
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		//TODO: On save parsing should apparently go into a builder.

		// Trim trailing spaces if the option is turned on
		if (getPreferenceStore().getBoolean(EditorPreferenceConstants.P_RTRIM_ON_SAVE)) {

			// Perform Trim Trailing Spaces
			RTrimAction trimAction = new RTrimAction();
			trimAction.setActiveEditor(null, getSite().getPage().getActiveEditor());
			trimAction.run(null);

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
				
	}

	public StyledText getTextWidget() {
		return this.getSourceViewer().getTextWidget();
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
		IPreferenceStore generalTextStore= EditorsUI.getPreferenceStore();
		IPreferenceStore cfmlStore = CFMLPlugin.getDefault().getPreferenceStore();
				
		IPreferenceStore combinedPreferenceStore= new ChainedPreferenceStore(new 
		IPreferenceStore[] { cfmlStore,generalTextStore  });		
		setPreferenceStore(combinedPreferenceStore);
		
		// This ensures that we are notified when the preferences are saved
		CFMLPlugin.getDefault().getPreferenceStore()
				.addPropertyChangeListener(this);
		setEditorContextMenuId("#CFMLEditorContext"); //$NON-NLS-1$
		setRulerContextMenuId("#TextRulerContext"); //$NON-NLS-1$
		setHelpContextId(ITextEditorHelpContextIds.TEXT_EDITOR);
		configureInsertMode(SMART_INSERT, false);
		setInsertMode(INSERT);	
		isMarkOccurrenceEnabled= getPreferenceStore().getBoolean(TextSelectionPreferenceConstants.P_MARK_OCCURRENCES);
	}
	
	


	public void createPartControl(Composite parent) {
	    
		
		
		/* TODO: hook this up to a button
		 * Check the preferences, and add a toolbar */
		if(getPreferenceStore().getBoolean(EditorPreferenceConstants.P_SHOW_EDITOR_TOOLBAR)){
			CFMLEditorToolbar editorWithToolbar = new CFMLEditorToolbar();
			parent = editorWithToolbar.getTabs(parent);
		}
		
		super.createPartControl(parent);
		IKeyBindingService service = this.getSite().getKeyBindingService();
		service.setScopes(new String[]{EDITOR_CONTEXT});
		this.setBackgroundColor();
//		this.fSourceViewerDecorationSupport.install(getPreferenceStore());

		ProjectionViewer projectionViewer = (ProjectionViewer) getSourceViewer();

		this.fProjectionSupport = new ProjectionSupport(projectionViewer,
				getAnnotationAccess(), getSharedColors());
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.task");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.cfeclipse.cfml.parserProblemAnnotation");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.cfeclipse.cfml.parserWarningAnnotation");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning");
		this.fProjectionSupport
				.addSummarizableAnnotationType("org.cfeclipse.cfml.occurrenceAnnotation");
		
		this.fProjectionSupport.setHoverControlCreator(new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell shell) {

                IInformationControl returnIInformationControl = new DefaultInformationControl(
                        shell);
				return returnIInformationControl;
			}
		});
		this.fProjectionSupport.install();
		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(getSourceViewer());
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
		

		try {
			if (isEditorInputReadOnly()) {				
			
				if (getPreferenceStore().getBoolean(
						EditorPreferenceConstants.P_WARN_READ_ONLY_FILES)
						&& !getPreferenceStore().getBoolean("MAKE_WRITABLE")) {
					
					String[] labels = new String[2];
					labels[0] = "OK";
					labels[1] = "Make writable";
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
					} else {
						if (msg.getToggleState()) {
							// Don't show warning in future.
							getPreferenceStore().setValue("MAKE_WRITABLE", true);
						}
						setReadOnly(false);
					}
				} else if (getPreferenceStore().getBoolean("MAKE_WRITABLE")){
					setReadOnly(false);					
				}
			}
			

			//if (this.DEBUG) {
			    //IWorkbenchPage p = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			    //System.out.println(p);
			//}
			
		} catch (Exception e) {
            

			e.printStackTrace();
			
		}
		
		setStatusLine();
		setupSelectionListeners(projectionViewer);
		
		
	}
	
	protected void setReadOnly(boolean mode) {
		IEditorInput input = getEditorInput();
		String fileName = input.getName();
		File file = new File(fileName);
		
		if (input instanceof CFJavaFileEditorInput) {
			CFJavaFileEditorInput tmp;			
			tmp = (CFJavaFileEditorInput) input;
			IPath path = tmp.getPath();
			file = path.toFile();
			if (!file.exists()) {
				MessageDialog.openError(
						getEditorSite().getShell(), "Error", "Cannot fetch " + fileName + " for attributes change.");					
			} else {
								
				try {
					String command = "";					
					String osName = System.getProperty("os.name");					
					
					if (osName.indexOf("Windows") > -1) {
						if (mode)
							command = "attrib +r " + path.toOSString();
						else 
							command = "attrib -r " + path.toOSString();							
					} else if (osName.indexOf("Linux") > -1) {
						if (mode)
							command = "chmod 444 " + path.toOSString();
						else 
							command = "chmod 644 " + path.toOSString();							
					}
					
					Runtime rt = Runtime.getRuntime();
					rt.exec(command);
					setEditorInputModifiable();
				} catch (Exception e) {
					MessageDialog.openError(
							getEditorSite().getShell(), "Error", "Unable to set read/write permission of file " + fileName);					
				}
				
				
			}			
		}		
	}
	/**
	 * Allow user to make change to current open document.
	 *
	 */
	protected void setEditorInputModifiable() {
		IDocumentProvider provider= getDocumentProvider();
		if (provider instanceof IDocumentProviderExtension) {
			IDocumentProviderExtension extension= (IDocumentProviderExtension) provider;
			extension.setCanSaveDocument(getEditorInput());
		}		
	}
	
	private void partActivated(){
		System.out.println("I am activated" + this.getPartName());
	}
	public void setStatusLine(){
		
	/*	try{
		//		Sets the current file path to the status line
		IEditorInput input= getEditorInput();
		
			if (getEditorInput() instanceof RemoteFileEditorInput) {
				RemoteFileEditorInput rfd = (RemoteFileEditorInput) getEditorInput();
				getEditorSite().getActionBars().getStatusLineManager().setMessage(rfd.getName());
			}
			else{
				IFile original= (input instanceof IFileEditorInput) ? ((IFileEditorInput) input).getFile() : null;
				getEditorSite().getActionBars().getStatusLineManager().setMessage(original.getLocation().toString());
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}*/
		
		
		
		try{
			//From Dean Harmon @ Adobe, to work with the RDS plugin
            //          Sets the current file path to the status line

            IEditorInput input= getEditorInput();
            IFile original= (input instanceof IFileEditorInput) ? ((IFileEditorInput) input).getFile() : null;
            String message;
            if (original != null)

            {
                  message = original.getLocation().toString();
            } else {
                  message = input.getToolTipText();
            }

            getEditorSite().getActionBars().getStatusLineManager().setMessage(message);

            //this.getEditorSite().getWorkbenchWindow().getShell().setToolTipText(original.getLocation().toString());

            }

            catch (Exception e){

                  System.err.println(e);

            }
		
		
	}

	
	/**
	 * Initializes the drag and drop support for the given viewer based on
	 * provided editor adapter for drop target listeners.
	 *
	 * @param viewer the viewer
	 * @since 3.0
	 */
	@Override
	protected void initializeDragAndDrop(ISourceViewer viewer) {
		// TODO Auto-generated method stub
		StyledText tw = getSourceViewer().getTextWidget();
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getTransfer(),TextTransfer.getInstance(),ResourceTransfer.getInstance(),FileTransfer.getInstance() };
		//installTextDragAndDrop(getSourceViewer());
		CFEDragDropListener ddListener = new CFEDragDropListener(this,
				(ProjectionViewer) this.getSourceViewer(), SelectionCursorListener);
		IDragAndDropService dtSvc = (IDragAndDropService) getSite().getService(IDragAndDropService.class);
		dtSvc.addMergedDropTarget(tw, operations, transfers, ddListener);
		final DragSource source= new DragSource(tw, DND.DROP_COPY | DND.DROP_MOVE);
		source.setTransfer(new Transfer[] {TextTransfer.getInstance()});
		source.addDragListener(ddListener);			
	}
	
	/**
	 * sets up seleciton listeners, like the TextSelection listener, and DND (eventually)
	 *
	 */

	private void setupSelectionListeners(ProjectionViewer projectionViewer) {

		// this is implemented by default in Eclipse 3.3, so we just exit out
		// we do this by checking for JavaFileEditorInput, which 3.3 removed
		Class c = null;
		try
		{
			c = Class.forName("org.eclipse.ui.internal.editors.text.JavaFileEditorInput");
		} catch (ClassNotFoundException cnfe)
		{
			//nothing, we are already null;
		}

		if (c == null) // ie we are on 3.3 or higher
		{
			setSelectionCursorListener();
			if (isMarkOccurrenceEnabled) {
				SelectionCursorListener.installOccurrencesFinder();
			}

			return;
		}
		
		setSelectionCursorListener();

		/*
		 * moved to initializeDragAndDrop but not tested on <3.3
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
				(ProjectionViewer) this.getSourceViewer(), SelectionCursorListener);

		this.dragsource.addDragListener(ddListener);

		target.addDropListener(ddListener);
		*/

	}

		protected void setSelectionCursorListener() {
			String[] wordChars = {
					getPreferenceStore().getString(TextSelectionPreferenceConstants.P_PART_OF_WORD_CHARS),
					getPreferenceStore().getString(TextSelectionPreferenceConstants.P_BREAK_WORD_CHARS),
					getPreferenceStore().getString(TextSelectionPreferenceConstants.P_PART_OF_WORD_CHARS_ALT),
					getPreferenceStore().getString(TextSelectionPreferenceConstants.P_BREAK_WORD_CHARS_ALT),
					getPreferenceStore().getString(TextSelectionPreferenceConstants.P_PART_OF_WORD_CHARS_SHIFT),
					getPreferenceStore().getString(TextSelectionPreferenceConstants.P_BREAK_WORD_CHARS_SHIFT)
			};
			if (SelectionCursorListener != null) {
				SelectionCursorListener.setWordSelectionChars(wordChars);
			} else {
				ProjectionViewer projectionViewer = (ProjectionViewer)getSourceViewer();
				SelectionCursorListener = new SelectionCursorListener(this, projectionViewer, wordChars);
				//projectionViewer.addSelectionChangedListener(SelectionCursorListener);
				//projectionViewer.getTextWidget().addKeyListener(SelectionCursorListener);				
				SelectionCursorListener.setWordSelectionChars(wordChars);
				projectionViewer.addPostSelectionChangedListener(SelectionCursorListener);
				projectionViewer.getTextWidget().addMouseListener(SelectionCursorListener);									
			}
		}			
		public SelectionCursorListener getSelectionCursorListener() {
				if (SelectionCursorListener == null) {
					setSelectionCursorListener();
				}
				return SelectionCursorListener;
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
	
	
	
	private CFEPartition getPartitionAtCursor(){
		final IEditorPart iep = getSite().getPage().getActiveEditor();
		final ITextEditor editor = (ITextEditor) iep;
		final IDocument doc = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());

		final ICFDocument cfd = (ICFDocument) doc;
		final ITextSelection sel = (ITextSelection) editor.getSelectionProvider()
				.getSelection();
		int startpos = sel.getOffset();
		int len = Math.max(sel.getLength(),1);
		CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
		
		CFEPartition part = partitioner.findClosestPartition(startpos);
		return part;
	}

	/**
	 * Add menu items based on the tag that was right clicked on... doesnt work as
	 * I have no idea how to find out what tag was just clicked on :) seems like
	 * perhaps the CFDocument could know...
	 * 
	 * @param menu
	 */
	protected void addTagSpecificMenuItems(IMenuManager menu) {
		
		//Find out which version of Eclipse we are running in:
		boolean inEclipse32 = false;
		final String version = System.getProperty("osgi.framework.version"); //$NON-NLS-1$
		   if (version != null && version.startsWith("3.2")) //$NON-NLS-1$
		   {
		       inEclipse32 = true;
		   }
		

		
		
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
					new CFDocumentSetupParticipant().setup(doc);				  
				   } catch (Exception e) {
				       e.printStackTrace();
				   }
				}
			};
			menu.add(act);

			//Add programatically the locate in File Explorer and navigator for Eclipse 3.1 users
			
			if(!inEclipse32){
				
				act = new Action("Show in File Explorer", null){
					public void run(){
						LocateInFileSystemAction action = new LocateInFileSystemAction();
						action.run(null);
					}
				};
					menu.add(act);
					
					
				act = new Action("Show in Navigator", null){
					public void run(){
						LocateInTreeAction action = new LocateInTreeAction();
						action.run(null);
					}
				};
					menu.add(act);
				
				
			}
			
			
			
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
			 * TODO: re-write this so the edit this tag action can be called from different places
			 * Edit this tag action start
			 */
			act = new Action("Edit this tag", null){
				public void run() {
				
						EditTagAction eta = new EditTagAction();
						eta.run();
					 
				}
			};
			
			//Only display if you are at the start tag
			int startpos = sel.getOffset();
			CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
			CFEPartition part = partitioner.findClosestPartition(startpos);
//			ITypedRegion part = cfd.getDocumentPartitioner(CFDocumentSetupParticipant.CFML_PARTITIONING).getPartition(startpos);
			
			if(part != null && EditableTags.isEditable(part.getType())){
				menu.add(act);
			}
			
			
			//This is not only for
			act = new Action("Genrate Getters and Setters", null){
					public void run(){
						InsertGetAndSetAction insertGetSet = new InsertGetAndSetAction();
						insertGetSet.setActiveEditor(null, getSite().getPage().getActiveEditor());
						insertGetSet.run(null);
					}
				};
			
			/*	TODO: Setup the Generate Getters and Setters, 
			 * 	This might actually go into the suggest stuff
			 *	Add More checks:
			 *		1) If we are in a CFC
			 *		2) If the cursor is in a cfproperty tag
			 *		3) or if we are in a cfset tag who's parent tag is a cfcomponent tag  
			 */	
			
			if(getSelectionCursorListener().getSelectedTag() != null && 
					getSelectionCursorListener().getSelectedTag().getName().equalsIgnoreCase("cfproperty")){
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

				System.out.println(part);
				// this is a bit hokey - there has to be a way to load the
				// action in the xml file then just call it here...
				if (getSelectionCursorListener().getSelectedTag() != null
						&& (getSelectionCursorListener().getSelectedTag().getName().equalsIgnoreCase("cfinclude") 
								|| getSelectionCursorListener().getSelectedTag().getName().equalsIgnoreCase("cfmodule"))) {
					this.gfa.setActiveEditor(null, getSite().getPage().getActiveEditor());

					Action ack = new Action("Open/Create File", CFPluginImages.getImageRegistry().getDescriptor(
							CFPluginImages.ICON_IMPORT)) {
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
				if(cfcontentOutlineView == null) {	
					cfcontentOutlineView = new CFContentOutlineView();
				}
				return cfcontentOutlineView;
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			return super.getAdapter(required);
			//return super.getAdapter(adapter);
		}
		
		return super.getAdapter(required);
		
	}

    /**
     * Returns the viewer associated with this editor
     * @return The viewer associated with this editor
     */
    public ISourceViewer getViewer() {
        return getSourceViewer();
    }

    public void createActions() {
		super.createActions();

		//this sets up the ctrl+space code insight stuff
		try {
			/*
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
*/

			IAction action = new TextOperationAction(CFMLPlugin.getDefault()
					.getResourceBundle(), "ContentFormat.", this, ISourceViewer.FORMAT); //$NON-NLS-1$
			action.setActionDefinitionId(CFMLPlugin.PLUGIN_ID + ".FormatAction");
			action.setEnabled(true);
			// for some reason this isn't getting picked up from the RB above.  #517
			action.setText("Format Source");
			markAsStateDependentAction("ContentFormat", true); //$NON-NLS-1$
			markAsSelectionDependentAction("ContentFormat", true); //$NON-NLS-1$
			setAction("ContentFormat", action); //$NON-NLS-1$			
			
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
		//remove the dnd listener
		final IDragAndDropService dndService= (IDragAndDropService)getSite().getService(IDragAndDropService.class);
		if (dndService == null)
			return;

		StyledText st= getSourceViewer().getTextWidget();
		dndService.removeMergedDropTarget(st);

		DragSource dragSource= (DragSource)st.getData(DND.DRAG_SOURCE_KEY);
		if (dragSource != null) {
			dragSource.dispose();
			st.setData(DND.DRAG_SOURCE_KEY, null);
		}
		//Remove the listener
		if(SelectionCursorListener!=null){			
			SelectionCursorListener.uninstallOccurrencesFinder();
		}
		fCFDocument = null;
		
		super.dispose();
	}

	public void propertyChange(PropertyChangeEvent event) {
		handlePreferenceStoreChanged(event);
		System.out.println(event);
		if(getViewer()!= null){
			setStatusLine();
			setSelectionCursorListener();			
			isMarkOccurrenceEnabled= getPreferenceStore().getBoolean(TextSelectionPreferenceConstants.P_MARK_OCCURRENCES);
		}
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
// switched to using the preference lookup within DecorationSupport		
//		Iterator e = getAnnotationPreferences().getAnnotationPreferenceFragments()
//				.iterator();
//		while (e.hasNext()) {
//			AnnotationPreference pref = (AnnotationPreference) e.next();
//			support.setAnnotationPainterPreferenceKeys(pref.getAnnotationType(), pref
//					.getColorPreferenceKey(), pref.getTextPreferenceKey(), pref
//					.getOverviewRulerPreferenceKey(), 4);
//		}
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
	
		/*
		 * TODO: ADD hyperlink support
		 * 
		 * 
		 */
		CFHyperlinkDetector[] detectors = new CFHyperlinkDetector[1];
		
		
		CFHyperlinkDetector cfhd = new CFHyperlinkDetector();
		
		detectors[0] = cfhd;
		viewer.setHyperlinkDetectors(detectors, SWT.CONTROL);
		
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

	public boolean isMarkingOccurrences() {
		return isMarkOccurrenceEnabled;
	}

	public void projectionDisabled() {
		// TODO Auto-generated method stub
		
	}


	public void projectionEnabled() {
		// TODO Auto-generated method stub
		System.out.println("wee");
	}

	/**
	 * The editor has entered or exited linked mode.
	 * @param inLinkedMode whether an enter or exit has occurred
	 * @param effectsOccurrences whether to change the state of the occurrences finder
	 */
	public void setInLinkedMode(boolean inLinkedMode, boolean effectsOccurrences) {
		if (inLinkedMode) {
			getCFModel().setShouldReconcile(false);
			if (effectsOccurrences) {
				SelectionCursorListener.uninstallOccurrencesFinder();
			}
		} else {
			getCFModel().setShouldReconcile(true);
			if (effectsOccurrences) {
				SelectionCursorListener.installOccurrencesFinder();
			}
		}
	}

	
	
	public void openReferenceElement() {
		ISelection selection= getSelectionProvider().getSelection();
		Object target= null;
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection= (ITextSelection)selection;
			ISourceViewer viewer= getSourceViewer();
			int textOffset= textSelection.getOffset();
			//IRegion region= XMLTextHover.getRegion(viewer, textOffset);
			//target= findTarget(region);
		}
		
		//openTarget(target);
	}	
	
//	someday we'll use this
//	public void saveState(IMemento memento) {
//		System.out.println("ColdFusion Rocks!");
//	}
//	
//	public void restoreState(IMemento memento) {
//		System.out.println("ColdFusion Rolls!");
//	}
}