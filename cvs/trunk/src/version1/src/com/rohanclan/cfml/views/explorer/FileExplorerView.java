/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseEvent;

import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author Stephen Milligan
 *
 * The explorer view is suposed to be a faithful replication
 * of the file explorer tab in Homesite/CFStudio.
 */
public class FileExplorerView extends ViewPart {

    ComboViewer comboViewer = null;
    TreeViewer directoryTreeViewer = null;
    TableViewer fileViewer = null;
    
    
    public void createPartControl(Composite parent) {
    	
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout containerLayout = new GridLayout();
        containerLayout.numColumns = 2;
        container.setLayout(containerLayout);
        
        
        // Combo viewer
        comboViewer = new ComboViewer(container, SWT.READ_ONLY);
        comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent e) {
            	try {
            		directoryTreeViewer.setInput(((StructuredSelection)e.getSelection()).getFirstElement());
            		fileViewer.setInput(((StructuredSelection)e.getSelection()).getFirstElement());
            	}
            	catch (Exception ex) {
            		ex.printStackTrace();
            	}
            }
        });
        comboViewer.setLabelProvider(new ComboLabelProvider());
        comboViewer.setContentProvider(new ComboContentProvider());
        final Combo combo = comboViewer.getCombo();
        final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		
        
        combo.setLayoutData(gridData);
        comboViewer.setInput(new LocalFileSystem());

        Button addButton = new Button(container,SWT.PUSH);
        addButton.addMouseListener(new MouseListener() {
            public void mouseUp(MouseEvent e) {
            	try {
	            	FtpConnectionDialog dialog = new FtpConnectionDialog(e.widget.getDisplay().getActiveShell(),null);
	            	if (dialog.open() == IDialogConstants.OK_ID) {
	            		comboViewer.setInput(dialog.connectionProperties);
	            		
	            	}
            	}
            	catch (Exception ex) {
            		ex.printStackTrace();
            	}
            }
            
            public void mouseDown(MouseEvent e) {
            	
            }
            
            public void mouseDoubleClick(MouseEvent e) {
            	
            }
        });
        addButton.setText("New...");
        
        
        
        // This is what makes the controls resizable
        SashForm sash = new SashForm(container,SWT.VERTICAL);
        GridData sashData = new GridData(GridData.FILL_BOTH);
        sashData.horizontalSpan = 2;
        sash.setLayoutData(sashData);
        
        // Directory tree
        directoryTreeViewer = new TreeViewer(sash, SWT.BORDER);
        directoryTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent e) {
                fileViewer.setInput(e.getSelection());
            }
        });
        directoryTreeViewer.setSorter(new DirectorySorter());
        directoryTreeViewer.setLabelProvider(new DirectoryLabelProvider());
        directoryTreeViewer.setContentProvider(new DirectoryContentProvider());
        //final TableTree tableTree = directoryTreeViewer.getTableTree();
        final Tree tree = directoryTreeViewer.getTree();
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        directoryTreeViewer.setInput(new LocalFileSystem());
        

        
        fileViewer = new TableViewer(sash, SWT.BORDER);
        fileViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent e) {
                String filename = e.getSelection().toString();
                if (filename.indexOf("[") == 0) {
                    filename = filename.substring(1,filename.length()-1);
                }
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    	        JavaFileEditorInput input = new JavaFileEditorInput(new File(filename));
    	        try {
    	            page.openEditor(input,"com.rohanclan.cfml.editors.CFMLEditor");
    	        }
    	        catch (Exception ex) {
    	            ex.printStackTrace();
    	        }
            }
        });
        fileViewer.setSorter(new Sorter());
        fileViewer.setLabelProvider(new FileLabelProvider());
        fileViewer.setContentProvider(new FileContentProvider());
        final Table table = fileViewer.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        fileViewer.setInput(new LocalFileSystem());
        
        createActions();
        initializeToolBar();
        initializeMenu();
    	
    }

    private void createActions() {
    }

    private void initializeToolBar() {
        IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
    }

    private void initializeMenu() {
        IMenuManager manager = getViewSite().getActionBars().getMenuManager();
    }

    public void setFocus() {
    
    }
    
    public void dispose() {
    	comboViewer.getContentProvider().dispose();
    	directoryTreeViewer.getContentProvider().dispose();
    	fileViewer.getContentProvider().dispose();
    	super.dispose();
    }
    
}
