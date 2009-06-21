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
package org.cfeclipse.cfml.views.contentoutline;

//import org.eclipse.core.resources.IResource;
import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.editors.actions.GenericOpenFileAction;
import org.cfeclipse.cfml.editors.actions.GotoFileAction;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * @author Rob
 *
 * This is the default content outline view
 */
public class CFContentOutlineView extends ContentOutlinePage implements IPartListener, IPropertyListener, ISelectionListener {
	public static final String ID_CONTENTOUTLINE = "org.cfeclipse.cfml.views.contentoutline.cfcontentoutlineview";
	
	protected LabelProvider labelProvider;
	protected Action jumpAction, selectAction, linkWithEditorAction, expandAction, filterOnAction, openAction;
	
	protected static GenericOpenFileAction openFileAction;
	protected static GotoFileAction gfa = new GotoFileAction(); 
	
	protected Action filters[];
	protected MenuManager menuMgr;
	
	private boolean menusmade = false;
	private boolean treemade = false;
	private boolean linkWithEditor = true;
	
	private static String filter = "";
	
	private ArrayList lastExpandedElements = null;
	
	private OutlineContentProvider cop;

	protected boolean changeCameFromEditor;
	
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
			// add this view as a selection listener to the workbench page
			//getSite().getPage().addSelectionListener((ISelectionListener) this);
			getSite().getPage().addPostSelectionListener((ISelectionListener) this);
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
				scratch.addChild((DocItem)i.next());
			}
			catch (Exception e) 
			{
				System.err.println("Tree item set error ");
				e.printStackTrace();
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
			ICFDocument icfd = null;
			CFDocument cfd = null;
			if (ite.getDocumentProvider().getDocument(iep.getEditorInput()) instanceof ICFDocument) {
				icfd = (ICFDocument)ite.getDocumentProvider().getDocument(iep.getEditorInput());
				cfd = icfd.getCFDocument();
			}
			//icfd.clearAllMarkers();
			//icfd.parseDocument();
			
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
	 * from editor selects corresponding tree item
	 * if there is one
	 * @return
	 */
	public DocItem getItemFromPosition(int startPos) {
		DocItem rootItem = getRootInput();
		CFNodeList nodes = rootItem.selectNodes("//*");
		Iterator i = nodes.iterator();
		while(i.hasNext())
		{
			DocItem item = (DocItem)i.next();			
			if(item.getStartPosition() == startPos){
				return item;
			}
		}
		return null;
	}
	
	/**
	 * gets the currently selected item in docitem form or <code>null</code>
	 * if there is none
	 * @return
	 */
	private Iterator getSelectedDocItems()
	{
		Iterator selecteditem = null;
		
		//can't do much if nothing is selected
		if(getTreeViewer().getSelection().isEmpty()) 
		{
			return null;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)getTreeViewer().getSelection();
			selecteditem = selection.iterator();
		}
		
		return selecteditem;
	}

	
	/**
	 * gets the currently selected item in docitem form or <code>null</code>
	 * if there is none
	 * @return
	 */
	private DocItem getSelectedDocItem()
	{
		Iterator selecteditems = getSelectedDocItems();
		
		//can't do much if nothing is selected
		if(selecteditems == null || !selecteditems.hasNext()) 
		{
			return null;
		}		
		return (DocItem)selecteditems.next();
	}
	
	/**
	 * sets the currently selected item in docitem form or <code>null</code>
	 * if there is none
	 * @return
	 */
	private void setSelectedDocItem(DocItem item)
	{		
		//can't do much if nothing is selected
		if(item != null) 
		{
			//getTreeViewer().expandToLevel(new StructuredSelection(item), 1);
			//getTreeViewer().reveal(new StructuredSelection(item));
			getTreeViewer().setSelection(new StructuredSelection(item), true);
		}		
	}

	
	/**
	 * Selects the item(s) within the source code view
	 */
	protected void selectItem() 
	{
		//get a handle to the current editor and assign it to our temp action
		IEditorPart iep = getSite().getPage().getActiveEditor();
		Iterator selecteditems = getSelectedDocItems();
		
		if(!selecteditems.hasNext()) return;
		
		ITextEditor editor = (ITextEditor)iep;
		DocItem firstItem = ((DocItem)selecteditems.next());
		int startPos = firstItem.getStartPosition();
		int endPos = firstItem.getEndPosition();
		while(selecteditems.hasNext()){
			endPos = ((DocItem)selecteditems.next()).getEndPosition();
		}
		editor.selectAndReveal(startPos,endPos-startPos+1);
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
		//editor.setHighlightRange(selecteditem.getStartPosition(),selecteditem.getMatchingItem().getEndPosition(),true);
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
				// we only want to jump if the change came from tree viewer selection
				if(!changeCameFromEditor) {
					changeCameFromEditor = false;					
					jumpToItem();
				} else {
					changeCameFromEditor = false;					
				}
			}
		};
		jumpAction.setToolTipText("Jump to selected tag in the editor");
		
		selectAction = new Action(
				"Select",
				CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW)
			){
				public void run() { 
					selectItem();
				}
			};
			selectAction.setToolTipText("Select tag in the editor");

		linkWithEditorAction = new Action("Link with Editor", CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_LINK_TO_EDITOR)) {
			public void run() {
				if(linkWithEditor) {
					linkWithEditor = false;
				} else {
					linkWithEditor = true;					
				}
				this.setChecked(linkWithEditor);
				menuMgr.update();
			}
		};
		selectAction.setToolTipText("Link outline view with the editor");

		filterOnAction = new Action(
			"Filter On This",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run() { 
				filterOnSelected();
			}
		};
		filterOnAction.setToolTipText("Filter on selected tag type");
		
		expandAction = new Action(
			"Expand All",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ADD)
		){
			public void run() 
			{
				getTreeViewer().expandAll();
			}
		};
		expandAction.setToolTipText("Expand all");
		
		openAction = new Action(
			"Open File",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_IMPORT)
		){
			public void run() { 
				openFile();
			}
		};
		
		///filters
		filters = new Action[7];
		
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
				filter = "//cfinclude";
				reload(getItems(filter));
			}
		};
		
		filters[2] = new Action(
			"Module",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//cfmodule";
				reload(getItems(filter));
			}
		};
		
		filters[3] = new Action(
			"Query",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//cfquery";
				reload(getItems(filter));
			}
		};
		
		filters[4] = new Action(
			"Set",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//cfset";
				reload(getItems(filter));
			}
		};
		
		filters[5] = new Action(
			"Case",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run(){
				filter = "//cfcase";
				reload(getItems(filter));
			}
		};
		
		filters[6] = new Action(
			"Custom",
			CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_TAG)
		){
			public void run()
			{
				InputDialog pathdialog = new InputDialog(
					getSite().getShell(),
					"CFML Path Filter",
					"Filter outline using path (i.e. \"//cfoutput\" for all the cfoutput tags):",
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
		if(si.equalsIgnoreCase("cfinclude") || si.equalsIgnoreCase("cfmodule"))
		{	
			gfa.setActiveEditor(null,iep);
			gfa.run(null);
		}
	}
	
	private void hookDoubleClickAction() {
		getTreeViewer().addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				selectAction.run();
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

	public void selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent event) {
		if(linkWithEditor) {
			if(event.getSelection() instanceof ITreeSelection){				
				jumpAction.run();
			} else {
				changeCameFromEditor = true;
				getTreeViewer().expandToLevel(((DocItem)(event.getSelection())), 1);
			}
		}
		saveExpandedElements();	
		//getTreeViewer().refresh(new StructuredSelection(event.getSelection()));
	}

	public void selectionChanged(IWorkbenchPart workbench, ISelection selection) {
		// TODO Auto-generated method stub
		if(selection != null && selection instanceof ITextSelection){
			// this change will file the selection changed event.  This bool prevents a line jump
			changeCameFromEditor = true;
			CFMLEditor curDoc = (CFMLEditor) workbench.getSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			ICFDocument cfd = (ICFDocument) curDoc.getDocumentProvider().getDocument(curDoc.getEditorInput());
			//if(curDoc instanceof CFMLEditor){
				ITextSelection tselection = (ITextSelection)selection;
				int startPos;
//					startPos = cfd.getLineOffset(tselection.getStartLine())+1;
					startPos = tselection.getOffset()+1;
					DocItem cti = (DocItem)cfd.getTagAt(startPos, startPos, true);				
					if (cti != null) {
						if(linkWithEditor) {
							setSelectedDocItem(cti);
							DocItem curItem = cti;
							getTreeViewer().setExpandedState(curItem.getParent(), true);
							getTreeViewer().refresh(curItem.getParent(),false);
							getTreeViewer().setExpandedState(curItem, true);
							getTreeViewer().refresh(curItem,false);
//							while(curItem.getName().compareToIgnoreCase("Doc Root") !=1) {
//								getTreeViewer().setExpandedState(curItem.getParent(), true);
//								getTreeViewer().refresh(curItem,false);
//								curItem = curItem.getParent();
//							}
							
						}							
					}			
			//}
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
		rootMenuManager.add(new Separator());
		linkWithEditorAction.setChecked(true);
		rootMenuManager.add(linkWithEditorAction);
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
			if(sname.equals("cfinclude") || sname.equals("cfmodule"))
				mgr.add(openAction);
		}
	}
	
	
	protected void createToolbar()
	{
		IToolBarManager toolbarManager = super.getSite().getActionBars().getToolBarManager();
		toolbarManager.add(linkWithEditorAction);
		toolbarManager.add(jumpAction);
		toolbarManager.add(filterOnAction);
	}

}
