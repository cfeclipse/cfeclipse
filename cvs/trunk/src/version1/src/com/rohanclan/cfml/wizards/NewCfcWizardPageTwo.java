
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


import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

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

public class NewCfcWizardPageTwo extends WizardPage {
	private Text propertyName;
	private Text propertyDisplayName;
	private Combo propertyTypes;
	private Text propertyHint;
	private Text propertyDefault;
	
	private List propertyNames;
	
	private Hashtable propertyBeans;

	private ISelection selection;
	
	private int propertyIdx = -1;
	
	private boolean isSelection = false;


	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCfcWizardPageTwo(ISelection selection) {
		super("wizardPage");
		setTitle("New CF Component");
		setDescription("New CF Component Properties wizard.");
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
		
		Label blankLabel = new Label(container, SWT.NULL);
		blankLabel.setText("");
		GridData data = new GridData();
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
		minus.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleMinus();
			}
		});
		
		
		Label propLabel = new Label(container, SWT.NULL);
		propLabel.setText("&Properties");
		propLabel.setAlignment(SWT.BEGINNING);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		propLabel.setLayoutData(data);
		
		this.propertyNames = new List (container, SWT.BORDER | SWT.V_SCROLL);
		
		// this.propertyNames.setItems (new String [] {});

		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 5;
		data.widthHint = 300;
		this.propertyNames.setLayoutData(data);	
		this.propertyNames.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e)
			{
				
				selectionChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				selectionChanged();
			}
		});
		
		
		Label nameLabel = new Label (container, SWT.NONE);
		nameLabel.setText ("&Name");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		nameLabel.setLayoutData(data);
		
		propertyName = new Text(container, SWT.BORDER);
		propertyName.setText ("");
		propertyName.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		propertyName.setLayoutData(data);
		propertyName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});

		Label displayLabel = new Label (container, SWT.NONE);
		displayLabel.setText ("&Display Name");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		displayLabel.setLayoutData(data);
		
		propertyDisplayName = new Text(container, SWT.BORDER);
		propertyDisplayName.setText ("");
		propertyDisplayName.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		propertyDisplayName.setLayoutData(data);
		propertyDisplayName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});
		
		Label hintLabel = new Label (container, SWT.NONE);
		hintLabel.setText ("&Hint");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		hintLabel.setLayoutData(data);
		
		propertyHint = new Text(container, SWT.BORDER);
		propertyHint.setText ("");
		propertyHint.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		propertyHint.setLayoutData(data);
		propertyHint.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});
		
		Label defaultLabel = new Label (container, SWT.NONE);
		defaultLabel.setText ("&Default");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		defaultLabel.setLayoutData(data);
		
		propertyDefault = new Text(container, SWT.BORDER);
		propertyDefault.setText ("");
		propertyDefault.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		propertyDefault.setLayoutData(data);
		propertyDefault.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});
		
		Label typeLabel = new Label (container, SWT.NONE | SWT.READ_ONLY);
		typeLabel.setText ("&Type");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		typeLabel.setLayoutData(data);
		
		propertyTypes = new Combo(container, SWT.BORDER);
		propertyTypes.setItems(new String[]{"any", "array", "struct", "query"});
		propertyTypes.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		propertyTypes.setLayoutData(data);
		propertyTypes.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
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
	
	private void initialize()
	{
		if (this.selection!=null && 
				this.selection.isEmpty()==false && 
				this.selection instanceof IStructuredSelection) 
		{
			IStructuredSelection ssel = (IStructuredSelection)this.selection;
			if (ssel.size()>1) return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) 
			{
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				this.propertyName.setText("");
				this.propertyDisplayName.setText("");
				this.propertyHint.setText("");
				disable();
			}
		}
	}
	
	
	private void disable()
	{
		this.propertyName.setEnabled(false);
		this.propertyDefault.setEnabled(false);
		this.propertyDisplayName.setEnabled(false);
		this.propertyHint.setEnabled(false);
		this.propertyTypes.setEnabled(false);
	}
	
	private void enable()
	{
		this.propertyName.setEnabled(true);
		this.propertyDefault.setEnabled(true);
		this.propertyDisplayName.setEnabled(true);
		this.propertyHint.setEnabled(true);
		this.propertyTypes.setEnabled(true);
	}
	
	private void dialogChanged()
	{		
		updateStatus(null);
	}
	
	
	private int getTypeIndex(String s)
	{
		for(int i = 0; i < this.propertyTypes.getItemCount(); i++)
		{
			
			if(this.propertyTypes.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private void selectionChanged()
	{
		// updateProperties();
		selectProperties();
	}
	
	private void selectProperties()
	{
		int idx = this.propertyNames.getSelectionIndex();
		CfcPropertyBean bean = (CfcPropertyBean)this.propertyBeans.get(new Integer(idx));
		
		this.isSelection = true;
		this.propertyName.setText(bean.getName());
		this.propertyHint.setText(bean.getHint());
		this.propertyDisplayName.setText(bean.getDisplayName());
		this.propertyDefault.setText(bean.getDefaultVal());
		this.propertyTypes.select(getTypeIndex(bean.getType()));
		this.isSelection = false;
	}
	
	private void propertyChanged() 
	{
		if(this.propertyIdx != -1)
			this.propertyNames.setItem(this.propertyNames.getSelectionIndex(), this.propertyName.getText());
		updateProperties();
		updateStatus(null);
	}
	
	private void updateProperties()
	{
		if(this.propertyBeans != null && !this.isSelection)
		{
			int idx = this.propertyNames.getSelectionIndex();
			CfcPropertyBean bean = (CfcPropertyBean)this.propertyBeans.get(new Integer(idx));
			bean.setName(this.propertyName.getText());
			bean.setDisplayName(this.propertyDisplayName.getText());
			bean.setHint(this.propertyHint.getText());
			bean.setType(this.propertyTypes.getText());
			bean.setDefaultVal(this.propertyDefault.getText());
		}
	}
	
	private void handleMinus()
	{
		int i = this.propertyNames.getSelectionIndex();
		this.propertyNames.remove(i);
		this.isSelection = true;
		CfcPropertyBean bean = null;
		if(i-1 != 0 && i != 0)
		{
			this.propertyNames.select(i-1);
			bean = (CfcPropertyBean)this.propertyBeans.get(new Integer(i-1));
		}
		else
		{
			this.propertyNames.select(0);
			bean = (CfcPropertyBean)this.propertyBeans.get(new Integer(0));
		}
		this.propertyName.setText(bean.getName());
		this.propertyHint.setText(bean.getHint());
		this.propertyDisplayName.setText(bean.getDisplayName());
		this.propertyDefault.setText(bean.getDefaultVal());
		resortBeans(i);
		this.isSelection = false;
		this.propertyIdx--;
	}
	
	private void handleAdd() 
	{
		this.propertyIdx++;
		CfcPropertyBean bean = new CfcPropertyBean();
		enable();
		if(this.propertyBeans == null)
			this.propertyBeans = new Hashtable();
		bean.setDisplayName("");
		bean.setName("");
		bean.setHint("");
		bean.setType("");
		bean.setDefaultVal("");
		bean.setType("");
		
		this.propertyBeans.put(new Integer(this.propertyIdx), bean);
		this.propertyNames.add(bean.getName());
		this.propertyNames.select(this.propertyIdx);
		this.propertyName.setText("newProperty");
		this.propertyDisplayName.setText("newProperty");
		this.propertyHint.setText("new property hint");
		this.propertyDefault.setText("new default value");
		this.propertyTypes.select(0);
	}
	
	public Collection getProperties()
	{
		return this.propertyBeans.values();
	}
	
	public String getPropertiesAsTags()
	{
		StringBuffer sb = new StringBuffer();
		for (Iterator iter = this.propertyBeans.values().iterator(); iter.hasNext();)
		{
			CfcPropertyBean bean = (CfcPropertyBean) iter.next();
			sb.append("\t");
			sb.append("<cfproperty name=\"");
			sb.append(bean.getName() + "\"");
			sb.append(" displayname=\"" + bean.getDisplayName() + "\"");
			sb.append(" hint=\"" + bean.getHint() + "\"");
			sb.append(" type=\"" + bean.getType() + "\"");
			sb.append(" default=\"" + bean.getDefaultVal() + "\"");
			sb.append(" />");
			sb.append("\n");
			
		}
		return sb.toString();
	}
	
	public boolean hasProperties()
	{
		if(this.propertyBeans != null && this.propertyBeans.size() > 0)
			return true;
		else
			return false;
	}
	
	private void resortBeans(int i)
	{
		System.out.println(i);
		int loopLen = this.propertyBeans.size();
		System.out.println(loopLen);
		for(int j = i; j < loopLen; j++)
		{
			System.out.println(j);
			if(j+1 != loopLen)
				this.propertyBeans.put(new Integer(j), this.propertyBeans.get(new Integer(j + 1)));
		}
		this.propertyBeans.remove(new Integer(loopLen - 1));
	}
	
}