/*
 * Created on Apr 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.contentoutline;

import java.util.Iterator;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.parser.*;

import org.eclipse.jface.viewers.IStructuredSelection;
import com.rohanclan.cfml.util.CFPluginImages;
//import com.rohanclan.cfml.views.cfcmethods.CFCMethodViewItem;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Control;

//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.jface.viewers.SelectionChangedEvent;
//import org.eclipse.jface.viewers.ISelectionChangedListener;

import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import com.rohanclan.cfml.editors.CFMLEditor;

import org.eclipse.jface.dialogs.InputDialog;

//import com.rohanclan.cfml.parser.*;

/**
 * @author Rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFContentOutlineView extends ContentOutlinePage implements IPartListener, IPropertyListener {
	public static final String ID_CONTENTOUTLINE = "com.rohanclan.cfml.views.contentoutline.cfcontentoutlineview";
	
	protected LabelProvider labelProvider;
	protected Action jumpAction; //, refreshAction, expandAction;
	protected Action filters[];
	protected MenuManager menuMgr;
	
	private boolean menusmade = false;
	
	private static String filter = "";
	
	public Control getControl()
	{
		createTree();
		return getTreeViewer().getControl();
	}
	
	protected void createTree()
	{
		if(filter.length() == 0)
		{
			reload();
		}
		else
		{
			reload(getItems(filter));
		}
		
		if(!menusmade)
		{
			createActions();
			createMenus();
			createToolbar();
			createContextMenu();
			menusmade = true;
		}
	}
	
	/**
	 * Gets a 1 level tree of items in the document of a type. See the parser
	 * for the filter syntax 
	 * @param filter
	 * @return
	 */
	public DocItem getItems(String filter)
	{
		DocItem scratch = new TagItem(1,1,1,"root");
		
		DocItem rootItem = getRootInput();
		CFNodeList nodes = rootItem.selectNodes(filter);

		Iterator i = nodes.iterator();
		while(i.hasNext())
		{
			try 
			{
				scratch.addChild((CfmlTagItem)i.next());
			}
			catch (Exception e) 
			{
				System.err.println(e.getMessage());
			}
		}
		
		return scratch;
	}
	
	
	/**
	 * Gets the root element of the document
	 * @return the root directory
	 */
	public DocItem getRootInput()
	{	
		try
		{
			IEditorPart iep = super.getSite().getPage().getActiveEditor();
			iep.addPropertyListener(this);
			getSite().getPage().addPartListener(this);
			ITextEditor ite = (ITextEditor)iep;
			ICFDocument icfd = (ICFDocument)ite.getDocumentProvider().getDocument(iep.getEditorInput());
			
			icfd.getParser().parseSaveDoc();
			DocItem docRoot = icfd.getParser().getParseResult().getDocumentRoot();
			
			return docRoot;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		//a fake root
		return new TagItem(1,1,1,"Unk");
	}
	
	
	/**
	 * Gets the selected item parses it, and adds the defined stuff to the
	 * editor
	 */
	protected void jumpToItem() 
	{
		//get a handle to the current editor and assign it to our temp action
		IEditorPart iep = getSite().getPage().getActiveEditor();
		DocItem selecteditem = null;
		
		//can't do much if nothing is selected
		if(getTreeViewer().getSelection().isEmpty()) 
		{
			return;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)getTreeViewer().getSelection();
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
	
	public void reload(DocItem root)
	{
		//getTreeViewer().setUseHashlookup(true);
		getTreeViewer().setContentProvider(
			new OutlineContentProvider(root)
		);
		
		if(labelProvider == null)
		{
			labelProvider = new OutlineLabelProvider();
			getTreeViewer().setLabelProvider(labelProvider);
		}
		getTreeViewer().setInput(root);
		getTreeViewer().expandAll();
	}
	
	public void reload()
	{
		if(filter.length() == 0)
		{
			DocItem di = getRootInput();
			reload(di);
		}
		else
		{
			reload(getItems(filter));
		}
	}
	
	protected void expand()
	{
		getTreeViewer().expandAll();
	}
	
	/**
	 * creates all the default actions
	 */
	protected void createActions() 
	{
		jumpAction = new Action(
			"Jump",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW)
		){
			public void run() { 
				jumpToItem();
			}
		};
		/* refreshAction = new Action(
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
		*/
		///filters
		filters = new Action[6];
		
		filters[0] = new Action(
			"None",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_DELETE)
		){
			public void run(){
				filter = "";
				reload();
			}
		};
		
		filters[1] = new Action(
			"Include",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//include";
				reload(getItems(filter));
			}
		};
		
		filters[2] = new Action(
			"Module",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//module";
				reload(getItems(filter));
			}
		};
		
		filters[3] = new Action(
			"Query",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//query";
				reload(getItems(filter));
			}
		};
		
		filters[4] = new Action(
			"Set",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//set";
				reload(getItems(filter));
			}
		};
		
		filters[5] = new Action(
			"Custom",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run()
			{
				InputDialog pathdialog = new InputDialog(
					getSite().getShell(),
					"CFML Path Filter",
					"Filter outline using path (i.e. \"//output\" for all the cfoutput tags):",
					"",
					null
				);
				
				if(pathdialog.open() == org.eclipse.jface.window.Window.OK) 
				{
					String xpath = pathdialog.getValue();
					if(xpath.length() > 0)
					{
						filter = xpath;
						reload(getItems(filter));
					}
				}
			}
		};
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	public void partActivated(IWorkbenchPart part) {
		//System.out.println("Part activated: " + part.getClass().getName());
		if(part instanceof CFMLEditor)
		{
			reload();
		}
	}
	
	public void partBroughtToTop(IWorkbenchPart part) {
		//System.out.println("Part brought to top: "+part.getClass().getName());
		if(part instanceof CFMLEditor)
		{
			reload();
		}
	}
	
	public void partClosed(IWorkbenchPart part) 
	{
		System.out.println("Part closed: " + part.getClass().getName());
	}
	
	public void partDeactivated(IWorkbenchPart part) 
	{
		System.out.println("Part deactivated: " + part.getClass().getName());
	}
	
	public void partOpened(IWorkbenchPart part) 
	{
		System.out.println("Part opened: " + part.getClass().getName());
		if(part instanceof CFMLEditor)
		{
			reload();
		}
	}
	
	public void propertyChanged(Object source, int propId)
	{
		System.out.println("Property changed: " + source.getClass().getName());
		if(source instanceof CFMLEditor)
		{
			reload();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	protected void createMenus()
	{
		IMenuManager rootMenuManager = super.getSite().getActionBars().getMenuManager();
		//rootMenuManager.add(refreshSnippetsAction);
		int flen = filters.length;
		for(int i=0; i<flen; i++)
		{
			rootMenuManager.add(filters[i]);
		}
	}
	
	private void createContextMenu()
	{
		//Create menu manager.
		menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(mgr);
			}
		});
		
		// Create menu.
		Menu menu = menuMgr.createContextMenu(getTreeViewer().getControl());
		getTreeViewer().getControl().setMenu(menu);
	}
	
	private void fillContextMenu(IMenuManager mgr) 
	{
		mgr.add(jumpAction);
	}
	
	
	protected void createToolbar()
	{
		IToolBarManager toolbarManager = super.getSite().getActionBars().getToolBarManager();
		toolbarManager.add(jumpAction);
		//toolbarManager.add(expandAction);
		//toolbarManager.add(refreshAction);
	}
	
	/*
	public void setSelection(ISelection selection){;}  
	public void selectionChanged(SelectionChangedEvent event) {;}
	public void removeSelectionChangedListener(ISelectionChangedListener listener){;}
	public ISelection getSelection(){ return null; } 
	protected void fireSelectionChanged(ISelection selection){;} 
	public void addSelectionChangedListener(ISelectionChangedListener listener){;}
	*/
}
