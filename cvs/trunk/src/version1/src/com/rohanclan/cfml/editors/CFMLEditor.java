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
package com.rohanclan.cfml.editors;

import org.eclipse.ui.editors.text.TextEditor;
//import com.rohanclan.coldfusionmx.editors.actions.*;
//import org.eclipse.jface.action.IMenuManager;
//import org.eclipse.jface.action.MenuManager;
//import com.rohanclan.coldfusionmx.editors.CFSyntaxDictionary;
//import com.rohanclan.coldfusionmx.editors.actions.SnippetActionLoader;
//import com.rohanclan.coldfusionmx.editors.script.JSSyntaxDictionary;
//import com.rohanclan.coldfusionmx.util.*;
//import org.eclipse.ui.IActionBars;
//import org.eclipse.ui.IWorkbenchActionConstants; 
import org.eclipse.swt.widgets.Composite;

//import org.eclipse.jface.action.*;
//import org.eclipse.ui.IEditorActionDelegate;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;

/**
 * @author Rob
 *
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
public class CFMLEditor extends TextEditor {

	private ColorManager colorManager;
	
	protected GenericEncloserAction testAction;
	
	public CFMLEditor() 
	{
		super();
		colorManager = new ColorManager();
		//setup color coding and the damage repair stuff
		setSourceViewerConfiguration(new CFConfiguration(colorManager));
		//assign the cfml document provider which does the partitioning
		//and connects it to this Edtior
		setDocumentProvider(new CFDocumentProvider());
	}
	
	public void createPartControl(Composite parent) 
	{
		super.createPartControl(parent);
		
		//label = new Label(parent, 0);
		//label.setText("Hello World");
		//getViewSite().getPage().addSelectionListener(this);
	}
	
	public void createActions()
	{
		super.createActions();
		//System.err.println("buildin actions");
		/* testAction = new Action("test") {
			public void run() { 
				System.err.println("hi there :)");
			}
		}; */
		
		//this.getEditorSite().getActionBars().updateActionBars();
		//createMenu();
		//createToolbar();
	}
	
	/**
	 * Create menu.
	 */
	/* private void createMenu() 
	{
		//IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
		//mgr.add(selectAllAction);
		IMenuManager imm = this.getEditorSite().getActionBars().getMenuManager();
			
		
		testAction = new GenericEncloserAction("hi","hi");
		testAction.setText("hi");
		testAction.setId("com.rohanclan.coldfusionmx.actions.GenericEncloserAction.test");
		testAction.setDescription("I am a thing");
		//testAction.setActionDefinitionId("org.eclipse.ui.editorActions");
				
		//imm.appendToGroup("org.eclipse.ui.editorActions",testAction);
		imm.add(testAction);
		//this.getEditorSite().getActionBars().updateActionBars();
	} */
	
	/**
	 * Create toolbar.
	 
	private void createToolbar() 
	{
		//IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		//mgr.add(addItemAction);
		//mgr.add(deleteItemAction);
		//IToolBarManager tbm = this.getEditorSite().getActionBars().getToolBarManager();
		//tbm.add(testAction);
		//this.getEditorSite().getActionBars().updateActionBars();
	}*/
	
	
	
	/* protected MenuManager createMenuManager()
	{
		SnippetActionLoader sa = new SnippetActionLoader();
		return sa.createMenuManager();
	} */
	
	
	/* right click menu 
	public void editorContextMenuAboutToShow(MenuManager menu) {
		//super.editorContextMenuAboutToShow(menu);
		menu.add(new CFCommentAction());
	}*/
	
	public void dispose() 
	{
		colorManager.dispose();
		super.dispose();
	}
}
