package org.cfeclipse.cfml.wizards.projectfromtemplatewizard;


import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.ApplicationTemplatesPreferenceConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


public class Page1 extends WizardPage {

	private ISelection selection;
	//private NewLavaProjectBean projectbean;
	private Text projectName;
	private Combo applicationList;
	
	public Page1(ISelection selection) {
			super("selectionPage");
			setTitle("New Lava Project Settings");
			setDescription("Define the location you would like to store the files, and the location of your Lava server)");
			
		}


	public void createControl(Composite parent) {
		
		 Composite projectGroup = new Composite(parent, SWT.NONE);
	        GridLayout layout = new GridLayout();
	        layout.numColumns = 2;
	        projectGroup.setLayout(layout);
	        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	        // new project label
	     final  Label projectLabel = new Label(projectGroup, SWT.NONE);
	        projectLabel.setText("Select Template Application");
	        projectLabel.setFont(parent.getFont());
	      
		
	    	

		applicationList = new Combo(projectGroup, SWT.NULL | SWT.READ_ONLY);
		GridData data2 = new GridData ();
		data2.widthHint = 295;
		applicationList.setLayoutData(data2);
		applicationList.setItems(getApplications());
		applicationList.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e){
				updatePageComplete();
			}
			
			
		});
		setControl(projectGroup);
		

	}
	

	
	
	private String[] getApplications(){
		String[] applicationList = null;
		//Get the project properties
		String ApplicationTemplatesProject = CFMLPlugin.getDefault().getPluginPreferences().getString(ApplicationTemplatesPreferenceConstants.P_APPLICATION_TEMPLATE_PROJECT);
		//Get the project and list the children
		IProject project = CFMLPlugin.getWorkspace().getRoot().getProject(ApplicationTemplatesProject);
		 try {
			IResource children[] = project.members();
			
			ArrayList apps = new ArrayList<String>();
			for (int i = 0; i < children.length; i++) {
				IResource memberItem = children[i];
				if((int)memberItem.getType() == 2){
					apps.add(memberItem.getName());
				}
			}
			
			
			applicationList  = new String[apps.size()];
			Iterator appIter = apps.iterator();
			int counter = 0;
			while (appIter.hasNext()) {
				String object = (String) appIter.next();
				applicationList[counter] = object;
				counter++;
			}	
				
			
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return applicationList;
	}
	
	
	public String getSelectedApplication(){
		return applicationList.getItem(applicationList.getSelectionIndex());
	}
	
	private void updatePageComplete() {
		setPageComplete(false);
		
		
		if(applicationList.getText().length() == 0){
			setErrorMessage("Please select a Template");
			return;
		}
		else{
			setPageComplete(true);
		}
		
		setMessage(null);
		setErrorMessage(null);
		
		
	}

}
