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
package com.rohanclan.cfml.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.SWT;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.events.*;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.eclipse.jface.viewers.*;

/**
 * The "New" wizard page allows setting the container for
 * the new file as well as the file name. The page
 * will only accept file name without the extension OR
 * with the extension that matches the expected one (cfm).
 */

public class NewCfcWizardPage extends WizardPage {
	
	
	private Text cfcName;
	private Text cfcExtends;
	private Text cfcPath;
	private Text cfcHint;
	private Text cfcDisplayName;
	
	
	private ISelection selection;
	
	private CfcBean cfcBean;

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCfcWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("New CF Component");
		setDescription("New CF Component wizard.");
		this.selection = selection;
		this.cfcBean = new CfcBean("", "", "", "", "");
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
		label.setText("&Path:");

		this.cfcPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.BEGINNING);
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
		label.setText("&Component Name:");
		this.cfcName = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		gd.horizontalSpan = 2;
		this.cfcName.setLayoutData(gd);
		this.cfcName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
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
		this.cfcBean.setName("NewCfCompontent");
		this.cfcName.setText(this.cfcBean.getName());
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
				true,
				"Select new file container");
		
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
			listSelection = new ResourceListSelectionDialog(getShell(),
					ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
			
		} catch (Exception e) {
			// TODO: handle exception
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
				this.cfcBean.setExtendCfc(s);
				this.cfcExtends.setText(this.cfcBean.getExtendCfc());
			}
		}
	}
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		String containerName = this.cfcPath.getText();
		String fileName = this.cfcName.getText();
		String extend = this.cfcExtends.getText();
		String hint = this.cfcHint.getText();
		String displayName = this.cfcDisplayName.getText();
		this.cfcBean.setDisplayName(displayName);
		this.cfcBean.setExtendCfc(extend);
		this.cfcBean.setHint(hint);
		this.cfcBean.setName(fileName);
		this.cfcBean.setPath(containerName);

		if (containerName.length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}

		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		IContainer container = (IContainer)resource;
		IFile file = container.getFile(new Path(fileName + ".cfc"));
		
		System.out.println(file.getName());

		if(file.exists())
		{
			updateStatus("File already exists!");
			return;
		}
		
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc > 0) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("cfc") == false) {
				updateStatus("File extension will be added automatically \"cfc\"");
				return;
			}
		}
		updateStatus(null);
	}
	
	private boolean validatePage()
	{
		return true;
		
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	public CfcBean getCfcBean()
	{
		return this.cfcBean;
	}
}