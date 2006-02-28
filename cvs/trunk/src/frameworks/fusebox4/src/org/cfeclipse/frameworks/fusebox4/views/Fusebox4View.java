package org.cfeclipse.frameworks.fusebox4.views;

import java.util.Iterator;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericNewFileAction;
import com.rohanclan.cfml.editors.actions.GenericOpenFileAction;

/** 
 * @author Mark Drew
 *
 */
public class Fusebox4View extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	//add rightclicks to add fusebox files
	private Action addAction, addDisplay, addForm, addLayout, addModule, addQuery, addUDF, addURL;
	//core file additions
	private Action addSwitch, addCircuit, addFuseAction, addFuse;
	
	private Action doubleClickAction;
	private Action fileSeleaction;
	private Combo projcombo;
	private Action makeFolderWWWRoot;
	private Action makeFolderCFCRoot;
	private Action makeFolderCF_Root;
	private Action makeFolderDefault;
	private Action associateProjectWithNature;
	private Action refreshPackage;
	
	protected Text text, preview;
	protected Label previewLabel;
	protected LabelProvider labelProvider;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
/*	class NameSorter extends ViewerSorter {
		
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
*/
	/**
	 * The constructor.
	 */
	public Fusebox4View() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
	    //Here we should do the tree and label/table view
	    
	    //create the layout
	    GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
	    
		
		Label projlabel = new Label(parent, SWT.HORIZONTAL);
		projlabel.setText("Project:");
		
		projcombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData combolayoutData = new GridData();
		combolayoutData.grabExcessHorizontalSpace = true;
		projcombo.setLayoutData(combolayoutData);
		
		//Set the contents of the project list
		IProject[] projects = CFMLPlugin.getWorkspace().getRoot().getProjects();
		for(int i = 0; i < projects.length;i++){
			if(projects[i].isOpen()){
				projcombo.add(projects[i].getName());
			}
		}
		projcombo.select(0);
		projcombo.setToolTipText("Select a project to view the fusebox");
		
		
//		Create a "label" to display information in. I'm
		//using a text field instead of a lable so you can
		//copy-paste out of it.
		
		// layout the text field above the treeviewer
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		
		
        
        	
		//Site Tree
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider(projcombo.getText()));
		viewer.setLabelProvider(new ViewLabelProvider());
		//viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(layoutData);
		
		
		//Document output panel
		preview = new Text(parent, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		// layout the text field above the treeviewer
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.heightHint = 100;
		layoutData.horizontalAlignment = GridData.FILL;
		preview.setLayoutData(layoutData);
		
		
		initActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		hookListeners();
		
	}

	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				Fusebox4View.this.fillContextMenu(manager);
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
		//manager.add(action1);
		//manager.add(new Separator());
		//manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		//manager.add(action1);
		//manager.add(action2);
		
		//manager.add(addAction);
		/*manager.add(addDisplay);
		manager.add(addForm);
		manager.add(addLayout);
		manager.add(addModule);
		manager.add(addQuery);
		manager.add(addUDF);
		manager.add(addURL);*/
		
		
		manager.add(new Separator());
		//manager.add(this.makeFolderCF_Root);
		//manager.add(this.makeFolderCFCRoot);
		//manager.add(this.makeFolderDefault);
		//manager.add(this.makeFolderWWWRoot);
		//manager.add(this.associateProjectWithNature);
		manager.add(this.refreshPackage);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		//manager.add(action1);
		//manager.add(action2);
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
		
		this.refreshPackage  = new Action() {
		    public void run() {
		        refresh();
		    }
		};
		this.refreshPackage.setText("Refresh Package");
		this.refreshPackage.setToolTipText("Refresh Package");
		
		this.associateProjectWithNature = new Action() {
		    public void run(){
		        TreeObject selectedItem = getSelectedDocItem();
		        IProject project = null;
	    		if(selectedItem == null) return;
	    		if(selectedItem instanceof ProjectNode) {
	    		    ProjectNode prjNode = (ProjectNode)getSelectedDocItem();
	    		    project = prjNode.getProject();
	    		    setNature(project);
	    		
	    		}
		        
		    }
		};
		this.associateProjectWithNature.setText("Make this project a CFE project");
		this.associateProjectWithNature.setToolTipText("Make this project a CFE project");
		//this.associateProjectWithNature.setEnabled(false);
		
		 
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
			    GenericNewFileAction nfa = new GenericNewFileAction();
			    nfa.setFilename("test.cfm");
			    nfa.setContents("a test string");
			    nfa.run();
			    
				showMessage("Fusebox Files created");
			}
		};
		action1.setText("Add Fusebox Files");
		action1.setToolTipText("This inserts the standard fusebox files");
		
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
			    //SnipSmartDialog dia = new SnipSmartDialog();
			    //dia.parse();
				showMessage("Added a fuseaction");
			}
		};
		action2.setText("add fuseaction");
		action2.setToolTipText("Inserts a fuseaction");
		action2.setImageDescriptor(PluginImages.getImageRegistry().getDescriptor(PluginImages.ICON_FBX_FUSEACTION));
		
		//private Action addAction, addDisplay, addForm, addLayout, addModule, addQuery, addUDF, addURL;
		addAction = new Action() {
			public void run() {
			    //SnipSmartDialog dia = new SnipSmartDialog();
			    //dia.parse();
				showMessage("This action will add a new Action File to your application");
			}
		};
		addAction.setText("Add Action File act_<file>.cfm");
		addAction.setToolTipText("Inserts a Action File");
		addAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
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

	
	protected void hookListeners() {
	  viewer.addSelectionChangedListener(new ISelectionChangedListener() {
	      public void selectionChanged(SelectionChangedEvent event) 
			{
	          IStructuredSelection selection = (IStructuredSelection)event.getSelection(); 
              Iterator structIter = selection.iterator(); 
             // text.setText(selection.getClass().toString());
              
              
              while(structIter.hasNext()) 
              { 
                    // text.setText("Selection has type: \'" + structIter.next().getClass() + "\'"); 
                   if(structIter.next() instanceof FileNode){
                       	//If this selected item is a FileNode, get its path
                       IStructuredSelection thisel = (IStructuredSelection)viewer.getSelection();
                       preview.setText(thisel.getFirstElement().toString());
           				
                       
                   }
              }
	          
			}
	      
	  });
	  
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
	
	/**
	 * Set the nature of this project when we look at it
	 **/
	public void setNature(IProject project){
	    	
	    
	    try {
	        System.out.println("Call of nature");
	        IProjectDescription description = project.getDescription();
	        String[] natures = description.getNatureIds();
	        String[] newNatures = new String[natures.length + 1];
	        System.arraycopy(natures, 0, newNatures, 0, natures.length);
	        newNatures[natures.length] = "com.rohanclan.cfml.natures.CFENature";
	        description.setNatureIds(newNatures);
	        
	        project.setDescription(description, null);
	
	    } catch (CoreException e) {
	        // Something went wrong
	         System.out.println("Call to project " + "nature failed because: " + e.getMessage());
	     }

	
	}
	public void refresh(){
	    viewer.refresh();
	    showMessage("Fusebox files refreshed");
	    
	}
}