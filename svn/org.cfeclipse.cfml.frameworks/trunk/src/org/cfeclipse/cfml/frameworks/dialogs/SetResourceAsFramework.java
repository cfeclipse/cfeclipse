package org.cfeclipse.cfml.frameworks.dialogs;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.FrameworkType;
import org.cfeclipse.cfml.frameworks.FrameworkManager;
import org.cfeclipse.cfml.frameworks.popup.actions.SetFrameworkFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SetResourceAsFramework extends Dialog {
	
	private IResource resourceToSet;
	public Combo comboFrameworks;
	private String selectedFramework;
	private static String REMOVE_ITEM = ">>Remove";
	
	private Log logger = LogFactory.getLog(SetResourceAsFramework.class);
	
	public SetResourceAsFramework(Shell parentShell, IResource resourceToSet) {
		super(parentShell);
		this.resourceToSet = resourceToSet;
	}
	
	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 2;
		
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;
		Label resourceLabel = new Label(container, SWT.NONE);
		resourceLabel.setLayoutData(labelData);
		resourceLabel.setText("Set Framework type for Resource: " + resourceToSet.getName());
		
		
		Label frameworkLabel = new Label(container, SWT.NONE);
		frameworkLabel.setText("Framework:");
		//Create the combo
		comboFrameworks = new Combo(container, SWT.READ_ONLY);
		comboFrameworks.add(REMOVE_ITEM);
		
		
		FrameworkManager frameworkManager = new FrameworkManager();
		FrameworkType[] allFrameworks = frameworkManager.getAllFrameworks();
		for (int i = 0; i < allFrameworks.length; i++) {
			FrameworkType type = allFrameworks[i];
				comboFrameworks.add(type.getName());
		}
		
		
		if(this.resourceToSet != null){
			
			try {
				String persistentProperty = this.resourceToSet.getPersistentProperty(new QualifiedName("", "FrameworkType"));
				if(persistentProperty !=null){
					comboFrameworks.setText(persistentProperty);
				}
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		comboFrameworks.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				//logger.debug("I doing the default select");
			}

			public void widgetSelected(SelectionEvent e) {
				
				if(comboFrameworks.getText().equals(REMOVE_ITEM)){
					setSelectedFramework(null);
				}
				else{
					setSelectedFramework(comboFrameworks.getText());
				}
			}
			
			
		});
		
		return super.createDialogArea(parent);
	}

	/**
	 * @param parentShell
	 */
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			if(comboFrameworks.getText().equals(REMOVE_ITEM)){
				setSelectedFramework(null);
			}
			else{
				FrameworkManager frameworkManager = new FrameworkManager();
				setSelectedFramework(frameworkManager.getFrameworkId(comboFrameworks.getText()));
			}
		}
		super.buttonPressed(buttonId);
	}



	public String getSelectedFramework() {
		return selectedFramework;
	}

	public void setSelectedFramework(String selectedFramework) {
		this.selectedFramework = selectedFramework;
	}

}
