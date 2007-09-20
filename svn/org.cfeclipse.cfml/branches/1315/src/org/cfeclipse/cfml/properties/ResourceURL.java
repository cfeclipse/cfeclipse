package org.cfeclipse.cfml.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

public class ResourceURL extends PropertyPage implements IWorkbenchPropertyPage {

	public ResourceURL() {
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		
		GridLayout maingd = new GridLayout();
		maingd.numColumns = 4;
		composite.setLayout(maingd);
	
		
		
		
		
		
		return composite;
	}

}
