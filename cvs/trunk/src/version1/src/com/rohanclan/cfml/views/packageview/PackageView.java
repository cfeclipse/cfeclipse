package com.rohanclan.cfml.views.packageview;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.packageview.components.PackageViewTree;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 * 
 * TODO: This PackageView will be re-modelled to display the current project's
 * CFC's as a dot notated packages. With the help of a new parser, it should be
 * able to parse all the CFC's and give us all the methods and method signatures
 * On Save actions should trigger this view. (or whatever action the Java
 * perspective uses to update the Package Explorer
 * 
 * @author markd
 * @author mike nimer
 */

public class PackageView extends ViewPart
{
    private PackageViewTree packageTreeView;
    
    private Composite localCFCServices;
    private ToolBarManager toolBarManager;
    private Action acRemoteCFCFilterRemote;
    private Action acRemoteCFCFilterPublic;
    private Action acRemoteCFCFilterPackage;
    private Action acRemoteCFCFilterPrivate;    
    private Action acRefreshTree;
    

    /*
     * The content provider class is responsible for providing objects to the
     * view. It can wrap existing objects in adapters or simply return objects
     * as-is. These objects may be sensitive to the current input of the view,
     * or ignore it and always show the same content (like Task List, for
     * example).
     */

    class NameSorter extends ViewerSorter
    {

        public int compare(Viewer viewer, Object e1, Object e2)
        {
            /*
             * if(e1 instanceof FolderNode && !(e2 instanceof FolderNode)) {
             * return -1; } else if(e2 instanceof FolderNode && !(e1 instanceof
             * FolderNode)) { return 1; }
             */
            return super.compare(viewer, e1, e2);
        }
    }

    /**
     * The constructor.
     */
    public PackageView()
    {}


    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent)
    {
        localCFCServices = new Composite(parent, SWT.NONE);
                        
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;        
        localCFCServices.setLayout(layout); 

        createPackageView(localCFCServices);

    }

   
    /**
     * create the remote CFC level view of the components 
     * @param parent
     */
    private void createPackageView(Composite parent)
    {
        
        createViewToolbarAndActions(parent); //TODO: move this to the top of the view
       
        
        packageTreeView = new PackageViewTree(parent, getViewSite(), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        packageTreeView.addSelectionChangedListener(new ISelectionChangedListener()
        {
            public void selectionChanged(SelectionChangedEvent event)
            {
                //TreeObject node = (TreeObject) ((IStructuredSelection)event.getSelection()).getFirstElement();
            }
        });
        
        GridData gd = new GridData();
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        gd.horizontalAlignment = SWT.FILL;
        gd.verticalAlignment = SWT.FILL;
        packageTreeView.getTree().setLayoutData(gd);
        packageTreeView.setInput(getViewSite());
    }
    
    

    
    private void createViewToolbarAndActions(Composite parent) 
    {
        IToolBarManager tbm = getToolBarManager(parent);
        
        
        acRemoteCFCFilterRemote = new Action() 
        {
            public void run() 
            {
                packageTreeView.doFilterRemote();
            }
        };
        acRemoteCFCFilterRemote.setText("Show Remote");
        acRemoteCFCFilterRemote.setToolTipText("Show Remote");
        acRemoteCFCFilterRemote.setImageDescriptor( ImageDescriptor.createFromImage( CFPluginImages.get(CFPluginImages.ICON_METHOD_REMOTE) ) );
        
        
        acRemoteCFCFilterPublic = new Action() 
        {
            public void run() 
            {
                packageTreeView.doFilterPublic();                
            }
        };
        acRemoteCFCFilterPublic.setText("Show Public");
        acRemoteCFCFilterPublic.setToolTipText("Show Public");
        acRemoteCFCFilterPublic.setImageDescriptor( ImageDescriptor.createFromImage( CFPluginImages.get(CFPluginImages.ICON_METHOD_PUBLIC) ) );
        
        
        acRemoteCFCFilterPackage = new Action() 
        {
            public void run() 
            {
                packageTreeView.doFilterPackage();
            }
        };
        acRemoteCFCFilterPackage.setText("Show Package");
        acRemoteCFCFilterPackage.setToolTipText("Show Package");
        acRemoteCFCFilterPackage.setImageDescriptor( ImageDescriptor.createFromImage( CFPluginImages.get(CFPluginImages.ICON_METHOD_PACKAGE) ) );
        
        
        acRemoteCFCFilterPrivate = new Action() 
        {
            public void run() 
            {
                packageTreeView.doFilterPrivate();
            }
        };
        acRemoteCFCFilterPrivate.setText("Show Private");
        acRemoteCFCFilterPrivate.setToolTipText("Show Private");
        acRemoteCFCFilterPrivate.setImageDescriptor( ImageDescriptor.createFromImage( CFPluginImages.get(CFPluginImages.ICON_METHOD_PRIVATE) ) );
        
        acRefreshTree = new Action()
        {
            public void run()
            {
                //todo 
                //ViewContentProvider contentprovider = new ViewContentProvider(this, viewSite);
                //viewer.setContentProvider(contentprovider);
            }	
        };
        acRefreshTree.setText("Refresh View");
        acRefreshTree.setToolTipText("Refresh the Component Tree");
        acRefreshTree.setImageDescriptor(CFPluginImages.getImageRegistry().getDescriptor(CFPluginImages.ICON_REFRESH));

        
        
        
        tbm.add(acRemoteCFCFilterRemote);
        tbm.add(acRemoteCFCFilterPublic);
        tbm.add(acRemoteCFCFilterPackage);
        tbm.add(acRemoteCFCFilterPrivate);
  
        // this forces the toolbar to get the actions that we just added.  
        // I think it should do it by default, (and it took me like 2 days to figure this one 
        // method call out) but...  (stupid eclipse)
        tbm.update(true);
    }

    
    /**
     * Returns the tool bar manager that is used to manage tool items in the
     * form's title area (where you can add and delete forms).
     *
     * @return form tool bar manager
     */
    protected IToolBarManager getToolBarManager(Composite parent) 
    {
        if (toolBarManager == null) 
        {
            toolBarManager = new ToolBarManager(SWT.FLAT);
            //ToolBar toolbar = 
            toolBarManager.createControl(parent);
//               toolbar.setBackground(getBackground());
//               toolbar.setForeground(getForeground());
//               toolbar.setCursor(FormsResources.getHandCursor());

            parent.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    if (toolBarManager != null) {
                    	
                        toolBarManager.dispose();
                        toolBarManager = null;
                    }
                }
            });
        }
       return toolBarManager;
    }
    

       
    
    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus()
    {
        packageTreeView.getControl().setFocus();
    }
}