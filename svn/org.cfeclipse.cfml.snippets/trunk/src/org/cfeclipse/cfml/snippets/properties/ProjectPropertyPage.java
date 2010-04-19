package org.cfeclipse.cfml.snippets.properties;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;


public class ProjectPropertyPage extends PropertyPage {
    
	private static final String PATH_TITLE = "Path:";
	//private static final String SNIPPETS_PATH_TITLE = "&Snippets Path:";
	private static final String SNIPPETS_PATH_PROPERTY = "snippetsPath";
	private static String DEFAULT_SNIPPETS_PATH = "";
	
	private DirectoryFieldEditor snippetsPathField;
	private CFMLPropertyManager propertyManager;

	private ProjectPropertyStore propStore;
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public ProjectPropertyPage() {
		super();
		propertyManager = SnippetPlugin.getDefault().getPropertyManager();
		
		this.propStore = new ProjectPropertyStore();
		DEFAULT_SNIPPETS_PATH = propertyManager.defaultSnippetsPath();
	}

	public void setElement(IAdaptable element) 
	{
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
		//addURLSection(composite);
		//addComponentRootSection(composite);
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

	protected void performDefaults() 
	{
		// Populate the owner text field with the default value
		snippetsPathField.setStringValue(DEFAULT_SNIPPETS_PATH);
	}
	
	public boolean performOk() {
		// Snippets path
		try 
		{
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", SNIPPETS_PATH_PROPERTY),
				snippetsPathField.getStringValue()
			);
			propertyManager.setSnippetsPath(snippetsPathField.getStringValue(),(IProject)getElement());
		} 
		catch (CoreException e) 
		{
			return false;
		}
		return true;
	}

}