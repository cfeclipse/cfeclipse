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
package com.rohanclan.cfml.views.snips;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IEditorPart;
//import com.rohanclan.cfml.util.XMLConfigFile;
import org.eclipse.core.runtime.IPath;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.properties.CFMLPropertyManager;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;
import java.io.File;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.core.resources.IFile;

import org.eclipse.ui.IFileEditorInput;

import org.eclipse.core.runtime.Path;

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
	public static final String ID_SNIPVIEWTREE = "com.rohanclan.cfml.views.snips.sniptreeview";

	public static final String DREAMWEAVER_SNIP_TYPE = "Dreamweaver";
	public static final String HOMESITE_SNIP_TYPE = "Homesite";
	public static final String CFECLIPSE_SNIP_TYPE = "CFEclipse";
	public static final String UNKNOWN_SNIP_TYPE = "Unknown";
	
	/** the treeviewer control */
	protected TreeViewer treeViewer;
	protected Text text;
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
	
	/** the root directory */
	protected File root;
	
	private CFMLPropertyManager propertyManager;
	
	/**
	 * The constructor.
	 */
	public SnipTreeView() {
		super();
		
		
		propertyManager = new CFMLPropertyManager();
		// By default we want to use cfeclipse style snippets
		snippetType = CFECLIPSE_SNIP_TYPE;

		// This ensures that we are notified when the properties are saved
		CFMLPlugin.getDefault().getPropertyStore().addPropertyChangeListener(this);
		try 
		{
			//snipBase = CFMLPlugin.getDefault().getStateLocation();
			snipBase = new Path(propertyManager.snippetsPath());
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}

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
		
		//Create menu, toolbars, filters
		createActions();
		createMenus();
		createToolbar();
		createContextMenu();
		hookListeners();
		
		treeViewer.setInput(getRootInput());
		//treeViewer.expandAll();
	}

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
					return;
				}
				
				if(event.getSelection() instanceof IStructuredSelection) 
				{
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					StringBuffer toShow = new StringBuffer("");
					
					//IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
					File selectedfile = (File)selection.getFirstElement();
					
					if(selectedfile.isDirectory()) return;
						
					//get the full path to the file
					String f = selectedfile.getAbsolutePath();
					
					try
					{
						
						snipReader.read(f);
						toShow.append(snipReader.getSnipDescription());
						
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
					}
					text.setText(toShow.toString());
				}
			}
		});

		treeViewer.addDoubleClickListener(new SnipDoubleClickListener(this));
	}
	
	/**
	 * creates all the default actions
	 */
	protected void createActions() 
	{
		insertAction = new Action("Insert"){
			public void run() { 
				insertItem();
			}
		};
		createFolderAction = new Action("Create Folder"){
			public void run() { 
				createSnipFolder();
			}
		};
		createSnippetAction = new Action("Create Snippet"){
			public void run() { 
				createSnippet();
			}
		};
		editSnippetAction = new Action("Edit Snippet"){
			public void run() { 
				editSnippet();
			}
		};
		refreshSnippetsAction = new Action("Refresh Snippets") {
			public void run() {
				reloadSnippets();
			}
			
		};
		deleteSnippetAction = new Action("Delete Snippet") {
			public void run() {
				deleteSnippet();
			}
			
		};
		deleteFolderAction = new Action("Delete Folder") {
			public void run() {
				deleteSnipFolder();
			}
			
		};
		
		
	}
	
	
	
	
	/**
	 * creates all the menus
	 */
	protected void createMenus() {
		IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		//rootMenuManager.add(refreshSnippetsAction);
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

		
		if (selectedFile.isDirectory()) {
			mgr.add(createFolderAction);
			mgr.add(createSnippetAction);
			String[] files = selectedFile.list();
			if (files.length == 0) {
				mgr.add(deleteFolderAction);
			}
			
		}
		else {
			mgr.add(insertAction);
			mgr.add(editSnippetAction);
			mgr.add(deleteSnippetAction);
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
		toolbarManager.add(refreshSnippetsAction);
		//toolbarManager.add(createSnippetAction);
		//toolbarManager.add(createFolderAction);
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
		File selectedfile = null;
		
		
		if(treeViewer.getSelection().isEmpty()) 
		{
			return;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
			selectedfile = (File)selection.getFirstElement();
		}
		
		if(selectedfile.isDirectory()) return;
		
		//get the full path to the file
		String f = selectedfile.getAbsolutePath();
		
		snipReader.read(f);
		//System.out.println(f);
		
		try
		{
			//try to load up the xml file
			//xmlconfile.setFileName(f);
			//xmlconfile.openFile();
			
			IFile activeFile = null;
			if (iep.getEditorInput() instanceof IFileEditorInput) {
				activeFile = ((IFileEditorInput) iep.getEditorInput()).getFile();
			}
			
			tmpAction.setEnclosingStrings(
					SnippetVarParser.parse(snipReader.getSnipStartBlock(),activeFile),
					SnippetVarParser.parse(snipReader.getSnipEndBlock(),activeFile)	
			);
			
			
			tmpAction.run(null);
			
		}catch(Exception e)
		{
			e.printStackTrace(System.err);
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

		SnipWriter writer = new SnipWriter(selectedfile,snippetType);
		SnipFolderDialog folderDialog = new SnipFolderDialog(this.getViewSite().getShell(),writer,this.treeViewer);
		folderDialog.open();

	}
	
	protected void deleteSnipFolder() {
		File selectedfile = getSelectedFile();
		
		if(!selectedfile.isDirectory())  {
			selectedfile = selectedfile.getParentFile();
		}

		SnipWriter writer = new SnipWriter(selectedfile,snippetType);
		MessageBox deleteDialog = new MessageBox(this.getViewSite().getShell(),SWT.YES | SWT.NO);
		deleteDialog.setMessage("Are you sure you want to delete this folder?");
		if (deleteDialog.open() == SWT.YES) {
			selectedfile.delete();
			reloadSnippets();
		}

	}
	
	
	
	protected void createSnippet() {
		File selectedfile = getSelectedFile();

		if(!selectedfile.isDirectory())  {
			selectedfile = selectedfile.getParentFile();
		}
		snippetType = CFECLIPSE_SNIP_TYPE;
		SnipWriter writer = new SnipWriter(selectedfile,snippetType);
		SnipFileDialog snippetDialog = new SnipFileDialog(this.getViewSite().getShell(),writer,this.treeViewer,"","","","");
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

		String f = selectedfile.getAbsolutePath();

		if (f.endsWith(".csn") || f.endsWith(".CSN")){
			snippetType = DREAMWEAVER_SNIP_TYPE;
		}
		else {
			snippetType = CFECLIPSE_SNIP_TYPE;
		}
		
		snipReader.read(f);
		
		String snippetName = selectedfile.getName().substring(0,selectedfile.getName().length()-4);
		String snippetDescription = snipReader.getSnipDescription();
		String snippetStartText = snipReader.getSnipStartBlock();
		String snippetEndText = snipReader.getSnipEndBlock();
		
		SnipWriter writer = new SnipWriter(parentDirectory,snippetType);
		SnipFileDialog snippetDialog = new SnipFileDialog(this.getViewSite().getShell(),writer,this.treeViewer,snippetName,snippetDescription,snippetStartText,snippetEndText);
		snippetDialog.open();
		
	}
	
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event)
    {

    	if (event.getProperty().equals(ICFMLPreferenceConstants.P_SNIPPETS_PATH)) {
    		snipBase = new Path(propertyManager.snippetsPath());
    		treeViewer.setInput(getRootInput());
    	}
    }
	/*
	 * @see IWorkbenchPart#setFocus()
	 */
	public void setFocus(){;}
}

