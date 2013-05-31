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
package org.cfeclipse.cfml.wizards.cfcwizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;


/**
 * The "New" wizard page allows setting the container for
 * the new file as well as the file name. The page
 * will only accept file name without the extension OR
 * with the extension that matches the expected one (cfm).
 */
public class NewCFCWizardPage extends WizardPage {
	private Text cfcName;
	private Text cfcExtends;
	private Text cfcPath;
	private Text cfcHint;
	private Text cfcDisplayName;
	private Button cfcAccessors;
	private Button cfcOutput;
	private Button cfcPersistent;
	
	private ISelection selection;
	
	private CFCBean cfcBean;
	private Combo cfcStyle;

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCFCWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("New CF Component");
		setDescription("New CF Component wizard.");
		this.selection = selection;
		
		this.cfcBean = new CFCBean();
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 3;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Component Name:");
		this.cfcName = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		gd.horizontalSpan = 2;
		this.cfcName.setLayoutData(gd);
		this.cfcName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Style:");

		this.cfcStyle = new Combo(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		gd.horizontalSpan = 2;
		this.cfcStyle.setLayoutData(gd);
		String items[] = { "cfscript", "tag" };
		this.cfcStyle.setItems(items);
		this.cfcStyle.select(0);
		this.cfcStyle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Path:");

		this.cfcPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		this.cfcPath.setLayoutData(gd);
		this.cfcPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setLayoutData(new GridData(GridData.BEGINNING));
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		
		label = new Label(container, SWT.NULL);
		label.setText("&Extends:");
		this.cfcExtends = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		this.cfcExtends.setLayoutData(gd);
		this.cfcExtends.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Button extButton = new Button(container, SWT.PUSH);
		extButton.setText("Browse...");
		extButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleExtendBrowse();
			}
		});
				
		label = new Label(container, SWT.NULL);
		label.setText("&Hint:");
		this.cfcHint = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		gd.horizontalSpan = 2;
		this.cfcHint.setLayoutData(gd);
		this.cfcHint.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(container, SWT.NULL);
		label.setText("&Display Name:");
		this.cfcDisplayName = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		gd.horizontalSpan = 2;
		this.cfcDisplayName.setLayoutData(gd);
		this.cfcDisplayName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Accessors");
		this.cfcAccessors = new Button(container, SWT.CHECK);
		gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		this.cfcAccessors.setLayoutData(gd);
		this.cfcAccessors.setSelection(true);
		this.cfcAccessors.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Output");
		this.cfcOutput = new Button(container, SWT.CHECK);
		gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		this.cfcOutput.setLayoutData(gd);
		this.cfcOutput.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Persistent");
		this.cfcPersistent = new Button(container, SWT.CHECK);
		gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		this.cfcPersistent.setLayoutData(gd);
		this.cfcPersistent.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		initialize();
		dialogChanged();
		setControl(container);
	}
	
	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	private void initialize() {
		if(selection != null && selection.isEmpty() == false
			&& selection instanceof IStructuredSelection) 
		{
			IStructuredSelection ssel = (IStructuredSelection)selection;
			
			if (ssel.size()>1) return;
			
			Object obj = ssel.getFirstElement();
			
			if(obj instanceof IResource) 
			{
				IContainer container;
				if(obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				
				this.cfcBean.setPath(container.getFullPath().toString());
				this.cfcPath.setText(this.cfcBean.getPath());
			}
		}
		
		this.cfcBean.setName("NewCFComponent");
		this.cfcBean.setAccessors(true);

		this.cfcName.setText(this.cfcBean.getName());
		this.cfcName.setSelection(0, this.cfcBean.getName().length());

	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */
	private void handleBrowse() {
				
		ContainerSelectionDialog dialog =	new ContainerSelectionDialog(
			getShell(),
			ResourcesPlugin.getWorkspace().getRoot(),
			true,
			"Select new file container"
		);
		
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				this.cfcBean.setPath(((Path)result[0]).toOSString());
				this.cfcPath.setText(this.cfcBean.getPath());
			}
		}
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the cfcExtend field.
	 */
	private void handleExtendBrowse() {
		ResourceListSelectionDialog listSelection = null;
		
		try {
			listSelection = new ResourceListSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), 
				IResource.FILE
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(listSelection.open() == ResourceListSelectionDialog.OK)
		{
			Object[] result = listSelection.getResult();
			
			if(result.length == 1)
			{
				IResource resource = (IResource)result[0];
				String s = resource.getProjectRelativePath().toString();
				s = s.replaceAll("/", ".").replaceAll("." + resource.getFileExtension(), "");
				
				this.cfcBean.setExtendCFC(s);
				this.cfcExtends.setText(this.cfcBean.getExtendCFC());
			}
		}
	}
	
	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		
		String containerName = this.cfcPath.getText();
		String style = this.cfcStyle.getText();
		String fileName = this.cfcName.getText();
		String extend = this.cfcExtends.getText();
		String hint = this.cfcHint.getText();
		String displayName = this.cfcDisplayName.getText();
		String accessors = Boolean.toString(this.cfcAccessors.getSelection());
		String output = Boolean.toString(this.cfcOutput.getSelection());
		String persistent = Boolean.toString(this.cfcPersistent.getSelection());
		
		this.cfcBean.setDisplayName(displayName);
		this.cfcBean.setStyle(style);
		this.cfcBean.setExtendCFC(extend);
		this.cfcBean.setHint(hint);
		this.cfcBean.setName(fileName);
		this.cfcBean.setOutput(output);
		this.cfcBean.setAccessors(accessors);
		this.cfcBean.setPersistent(persistent);
		this.cfcBean.setPath(containerName);

		if(containerName.length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		
		if(fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		
		IContainer container = (IContainer)resource;
		IFile file = container.getFile(new Path(fileName + ".cfc"));
		
		//System.out.println(file.getName());

		if(file.exists()) {
			updateStatus("File already exists!");
			return;
		}
		
		int dotLoc = fileName.lastIndexOf('.');
		
		if(dotLoc > 0){
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("cfc")) {
				updateStatus("File extension will be added automatically \"cfc\"");
				return;
			}
		}
		updateStatus(null);
	}
	
	private boolean validatePage() {
		return true;
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	public CFCBean getCFCBean() {
		return this.cfcBean;
	}
}