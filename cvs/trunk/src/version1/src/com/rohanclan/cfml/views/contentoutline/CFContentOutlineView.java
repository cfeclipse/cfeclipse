/*
 * Created on Apr 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.contentoutline;

//import org.eclipse.jface.viewers.TreeViewer;
//import org.eclipse.swt.widgets.Control;

/* import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent; */
import org.eclipse.jface.viewers.TreeViewer;
//import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.LabelProvider;
/* import org.eclipse.jface.action.GroupMarker;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IEditorPart;
import com.rohanclan.cfml.util.XMLConfigFile;
import org.eclipse.core.runtime.IPath;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;
import java.io.File;
*/

import org.eclipse.ui.IEditorPart;
//import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.parser.*;
import java.util.*;

import org.eclipse.jface.viewers.IStructuredSelection;
//import org.eclipse.ui.IFileEditorInput;
//import org.eclipse.jface.text.IDocument;
//import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.util.CFPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.swt.widgets.Menu;
//import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;

/**
 * @author Rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFContentOutlineView extends ViewPart {
	public static final String ID_SNIPVIEWTREE = "com.rohanclan.cfml.views.contentoutline.cfcontentoutlineview";
	
	/** the treeviewer control */
	protected TreeViewer treeViewer;
	//protected Text text;
	protected LabelProvider labelProvider;
		
	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	//protected static IPath snipBase;
	/** used as a proxy action to add snips to the editor */
	//private static GenericEncloserAction tmpAction;
	/** Config file is used to load simple xml documents and get to
	 * simple items via DOM - not recommended for large documents
	 */
	//private static XMLConfigFile xmlconfile;
	
	protected Action jumpAction, refreshAction, expandAction; //addItemAction; //, deleteItemAction, selectAllAction;
	
	protected MenuManager menuMgr;
	
	/** the root directory */
	//protected File root;
	
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
		//text = new Text(parent, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		// layout the text field above the treeviewer
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		//text.setLayoutData(layoutData);
		
		//Create the tree viewer as a child of the composite parent
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(
			new OutlineContentProvider(getRootInput())
		);
		labelProvider = new OutlineLabelProvider();
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
	
	protected void hookListeners(){;}
	protected void createMenus(){
		//IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		//rootMenuManager.add(refreshSnippetsAction);
	}
	
	private void createContextMenu(){
		//Create menu manager.
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
		mgr.add(jumpAction);
	}
	
	
	protected void createToolbar(){
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(jumpAction);
		toolbarManager.add(expandAction);
		toolbarManager.add(refreshAction);
	}
	
	/**
	 * Gets the root element of the document
	 * @return the root directory
	 */
	public DocItem getRootInput()
	{
		try
		{
			//get a handle to the current editor and assign it to our temp action
			//IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			//ITextEditor ite = (ITextEditor)iep; //.getEditorInput();
			//ICFDocument icfd = (ICFDocument)ite.getDocumentProvider().getDocument(iep.getEditorInput());
			
			//icfd.parseDocument();
			//DocItem docRoot = icfd.getParser().getParseResult().getDocumentRoot();
			//DocItem docRoot = icfd.getParser().getParseResult().getDocumentRoot();
			
			//System.out.println("Root element is: " + docRoot.getName());
//			get a handle to the current editor and assign it to our temp action
			IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			//((FileEditorInput)iep.getEditorInput()).getFile();
			ITextEditor ite = (ITextEditor)iep; //.getEditorInput();
			ICFDocument icfd = (ICFDocument)ite.getDocumentProvider().getDocument(iep.getEditorInput());
			
			//IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			
			//icfd.parseDocument();
			//CFDocument cfd = icfd.getParser().getParseResult();
			//CFDocument cfd = icfd.getParser().parseDoc();
			//icfd.getParser().parseSaveDoc();
			//CFDocument cfd = icfd.getParser().getParseResult();
			
			//cfd.docVariables
			
			ArrayList tmitems = icfd.getParser().getTagMatches(icfd);
			CFDocument cfd = icfd.getParser().createDocTree(tmitems);
			
			//CFDocument cfd = icfd.getParser().parseDoc(icfd);
			System.out.println("**********" + cfd.getFilename() + "********");
			
			Stack items = cfd.docTree;
			
			if(items == null || items.size() < 1){
				System.err.println("No items?!");
			}
			else
			{
				//DocItem docroot = (DocItem)items.get(0);
				//TagMatch tm = (TagMatch)items.get(0);
				
				//System.out.println(
				//	"got " + tm.match
				//);
				
				java.util.Iterator i = items.iterator();
				while(i.hasNext())
				{
					System.err.println("Item::::" + (DocItem)i.next());
				}
				
				//return tm;
				return (DocItem)items.get(0);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		return null; //new TagMatch("/",0,0,1);
	}
	
	
	/**
	 * Gets the selected item parses it, and adds the defined stuff to the
	 * editor
	 */
	protected void jumpToItem() 
	{
		//get a handle to the current editor and assign it to our temp action
		IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
		//tmpAction.setActiveEditor(null,iep);
		//File selectedfile = null;
		DocItem selecteditem = null;
		
		//can't do much if nothing is selected
		if(treeViewer.getSelection().isEmpty()) 
		{
			return;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
			//selectedfile = (File)selection.getFirstElement();
			selecteditem = (DocItem)selection.getFirstElement();
		}
		
		if(selecteditem == null)
			return;
		
		try
		{
			ITextEditor editor = (ITextEditor)iep;
			//IDocument doc = editor.getDocumentProvider().getDocument(iep.getEditorInput());
			editor.setHighlightRange(selecteditem.getStartPosition(),0,true);
		}catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	protected void reload() {
		treeViewer.setInput(getRootInput());		
	}
	
	protected void expand(){
		treeViewer.expandAll();
	}
	
	/**
	 * creates all the default actions
	 */
	protected void createActions() 
	{
		jumpAction = new Action(
			"Jump",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SNIP)
		){
			public void run() { 
				jumpToItem();
			}
		};
		refreshAction = new Action(
			"Refresh",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH)		
		) {
			public void run() {
				reload();
			}
		};
		expandAction = new Action(
			"Expand All",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ADD)
		){
			public void run() { 
				expand();
			}
		};
	}
	
	public void setFocus(){;}
}
