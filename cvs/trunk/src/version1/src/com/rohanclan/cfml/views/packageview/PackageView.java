package com.rohanclan.cfml.views.packageview;


import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericOpenFileAction;
import com.rohanclan.cfml.properties.CFMLPropertyManager;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.browser.BrowserView;
import com.rohanclan.cfml.views.packageview.objects.*;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 * 
 * TODO: This PackageView will be re-modelled to display the current project's CFC's as a dot notated packages.
 * With the help of a new parser, it should be able to parse all the CFC's and give us all the methods and method signatures
 * On Save actions should trigger this view. (or whatever action the Java perspective uses to update the Package Explorer
 */

public class PackageView extends ViewPart {
	private TreeViewer viewer;
	private Combo projcombo;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Action refresh;
	
	//Right Click actions
	
	private Action funcCreateObject; 		//Creates a cfscript version of a function
	private Action funcInvokeObject; 		//Creates a Invoke version of a function
	private Action compCreateObject;		//Creates a cfscript version of a component
	private Action getFunctionDetails;	//Displays a message saying what this function is 
	private Action getComponentDetails;	//Displays a message saying what this function is 
	private Action getDescription; 	//Opens the internal browser to show the details of component 
									//http://markdrew.local/CFIDE/componentutils/cfcexplorer.cfc?NAME=ggcc7.controller.mailer&METHOD=getcfcinhtml
	
	
	
	private Action makeFolderWWWRoot;
	private Action makeFolderCFCRoot;
	private Action makeFolderCF_Root;
	private Action makeFolderDefault;
	

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	
	class NameSorter extends ViewerSorter {
		
		public int compare(Viewer viewer, Object e1, Object e2) {
			/*if(e1 instanceof FolderNode && !(e2 instanceof FolderNode)) {
				return -1;
			}
			else if(e2 instanceof FolderNode && !(e1 instanceof FolderNode)) {
				return 1;
			}*/
			return super.compare(viewer, e1, e2);
		}
}

	/**
	 * The constructor.
	 */
	public PackageView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
	
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
		
		Label projlabel = new Label(parent, SWT.HORIZONTAL);
		projlabel.setText("Project:");
		
		projcombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		projcombo.setLayoutData(layoutData);
		
		//Set the contents of the project list
		IProject[] projects = CFMLPlugin.getWorkspace().getRoot().getProjects();
		for(int i = 0; i < projects.length;i++){
			if(projects[i].isOpen()){
				projcombo.add(projects[i].getName());
			}
		}
		projcombo.select(0);
		projcombo.setToolTipText("Select a project to view the components");
		
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalSpan = 3;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.getControl().setLayoutData(layoutData);
		viewer.setContentProvider(new ViewContentProvider(projcombo.getText()));
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		initActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		hookListeners();
		
	}

	
	private void hookListeners(){
		projcombo.addSelectionListener(
		  new SelectionAdapter()
		  {
		    public void widgetSelected(SelectionEvent e)
		    {
		    	if(!((ViewContentProvider)viewer.getContentProvider()).getProject().equals(projcombo.getText())){
			      projcombo.getText();
			      refresh.run();
			      viewer.expandToLevel(2);
			    }
		    }
		  }
		 );
		
		
	}
	
	//This is the local right click?
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				PackageView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}
	//This is the right click item
	private void fillContextMenu(IMenuManager manager) {
		//showMessage(getSelectedDocItem().getClass().toString());
		//WE need to find out what item this is
		System.out.println(getSelectedDocItem().getClass().toString());
		if (getSelectedDocItem() instanceof FunctionNode){
			manager.add(funcCreateObject);
			manager.add(funcInvokeObject);
			manager.add(getFunctionDetails);
		}
		if(getSelectedDocItem() instanceof ComponentNode){
			manager.add(compCreateObject);
			manager.add(getComponentDetails);
			manager.add(getDescription);
			
		}
		
		
		// manager.add(new Separator());
		// manager.add(this.makeFolderCF_Root);
		// manager.add(this.makeFolderCFCRoot);
		// manager.add(this.makeFolderDefault);
		// manager.add(this.makeFolderWWWRoot);
		 manager.add(new Separator());
		// drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		 manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refresh);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void initMakeActions() {
		refresh = new Action() {
			public void run() {
			   ViewContentProvider contentprovider = new ViewContentProvider(projcombo.getText());
				viewer.setContentProvider(contentprovider);
			}
		};
		refresh.setText("Refresh View");
		refresh.setToolTipText("Refresh the Component Tree");
		refresh.setImageDescriptor( CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH));
		
		this.makeFolderCF_Root = new Action() {
			public void run() {
				setFolderType(FolderTypes.CF_ROOT);
			}
		};
		this.makeFolderCF_Root.setText("Add to custom tag path");
		this.makeFolderCF_Root.setToolTipText("Add to custom tag path");
	
	
		
		this.makeFolderCFCRoot = new Action() {
			public void run() {
				setFolderType(FolderTypes.CFCROOT);
			}
		};
		this.makeFolderCFCRoot.setText("Add to CFC path");
		this.makeFolderCFCRoot.setToolTipText("Add to CFC path");
		
		
		this.makeFolderDefault = new Action() {
			public void run() {
				setFolderType(FolderTypes.DEFAULT);
			}
		};
		this.makeFolderDefault.setText("Make a normal folder");
		this.makeFolderDefault.setToolTipText("Make a normal folder");
		
		this.makeFolderWWWRoot = new Action() {
			public void run() {
				setFolderType(FolderTypes.WWWROOT);
			}
		};		
		this.makeFolderWWWRoot.setText("Make webserver root folder");
		this.makeFolderWWWRoot.setToolTipText("Make webserver root folder");
		
	}
	
	/**
	 * gets the currently selected item in docitem form or <code>null</code>
	 * if there is none
	 * @return
	 */
	private IComponentViewObject getSelectedDocItem()
	{
		IComponentViewObject selecteditem = null;
		
		//can't do much if nothing is selected
		if(this.viewer.getSelection().isEmpty()) 
		{
			return null;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)this.viewer.getSelection();
			selecteditem = (IComponentViewObject)selection.getFirstElement();
		}
		
		return selecteditem;
	}	
	
	protected void setFolderType(String folderType) {
		IComponentViewObject selectedItem = getSelectedDocItem();
		
		if(selectedItem == null) return;
		viewer.update(selectedItem, null);
		
	}
	
	private void initActions() {
		initMakeActions();
		
		//Make the CFC specific Right click actions
		compCreateObject = new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				ComponentNode obj = (ComponentNode)((IStructuredSelection)selection).getFirstElement();
				insert(obj.getCreateObjectSnippet());
			}
		};
		compCreateObject.setText("Insert Create Object");
		compCreateObject.setToolTipText("Inserts CreateObject code");
		
		funcCreateObject = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				FunctionNode obj =(FunctionNode) ((IStructuredSelection)selection).getFirstElement();
				insert(obj.getCreateObjectSnippet());
			}
		};
		funcCreateObject.setText("Insert CreateObject");
		funcCreateObject.setToolTipText("Inserts CreateObject code");
		
		funcInvokeObject = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				FunctionNode obj =(FunctionNode) ((IStructuredSelection)selection).getFirstElement();
				insert(obj.getInvokeSnippet());
			}
		};
		funcInvokeObject.setText("Insert Invoke");
		funcInvokeObject.setToolTipText("Inserts cfinvoke code");
		
		getFunctionDetails = new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				FunctionNode obj =(FunctionNode) ((IStructuredSelection)selection).getFirstElement();
				showMessage(obj.getDetails());
				
			}
		};
		getFunctionDetails.setText("Get Details");
		
		
		getComponentDetails = new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				ComponentNode obj =(ComponentNode) ((IStructuredSelection)selection).getFirstElement();
				showMessage(obj.getDetails());
			}
		};
		getComponentDetails.setText("Get Details");
		
		getDescription = new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				ComponentNode obj =(ComponentNode) ((IStructuredSelection)selection).getFirstElement();
				//showMessage(obj.getDetails());
				CFMLPropertyManager propMan = new CFMLPropertyManager();
				
				try {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
					String rootURL = propMan.defaultProjectURL();
					
					URL projURL = new URL(rootURL);
					
					 browser.setUrl("http://" + projURL.getHost() + "/CFIDE/componentutils/cfcexplorer.cfc?METHOD=getcfcinhtml&NAME=" + obj.getPackageName());
					 browser.setFocus();
				} catch (PartInitException e) {
					e.printStackTrace();
				} catch (MalformedURLException mue){
					mue.printStackTrace();
				}
				
			}
		};
		getDescription.setText("Get Description");
		
		action1 = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				FunctionNode obj =(FunctionNode) ((IStructuredSelection)selection).getFirstElement();
				showMessage(obj.getCreateObjectSnippet());
			}
		};
		action1.setText("Insert CreateObject");
		action1.setToolTipText("Inserts CreateObject code");
		
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				
				if(obj instanceof ComponentNode){
					ComponentNode component = (ComponentNode)obj;
					GenericOpenFileAction openAction = new GenericOpenFileAction(component.getFile());
					openAction.run();
				}
				else if(obj instanceof FunctionNode){
					FunctionNode fnode = (FunctionNode)obj;
					insert(fnode.getInvokeSnippet());
				}
				
				
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Package View",
			message);
	}
	private void insert(String insertString) {
	    if(viewer.getSelection().isEmpty()) 
		{
			return;
		}
		else 
		{
			IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			IDocument doc =  ((ITextEditor)iep).getDocumentProvider().getDocument(iep.getEditorInput());
			ITextEditor ite = (ITextEditor)iep;
			ISelection sel = ite.getSelectionProvider().getSelection();
			int cursorOffset = ((ITextSelection)sel).getOffset();
			
			try {
				doc.replace(cursorOffset, 0, insertString);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}