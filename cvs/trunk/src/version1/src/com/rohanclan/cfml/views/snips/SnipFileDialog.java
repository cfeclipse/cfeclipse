/*
 * Created on May 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.jface.dialogs.IDialogConstants;
//import org.eclipse.jface.dialogs.IInputValidator;
/**
 * @author Stephen Milligan
 *
 *TODO: This really shouldn't be extending inputDialog, it should be extending Dialog
 *
 */

public class SnipFileDialog extends Dialog {
	
	private static String dialogTitle = "New Snippet";
	private static String snippetNameLabel = "Snippet name: ";
	private static String snippetCodeStartLabel = "Snippet starting block: ";
	private static String snippetCodeEndLabel = "Snippet closing block: ";
	private TreeViewer treeView;
	private Text snippetStartText, snippetEndText, snippetNameText;
	private String snippetNameValue = "";
	private String snippetStartValue = "";
	private String snippetEndValue = "";
	private SnipWriter writer;

	/**
	 * Ok button widget.
	 */
	private Button okButton;


	/**
	 * Error message label widget.
	 */
	private Label errorMessageLabel;
	
	
	
	public SnipFileDialog(Shell parent, SnipWriter fileWriter, TreeViewer treeView, String snippetNameInitialValue, String startTextInitialValue, String endTextInitialValue) {
		//super(parent, dialogTitle, snippetNameLabel, snippetNameInitialValue, null);
		
		super(parent);
		if (snippetNameInitialValue == null)
			snippetNameValue = "";//$NON-NLS-1$
		else
			snippetNameValue = snippetNameInitialValue;
		//this.validator = validator;
		
		this.treeView = treeView;
		writer = fileWriter;
		if (startTextInitialValue != null) {
			snippetStartValue = startTextInitialValue;
		}
		if (endTextInitialValue != null) {
			snippetEndValue = endTextInitialValue;
		}
		
	}
	
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = (Composite)super.createDialogArea(parent);

		// create message
		if (snippetNameLabel != null) {
			Label label = new Label(composite, SWT.WRAP);
			label.setText(snippetNameLabel);
			GridData data = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.GRAB_VERTICAL |
				GridData.HORIZONTAL_ALIGN_FILL |
				GridData.VERTICAL_ALIGN_CENTER);
			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			label.setLayoutData(data);
			label.setFont(parent.getFont());
		}
	
		snippetNameText= new Text(composite, SWT.SINGLE | SWT.BORDER);
		snippetNameText.setLayoutData(new GridData(
			GridData.GRAB_HORIZONTAL |
			GridData.HORIZONTAL_ALIGN_FILL));
		snippetNameText.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateInput();
				}
			}
		);
		snippetNameText.setFont(parent.getFont());
	
		errorMessageLabel = new Label(composite, SWT.NONE);
		errorMessageLabel.setLayoutData(new GridData(
			GridData.GRAB_HORIZONTAL |
			GridData.HORIZONTAL_ALIGN_FILL));
		errorMessageLabel.setFont(parent.getFont());
		
	
	 	//add controls to composite as necessary
		//Snippet start block
		Label label = new Label(composite, SWT.WRAP);
		label.setText(snippetCodeStartLabel);
		GridData data = new GridData(
			GridData.GRAB_HORIZONTAL |
			GridData.GRAB_VERTICAL |
			GridData.HORIZONTAL_ALIGN_FILL |
			GridData.VERTICAL_ALIGN_CENTER);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
		label.setLayoutData(data);
		label.setFont(parent.getFont());


		snippetStartText= new Text(composite, SWT.BORDER | SWT.MULTI);
		data = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL |
				GridData.GRAB_VERTICAL | 
				GridData.VERTICAL_ALIGN_FILL);
		data.heightHint = convertHeightInCharsToPixels(6);
		snippetStartText.setLayoutData(data);
		snippetStartText.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateInput();
				}
			}
		);
		snippetStartText.setFont(parent.getFont());

		
		
		//Snippet end block
		label = new Label(composite, SWT.WRAP);
		label.setText(snippetCodeEndLabel);
		data = new GridData(
			GridData.GRAB_HORIZONTAL |
			GridData.GRAB_VERTICAL |
			GridData.HORIZONTAL_ALIGN_FILL |
			GridData.VERTICAL_ALIGN_CENTER);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
		label.setLayoutData(data);
		label.setFont(parent.getFont());


		snippetEndText= new Text(composite, SWT.BORDER | SWT.MULTI);
		data = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL |
				GridData.GRAB_VERTICAL | 
				GridData.VERTICAL_ALIGN_FILL);
		data.heightHint = convertHeightInCharsToPixels(6);
		snippetEndText.setLayoutData(data);
		snippetEndText.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateInput();
				}
			}
		);
		snippetEndText.setFont(parent.getFont());
		
	 	return composite;

	}
	


	protected void okPressed() {

		writer.writeSnippet(snippetNameText.getText(),snippetStartText.getText(),snippetEndText.getText());
		
		close();
		treeView.refresh();

	}


	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			snippetNameValue = snippetNameText.getText();
			snippetStartValue = snippetStartText.getText();
			snippetEndValue = snippetEndText.getText();
		} else {
			snippetNameValue= null;
			snippetStartValue = null;
			snippetEndValue = null;
		}
		super.buttonPressed(buttonId);
	}


	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (dialogTitle != null)
			shell.setText(dialogTitle);
	}
	
	
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	
		//do this here because setting the text will set enablement on the ok button
		snippetNameText.setFocus();
		if (snippetNameValue != null) {
			snippetNameText.setText(snippetNameValue);
			snippetNameText.selectAll();
		}
		
		if (snippetStartValue != null) {
			snippetStartText.setText(snippetStartValue);
		}
		
		if (snippetEndValue != null) {
			snippetEndText.setText(snippetEndValue);
		}
	}
	
	
	protected Label getErrorMessageLabel() {
		return errorMessageLabel;
	}
	
	protected Button getOkButton() {
		return okButton;
	}
	
	
	
	

	
	protected void validateInput() {
		
		/*
		String errorMessage = null;
	
		if (validator != null) {
			errorMessage = validator.isValid(snippetNameText.getText());
		}
	
		// Bug 16256: important not to treat "" (blank error) the same as null (no error)
		errorMessageLabel.setText(errorMessage == null ? "" : errorMessage); //$NON-NLS-1$
		okButton.setEnabled(errorMessage == null);
	
		errorMessageLabel.getParent().update();
		*/
	}
	
}
