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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IEditorPart;
import com.rohanclan.cfml.util.XMLConfigFile;
import org.eclipse.core.runtime.IPath;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;
import java.io.File;

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
public class SnipTreeView extends ViewPart {
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
	private static XMLConfigFile xmlconfile;
	
	protected Action insertAction; //addItemAction; //, deleteItemAction, selectAllAction;
	
	/** the root directory */
	protected File root;
	
	/**
	 * The constructor.
	 */
	public SnipTreeView() {
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
						//try to load up the xml file
						xmlconfile.setFileName(f);
						xmlconfile.openFile();
						
						//figure out if this is a DWimport or a normal cfeclipse snip
						//and set the encloser accordingly
						if(f.endsWith(".xml") || f.endsWith(".XML"))
						{
							toShow.append(xmlconfile.getValue("help"));
						}
						else if(f.endsWith(".csn") || f.endsWith(".CSN"))
						{	
							toShow.append("Dreamweaver Import");
						}
						else
						{
							toShow.append("Unknown Snip Type");
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
					}
					text.setText(toShow.toString());
				}
			}
		});
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
	}
	
	/**
	 * creates all the menus
	 */
	protected void createMenus() {
		IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		rootMenuManager.add(insertAction);
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
		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		
		// Register menu for extension.
		getSite().registerContextMenu(menuMgr, treeViewer);
	}
	
	private void fillContextMenu(IMenuManager mgr) {
		mgr.add(insertAction);
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
		toolbarManager.add(insertAction);
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
	private void insertItem() 
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
	}

	/*
	 * @see IWorkbenchPart#setFocus()
	 */
	public void setFocus(){;}
}

