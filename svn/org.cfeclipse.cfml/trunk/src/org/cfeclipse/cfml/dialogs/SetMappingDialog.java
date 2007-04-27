package org.cfeclipse.cfml.dialogs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.mappings.MappingManager;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A dialog used to enter a mapping for a resource in the project.
 * @author Mike Kelp
 */
public class SetMappingDialog extends Dialog {
	private Log logger = LogFactory.getLog(SetMappingDialog.class);
	
	/**
	 * The resource to set a mapping for. 
	 */
	private IResource resource;

	/**
	 * Text field for the mapping name. 
	 */
	private Text mappingNameField;

	/**
	 * The mapping name.
	 */
	private String mappingName = "";

	/**
	 * @return The mapping name entered in the dialog.
	 */
	public String getMappingName() {
		return this.mappingName;
	}
	
	/**
	 * Constructs a new dialog for setting a mapping.
	 * @param parentShell
	 * @param resource The resource to set a mapping for.
	 */
	public SetMappingDialog(Shell parentShell, IResource resource) {
		super(parentShell);
		this.resource = resource;
	}


	/**
	 * Creates the dialog area for the 
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		/*
		 * Build the dialog
		 */

		// Create a container for the whole form
		Composite container = new Composite(parent, SWT.NULL);

		// Setup layout
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 2;

		// Dialog heading label
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;
		Label resourceLabel = new Label(container, SWT.NONE);
		resourceLabel.setLayoutData(labelData);
		resourceLabel.setText("Set ColdFusion Mapping To: " + resource.getName());

		// Label the mapping name text field
		Label frameworkLabel = new Label(container, SWT.NONE);
		frameworkLabel.setText("Mapping:");
		// Mapping Name Text Field
		mappingNameField = new Text(container, SWT.BORDER);
		mappingNameField.setText(MappingManager.getMapping(resource));


		/*
		 * Set the listeners for the dialog
		 */
		mappingNameField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				logger.trace("Pressed a key!");
			}

			public void keyReleased(KeyEvent e) {
				/* TODO: On key release, check if a mapping already exists */
				logger.trace("Released a key!");
			}
		});

		return super.createDialogArea(parent);
	}

	/**
	 * 
	 */
	protected void buttonPressed(int buttonId) {
		// Check if the OK button was pressed
		if (buttonId == IDialogConstants.OK_ID) {
			mappingName = mappingNameField.getText();
		}

		super.buttonPressed(buttonId);
	}
}
