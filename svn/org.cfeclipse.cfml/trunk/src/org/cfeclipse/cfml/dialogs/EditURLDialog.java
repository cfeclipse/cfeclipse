package org.cfeclipse.cfml.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EditURLDialog extends Dialog {

	public String url = "http://";
	public String message = "Please enter  this item's URL:";
	public Text fileNameField;
	
	public EditURLDialog(Shell parentShell) {
		super(parentShell);
		
	}
	public EditURLDialog(Shell parentShell, String url) {
		super(parentShell);
		this.url = url;
		
	}
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		 GridLayout gl = new GridLayout();
	     gl.numColumns = 1;
	     container.setLayout(gl);
		
	     
	     Label messageLabel = new Label(container, SWT.NONE); 
	     messageLabel.setText(message);
		
	     GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
         gridData.widthHint = 400;
		
         parent.addPaintListener(new PaintListener(){

			public void paintControl(PaintEvent e) {
				//TODO: This is rather hokey, I couldn't find a way to find out if the dialog finally has the buttons
			//	validateInput();
				
			}});

         fileNameField = new Text(container, SWT.BORDER);
      
         fileNameField.setLayoutData(gridData);
         fileNameField.setText(this.url);
        
        
		return container;
	}


	
	public String getFileName() {
		return fileNameField.getText();
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
				this.url = fileNameField.getText();
		}
		super.buttonPressed(buttonId);
	}

	private void validateInput() {
		Button button = getButton(IDialogConstants.OK_ID);
		//if (fileNameField.getText().trim().length() == 0) {
		//	button.setEnabled(false);
		//}else{
			button.setEnabled(true);
		//}
		
	}

	public Text getFileNameField() {
		return fileNameField;
	}

	public void setFileNameField(Text fileNameField) {
		this.fileNameField = fileNameField;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	
	
}
