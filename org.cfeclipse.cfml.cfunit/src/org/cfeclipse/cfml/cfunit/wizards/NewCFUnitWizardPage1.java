package org.cfeclipse.cfml.cfunit.wizards;

import org.cfeclipse.cfml.cfunit.TestFrameworkType;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;
/**
 * The first CFUnit wizard page. This is the main page which asks the user
 * to select their prefrences and test case location.
 * 
 * @author Robert Blackburn
 */
public class NewCFUnitWizardPage1 extends WizardPage {

	private final static int GRID_WIDTH = 3;
	
	private Text containerText;
	private Text fileText;
	private Text frameworkExtendsPath;
	private Text runnerText;
	private Button propertyCreateRunner;
	private Button propertySetupStub;
	private Button propertyTeardownStub;
	private Button propertyOutputStub;
	private ISelection selection;
	private IEditorPart editor = null;
	private Text futLocation;
	private IResource futResource;
	private Button fwCFUnit; // Radio button used to indicate CFUnit framework
	private Button fwCfcUnit; // Radio button used to indicate cfcUnit framework
	private Button fwMxUnit; // Radio button used to indicate MXUnit framework
	
	private IWizardPage nextPage;
	
	/**
	 * Constructor for NewCFUnitWizardPage.
	 * @param pageName
	 */
	public NewCFUnitWizardPage1(ISelection selection, String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		this.selection = selection;
	}

	public NewCFUnitWizardPage1(IEditorPart editorPart, String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		this.editor = editorPart;
	}
	
	/** Returns the wizard page that will to be shown if the user was to press 
	 * the Next button.
	 */
	public IWizardPage getNextPage() {
		return nextPage;
	}
	
	/** Changes the wizard page that will to be shown if the user was to press 
	 * the Next button.
	 */
	public void setNextPage(IWizardPage p) {
		nextPage = p;
	}
	
	/**
	 * Creates the top level control for this dialog page under the given parent composite.
	 * @param parent The parent composite
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = GRID_WIDTH;
		layout.verticalSpacing = 9;

		createNameControls( container );
		createPathControls( container );
		createSeparator( container );
		createFrameworkControls( container );
		createSuperclassControls( container );
		createMethodStubsControls( container );
		createRunnerControls( container );
		createSeparator( container );
		createClassUnderTestControls( container );

		initialize();
		dialogChanged();
		setControl( container );
		setFilenameFocus();
		
	}
	
	/**
	 * Creates the class path controls
	 * @param parent The parent composite
	 */
	public void createPathControls(Composite parent) {
		Label label = new Label(parent, SWT.NULL);
		label.setText("Path:");
		containerText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		
		containerText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL) );
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(parent, SWT.PUSH);
		button.setText("  Browse...  ");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
	}
	
	/**
	 * Creates the test case name controls
	 * @param parent The parent composite
	 */
	public void createNameControls( Composite parent ) {
		Label label = new Label(parent, SWT.NULL);
		label.setText("Name:");

		fileText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		fileText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL) );
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(parent, SWT.NULL);
		label.setText("");		
	}
	
	/**
	 * Creates the superclass controls
	 * @param parent The parent composite
	 */
	public void createSuperclassControls( Composite parent ) {
		Label label = new Label(parent, SWT.NULL);
		label.setText("Framework Location:");
		
		frameworkExtendsPath = new Text(parent, SWT.BORDER | SWT.SINGLE);
		frameworkExtendsPath.setLayoutData( new GridData(GridData.FILL_HORIZONTAL) );
		frameworkExtendsPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		frameworkExtendsPath.setText("net.sourceforge.cfunit.framework");

		Button button2 = new Button(parent, SWT.PUSH);
		button2.setText("  Browse...  ");
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleCFUnitBrowse();
			}
		});		
	}

	/**
	 * Creates the framework controls
	 * @param parent The parent composite
	 */
	public void createFrameworkControls( Composite parent ) {
		GridData gd;
		
		// Create canvas to contain out radio buttons
		Canvas panel = new Canvas(parent, SWT.NONE);
		gd = new GridData ( GridData.FILL_HORIZONTAL );
		gd.horizontalSpan = GRID_WIDTH;
		panel.setLayoutData(gd);
		panel.setLayout(new GridLayout(3, true));
		
		// Add all our radio controls
		gd = new GridData();
		gd.horizontalIndent = 5;
		
		fwCFUnit = new Button(panel, SWT.RADIO);
		fwCFUnit.setText("New CFUnit Test");
		fwCFUnit.setLayoutData(gd);
		fwCFUnit.setSelection( true );
		fwCFUnit.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
				frameworkChanged(TestFrameworkType.CFUNIT);
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		fwCfcUnit = new Button(panel, SWT.RADIO);
		fwCfcUnit.setText("New cfcUnit Test");
		fwCfcUnit.setLayoutData(gd);
		fwCfcUnit.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				frameworkChanged(TestFrameworkType.CFCUNIT);
				dialogChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		fwMxUnit = new Button(panel, SWT.RADIO);
		fwMxUnit.setText("New MXUnit Test");
		fwMxUnit.setLayoutData(gd);
		fwMxUnit.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				frameworkChanged(TestFrameworkType.MXUNIT);
				dialogChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		
	}
	
	/**
	 * 
	 * @param parent The parent composite
	 */
	public void createMethodStubsControls( Composite parent ) {
		GridData gd;
		
		// Add title
		Label text = new Label(parent, SWT.NONE);
		text.setText("Which method stubs would you like to create?");
		gd = new GridData ( GridData.FILL_HORIZONTAL );
		gd.horizontalSpan = GRID_WIDTH;
		text.setLayoutData(gd);
		
		// Create canvas to contain out checkboxes
		Canvas panel = new Canvas(parent, SWT.NONE);
		gd = new GridData ( GridData.FILL_HORIZONTAL );
		gd.horizontalSpan = GRID_WIDTH;
		panel.setLayoutData(gd);
		panel.setLayout(new GridLayout(3, true));
		
		// Add all our checkbox controls
		gd = new GridData();
		gd.horizontalIndent = 5;
		
		propertySetupStub = new Button(panel, SWT.CHECK);
		propertySetupStub.setText("setUp()");
		propertySetupStub.setLayoutData(gd);
		
		propertyTeardownStub = new Button(panel, SWT.CHECK);
		propertyTeardownStub.setText("tearDown()");
		propertyTeardownStub.setLayoutData(gd);
		
		propertyOutputStub = new Button(panel, SWT.CHECK);
		propertyOutputStub.setText("Template Output");
		propertyOutputStub.setLayoutData(gd);
		propertyOutputStub.setEnabled( false );
	}
	
	/**
	 * Creates the test runner controls
	 * @param parent The parent composite
	 */
	public void createRunnerControls( Composite parent ) {
		Label text = new Label(parent, SWT.NONE);
		text.setText("Do you want to create a test runner file to execute tests in this location?");
		GridData gd = new GridData ( GridData.FILL_HORIZONTAL );
		//gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = GRID_WIDTH;
		text.setLayoutData(gd);
		
		propertyCreateRunner = new Button(parent, SWT.CHECK);
		propertyCreateRunner.setText("Create Runner");
		gd = new GridData();
		gd.horizontalIndent = 5;
		propertyCreateRunner.setLayoutData(gd);
		
		propertyCreateRunner.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				setRunnerEnabled(propertyCreateRunner.getSelection());
			}
			public void widgetDefaultSelected(SelectionEvent e) {;}
		});
		
		runnerText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		runnerText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL) );
		runnerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		runnerText.setText("index.cfm");
		runnerText.setEnabled( false );
	}
	
	/**
	 * Creates a separator
	 * @param parent The parent composite
	 */
	public void createSeparator( Composite parent ) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.LINE_SOLID);
		GridData gd = new GridData ( GridData.FILL_HORIZONTAL );
		//gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = GRID_WIDTH;
		separator.setLayoutData(gd);		
	}
	
	/**
	 * Creates the class under test controls
	 * @param parent The parent composite
	 */
	public void createClassUnderTestControls( Composite parent ) {
		Label label = new Label(parent, SWT.NULL);
		label.setText("File Under Test:");
		
		futLocation = new Text(parent, SWT.BORDER | SWT.SINGLE);
		futLocation.setLayoutData( new GridData(GridData.FILL_HORIZONTAL) );
		futLocation.setEnabled( false );
		futLocation.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Button button = new Button(parent, SWT.PUSH);
		button.setText("  Browse...  ");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse();
			}
		});		
	}

	
	/**
	 * Enables/disables the create runner field
	 * @param b
	 */
	public void setRunnerEnabled(boolean b){
		runnerText.setEnabled( b );
	}

	/**
	 * This works if you do a right click but not if you come here from a select your page type page
	 * Mark D: I added this as I am trying to make it focus on the right item when you open the dialog
	 */
	private void setFilenameFocus(){
		fileText.setFocus();
		System.out.println("setting the focus");
		int textLen = fileText.getText().length() - 4;
		fileText.setSelection(0,textLen);
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
				containerText.setText(container.getFullPath().toString());
			}
		} else if (this.editor != null){
		    if (editor.getEditorInput() instanceof FileEditorInput) {
		        FileEditorInput input = (FileEditorInput)editor.getEditorInput();
		        containerText.setText(input.getFile().getParent().getFullPath().toString());
		    }
		}

		fileText.setText("TestUntitled.cfc");
		setFilenameFocus();
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
				"Select CFUnit Location");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path)result[0]).toOSString());
			}
		}
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */
	private void handleCFUnitBrowse() {
		ContainerSelectionDialog dialog =
			new ContainerSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				
				// Convert the path to a dot-nation location which can be used by ColdFusion
				String path = ((Path)result[0]).toString().replace('/', '.');
					
				if( path.startsWith(".") ) {
					path = path.substring(1);					
				}
				
				frameworkExtendsPath.setText( path );
			}
		}
	}
	

	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the file under test field.
	 */
	private void handleFileBrowse() {
		ResourceListSelectionDialog listSelection = null;
		
		try {
			listSelection = new ResourceListSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), 
				IResource.FILE
			);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(listSelection.open() == ResourceListSelectionDialog.OK) {
			Object[] result = listSelection.getResult();
			
			if(result.length == 1) {
				futResource = (IResource)result[0];
				futLocation.setText( futResource.getFullPath().toString() );
				futLocation.setEnabled( true );
				setPageComplete( true );
				
				// Add method selection page
				NewCFUnitWizard myWizard = (NewCFUnitWizard)getWizard();
				myWizard.addMethodsPage();
				setPageComplete(true);
			}
		}
	}
	
	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		String container = getContainerName();
		String fileName = getFileName();
		
		if (container.length() == 0) {
			updateStatus("File container must be specified");
			return;
		}

		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		
		if (  !fileName.toLowerCase().endsWith(".cfc")    ) {
			updateStatus("The file extension for test case must be \"cfc\"");
			return;
		}
		
		if(  !isSafeTestMethodName(fileName)  ) {
			setMessage("It is recommended that a test case name begin or end with the phrase \"Test\" – this will ensure it is found by automated test scripts", 2);
			return;
		}
		
		if(isCFUnitFamework()) {
			IResource fut = getFutResource();
			
			if(fut != null) {
				String ext = fut.getFileExtension();
				if(ext.equals("cfm")) {
					propertyOutputStub.setEnabled( true );
				} else {
					propertyOutputStub.setEnabled( false );
				}
			}
		} else {
			propertyOutputStub.setEnabled( false );
		}
		
		setMessage(null);
		updateStatus(null);
	}

	private boolean isSafeTestMethodName(String fileName) {		
		return fileName.matches( "(?i)Test.*.cfc|(?i).*Test.cfc" );
	}
	
	/**
	 * Handles a change in the framework
	 * 
	 * @param f The new framework name
	 */
	private void frameworkChanged(TestFrameworkType type) {
		if(type==TestFrameworkType.CFCUNIT){
			frameworkExtendsPath.setText("org.cfcunit.framework");
		}else if(type==TestFrameworkType.MXUNIT){
			frameworkExtendsPath.setText("mxunit.framework");
		}else{
			frameworkExtendsPath.setText("net.sourceforge.cfunit.framework");
		}
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}

	public String getFrameworkExtendsPath() {
		return frameworkExtendsPath.getText();
	}

	public String getRunner() {
		return runnerText.getText();
	}

	public boolean isCreateRunner() {
		return propertyCreateRunner.getSelection();
	}

	public boolean isCreateSetupStub() {
		return propertySetupStub.getSelection();
	}

	public boolean isCreateTeardownStub() {
		return propertyTeardownStub.getSelection();
	}

	public boolean isCreateOutputStub() {
		if(propertyOutputStub.isEnabled()) {
			return propertyOutputStub.getSelection();
		} else {
			return false;
		}
	}

	public IResource getFutResource() {
		return futResource;
	}

	/**
	 * Returns <code>true</code> if the CFUnit framework radio is selected,
	 * and false otherwise.
	 *
	 * @return the selection state
	 */
	public boolean isCFUnitFamework() {
		return fwCFUnit.getSelection();
	}
	/**
	 * Returns <code>true</code> if the cfcUnit framework radio is selected,
	 * and false otherwise.
	 *
	 * @return the selection state
	 */
	public boolean isCfcUnitFamework() {
		return fwCfcUnit.getSelection();
	}
	
	/**
	 * Returns <code>true</code> if the MXUnit framework radio is selected,
	 * and false otherwise.
	 *
	 * @return the selection state
	 */
	public boolean isMXUnitFamework() {
		return fwMxUnit.getSelection();
	}
}
