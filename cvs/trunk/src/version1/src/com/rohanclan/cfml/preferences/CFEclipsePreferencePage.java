/*
 * Created on Apr 1, 2004
 *
 */
package com.rohanclan.cfml.preferences;

/**
 * @author Stephen Milligan
 *
 * This generates the UI for the preferences page. When the preferences are updated CFConfiguration is notified
 * via the propertyChange() method. Anything else that wants to be notified needs to implement IPropertyChangeListener
 * 
 * TODO: Need to add a bunch more preferences to the system
 */
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
//import org.eclipse.jface.dialogs.Dialog;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.ICFMLPluginConstants;

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


public class CFEclipsePreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage, SelectionListener  {

	private static final String DEFAULT_INSIGHT_DELAY = "500";
	private static final String DEFAULT_TAB_WIDTH = "4";
	private static final String DEFAULT_TABS_AS_SPACES = "false";
	
	Text insightDelay;
	Text tabWidth;
	Button tabsAsSpaces;

	
	
	public CFEclipsePreferencePage() {
		super();
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("CFEclipse preferences");
	}
	

	public void createControl(Composite parent) {
        super.createControl(parent);
    }
	
	private Composite createContainer(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = false;
        composite.setLayout(layout);
        GridData gridData =
            new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
        composite.setLayoutData(gridData);
        return composite;
    }
	
	
    protected Control createContents(Composite parent) {
        Composite composite = createContainer(parent);       
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        composite.setLayout(gridLayout);

        Composite defPanel = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        defPanel.setLayout(layout);
        GridData gridData =
            new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
        defPanel.setLayoutData(gridData);

        Label label = new Label(defPanel, SWT.WRAP);
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        label.setLayoutData(gridData);
        label.setText("Editor"); //$NON-NLS-1$
        
        // Tag and function insight options

        Group defaultComposite = new Group(defPanel, SWT.SHADOW_ETCHED_IN); 
        layout = new GridLayout();        
        layout.numColumns = 3;     
        defaultComposite.setLayout(layout);
        gridData =
            new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 3;
        defaultComposite.setLayoutData(gridData);        
        
        
        defaultComposite.setText("Tag and function insight"); //$NON-NLS-1$

        insightDelay = createLabeledText(
            "Insight Delay (ms)", //$NON-NLS-1$
            getPreferenceStore().getString(ICFMLPluginConstants.P_INSIGHT_DELAY), 
            defaultComposite);
        insightDelay.setTextLimit(5);
        insightDelay.setBounds(insightDelay.getSize().x,insightDelay.getSize().y,20,10);
                   

        
        insightDelay.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                String number = ((Text) e.widget).getText();
                number = number == null? number : number.trim();
                try {
                    int value = Integer.parseInt(number);
                    if (value <= 0) {
                        setValid(false);
                        setErrorMessage("The insight delay must be an integer");
                    }
                    else {
                    	setValid(true);
                    	setErrorMessage(null);
                    }
                } catch (NumberFormatException ex) {
                	setValid(false);
                    setErrorMessage("The insight delay must be an integer");
                }
            }
        });
        
        
        
        // Tabs and spaces options

        Group tabsComposite = new Group(defPanel, SWT.SHADOW_ETCHED_IN); 
        layout = new GridLayout();        
        layout.numColumns = 3;              
        tabsComposite.setLayout(layout);
        gridData =
            new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 3;
        tabsComposite.setLayoutData(gridData);        
        
        
        tabsComposite.setText("Tabs and spaces"); //$NON-NLS-1$
        
        // Tab width
        tabWidth = createLabeledText(
            "Tab width (characters)", //$NON-NLS-1$
            getPreferenceStore().getString(ICFMLPluginConstants.P_TAB_WIDTH), 
			tabsComposite);
        tabWidth.setTextLimit(3);
                   

        
        tabWidth.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                String number = ((Text) e.widget).getText();
                number = number == null? number : number.trim();
                try {
                    int value = Integer.parseInt(number);
                    if (value <= 0) {
                        setValid(false);
                        setErrorMessage("The tab width must be an integer");
                    }
                    else {
                    	setValid(true);
                    	setErrorMessage(null);
                    }
                } catch (NumberFormatException ex) {
                    setValid(false);
                    setErrorMessage("The tab width must be an integer");
                }
            }
        });
        
        // Tabs as spaces
        
        boolean checkBoxValue = getPreferenceStore().getString(ICFMLPluginConstants.P_TABS_AS_SPACES).trim().equalsIgnoreCase("true");
        tabsAsSpaces = createLabeledCheck(
            "Insert spaces instead of tabs", //$NON-NLS-1$
            checkBoxValue, 
			tabsComposite);
        
        return composite;
    }

    public void widgetDefaultSelected(SelectionEvent selectionEvent) {
        widgetSelected(selectionEvent);
    }
    
    
    public void widgetSelected(SelectionEvent selectionEvent) {}

    public boolean performOk() {
        IPreferenceStore store = getPreferenceStore();
        store.setValue(ICFMLPluginConstants.P_INSIGHT_DELAY, insightDelay.getText());
        store.setValue(ICFMLPluginConstants.P_TAB_WIDTH, tabWidth.getText());
        store.setValue(ICFMLPluginConstants.P_TABS_AS_SPACES, String.valueOf(tabsAsSpaces.getSelection()));
        return true;
    }
    
    
    private Text createLabeledText(String labelText, String value, Composite defPanel) {
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
    }

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
        insightDelay.setText(DEFAULT_INSIGHT_DELAY);
        tabWidth.setText(DEFAULT_TAB_WIDTH);
        tabsAsSpaces.setSelection(DEFAULT_TABS_AS_SPACES.trim().equalsIgnoreCase("true"));
    }
	

	public void init(IWorkbench workbench) {
	}
}