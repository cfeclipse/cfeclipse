/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;


import com.rohanclan.cfml.ftp.FtpConnectionProperties;
/**
 * @author spike
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FtpConnectionDialog extends Dialog {


	public FtpConnectionProperties connectionProperties;
	private Text host,path,username,password,connectionid;
	
	
	
	/**
	 * @param parent
	 */
	public FtpConnectionDialog(Shell parent,String connectionId) {
		super(parent);
		// TODO Auto-generated constructor stub
		
		connectionProperties = new FtpConnectionProperties(connectionId);
		
		
	}
	
	
	

	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		

		// Connectionid
		connectionid = createTextControl(container,"Connection Name:",connectionProperties.getHost(),50);

		// Host name
		host = createTextControl(container,"Host Name:",connectionProperties.getHost(),50);
		
		// Path
		path = createTextControl(container,"Path:",connectionProperties.getPath(),20);
		
		// Username
		username = createTextControl(container,"Username:",connectionProperties.getUsername(),20);
		
		// Password
		password = createPasswordControl(container,"Password:",connectionProperties.getPassword(),20);

		return container;
	}
	
	
	private Text createTextControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT);
		label.setText(labelText);
		Text control = new Text(parent,SWT.RIGHT);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}
	
	
	private Text createPasswordControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT);
		label.setText(labelText);
		Text control = new Text(parent,SWT.RIGHT|SWT.PASSWORD);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}
	
	

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			try {
			connectionProperties.setHost(host.getText());
			connectionProperties.setPath(path.getText());
			connectionProperties.setUsername(username.getText());
			connectionProperties.setPassword(password.getText());
			connectionProperties.setConnectionid(connectionid.getText());
			connectionProperties.save();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			
		}
		super.buttonPressed(buttonId);
	}

	
	
	protected void validateInput() {
		

		String errorMessage = null;
		/*
		String test = snippetNameText.getText();
		if (!snippetNameText.getText().matches("[0-9a-zA-Z _-]+")) {
		    errorMessage = "The snippet name can only contain numbers, alphabetic characters, space underscore and dash.";
		}
		else if (snippetKeyComboText.getText().length() > 0 && !snippetKeyComboText.getText().matches("[0-9a-zA-Z_-]+")) {
		    errorMessage = "The trigger text can only contain numbers, alphabetic characters, underscore and dash.";
		}
		errorMessageLabel.setText(errorMessage == null ? "" : errorMessage); //$NON-NLS-1$
	    
		okButton.setEnabled(errorMessage == null);
	
		errorMessageLabel.getParent().update();
		*/
	}
	
	
	
}
