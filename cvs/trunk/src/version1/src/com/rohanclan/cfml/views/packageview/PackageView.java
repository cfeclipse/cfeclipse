package com.rohanclan.cfml.views.packageview;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.ide.IIDEActionConstants;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericOpenFileAction;
import com.rohanclan.cfml.parser.docitems.DocItem;


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
 */

public class PackageView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	
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
			if(e1 instanceof FolderNode && !(e2 instanceof FolderNode)) {
				return -1;
			}
			else if(e2 instanceof FolderNode && !(e1 instanceof FolderNode)) {
				return 1;
			}
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
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		initActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
	}

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

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		manager.add(this.makeFolderCF_Root);
		manager.add(this.makeFolderCFCRoot);
		manager.add(this.makeFolderDefault);
		manager.add(this.makeFolderWWWRoot);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void initMakeActions() {
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
	private TreeObject getSelectedDocItem()
	{
		TreeObject selecteditem = null;
		
		//can't do much if nothing is selected
		if(this.viewer.getSelection().isEmpty()) 
		{
			return null;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)this.viewer.getSelection();
			selecteditem = (TreeObject)selection.getFirstElement();
		}
		
		return selecteditem;
	}	
	
	protected void setFolderType(String folderType) {
		TreeObject selectedItem = getSelectedDocItem();
		
		if(selectedItem == null) return;
		if(!(selectedItem instanceof FolderNode)) {
			return;
		}
		
		((FolderNode)selectedItem).setFolderType(folderType);
		
		viewer.update(selectedItem, null);
		
	}
	
	private void initActions() {
		initMakeActions();
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		
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
			//	showMessage("Double-click detected on "+obj.toString());
				if(obj instanceof FileNode) {
					FileNode selectedFile = (FileNode)obj;
					GenericOpenFileAction openAction = new GenericOpenFileAction(selectedFile.getFile());
					openAction.run();
					
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

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}