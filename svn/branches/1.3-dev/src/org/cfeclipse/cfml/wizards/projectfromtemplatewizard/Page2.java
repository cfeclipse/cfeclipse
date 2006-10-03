package org.cfeclipse.cfml.wizards.projectfromtemplatewizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.ApplicationTemplatesPreferenceConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class Page2 extends WizardPage {

	private ISelection thisSelection;
	private String projectLocation;
	private CheckboxTableViewer stringReplacements;
	
	private Composite parent;
	private Composite container;
	private GridLayout gridLayout;
	
	protected Page2(ISelection selection) {
		super("replacePage");
		thisSelection = selection;
	}

	public void createControl(Composite parent) {
		 
		Composite container = new Composite(parent, SWT.NONE);
		
		container.setLayout(gridLayout);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		Label label = new Label(container, SWT.NONE);
		label.setText("Label");
		Text text = new Text(container, SWT.BORDER);
		text.setText("something");
		
		
		
		 
		 
		 
		 setControl(container);
	      
	     
	      
	    	   
	}


	private Properties getProperties() {
		IPath path = null;
		String ApplicationTemplatesProject = CFMLPlugin.getDefault().getPluginPreferences().getString(ApplicationTemplatesPreferenceConstants.P_APPLICATION_TEMPLATE_PROJECT);
		IProject project = CFMLPlugin.getWorkspace().getRoot().getProject(ApplicationTemplatesProject);
		String templatesProject = project.getLocation().toString();
		
		Page1 selectionPage = (Page1)((ProjectFromTemplateWizard) getWizard()).getPage("selectionPage");
		path = new Path(templatesProject + "/" + selectionPage.getSelectedApplication() + "/" + "replace.properties");
		
		File propertyFile = path.toFile();
		
		if(propertyFile.exists()){
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(propertyFile.toString()));
				return props;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}

	public void setVisible(boolean visible){
		
	if(visible){
		//  System.out.println("trying to get the settings...");
		  WizardNewProjectCreationPage projectPage = (WizardNewProjectCreationPage)((ProjectFromTemplateWizard) getWizard()).getPage("projectPage");
		  Page1 selectionPage = (Page1)((ProjectFromTemplateWizard) getWizard()).getPage("selectionPage");
	    //   System.out.println(projectPage.getLocationPath() + " " + projectPage.getProjectName() + selectionPage.getSelectedApplication());

		  Properties replaceFields = getProperties();
	       
	       	Set propItems = replaceFields.entrySet();
	       	Iterator iterator = propItems.iterator();
	    	  while (iterator.hasNext()) {
				Entry entryItem = (Entry) iterator.next();
				//Here we add text fields to the container:
				
				
				
			} 
		  
		  
	}
		
		
	}
}
