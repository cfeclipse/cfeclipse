/**
 * 
 */
package org.cfeclipse.cfml.wizards.snipex;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author markdrew
 *
 */
public class EnterDescriptionPage extends WizardPage {

	private SnipExExportBean exportBean;

	/**
	 * @param pageName
	 * @param exportBean 
	 */
	protected EnterDescriptionPage(String pageName, SnipExExportBean exportBean) {
		super(pageName);
		this.exportBean = exportBean;
		setTitle(pageName);
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
		layout.marginLeft = 10;
		
		
		GridData fieldsgd = new GridData();
		fieldsgd.widthHint = 200;
		
		Label lbl_uName = new Label(container, SWT.NONE);
		lbl_uName.setText("Your Name:");
		lbl_uName.setLayoutData(fieldsgd);
		
		
		Text txt_uName = new Text(container, SWT.BORDER);
		txt_uName.setLayoutData(fieldsgd);
		
		Label lbl_uEmail = new Label(container, SWT.NONE);
		lbl_uEmail.setText("Your Email:");
		
		Text txt_uEmail = new Text(container, SWT.BORDER);
		txt_uEmail.setLayoutData(fieldsgd);
		
		
		Label lbl_sName = new Label(container, SWT.NONE);
		lbl_sName.setText("Application Name:");
		
		
		Text txt_sName = new Text(container, SWT.BORDER);
		txt_sName.setLayoutData(fieldsgd);
		
		
		GridData spanLabel = new GridData();
		spanLabel.horizontalSpan = 2;
		
		Label lbl_sDescription = new Label(container, SWT.NONE);
		lbl_sDescription.setText("Application Description:");
		lbl_sDescription.setLayoutData(spanLabel);
		
		
		GridData spanField = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL |
				GridData.GRAB_VERTICAL | 
				GridData.VERTICAL_ALIGN_FILL);
		spanField.horizontalSpan = 2;
		spanField.widthHint = 410;
		spanField.heightHint = 200;
		Text txt_sDescription = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		txt_sDescription.setLayoutData(spanField);
		
		//Enter the user's name
		//Enter the user's email
		//Enter a description for this Project Snippet
		
		setControl(container);
		

	}

	private void setState() {
		((ExportApplicationWizard)getWizard()).setFinished(true);
		getWizard().getContainer().updateButtons();
	}
	

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			setState();
		}
	}
	
	
	
}
