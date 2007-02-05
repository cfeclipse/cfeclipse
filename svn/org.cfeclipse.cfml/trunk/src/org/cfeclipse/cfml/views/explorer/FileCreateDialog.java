package org.cfeclipse.cfml.views.explorer;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FileCreateDialog extends Dialog {

	public String filename = "untitled.cfm";
	public Text fileNameField;
	
	protected FileCreateDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		 GridLayout gl = new GridLayout();
	     gl.numColumns = 1;
	     container.setLayout(gl);
		
	     
	     Label messageLabel = new Label(container, SWT.NONE); 
	     messageLabel.setText("Please enter a filename:");
		
	     GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
         gridData.widthHint = 200;
         
         fileNameField = new Text(container, SWT.BORDER);
         fileNameField.setLayoutData(gridData);
         fileNameField.setText(this.filename);
         
	     
		return container;
	}


	public String getFileName() {
		return fileNameField.getText();
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
				this.filename = fileNameField.getText();
		}
		super.buttonPressed(buttonId);
	}

	
	
	
}
