package org.cfeclipse.cfml.cfunit.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Create the preferences page used to configure CFUnit specific preferences.
 * @author Robert Blackburn
 */
public class CFUnitPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	/**
	 * Main constructor method
	 */
	public CFUnitPreferencesPage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("You can set all the preferences related to CFUnit on this page.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors()
	{
    	Group FacadeComposite = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);        
    	
    	GridLayout layout = new GridLayout();
    	layout.numColumns = 2;
    	layout.makeColumnsEqualWidth = false;
        FacadeComposite.setLayout( layout );
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        FacadeComposite.setLayoutData(gridData);
        FacadeComposite.setText("Façade URL");
		
		addField(new StringFieldEditor("CFUnitFacadeLocation", "http://", FacadeComposite));
		
		Label l1 = new Label(FacadeComposite, SWT.NONE);
		l1.setText("");
		
		Label l2 = new Label(FacadeComposite, SWT.NONE);
		l2.setText("Example: localhost:8500/net/sourceforge/cfunit/framework");
		
		Label l3 = new Label(FacadeComposite, SWT.NONE);
		l3.setText("");
		
		Label l4 = new Label(FacadeComposite, SWT.NONE);
		l4.setText(" \nIn order for the CFUnit view to execute a CFUnit by its name, it needs\nto have access to a CFUnit façade. Though this is not required to use\nthe CFUnit view, it can be very helpful.\n\nTo do this you need to copy the CFEclipseFacade.cfc provided with the\nCFUnit framework (downloadable at http://cfunit.sf.net) to your web\nserver; then update this preference page with its URL. For example:\nhttp://localhost:8500/net/sourceforge/cfunit/framework\n ");
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench){;}
}
