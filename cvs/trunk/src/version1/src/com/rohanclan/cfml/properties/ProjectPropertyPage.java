package com.rohanclan.cfml.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

import com.rohanclan.cfml.preferences.CFMLPreferenceConstants;

public class ProjectPropertyPage extends PropertyPage {
    
	private static final String PATH_TITLE = "Path:";
	//private static final String SNIPPETS_PATH_TITLE = "&Snippets Path:";
	private static final String SNIPPETS_PATH_PROPERTY = "snippetsPath";
	private static String DEFAULT_SNIPPETS_PATH = "";
	private static final String PROJECT_URL_TITLE = "Project URL:";
	private static final String PROJECT_URL_PROPERTY = CFMLPreferenceConstants.P_PROJECT_URL;
	private static final String DEFAULT_PROJECT_URL = "http://livedocs.macromedia.com";
	
	private static final String CFML_DICTIONARY_TITLE = "&CFML Language Version";

	//private static final int TEXT_FIELD_WIDTH = 50;

	
	private DirectoryFieldEditor snippetsPathField;
	private StringFieldEditor projectURLField;
	private CFMLPropertyManager propertyManager;
	private RadioGroupFieldEditor cfmlSyntaxField;

	private ProjectPropertyStore propStore;
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public ProjectPropertyPage() {
		super();
		propertyManager = new CFMLPropertyManager();
		
		this.propStore = new ProjectPropertyStore();
		DEFAULT_SNIPPETS_PATH = propertyManager.defaultSnippetsPath();
	}

	public void setElement(IAdaptable element) {
        super.setElement(element);
        IProject project = (IProject)getElement();
        this.propStore.setProject(project);
    }
	
	private void addPathSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		//Label for path field
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(PATH_TITLE);

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

	private void addSnippetsSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);
		snippetsPathField = new DirectoryFieldEditor("", "Path to snippets directory", composite);
        snippetsPathField.setStringValue(SNIPPETS_PATH_PROPERTY);
        
		
		try {
			String snippetsPath =
				((IResource) getElement()).getPersistentProperty(
					new QualifiedName("", SNIPPETS_PATH_PROPERTY));
			snippetsPathField.setStringValue((snippetsPath != null) ? snippetsPath : DEFAULT_SNIPPETS_PATH);
		} catch (CoreException e) {
			snippetsPathField.setStringValue(DEFAULT_SNIPPETS_PATH);
		}
	}

	

	private void addURLSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);


		// Project URL field
		projectURLField = new StringFieldEditor("projectURL",PROJECT_URL_TITLE,composite);
		try {
			QualifiedName propertyName = new QualifiedName("", PROJECT_URL_PROPERTY);
			String projectURL = ((IResource) getElement()).getPersistentProperty(propertyName);
			if (projectURL == null || projectURL == "") {
				projectURL = DEFAULT_PROJECT_URL;
			}
			projectURLField.setStringValue(projectURL);
		} catch (CoreException e) {
			projectURLField.setStringValue(DEFAULT_PROJECT_URL);
		}
	}

	private void addCFMLSyntaxSection(Composite parent)
	{
	    Composite composite = createDefaultComposite(parent);
	    String [][] options = new String[][] {
	            {"CFML 6.0 (CFMX)", ProjectPropertyStore.P_CFML_DICTIONARY_DEFAULT},
	            {"CFML 5.0", "cfml-5.0.xml"}
	    };
	    
	    this.cfmlSyntaxField = new RadioGroupFieldEditor(
	            ProjectPropertyStore.P_CFML_DICTIONARY, 
	            CFML_DICTIONARY_TITLE, 
	            1,
	            options, 
	            composite,
	            true
	    );
	    this.cfmlSyntaxField.setPreferencePage(this);
	    this.cfmlSyntaxField.setPreferenceStore(this.propStore/*this.getPreferenceStore()*/);
	    this.cfmlSyntaxField.load();
	    
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

		addPathSection(composite);
		addSeparator(composite);
		addSnippetsSection(composite);
		addURLSection(composite);
		addCFMLSyntaxSection(composite);
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
		// Populate the owner text field with the default value
		snippetsPathField.setStringValue(DEFAULT_SNIPPETS_PATH);
		projectURLField.setStringValue(DEFAULT_PROJECT_URL);
		this.cfmlSyntaxField.loadDefault();
	}
	
	public boolean performOk() {
		// Snippets path
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", SNIPPETS_PATH_PROPERTY),
				snippetsPathField.getStringValue());
			propertyManager.setSnippetsPath(snippetsPathField.getStringValue());
		} catch (CoreException e) {
			return false;
		}
		// Project URL
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", PROJECT_URL_PROPERTY),
				projectURLField.getStringValue());
			propertyManager.setProjectURL(projectURLField.getStringValue());
		} catch (CoreException e) {
			//e.printStackTrace(System.err);
			return false;
		}
		
		this.cfmlSyntaxField.store();

		return true;
	}

}