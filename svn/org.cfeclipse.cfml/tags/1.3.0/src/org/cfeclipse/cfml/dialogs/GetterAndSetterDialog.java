package org.cfeclipse.cfml.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class GetterAndSetterDialog extends Dialog {
	
	protected String title = "Generate Getters and Setters";
	
	public GetterAndSetterDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	
	}

	
	protected Control createDialogArea(Composite parent) {
		
			
		return super.createDialogArea(parent);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(this.title);
		newShell.setText("Select the properties that you want to create Set and Get methods for.");
	}

}
