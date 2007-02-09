package org.cfeclipse.frameworks.fusebox.wizards;

import org.cfeclipse.frameworks.fusebox.dialogs.AddCurcuitDialog;
import org.cfeclipse.frameworks.fusebox.dialogs.EditApplicationDialog;
import org.cfeclipse.frameworks.fusebox.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox.objects.FBXCircuit;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuse;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuseAction;
import org.cfeclipse.frameworks.fusebox.objects.FBXRoot;
import org.cfeclipse.frameworks.fusebox.views.ViewLabelProvider;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The "New" wizard page allows setting the container for
 * the new file as well as the file name. The page
 * will only accept file name without the extension OR
 * with the extension that matches the expected one (cfm).
 */

public class Fusebox3NewApplicationWizzard extends WizardPage {
	private Text applicationPath;
	private Text applicationName;
	private TreeViewer applicationTree;
	private Text fileText;
	private ISelection selection;
	private Action doubleClickAction;
	private Action addCircuit;
	
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public Fusebox3NewApplicationWizzard(ISelection selection) {
		super("wizardPage");
		setTitle("Fusebox 3 Application Wizzard");
		setDescription("This wizzard creates a new Fusebox 3 application");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL | SWT.RESIZE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
	
		//I never see Labels initalised, I guess they dont need to be properties
		
		/*
		 * The application root field
		 */
		Label appRootLabel = new Label(container, SWT.NONE);
		appRootLabel.setText("Application Root:");
		
		applicationPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		applicationPath.setLayoutData(gd);
		applicationPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		
		/*
		 * Once we do the above, we create the tree view
		 */
		//Label
		Label appTreeLabel = new Label(container, SWT.NONE);
		GridData labelgd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		labelgd.horizontalSpan = 3;
		appTreeLabel.setLayoutData(labelgd);
		
		appTreeLabel.setText("Application Tree: \n" +
				"Define your Circuits, FuseActions and fuses using the model below, right click to get options at each node\n" +
				"Double click on a node to edit");
		//Tree
	
		applicationTree = new TreeViewer(container, SWT.BORDER);
		GridData mygd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		mygd.horizontalSpan = 3;
		mygd.grabExcessHorizontalSpace = true;
		mygd.grabExcessVerticalSpace = true;
		applicationTree.getControl().setLayoutData(mygd);
		applicationTree.setContentProvider(new FBX3WizardContentProvider());
		applicationTree.setLabelProvider(new ViewLabelProvider());
		applicationTree.setInput(getInitialInput());
	
		
		//Display our tree dialog with a model.
		//the first node is by default, called home or something
		//we then add right click actions to define the rest
		
	
		
		initialize();
		//dialogChanged();
		makeActions();
		hookDoubleClickAction();
		hookContextMenu();
		setControl(container);
		
	}
	
	
	
	
	
	private void hookDoubleClickAction(){
		applicationTree.addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					doubleClickAction.run();
				}
			});
	}
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		Menu menu = menuMgr.createContextMenu(applicationTree.getControl());
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				Fusebox3NewApplicationWizzard.this.fillContextMenu(manager);
			}
		});
		applicationTree.getControl().setMenu(menu);
		
	}
	private void makeActions() {
		//These are the context menu (right click) actions
		addCircuit = new Action(){
			public void run() {
				
				addCircuit();
				
			}
		};
		addCircuit.setText("Add Circuit");
		addCircuit.setToolTipText("Adds a Circuit to the Application");
		
		//These are the double click actions
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = applicationTree.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				
				if (obj instanceof FBXApplication){
					
					editApplication(obj);
						
					
				}
				else if(obj instanceof FBXCircuit){
					
					showMessage("Edit FBXCircuit");
					
				
					
				}	
				else if(obj instanceof FBXFuseAction){
					showMessage("Edit FBXCircuit");
				}
				else if(obj instanceof FBXFuse){
					showMessage("Edit FBXFuse");
				}
				
				else{
					showMessage("Double-click detected on "+obj.toString() + " " + obj.getClass().toString());
				}
			}

			
		};
	}
	
	
	//Common actions used above
	public void addCircuit(){
		
		ISelection selection = applicationTree.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		
		if (obj instanceof FBXApplication){
			//Show the edit FBXApplication dialog
			FBXApplication app = (FBXApplication)obj;
			AddCurcuitDialog addCircuit =  new AddCurcuitDialog(applicationTree.getControl().getShell());
			addCircuit.setCreatefolders(true);
			
			if(addCircuit.open() == IDialogConstants.OK_ID){
				FBXCircuit circ = new FBXCircuit(addCircuit.getCircuitName(), addCircuit.getCircuitPath());
				circ.setCreateFolders(addCircuit.isCreatefolders());
				app.addChild(circ);
				//now we have set it, refresh the tree?
				applicationTree.refresh();
				
			}
				
			
		}
	}
	
	
	/**
	 * @param obj
	 */
	private void editApplication(Object obj) {
		//Show the edit FBXApplication dialog
		FBXApplication app = (FBXApplication)obj;
		EditApplicationDialog editApp = new EditApplicationDialog(applicationTree.getControl().getShell());
		editApp.setAppNameValue(app.getName());
		
		if(editApp.open() == IDialogConstants.OK_ID){
			app.setName(editApp.getAppNameValue());
			//now we have set it, refresh the tree?
			applicationTree.refresh();
		}
	}
	
	public void editApplication(FBXApplication app){
		
	}
	
	
	private void fillContextMenu(IMenuManager manager) {
		
		
		IStructuredSelection selection = (IStructuredSelection)applicationTree.getSelection();
		
		if(selection.getFirstElement() instanceof FBXApplication){
			manager.add(addCircuit);
		}
		
		
		
		
		//Here we need to know what we show (maybe) so could do a test)
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	
	
	private void initialize() {
		if (selection!=null && selection.isEmpty()==false && selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection)selection;
			if (ssel.size()>1) return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				applicationPath.setText(container.getFullPath().toString());
			}
		}
		//fileText.setText("fusebox.cfm");
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog =
			new ContainerSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				applicationPath.setText(((Path)result[0]).toOSString());
			}
		}
	}
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		String container = applicationPath.getText();
		

		if (container.length() == 0) {
			updateStatus("PLease specify the root of your application");
			return;
		}
		
		updateStatus(null);
	}

	private FBXRoot getInitialInput(){
		FBXRoot invisibleroot = new FBXRoot("");
		FBXApplication app = new FBXApplication("home");
		invisibleroot.addChild(app);
		return invisibleroot;
		
	}
	//Helper methods for this view
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
				applicationTree.getControl().getShell(),
			"Fusebox View",
			message);
	}
	

}