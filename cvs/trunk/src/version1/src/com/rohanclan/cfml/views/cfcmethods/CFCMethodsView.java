package com.rohanclan.cfml.views.cfcmethods;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import com.rohanclan.cfml.editors.ICFDocument;
import org.eclipse.ui.texteditor.ITextEditor;
import com.rohanclan.cfml.util.CFPluginImages;
//import org.eclipse.swt.events.MouseTrackListener;
//import org.eclipse.swt.events.MouseEvent;

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

public class CFCMethodsView extends ViewPart implements IPartListener, IPropertyListener {
	/** the id for this view (so it can be set in the perspective */
	public static final String ID_CFCMETHODVIEW = "com.rohanclan.cfml.views.cfcmethods.CFCMethodsView";
	
	private TableViewer viewer;
	private Action jumpToMethod;
	private Action selectMethod;
	private Action sortMethodsAction;
	private Action doubleClickAction;
	private boolean visible = false;
	private boolean sortItems = false;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	

	/**
	 * The constructor.
	 */
	public CFCMethodsView() {
		
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new CFCMethodsContentProvider(getRootInput(), sortItems));
		viewer.setLabelProvider(new CFCMethodsLabelProvider());
		//viewer.setSorter(new CFCMethodsNameSorter());
		viewer.setInput(getRootInput());
		try {
			IWorkbenchPartSite site = getSite();
			IWorkbenchWindow window =  site.getWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IEditorPart iep = page.getActiveEditor();
			//iep.addPropertyListener(this);
			page.addPartListener(this);
		}
		catch (Exception e) {
			//e.printStackTrace(System.err);
		}
		
		/*
		try {
			this.getViewSite().getShell().addMouseTrackListener(this);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
		*/
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CFCMethodsView.this.fillContextMenu(manager);
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
		manager.add(sortMethodsAction);
		manager.add(jumpToMethod);
		manager.add(selectMethod);
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(sortMethodsAction);
		manager.add(jumpToMethod);
		manager.add(selectMethod);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(sortMethodsAction);
		manager.add(jumpToMethod);
		manager.add(selectMethod);
	}

	private void makeActions() {
		jumpToMethod = new Action() {
			public void run() {
				jumpToItem();
			}
		};
		jumpToMethod.setText("Jump To Method");
		jumpToMethod.setToolTipText("Jump to the currently selected method");
		jumpToMethod.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW));


		selectMethod = new Action() {
			public void run() {
				selectAndViewItem();
			}
		};
		selectMethod.setText("Jump To and Select");
		selectMethod.setToolTipText("Jump to the currently selected method and select its contents.");
		selectMethod.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SHOW_AND_SELECT));
		


		sortMethodsAction = new Action() {
			public void run() {
				sortMethods();
			}
		};
		sortMethodsAction.setText("Toggle method sorting");
		sortMethodsAction.setToolTipText("Toggle the sort order of the methods from natural to alphabetic.");
		sortMethodsAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SORTAZ));
		

		doubleClickAction = jumpToMethod;
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	/*
	 
	public void mouseEnter(MouseEvent e) {
	//System.out.println("Mouse entered viewer");
	}
	
	public void mouseHover(MouseEvent e) {
	//System.out.println("Mouse hovered over viewer");
	}
	
	public void mouseExit(MouseEvent e) {
	//System.out.println("Mouse exited viewer");
	}
	
	*/
	
	private void jumpToItem() {
		CFCMethodViewItem selectedMethod;
		if(viewer.getSelection().isEmpty()) 
		{
			return;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
			selectedMethod = (CFCMethodViewItem)selection.getFirstElement();
			IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			ITextEditor ite = (ITextEditor)iep;
			ite.setHighlightRange(selectedMethod.getDocumentOffset(),0,true);
			ite.setFocus();
		}
	}
	
	
	
	private void selectAndViewItem() {
		CFCMethodViewItem selectedMethod;
		if(viewer.getSelection().isEmpty()) 
		{
			return;
		}
		else 
		{
			IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
			selectedMethod = (CFCMethodViewItem)selection.getFirstElement();
			IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			ITextEditor ite = (ITextEditor)iep;
			ite.selectAndReveal(selectedMethod.getDocumentOffset(),selectedMethod.getSize(ite));
			ite.setFocus();
		}
	}
	
	
	
	private void sortMethods() {
		CFCMethodViewItem selectedMethod;
		if(this.sortItems) {
		    this.sortItems = false;
		} else {
		    this.sortItems = true;
		}
		reload();
	}
	
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"CFC Methods View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private ICFDocument getRootInput() {

		try {
			IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			ITextEditor ite = (ITextEditor)iep; 
			ICFDocument icfd = (ICFDocument)ite.getDocumentProvider().getDocument(iep.getEditorInput());

		return icfd;
		}
		catch (Exception e) {
			//System.err.println("No valid CF document found for CFCMethodsView");
			return null;
		}
	}
	
	
	public void reload() {
		
		try {
			IWorkbenchPartSite site = getSite();
			IWorkbenchWindow window =  site.getWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IEditorPart iep = page.getActiveEditor();
			if (iep != null) {
				iep.addPropertyListener(this);
				//System.out.println("CFCMethods View updated");
				viewer.setContentProvider(new CFCMethodsContentProvider(getRootInput(),sortItems));
				viewer.setInput(getRootInput());
			}
			else {
				viewer.setContentProvider(new CFCMethodsContentProvider(null,sortItems));
				viewer.setInput(null);
			}
			
		}
		catch (Exception e) {
			System.err.println("Couldn't add property listener to editor.");
		}
		
	}
	
	
	
	
	public void partActivated(IWorkbenchPart part) {
	//System.out.println("Part activated: "+part.getClass().getName());
		reload();
	}
	
	public void partBroughtToTop(IWorkbenchPart part) {
	//System.out.println("Part brought to top: "+part.getClass().getName());
		//if (!part.equals(this)) {
			reload();
		//}
	}
	
	public void partClosed(IWorkbenchPart part) {
	//System.out.println("Part closed: " + part.getClass().getName());
		
		if (part.equals(this) || part.equals(this.getViewSite())) {
			try {
				getSite().getWorkbenchWindow().getActivePage().removePartListener(this);
			//System.out.println("CFCMethodView listener removed from page");
				getSite().getWorkbenchWindow().getActivePage().getActiveEditor().removePropertyListener(this);
			//System.out.println("CFCMethodView listener removed from editor");
				}
				catch (Exception e) {
					//e.printStackTrace(System.err);
				}		
		}
		else {
			reload();
			
		}
	}
	
	public void partDeactivated(IWorkbenchPart part) {
	//System.out.println("Part deactivated: "+part.getClass().getName());
		if (!part.equals(this)) {
			reload();
		}
		else {
			
			/*
			try {
			getSite().getWorkbenchWindow().getActivePage().removePartListener(this);
			getSite().getWorkbenchWindow().getActivePage().getActiveEditor().removePropertyListener(this);
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
			}
			*/
		}
	}
	
	public void partOpened(IWorkbenchPart part) {
	//System.out.println("Part opened: "+part.getClass().getName());
		//if (!part.equals(this)) {
			reload();
		//}
	}
	
	public void propertyChanged(Object source, int propId) {
		//System.out.println("Property changed: "+source.getClass().getName());
		//if (!part.equals(this)) {
			reload();
		//}
	}
}