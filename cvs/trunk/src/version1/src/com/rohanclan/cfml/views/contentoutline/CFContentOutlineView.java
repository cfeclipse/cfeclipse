/*
 * Created on Apr 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.contentoutline;

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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Control;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;

import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * @author Rob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFContentOutlineView extends ContentOutlinePage implements IPartListener, IPropertyListener {
	public static final String ID_CONTENTOUTLINE = "com.rohanclan.cfml.views.contentoutline.cfcontentoutlineview";
	
	protected LabelProvider labelProvider;
	protected Action jumpAction, refreshAction, expandAction;
	protected MenuManager menuMgr;
	
	public Control getControl()
	{
		createTree();
		return super.getTreeViewer().getControl();
	}
	
	public void setSelection(ISelection selection){;}  
	public void selectionChanged(SelectionChangedEvent event) {;}
	public void removeSelectionChangedListener(ISelectionChangedListener listener){;}
	public ISelection getSelection(){ return null; } 
	protected void fireSelectionChanged(ISelection selection){;} 
	public void addSelectionChangedListener(ISelectionChangedListener listener){;}
	
	public void createTree()
	{
		getTreeViewer().setContentProvider(
			new OutlineContentProvider(getRootInput())
		);
		labelProvider = new OutlineLabelProvider();
		getTreeViewer().setLabelProvider(labelProvider);
		getTreeViewer().setUseHashlookup(true);
		getTreeViewer().setInput(getRootInput());
		
		createActions();
		createMenus();
		createToolbar();
		createContextMenu();
	}
	
	protected void createMenus()
	{
		//IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		IMenuManager rootMenuManager = super.getSite().getActionBars().getMenuManager();
		//rootMenuManager.add(refreshSnippetsAction);
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
			IEditorPart iep = super.getSite().getPage().getActiveEditor();
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
		IEditorPart iep = super.getSite().getPage().getActiveEditor();
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
	
	protected void reload() 
	{
		getTreeViewer().setContentProvider(
			new OutlineContentProvider(getRootInput())
		);
		getTreeViewer().setInput(getRootInput());
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
	
	////////////////////////////////////////////////////////////////////////////
	
	public void partActivated(IWorkbenchPart part) {
		System.out.println("Part activated: " + part.getClass().getName());
		reload();
	}
	
	public void partBroughtToTop(IWorkbenchPart part) {
		System.out.println("Part brought to top: "+part.getClass().getName());
		reload();
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
		reload();
	}
	
	public void propertyChanged(Object source, int propId)
	{
		System.out.println("Property changed: " + source.getClass().getName());
		reload();
	}
}
