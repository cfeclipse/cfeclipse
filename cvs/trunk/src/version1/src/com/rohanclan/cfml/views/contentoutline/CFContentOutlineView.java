/*
 * Created on Apr 6, 2004
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
package com.rohanclan.cfml.views.contentoutline;

//import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
//import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Control;

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.editors.actions.GenericOpenFileAction;
import com.rohanclan.cfml.editors.actions.GotoFileAction;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.editors.ICFDocument;
import com.rohanclan.cfml.parser.DocItem;
import com.rohanclan.cfml.parser.TagItem;
import com.rohanclan.cfml.parser.CFDocument;
import com.rohanclan.cfml.parser.CFNodeList;
import com.rohanclan.cfml.parser.CfmlTagItem;

import java.util.Iterator;
import java.util.ArrayList;

/**
 * @author Rob
 *
 * This is the default content outline view
 */
public class CFContentOutlineView extends ContentOutlinePage implements IPartListener, IPropertyListener {
	public static final String ID_CONTENTOUTLINE = "com.rohanclan.cfml.views.contentoutline.cfcontentoutlineview";
	
	protected LabelProvider labelProvider;
	protected Action jumpAction, expandAction, filterOnAction, openAction;
	
	protected static GenericOpenFileAction openFileAction;
	protected static GotoFileAction gfa = new GotoFileAction(); 
	
	protected Action filters[];
	protected MenuManager menuMgr;
	
	private boolean menusmade = false;
	private boolean treemade = false;
	
	private static String filter = "";
	
	private ArrayList lastExpandedElements = null;
	
	private OutlineContentProvider cop;
	
	public Control getControl()
	{
		if(!treemade)
		{
			//first go round setup some of our default stuff
			getTreeViewer().setUseHashlookup(true);
			lastExpandedElements = new ArrayList();
			saveExpandedElements();
			createTree();
			treemade = true;
		}
		
		if(!menusmade)
		{
			createActions();
			createMenus();
			createToolbar();
			createContextMenu();
			hookDoubleClickAction();
			menusmade = true;
		}
		
		return getTreeViewer().getControl();
	}
	
	private void saveExpandedElements()
	{
		Object ob[] = getTreeViewer().getExpandedElements();
		int oblen = ob.length;
		for(int i=0; i<oblen; i++)
		{
			lastExpandedElements.clear();
		//System.out.println(ob[i]);
			lastExpandedElements.add(ob[i]);
		}
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
	 * Gets the root element of the document. Saves information into a local
	 * cache so if there is an error in the parser it uses the last known
	 * good outline (which kind of sucks but it looks better)
	 * @return the root directory
	 */
	public DocItem getRootInput()
	{	
		try
		{
			DocItem docRoot = null;
			
			IEditorPart iep = getSite().getPage().getActiveEditor();
			
			iep.addPropertyListener(this);
			getSite().getPage().addPartListener(this);
			
			ITextEditor ite = (ITextEditor)iep;
			ICFDocument icfd = (ICFDocument)ite.getDocumentProvider().getDocument(iep.getEditorInput());
			
			icfd.clearAllMarkers();
			icfd.parseDocument();
			
			CFDocument cfd = icfd.getCFDocument();
			if(cfd != null)
			{
				docRoot = cfd.getDocumentRoot();
				//lastDocRoot = docRoot;
			}
			
			if(docRoot != null)
			{
				return docRoot;
			}
			else
			{
				return createFakeRoot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		//a fake root
		return createFakeRoot();
	}
	
	private TagItem createFakeRoot()
	{
		TagItem tg = new TagItem(1,1,1,"Unk");
		return tg;
	}
	
	/**
	 * gets the currently selected item in docitem form or <code>null</code>
	 * if there is none
	 * @return
	 */
	private DocItem getSelectedDocItem()
	{
		DocItem selecteditem = null;
		
		//can't do much if nothing is selected
		if(getTreeViewer().getSelection().isEmpty()) 
		{
			return null;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)getTreeViewer().getSelection();
			selecteditem = (DocItem)selection.getFirstElement();
		}
		
		return selecteditem;
	}
	
	
	/**
	 * Gets the selected item parses it, and adds the defined stuff to the
	 * editor
	 */
	protected void jumpToItem() 
	{
		//get a handle to the current editor and assign it to our temp action
		IEditorPart iep = getSite().getPage().getActiveEditor();
		DocItem selecteditem = getSelectedDocItem();
		
		if(selecteditem == null) return;
		
		ITextEditor editor = (ITextEditor)iep;
		editor.setHighlightRange(selecteditem.getStartPosition(),0,true);
	}
	
	/**
	 * filters the outline on the currenly selected element
	 */
	protected void filterOnSelected()
	{
		DocItem selecteditem = getSelectedDocItem();
		
		if(selecteditem == null)
			return;
		
		filter = "//" + selecteditem.getName();
		reload();
	}
	
	public void reload(DocItem root)
	{
		//saveExpandedElements();
		
		if(cop == null)
		{
			cop = new OutlineContentProvider(root);
			getTreeViewer().setContentProvider(cop);
		}
		
		if(labelProvider == null)
		{
			labelProvider = new OutlineLabelProvider();
			getTreeViewer().setLabelProvider(labelProvider);
		}
		
		getTreeViewer().setInput(root);
		//getTreeViewer().setExpandedElements(lastExpandedElements.toArray());
		//getTreeViewer().expandAll();
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
	
	public void expand()
	{
		getTreeViewer().expandAll();
	}
	
	/**
	 * creates all the default actions
	 */
	protected void createActions() 
	{
		jumpAction = new Action(
			"Jump To",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW)
		){
			public void run() { 
				jumpToItem();
			}
		};
		
		filterOnAction = new Action(
			"Filter On This",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run() { 
				filterOnSelected();
			}
		};
		
		expandAction = new Action(
			"Expand All",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ADD)
		){
			public void run() 
			{
				getTreeViewer().expandAll();
			}
		};
		
		openAction = new Action(
			"Open File",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_IMPORT)
		){
			public void run() { 
				openFile();
			}
		};
		
		///filters
		filters = new Action[6];
		
		filters[0] = new Action(
			"Remove Filter",
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
	
	/**
	 * method to open the currently selected docitem if its a include or a 
	 * module item
	 */
	private void openFile()
	{
		IEditorPart iep = getSite().getPage().getActiveEditor();
		DocItem selecteditem = getSelectedDocItem();
		if(selecteditem == null) return;
		
		String si = selecteditem.getName();
		if(si.equalsIgnoreCase("include") || si.equalsIgnoreCase("module"))
		{	
			gfa.setActiveEditor(null,iep);
			gfa.run(null);
		}
	}
	
	private void hookDoubleClickAction() {
		getTreeViewer().addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				jumpAction.run();
			}
		});
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	public void partActivated(IWorkbenchPart part) {
		//System.out.println("Part activated: " + part.getClass().getName());
		if(part instanceof CFMLEditor)
		{
			//reload();
		}
	}
	
	public void partBroughtToTop(IWorkbenchPart part) {
		//System.out.println("Part brought to top: "+part.getClass().getName());
		//if(part instanceof CFMLEditor)
		//{
		//	reload();
		//}
	}
	
	public void partClosed(IWorkbenchPart part) 
	{
		//System.out.println("Part closed: " + part.getClass().getName());
	}
	
	public void partDeactivated(IWorkbenchPart part) 
	{
		//System.out.println("Part deactivated: " + part.getClass().getName());
	}
	
	public void partOpened(IWorkbenchPart part) 
	{
	//System.out.println("Part opened: " + part.getClass().getName());
		if(part instanceof CFMLEditor)
		{
			//reload();
		}
	}
	
	public void propertyChanged(Object source, int propId)
	{
		//System.out.println("Property changed: " + source.getClass().getName());
		if(source instanceof CFMLEditor)
		{
			//reload();
			DocItem old = (DocItem)getTreeViewer().getInput();
			reload();
			cop.inputChanged(getTreeViewer(),old,getTreeViewer().getInput());
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	protected void createMenus()
	{
		IMenuManager rootMenuManager = super.getSite().getActionBars().getMenuManager();
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
		mgr.add(new Separator());
		mgr.add(filterOnAction);
		
		if(filter.length() > 0)
			mgr.add(filters[0]);
		
		DocItem di = getSelectedDocItem();
		if(di != null)
		{
			String sname = di.getName();
			if(sname.equals("include") || sname.equals("module"))
				mgr.add(openAction);
		}
	}
	
	
	protected void createToolbar()
	{
		IToolBarManager toolbarManager = super.getSite().getActionBars().getToolBarManager();
		toolbarManager.add(jumpAction);
		toolbarManager.add(filterOnAction);
	}
}
