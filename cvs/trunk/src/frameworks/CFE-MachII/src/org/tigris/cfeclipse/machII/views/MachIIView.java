package org.tigris.cfeclipse.machII.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import com.rohanclan.cfml.editors.actions.GenericOpenFileAction;
import com.rohanclan.cfml.editors.actions.OpenAtMethodAction;
import com.rohanclan.cfml.util.CFPluginImages;

import org.tigris.cfeclipse.machII.views.forms.PropertyForm;
import org.tigris.cfeclipse.machII.views.model.*;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.tigris.cfeclipse.machII.views.model.EventFilterNode;

/**


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

public class MachIIView extends ViewPart {
	private TreeViewer viewer;
//	private DrillDownAdapter drillDownAdapter;
	private Action refreshAction;
	private Action loadConfigFileAction;
	private Action newPropertyAction;
	private Action doubleClickAction;
 
	private Action makeFolderDefault;
	private Action refreshPackage;
	
	//private String fileLocation = "/CFusionRoot/ContactManager/config/mach-ii.xml";
	//private String fileLocation = "file:///C:/mach-ii.xml";
	private IFile sourceFile;

	class NameSorter extends ViewerSorter
	{
		public int compare(Viewer viewer, Object e1, Object e2) {
			if(e1 instanceof TreeObject)
			{
				if(((TreeObject)e1).getParent() instanceof EventHandlerNode)
					return 0;
			}
			
			return super.compare(viewer, e1, e2);
		}
	}

	/**
	 * The constructor.
	 */
	public MachIIView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		//drillDownAdapter = new DrillDownAdapter(viewer);
		try {
			viewer.setContentProvider(new ViewContentProvider(this.sourceFile));
			viewer.setLabelProvider(new ViewLabelProvider());
			viewer.setSorter(new NameSorter());
			viewer.setInput(getViewSite());
			initActions();
			hookContextMenu();
			hookDoubleClickAction();
			contributeToActionBars();

		} catch(Throwable ex) {
			ex.printStackTrace();
		}

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				MachIIView.this.fillContextMenu(manager);
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
		manager.add(refreshAction);
		manager.add(new Separator());
		manager.add(loadConfigFileAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(new Separator());
		manager.add(this.newPropertyAction);

		//drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(loadConfigFileAction);
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
	}

	private void initMakeActions() {
	
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

	private void refreshTree()
	{
		viewer.setContentProvider(new ViewContentProvider(this.sourceFile));
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.refresh();
	}

	private void initActions() {
		initMakeActions();

		refreshAction = new Action() {
			public void run() {
				refreshTree();
				showMessage("Refreshed");
			}
		};
		refreshAction.setText("Refresh");
		refreshAction.setToolTipText("Refresh from file");
		refreshAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH));

		loadConfigFileAction = new Action() {
			public void run() {
				handleAction2Execute();
			}
		};
		loadConfigFileAction.setText("Load config file");
		loadConfigFileAction.setToolTipText("Load a Mach II config file");
		loadConfigFileAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));

		doubleClickAction = new Action() {
			public void run() {
				handleDoubleClick();
			}
		};
		
		
		this.newPropertyAction = new Action() {
			public void run() {
				handleNewProperty();
			}
		};
	}
	
	private MachIIRoot getMIIRoot()
	{
		return (MachIIRoot)this.viewer.getTree().getTopItem().getData();
	}
	
	private void handleNewProperty()
	{
		PropertyForm form = new PropertyForm(this.getViewSite().getShell());
		if(form.open() == PropertyForm.OK)
		{
			PropertyNode newProp = new PropertyNode(form.getName(), form.getValue());
			MachIIRoot root = getMIIRoot();
			root.addProperty(newProp);
			saveTree();
		}
	}
	
	private void saveTree()
	{
		MachIIWriter writer = new MachIIWriter();
		//writer.saveXml("c:\\out.xml", getMIIRoot());
	}
	
	private void handleAction2Execute()
	{
		ResourceListSelectionDialog listSelection = null;
		try {
			listSelection = new ResourceListSelectionDialog(this.getViewSite().getShell(),
					ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
			listSelection.setMessage("Select location of the Mach II config file");
			String initialSelect [] = new String[1];
			initialSelect[0] = "mach-ii.xml";
			listSelection.setInitialSelections(initialSelect);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(listSelection.open() == ResourceListSelectionDialog.OK)
		{
			Object[] result = listSelection.getResult();
			if(result.length == 1)
			{
				IResource resource = (IResource)result[0];
				String s = resource.getProjectRelativePath().toString();
				//this.fileLocation = resource.getLocation().toOSString();
				if(resource instanceof IFile)
				{
					this.sourceFile = (IFile)resource;
					refreshTree();
				}
			}
		}	
	}
	
	private void handlePageViewDlbClick(PageViewNode node)
	{
		MachIIRoot rootNode = (MachIIRoot)node.getParent().getParent();
		String appRoot = rootNode.getAppRoot();
		//
		// The assumption is that the entire CFE project is the Mach-II project.
		// Therefore the app root should simply be the name of the project +
		// the app root.
		if(appRoot.startsWith("/"))
			appRoot = appRoot.substring(1);
		
		IFolder appRootFolder = this.sourceFile.getProject().getFolder(appRoot);
		if(!appRootFolder.exists())
		{
			showMessage("Application root does not exist.");
			return;
		}
		String typeLocation = node.getPage();
		IFile targetFile = appRootFolder.getFile(typeLocation);
		if(!targetFile.exists())
		{
			showMessage("Cannot find template \'" + node.getPage() + "\'");
		}
		GenericOpenFileAction openAct = new GenericOpenFileAction(targetFile);
		openAct.run();		
	}
	
	private IFile getCFCFile(String cfcName, String appRoot)
	{
		//
		// The assumption is that the entire CFE project is the Mach-II project.
		// Therefore the app root should simply be the name of the project +
		// the app root.
		if(appRoot.startsWith("/"))
			appRoot = appRoot.substring(1);
		
		IProject appRootFolder = this.sourceFile.getProject();
		if(!appRootFolder.exists())
		{
			return null;
		}
		String typeLocation = cfcName;
		typeLocation = typeLocation.replace('.', '/');

		IFile targetFile = appRootFolder.getFile(typeLocation + ".cfc");
		
		return targetFile;
	}
	
	private void openCFCForUser(String cfcName, String appRoot)
	{
		IFile targetFile = getCFCFile(cfcName, appRoot);
		if(targetFile == null || !targetFile.exists())
		{
			showMessage("Cannot find component \'" + cfcName + "\'");
			return;
		}
		GenericOpenFileAction openAct = new GenericOpenFileAction(targetFile);
		openAct.run();
	}
	
	private void handleListenerDblClick(ListenerNode node)
	{
		openCFCForUser(node.getType(), getAppRootFromSecondLevelNode(node));
	}
	
	private MachIIRoot getObjectRoot(TreeObject startNode)
	{
		TreeObject currObj = startNode;
		while(currObj != null)
		{
			if(currObj instanceof MachIIRoot)
				return (MachIIRoot)currObj;
			currObj = currObj.getParent();
		}
		return null;
	}
	
	private String getAppRootFromSecondLevelNode(TreeObject node)
	{
		MachIIRoot rootNode = (MachIIRoot)node.getParent().getParent();
		return rootNode.getAppRoot();
	}
	
	private void handleEventFilterDlbClick(EventFilterNode node)
	{
		openCFCForUser(node.getValue(), ((MachIIRoot)node.getParent().getParent()).getAppRoot());
	}
	
	private void handleNotifyNodeDblClick(NotifyNode targetNode)
	{
		MachIIRoot m2root;
		try {
			//m2root = getMIIRoot();
			m2root = getObjectRoot(targetNode);
		} catch(Exception ex) {
			ex.printStackTrace();
			return;
		}
		ListenersNode listenersParent = m2root.getListeners();
		TreeObject children[] = listenersParent.getChildren();
		ListenerNode searchNode = null;
		boolean found = false;
		for(int i = 0; i < children.length; i++)
		{
			ListenerNode currNode = (ListenerNode)children[i];
			String listenerType = currNode.getName();
			String targetType = targetNode.getListener();
			
			if(listenerType.equals(targetType))
			{
				found = true;
				searchNode = currNode;
				break;
			}
		}
		if(!found)
			showMessage("Could not find listener \'" + targetNode.getListener() + "\'");
		
		//openCFCForUser(searchNode.getType(), getAppRootFromSecondLevelNode(node));
		IFile targetFile = getCFCFile(searchNode.getType(), getAppRootFromSecondLevelNode(targetNode.getParent()));
		String method = targetNode.getMethod();
		if(method == null)
			return;
		
		OpenAtMethodAction openAct = new OpenAtMethodAction();
		openAct.setFile(targetFile);
		openAct.setMethodName(method);
		openAct.run();
	}
	
	private void handleDoubleClick()
	{
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		if(obj instanceof ListenerNode)
		{
			handleListenerDblClick((ListenerNode)obj);
		}
		else if(obj instanceof PageViewNode)
		{
			handlePageViewDlbClick((PageViewNode)obj);
		}
		else if(obj instanceof EventFilterNode)
		{
			handleEventFilterDlbClick((EventFilterNode)obj);
		}
		else if(obj instanceof EventBeanNode)
		{
			openCFCForUser(((EventBeanNode)obj).getType(), getAppRootFromSecondLevelNode((EventBeanNode)obj));
		}
		else if(obj instanceof NotifyNode) 
		{
			handleNotifyNodeDblClick((NotifyNode)obj);
		}
	}

	//private void handleEventFilterDblClick(Event
	
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
			"Mach II Explorer",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	
	public void refresh(){

	    viewer.refresh();

	}
}