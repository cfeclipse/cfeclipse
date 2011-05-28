package org.cfeclipse.frameworks.fusebox.views;

import java.util.Iterator;

import org.cfeclipse.frameworks.fusebox.FuseboxPlugin;
import org.cfeclipse.frameworks.fusebox.actions.OpenFileAction;
import org.cfeclipse.frameworks.fusebox.dialogs.AddCurcuitDialog;
import org.cfeclipse.frameworks.fusebox.dialogs.AddFuseDialog;
import org.cfeclipse.frameworks.fusebox.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox.objects.FBXCircuit;
import org.cfeclipse.frameworks.fusebox.objects.FBXDo;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuse;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuseAction;
import org.cfeclipse.frameworks.fusebox.objects.FBXObject;
import org.cfeclipse.frameworks.fusebox.util.PluginImages;
import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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

public class FBX3View extends ViewPart {
	public final static String ID_FBXVIEW = "org.cfeclipse.frameworks.fusebox.views";
	private TreeViewer viewer;
	private Combo projcombo;
    private Text text;
    private Label projlabel, filterlabel;
    private Label projroot; 
	private DrillDownAdapter drillDownAdapter;
	private Action refresh;
	private Action openfile;
	private Action doubleClickAction;
	private Action addcircuit;
	private Action addfuseaction;
	private Action addcorefiles;
	private Text helptext;
	//private CircuitFilter circuitfilter;
	//private FuseactionFilter fuseactionfilter;
	private CircuitFuseactionFilter circfusefilter;
	private Button clearfilter;
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
	public FBX3View() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);
		
		/*IWorkbench wb = PlatformUI.getWorkbench(); 
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow(); 
		IWorkbenchPage page = win.getActivePage();
		
		IEditorPart iep = page.getActiveEditor();
		if (iep.getEditorInput() instanceof IFileEditorInput) { 
            activeFile = ((IFileEditorInput) iep.getEditorInput()).getFile();
		}*/
		
		/*
		try {
			System.out.println(project.getName());
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		}*/
		
		//Create a projects dropdown
		  Label projlabel = new Label(parent,SWT.HORIZONTAL);
		  projlabel.setText("Project:");
          
		projcombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		projcombo.setLayoutData(layoutData);
		//ComboModifyListener modifyListener = new ComboModifyListener(projcombo);  
	    //Here we add the project list
		IProject[] projects =  FuseboxPlugin.getWorkspace().getRoot().getProjects();
		for(int i = 0; i < projects.length; i++) {
			if(projects[i].isOpen()){
				projcombo.add(projects[i].getName());
			}
		}
		projcombo.select(0);
		projcombo.setToolTipText("Select a project to view the Fusebox application");
		
		
		
		
		//Show the path
		projroot = new Label(parent,SWT.HORIZONTAL);
		
		
		//Set the filtering item
		//I shall add this later as a nice function so that users can put circuit.fuseaction and it filters on that
		
		Label filterLabel = new Label(parent,SWT.HORIZONTAL);
		filterLabel.setText("Filter:");
		final GridData textgridData = new GridData(GridData.GRAB_HORIZONTAL);
		textgridData.widthHint = 100;
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(textgridData);
		text.setToolTipText("Enter the the item you want to filter on in the format circuit.fuseaction");
	    
		clearfilter = new Button(parent, SWT.NONE);
		clearfilter.setText("clear");
		clearfilter.setToolTipText("Click here to remove any filter you have entered");
		
		
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalSpan = 3;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.getControl().setLayoutData(layoutData);
		drillDownAdapter = new DrillDownAdapter(viewer);
		
		FBXViewContentProvider fbxContent = new FBXViewContentProvider(projcombo.getText());
		viewer.setUseHashlookup(true);
		viewer.setContentProvider(fbxContent);
		viewer.setLabelProvider(new ViewLabelProvider());
		//removed the view sorter as it sorts by name and it should all be by addition
		//viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		viewer.expandToLevel(2);
		//Here we want to add a selection changed 
		//viewer.addFilter(new FBX3ViewerFilter());
		
		//Here we have the help section
		layoutData = new GridData();
		layoutData.horizontalSpan = 3;
		layoutData.heightHint = 150;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		helptext = new Text(parent, SWT.READ_ONLY | SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
		helptext.setLayoutData(layoutData);
		helptext.setText("help area");
		
		
		
		makeActions();
		hookListeners();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		hookSelection();
		addFilters();
		
	}


	
	
	private void hookSelection(){
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			   public void selectionChanged(SelectionChangedEvent event) {
			       // if the selection is empty clear the label
			       if(event.getSelection().isEmpty()) {
			       		helptext.setText("");
			           return;
			       }
			       if(event.getSelection() instanceof IStructuredSelection) {
			           IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			           StringBuffer toShow = new StringBuffer();
			           for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			           		FBXObject obj = (FBXObject)iterator.next();
			           		helptext.setText(obj.getFusedoc());

			           }
			           // remove the trailing comma space pair
			           if(toShow.length() > 0) {
			               toShow.setLength(toShow.length() - 2);
			           }
			           text.setText(toShow.toString());
			       }
			   }
			});

	}
	
	public void setProject(String project){
		this.projcombo.setText(project);
	}
	private void hookListeners(){
		//This listens to the project combo
		projcombo.addSelectionListener(
				  new SelectionAdapter()
				  {
				    public void widgetSelected(SelectionEvent e)
				    {
				      System.out.println("Selection:"+
				      projcombo.getText());
				      refresh.run();
				      viewer.expandToLevel(2);
				    }
				  }
				 );
		// this listens to the select box
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				//This doesnt seem to get the latest text. The latest text comes after modifiation?
				//text.g
				Utils.println("seaching for: "  + text.getText());
				String searchpattern = text.getText();
		
				if(searchpattern.trim().length() > 0){
					String[] cirfuse = searchpattern.split("\\.");
					//Here we try the split
					
					viewer.addFilter(circfusefilter);
					if(searchpattern.indexOf(".") != -1){
						viewer.expandToLevel(3);
					}else {
						viewer.expandToLevel(2);
					}
					/* ye olde JDKe canne handle it cap'tain
					 if(searchpattern.contains(".")){
						viewer.expandToLevel(3);	
					} else{
						viewer.expandToLevel(2);
					}
					 */
					

					//now we add tests for each
					if (cirfuse.length == 2){
						//since there is a dot
						
						if(cirfuse[0].length() > 0){
							circfusefilter.setCircuitmatch(cirfuse[0]);
						} else {
							circfusefilter.setCircuitmatch(null);
						}
						if(cirfuse[1].length() >0){
							circfusefilter.setFuseactionmatch(cirfuse[1]);
						}
						else {
							circfusefilter.setFuseactionmatch(null);
						}
						
					} 
					else if(cirfuse.length == 1){
						circfusefilter.setCircuitmatch(cirfuse[0]);
					}
				} else{
					viewer.removeFilter(circfusefilter);
					circfusefilter.setFuseactionmatch(null);
					circfusefilter.setCircuitmatch(null);
				}

			}
		});
		
		//this listens to the button to clear filters
		clearfilter.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				viewer.removeFilter(circfusefilter);
				text.setText("");
				refresh();
				viewer.expandToLevel(2);
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
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				FBX3View.this.fillContextMenu(manager);
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
		manager.add(refresh);
		//manager.add(new Separator());
		//manager.add(openfile);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(refresh);
		
		
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		
		if(selection.getFirstElement() instanceof FBXApplication){
			manager.add(addcircuit);
		}
		else if(selection.getFirstElement() instanceof FBXCircuit){
			manager.add(addfuseaction);
		}
		
		
		
		//Here we need to know what we show (maybe) so could do a test)
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refresh);
		//manager.add(openfile);
		//manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		refresh = new Action() {
			public void run() {
			    FBXViewContentProvider fbxContent = new FBXViewContentProvider(projcombo.getText());
				
				viewer.setContentProvider(fbxContent);
			}
		};
		refresh.setText("Refresh View");
		refresh.setToolTipText("Refresh the Fusebox Tree");
		refresh.setImageDescriptor(PluginImages.getImageRegistry().getDescriptor(PluginImages.ICON_REFRESH));
		
		openfile = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		openfile.setText("Action 2");
		openfile.setToolTipText("Action 2 tooltip");
		openfile.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		
		addcircuit = new Action(){
			public void run() {
					addCircuit();
			}
		};
		addcircuit.setText("Add Circuit");
		addcircuit.setToolTipText("Adds a Circuit to the Application");
		addcircuit.setImageDescriptor(PluginImages.getImageRegistry().getDescriptor(PluginImages.ICON_FBX_CIRCUIT));
		
		
		
		addfuseaction = new Action(){
			public void run() {
				 addFuseaction();
			}
		};
		addfuseaction.setText("Add FuseAction");
		addfuseaction.setToolTipText("Adds a fuseaction(switch) statement to the file");
		addcircuit.setImageDescriptor(PluginImages.getImageRegistry().getDescriptor(PluginImages.ICON_FBX_FUSEACTION));
		
		
		
		
		/**
		 * The double click action
		 */
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject prj = FuseboxPlugin.getWorkspace().getRoot().getProject(projcombo.getText());
				
				String swichFileName  = "fbx_Switch.cfm";
				String circFileName  = "fbx_Circuits.cfm";
				
				
				try {
					swichFileName  = prj.getPersistentProperty(new QualifiedName("", "FBXSWITCHFILE"));
					circFileName  = prj.getPersistentProperty(new QualifiedName("", "FBXCIRCUITFILE"));
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			/*
			 * TODO: Set the case insensitive path to a file, not the path itself but need to make a path, 
			 * try it, then if it fails, try it with lowercase. This might be smoething of the file open action.
			 *  */
				
				if (obj instanceof FBXApplication){
					
					//Hopefully we dont need to go and get the path etc now
					FBXApplication fbxapp =(FBXApplication)obj;
					String circuitFile = fbxapp.getCircuitFile().getFullPath().toString();
								
					//showMessage("opening:" + circuitFile);
					OpenFileAction openFileAction = new OpenFileAction();
					openFileAction.setFilename(circuitFile);
					openFileAction.run();
					if(!openFileAction.isSuccess()){
						showMessage("File Not found: " + circuitFile);
						
					}
				}
				else if(obj instanceof FBXCircuit){
					
					//Maybe we should get a run action from the object?
					//Failing that ,we should ask the object what to do 
					FBXCircuit circ = (FBXCircuit)obj;
					
					
					if(circ.getSwitchFile() != null){
						OpenFileAction openFileAction = new OpenFileAction();
						String switchPath = circ.getSwitchFile().getFullPath().toString();
						openFileAction.setFilename(switchPath);
						openFileAction.run();
						if(!openFileAction.isSuccess()){
							showMessage("File Not found: " + switchPath);
							
						}
					}
					else{
						Utils.println("No switch file exists.. create?");
						showMessage("Switch File not found");
					}
					
				}	
				else if(obj instanceof FBXFuseAction){
					FBXFuseAction fuseaction = (FBXFuseAction)obj;
					FBXCircuit circ = fuseaction.getCircuit();
					String switchFile = fuseaction.getSwichFile().getFullPath().toString();
					
					OpenFileAction openFileAction = new OpenFileAction();
				
					openFileAction.setFilename(switchFile);
					openFileAction.open(fuseaction.getTagStart(), fuseaction.getTagEnd());
					if(!openFileAction.isSuccess()){
						showMessage("File Not found: " + switchFile);
						
					}
				}
				else if(obj instanceof FBXFuse){
					FBXFuse fuse= (FBXFuse)obj;
					FBXFuseAction fuseaction = (FBXFuseAction)fuse.getParent();
					FBXCircuit circ = fuseaction.getCircuit();
					
					
					
					OpenFileAction openFileAction = new OpenFileAction();
					String fusePath = projcombo.getText() + "/" + fuse.getPath();
					
					
					//showMessage(fusePath);
					//We open the file then go to the line.(which we should have stored in the FBXFuseAction or we get it 
					// again with a "getFuseActionLocation(circuit, fuseaction) from the FBXparser
					//Find if it already has 
					openFileAction.setFilename(fusePath);
					openFileAction.run();
					if(!openFileAction.isSuccess()){
						showMessage("File Not found: " + fusePath);
						
					}
				}
				else if (obj instanceof FBXDo){
					//we now redirect to the other part of the tree
					FBXDo dopath = (FBXDo)obj;
					
					showMessage(" This DO action points to "+ dopath.getAction() + viewer.getSelection());
					//ISelection sel = obj.toString();
					//viewer.setSelection();
					
				}
				else{
					showMessage("Double-click detected on "+obj.toString() + " " + obj.getClass().toString());
				}
			}
		};
	}

	private void addFilters(){
		//This is called on the update.
		//circuitfilter = new CircuitFilter();
		//fuseactionfilter = new FuseactionFilter();
		this.circfusefilter = new CircuitFuseactionFilter();
	}
	

	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Fusebox View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public void refresh(){
		FBXViewContentProvider fbxContent = new FBXViewContentProvider(projcombo.getText());
		viewer.setContentProvider(fbxContent);
		viewer.expandToLevel(2);
	}
	public void addCircuit(){
		//We need to get the slected object and test it
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		FBXApplication app  = (FBXApplication)selection.getFirstElement();
		//Here we create a dialog and pass the items to the app
		AddCurcuitDialog dialog = new AddCurcuitDialog(this.getViewSite().getShell());
		
		if(dialog.open() == IDialogConstants.OK_ID){
			app.addCircuit(dialog.getCircuitName(), dialog.getCircuitPath(), dialog.isCreatefolders());
		}
		
		
	}
	public void addFuseaction(){
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		FBXCircuit circuit  = (FBXCircuit)selection.getFirstElement();
		AddFuseDialog dialog = new AddFuseDialog(this.getViewSite().getShell());
		
		if(dialog.open() == IDialogConstants.OK_ID){
			circuit.addFuseaction(dialog.getFuseactionName());
		}
		
	}
	public void addCoreFiles(){
		//this would appear if there is a need for core files
		
	}

	

}