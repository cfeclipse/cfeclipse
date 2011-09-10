/*
 * Created on Feb 27, 2004
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
package org.cfeclipse.cfml.snippets.views.snips;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.cfeclipse.cfml.snippets.editors.actions.GenericEncloserAction;
import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.snippets.preferences.SnipExPreferenceConstants;
import org.cfeclipse.cfml.snippets.preferences.SnipExPreferencePage;
import org.cfeclipse.cfml.snippets.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.snippets.util.CFPluginImages;
import org.cfeclipse.cfml.snippets.wizards.snipex.SnippetToSnipExWizard;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.cfeclipse.snippet.snipex.Library;
import org.cfeclipse.snippet.snipex.SnipEx;
import org.cfeclipse.snippet.snipex.Snippet;

/**
 * @author Rob
 *
 * This is a more complex view of snips with a tree view. This is the main
 * view class
 * 
 * @see ViewPart
 * 
 * This class was influenced by the aricle:
 * How to use the JFace Tree Viewer
 * By Chris Grindstaff, Applied Reasoning (chrisg at appliedReasoning.com)
 * May 5, 2002
 */
public class SnipTreeView extends ViewPart 
	implements IPropertyChangeListener {
	public static final String ID_SNIPVIEWTREE = "org.cfeclipse.cfml.snippets.views.snips.sniptreeview";

	public static final String DREAMWEAVER_SNIP_TYPE = "Dreamweaver";
	public static final String HOMESITE_SNIP_TYPE = "Homesite";
	public static final String CFECLIPSE_SNIP_TYPE = "CFEclipse";
	public static final String UNKNOWN_SNIP_TYPE = "Unknown";
	
	public static final String DW_SNIP_EXT  = "csn";
	public static final String CFE_SNIP_EXT = "xml";
	
	/** the treeviewer control */
	protected TreeViewer treeViewer;
	protected Text text, preview;
	protected Label previewLabel;
	protected LabelProvider labelProvider;
		
	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static IPath snipBase;
	/** used as a proxy action to add snips to the editor */
	private static GenericEncloserAction tmpAction;
	/** Config file is used to load simple xml documents and get to
	 * simple items via DOM - not recommended for large documents
	 */
	//private static XMLConfigFile xmlconfile;
	private static SnipReader snipReader;
	private String snippetType;
	
	MenuManager menuMgr;
	
	protected Action insertAction, createFolderAction, createSnippetAction, editSnippetAction, refreshSnippetsAction, deleteSnippetAction, deleteFolderAction;
	
	protected Action refreshSnipEx;
	protected Action exportToSnipEx;
	protected Action openSnipExPage;
	
	/** the root directory */
	protected File root;
	
	private CFMLPropertyManager propertyManager;
	
	
	
	/**
	 * The constructor.
	 */
	public SnipTreeView() {
		super();
	
		
		propertyManager = SnippetPlugin.getDefault().getPropertyManager();
		// By default we want to use cfeclipse style snippets
		snippetType = CFECLIPSE_SNIP_TYPE;

		// This ensures that we are notified when the properties are saved
		SnippetPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		propertyManager.addPropertyChangeListener(this);
		snipBase = new Path(propertyManager.getSnippetsPath());
		

		if(tmpAction == null)
			tmpAction = new GenericEncloserAction();
		//if(xmlconfile == null)
		//	xmlconfile = new XMLConfigFile();
		if(snipReader == null)
			snipReader = new SnipReader();
	}

	/*
	 * @see IWorkbenchPart#createPartControl(Composite)
	 */
	public void createPartControl(Composite parent) 
	{
		//Create a grid layout object so the text and treeviewer
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
		
		
		
		//Create a "label" to display information in. I'm
		//using a text field instead of a lable so you can
		//copy-paste out of it.
		text = new Text(parent, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		// layout the text field above the treeviewer
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		text.setLayoutData(layoutData);
		
		
		//Create the tree viewer as a child of the composite parent
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new SnipTreeViewContentProvider(getRootInput()));
		labelProvider = new SnipTreeViewLabelProvider();
		treeViewer.setLabelProvider(labelProvider);
		
		treeViewer.setUseHashlookup(true);
		
		//layout the tree viewer below the text field
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		treeViewer.getControl().setLayoutData(layoutData);
		
		previewLabel = new Label(parent, SWT.WRAP);
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        previewLabel.setLayoutData(gridData);
        previewLabel.setText("Preview"); //$NON-NLS-1$
		
		
		preview = new Text(parent, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		// layout the text field above the treeviewer
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.heightHint = 100;
		layoutData.horizontalAlignment = GridData.FILL;
		preview.setLayoutData(layoutData);
		
		
		//Create menu, toolbars, filters
		createActions();
		createMenus();
		createToolbar();
		createContextMenu();
		hookListeners();
		
		treeViewer.setInput(getRootInput());
		//treeViewer.expandAll();
	}

	
	public static Object[] getSnipExURLs() {
		String[] prefKeys = {	SnipExPreferenceConstants.P_SNIPEX_URL1,
							SnipExPreferenceConstants.P_SNIPEX_URL2,
							SnipExPreferenceConstants.P_SNIPEX_URL3,
							SnipExPreferenceConstants.P_SNIPEX_URL4,
							SnipExPreferenceConstants.P_SNIPEX_URL5,
							SnipExPreferenceConstants.P_SNIPEX_URL6,
							SnipExPreferenceConstants.P_SNIPEX_URL7,
							SnipExPreferenceConstants.P_SNIPEX_URL8
						};
		
		Object[] snipex = new Object[0];
		
		CFMLPreferenceManager pm = SnippetPlugin.getDefault().getPreferenceManager();
		
		for(int i = 0; i < prefKeys.length; i++ ) {
			
			String url = pm.getStringPref( prefKeys[i] );
			if( url.trim().length() > 0 ) {
				try {
					Object[] temp = new Object[1];
					temp[0] = new SnipEx( new URL( url ) , false);
					snipex = appendArrays(snipex, temp);
				} catch(MalformedURLException e) {
					System.err.println("Snipex URL failed:"+e);
				} catch(Exception e) {
					System.err.println("Snipex failed to load:"+e);
				}
			}
		}
		
		return snipex;
	}

	public static Object[] appendArrays(Object[] array1, Object[] array2) {
		Object[] newArray = new Object[array1.length + array2.length];
		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);
		return newArray;
	}
	
	/*
	public void mouseEnter(MouseEvent e) {
	//System.out.println("Mouse entered viewer");
	}
	
	public void mouseHover(MouseEvent e) {
	//System.out.println("Mouse hovered over viewer");
	}
	
	public void mouseExit(MouseEvent e) {
	//System.out.println("Mouse exited viewer");
	}
	*/
	
	
	protected void hookListeners() 
	{
		//add a selection listener so we can look at the selected file and
		//get the help information out
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) 
			{
				// if the selection is empty clear the label
				if(event.getSelection().isEmpty()) 
				{
					text.setText("");
					preview.setText("");
					return;
				}
				
				if(event.getSelection() instanceof IStructuredSelection) 
				{
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					StringBuffer toShow = new StringBuffer("");
					StringBuffer toPreview = new StringBuffer("");
					
					Object element = selection.getFirstElement();
					// Clear text/preview
					text.setText("");
					preview.setText("");
					
					if(element instanceof File) {
						//IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
						File selectedfile = (File)selection.getFirstElement();
						
						if(selectedfile.isDirectory()){
							return;
						}
							
						//get the full path to the file
						String f = selectedfile.getAbsolutePath();
						
						try
						{
							
							snipReader.read(f);
							toShow.append(snipReader.getSnipDescription());
							toPreview.append(snipReader.getSnipStartBlock() + snipReader.getSnipEndBlock());
							
						}
						catch(Exception e)
						{
							e.printStackTrace(System.err);
						}
						
						preview.setText(toPreview.toString());
					} else if(element instanceof SnipEx) {
						SnipEx sx = (SnipEx)element;
						text.setText(sx.toString());
						preview.setText(sx.getSource().toString());
						
					} else if(element instanceof Library) {
						Library lib = (Library)element;
						text.setText(lib.getName());
						preview.setText(lib.getDescription());
						
					} else if(element instanceof Snippet) {
						Snippet snip = (Snippet)element;
						text.setText(snip.getDescription());
						preview.setText(snip.getStartText()+snip.getEndText());
						
					}
				}
			}
		});
		
		treeViewer.addDoubleClickListener(new SnipDoubleClickListener(this));
		
		/*
		try {
			this.getViewSite().getShell().addMouseTrackListener(this);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
		*/
	}
	
	/**
	 * creates all the default actions
	 */
	protected void createActions() 
	{
		
		
		openSnipExPage = new Action("Edit SnipEx Servers", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SNIPEX)){
			public void run(){
				   IPreferencePage page = new SnipExPreferencePage();
				   PreferenceManager mgr = new PreferenceManager();
				   IPreferenceNode node = new PreferenceNode("1", page);
				   mgr.addToRoot(node);
				   PreferenceDialog dialog = new PreferenceDialog(getSite().getShell(), mgr);
				   dialog.create();
				   dialog.setMessage(page.getTitle());
				   dialog.open();
				
			}
			
			
		};
		
		refreshSnipEx = new Action("Refresh SnipEx Server", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH)){
			public void run(){
				
				System.out.println("Refreshing the snipex server");
			}
		};
		
		
		insertAction = new Action(
			"Insert",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SNIP)
		){
			public void run() { 
				insertItem();
			}
		};
		insertAction.setToolTipText("Insert the selected snip into the document");
		
		createFolderAction = new Action(
			"Create Folder",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_PACKAGE)
		){
			public void run() { 
				createSnipFolder();
			}
		};
		createFolderAction.setToolTipText("Create a new snip package");
		
		createSnippetAction = new Action(
			"Create Snippet",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ADD)
		){
			public void run() { 
				createSnippet();
			}
		};
		createSnippetAction.setToolTipText("Create a new snip");
		
		editSnippetAction = new Action(
			"Edit Snippet",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_EDIT)
		){
			public void run() { 
				editSnippet();
			}
		};
		editSnippetAction.setToolTipText("Edit the selected snip");
		
		refreshSnippetsAction = new Action(
			"Refresh Snippets",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH)		
		) {
			public void run() {
				reloadSnippets();
			}
		};
		refreshSnippetsAction.setToolTipText("Refresh snip view");
		
		deleteSnippetAction = new Action(
			"Delete Snippet",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REMOVE)
		){
			public void run() {
				deleteSnippet();
			}
		};
		deleteSnippetAction.setToolTipText("Delete selected snip");
		
		deleteFolderAction = new Action(
			"Delete Folder",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_DELETE)
		) {
			public void run() {
				deleteSnipFolder();
			}
		};
		deleteFolderAction.setToolTipText("Delete selected snip package (must be empty)");
		
		
		exportToSnipEx = new Action(
				"Export to SnipEx server",
				CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SNIP_EXPORT)
		){
			public void run(){
				IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
				File selectedfile = (File)selection.getFirstElement();

				
				Shell shell = getSite().getShell();
				   SnippetToSnipExWizard wizard = new SnippetToSnipExWizard(selectedfile); //TODO: pass in the object we have selected
				   
				   WizardDialog dialog = new WizardDialog(shell, wizard);
				   int result = dialog.open();
				
				//Run the wizard
				
			}
		};
		exportToSnipEx.setToolTipText("Export the selected snippet to a SnipX Server");
	}
	
	/**
	 * creates all the menus
	 * This is here mosly because I have found Mac users dont like to right 
	 * click most of the time (ctrl+click actually)
	 */
	protected void createMenus() {
		IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		rootMenuManager.add(refreshSnippetsAction);
		rootMenuManager.add(insertAction);
		
		rootMenuManager.add(createSnippetAction);
		rootMenuManager.add(editSnippetAction);
		rootMenuManager.add(deleteSnippetAction);
		
		rootMenuManager.add(createFolderAction);
		rootMenuManager.add(deleteFolderAction);
		rootMenuManager.add(openSnipExPage);
	}
	
	/**
	 * Create context menu.
	 */
	private void createContextMenu() {
		// Create menu manager.
		menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(mgr);
			}
		});
		
		// Create menu.
		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		
		// Register menu for extension.
		getSite().registerContextMenu(menuMgr, treeViewer);
	}
	
	private void fillContextMenu(IMenuManager mgr) {
		File selectedFile = getSelectedFile();
		
		if(selectedFile.isDirectory()) {
			mgr.add(createFolderAction);
			mgr.add(createSnippetAction);
			String[] files = selectedFile.list();
			if (files.length == 0) {
				mgr.add(deleteFolderAction);
			}
			
		} else {
			mgr.add(insertAction);
			mgr.add(editSnippetAction);
			mgr.add(deleteSnippetAction);
			mgr.add(exportToSnipEx);
		}
		
		
		mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		//mgr.add(deleteItemAction);
		//mgr.add(new Separator());
		//mgr.add(selectAllAction);
	}
	
	/**
	 * creates the toolbars
	 */
	protected void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		//toolbarManager.add(createSnippetAction);
		//toolbarManager.add(createFolderAction);
		toolbarManager.add(refreshSnippetsAction);
		toolbarManager.add(insertAction);
		
		toolbarManager.add(createSnippetAction);
		toolbarManager.add(editSnippetAction);
		toolbarManager.add(deleteSnippetAction);
		
		toolbarManager.add(createFolderAction);
		toolbarManager.add(deleteFolderAction);
		
	}
	
	/**
	 * Gets the root directory used as the snips base
	 * @return the root directory
	 */
	public File getRootInput(){
		return snipBase.toFile();
	}
	
	/**
	 * Gets the selected item parses it, and adds the defined stuff to the
	 * editor
	 */
	protected void insertItem() 
	{
		//get a handle to the current editor and assign it to our temp action
		IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
		tmpAction.setActiveEditor(null,iep);
		
		Object element = null;
		String startBlock = null;
		String endBlock = null;
		
		if(treeViewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
			element = (Object)selection.getFirstElement();
		}
		
		if(element instanceof File) {
			File selectedfile = (File)element;
			if(selectedfile.isDirectory()) return;
			
			snipReader.read( selectedfile.getAbsolutePath() );
			
			try
			{	
				IFile activeFile = null;
				if (iep.getEditorInput() instanceof IFileEditorInput) {
					activeFile = ((IFileEditorInput) iep.getEditorInput()).getFile();
				}
				
				startBlock = SnipVarParser.parse(snipReader.getSnipStartBlock(),activeFile,this.getViewSite().getShell());
				endBlock = SnipVarParser.parse(snipReader.getSnipEndBlock(),activeFile,this.getViewSite().getShell());
	
			} catch(Exception e) {
				e.printStackTrace(System.err);
			}
		} else if(element instanceof Snippet) {
			
			IFile activeFile = null;
			if (iep.getEditorInput() instanceof IFileEditorInput) {
				activeFile = ((IFileEditorInput) iep.getEditorInput()).getFile();
			}
			
			Snippet snip = (Snippet)element;
			startBlock = SnipVarParser.parse(snip.getStartText(),activeFile,this.getViewSite().getShell());
			endBlock = SnipVarParser.parse(snip.getEndText(),activeFile,this.getViewSite().getShell());
		}
		
		
		if (startBlock != null && endBlock != null) {
		    tmpAction.setEnclosingStrings(startBlock,endBlock);
		    tmpAction.run(null);
		    
			//after the addition, they'll probably want to start typing
			iep.setFocus();
		}
	}

	
	/*
	 * Returns the currently selected file or the root directory if nothing is selected
	 */
	
	private File getSelectedFile() {
		File selectedfile = null;
		
		if(treeViewer.getSelection().isEmpty()) 
		{
			selectedfile = getRootInput();
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
			selectedfile = (File)selection.getFirstElement();
			treeViewer.setExpandedState(selection.getFirstElement(),true);

		}
		return selectedfile;
	}

	
	protected void reloadSnippets() {
		
		//Display a delete cache dialog
		MessageBox warndialog = new MessageBox(this.getViewSite().getShell(),SWT.ICON_WARNING | SWT.YES | SWT.NO);
		warndialog.setMessage("Also delete SnipEx cached snippets?");
		
		        int response = warndialog.open();
		        
		        
		        if (response == SWT.YES){
					String dir = SnippetPlugin.getDefault().getStateLocation().toString()+"/snipex";
					File cacheFolder = new File(dir);
					if(cacheFolder.exists() && cacheFolder.isDirectory()){
							String[] list = cacheFolder.list();
							for (int i = 0; i < list.length; i++) {
								File cacheFile = new File(dir + File.separatorChar +  list[i]);
									boolean delete = cacheFile.delete();
								System.out.println("deleting " + cacheFile + " " + delete);
								
							}
							
					}
				}
		snipBase = new Path(propertyManager.getSnippetsPath());		
		treeViewer.setInput(getRootInput());
	}
	
	
	/**
	 * Creates a new folder called below the currently active folder
	 * If no folder is currently active it creates the folder below the root.
	 * 
	 */
	
	protected void createSnipFolder() {
		File selectedfile = getSelectedFile();
		
		if(!selectedfile.isDirectory())  {
			selectedfile = selectedfile.getParentFile();
		}

		SnipWriter writer = new SnipWriter(selectedfile,snippetType,snipBase);
		SnipFolderDialog folderDialog = new SnipFolderDialog(this.getViewSite().getShell(),writer,this.treeViewer);
		folderDialog.open();

	}
	
	protected void deleteSnipFolder() {
		File selectedfile = getSelectedFile();
		
		if(!selectedfile.isDirectory())  {
			selectedfile = selectedfile.getParentFile();
		}

		//SnipWriter writer = new SnipWriter(selectedfile,snippetType,snipBase);
		MessageBox deleteDialog = new MessageBox(this.getViewSite().getShell(),SWT.YES | SWT.NO);
		
		if(selectedfile.listFiles().length > 0){
			MessageBox warndialog = new MessageBox(this.getViewSite().getShell(),SWT.ICON_WARNING);
			warndialog.setMessage("You must delete the snippets in this folder first");
		}
		else {
		
			deleteDialog.setMessage("Are you sure you want to delete this folder?");
			if (deleteDialog.open() == SWT.YES) {
				
				selectedfile.delete();
				reloadSnippets();
			}
		
		}

	}
	
	
	
	protected void createSnippet() {
		File selectedfile = getSelectedFile();

		if(!selectedfile.isDirectory())  {
			selectedfile = selectedfile.getParentFile();
		}
		snippetType = CFECLIPSE_SNIP_TYPE;
		SnipWriter writer = new SnipWriter(selectedfile,snippetType,snipBase);
		SnipFileDialog snippetDialog = new SnipFileDialog(this.getViewSite().getShell(),writer,this.treeViewer,"","","","","",false,"cfm");
		snippetDialog.open();

	}
	
	
	
	protected void deleteSnippet() {
		File selectedfile = getSelectedFile();

		if(selectedfile.isDirectory())  {
			return;
		}
		MessageBox deleteDialog = new MessageBox(this.getViewSite().getShell(),SWT.YES | SWT.NO);
		deleteDialog.setMessage("Are you sure you want to delete this snippet?");
		if (deleteDialog.open() == SWT.YES) {
			selectedfile.delete();
			reloadSnippets();
		}

	}
	
	
	protected void editSnippet() {
		File selectedfile = getSelectedFile();

		if(selectedfile.isDirectory())  {
			return;
		}
		
		File parentDirectory = selectedfile.getParentFile();

		String f = selectedfile.getAbsolutePath().toLowerCase();

		if (f.endsWith(SnipTreeView.DW_SNIP_EXT)){
			snippetType = DREAMWEAVER_SNIP_TYPE;
		}
		else {
			snippetType = CFECLIPSE_SNIP_TYPE;
		}
		
		snipReader.read(f);
		
		SnipKeyCombos keyCombos = new SnipKeyCombos();
		
		String filepath = selectedfile.getAbsolutePath().replaceAll("\\\\","/");
		String basePath = snipBase.toString();
		
		String relativePath = filepath.replaceFirst(basePath,"");

		String snippetName = selectedfile.getName().substring(0,selectedfile.getName().length()-4);
		String snippetKeyCombo = keyCombos.getSequence(relativePath);
		String snippetDescription = snipReader.getSnipDescription();
		String snippetStartText = snipReader.getSnipStartBlock();
		String snippetEndText = snipReader.getSnipEndBlock();
		boolean isTemplate = snipReader.isFileTemplate();
		String templateExtension = snipReader.getTemplateExtension();
		
		SnipWriter writer = new SnipWriter(parentDirectory,snippetType,snipBase);
		SnipFileDialog snippetDialog = new SnipFileDialog(this.getViewSite().getShell(),writer,this.treeViewer,snippetName,snippetKeyCombo,snippetDescription,snippetStartText,snippetEndText,isTemplate,templateExtension);
		snippetDialog.open();
		
	}
	
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event)
    {

    	if (event.getProperty().equals(CFMLPreferenceConstants.P_SNIPPETS_PATH)) {
			snipBase = new Path(event.getNewValue().toString());
    		treeViewer.setInput(getRootInput());    		    		    		
    	}
    }
	/*
	 * @see IWorkbenchPart#setFocus()
	 */
	public void setFocus(){;}
}

