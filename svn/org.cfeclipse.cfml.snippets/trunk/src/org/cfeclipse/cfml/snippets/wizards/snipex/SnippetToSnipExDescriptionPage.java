/**
 * 
 */
package org.cfeclipse.cfml.snippets.wizards.snipex;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.cfeclipse.cfml.snippets.preferences.SnipExPreferenceConstants;
import org.cfeclipse.cfml.snippets.util.ResourceUtils;
import org.cfeclipse.cfml.snippets.views.snips.SnippetObject;
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
import org.eclipse.swt.widgets.Text;

/**
 * @author markdrew
 *
 */
public class SnippetToSnipExDescriptionPage extends WizardPage {

	private Combo serverList;
	private Text txt_sDescription;
	private Text txt_sName;
	private Text txt_uEmail;
	private SnippetObject snippet;
	private Text txt_uName;
	
	
	private Log logger = LogFactory.getLog(SnippetToSnipExDescriptionPage.class);
	
	protected SnippetToSnipExDescriptionPage(String pageName, SnippetObject object) {
		super(pageName);
		setTitle(pageName);
		this.snippet = object;
		setDescription("Select the SnipEx server to export this snippet to and enter details about yourself and the snippet that is being exported.");
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
		
		Label lblServer = new Label(container, SWT.NONE);
		lblServer.setText("SnipEx Server:");
		
		
		GridData comboGD = new GridData();
		comboGD.widthHint = 200;
	
		serverList = new Combo(container, SWT.READ_ONLY);
		serverList.setLayoutData(comboGD);	
		serverList.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {	isValidatedPage();}	
		});
		fillServers(serverList);
		
		
		// DETAILS SECTION
		GridData fieldsgd = new GridData();
		fieldsgd.widthHint = 200;
		
		Label lbl_uName = new Label(container, SWT.NONE);
		lbl_uName.setText("Your Name:");
		
		lbl_uName.setLayoutData(fieldsgd);
		
		
		txt_uName = new Text(container, SWT.BORDER);
		txt_uName.setText(System.getProperty("user.name"));
		txt_uName.setLayoutData(fieldsgd);
		txt_uName.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {	isValidatedPage();}	
		});
		
		
		Label lbl_uEmail = new Label(container, SWT.NONE);
		lbl_uEmail.setText("Your Email:");
		
		txt_uEmail = new Text(container, SWT.BORDER);
		txt_uEmail.setLayoutData(fieldsgd);
		txt_uEmail.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {	isValidatedPage();}	
		});
		
		Label lbl_sName = new Label(container, SWT.NONE);
		lbl_sName.setText("Snippet Name:");  // This should be pre-filled from the snippet
		
		
		txt_sName = new Text(container, SWT.BORDER);
		txt_sName.setLayoutData(fieldsgd);
		txt_sName.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {	isValidatedPage();}	
		});
		
		
		GridData spanLabel = new GridData();
		spanLabel.horizontalSpan = 2;
		
		Label lbl_sDescription = new Label(container, SWT.NONE);
		lbl_sDescription.setText("Snippet Description:");
		lbl_sDescription.setLayoutData(spanLabel);
		
		
		GridData spanField = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL |
				GridData.GRAB_VERTICAL | 
				GridData.VERTICAL_ALIGN_FILL);
		spanField.horizontalSpan = 2;
		spanField.widthHint = 410;
		spanField.heightHint = 200;
		txt_sDescription = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		txt_sDescription.setLayoutData(spanField);
		
		
		
		
		
		
		
		
		setControl(container);
	}

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
		
		if(url1.length()>0 && canSubmit(url1)) 	serverList2.add(url1);
		if(url2.length()>0 && canSubmit(url2))  serverList2.add(url2);
		if(url3.length()>0 && canSubmit(url3))  serverList2.add(url3);
		if(url4.length()>0 && canSubmit(url4))  serverList2.add(url4);
		if(url5.length()>0 && canSubmit(url5))  serverList2.add(url5);
		if(url6.length()>0 && canSubmit(url6))  serverList2.add(url6);
		if(url7.length()>0 && canSubmit(url7))  serverList2.add(url7);
		if(url8.length()>0 && canSubmit(url8))  serverList2.add(url8);
	}
	
	private boolean canSubmit(String url1) {
		try {
			URL snipexServer = new URL(url1 + "?method=canSubmit");
			Object response = snipexServer.getContent();
			
			String stringFromInputStream = ResourceUtils.getStringFromInputStream((InputStream)response);
			if(stringFromInputStream.trim().equals("true")){
				return true;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
				logger.debug("Description page is visible");
				if(this.snippet !=null){
					this.txt_sName.setText(snippet.getName());
					this.txt_sDescription.setText(snippet.getDescription());
				}
				isValidatedPage();
		}
	}
	
	public boolean canFinish(){
		return isValidatedPage();
	}
	
	private boolean isValidatedPage(){
		
		if(serverList.getText() == null || serverList.getText().length() == 0){
			setErrorMessage("Please select a SnipEx server");
			return false;
		} 
		if(txt_uName.getText().length() == 0){
			setErrorMessage("Please enter a user's name");
			return false;
		}
		if(txt_uEmail.getText().length() == 0){
			setErrorMessage("Please enter a user's email address");
			return false;
		}
		if(txt_sName.getText().length() == 0){
			setErrorMessage("Please enter a name for this snippet");
			return false;
		}
		
		
		setErrorMessage(null);
		return true;
	}




	public Combo getServerList() {
		return serverList;
	}




	public String getSnippetDescription() {
		return txt_sDescription.getText();
	}




	public String getSnippetName() {
		return txt_sName.getText();
	}




	public String getUserEmail() {
		return txt_uEmail.getText();
	}




	public String getUserName() {
		return txt_uName.getText();
	}
	
}
