/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Locale;


import org.cfeclipse.cfml.dictionary.Tag;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.RemoteFile;
import org.cfeclipse.cfml.views.dictionary.DictionaryView;
import org.cfeclipse.cfml.views.dictionary.TagItem;
import org.cfeclipse.cfml.views.explorer.ftp.FtpConnectionDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;





/**
 * @author Stephen Milligan
 *
 * The explorer view is suposed to be a faithful replication
 * of the file explorer tab in Homesite/CFStudio.
 */
public class FileExplorerView extends ViewPart implements IShowInTarget {
	public final static String ID_FILE_EXPLORER = "org.cfeclipse.cfml.views.explorer.FileExplorerView";

    private MenuItem disconnectItem,connectItem,manageItem;
    
    private Action createFile;
	private Action createDirectory;
	private Action deleteItem;
	private Shell shell;
    
    
    protected IFileProvider fileProvider = null;
    
    private final class ComboSelectionListener implements ISelectionChangedListener {
        ViewPart viewpart = null;
        public ComboSelectionListener(ViewPart viewpart) {
            this.viewpart = viewpart;
        }
        
        public void selectionChanged(SelectionChangedEvent e) {
        	try {
        	    StructuredSelection sel = (StructuredSelection)e.getSelection();
        		directoryTreeViewer.setInput(sel.getFirstElement());
        		fileViewer.setInput(sel.getFirstElement());
        		if (sel.getFirstElement() instanceof FTPConnectionProperties) {
        		    connectItem.setEnabled(true);
        		    disconnectItem.setEnabled(true);
        		}
        		else {
        		    connectItem.setEnabled(false);
        		    disconnectItem.setEnabled(false);
        		}
        	}
        	catch (Exception ex) {
        		ex.printStackTrace();
        	}
        }
    }

    private final class DirectorySelectionListener implements ISelectionChangedListener {
        ViewPart viewpart = null;
        public DirectorySelectionListener(ViewPart viewpart) {
            this.viewpart = viewpart;
        }
        public void selectionChanged(SelectionChangedEvent e) {
            fileViewer.setInput(e.getSelection());
        }
    }

    private final class MenuMouseListener implements MouseListener {
        Menu menu = null;

        public MenuMouseListener (Menu menu) {
            this.menu = menu;
        }

        public void mouseUp(MouseEvent e) {
            
        	menu.setVisible(true);
        }

        public void mouseDown(MouseEvent e) {
        	
        }

        public void mouseDoubleClick(MouseEvent e) {
        	
        }
    }

    ComboViewer comboViewer = null;
    TreeViewer directoryTreeViewer = null;
    TableViewer fileViewer = null;
    
    
    public void createPartControl(Composite parent) {

        Composite container = new Composite(parent, SWT.NONE);
        GridLayout containerLayout = new GridLayout();
        containerLayout.numColumns = 2;
        container.setLayout(containerLayout);
        this.shell = parent.getShell();
        
        
        // Select Site or local path
        comboViewer = new ComboViewer(container, SWT.READ_ONLY);
        comboViewer.addSelectionChangedListener(new ComboSelectionListener(this));
        comboViewer.setLabelProvider(new ComboLabelProvider());
        comboViewer.setContentProvider(new ComboContentProvider());
        final Combo combo = comboViewer.getCombo();
        final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		
        
        combo.setLayoutData(gridData);
        comboViewer.setInput(new LocalFileSystem());
        
        Menu menu = new Menu(container.getShell(), SWT.POP_UP);
        createMenuItems(menu);
        Button menuButton = new Button(container,SWT.ARROW | SWT.RIGHT);
        menuButton.addMouseListener(new MenuMouseListener(menu));
        
        
        
        // This is what makes the controls resizable
        SashForm sash = new SashForm(container,SWT.VERTICAL);
        GridData sashData = new GridData(GridData.FILL_BOTH);
        sashData.horizontalSpan = 2;
        sash.setLayoutData(sashData);
        
        // Directory tree
        directoryTreeViewer = new TreeViewer(sash, SWT.BORDER);
        directoryTreeViewer.addSelectionChangedListener(new DirectorySelectionListener(this));
        directoryTreeViewer.setSorter(new DirectorySorter());
        directoryTreeViewer.setLabelProvider(new DirectoryLabelProvider());
        directoryTreeViewer.setContentProvider(new DirectoryContentProvider(this));
        final Tree tree = directoryTreeViewer.getTree();
        directoryTreeViewer.setComparer(new FileComparer());
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        
        directoryTreeViewer.setInput(new LocalFileSystem());
        
//Create the columns
        
        
        
        
        
        FileContentProvider fileProvider = new FileContentProvider(this);
        fileViewer = new TableViewer(sash, SWT.BORDER);
        final Table fileTable = fileViewer.getTable();
        
      
        
        
        fileViewer.addDoubleClickListener(new FileDoubleClickListener(fileProvider)); 
        
        fileViewer.setSorter(new Sorter());
        fileViewer.setLabelProvider(new FileLabelProvider());
        fileViewer.setContentProvider(fileProvider);
        fileViewer.setComparer(new FileComparer());
        
        final Table table = fileViewer.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        
        
        //Add the columns and sort out the column stuff
        TableColumn filenameColumn = new TableColumn(fileViewer.getTable(), SWT.NONE);
        filenameColumn.setWidth(200);
        filenameColumn.setText("Name");
        
        TableColumn fileSizeColumn = new TableColumn(fileViewer.getTable(), SWT.NONE);
        fileSizeColumn.setWidth(50);
        fileSizeColumn.setText("Size");
        
       /* Listener sortListener = new Listener() {
            public void handleEvent(Event e) {
                TableItem[] items = table.getItems();
                Collator collator = Collator.getInstance(Locale.getDefault());
                TableColumn column = (TableColumn)e.widget;
                int index = column == filenameColumn ? 0 : 1;
                for (int i = 1; i < items.length; i++) {
                    String value1 = items[i].getText(index);
                    for (int j = 0; j < i; j++){
                        String value2 = items[j].getText(index);
                        if (collator.compare(value1, value2) < 0) {
                            String[] values = {items[i].getText(0), items[i].getText(1)};
                            items[i].dispose();
                            TableItem item = new TableItem(table, SWT.NONE, j);
                            item.setText(values);
                            items = table.getItems();
                            break;
                        }
                    }
                }
                table.setSortColumn(column);
            }
        };
        filenameColumn.addListener(SWT.Selection, sortListener);
        fileSizeColumn.addListener(SWT.Selection, sortListener);
        */
        
        
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setSortColumn(filenameColumn);
        //table.setSortDirection(SWT.UP);
        
        fileViewer.setInput(DirectoryContentProvider.localFS);
        
        initializeToolBar();
        initializeMenu();
        initializeRightClick();
        hookContextMenu();
    	
    }
    
    
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				FileExplorerView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(directoryTreeViewer.getControl());
		directoryTreeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, directoryTreeViewer);
	}
	
    
    private void fillContextMenu(IMenuManager manager) {
		//Somehow we have to know what this item is
		manager.add(createFile);
		
	}
    
    private void initializeRightClick() {
		// TODO Auto-generated method stub
    	createFile = new Action() {
			public void run() {
			
				ISelection selection = directoryTreeViewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				
				if(obj instanceof RemoteFile){
					RemoteFile rem = (RemoteFile)obj;
					System.out.println(rem.getAbsolutePath());
				} 
				else if(obj instanceof File){
					File lfile = (File)obj;

					if(lfile.isDirectory()){
						FileCreateDialog fcd = new FileCreateDialog(null);
						System.out.println("about to open");
						if(fcd.open() == IDialogConstants.OK_ID){ 

							String filePath = lfile.getAbsoluteFile() + lfile.separator +   fcd.filename;
							File newFile = new File(filePath);
							
							if(!newFile.exists()){
								try {
									newFile.createNewFile();
									fileViewer.refresh();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Error!","File already exits!");
								
							}

						}
					}
				}
				
				
			}
		};
		
		createFile.setText("Create File");
		//editTagAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		//		.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
	}

	public boolean show(ShowInContext context) {
    	String filePath;
    
        if (fileViewer == null || context == null)
           return false;
        ISelection sel = context.getSelection();
        Object input = context.getInput();
        if (input instanceof JavaFileEditorInput) {
        	filePath = ((JavaFileEditorInput)input).getPath(input).toString();
        }
		else {
			filePath = ((FileEditorInput)input).getFile().getRawLocation().toString();
		}
		
        
        showFile(filePath);
        return false;
     }
    private void createMenuItems(Menu menu) {

        manageItem = new MenuItem(menu,SWT.CASCADE);
        manageItem.setText("Manage FTP Connections");
        manageItem.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	try {
	            String connectionID = null;
	            StructuredSelection sel = (StructuredSelection)comboViewer.getSelection();
	            if (sel.getFirstElement() instanceof FTPConnectionProperties) {
	                connectionID = ((FTPConnectionProperties)sel.getFirstElement()).getConnectionid();
	            }
                FtpConnectionDialog dialog = new FtpConnectionDialog(e.widget.getDisplay().getActiveShell(),connectionID);
            	if (dialog.open() == IDialogConstants.OK_ID) {
            		comboViewer.setInput(dialog.connectionProperties);
            		
            	}
	        	}
	        	catch (Exception ex) {
	        		ex.printStackTrace();
	        	}
            }
        });
        
        disconnectItem = new MenuItem(menu,SWT.CASCADE);
        disconnectItem.setText("Disconnect");
        disconnectItem.setEnabled(false);
        disconnectItem.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	            try {
		            fileProvider.disconnect();
		            disconnectItem.setEnabled(false);
		            connectItem.setEnabled(true);
		            
	            }
	            catch (Exception ex) {
	                ex.printStackTrace();
	            }
            }
        });
        
        connectItem = new MenuItem(menu,SWT.CASCADE);
        connectItem.setText("Re-Connect");
        connectItem.setEnabled(false);
        connectItem.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	            try {
		            fileProvider.connect();
		            disconnectItem.setEnabled(true);
		            connectItem.setEnabled(false);
	            }
	            catch (Exception ex) {
	                ex.printStackTrace();
	            }
            }
        });
        
    }

    

    private void initializeToolBar() {
        IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
    }

    private void initializeMenu() {
        IMenuManager manager = getViewSite().getActionBars().getMenuManager();
    }
    

    public void setFocus() {
    
    }
    
    public void showFile(String filePath) {
    	String directory = filePath.substring(0,filePath.lastIndexOf("/"));
    	directoryTreeViewer.setInput(DirectoryContentProvider.localFS);
    	directoryTreeViewer.setSelection(new StructuredSelection(new File(directory)),true);
    	this.fileViewer.setSelection(new StructuredSelection(new File(filePath)));
    }
    
    
    public void dispose() {

		if (comboViewer.getContentProvider() != null) {
			comboViewer.getContentProvider().dispose();
		}
		if (directoryTreeViewer.getContentProvider() != null) {
			directoryTreeViewer.getContentProvider().dispose();
		}
		if (fileViewer.getContentProvider() != null) {
			fileViewer.getContentProvider().dispose();
		}

    	super.dispose();
    }
    
}
 