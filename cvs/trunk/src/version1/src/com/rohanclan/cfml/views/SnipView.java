/*
 * Created on Feb 22, 2004
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
package com.rohanclan.cfml.views;

import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.jface.action.Action;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;

import com.rohanclan.cfml.CFMLPlugin;
import org.eclipse.core.runtime.IPath;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.*;
import com.rohanclan.cfml.util.XMLConfigFile;
import java.io.File;
import org.eclipse.ui.IEditorPart;

/**
 * @author Rob
 *
 * This does a simple list view of the snips directory its only a single level
 * and it has no fancy stuff. If they like snips but have limited resources
 * this is a pretty good view
 * 
 * This class was influenced by the aricle:
 * Creating an Eclipse View
 * By Dave Springgay, OTI 
 * November 2, 2001
 * Found on www.eclipse.org
 */
public class SnipView extends ViewPart {
	public static final String ID_SNIPVIEW = "com.rohanclan.cfml.views.snipview";
	
	protected ListViewer viewer;
	protected Action insertAction; //addItemAction; //, deleteItemAction, selectAllAction;
	
	private static GenericEncloserAction tmpAction;
	private static XMLConfigFile xmlconfile;
	
	//IMemento memento;
	
	/** the path to the icons. i.e. file://C/blah/plugin/icons/ */
	protected static IPath snipBase;
	
	
	/**
	 * Constructor
	 */
	public SnipView() 
	{
		super();
		if(snipBase == null)
		{
			try 
			{
				snipBase = CFMLPlugin.getDefault().getStateLocation();
			} 
			catch (Exception e) 
			{
				e.printStackTrace(System.err);
			}
		}
		
		if(tmpAction == null)
			tmpAction = new GenericEncloserAction();
		if(xmlconfile == null)
			xmlconfile = new XMLConfigFile();
	}

	/**
	 * @see IViewPart.init(IViewSite)
	 */
	public void init(IViewSite site) throws PartInitException 
	{
		super.init(site);
	}

	/**
	 * @see IWorkbenchPart#createPartControl(Composite)
	 */
	public void createPartControl(Composite parent) 
	{
		// Create viewer.
		viewer = new ListViewer(parent);
		//viewer.setContentProvider(new WordContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		//viewer.setInput(input);

		//System.out.println(snipBase);
		
		File snipdir = snipBase.toFile();
		if(snipdir.isDirectory())
		{
			String f[] = snipdir.list();
			
			for(int q=0; q<f.length; q++)
			{
				//PictureLabel pl = new PictureLabel(parent,0);
				//pl.setText(f[q]);
				//pl.setImage(CFPluginImages.get(CFPluginImages.ICON_TOOLS));
				if(f[q].endsWith(".xml") || f[q].endsWith(".XML") 
					|| f[q].endsWith(".csn") || f[q].endsWith(".CSN"))
				viewer.add(f[q]);
			}
			//viewer.add(snipdir.list());
		}
		
		
		// Create menu and toolbars.
		createActions();
		createMenu();
		createToolbar();
		createContextMenu();
		//hookGlobalActions();
		
		// Restore state from the previous session.
		//restoreState();
	}
	
	/**
	 * Create the actions.
	 */
	public void createActions() {
		insertAction = new Action("Insert") {
			public void run() { 
				insertItem();
			}
		};
		
		// Add selection listener.
		/* viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateActionEnablement();
			}
		}); */
	}
	
	/**
	 * Create menu.
	 */
	private void createMenu() {
		IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
		//mgr.add(selectAllAction);
		mgr.add(insertAction);
	}
	
	/**
	 * Create toolbar.
	 */
	private void createToolbar() {
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(insertAction);
		//mgr.add(deleteItemAction);
	}
		
	/**
	 * Create context menu.
	 */
	private void createContextMenu() {
		// Create menu manager.
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(mgr);
			}
		});
		
		// Create menu.
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		
		// Register menu for extension.
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(insertAction);
		mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		//mgr.add(deleteItemAction);
		//mgr.add(new Separator());
		//mgr.add(selectAllAction);
	}
	
	/**
	 * Hook global actions
	 */
	/* private void hookGlobalActions() {
		IActionBars bars = getViewSite().getActionBars();
		bars.setGlobalActionHandler(IWorkbenchActionConstants.SELECT_ALL, selectAllAction);
		bars.setGlobalActionHandler(IWorkbenchActionConstants.DELETE, deleteItemAction);
		viewer.getControl().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && 
						event.stateMask == 0 && 
						deleteItemAction.isEnabled()) 
				{
					deleteItemAction.run();
				}
			}
		});
	} */
	
	
	/**
	 * Add item to list.
	 */
	private void insertItem() 
	{
		//get a handle to the current editor and assign it to our 
		//temp action
		IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
		tmpAction.setActiveEditor(null,iep);
		
		//System.err.println( (String)((IStructuredSelection)viewer.getSelection()).getFirstElement() );
		
		//get the full path to the file
		String f = snipBase.toFile().getAbsolutePath() + "/"
			+ (String)((IStructuredSelection)viewer.getSelection()).getFirstElement();
		
		//System.out.println(f);
		
		try
		{
			//try to load up the xml file
			xmlconfile.setFileName(f);
			xmlconfile.openFile();
			
			//figure out if this is a DWimport or a normal cfeclipse snip
			//and set the encloser accordingly
			if(f.endsWith(".xml") || f.endsWith(".XML"))
			{
				tmpAction.setEnclosingStrings(
					xmlconfile.getValue("starttext"),
					xmlconfile.getValue("endtext")	
				);
			}
			else if(f.endsWith(".csn") || f.endsWith(".CSN"))
			{	
				tmpAction.setEnclosingStrings(
					xmlconfile.getValue("insertText",0),
					xmlconfile.getValue("insertText",1)	
				);
			}
			
			tmpAction.run(null);
			
		}catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		
		/* String name = promptForValue("Enter name:", null);
		if (name != null) {
			Word word = new Word(name);
			input.add(word);
			viewer.setSelection(new StructuredSelection(word));
		}
		*/
	}
	
	
	/**
	 * @see WorkbenchPart#setFocus()
	 */
	public void setFocus() 
	{
		viewer.getControl().setFocus();
	}
}
