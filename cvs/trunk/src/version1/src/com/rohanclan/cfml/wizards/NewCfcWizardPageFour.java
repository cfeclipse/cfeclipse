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
import org.eclipse.swt.events.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Font;

/**
 * The "New" wizard page allows setting the container for
 * the new file as well as the file name. The page
 * will only accept file name without the extension OR
 * with the extension that matches the expected one (cfm).
 */

public class NewCfcWizardPageFour extends WizardPage {
	private Text argumentName;
	private Text argumentDisplayName;
	private Combo argumentType;
	private Text argumentHint;
	private Text argumentDefault;
	private String[] argumentNames;
	private Combo funcList;
	private Button argumentIsRequired;
	private List argumentList;
	
	private ISelection selection;
	
	private NewCfcWizardPageThree functPage;


	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCfcWizardPageFour(ISelection selection) {
		super("wizardPage");
		setTitle("New CF Component");
		setDescription("New CF Component Arguments wizard.");
		this.selection = selection;
		
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		
		Label availFuncsLabel = new Label(container, SWT.NULL);
		availFuncsLabel.setText("&Functions");
		GridData data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		availFuncsLabel.setLayoutData(data);
		
		funcList = new Combo(container, SWT.NULL | SWT.READ_ONLY);
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 5;
		data.widthHint = 300;
		funcList.setLayoutData(data);
		
		
		Label blankLabel = new Label(container, SWT.NULL);
		blankLabel.setText("");
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		blankLabel.setLayoutData(data);		
		
		Composite buttons = new Composite(container, SWT.NULL);
		GridLayout buttonLayout = new GridLayout(2, true);
		buttonLayout.marginWidth = 0;
		buttons.setLayout(buttonLayout);
		
		Button plus = new Button(buttons, SWT.PUSH);
		plus.setText(" + ");
		plus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		plus.setLayoutData(data);
		plus.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAdd();
			}
		});
		
		
		Button minus = new Button(buttons, SWT.PUSH);
		minus.setText(" - ");
		minus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		minus.setLayoutData(data);		
		
		
		Label propLabel = new Label(container, SWT.NULL);
		propLabel.setText("&Arguments");
		propLabel.setAlignment(SWT.BEGINNING);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		propLabel.setLayoutData(data);
		
		argumentList = new List (container, SWT.BORDER | SWT.V_SCROLL);
		// argumentList.setItems (new String [] {"ArgOne", "ArgTwo", "ArgThree"});

		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 5;
		data.widthHint = 300;
		argumentList.setLayoutData(data);	
		
		
		Label nameLabel = new Label (container, SWT.NONE);
		nameLabel.setText ("&Name");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		nameLabel.setLayoutData(data);
		
		argumentName = new Text(container, SWT.BORDER);
		argumentName.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentName.setLayoutData(data);
		argumentName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Label displayLabel = new Label (container, SWT.NONE);
		displayLabel.setText ("&Display Name");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		displayLabel.setLayoutData(data);
		
		argumentDisplayName = new Text(container, SWT.BORDER);
		argumentDisplayName.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentDisplayName.setLayoutData(data);
		
		Label hintLabel = new Label (container, SWT.NONE);
		hintLabel.setText ("&Hint");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		hintLabel.setLayoutData(data);
		
		argumentHint = new Text(container, SWT.BORDER);
		argumentHint.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentHint.setLayoutData(data);
		
		
		Label accessLabel = new Label (container, SWT.NONE);
		accessLabel.setText ("&Type");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		accessLabel.setLayoutData(data);
		
		argumentType = new Combo(container, SWT.BORDER);
		argumentType.setItems(new String[]{"any", "array", "struct", "query"});
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentType.setLayoutData(data);
		
		
		Label requiredLabel = new Label (container, SWT.NONE);
		requiredLabel.setText ("&Required");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		requiredLabel.setLayoutData(data);
		
		argumentIsRequired = new Button(container, SWT.CHECK);
		
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentIsRequired.setLayoutData(data);
		
		
		Label defaultLabel = new Label (container, SWT.NONE);
		defaultLabel.setText ("&Default");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		defaultLabel.setLayoutData(data);
		
		argumentDefault = new Text(container, SWT.BORDER);
		argumentDefault.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentDefault.setLayoutData(data);
		
		initialize();		
		dialogChanged();
		setControl(container);
	}
	
	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	
	private void initialize() 
	{
		this.functPage = (NewCfcWizardPageThree)getPreviousPage();
		if (this.selection!=null && 
				this.selection.isEmpty()==false && 
				this.selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection)this.selection;
			if (ssel.size()>1) return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				disable();
			}
		}
	}
	
	
	private void disable()
	{
		this.argumentName.setEnabled(false);
		this.argumentDisplayName.setEnabled(false);
		this.argumentHint.setEnabled(false);
		this.argumentDefault.setEnabled(false);
		this.argumentIsRequired.setEnabled(false);
		this.argumentType.setEnabled(false);
		this.argumentList.setEnabled(false);
	}
	
	private void enable()
	{
		this.argumentName.setEnabled(true);
		this.argumentDisplayName.setEnabled(true);
		this.argumentHint.setEnabled(true);
		this.argumentDefault.setEnabled(true);
		this.argumentIsRequired.setEnabled(true);
		this.argumentType.setEnabled(true);
		this.argumentList.setEnabled(true);
	}
	
	
	public void setFunctionItems(String[] s)
	{
		this.funcList.setItems(s);
		this.funcList.select(0);
	}
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		/*if(this.functPage.hasFunctions())
		{
			funcList.setItems(this.functPage.getTagArray());
			funcList.select(0);
		}*/
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private void handleAdd() 
	{
		enable();
		CfcFunctionBean functBean = functPage.getBeanByName(funcList.getText());
		CfcArgumentBean argBean = new CfcArgumentBean();
		
		this.argumentName.setText("newArgument");
		this.argumentDisplayName.setText("newArgument");
		this.argumentHint.setText("new argument hint");
		this.argumentDefault.setText("new argument default value");
		this.argumentIsRequired.setSelection(false);
		this.argumentType.select(0);
		this.argumentList.add("newArgument");
		
		argBean.setDefaultVal("new default value");
		argBean.setDisplayName("new argument");
		argBean.setHint("new argument");
		argBean.setName("newArgument");
		argBean.setRequired(false);
		argBean.setType("any");
		
		functBean.addArgumentBean(argBean);
		
		
	//System.out.println(functBean.getDisplayName());
	//System.out.println(functBean.getName());
	}
}