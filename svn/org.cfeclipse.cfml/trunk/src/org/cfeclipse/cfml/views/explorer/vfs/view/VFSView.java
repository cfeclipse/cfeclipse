/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cfeclipse.cfml.views.explorer.vfs.view;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.program.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.*;
import java.util.*;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.commons.vfs.util.Os;

import org.cfeclipse.cfml.editors.CFJavaFileEditorInput;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.images.ColumnViewerToolTipSupport;
import org.cfeclipse.cfml.images.ToolTip;
import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.net.RemoteFileEditorInput;
import org.cfeclipse.cfml.preferences.FtpConnectionDialog;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.cfeclipse.cfml.views.explorer.FileExplorerView;
import org.cfeclipse.cfml.views.explorer.vfs.FileOperation;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.EditMenu;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.FileMenu;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.EditMenu.CopyAction;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.EditMenu.PasteAction;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.FileMenu.ConnectionAction;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.FileMenu.DeleteAction;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.FileMenu.NewFileAction;
import org.cfeclipse.cfml.views.explorer.vfs.view.menus.FileMenu.NewFolderAction;


/**
 * File Viewer example
 */
public class VFSView extends ViewPart implements IShowInTarget 
{ 
	public static final String ID_FILEEXPLORER = "org.cfeclipse.cfml.views.explorer.vfs.view.VFSView";
	private static final String RESOURCE_BUNDLE= VFSView.class.getName();
	private static ResourceBundle resourceBundle= ResourceBundle.getBundle(RESOURCE_BUNDLE);

	private final static String DRIVE_A = "a:" + FileName.SEPARATOR;
//	private final static String DRIVE_B = "b:" + FileName.SEPARATOR;
	private static boolean initial = true;
 	
	/* UI elements */ 	
	private Display display; 
	private Shell shell;
//	private ToolBar toolBar;

	private Label numObjectsLabel;
	private Label diskSpaceLabel;
	
	/* currently selected directory */
	private FileObject currentDirectory = null;
	
	/* Drag and drop optimizations */
	private boolean isDragging = false; // if this app is dragging
	private boolean isDropping = false; // if this app is dropping

//	private FileObject[]  processedDropFiles = null; // so Drag only deletes what it needs to
	private FileObject[]  deferredRefreshFiles = null;      // to defer notifyRefreshFiles while we do DND

	private boolean deferredRefreshRequested = false; // to defer notifyRefreshFiles while we do DND

	/* Combo view */
	private static final String COMBODATA_ROOTS = "Combo.roots";
		// File[]: Array of files whose paths are currently displayed in the combo
	private static final String COMBODATA_LASTTEXT = "Combo.lastText";
		// String: Previous selection text string
	private static final String COMBODATA_LASTCONNECTIONID = "Combo.lastConnectionId";
	// String: Previous connectionid

	private Combo combo;

	/* Tree view */
	static public ImageRegistry iconCache = CFPluginImages.getImageRegistry();
	
	public static final String TREEITEMDATA_FILE = "TreeItem.file";
		// File: File associated with tree item
	public static final String TREEITEMDATA_IMAGEEXPANDED = "TreeItem.imageExpanded";
		// Image: shown when item is expanded
	public static final String TREEITEMDATA_IMAGECOLLAPSED = "TreeItem.imageCollapsed";
		// Image: shown when item is collapsed
	private static final String TREEITEMDATA_STUB = "TreeItem.stub";
		// Object: if not present or null then the item has not been populated

	public static final String TREEITEMDATA_URI = "TreeItem.uri";
		// VFS URI associated with table row
	public static final String TREEITEMDATA_CONNECTIONID = "TableItem.connectionId";
		// connection id 

	private static Tree tree;
	private Label treeScopeLabel;

	/* Table view */
	protected static final DateFormat dateFormat = DateFormat.getDateTimeInstance(
		DateFormat.MEDIUM, DateFormat.MEDIUM);

	public static final String TABLEITEMDATA_FILE = "TableItem.file";
		// File: File associated with table row
	public static final String TABLEDATA_DIR = "Table.dir";
		// File: Currently visible directory

	private static final int[] tableWidths = new int[] {150, 60, 75, 150};
		public static final String TABLEITEMDATA_CONNECTIONID = "Table.connectionId";
		protected static final String TABLEDATA_CONNECTIONID = "TableItem.connectionId";
	private final String[] tableTitles = new String [] {
		VFSView.getResourceString("table.Name.title"),
		VFSView.getResourceString("table.Size.title"),
		VFSView.getResourceString("table.Type.title"),
		VFSView.getResourceString("table.Modified.title")
	};
	private Table table;
	
	/* File Items Tabs */
	private TabItem tabFileItems; 
//	private TabItem tabTrasnferItems;
	
	/* Table update worker */
	// Control data
	private final Object workerLock = new Object();
		// Lock for all worker control data and state
	private volatile Thread  workerThread = null;
		// The worker's thread
	private volatile boolean workerStopped = false;
		// True if the worker must exit on completion of the current cycle
	private volatile boolean workerCancelled = false;
		// True if the worker must cancel its operations prematurely perhaps due to a state update

	// Worker state information -- this is what gets synchronized by an update
	private volatile FileObject workerStateDir = null;

	// State information to use for the next cycle
	private volatile FileObject workerNextDir = null;

	// Comnnons VFS Manager
	private FileSystemManager fsManager = null;
	public TreeMap<String, FTPConnectionProperties> fConnections;
	private String currentConnectionId;
	private String workerNextConnectionId;
	protected String workerStateConnectionId;
	public Object fSourceConnectionId;
	private ConnectionAction connectionAction;
	private DeleteAction deleteAction;
	private CopyAction copyAction;
	private PasteAction pasteAction;
	private NewFolderAction newFolderAction;
	private NewFileAction newFileAction;
	//private static boolean VFS_DEBUG = System.getProperty("org.cfeclipse.cfml.views.explorer.vfs.debug") != null;
	private static boolean VFS_DEBUG = true;
			
	public static void error (String s) {
		if ( VFS_DEBUG )
			System.err.println(s);
	}

	public static void debug (Exception e) {
		if ( VFS_DEBUG )
			e.printStackTrace();
	}
	
	public Tree getTree () {
		return tree;
	}
	
	public Table getTable () {
		return table;
	}
	
	/**
	 * Returns the Commons VFS File System Manager
	 */
	public FileSystemManager getFileSystemManager ()
	{
		return fsManager;
	}
	
	/**
	 * Closes the main program.
	 */
	public void dispose() {
		workerStop();
		//return super.close();
	}
	
	/**
	 * Returns a string from the resource bundle.
	 * We don't want to crash because of a missing String.
	 * Returns the key if not found.
	 */
	public static String getResourceString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}			
	}

	/**
	 * Returns a string from the resource bundle and binds it
	 * with the given arguments. If the key is not found,
	 * return the key.
	 */
	public static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}

	/**
	 * Returns a string from the resource bundle and binds it
	 * with the given arguments. If the key is not found,
	 * return the key.
	 */
	public static String getResourceString(String key, Object arg) {
		try {
			return MessageFormat.format(getResourceString(key), new Object []{arg});
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}
		
	/**
	 * Returns a new menu manager for the window
	 */
	protected MenuManager createMenuManager()
	{
		MenuManager menuManager = (MenuManager) getViewSite().getActionBars().getMenuManager();
		// File
		MenuManager fileMenu =new MenuManager(getResourceString("menu.File.text"));
		connectionAction = new FileMenu.ConnectionAction(this
				, getResourceString("menu.File.New.text")
				, getResourceString("menu.File.New.text") );
		fileMenu.add(connectionAction);

		deleteAction = new FileMenu.DeleteAction(this
				, getResourceString("menu.Connection.Delete.text")
				, getResourceString("menu.Connection.Delete.text") );
		fileMenu.add(deleteAction);

		newFolderAction = new FileMenu.NewFolderAction(this
				, getResourceString("menu.File.NewFolder.text")
				, getResourceString("menu.File.NewFolder.text"));
		fileMenu.add(newFolderAction);
		
		newFileAction = new FileMenu.NewFileAction(this
				, getResourceString("menu.File.NewFile.text")
				, getResourceString("menu.File.NewFile.text"));
		fileMenu.add(newFileAction);

		// Edit
		MenuManager editMenu =new MenuManager(getResourceString("menu.Edit.text"));
		copyAction = new EditMenu.CopyAction(this
				, getResourceString("menu.Edit.copy.text")
				, getResourceString("menu.Edit.copy.text") );
		editMenu.add(copyAction);
		
		pasteAction = new EditMenu.PasteAction(this
				, getResourceString("menu.Edit.paste.text")
				, getResourceString("menu.Edit.paste.text"));
		editMenu.add(pasteAction);
		

		
		menuManager.add(fileMenu);
		menuManager.add(editMenu);

		// hook the tree context menu
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				VFSView.this.fillContextMenu(manager,new Action[]{connectionAction,deleteAction,copyAction,pasteAction,newFolderAction,newFileAction});
			}
		});
		Menu menu = menuMgr.createContextMenu(tree);
		tree.setMenu(menu);

		// hook the table context menu
		MenuManager tabMenuMgr = new MenuManager("#PopupMenu");
		tabMenuMgr.setRemoveAllWhenShown(true);
		tabMenuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				VFSView.this.fillContextMenu(manager,new Action[]{copyAction,pasteAction,newFolderAction,newFileAction});
			}
		});
		Menu tabMenu = tabMenuMgr.createContextMenu(table);
		table.setMenu(tabMenu);
		
		return menuManager;
	}

    private void fillContextMenu(IMenuManager manager,Action[] actions) {
		//Somehow we have to know what this item is
    	for(Action action:actions) {    		
    		manager.add(action);
    	}		
	}
	
	 /**
	   * Helper method to calculate the size of the cool item
	   * 
	   * @param item the cool item
	   */
	private void calcSize(CoolItem item) {
	    Control control = item.getControl();
	    Point pt = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	    pt = item.computeSize(pt.x, pt.y);
	    item.setSize(pt);
	}
	
	/**
	 * Construct the UI
	 * 
	 * @param container the ShellContainer managing the Shell we are rendering inside
	 */
    public void createPartControl(Composite parent) 
	{
    	display = parent.getDisplay();
		shell = getSite().getShell();
		fConnections = VFSUtil.loadFileSystems();
		try {
			fsManager = VFS.getManager();
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
//		createComboView(parent, gridData); // shell, gridData);
		
		CoolBar coolbar = new CoolBar(container, SWT.NONE);
		coolbar.setLayoutData(gridData);

	    // Create a toolbar coolitem
		final CoolItem item1 = new CoolItem(coolbar, SWT.NONE);
	    item1.setControl(createToolBar(coolbar));
	    calcSize(item1);
		
		// Create a combo coolitem
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 185;
		
	    final CoolItem item2 = new CoolItem(coolbar, SWT.NONE);
	    item2.setControl(createComboView(coolbar, gridData));
	    calcSize(item2);
	    
		SashForm sashForm = new SashForm(container, SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		sashForm.setLayoutData(gridData);
		
		createTreeView(sashForm);
		createTableView(sashForm);
		
		sashForm.setWeights(new int[] { 2, 5 });

		Composite container1 = new Composite(container, SWT.NONE);
		container1.setLayout(new GridLayout(2, true));
		container1.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));

		numObjectsLabel = new Label(container1, SWT.BORDER);
		numObjectsLabel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));// gridData);

		diskSpaceLabel = new Label(container1, SWT.BORDER);
		diskSpaceLabel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true)); //gridData);
		createMenuManager();
		try {
			notifyRefreshFiles(null);
		} catch (Exception e) {
			debug (e);
		}
		//return container; 
	}
	
	
	/**
	 * Creates the toolbar
	 * 
	 * @param shell the shell on which to attach the toolbar
	 * @param layoutData the layout data
	 */
	private Control createToolBar(final Composite shell) { //, Object layoutData) {
		final ToolBar toolBar = new ToolBar(shell, SWT.FLAT);
//		toolBar.setLayoutData(layoutData);
		
		// New VFS
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(iconCache.get(CFPluginImages.ICON_DRIVE_FTP));//.iconFile]);
		//item.setText(getResourceString("tool.New.vfs.text"));
		item.setToolTipText(getResourceString("tool.New.vfs.tip"));
		
		item.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent e) 
			{
				connectionAction.run();
			}
		});

		// Home
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(iconCache.get(CFPluginImages.ICON_HOME));
		//item.setText(getResourceString("tool.home.text"));
		item.setToolTipText(getResourceString("tool.home.tip"));
		
		item.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent e) 
			{
				try {
					// Jump the file system home directory, some FS don't have homes
					
					String homeDir 	= getCurrentDirectory().getName().getScheme().equals("file") 
							? VFSUtil.USER_HOME
							: (String)getCurrentDirectory().getFileSystem().getAttribute("HOME_DIRECTORY");
					
					// Build a URI from the home dir
					String uri 	= Os.isFamily(Os.OS_FAMILY_UNIX) 
								? getCurrentDirectory().getName().getRootURI() + homeDir
								: getCurrentDirectory().getName().getScheme() + ":///" + homeDir.replace('\\','/');
								
					FileObject fo	= resolveURI(uri,getCurrentConnectionId());
					
					if ( fo.exists() ) 
						notifySelectedDirectory(fo, getCurrentConnectionId());
					else
						VFSUtil.MessageBoxInfo(getResourceString("error.jump.home", new Object[] {homeDir} ));
					
				} catch (FileSystemException ex) {
					FileObject fo;
					try {
						fo = resolveURI(fConnections.get(getCurrentConnectionId()).getURI().toString(),getCurrentConnectionId());
						if ( fo.exists() ) 
							notifySelectedDirectory(fo, getCurrentConnectionId());
						else
							VFSUtil.MessageBoxInfo(getResourceString("error.jump.home", new Object[] {fo.getURL().toString()} ));
					} catch (FileSystemException e1) {
						VFSUtil.MessageBoxInfo(ex.getMessage());
					}
				}
			}
		});
		
		// New folder
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(iconCache.get(CFPluginImages.ICON_FOLDER_NEW));
		//item.setText(getResourceString("tool.New.folder.text"));
		item.setToolTipText(getResourceString("tool.New.folder.tip"));
		
		item.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent e) 
			{
				VFSUtil.newFolder(getCurrentDirectory());
			}
		});
		
		item = new ToolItem(toolBar, SWT.SEPARATOR);
		
		// Parent
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(iconCache.get(CFPluginImages.ICON_FOLDER_PARENT));
		//item.setText(getResourceString("tool.Parent.tiptext"));
		item.setToolTipText(getResourceString("tool.Parent.tiptext"));
		item.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent e) {
				doParent();
			}
		});
		
		// Refresh
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(iconCache.get(CFPluginImages.ICON_REFRESH));
		//item.setText(getResourceString("tool.Refresh.tiptext"));
		item.setToolTipText(getResourceString("tool.Refresh.tiptext"));
		item.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent e) {
				doRefresh();
			}
		});
	
		return toolBar;
	}

	/**
	 * Creates the combo box view.
	 * 
	 * @param parent the parent control
	 */
	private Control createComboView(Composite parent, Object layoutData) 
	{
		combo = new Combo(parent, SWT.NONE);
		combo.setLayoutData(layoutData);
		combo.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) 
			{
				final FileObject[] roots = (FileObject[]) combo.getData(COMBODATA_ROOTS);
				if (roots == null) return;
				int selection = combo.getSelectionIndex();
				
				if (selection >= 0 && selection < roots.length) 
				{
					try {
						System.out.println("combo.widgetSelected " + roots[selection]);
						notifySelectedDirectory(roots[selection],roots[selection].getName().toString());
					} catch (Exception ex) {
						System.err.println("createComboView.widgetSelected: " + ex);
					}
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				final String lastText = (String) combo.getData(COMBODATA_LASTTEXT);
				final String lastConnectionId = (String) combo.getData(COMBODATA_LASTCONNECTIONID);
				String text = combo.getText();
				if (text == null) return;
				if (lastText != null && lastText.equals(text)) return;
				combo.setData(COMBODATA_LASTTEXT, text);
				
				try {
					FileObject file = resolveURI(text,lastConnectionId);
					
					if ( file.exists() )
						notifySelectedDirectory(file,lastConnectionId);
					else {
						VFSUtil.MessageBoxInfo(
								getResourceString("file.not.found", new Object[] {file}) );
						combo.setText(getCurrentDirectory().toString());
					}
					
				} catch (FileSystemException ex) {
					System.err.println("createComboView::widgetDefaultSelected Error: " + ex);
				}
			}
		});
		return combo;
	}

	/**
	 * Creates the file tree view.
	 * 
	 * @param parent the parent control
	 */
	private void createTreeView(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = gridLayout.marginWidth = 2;
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
		composite.setLayout(gridLayout);

		treeScopeLabel = new Label(composite, SWT.BORDER);
		treeScopeLabel.setText(VFSView.getResourceString("details.AllFolders.text"));
		treeScopeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));

		tree = new Tree(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE);
		tree.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

		tree.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) 
			{
				
				final TreeItem[] selection = tree.getSelection();
				if (selection != null && selection.length != 0) {
					TreeItem item = selection[0];
					
					try {
						//FileObject file = (FileObject) item.getData(TREEITEMDATA_FILE);
						String uri = (String) item.getData(TREEITEMDATA_URI);
						String connectionId = (String) item.getData(TREEITEMDATA_CONNECTIONID);
						
						shell.setText(getResourceString("Title.Resolving"
								, new Object[] { uri } )); 

						notifySelectedDirectory(resolveURI(uri, connectionId),connectionId); 
					} 
					catch (FileSystemException e) {
						debug(e); // "createTreeView.widgetSelected " +
						
						VFSUtil.MessageBoxError( VFSUtil.getErrorMessageStack(e));
						VFSUtil.setUiStateIdle(shell, getCurrentDirectory().getName().toString());
					}
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent event) {
				final TreeItem[] selection = tree.getSelection();
				if (selection != null && selection.length != 0) {
					TreeItem item = selection[0];
					String imageName;
					if(item.getExpanded()) {
						imageName = TREEITEMDATA_IMAGECOLLAPSED;						
					} else {
						imageName = TREEITEMDATA_IMAGEEXPANDED;
					}
					item.setExpanded(!item.getExpanded());
					final Image image = (Image) item.getData(imageName);
					if (image != null) item.setImage(image);
					treeExpandItem(item);
				}
			}
		});
		tree.addTreeListener(new TreeAdapter() {
			public void treeExpanded(TreeEvent event) {
				final TreeItem item = (TreeItem) event.item;
				final Image image = (Image) item.getData(TREEITEMDATA_IMAGEEXPANDED);
				if (image != null) item.setImage(image);
				treeExpandItem(item);
			}
			public void treeCollapsed(TreeEvent event) {
				final TreeItem item = (TreeItem) event.item;
				final Image image = (Image) item.getData(TREEITEMDATA_IMAGECOLLAPSED);
				if (image != null) item.setImage(image);
			}
		});
		
		// Listen for key presses
		tree.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) 
			{
				if ( event.keyCode == 127 ) 
				{
					TreeItem item = tree.getSelection()[0];
					String connectionId = (String)item.getData(TREEITEMDATA_CONNECTIONID);
					
					// delete at the root level only
					if ( ! (item.getParentItem() == null) ) return;
					
					if ( connectionId.startsWith("file://"))
						VFSUtil.MessageBoxInfo(getResourceString("remove.local.fs"));
					else {
							deleteAction.run(item);
					}
				}
			}
		});
		
		createTreeDragSource(tree);
		createTreeDropTarget(tree);
	}

	/**
	 * Creates the Drag & Drop DragSource for items being dragged from the tree.
	 * 
	 * @return the DragSource for the tree
	 */
	private DragSource createTreeDragSource(final Tree tree){
		DragSource dragSource = new DragSource(tree, DND.DROP_COPY); // | DND.DROP_MOVE 
		
		// Transfer data type is text (file URI)
		dragSource.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		//dragSource.setTransfer(new Transfer[] { RTFTransfer.getInstance() });
		
		dragSource.addDragListener(new DragSourceListener() {
			TreeItem[] dndSelection = null;
			String[] sourceNames = null;
			
			public void dragStart(DragSourceEvent event){
				dndSelection = tree.getSelection();
				sourceNames = null;
				event.doit = dndSelection.length > 0;
				isDragging = true;
//				processedDropFiles = null;
			}
			
			public void dragFinished(DragSourceEvent event){
				dragSourceHandleDragFinished(event, sourceNames);
				dndSelection = null;
				sourceNames = null;
				isDragging = false;
//				processedDropFiles = null;
//TODO				try {
//					handleDeferredRefresh();
//				} catch (Exception e) {
//					debug ("createTreeDragSource.dragFinished " + e);
//				}
			}
			
			public void dragSetData(DragSourceEvent event){
				if (dndSelection == null || dndSelection.length == 0) return;
				//if (! FileTransfer.getInstance().isSupportedType(event.dataType)) return;
				
				if (! FileTransfer.getInstance().isSupportedType(event.dataType)) {
					//debug("createTreeDragSource.dragSetData File transfer data type is required");
					return;
				}
				
				sourceNames  	= new String[dndSelection.length];
				FileObject file = null;
				String uri		= null;
				String connectionId = null;
				for (int i = 0; i < dndSelection.length; i++) {
					uri 	= (String) dndSelection[i].getData(TREEITEMDATA_URI);
					connectionId = (String) dndSelection[i].getData(TREEITEMDATA_CONNECTIONID);
					try {
						file 	= resolveURI(uri,connectionId);
					} catch (FileSystemException e) {
						debug(e); //"createTreeDragSource.dragSetData " + 
						continue;
					}
					
					/*
					 * In Order for FileTransfer to Work, the name must begin w/
					 * / in UNIX systems
					 */
					sourceNames[i] = Os.isFamily(Os.OS_FAMILY_UNIX)
						? FileName.SEPARATOR + file.toString()
						: file.toString(); 
			
					System.out.println("createTreeDragSource.dragSetData file path=" + sourceNames[i]);					
				}
				//sourceNames[0] = connectionId;
				fSourceConnectionId = connectionId;
				event.data = sourceNames;
			}
		});
		return dragSource;
	}

	/**
	 * Creates the Drag & Drop DropTarget for items being dropped onto the tree.
	 * 
	 * @return the DropTarget for the tree
	 */
	private DropTarget createTreeDropTarget(final Tree tree) 
	{
		DropTarget dropTarget = new DropTarget(tree, DND.DROP_COPY); // | DND.DROP_MOVE 
		
		dropTarget.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		
		// Transfer data type is text 
		//dropTarget.setTransfer(new Transfer[] { RTFTransfer.getInstance()} );
		
System.out.println("createTreeDropTarget");

		dropTarget.addDropListener(new DropTargetAdapter() {
			public void dragEnter(DropTargetEvent event) {
				isDropping = true;
			}
			public void dragLeave(DropTargetEvent event) {
				isDropping = false;
//TODO				try {
//					handleDeferredRefresh();
//				} catch (Exception e) {
//					debug("createTreeDropTarget.dragLeave " + e);
//				}
			}
			public void dragOver(DropTargetEvent event) {
				dropTargetValidate(event, getTargetFile(event));
				event.feedback |= DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
			}
			
			public void drop(DropTargetEvent event) {
				FileAndConnectionId targetFile = getTargetFile(event);
				
				try {
System.out.println("createTreeDropTarget.drop targetFile=" + targetFile.file);
					if (dropTargetValidate(event, targetFile))
						dropTargetHandleDrop(event, targetFile);
					
				} 
				catch (FileSystemException e) {
					VFSUtil.MessageBoxError(e.getMessage());
				}
				catch (Exception e1) {
					debug(e1); // "createTreeDropTarget.drop " + 
				}
			}
			
			private FileAndConnectionId getTargetFile(DropTargetEvent event) {
				// Determine the target FileObject for the drop 
				TreeItem item = tree.getItem(tree.toControl(new Point(event.x, event.y)));
				FileObject targetFile = null;
				String connectionId = "";
				
				if (item != null) {
					// We are over a particular item in the tree, use the item's file
					//targetFile = (FileObject) item.getData(TREEITEMDATA_FILE);
					String uri = (String)item.getData(TREEITEMDATA_URI);
					connectionId = (String)item.getData(TREEITEMDATA_CONNECTIONID);
					try {
						targetFile 	= resolveURI(uri,connectionId);
					} catch (Exception e) {
						debug(e); //"createTreeDropTarget.getTargetFile " + 
					}
				}
				return new FileAndConnectionId(targetFile,connectionId);
			}
		});
		return dropTarget;	
	}

	/**
	 * Handles expand events on a tree item.
	 * 
	 * @param item the TreeItem to fill in
	 */
	private void treeExpandItem(TreeItem item)  
	{
		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorWait]);
		final Object stub = item.getData(TREEITEMDATA_STUB);
		
		if (stub == null) {
			try {
				treeRefreshItem(item, true);
			} catch (Exception e) {
				debug(e); // "treeExpandItem " + 
			}
		}
		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorDefault]);
	}
	
	/**
	 * Add a file sytem node
	 * @param filesystem
	 */
	public void addFileSystem (String connectionId)
	{
		try {			
			TreeItem newItem = new TreeItem(tree, SWT.NULL);
			treeInitVolume(newItem, connectionId) ;// masterFile);
			
			//	placeholder child item to get "expand" button
			new TreeItem(newItem, SWT.NULL); 
			
		} catch (Exception e) {
			VFSUtil.MessageBoxInfo(e.getMessage());
		}
	}
	
	/**
	 * Traverse the entire tree and update only what has changed.
	 * 
	 * @param roots the root directory listing
	 */
	private void treeRefresh(FileObject[] masterFiles) {
		TreeItem[] items = tree.getItems();
		int masterIndex = 0;
		int itemIndex = 0;

		for (int i = 0; i < items.length; ++i) {
			final TreeItem item = items[i];
//			final String uri = (String)item.getData(TREEITEMDATA_URI);
			final FileObject itemFile = null;
			
			
			if ((itemFile == null) || (masterIndex == masterFiles.length)) {
				// remove bad item or placeholder
				item.dispose();
				continue;
			}
			final FileObject masterFile = masterFiles[masterIndex];
			
			int compare = VFSUtil.compareFiles(masterFile, itemFile);
			
			if (compare == 0) {
				// same file, update it
				try {
					treeRefreshItem(item, false);
				} catch (Exception e) {
					error("treeRefresh.treeRefreshItem " + e);
				}
				
				++itemIndex;
				++masterIndex;
			} 
			else if (compare < 0) {
				// should appear before file, insert it
				TreeItem newItem = new TreeItem(tree, SWT.NULL, itemIndex);
				treeInitVolume(newItem, masterFile.getName().toString()) ;// masterFile);
				new TreeItem(newItem, SWT.NULL); // placeholder child item to get "expand" button
				++itemIndex;
				++masterIndex;
				--i;
			} else {
				// should appear after file, delete stale item
				item.dispose();
			}
		}

		for (;masterIndex < masterFiles.length; ++masterIndex) {
			final FileObject masterFile = masterFiles[masterIndex];
			TreeItem newItem = new TreeItem(tree, SWT.NULL);
			
//System.out.println("treeRefresh file=" + masterFile.getName().getURI());
			treeInitVolume(newItem, masterFile.getName().toString()) ;// masterFile);
			new TreeItem(newItem, SWT.NULL); // placeholder child item to get "expand" button
		}		
	}

	/**
	 * Insert URIS from $HOME/.vfs into the Tree
	 */
	private void treeInsertRemoteURIs ()
	{
		// Add remote URI nodes to the tree
		
		if ( fConnections != null ) {
		    Iterator it = fConnections.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        FTPConnectionProperties conProps = (FTPConnectionProperties) pairs.getValue();
		        if(!conProps.getURI().equals(conProps.getConnectionid())) {		        	
		        	VFSUtil.treeAddFsNode(tree, conProps);
		        }
		    }
		}
	}

	public void treeRefreshRemoteURIs ()
	{
		fConnections = VFSUtil.loadFileSystems();		
		try {
			treeRefresh(getRoots(fsManager));
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( fConnections != null ) {
		    Iterator it = fConnections.entrySet().iterator();
			TreeItem[] items = tree.getItems();
			int masterIndex = 0;
			int itemIndex = 0;

		    while (it.hasNext()) {
		    	boolean foundConnection = false;
		        Map.Entry pairs = (Map.Entry)it.next();
		        FTPConnectionProperties conProps = (FTPConnectionProperties) pairs.getValue();
				for (int i = 0; i < items.length; ++i) {
					final TreeItem item = items[i];
					final String treeConnectionId = (String)item.getData(TREEITEMDATA_CONNECTIONID);
					final String filePath = (String)item.getData(TREEITEMDATA_FILE);
					if(treeConnectionId.equals(conProps.getConnectionid())){
						foundConnection = true;
						break;
					} else {
						if(fConnections.get(treeConnectionId) == null && filePath != treeConnectionId) {
							item.dispose();							
						}
					}
					
				}
				if(!foundConnection) {
					VFSUtil.treeAddFsNode(tree, conProps);					
				}
		    }
		}
	}
	
	
	/**
	 * Traverse an item in the tree and update only what has changed.
	 * 
	 * @param dirItem the tree item of the directory
	 * @param forcePopulate true iff we should populate non-expanded items as well
	 */
	private void treeRefreshItem(TreeItem dirItem, boolean forcePopulate) 
		throws FileSystemException
	{
		final String uri = (String) dirItem.getData(TREEITEMDATA_URI);

//System.out.println("treeRefreshItem uri=" + uri);

		if (! forcePopulate && ! dirItem.getExpanded()) {
			// Refresh non-expanded item
			if (dirItem.getData(TREEITEMDATA_STUB) != null) {
				treeItemRemoveAll(dirItem);
				new TreeItem(dirItem, SWT.NULL); // placeholder child item to get "expand" button
				dirItem.setData(TREEITEMDATA_STUB, null);
			}
			return;
		}
		// Refresh expanded item
		dirItem.setData(TREEITEMDATA_STUB, this); // clear stub flag

		/* Get directory listing */
		FileObject[] subFiles = (uri != null) 
			? getDirectoryList( resolveURI(uri,(String) dirItem.getData(TREEITEMDATA_CONNECTIONID))) 
			: null;
			
		if (subFiles == null || subFiles.length == 0) {
			/* Error or no contents */
			treeItemRemoveAll(dirItem);
			dirItem.setExpanded(false);
			return;
		}

		/* Refresh sub-items */
		TreeItem[] items = dirItem.getItems();
		final FileObject[] masterFiles = subFiles;
		
		int masterIndex = 0;
		int itemIndex = 0;
		FileObject masterFile = null;
		
		String folderBaseName = null;
		
		for (int i = 0; i < items.length; ++i) {
			while ((masterFile == null) && (masterIndex < masterFiles.length)) {
				masterFile = masterFiles[masterIndex++];
				
				//if (! masterFile.isDirectory()) masterFile = null;
				if (! VFSUtil.isDirectory(masterFile)) masterFile = null;
			}

			final TreeItem item = items[i];
//			final String itemUri = (String) item.getData(TREEITEMDATA_URI);
//System.out.println("itemUri=" + itemUri);			
			final String itemFile = (String) item.getData(TREEITEMDATA_URI);
			//final FileObject itemFile = (FileObject) item.getData(TREEITEMDATA_FILE);
			
			if ((itemFile == null) || (masterFile == null)) {
				// remove bad item or placeholder
				item.dispose();
				continue;
			}
			int compare = VFSUtil.compareFiles(masterFile, resolveURI( itemFile, (String) item.getData(TREEITEMDATA_CONNECTIONID)) );
			
			if (compare == 0) {
				// same file, update it
				treeRefreshItem(item, false);
				masterFile = null;
				++itemIndex;
			} 
			else if (compare < 0) {
				// should appear before file, insert it
				TreeItem newItem = new TreeItem(dirItem, SWT.NULL, itemIndex);
				
				folderBaseName = masterFile.getName().getBaseName();
				
				//treeInitFolder(newItem, folderBaseName , uri + FileName.SEPARATOR + folderBaseName); //masterFile);
				treeInitFolder(newItem, folderBaseName , masterFile.toString());
				
				new TreeItem(newItem, SWT.NULL); // add a placeholder child item so we get the "expand" button
				masterFile = null;
				++itemIndex;
				--i;
			} else {
				// should appear after file, delete stale item
				item.dispose();
			}
		}
		while ((masterFile != null) || (masterIndex < masterFiles.length)) {
			if (masterFile != null) {
				TreeItem newItem = new TreeItem(dirItem, SWT.NULL);
				
				folderBaseName = masterFile.getName().getBaseName();
				
				//treeInitFolder(newItem, folderBaseName , uri + FileName.SEPARATOR + folderBaseName );
				treeInitFolder(newItem, folderBaseName , masterFile.toString());
				
				new TreeItem(newItem, SWT.NULL); // add a placeholder child item so we get the "expand" button
				if (masterIndex == masterFiles.length) break;
			}
			masterFile = masterFiles[masterIndex++];
			
			if (!  VFSUtil.isDirectory(masterFile) ) {
				masterFile = null;
			}
		}
	}

	/**
	 * Foreign method: removes all children of a TreeItem.
	 * @param treeItem the TreeItem
	 */
	private static void treeItemRemoveAll(TreeItem treeItem) {
		final TreeItem[] children = treeItem.getItems();
		for (int i = 0; i < children.length; ++i) {
			children[i].dispose();
		}
	}

	/**
	 * Initializes a folder item.
	 * 
	 * @param item the TreeItem to initialize
	 * @param folder the FileObject associated with this TreeItem
	 */
	private void treeInitFolder(TreeItem item, String folder, String uri) { 
		item.setText(folder);
		item.setImage(iconCache.get(CFPluginImages.ICON_FOLDER));
		item.setData(TREEITEMDATA_FILE, folder);
		item.setData(TREEITEMDATA_URI, uri);
		item.setData(TREEITEMDATA_CONNECTIONID, item.getParentItem().getData(TREEITEMDATA_CONNECTIONID));		
		item.setData(TREEITEMDATA_IMAGEEXPANDED, iconCache.get(CFPluginImages.ICON_FOLDER_OPEN));
		item.setData(TREEITEMDATA_IMAGECOLLAPSED, iconCache.get(CFPluginImages.ICON_FOLDER));
	}

	/**
	 * Initializes a volume item.
	 * 
	 * @param item the TreeItem to initialize
	 * @param volume the File associated with this TreeItem
	 */
	private void treeInitVolume(TreeItem item, String connectionId ) {//FileObject volume) {
		FTPConnectionProperties connectionProperties = fConnections.get(connectionId);
		if(connectionProperties == null) {
			addLocalConnection(connectionId);
			connectionProperties = fConnections.get(connectionId);
		}
		item.setText(connectionId);
		item.setImage(iconCache.get(CFPluginImages.ICON_DRIVE));
		item.setData(TREEITEMDATA_FILE, connectionProperties.getConnectionid());
		item.setData(TREEITEMDATA_URI, connectionProperties.getURI());
		item.setData(TREEITEMDATA_CONNECTIONID, connectionProperties.getConnectionid());
		item.setData(TREEITEMDATA_IMAGEEXPANDED, iconCache.get(CFPluginImages.ICON_DRIVE_FTP));
		item.setData(TREEITEMDATA_IMAGECOLLAPSED, iconCache.get(CFPluginImages.ICON_DRIVE));
	}

	/**
	 * Creates the file details table.
	 * 
	 * @param parent the parent control
	 */
	private void createTableView(Composite parent) 
	{
		//Composite composite = new Composite(parent, SWT.NONE);
		TabFolder composite = new TabFolder(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = gridLayout.marginWidth = 2;
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
		composite.setLayout(gridLayout);
		
		tabFileItems 		= new TabItem(composite, SWT.CLOSE);
		
//		tableContentsOfLabel = new Label(composite, SWT.BORDER);
//		tableContentsOfLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));

		table = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

		//ColumnViewerToolTipSupport.enableFor(table);
		// Tooltips
//		final ToolTipHandler tooltip = new ToolTipHandler(parent.getParent().getShell());
//		tooltip.activateHoverHelp(table);
		
		tabFileItems.setControl(table);
		
		for (int i = 0; i < tableTitles.length; ++i) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(tableTitles[i]);
			column.setWidth(tableWidths[i]);
		}
		table.setHeaderVisible(true);
		// Listen for key presses
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				// Del key pressed
				if ( event.keyCode == 127 ) 
				{
					FileObject[] files = getSelectedFiles();
					int count = files.length;
					
					// set a single delete or multiple delete text message
					String message = (count == 1) 
						? getResourceString("msg.box.delete.uri", new Object[]{files[0]})
						: getResourceString("msg.box.delete.resources", new Object[]{new Integer(count), getCurrentDirectory() }) ;
						
					if ( VFSUtil.MessageBoxYesNo( message) ) 
					{
						deleteObjects(files);
						
						// Refresh
						doRefresh();
					}
				}
			}

			private FileObject[] getSelectedFiles() {
				final TableItem[] items = table.getSelection();
				final FileObject[] files = new FileObject[items.length];
				
				for (int i = 0; i < items.length; ++i) {
					files[i] = (FileObject) items[i].getData(TABLEITEMDATA_FILE);
				}
				return files;
			}
		});
		
		// Listen for selections
		table.addSelectionListener(new SelectionAdapter() {
			// Fires when item is clicked
			public void widgetSelected(SelectionEvent event) {
				try {
					notifySelectedFiles(getSelectedFiles());
				} catch (Exception e) {
					//debug ("createTableView.widgetSelected " + e);
				}
			}
			
			// fires when list item is selected (double clicked)
			public void widgetDefaultSelected(SelectionEvent event) {
				doDefaultFileAction(getSelectedFiles());
			}
			
			private FileObject[] getSelectedFiles() {
				final TableItem[] items = table.getSelection();
				final FileObject[] files = new FileObject[items.length];
				
				for (int i = 0; i < items.length; ++i) {
					files[i] = (FileObject) items[i].getData(TABLEITEMDATA_FILE);
				}
				return files;
			}
		});

		createTableDragSource(table);
		createTableDropTarget(table);
	}

	/**
	 * Creates the Drag & Drop DragSource for items being dragged from the table.
	 * 
	 * @return the DragSource for the table
	 */
	private DragSource createTableDragSource(final Table table) {
		DragSource dragSource = new DragSource(table, DND.DROP_COPY); // | DND.DROP_MOVE

		// transfer type is text data (file URI)
		dragSource.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		
		dragSource.addDragListener(new DragSourceListener() {
			TableItem[] dndSelection = null;
			String[] sourceNames = null;
			
			public void dragStart(DragSourceEvent event){
				dndSelection = table.getSelection();
				sourceNames = null;
				event.doit = dndSelection.length > 0;
				isDragging = true;
			}
			
			public void dragFinished(DragSourceEvent event){
				dragSourceHandleDragFinished(event, sourceNames);
				dndSelection = null;
				sourceNames = null;
				isDragging = false;
				
//TODO				try {
//					handleDeferredRefresh();
//				} catch (Exception e) {
//					debug("createTableDragSource.dragFinished " + e);
//				}
				
			}
			
			public void dragSetData(DragSourceEvent event) {
				if (dndSelection == null || dndSelection.length == 0) return;
				
				if (! FileTransfer.getInstance().isSupportedType(event.dataType)) {
					error("createTableDragSource.dragSetData File Transfer data type text is required.");
					return;
				}
				
				// Cannot put Objects in the transfer event data field, they must be path
				// and begin with / in UNIX
				sourceNames  = new String[dndSelection.length+1];
				String connectionId = "";
				for (int i = 1; i < dndSelection.length; i++) {
					FileObject file = (FileObject) dndSelection[i].getData(TABLEITEMDATA_FILE);
					connectionId = (String) dndSelection[i].getData(TABLEITEMDATA_CONNECTIONID);
					/*
					 * In Order for FileTransfer to Work, the name must begin w/
					 * / in UNIX systems
					 */
					sourceNames[i] = Os.isFamily(Os.OS_FAMILY_UNIX)
						? FileName.SEPARATOR + file.toString()
						: file.toString(); 
					
System.out.println("createTableDragSource::dragSetData: sourceNames [" 
		+ i + "] path= " + sourceNames[i] );

				}
				sourceNames[0] = connectionId;
				event.data = sourceNames;
			}
		});
		return dragSource;
	}

	/**
	 * Creates the Drag & Drop DropTarget for items being dropped onto the table.
	 * 
	 * @return the DropTarget for the table
	 */
	private DropTarget createTableDropTarget(final Table table){
		DropTarget dropTarget = new DropTarget(table, DND.DROP_COPY); // | DND.DROP_MOVE
		
		dropTarget.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		//dropTarget.setTransfer(new Transfer[] { RTFTransfer.getInstance() });
		
		dropTarget.addDropListener(new DropTargetAdapter() {
			public void dragEnter(DropTargetEvent event) {
				isDropping = true;
			}
			
			public void dragLeave(DropTargetEvent event) {
				isDropping = false;
				
// TODO				try {
//					handleDeferredRefresh();
//				} catch (Exception e) {
//					debug("createTableDropTarget.dragLeave " + e);
//				}
				
			}
			
			public void dragOver(DropTargetEvent event) {
				dropTargetValidate(event, getTargetFile(event));
				event.feedback |= DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
			}
			public void drop(DropTargetEvent event) {
				FileAndConnectionId targetFile = getTargetFile(event);
				try {
					if (dropTargetValidate(event, targetFile))
						dropTargetHandleDrop(event, targetFile);
					
				} catch (Exception e) {
					debug(e); // "createTableDropTarget.drop " + 
				}
			}
			
			private FileAndConnectionId getTargetFile(DropTargetEvent event) {
				// Determine the target FileObject for the drop 
				TableItem item = table.getItem(table.toControl(new Point(event.x, event.y)));
				FileObject targetFile = null;
				String connectionId = "";
				if (item == null) {
					// We are over an unoccupied area of the table.
					// If it is a COPY, we can use the table's root file.
					if (event.detail == DND.DROP_COPY) {
						targetFile = (FileObject) table.getData(TABLEDATA_DIR);
						connectionId = (String) table.getData(TABLEDATA_CONNECTIONID);
					}
				} else {
					// We are over a particular item in the table, use the item's file
					targetFile = (FileObject) item.getData(TABLEITEMDATA_FILE);
					connectionId = (String) table.getData(TABLEITEMDATA_CONNECTIONID);
				}
				return new FileAndConnectionId(targetFile,connectionId);
			}
		});
		return dropTarget;
	}

	
	/**
	 * Notifies the application components that a new current directory has been selected
	 * 
	 * @param dir the directory that was selected, null is ignored
	 */
	public void notifySelectedDirectory(FileObject dir, String connectionId) 
		throws FileSystemException 
	{
		if (dir == null || connectionId == null) return;
		if (getCurrentDirectory() != null && dir.equals(getCurrentDirectory())) return;
		
		setCurrentDirectory(dir);
		setCurrentConnectionId(connectionId);

//System.err.println("notifySelectedDirectory currentDirectory:" 
//		+ currentDirectory + " notify dir:" + dir );

		// Processing...
		VFSUtil.setUiStateListContents(shell, getCurrentDirectory().getName().toString());
		
		notifySelectedFiles(null);

		/* Table view:
		 * Displays the contents of the selected directory.
		 */
		workerUpdate(dir, connectionId, false);

		/* Combo view:
		 * Sets the combo box to point to the selected directory.
		 */
		final FileObject[] comboRoots = (FileObject[]) combo.getData(COMBODATA_ROOTS);
		int comboEntry = -1;
		if (comboRoots != null) {		
			for (int i = 0; i < comboRoots.length; ++i) {
				if (dir.equals(comboRoots[i])) {
					comboEntry = i;
					break;
				}
			}
		}
		
		if (comboEntry == -1) combo.setText(dir.getName().toString()); 
		else combo.select(comboEntry);

		/* Tree view:
		 * If not already expanded, recursively expands the parents of the specified
		 * directory until it is visible.
		 */
		Vector /* of FileObject */ path = new Vector();
		
		// Build a stack of paths from the root of the tree
		while (dir != null) {
			path.add(dir);
			//dir = dir.getParentFile();
			
			try {
				dir = dir.getParent();
			} catch (Exception e) {
				System.err.println("notifySelectedDirectory:" + e);
			}
		}

//System.out.println("notifySelectedDirectory currentDirectory:" 
//		+ currentDirectory + " dir:" + dir + " tree path:" + path);

		// Recursively expand the tree to get to the specified directory
		TreeItem[] items = tree.getItems();
		TreeItem lastItem = null;
		String destConnectionId = null;
		TreeItem item = null;
		// filter by connection first
		for (int k = 0; k < items.length; ++k) {			
			item = items[k];
			destConnectionId 		= (String)item.getData(TREEITEMDATA_CONNECTIONID);
			if(connectionId.equals(connectionId)){
				items = items[k].getItems(); 
			}
		}
		
		for (int i = path.size() - 1; i >= 0; --i) 
		{
			final FileObject pathElement = (FileObject) path.elementAt(i);

			// Search for a particular FileObject in the array of tree items
			// No guarantee that the items are sorted in any recognizable fashion, so we'll
			// just sequential scan.  There shouldn't be more than a few thousand entries.
			item = null;
			for (int k = 0; k < items.length; ++k) {
				item = items[k];
				if (item.isDisposed()) continue;

				final String fileURI 		= ((String)item.getData(TREEITEMDATA_URI));
				destConnectionId 		= (String)item.getData(TREEITEMDATA_CONNECTIONID);
				
				// Resolving the children will attempt a server connection. This will hang the UI if the server is down
				// Thus perform a comparison by Host/Scheme/Path only
				// Cannot just compare the URI strings cuz FileObject hides the port number
				
				if (connectionId.equals(destConnectionId) && VFSUtil.compareURIs(fileURI, pathElement.toString()) ) 
					break;
			}
				
			if (item == null) break;
			lastItem = item;
			
			if (i != 0 && !item.getExpanded()) {
				treeExpandItem(item);
				item.setExpanded(true);
			}
			items = item.getItems();
		}
		if(connectionId.equals(destConnectionId)) {
			tree.setSelection((lastItem != null) ? new TreeItem[] { lastItem } : new TreeItem[0]);
		}

		/* Shell:
		 * Sets the title to indicate the selected directory
		 */
		VFSUtil.setUiStateIdle(shell, VFSUtil.stripUserTokens(getCurrentDirectory().toString())); 
	}
	
	/**
	 * Notifies the application components that files have been selected
	 * 
	 * @param files the files that were selected, null or empty array indicates no active selection
	 */
	void notifySelectedFiles(FileObject[] files) throws FileSystemException {
		/* Details:
		 * Update the details that are visible on screen.
		 */
		if ((files != null) && (files.length != 0)) 
		{
			numObjectsLabel.setText(getResourceString("details.NumberOfSelectedFiles.text",
				new Object[] { new Integer(files.length) }));
			
			long fileSize = 0L;
			
			for (int i = 0; i < files.length; ++i) {
				//fileSize += files[i].length();
				fileSize += files[i].getContent().getSize();
			}
			
			diskSpaceLabel.setText(getResourceString("details.FileSize.text",
				new Object[] { new Long(fileSize) }));
		} else {
			// No files selected
			diskSpaceLabel.setText("");
			if (getCurrentDirectory() != null) 
			{
				int numObjects = getDirectoryList( getCurrentDirectory()).length;
				
				numObjectsLabel.setText(getResourceString("details.DirNumberOfObjects.text",
					new Object[] { new Integer(numObjects) }));
			} else {
				numObjectsLabel.setText("");
			}
		}
	}

	/**
	 * Notifies the application components that files must be refreshed
	 * 
	 * @param files the files that need refreshing, empty array is a no-op, null refreshes all
	 */
	public void notifyRefreshFiles(FileObject[] files) throws FileSystemException {
		if (files != null && files.length == 0) return;

		if ((deferredRefreshRequested) && (deferredRefreshFiles != null) && (files != null)) {
			// merge requests
			FileObject[] newRequest = new FileObject[deferredRefreshFiles.length + files.length];
			System.arraycopy(deferredRefreshFiles, 0, newRequest, 0, deferredRefreshFiles.length);
			System.arraycopy(files, 0, newRequest, deferredRefreshFiles.length, files.length);
			deferredRefreshFiles = newRequest;
		} else {
			deferredRefreshFiles = files;
			deferredRefreshRequested = true;
		}
		
		handleDeferredRefresh();
	}

	/**
	 * Handles deferred Refresh notifications (due to Drag & Drop)
	 */
	void handleDeferredRefresh() throws FileSystemException
	{
//System.out.println("handleDeferredRefresh");

		if (isDragging || isDropping || ! deferredRefreshRequested) return;

		deferredRefreshRequested = false;
		FileObject[] files = deferredRefreshFiles;
		deferredRefreshFiles = null;

		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorWait]);
		
		/* Table view:
		 * Refreshes information about any files in the list and their children.
		 */
		boolean refreshTable = false;
		
		if (files != null) {
			for (int i = 0; i < files.length; ++i) {
				final FileObject file = files[i];
				if (file.equals(getCurrentDirectory())) {
					refreshTable = true;
					break;
				}

				FileObject parentFile = file.getParent();
				
				if ((parentFile != null) && (parentFile.equals(getCurrentDirectory()))) {
					refreshTable = true;
					break;
				}
			}
		} 
		else 
			refreshTable = true;

		if (refreshTable) 
			workerUpdate(getCurrentDirectory(), getCurrentConnectionId(), true);

		/* Combo view:
		 * Refreshes the list of roots
		 */
		final FileObject[] roots = getRoots(fsManager);

		if (files == null) {
			boolean refreshCombo = false;
			final FileObject[] comboRoots = (FileObject[]) combo.getData(COMBODATA_ROOTS);
		
			if ((comboRoots != null) && (comboRoots.length == roots.length)) {
				for (int i = 0; i < roots.length; ++i) {
					if (! roots[i].equals(comboRoots[i])) {
						refreshCombo = true;
						break;
					}
				}
			} else refreshCombo = true;

			if (refreshCombo) {
				combo.removeAll();
				combo.setData(COMBODATA_ROOTS, roots);
				
				for (int i = 0; i < roots.length; ++i) {
					final FileObject file = roots[i];
					combo.add(file.getName().toString() ); 
				}
			}
		}

		/* Tree view:
		 * Refreshes information about any files in the list and their children.
		 */
		treeRefresh(roots);
		
		treeInsertRemoteURIs();
		
		// Remind everyone where we are in the filesystem
		final FileObject dir = getCurrentDirectory();
		setCurrentDirectory(null);
		final String con = getCurrentConnectionId();
		setCurrentConnectionId(null);
		notifySelectedDirectory(dir,con);

		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorDefault]);
	}

	/**
	 * Performs the default action on a set of files.
	 * 
	 * @param files the array of files to process
	 */
	void doDefaultFileAction(FileObject[] files) {
		// only uses the 1st file (for now)
		if (files.length == 0) return;
		final FileObject file = files[0];

		if (VFSUtil.isDirectory(file)) {
			try {
				notifySelectedDirectory(file,getCurrentConnectionId());
			} catch (Exception e) {
				debug(e); // "FileViewer.doDefaultFileAction " + 
			}
		} 
		else {
			//final String fileName = file.getAbsolutePath();
			RemoteFileEditorInput input = new RemoteFileEditorInput(file);
			try {
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName().getBaseName());        
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage();
				//page.openEditor(input, desc.getId());

				page.openEditor(input, CFMLEditor.ID);

				
				// wonder how this could work?
//				IFileStore fileStore;
//				fileStore = EFS.getFileSystem(file.getName().getScheme()).getStore(file.getURL().toURI());
//				IDE.openEditorOnFileStore( page, fileStore );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
//			if (! Program.launch(fileName)) {	
//				MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
//				dialog.setMessage(getResourceString("error.FailedLaunch.message", new Object[] { fileName }));
//				dialog.setText(shell.getText ());
//				dialog.open();
//			}
		}
	}

	/**
	 * Navigates to the parent directory
	 */
	void doParent() {
		if (getCurrentDirectory() == null) return;
		//FileObject parentDirectory = currentDirectory.getParentFile();
		try {
			FileObject parentDirectory = getCurrentDirectory().getParent();
			notifySelectedDirectory(parentDirectory,getCurrentConnectionId());
		} catch (Exception e) {
			System.err.println("FileViewer::doParent " + e);
		}
	}
	 
	/**
	 * Performs a refresh
	 */
	void doRefresh() {
		try {
			notifyRefreshFiles(null);
		} catch (Exception e) {
			debug (e); // "FileViewer.doRefresh " + 
		}
	}

	/**
	 * Validates a drop target as a candidate for a drop operation.
	 * <p>
	 * Used in dragOver() and dropAccept().<br>
	 * Note event.detail is set to DND.DROP_NONE by this method if the target is not valid.
	 * </p>
	 * @param event the DropTargetEvent to validate
	 * @param fileAndConnectionId the FileObject representing the drop target location
	 *        under inspection, or null if none
	 */
	private boolean dropTargetValidate(DropTargetEvent event, FileAndConnectionId fileAndConnectionId) 
	{
		//if (targetFile != null && targetFile.isDirectory()) {
		if (fileAndConnectionId.file != null && VFSUtil.isDirectory(fileAndConnectionId.file) ) {
			if (event.detail != DND.DROP_COPY && event.detail != DND.DROP_MOVE) {
				event.detail = DND.DROP_COPY; // DND.DROP_MOVE;
			}
		} else {
			event.detail = DND.DROP_NONE;
		}
		return event.detail != DND.DROP_NONE;
	}

	/**
	 * Handles a drop on a dropTarget.
	 * <p>
	 * Used in drop().<br>
	 * Note event.detail is modified by this method.
	 * </p>
	 * @param event the DropTargetEvent passed as parameter to the drop() method
	 * @param targetFile the FileObject representing the drop target location
	 *        under inspection, or null if none
	 */
	private void dropTargetHandleDrop(DropTargetEvent event, FileAndConnectionId fileAndConnectionId)
		throws IOException
	{

		// Get dropped data (an array of filenames)
//		if (! dropTargetValidate(event, targetFile)) 
//			return;
		
		final String[] sourceNames = (String[]) event.data;

		if (sourceNames == null) event.detail = DND.DROP_NONE;
		if (event.detail == DND.DROP_NONE) return;
		VFSUtil.copyFiles(fsManager, sourceNames, fConnections.get(fSourceConnectionId), fileAndConnectionId.file, fConnections.get(fileAndConnectionId.connectionId));
		//VFSUtil.copyFiles(fsManager, sourceNames, fileAndConnectionId.file);
		
		// refresh
        notifyRefreshFiles(new FileObject[] { fileAndConnectionId.file });
        
	}

	/**
	 * Handles the completion of a drag on a dragSource.
	 * <p>
	 * Used in dragFinished().<br>
	 * </p>
	 * @param event the DragSourceEvent passed as parameter to the dragFinished() method
	 * @param sourceNames the names of the files that were dragged (event.data is invalid)
	 * @param sourceConnectionId 
	 */
	private void dragSourceHandleDragFinished(DragSourceEvent event, String[] sourceNames) {
		if (sourceNames == null) return;
		if (event.detail != DND.DROP_MOVE) return;
		
		// Get array of files that were actually transferred
		// Do nothing w/ source files
	}


	/**
	 * Gets filesystem root entries
	 * @param fsManager
	 * @return an array of Files corresponding to the root directories on the platform,
	 *         may be empty but not null
	 */
	FileObject[] getRoots( FileSystemManager fsManager)
		throws FileSystemException
	{
		/*
		 * On JDK 1.22 only...
		 */
		// return File.listRoots();
		FileObject[] roots 		= null;
//		FileObject[] newRequest = null;
		/*
		 * On JDK 1.1.7 and beyond...
		 * -- PORTABILITY ISSUES HERE --
		 */
		if (System.getProperty ("os.name").indexOf ("Windows") != -1) {
			Vector /* of FileObject */ list = new Vector();
			list.add(fsManager.resolveFile(DRIVE_A));
			
			for (char i = 'c'; i <= 'z'; ++i) 
			{
				//FileObject drive = new FileObject(i + ":" + FileName.SEPARATOR);
				FileObject drive = fsManager.resolveFile(i + ":" + FileName.SEPARATOR);
				
				if (VFSUtil.isDirectory(drive) && drive.exists()) {
					list.add(drive);
					if (initial && i == 'c') {
						setCurrentDirectory(drive);
						setCurrentConnectionId(drive.getName().toString());
						initial = false;
					}
				}
			}
			roots = (FileObject[]) list.toArray(new FileObject[list.size()]);
			VFSUtil.sortFiles(roots);
			
			//return roots;
		} 
		else {
			FileObject root = fsManager.resolveFile (FileName.SEPARATOR);
			
			if (initial) {
				setCurrentDirectory(root);
				setCurrentConnectionId(root.getName().toString());
				initial = false;
			}
			roots = new FileObject[] { root };
		}

		
		return roots; //newRequest; 
	}
	

	private void addLocalConnection(String connectionId) {
		FTPConnectionProperties newConProps = new FTPConnectionProperties(connectionId);
		newConProps.setURI(connectionId);
		fConnections.put(connectionId, newConProps);
	}

	/*
	 * This worker updates the table with file information in the background.
	 * <p>
	 * Implementation notes:
	 * <ul>
	 * <li> It is designed such that it can be interrupted cleanly.
	 * <li> It uses asyncExec() in some places to ensure that SWT Widgets are manipulated in the
	 *      right thread.  Exclusive use of syncExec() would be inappropriate as it would require a pair
	 *      of context switches between each table update operation.
	 * </ul>
	 * </p>
	 */

	/**
	 * Stops the worker and waits for it to terminate.
	 */
	void workerStop() {
		if (workerThread == null) return;
		synchronized(workerLock) {
			workerCancelled = true;
			workerStopped = true;
			workerLock.notifyAll();
		}
		while (workerThread != null) {
			if (! display.readAndDispatch()) display.sleep();
		}
	}

	/**
	 * Notifies the worker that it should update itself with new data.
	 * Cancels any previous operation and begins a new one.
	 * 
	 * @param dir the new base directory for the table, null is ignored
	 * @param currentConnectionId 
	 * @param force if true causes a refresh even if the data is the same
	 */
	void workerUpdate(FileObject dir, String connectionId, boolean force) {
		if (dir == null) return;
		if ((!force) && (workerNextDir != null) && (workerNextDir.equals(dir))) return;

		synchronized(workerLock) {
			workerNextDir = dir;
			workerNextConnectionId = connectionId;
			workerStopped = false;
			workerCancelled = true;
			workerLock.notifyAll();
		}
		if (workerThread == null) {
			workerThread = new Thread(workerRunnable);
			workerThread.start();
		}
	}

	/**
	 * Manages the worker's thread
	 */
	private final Runnable workerRunnable = new Runnable() {
		public void run()  {
			while (! workerStopped) {
				synchronized(workerLock) {
					workerCancelled = false;
					workerStateDir = workerNextDir;
					workerStateConnectionId = workerNextConnectionId;
				}
				
				workerExecute();
				
				synchronized(workerLock) {
					try {
						if ((!workerCancelled) && (workerStateDir == workerNextDir)) workerLock.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			workerThread = null;
			// wake up UI thread in case it is in a modal loop awaiting thread termination
			// (see workerStop())
			display.wake();
		}
	};
	
	/**
	 * Updates the table's contents
	 */
	private void workerExecute() {
		FileObject[] dirList;
		
		// Clear existing information
		display.syncExec(new Runnable()	{
			public void run() 
			{
				tabFileItems.setText(VFSView.getResourceString("details.ContentsOf.text",
						new Object[] { VFSUtil.stripUserTokens(workerStateDir.toString()) }));

				table.removeAll();
				table.setData(TABLEDATA_DIR, workerStateDir);
				table.setData(TABLEDATA_CONNECTIONID,workerStateConnectionId);
			}
		});
		
		try {
			dirList = getDirectoryList( workerStateDir);

			for (int i = 0; (! workerCancelled) && (i < dirList.length); i++) {
				workerAddFileDetails(dirList[i],workerStateConnectionId);
			}

		} catch (FileSystemException e) {
			VFSUtil.MessageBoxError( e.getMessage());
		}
	}
		
	/**
	 * Adds a file's detail information to the directory list
	 */
	private void workerAddFileDetails(final FileObject file, final String connectionId) 
	{
		long date = 0;
		try {
			date = file.getContent().getLastModifiedTime();
		} catch (Exception e) {}
		
		final String nameString = file.getName().getBaseName();
		final String dateString = dateFormat.format(new Date(date));
		final String sizeString;
		final String typeString;
		final Image iconImage;
		
		// If directory
		if (VFSUtil.isDirectory(file)) 
		{
			typeString = getResourceString("filetype.Folder");
			sizeString = "";
			iconImage = iconCache.get(CFPluginImages.ICON_FOLDER);
		} 
		else {
			long size = 0;
			try {
				size = file.getContent().getSize();
			} catch (Exception e) {}
			
			// It is a file, get size
			sizeString = getResourceString("filesize.KB",
				new Object[] { new Long((size + 512) / 1024) });
			
			int dot = nameString.lastIndexOf('.');
			if (dot != -1) 
			{
				String extension = nameString.substring(dot);
				Program program = Program.findProgram(extension);
				
				// get icon based on extension
				if (program != null) 
				{
					typeString = program.getName();
					iconImage = CFPluginImages.getIconFromProgram(program);
				} 
				else {
					typeString = getResourceString("filetype.Unknown", new Object[] { extension.toUpperCase() });
					iconImage = iconCache.get(CFPluginImages.ICON_FILE);
				}
			} 
			else {
				typeString = getResourceString("filetype.None");
				iconImage = iconCache.get(CFPluginImages.ICON_FILE);
			}
		}
		
		// Table values: Name, Size, Type, Date
		final String[] strings = new String[] { nameString, sizeString, typeString, dateString };

		display.syncExec(new Runnable() {
			public void run () {
				String attrs = VFSUtil.getFileAttributes(file);
				
				// guard against the shell being closed before this runs
				if (shell.isDisposed()) return;
				TableItem tableItem = new TableItem(table, 0);
				tableItem.setText(strings);
				tableItem.setImage(iconImage);
				tableItem.setData(TABLEITEMDATA_FILE, file);
				tableItem.setData(TABLEITEMDATA_CONNECTIONID, connectionId);
				
				if ( attrs.length() > 0 )
					tableItem.setData ("TIP_TEXT", attrs); 
			}
		});
	}
	
	/**
	 * Resolve a FileSystem Uri seeting progress UI messages
	 */
	public FileObject resolveURI (String Uri, String connectionId) throws FileSystemException
	{
		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorWait]);
		shell.setText(getResourceString("Title.Resolving"
				, new Object[] { Uri }));
		FTPConnectionProperties connectionProperties = fConnections.get(connectionId);

		FileObject fo = fsManager.resolveFile(Uri,connectionProperties.getFileSystemOptions());

		shell.setCursor(CFPluginImages.cursors[CFPluginImages.cursorDefault]);
		shell.setText(getResourceString("Title"
				, new Object[] { Uri }));
		
		return fo;
	}
	
	/**
	 * Gets a directory listing
	 * 
	 * @param file the directory to be listed
	 * @return an array of files this directory contains, may be empty but not null
	 */
	private FileObject[] getDirectoryList(FileObject file)
		throws FileSystemException
	{
		FileObject[] list = file.getChildren();
		
		if (list == null) {
			return new FileObject[0];
		}

		VFSUtil.sortFiles(list);

		return list;
	}
	
	/**
	 * Delete selected files
	 * @param files
	 */
	private void deleteObjects (FileObject[] files)
	{
		// run a delete operation
        try {
        	FileOperation delOperation = new FileOperation(fsManager, FileOperation.DELETE, true);
        	delOperation.setDeleteArgs(files);
        	
        	new ProgressMonitorDialog(shell).run(true, true, delOperation);
        } 
        catch (InvocationTargetException e) 
        {
            VFSUtil.MessageBoxError(e.getMessage());
        } catch (InterruptedException e) 
        {
        	VFSUtil.MessageBoxInfo(e.getMessage());
        }

        // Refresh
		doRefresh();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public boolean show(ShowInContext context) {
    	String filePath;
    
        if (table == null || context == null)
           return false;
        ISelection sel = context.getSelection();
        Object input = context.getInput();
        if (input instanceof CFJavaFileEditorInput) {
        	filePath = ((CFJavaFileEditorInput)input).getPath(input).toString();
        }
		else {
			filePath = ((FileEditorInput)input).getFile().getRawLocation().toString();
		}
		
        
        //showFile(filePath);
        return false;
     }
	
	/**
	 * @param currentConnectionId the currentConnectionId to set
	 */
	public void setCurrentConnectionId(String currentConnectionId) {
		this.currentConnectionId = currentConnectionId;
	}

	/**
	 * @return the currentConnectionId
	 */
	public String getCurrentConnectionId() {
		return currentConnectionId;
	}

	/**
	 * @param currentDirectory the currentDirectory to set
	 */
	public void setCurrentDirectory(FileObject currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	/**
	 * @return the currentDirectory
	 */
	public FileObject getCurrentDirectory() {
		return currentDirectory;
	}

	public class FileAndConnectionId {
		public FileObject file = null;
		public String connectionId = "";
		public FileAndConnectionId(FileObject file, String connectionId) {
			super();
			this.file = file;
			this.connectionId = connectionId;
		}
		
	}
}
