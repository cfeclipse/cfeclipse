package com.rohanclan.cfml.views.cfcmethods;


import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import com.rohanclan.cfml.editors.ICFDocument;
import org.eclipse.ui.texteditor.ITextEditor;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.editors.actions.Encloser;
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
	private Action refreshAction;
	private Action sortMethodsAction;
	private Action doubleClickAction;
	private Action pinAction;
	private Action insertAction;
	private Action createInsightAction;
	private Action togglePublicAction;
	private Action togglePrivateAction;
	private Action togglePackageAction;
	private Action toggleRemoteAction;
	private boolean autoRefresh = true;
	private boolean visible = false;
	private boolean sortItems = false;
	private boolean showRemote = true;
	private boolean showPublic = true;
	private boolean showPackage = true;
	private boolean showPrivate = true;
	private IFile CFCMethodsFile = null;
	private Text fileLabel;
	private CFCMethodsContentProvider methodProvider;
	private ICFDocument lastInput = null;
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
	    GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
		
		
		fileLabel = new Text(parent, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		// layout the label above the method list
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		FontData labelFontData = new FontData();
		labelFontData.setStyle(SWT.BOLD);
		labelFontData.setHeight(8);
		Font labelFont = new Font(parent.getDisplay(),labelFontData);
		fileLabel.setFont(labelFont);
		fileLabel.setLayoutData(layoutData);

		lastInput = getRootInput();
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new CFCMethodsContentProvider(lastInput, sortItems, showRemote, showPublic, showPackage, showPrivate));
		viewer.setLabelProvider(new CFCMethodsLabelProvider());
		
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(layoutData);
		
		
		//viewer.setSorter(new CFCMethodsNameSorter());
		viewer.setInput(lastInput);
		try {
			IWorkbenchPartSite site = getSite();
			
			IWorkbenchPage page = site.getPage();
			
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
	    manager.add(createInsightAction);
	    manager.add(insertAction);
		manager.add(pinAction);
		manager.add(refreshAction);
		manager.add(sortMethodsAction);
		manager.add(jumpToMethod);
		manager.add(selectMethod);
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
	    manager.add(insertAction);
		manager.add(pinAction);
		manager.add(refreshAction);
		manager.add(sortMethodsAction);
		manager.add(jumpToMethod);
		manager.add(selectMethod);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
	    manager.add(toggleRemoteAction);
	    manager.add(togglePublicAction);
	    manager.add(togglePackageAction);
	    manager.add(togglePrivateAction);
	    manager.add(new Separator());

	    manager.add(insertAction);
		manager.add(pinAction);
		manager.add(refreshAction);
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
		


		refreshAction = new Action() {
			public void run() {
				reload(true);
			}
		};
		refreshAction.setText("Refresh view");
		refreshAction.setToolTipText("Rebuild the view from the contents of the current file.");
		refreshAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH));
		


		pinAction = new Action(null, IAction.AS_CHECK_BOX) {
			public void run() {
				pin();
			}
		};
		pinAction.setText("Pin view");
		pinAction.setToolTipText("Don't automatically update the method list when the editor changes.");
		pinAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_PIN));
		pinAction.setChecked(!autoRefresh);



		


		insertAction = new Action() {
			public void run() {
				insert();
			}
		};
		insertAction.setText("Insert");
		insertAction.setToolTipText("Insert at current cursor position.");
		insertAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_INSERT_SNIP));



		


		createInsightAction = new Action() {
			public void run() {
				createInsightXML();
			}
		};
		createInsightAction.setText("Create Insight");
		createInsightAction.setToolTipText("Create Insight XML and insert at current cursor position.");
		createInsightAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_ALERT));
		


		sortMethodsAction = new Action(null, IAction.AS_CHECK_BOX) {
			public void run() {
				sortMethods();
			}
		};
		sortMethodsAction.setText("Toggle method sorting");
		sortMethodsAction.setToolTipText("Toggle the sort order of the methods from natural to alphabetic.");
		sortMethodsAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_SORTAZ));



		toggleRemoteAction = new Action(null, IAction.AS_CHECK_BOX) {
			public void run() {
				toggleRemote();
			}
		};
		toggleRemoteAction.setText("Show/Hide remote methods");
		toggleRemoteAction.setToolTipText("Show/Hide all remote methods.");
		toggleRemoteAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_METHOD_REMOTE));



		togglePublicAction = new Action(null, IAction.AS_CHECK_BOX) {
			public void run() {
				togglePublic();
			}
		};
		togglePublicAction.setText("Show/Hide public methods");
		togglePublicAction.setToolTipText("Show/Hide all public methods.");
		togglePublicAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_METHOD_PUBLIC));




		togglePackageAction = new Action(null, IAction.AS_CHECK_BOX) {
			public void run() {
				togglePackage();
			}
		};
		togglePackageAction.setText("Show/Hide package methods");
		togglePackageAction.setToolTipText("Show/Hide all package methods.");
		togglePackageAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_METHOD_PACKAGE));




		togglePrivateAction = new Action(null, IAction.AS_CHECK_BOX) {
			public void run() {
				togglePrivate();
			}
		};
		togglePrivateAction.setText("Show/Hide private methods");
		togglePrivateAction.setToolTipText("Show/Hide all private methods.");
		togglePrivateAction.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_METHOD_PRIVATE));
		

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
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try  {
				IEditorPart iep = IDE.openEditor(page,CFCMethodsFile,true);
				//IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
				ITextEditor ite = (ITextEditor)iep;
				ite.setHighlightRange(selectedMethod.getDocumentOffset(),0,true);
				ite.setFocus();}
			catch (Exception e) {
			    MessageBox msg = new MessageBox(page.getActivePart().getSite().getShell());
	            msg.setText("Error!");
	            msg.setMessage(e.getMessage());
	            msg.open();
			}
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
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try  {
				IEditorPart iep = IDE.openEditor(page,CFCMethodsFile,true);
				//IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
				ITextEditor ite = (ITextEditor)iep;
				ite.selectAndReveal(selectedMethod.getDocumentOffset(),selectedMethod.getSize(ite));
				ite.setFocus();
			}
			catch (Exception e) {
			    MessageBox msg = new MessageBox(page.getActivePart().getSite().getShell());
	            msg.setText("Error!");
	            msg.setMessage(e.getMessage());
	            msg.open();
			}
		}
	}
	
	
	
	private void sortMethods() {
		CFCMethodViewItem selectedMethod;
		if(this.sortItems) {
		    this.sortItems = false;
		    sortMethodsAction.setChecked(false);
		} else {
		    this.sortItems = true;
		    sortMethodsAction.setChecked(true);
		}
		reload(false);
	}
	
	
	
	private void toggleRemote() {
		if (this.showRemote) {
		    this.showRemote = false;
		}
		else {
		    this.showRemote = true;
		}
		reload(false);
	}
	
	
	
	private void togglePublic() {
		if (this.showPublic) {
		    this.showPublic = false;
		}
		else {
		    this.showPublic = true;
		}
		reload(false);
	}
	
	
	
	private void togglePackage() {
		if (this.showPackage) {
		    this.showPackage = false;
		}
		else {
		    this.showPackage = true;
		}
		reload(false);
	}
	
	
	
	private void togglePrivate() {
		if (this.showPrivate) {
		    this.showPrivate = false;
		}
		else {
		    this.showPrivate = true;
		}
		reload(false);
	}
	
	
	
	private void pin() {
	    if (autoRefresh) {
	        autoRefresh = false;
	    }
	    else {
	        autoRefresh = true;
	    }
	}
	
	private void insert() {
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
			IDocument doc =  ((ITextEditor)iep).getDocumentProvider().getDocument(iep.getEditorInput());
			ITextEditor ite = (ITextEditor)iep;
			ISelection sel = ite.getSelectionProvider().getSelection();
			int cursorOffset = ((ITextSelection)sel).getOffset();
			int selectionLength = ((ITextSelection)sel).getLength();
			Encloser encloser = new Encloser();
			encloser.enclose(doc,(ITextSelection)sel,selectedMethod.getInsertString(),"");
			
		}
	}
	
	private void createInsightXML() {
	    CFCMethodViewItem selectedMethod;
	    int i = 0;
		try {
	    String insight = "";
	    
	    String path = CFCMethodsFile.getFullPath().toString().replace('/','.');
	    String[] pathArray = path.split("\\.");
	    path = "";
	    for (i=0 ; i<pathArray.length -1; i++ ) {
	        if (path.length() > 0) {
	            path += ".";    
	        }
	         path += pathArray[i];
	    }
	    
	    
	    insight += "\t\t<!-- The path and framework attributes are not currently used.\n";
	    insight += "\t\tThe idea is that we should be able to use the path when a component is passed as an argument to a function.\n";
	    insight += "\t\tThe framework attribute is there so that you can specify the framework type used for a project and only get insight for that framework -->\n";
	    insight += "\t\t<component creator=\"8\" path=\"" + path + "\" framework=\"\">\n";
		insight += "\t\t\t<!--\n";
		insight += "\t\t\tThe fully scoped path to this component instance.\n";
		insight += "\t\t\te.g. session.user\n";
	    insight += "\t\t\t<scope value=\"\" />-->\n";
		insight += "\t\t\t<help><![CDATA[\n";
		insight += "\t\t\t\t \n";
		insight += "\t\t\t]]></help>\n";
		insight += "\n";
		Object[] elements = methodProvider.getElements(new Object());
		
		
		
			for (i=0; i< elements.length; i++) {
			    if (elements[i] instanceof CFCMethodViewItem) {
			        insight += ((CFCMethodViewItem)elements[i]).getInsightXML();
			    }
			}
		
		
			insight += "\t</component>\n";
		    
		    
		    IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
			selectedMethod = (CFCMethodViewItem)selection.getFirstElement();
			IEditorPart iep = this.getViewSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			IDocument doc =  ((ITextEditor)iep).getDocumentProvider().getDocument(iep.getEditorInput());
			ITextEditor ite = (ITextEditor)iep;
			ISelection sel = ite.getSelectionProvider().getSelection();
			int cursorOffset = ((ITextSelection)sel).getOffset();
			int selectionLength = ((ITextSelection)sel).getLength();
			Encloser encloser = new Encloser();
			encloser.enclose(doc,(ITextSelection)sel,insight,"");
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
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
	
	
	public void reload(boolean forced) {

		try {
			if (autoRefresh || forced) {
			    lastInput = getRootInput();
			    IWorkbenchPartSite site = getSite();
				IWorkbenchWindow window =  site.getWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				IEditorPart iep = page.getActiveEditor();
			    if (iep != null) {
				    CFCMethodsFile = ((FileEditorInput)iep.getEditorInput()).getFile();
				    fileLabel.setText(CFCMethodsFile.getFullPath().toString());
				}
				else {
				    CFCMethodsFile = null;
				    fileLabel.setText("No methods are available.");
				}
			}
		

			methodProvider = new CFCMethodsContentProvider(lastInput,sortItems,showRemote,showPublic,showPackage,showPrivate);
			viewer.setContentProvider(methodProvider);
			viewer.setInput(lastInput);
			
			
			
			
		}
		catch (Exception e) {
		    //e.printStackTrace();
			//System.err.println("Couldn't add property listener to editor.");
		}
	//}
		
	}
	
	
	
	
	public void partActivated(IWorkbenchPart part) {
	//System.out.println("Part activated: "+part.getClass().getName());
		reload(false);
	}
	
	public void partBroughtToTop(IWorkbenchPart part) {
	//System.out.println("Part brought to top: "+part.getClass().getName());
		//if (!part.equals(this)) {
			reload(false);
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
			reload(false);
			
		}
	}
	
	public void partDeactivated(IWorkbenchPart part) {
	//System.out.println("Part deactivated: "+part.getClass().getName());
		if (!part.equals(this)) {
			reload(false);
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
			reload(false);
		//}
	}
	
	public void propertyChanged(Object source, int propId) {
		//System.out.println("Property changed: "+source.getClass().getName());
		//if (!part.equals(this)) {
			reload(false);
		//}
	}
}