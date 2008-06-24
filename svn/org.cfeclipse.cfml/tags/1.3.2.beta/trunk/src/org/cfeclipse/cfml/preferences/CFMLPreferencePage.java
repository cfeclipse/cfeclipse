/*
 * Created on Apr 1, 2004
 * 
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.preferences;

/**
 * @author Stephen Milligan
 *
 * This generates the UI for the preferences page. When the preferences are updated CFConfiguration is notified
 * via the propertyChange() method. Anything else that wants to be notified needs to implement IPropertyChangeListener
 */
import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */
public class CFMLPreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage, SelectionListener  {


	Button tabbedBrowserCheckBox;
	CFMLPreferenceManager preferenceManager;
	DirectoryFieldEditor snippetsPathField;
	/** combo field full off projects? */
	Combo templateProjectsPathField;
	
	public CFMLPreferencePage() {
		super();
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("CFEclipse preferences");
		preferenceManager = new CFMLPreferenceManager();
	}
	

	public void createControl(Composite parent) {
        super.createControl(parent);
    }
	
	private Composite createContainer(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        //GridLayout layout = new GridLayout();
        //layout.numColumns = 2;
        //layout.makeColumnsEqualWidth = false;
        //composite.setLayout(layout);
        GridData gridData =
            new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
        composite.setLayoutData(gridData);
        return composite;
    }
	
	
    protected Control createContents(Composite parent) {
    	// The container for the preference page
        Composite composite = createContainer(parent);       
        
        // The layout info for the preference page
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        composite.setLayout(gridLayout);
        
        // A panel for the preference page
        Composite defPanel = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        defPanel.setLayout(layout);
        GridData gridData =
            new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
        defPanel.setLayoutData(gridData);
                

        // Browser options
        createBrowserGroup(defPanel);
        
        
        // File paths
        createFilePathGroup(defPanel);
        
        // Template Sites
        
       // createTemplateSitesPathGroup(defPanel);
        return composite;
    }

    public void widgetDefaultSelected(SelectionEvent selectionEvent) {
        widgetSelected(selectionEvent);
    }
    
    
    public void widgetSelected(SelectionEvent selectionEvent) {}

    


    
    private void createBrowserGroup(Composite parent) {
    	Group BrowserComposite = new Group(parent, SWT.SHADOW_ETCHED_IN); 
        GridLayout layout = new GridLayout();        
        layout.numColumns = 3;              
        BrowserComposite.setLayout(layout);
        GridData gridData =
            new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 3;
        BrowserComposite.setLayoutData(gridData);        
        
        
        BrowserComposite.setText("Web Browser"); //$NON-NLS-1$
        
        // Tabbed browsing
        tabbedBrowserCheckBox = createLabeledCheck(
                "Used tabbed browsing", //$NON-NLS-1$
                preferenceManager.tabbedBrowser(), 
				BrowserComposite);
                   

    }

    private void createTemplateSitesPathGroup(Composite parent){
    	Group TemplateProjectsComposite = new Group(parent, SWT.SHADOW_ETCHED_IN);
    	GridLayout layout = new GridLayout();
    	layout.numColumns = 3;
    	TemplateProjectsComposite.setLayout(layout);
    	
    	GridData gridData = new GridData();

        gridData.horizontalSpan = 3;
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        gridData.widthHint = 250;
        TemplateProjectsComposite.setLayoutData(gridData); 
        
        TemplateProjectsComposite.setText("Template Projects Located in:"); //$NON-NLS-1$
        
        
        /** go and get the projects that are available, including a blank one */
        
        
        templateProjectsPathField = new Combo(TemplateProjectsComposite, 10);
      
        templateProjectsPathField.add("");
        
       IProject[] projects = getProjects();
       
       int selectedItem = 0;
       
       //we could loop over this couldnt we?
       for(int proj = 0; proj <projects.length; proj++){
    	   IProject project = projects[proj];
    	   templateProjectsPathField.add(project.getFullPath().toString());
    	   if(project.getFullPath().toString().equals(preferenceManager.templateProjectsPath())){
    		   selectedItem = proj + 1;
    	   }
       }
       
       //We need to find what has been selected in the combo box, getting the preference.
       templateProjectsPathField.select(selectedItem);
       
      
       
        
        //then set the value up 
       // templateProjectsPathField.setStringValue(preferenceManager.snippetsPath());
    }


    private IProject[] getProjects(){
    	IWorkspace workspace = CFMLPlugin.getWorkspace();
    	IProject[] projects = workspace.getRoot().getProjects();
    	return projects;
    }
    
    private void createFilePathGroup(Composite parent) {
    	Group FilePathComposite = new Group(parent, SWT.SHADOW_ETCHED_IN); 
        GridLayout layout = new GridLayout();        
        layout.numColumns = 3;              
        FilePathComposite.setLayout(layout);
        //GridData gridData =
        //    new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        GridData gridData = new GridData();

        gridData.horizontalSpan = 3;

        gridData.grabExcessHorizontalSpace = true;

        gridData.horizontalAlignment = GridData.FILL;

        // default width is 'client screen width/2'
        //int screenwidth = parent.getDisplay().getBounds().width;

        gridData.widthHint = 250;

        // [End] Rudi R.P. 
        
        gridData.horizontalSpan = 3;
        FilePathComposite.setLayoutData(gridData);        
        
        
        FilePathComposite.setText("File Paths"); //$NON-NLS-1$
        
        // Snippets
        snippetsPathField = new DirectoryFieldEditor("", "Path to snippets directory", FilePathComposite);
        snippetsPathField.setStringValue(preferenceManager.snippetsPath());

    }
    
    /* private Text createLabeledText(String labelText, String value, Composite defPanel) {
        GridData gridData;
        Label label = new Label(defPanel, SWT.WRAP);
        gridData = new GridData();
        label.setLayoutData(gridData);
        label.setText(labelText);

        Text fText = new Text(defPanel, SWT.SHADOW_IN | SWT.BORDER);
        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 2;
        fText.setLayoutData(gridData);
        fText.setText(value);
        fText.setToolTipText(labelText);
        return fText;
    } */
    
    
    /* private Text createLabeledInt(String labelText, int value, Composite defPanel) {
        GridData gridData;
        Label label = new Label(defPanel, SWT.WRAP);
        gridData = new GridData();
        label.setLayoutData(gridData);
        label.setText(labelText);

        Text fText = new Text(defPanel, SWT.SHADOW_IN | SWT.BORDER);
        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 2;
        fText.setLayoutData(gridData);
        fText.setText(Integer.toString(value));
        fText.setToolTipText(labelText);
        return fText;
    } */

    private Button createLabeledCheck(String labelText, boolean value, Composite defPanel) {
        GridData gridData;
        Label label = new Label(defPanel, SWT.WRAP);
        gridData = new GridData();
        label.setLayoutData(gridData);
        label.setText(labelText);

        Button fButton = new Button(defPanel, SWT.CHECK);
        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 2;
        fButton.setLayoutData(gridData);
        fButton.setSelection(value);
        fButton.setToolTipText(labelText);
        return fButton;
    }


/**
 * Sets the default values of the preferences.
 */
    protected void performDefaults() {
        super.performDefaults();
        tabbedBrowserCheckBox.setSelection(preferenceManager.defaultTabbedBrowser());
        snippetsPathField.setStringValue(preferenceManager.defaultSnippetsPath());
        templateProjectsPathField.select(0);
    }


    public boolean performOk() {
        IPreferenceStore store = getPreferenceStore();
        store.setValue(CFMLPreferenceConstants.P_TABBED_BROWSER, String.valueOf(tabbedBrowserCheckBox.getSelection()));
        store.setValue(CFMLPreferenceConstants.P_SNIPPETS_PATH, snippetsPathField.getStringValue());
        
        //Since from a combo we can only get the selection index, lets get the item from the array (plus one for the blank filled one)
        IProject[] projects = getProjects();
        String templateProject = "";
       // if(templateProjectsPathField.getSelectionIndex() > 0){
       // 	templateProject = projects[templateProjectsPathField.getSelectionIndex()-1].getFullPath().toString();
        //}
       // store.setValue(CFMLPreferenceConstants.P_TEMPLATE_PROJECT_PATH, templateProject);
        
        return true;
    }

	public void init(IWorkbench workbench) {
	}
}