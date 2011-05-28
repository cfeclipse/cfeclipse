package org.cfeclipse.frameworks.fusebox.properties;

import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * @author Administrator
 * 10-Jan-2005
 * fusebox3cfe2
 * Description: This is the property page for the defaults for this project
 */
public class FuseboxPropertyPage extends PropertyPage {

	private static final String PROJECT_TITLE = "Project:";
	private static final String PATH_TITLE = "&Fusebox Root Path:";
	//Fields
	
	private static final String PATH_PROPERTY = FuseboxPreferenceConstants.P_FUSEBOX_PATH;
	private static String DEFAULT_PATH = "/";

	private static final String VERSION_TITLE = "&Fusebox Version";
	private static final String VERSION_PROPERTY = "FBXVERSION";
	private static final String VERSION_DEFAULT = "org.cfeclipse.frameworks.fusebox3.parser.FBX3parser";
	
	
	//This is now automatic, we shall add any more settings later if required
	/*
	 * A word about project preferences: I would prefer if this plugin did things as 
	 * "automagically" as possible so that it doesnt need any configuration and we dont
	 * run into any mis-configuration issues
	 * 
	 */
	private static final String FBX3CIRCUIT_FILE_TITLE = "&Fusebox 3 Circuit File";
	private static final String FBX3CIRCUIT_FILE_PROPERTY = "FBXCIRCUITFILE";
	private static final String FBX3CIRCUIT_FILE_DEFAULT = "fbx_Circuits.cfm";
	
	private static final String FBX3SWITCH_FILE_TITLE = "&Fusebox 3 Switch File";
	private static final String FBX3SWITCH_FILE_PROPERTY = "FBXSWITCHFILE";
	private static final String FBX3SWITCH_FILE_DEFAULT = "fbx_Switch.cfm";
	
	
	
	
	
	private static final int TEXT_FIELD_WIDTH = 50;

	private Text ownerText;
	private Text pathText;
	
	//The Fusebox root folder (relative to w/space
	private StringFieldEditor fuseboxRootField;
	
	private RadioGroupFieldEditor fbxVersionField;
	private Text circuitFileName;
	private Text switchFileName;
	private Combo verSelect;
	private FuseboxPropertyManager propertyManager;
	
	private FuseboxPropertyStore theStore;

	
	/**
	 * Constructor for FuseboxPropertyPage.
	 */
	public FuseboxPropertyPage() {
		super();
		propertyManager = new FuseboxPropertyManager();
		this.theStore = new FuseboxPropertyStore();
		
		//DEFAULT_PATH = propertyManager.defaultFuseboxPath();
	
	}

	private void addProjectNameSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		//Label for path field
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(PROJECT_TITLE);

		// Path text field
		Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
		pathValueText.setText(((IResource) getElement()).getFullPath().toString());
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addFBXRootSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		
		//fusebox root field
		fuseboxRootField = new StringFieldEditor("fuseboxpath", PATH_TITLE, composite);
		
		try{
			QualifiedName propertyName = new QualifiedName("", PATH_PROPERTY);
			String rootPath  = ((IResource) getElement()).getPersistentProperty(propertyName);
			if(rootPath == null || rootPath == ""){
				rootPath = DEFAULT_PATH;
			}
			fuseboxRootField.setStringValue(rootPath);
		} catch(CoreException e){
			fuseboxRootField.setStringValue(DEFAULT_PATH);
		}
		
	}
	private void addFBXVersionSection(Composite parent)
	{
	    Composite composite = createDefaultComposite(parent);
	    String [][] options = new String[][] {
	            {"Fusebox 3", FuseboxPropertyStore.P_FBX_VERSION_DEFAULT},
	            {"Fusebox 4", "org.cfeclipse.frameworks.fusebox4.parser.FBX4parser"}
	    };
	    
	    this.fbxVersionField = new RadioGroupFieldEditor(
	    		FuseboxPropertyStore.P_FBX_VERSION, 
	            VERSION_TITLE, 
	            1,
	            options, 
	            composite,
	            true
	    );
	    this.fbxVersionField.setPreferencePage(this);
	    this.fbxVersionField.setPreferenceStore(this.theStore);
	    this.fbxVersionField.load();
	   
	    
	}
	
	private void addFBX3CircuitDefaults(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		// Label for path field
		Label circuitLabel = new Label(composite, SWT.NONE);
		circuitLabel.setText(FBX3CIRCUIT_FILE_TITLE);

		// path text field
		circuitFileName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		circuitFileName.setLayoutData(gd);

		// Populate path text field
		try {
			String circFileName =
				((IResource) getElement()).getPersistentProperty(
					new QualifiedName("", FBX3CIRCUIT_FILE_PROPERTY));
			circuitFileName.setText((circFileName != null) ? circFileName : FBX3CIRCUIT_FILE_DEFAULT);
		} catch (CoreException e) {
			ownerText.setText(FBX3CIRCUIT_FILE_DEFAULT);
		}
	}
	private void addFBX3SwitchDefaults(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		// Label for path field
		Label switchLabel = new Label(composite, SWT.NONE);
		switchLabel.setText(FBX3SWITCH_FILE_TITLE);

		// path text field
		switchFileName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		switchFileName.setLayoutData(gd);

		// Populate path text field
		try {
			String swFileName =
				((IResource) getElement()).getPersistentProperty(
					new QualifiedName("", FBX3SWITCH_FILE_PROPERTY));
			switchFileName.setText((swFileName != null) ? swFileName : FBX3SWITCH_FILE_DEFAULT);
		} catch (CoreException e) {
			switchFileName.setText(FBX3SWITCH_FILE_DEFAULT);
		}
	}
	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addProjectNameSection(composite);
		addSeparator(composite);
		addFBXRootSection(composite);
		//Removed as it is now done automagically:addFBXVersionSection(composite);
		
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		// Populate the fields with the the default value
		pathText.setText(DEFAULT_PATH);
		//circuitFileName.setText(FBX3CIRCUIT_FILE_DEFAULT);
		//switchFileName.setText(FBX3SWITCH_FILE_DEFAULT);
		
		
		
		
	}
	
	public boolean performOk() {
		// store the value in the owner text field
		try {
		
			((IResource) getElement()).setPersistentProperty(
					new QualifiedName("", PATH_PROPERTY),
					fuseboxRootField.getStringValue());
			propertyManager.setFuseboxPath(fuseboxRootField.getStringValue());
		} catch (CoreException e) {
			return false;
		}
			
		this.fbxVersionField.store();
			
		Utils.println("hello");
		
		return true;
	}

}