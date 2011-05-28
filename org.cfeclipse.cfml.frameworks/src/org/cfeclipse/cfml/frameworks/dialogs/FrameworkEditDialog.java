/**
 * 
 */
package org.cfeclipse.cfml.frameworks.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

/**
 * @author markdrew
 *
 */
public class FrameworkEditDialog extends Dialog{

	
	private Element frameworkElement;
	private Text txtName;
	private Text txtId;
	private Text txtFiles;
	/**
	 * @param parentShell
	 */
	public FrameworkEditDialog(Shell parentShell) {
		super(parentShell);
	}
	
	public FrameworkEditDialog(Shell parentShell, Element framworkElement){
		super(parentShell);
		this.frameworkElement = framworkElement;
	}

	protected Control createContents(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 2;
		
		//Add Items for editing... 
	
		GridData entryBoxGD = new GridData();
		entryBoxGD.widthHint = 200;
		
		
		//Start creating the fields and they are something like:
		/*
		 * 	Framework Name (Displayed) *
		 *  Framework Id (Unique) *
		 *  Default filename (Comma separated list of filenames)
		 */

		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Framework Name * :");
		
		txtName = new Text(container, SWT.NONE);
		
		Label idLabel = new Label(container, SWT.NONE);
		idLabel.setText("Framework Id * :");
		
		txtId = new Text(container, SWT.NONE);
		
		
		Label filesLabel = new Label(container, SWT.NONE);
		filesLabel.setText("Comma separated list of default files for this framework * :");
		
		txtFiles = new Text(container, SWT.NONE);
		
		
		
		
		
		return container;
	}

}
