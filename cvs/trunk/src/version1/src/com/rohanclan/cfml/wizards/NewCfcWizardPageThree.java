
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

public class NewCfcWizardPageThree extends WizardPage {
	private Text functionName;
	private Text functionDisplayName;
	private Combo functionReturnType;
	private Combo functionAccess;
	private Text functionHint;
	private Text functionDefault;
	private Button isOutput;
	private List functionNames;
	private Text functionRoles;
	
	private Hashtable functionBeans;

	private ISelection selection;
	
	private int functionIdx = -1;
	private boolean isSelection = false;


	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCfcWizardPageThree(ISelection selection) {
		super("wizardPage");
		setTitle("New CF Component");
		setDescription("New CF Component Function wizard.");
		this.selection = selection;
		
		
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		
		System.out.println("Creating Function Controls! ");
		
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
		propLabel.setText("&Functions");
		propLabel.setAlignment(SWT.BEGINNING);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		propLabel.setLayoutData(data);
		
		this.functionNames = new List (container, SWT.BORDER | SWT.V_SCROLL);
		// propList.setItems (new String [] {});

		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 5;
		data.widthHint = 300;
		this.functionNames.setLayoutData(data);
		this.functionNames.addSelectionListener(new SelectionListener(){
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
		
		this.functionName = new Text(container, SWT.BORDER);
		this.functionName.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionName.setLayoutData(data);
		this.functionName.addModifyListener(new ModifyListener() {
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
		
		this.functionDisplayName = new Text(container, SWT.BORDER);
		this.functionDisplayName.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionDisplayName.setLayoutData(data);
		this.functionDisplayName.addModifyListener(new ModifyListener() {
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
		
		this.functionHint = new Text(container, SWT.BORDER);
		this.functionHint.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionHint.setLayoutData(data);
		this.functionHint.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});
		
		
		Label accessLabel = new Label (container, SWT.NONE);
		accessLabel.setText ("&Access");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		accessLabel.setLayoutData(data);
		
		this.functionAccess = new Combo(container, SWT.BORDER);
		this.functionAccess.setItems(new String[]{"public", "private", "package", "remote"});
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionAccess.setLayoutData(data);
		this.functionAccess.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});
		
		
		
		Label typeLabel = new Label (container, SWT.NONE);
		typeLabel.setText ("&Return Type");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		typeLabel.setLayoutData(data);
		
		this.functionReturnType = new Combo(container, SWT.BORDER);
		this.functionReturnType.setItems(new String[]{"any", "array", "struct", "query"});
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionReturnType.setLayoutData(data);
		
		Label rolesLabel = new Label (container, SWT.NONE);
		rolesLabel.setText ("&Roles");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		rolesLabel.setLayoutData(data);
		
		this.functionRoles = new Text(container, SWT.BORDER);
		this.functionRoles.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionRoles.setLayoutData(data);
		this.functionRoles.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		});
		
		
		Label outputLabel = new Label (container, SWT.NONE);
		outputLabel.setText ("&Output");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		outputLabel.setLayoutData(data);
		
		this.isOutput = new Button(container, SWT.CHECK);
		
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.isOutput.setLayoutData(data);
		this.isOutput.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e)
			{
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
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
	
	private void initialize() {
		
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
				this.functionName.setText("");
				this.functionDisplayName.setText("");
				this.functionDisplayName.setText("");
				this.functionHint.setText("");
				disable();
			}
		}
	}
	
	private void disable()
	{
		this.functionName.setEnabled(false);
		this.functionDisplayName.setEnabled(false);
		this.functionAccess.setEnabled(false);
		this.functionDisplayName.setEnabled(false);
		this.functionHint.setEnabled(false);
		this.functionReturnType.setEnabled(false);
		this.functionNames.setEnabled(false);
		this.functionRoles.setEnabled(false);
		this.isOutput.setEnabled(false);
	}
	
	private void enable()
	{
		this.functionName.setEnabled(true);
		this.functionDisplayName.setEnabled(true);
		this.functionAccess.setEnabled(true);
		this.functionDisplayName.setEnabled(true);
		this.functionHint.setEnabled(true);
		this.functionReturnType.setEnabled(true);
		this.functionNames.setEnabled(true);
		this.functionRoles.setEnabled(true);
		this.isOutput.setEnabled(true);
	}

	private void dialogChanged() {
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private void propertyChanged() 
	{
		if(this.functionIdx != -1)
			this.functionNames.setItem(this.functionNames.getSelectionIndex(), this.functionName.getText());
		updateProperties();
		updateStatus(null);
	}
	
	private void updateProperties()
	{
		if(this.functionBeans != null && !this.isSelection)
		{
			int idx = this.functionNames.getSelectionIndex();
			CfcFunctionBean bean = (CfcFunctionBean)this.functionBeans.get(new Integer(idx));
			bean.setName(this.functionName.getText());
			bean.setDisplayName(this.functionDisplayName.getText());
			bean.setHint(this.functionHint.getText());
			bean.setReturnType(this.functionReturnType.getText());
			bean.setOutput(this.isOutput.getSelection());
			bean.setRoles(this.functionRoles.getText());
			bean.setAccess(this.functionAccess.getText());
			
			NewCfcWizardPageFour page4 = (NewCfcWizardPageFour)getNextPage();
			page4.setFunctionItems(getTagArray());
		}
	}
	
	private void handleMinus()
	{
		int i = this.functionNames.getSelectionIndex();
		this.functionNames.remove(i);
		this.isSelection = true;
		CfcFunctionBean bean = null;
		if(i-1 != 0 && i != 0)
		{
			this.functionNames.select(i-1);
			bean = (CfcFunctionBean)this.functionBeans.get(new Integer(i-1));
		}
		else
		{
			this.functionNames.select(0);
			bean = (CfcFunctionBean)this.functionBeans.get(new Integer(0));
		}
		this.functionName.setText(bean.getName());
		this.functionHint.setText(bean.getHint());
		this.functionDisplayName.setText(bean.getDisplayName());
		resortBeans(i);
		this.isSelection = false;
		this.functionIdx--;
		NewCfcWizardPageFour page4 = (NewCfcWizardPageFour)getNextPage();
		page4.setFunctionItems(getTagArray());
	}
	
	private void handleAdd() 
	{
		this.functionIdx++;
		CfcFunctionBean bean = new CfcFunctionBean();
		enable();
		if(this.functionBeans == null)
			this.functionBeans = new Hashtable();
		bean.setDisplayName("");
		bean.setName("");
		bean.setHint("");
		bean.setReturnType("");
		bean.setAccess("");
		bean.setOutput(false);
		bean.setRoles("");
		
		this.functionBeans.put(new Integer(this.functionIdx), bean);
		this.functionNames.add(bean.getName());
		this.functionNames.select(this.functionIdx);
		
		this.functionName.setText("newFunction");
		this.functionDisplayName.setText("newFunction");
		this.functionHint.setText("new function hint");
		this.functionRoles.setText("");
		this.isOutput.setSelection(false);
		this.functionAccess.select(0);
		this.functionReturnType.select(0);
		
	}
	
	private void resortBeans(int i)
	{
		int loopLen = this.functionBeans.size();
		for(int j = i; j < loopLen; j++)
		{
			if(j+1 != loopLen)
				this.functionBeans.put(new Integer(j), this.functionBeans.get(new Integer(j + 1)));
		}
		this.functionBeans.remove(new Integer(loopLen - 1));
	}
	
	private void selectionChanged()
	{
		// updateProperties();
		selectProperties();
	}
	
	private void selectProperties()
	{
		int idx = this.functionNames.getSelectionIndex();
		CfcFunctionBean bean = (CfcFunctionBean)this.functionBeans.get(new Integer(idx));
		
		this.isSelection = true;
		this.functionName.setText(bean.getName());
		this.functionHint.setText(bean.getHint());
		this.functionDisplayName.setText(bean.getDisplayName());
		this.functionAccess.select(getAccessIndex(bean.getAccess()));
		this.functionReturnType.select(getTypeIndex(bean.getReturnType()));
		this.isOutput.setSelection(bean.isOutput());
		this.functionRoles.setText(bean.getRoles());
		this.isSelection = false;
		// this.propertyTypes.select(getPropertyIndex(bean.getType()));
	}
	
	private int getAccessIndex(String s)
	{
		for(int i = 0; i < this.functionAccess.getItemCount(); i++)
		{
			
			if(this.functionAccess.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	
	private int getTypeIndex(String s)
	{
		for(int i = 0; i < this.functionReturnType.getItemCount(); i++)
		{
			
			if(this.functionReturnType.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	
	public boolean hasFunctions()
	{
		if(this.functionBeans != null && this.functionBeans.size() > 0)
			return true;
		return false;
	}
	
	public String getFunctionTags()
	{
		StringBuffer sb = new StringBuffer();
		for (Iterator iter = this.functionBeans.values().iterator(); iter.hasNext();)
		{
			CfcFunctionBean bean = (CfcFunctionBean) iter.next();
			sb.append("\n\t");
			sb.append("<cffunction name=\"" + bean.getName() + "\"");
			sb.append("\n\t\t");
			sb.append("displayname=\"" + bean.getDisplayName() + "\"");
			sb.append("\n\t\t");
			sb.append("hint=\"" + bean.getHint() + "\"");
			sb.append("\n\t\t");
			sb.append("access=\"" + bean.getAccess() + "\"");
			sb.append("\n\t\t");
			if(bean.isOutput())
				sb.append("output=\"true\"");
			else
				sb.append("output=\"false\"");
			sb.append("\n\t\t");
			sb.append("returntype=\"" + bean.getReturnType() + "\"");
			sb.append("\n\t\t");
			sb.append("roles=\"" + bean.getRoles() + "\"");
			sb.append(">");
			sb.append("\n\t\t");
			
			if(bean.getArgumentBeans().size() > 0)
			{
				for (Iterator iterator = bean.getArgumentBeans().iterator(); iterator.hasNext();)
				{
						
					CfcArgumentBean argBean = (CfcArgumentBean)iterator.next();
					sb.append("<cfargument name=\"");
					sb.append(argBean.getName() + "\"");
					sb.append("\n\t\t\t");
					sb.append("displayName=\"");
					sb.append(argBean.getDisplayName() + "\"");
					sb.append("\n\t\t\t");
					sb.append("type=\"");
					sb.append(argBean.getType() + "\"");
					sb.append("\n\t\t\t");
					sb.append("hint=\"");
					sb.append(argBean.getHint() + "\"");
					sb.append("\n\t\t\t");
					sb.append("default=\"");
					sb.append(argBean.getDefaultVal() + "\"");
					sb.append("\n\t\t\t");
					sb.append("required=\"");
					if(argBean.isRequired())
						sb.append("true" + "\"");
					else
						sb.append("false" + "\"");
					sb.append(" />");
					sb.append("\n\t\t");
				}
			}
				
			
			sb.append("<!--- ToDo: Implement Method --->");
			sb.append("\n\t\t");
			sb.append("<cfreturn />");
			sb.append("\n");
			sb.append("\t</cffunction>\n");
			
		}
		return sb.toString();
	}
	
	public String[] getTagArray()
	{
		StringBuffer sb = new StringBuffer();
		String[] s = new String[this.functionBeans.size()];
		int counter = 0;
		for (Iterator iter = this.functionBeans.values().iterator(); iter.hasNext();)
		{
			CfcFunctionBean bean = (CfcFunctionBean) iter.next();
			s[counter] = bean.getName();
			counter++;			
		}
		return s;
	}
	
	public CfcFunctionBean getBeanByName(String s)
	{
		
		for (Iterator iter = this.functionBeans.values().iterator(); iter.hasNext();)
		{
			CfcFunctionBean bean = (CfcFunctionBean) iter.next();
			if(bean.getName().equalsIgnoreCase(s))
				return bean;
		}
		return null;
	}
	
}