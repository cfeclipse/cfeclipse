/*
 * Created on 10.04.2004
 *
 * The MIT License
 * Copyright (c) 2004 Chris Queener
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */

package org.cfeclipse.cfml.wizards.cfunit;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.jface.resource.ImageDescriptor;


/**
 * The CFUnit wizard page allows setting the container for
 * the new file as well as the file name.
 */
public class NewCFUnitWizardPage extends WizardPage {

	private Text containerText;
	private Text fileText;
	private Text cfunitLocation;
	private Text runnerText;
	private Button propertyCreateRunner;
	private ISelection selection;
	private IEditorPart editor = null;

	/**
	 * Constructor for NewCFUnitWizardPage.
	 * @param pageName
	 */
	public NewCFUnitWizardPage(ISelection selection) {
		super("wizardPage");
		setup();
		this.selection = selection;
	}

	public NewCFUnitWizardPage(IEditorPart editorPart) {
		super("wizardPage");
		setup();		
		this.editor = editorPart;
	}

	/**
	 * Initialization method used by the constructors to set up the wizard's details.
	 */
	private void setup() {
		setTitle("CFUnit Wizard");
		setDescription("For help or documentation: http://cfunit.sourceforge.net");

		ImageDescriptor img = ImageDescriptor.createFromFile( getClass(), "../../../../../../icons/obj16/cfunit_descriptor.gif" );
		setImageDescriptor( img );
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		// TEST CASE PATH
		Label label = new Label(container, SWT.NULL);
		label.setText("&Path:");
		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		// FILE NAME
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(container, SWT.NULL);
		label.setText("");

		// CFUNIT LOCATION
		label = new Label(container, SWT.NULL);
		label.setText("&CFUnit Location:");
		
		cfunitLocation = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		cfunitLocation.setLayoutData(gd);
		cfunitLocation.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		cfunitLocation.setText("net.sourceforge.cfunit.framework");

		Button button2 = new Button(container, SWT.PUSH);
		button2.setText("Browse...");
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleCFUnitBrowse();
			}
		});

		// CREATE TEST RUNNER
		propertyCreateRunner = new Button(container, SWT.CHECK);
		propertyCreateRunner.setText("Create Runner");
		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.horizontalAlignment = GridData.BEGINNING;
		propertyCreateRunner.setLayoutData(gd);
		
		propertyCreateRunner.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				setRunnerEnabled(propertyCreateRunner.getSelection());
			}
			public void widgetDefaultSelected(SelectionEvent e) {;}
		});
		
		runnerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		runnerText.setLayoutData(gd);
		runnerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		runnerText.setText("index.cfm");
		runnerText.setEnabled( false );
		
		
		initialize();
		dialogChanged();
		setControl(container);
		setFilenameFocus();

	}
	
	/**
	 * Enables/disables the create runner field
	 * @param to
	 */
	public void setRunnerEnabled(boolean b){
		runnerText.setEnabled( b );
	}

	/**
	 * 
	 * Mark D  I added this as I am trying to make it focus on the right item when you open the dialog
	 */
	private void setFilenameFocus(){
		// TODO: this works if you do a right click but not if you come here from a select your page type page
		fileText.setFocus();
		System.out.println("setting the focus");
		int textLen = fileText.getText().length() - 4;
		fileText.setSelection(0,textLen);
	}

	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	private void initialize() {

		if (selection!=null && selection.isEmpty()==false && selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection)selection;
			if (ssel.size()>1) return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		} else if (this.editor != null){
		    if (editor.getEditorInput() instanceof FileEditorInput) {
		        FileEditorInput input = (FileEditorInput)editor.getEditorInput();
		        containerText.setText(input.getFile().getParent().getFullPath().toString());
		    }
		}

		//TODO: this works if you do a right click but not if you come here from a select your page type page
		fileText.setText("TestUntitled.cfc");
		setFilenameFocus();
	}

	

	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */
	private void handleBrowse() {
		ContainerSelectionDialog dialog =
			new ContainerSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				false,
				"Select CFUnit Location");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path)result[0]).toOSString());
			}
		}
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */
	private void handleCFUnitBrowse() {
		ContainerSelectionDialog dialog =
			new ContainerSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				
				// Convert the path to a dot-nation location which can be used by ColdFusion
				String path = ((Path)result[0]).toString().replace("/", ".");
					
				if( path.startsWith(".") ) {
					path = path.substring(1);					
				}
				
				cfunitLocation.setText( path );
			}
		}
	}
	

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		String container = getContainerName();
		String fileName = getFileName();
		
		if (container.length() == 0) {
			updateStatus("File container must be specified");
			return;
		}

		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("cfc") == false) {
				updateStatus("The file extension for CFUnit test case must be \"cfc\"");
				return;
			}
		}		
		
		if(fileName.indexOf("Test") == -1) {
			setMessage("It is recommended that a test case name begin with the phrase \"Test\" – this will insure it is found by automated test scripts", 2);
			return;
		}
		
		setMessage(null);
		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}

	public String getCFUnitLocation() {
		return cfunitLocation.getText();
	}

	public String getRunner() {
		return runnerText.getText();
	}

	public boolean isCreateRunner() {
		return propertyCreateRunner.getSelection();
	}
	
	
}
