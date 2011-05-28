/**
 * 
 */
package org.cfeclipse.cfml.snippets.wizards.snipex;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.cfeclipse.cfml.snippets.preferences.SnipExPreferenceConstants;
import org.cfeclipse.snippet.snipex.SnipEx;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author markdrew
 *
 */
public class SelectAppPage extends WizardPage {
	
	
	//Some fields
	private Combo applicationList;
	private Combo serverList;
	private SnipExExportBean exportBean;
	private Log logger = LogFactory.getLog(SelectAppPage.class);
	

	/**
	 * @param pageName
	 * @param exportBean 
	 */
	protected SelectAppPage(String pageName, SnipExExportBean exportBean) {
		super(pageName);
		this.exportBean = exportBean;
		setDescription("Select the Project and the SnipEx server you want to export to.");
		setTitle("Export Project to SnipEx Server");
		//setImageDescriptor(image)
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;		
		
		
		GridData comboGD = new GridData();
		comboGD.widthHint = 200;
		
		Label lblProject = new Label(container, SWT.NONE);
		lblProject.setText("Project:");
		
		applicationList = new Combo(container, SWT.READ_ONLY);
		applicationList.setLayoutData(comboGD);
		applicationList.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				//setPageComplete(isValidState());
				
			}
			
		});
		
		//fill in the applicationList by getting a list of projects
		fillProjects(applicationList);
		
		Label lblServer = new Label(container, SWT.NONE);
		lblServer.setText("SnipEx Server:");
		
		serverList = new Combo(container, SWT.READ_ONLY);
		serverList.setLayoutData(comboGD);	
		serverList.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				//setPageComplete(isValidState());
			}
			
		});
		fillServers(serverList);

		setControl(container);
		
		
	}

	/**
	 * 
	 */
	private void setState() {
		// TODO Auto-generated method stub
		
		((ExportApplicationWizard)getWizard()).setFinished(false);
		getWizard().getContainer().updateButtons();
	}
	
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			//setPageComplete(isValidState());
			setState();
		}
			
	
	}

	/**
	 * @param serverList2
	 */
	private void fillServers(Combo serverList2) {
		//Get the servers from CFMLPlugin.getDefault().getPreferenceStore()
		
		IPreferenceStore preferenceStore = SnippetPlugin.getDefault().getPreferenceStore();
		String url1 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL1);
		String url2 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL2);
		String url3 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL3);
		String url4 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL4);
		String url5 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL5);
		String url6 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL6);
		String url7 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL7);
		String url8 = preferenceStore.getString(SnipExPreferenceConstants.P_SNIPEX_URL8);
		
		//Need to check if we can submit snippets to this server
		
		//
		if(url1.length()>0 && canSubmit(url1)) 	serverList2.add(url1);
		if(url2.length()>0 && canSubmit(url2))  serverList2.add(url2);
		if(url3.length()>0 && canSubmit(url3))  serverList2.add(url3);
		if(url4.length()>0 && canSubmit(url4))  serverList2.add(url4);
		if(url5.length()>0 && canSubmit(url5))  serverList2.add(url5);
		if(url6.length()>0 && canSubmit(url6))  serverList2.add(url6);
		if(url7.length()>0 && canSubmit(url7))  serverList2.add(url7);
		if(url8.length()>0 && canSubmit(url8))  serverList2.add(url8);
		
		
		
		
	}

	/**
	 * @param url1
	 */
	private boolean canSubmit(String url1) {
		try {
			URL snipexServer = new URL(url1 + "?method=canSubmit");
			Object response = snipexServer.getContent();
			
			logger.debug(response);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean canFinish(){
		return false;
	}

	/**
	 * @param applicationList2
	 */
	private void fillProjects(Combo applicationList2) {
		IProject[] projects = getProjects();
	       
	       int selectedItem = 0;
	       
	       //we could loop over this couldnt we?
	       for(int proj = 0; proj <projects.length; proj++){
	    	   IProject project = projects[proj];
	    	 
	    	   if(project.equals(exportBean.getProject())){
	    		   selectedItem = proj;
	    	   }
	    	   applicationList2.add(project.getName());
	       }
	       
	       //we shall put the selected one here, if none is chosen
	       applicationList2.select(selectedItem);
		
	}
	
	 private IProject[] getProjects(){
	    	IWorkspace workspace = SnippetPlugin.getWorkspace();
	    	IProject[] projects = workspace.getRoot().getProjects();
	    	return projects;
	    }
	 
	 public IProject getSelectedProject(){
		 IWorkspace workspace = SnippetPlugin.getWorkspace();
		 IProject project = workspace.getRoot().getProject(applicationList.getText());
		 
		 return project;
	 }

	 
	 public String getServer(){
		 return serverList.getText();
	 }
	 
	 private boolean isValidState(){
		 if(applicationList.getText() == null || applicationList.getText().equals("")){
			 setErrorMessage("Please select the project to export");
			 return false;
		 }
		 if(serverList.getText() == null || serverList.getText().equals("")){
			 setErrorMessage("Please select the SnipEx server to export the project to");
			 return false;
		 }
		 
		 return true;
	 }
	
}
