package org.cfeclipse.cfml.views.packageview.components;

import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.cfml.editors.actions.GenericOpenFileAction;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.views.browser.BrowserView;
import org.cfeclipse.cfml.views.packageview.FolderTypes;
import org.cfeclipse.cfml.views.packageview.ViewContentProvider;
import org.cfeclipse.cfml.views.packageview.ViewLabelProvider;
import org.cfeclipse.cfml.views.packageview.objects.ComponentNode;
import org.cfeclipse.cfml.views.packageview.objects.FunctionNode;
import org.cfeclipse.cfml.views.packageview.objects.ProjectNode;
import org.cfeclipse.cfml.views.packageview.objects.TreeObject;
import org.cfeclipse.cfml.views.packageview.objects.TreeParent;
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
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * A reusable component for viewing a specific type of file inside of open packages.
 *  
 * @author markd
 * @author mike nimer
 *
 */
public class PackageViewTree extends TreeViewer
{
    private TreeViewer viewer;
    private IViewSite viewSite;
    
    //private DrillDownAdapter drillDownAdapter;
    //Right Click actions
    private Action makeFolderWWWRoot;
    private Action makeFolderCFCRoot;
    private Action makeFolderCF_Root;
    private Action makeFolderDefault;
    private Action funcCreateObject; //Creates a cfscript version of a function
    private Action funcInvokeObject; //Creates a Invoke version of a function
    private Action compCreateObject; //Creates a cfscript version of a component
    private Action getFunctionDetails; //Displays a message saying what this function is 
    private Action getComponentDetails; //Displays a message saying what this function is 
    private Action getDescription; //Opens the internal browser to show the details of component 
    //http://markdrew.local/CFIDE/componentutils/cfcexplorer.cfc?NAME=ggcc7.controller.mailer&METHOD=getcfcinhtml
    private Action doubleClickAction;


    public PackageViewTree(Composite parent, IViewSite site)
    {
        super(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        setup(parent, site);
    }
    public PackageViewTree(Composite parent, IViewSite site, int style)
    {
        super(parent, style);
        setup(parent, site);
    }
    
    public void dispose()
    {        
    }

    protected void setup(Composite parent, IViewSite site)
    {
        
        this.viewer = this;
        this.viewSite = site;

        this.setContentProvider(new ViewContentProvider(this, viewSite));
        this.setLabelProvider(new ViewLabelProvider()); 
        
        ExpandCollapseListener expandCollapseListener = new ExpandCollapseListener();
        this.addTreeListener(expandCollapseListener);
        
        
        this.refresh();         
        
        makeActions();
        hookContextMenu();
        hookContextMenu();
        hookDoubleClickAction();
        contributeToActionBars();

    }

    /**
     * show/hide remote methods
     *
     */
    public void doFilterRemote()
    {
        //TODO
    }

    /**
     * show/hide public methods
     *
     */
    public void doFilterPublic()
    {
        //TODO
    }

    /**
     * show/hide package methods
     *
     */
    public void doFilterPackage()
    {
        //TODO
    }
    
    /**
     * show/hide private methods
     *
     */
    public void doFilterPrivate()
    {
        //TODO
    }
    
    /**
     * filter out everything but CFC files
     *
     */
    public void doFilterCFCFileType()
    {
        //TODO
    }

    /**
     * filter out everything but images in the project
     *
     */
    public void doFilterImageFileType()
    {
        //TODO
    }

    /**
     * create the main right-click actions for the tree.
     *
     */
    private void initMakeActions()
    {
        
        this.makeFolderCF_Root = new Action()
        {
            public void run()
            {
                setFolderType(FolderTypes.CF_ROOT);
            }
        };
        this.makeFolderCF_Root.setText("Add to custom tag path");
        this.makeFolderCF_Root.setToolTipText("Add to custom tag path");

        this.makeFolderCFCRoot = new Action()
        {
            public void run()
            {
                setFolderType(FolderTypes.CFCROOT);
            }
        };
        this.makeFolderCFCRoot.setText("Add to CFC path");
        this.makeFolderCFCRoot.setToolTipText("Add to CFC path");

        this.makeFolderDefault = new Action()
        {
            public void run()
            {
                setFolderType(FolderTypes.DEFAULT);
            }
        };
        this.makeFolderDefault.setText("Make a normal folder");
        this.makeFolderDefault.setToolTipText("Make a normal folder");

        this.makeFolderWWWRoot = new Action()
        {
            public void run()
            {
                setFolderType(FolderTypes.WWWROOT);
            }
        };
        this.makeFolderWWWRoot.setText("Make webserver root folder");
        this.makeFolderWWWRoot.setToolTipText("Make webserver root folder");

    }

    
    /**
     * add individual CFC actions to right-click
     *
     */
    private void makeActions()
    {
        initMakeActions();
        //Make the CFC specific Right click actions
        compCreateObject = new Action()
        {
            public void run()
            {
            	System.out.println("Component Node");
                ISelection selection = viewer.getSelection();
                ComponentNode obj = (ComponentNode) ((IStructuredSelection) selection).getFirstElement();
                insert(obj.getCreateObjectSnippet());
            }
        };
        compCreateObject.setText("Insert Create Object");
        compCreateObject.setToolTipText("Inserts CreateObject code");

        funcCreateObject = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                FunctionNode obj = (FunctionNode) ((IStructuredSelection) selection).getFirstElement();
                insert(obj.getCreateObjectSnippet());
            }
        };
        funcCreateObject.setText("Insert CreateObject");
        funcCreateObject.setToolTipText("Inserts CreateObject code");

        funcInvokeObject = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                FunctionNode obj = (FunctionNode) ((IStructuredSelection) selection).getFirstElement();
                insert(obj.getInvokeSnippet());
            }
        };
        funcInvokeObject.setText("Insert Invoke");
        funcInvokeObject.setToolTipText("Inserts cfinvoke code");

        getFunctionDetails = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                FunctionNode obj = (FunctionNode) ((IStructuredSelection) selection).getFirstElement();
                showMessage(obj.getDetails());

            }
        };
        getFunctionDetails.setText("Get Details");

        getComponentDetails = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                ComponentNode obj = (ComponentNode) ((IStructuredSelection) selection).getFirstElement();
                showMessage(obj.getDetails());
            }
        };
        getComponentDetails.setText("Get Details");

        getDescription = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                ComponentNode obj = (ComponentNode) ((IStructuredSelection) selection).getFirstElement();
                //showMessage(obj.getDetails());
                CFMLPropertyManager propMan = new CFMLPropertyManager();

                try
                {
                    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                    BrowserView browser = (BrowserView) page.showView(BrowserView.ID_BROWSER);
                    String rootURL = propMan.defaultProjectURL();

                    URL projURL = new URL(rootURL);

                    //todo
                    //browser.setUrl("http://" + projURL.getHost() + "/CFIDE/componentutils/cfcexplorer.cfc?METHOD=getcfcinhtml&NAME=" + obj.getPackageName());
                    //browser.setFocus();
                }
                catch (PartInitException e)
                {
                    e.printStackTrace();
                }
                catch (MalformedURLException mue)
                {
                    mue.printStackTrace();
                }

            }
        };
        getDescription.setText("Get Description");

        /*
        action1 = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                FunctionNode obj = (FunctionNode) ((IStructuredSelection) selection).getFirstElement();
                showMessage(obj.getCreateObjectSnippet());
            }
        };
        action1.setText("Insert CreateObject");
        action1.setToolTipText("Inserts CreateObject code");
        action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
        */

        doubleClickAction = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                Object obj = ((IStructuredSelection) selection).getFirstElement();

                if (obj instanceof ComponentNode)
                {
                    ComponentNode component = (ComponentNode) obj;
                    GenericOpenFileAction openAction = new GenericOpenFileAction(component.getFile());
                    openAction.run();
                }
                else if (obj instanceof FunctionNode)
                {
                    FunctionNode fnode = (FunctionNode) obj;
                    insert(fnode.getInvokeSnippet());
                }
                else {
                	viewer.expandToLevel(obj, 1);
                }

            }
        };
    }

    /**
     * setup the right-click menu for the tree
     *
     */
    private void hookContextMenu()
    {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener()
        {
            public void menuAboutToShow(IMenuManager manager)
            {
                fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        viewSite.registerContextMenu(menuMgr, viewer);
    }

    
    private void fillContextMenu(IMenuManager manager)
    {
        //showMessage(getSelectedDocItem().getClass().toString());
        //WE need to find out what item this is
        if (getSelectedDocItem() instanceof FunctionNode)
        {
            manager.add(funcCreateObject);
            manager.add(funcInvokeObject);
            manager.add(getFunctionDetails);
        }
        if (getSelectedDocItem() instanceof ComponentNode)
        {
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

    private void hookDoubleClickAction()
    {
        viewer.addDoubleClickListener(new IDoubleClickListener()
        {
            public void doubleClick(DoubleClickEvent event)
            {
                doubleClickAction.run();
            }
        });
    }

    
    private void contributeToActionBars()
    {
        IActionBars bars = viewSite.getActionBars();
        //fillLocalPullDown(bars.getMenuManager());
        fillLocalToolBar(bars.getToolBarManager());
    }

    private void showMessage(String message)
    {
        MessageDialog.openInformation(viewer.getControl().getShell(), "Package View", message);
    }

    private void fillLocalToolBar(IToolBarManager manager)
    {
        manager.add(new Separator());
        //drillDownAdapter.addNavigationActions(manager);
    }

    protected void setFolderType(String folderType)
    {
        TreeObject selectedItem = getSelectedDocItem();

        if (selectedItem == null)
            return;
        viewer.update(selectedItem, null);

    }


    
    
    
    private void insert(String insertString)
    {
        if (viewer.getSelection().isEmpty())
        {
            return;
        }
        else
        {
            IEditorPart iep = this.viewSite.getWorkbenchWindow().getActivePage().getActiveEditor();
            IDocument doc = ((ITextEditor) iep).getDocumentProvider().getDocument(iep.getEditorInput());
            ITextEditor ite = (ITextEditor) iep;
            ISelection sel = ite.getSelectionProvider().getSelection();
            int cursorOffset = ((ITextSelection) sel).getOffset();

            try
            {
                doc.replace(cursorOffset, 0, insertString);
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }

        }
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
        if (this.viewer.getSelection().isEmpty())
        {
            return null;
        }
        else
        {
            IStructuredSelection selection = (IStructuredSelection) this.viewer.getSelection();
            selecteditem = (TreeObject) selection.getFirstElement();
        }

        return selecteditem;
    }

    
    class ExpandCollapseListener implements ITreeViewerListener
    {
        public void treeCollapsed(TreeExpansionEvent event)
        {
            if (event.getElement() instanceof ComponentNode)
            {
                
            }
        }
        public void treeExpanded(TreeExpansionEvent event)
        {
            TreeParent node = (TreeParent) event.getElement();

            if (node instanceof ProjectNode)
            {
                ((ProjectNode)node).expand();
            }
            
            //now make sure everything is updated in the viewer
            event.getTreeViewer().refresh(event.getElement(), true);

            // collapse and expand it so that it displays properly. Be careful
            // because this will fire the TreeExpanded and TreeCollapsed events
            // again
            event.getTreeViewer().setExpandedState(event.getElement(), false);
            event.getTreeViewer().setExpandedState(event.getElement(), true);
        }
    }
}
