
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
package com.rohanclan.cfml.wizards.cfcwizard;

//import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.Value;
import com.rohanclan.cfml.editors.CFSyntaxDictionary;

/**
 * This page handles adding Functions to a CFC (note arguments are done on the next
 * page)
 * 
 * The "New" wizard page allows setting the container for the new file as well as 
 * the file name. The page will only accept file name without the extension OR
 * with the extension that matches the expected one (cfm).
 * 
 * @author qc
 */
public class NewCFCWizardFunctions extends WizardPage {
	private Text functionName;
	private Text functionDisplayName;
	private Combo functionReturnType;
	private Combo functionAccess;
	private Text functionHint;
	private Text functionDefault;
	private Button isOutput;
	private List functionNames;
	private Text functionRoles;
	
	/** the currently loaded (editing function bean) */
	private static CFCFunctionBean currentbean;
	
	/** all the function beans */
	private LinkedHashMap functionBeans;

	private ISelection selection;
	
	/** primary key for our functions */
	private int functionIdx = -1;
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCFCWizardFunctions(ISelection selection) {
		super("wizardPage");
		functionBeans = new LinkedHashMap();
		setTitle("New CF Component");
		setDescription("New CF Component Function wizard.");
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
		
		///////////////////////////////////////////////////////////////// ADD BUTTON
		Button plus = new Button(buttons, SWT.PUSH);
		plus.setText(" + ");
		//plus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		plus.setLayoutData(data);
		/* plus.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAdd();
			}
		}); */
		plus.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {;}
			public void mouseDown(MouseEvent e) {
				handleAdd();
			}
			public void mouseUp(MouseEvent e) {;}
		});
		
		////////////////////////////////////////////////////////////// REMOVE BUTTON
		Button minus = new Button(buttons, SWT.PUSH);
		minus.setText(" - ");
		//minus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		minus.setLayoutData(data);
		/* minus.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleMinus();
			}
		}); */
		minus.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {;}
			public void mouseDown(MouseEvent e) {
				handleMinus();
			}
			public void mouseUp(MouseEvent e) {;}
		});
		
		///////////////////////////////////////////////////////// FUNCTION NAME LIST
		Label propLabel = new Label(container, SWT.NULL);
		propLabel.setText("&Functions");
		propLabel.setAlignment(SWT.BEGINNING);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		propLabel.setLayoutData(data);
		
		functionNames = new List (container, SWT.BORDER | SWT.V_SCROLL);
		// propList.setItems (new String [] {});

		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 5;
		data.widthHint = 300;
		functionNames.setLayoutData(data);
		/* functionNames.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e)
			{		
				selectionChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				selectionChanged();
			}
		}); */
		functionNames.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				selectionChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				//selectionChanged();
			}
		});
		
		/////////////////////////////////////////////////////////////// NAME TEXTBOX
		Label nameLabel = new Label (container, SWT.NONE);
		nameLabel.setText ("&Name");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		nameLabel.setLayoutData(data);
		
		this.functionName = new Text(container, SWT.BORDER);
		functionName.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		functionName.setLayoutData(data);
		/* this.functionName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		}); */
		//with the name we are going to update the list a lot so the data stays
		//constant...
		functionName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePropertyNameInList();
			}
		});
		//but only update the bean we we lose focus
		functionName.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	
				propertyChanged();
			}
			public void focusLost(FocusEvent e){
				propertyChanged();	
			}
		});

		//////////////////////////////////////////////////////////// DISPLAY TEXTBOX		
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
		functionDisplayName.setLayoutData(data);
		/* functionDisplayName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		}); */
		functionDisplayName.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		/////////////////////////////////////////////////////////////// HINT TEXTBOX	
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
		functionHint.setLayoutData(data);
		/* functionHint.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		}); */
		functionHint.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		//////////////////////////////////////////////////////////// ACCESS DROPDOWN	
		Label accessLabel = new Label (container, SWT.NONE);
		accessLabel.setText ("&Access");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		accessLabel.setLayoutData(data);
		
		this.functionAccess = new Combo(container, SWT.BORDER);
		this.functionAccess.setItems(
			new String[]{"public", "private", "package", "remote"}
		);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		functionAccess.setLayoutData(data);
		/* functionAccess.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		}); */
		functionAccess.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		functionAccess.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		
		//////////////////////////////////////////////////////// RETURNTYPE DROPDOWN
		Label typeLabel = new Label (container, SWT.NONE);
		typeLabel.setText ("&Return Type");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		typeLabel.setLayoutData(data);
		
		functionReturnType = new Combo(container, SWT.BORDER);
		
		//get the proper return types
		CFSyntaxDictionary cfmldic = (CFSyntaxDictionary)DictionaryManager.getDictionary(
			DictionaryManager.CFDIC
		);
		TreeSet cfitems = new TreeSet(
			cfmldic.getFilteredAttributeValues("cffunction", "returntype", "")
		);
		Iterator i = cfitems.iterator();
		String[] str = new String[cfitems.size()];
		int q=0;
		while(i.hasNext()){
			str[q++] = ((Value)i.next()).getValue();
		}
		functionReturnType.setItems(str);
		/* functionReturnType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		}); */
		functionReturnType.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		functionReturnType.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		this.functionReturnType.setLayoutData(data);
		
		/////////////////////////////////////////////////////////////// ROLL TEXTBOX
		Label rolesLabel = new Label (container, SWT.NONE);
		rolesLabel.setText ("&Roles");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		rolesLabel.setLayoutData(data);
		
		functionRoles = new Text(container, SWT.BORDER);
		functionRoles.setText ("");
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		functionRoles.setLayoutData(data);
		/* functionRoles.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				propertyChanged();
			}
		}); */
		functionRoles.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		//////////////////////////////////////////////////////////// OUTPUT CHECKBOX		
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
		isOutput.setLayoutData(data);
		isOutput.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e){
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				propertyChanged();
			}
		});
				
		dialogChanged();
		setControl(container);
	}
	
	/**
	 * disables all the controls
	 */
	private void disable()
	{
		setControlsEnabled(false);
	}
	
	/**
	 * enables all the controls
	 */
	private void enable()
	{
		setControlsEnabled(true);
	}
	
	/**
	 * Turn on and off the controls on this page
	 * @param to
	 */
	public void setControlsEnabled(boolean to){
		functionName.setEnabled(to);
		functionDisplayName.setEnabled(to);
		functionAccess.setEnabled(to);
		functionDisplayName.setEnabled(to);
		functionHint.setEnabled(to);
		functionReturnType.setEnabled(to);
		functionNames.setEnabled(to);
		functionRoles.setEnabled(to);
		isOutput.setEnabled(to);
	}
	
	private void dialogChanged() {
		updateStatus(null);
	}
	
	/**
	 * shows a little message on the wizards status bar
	 * @param message
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	
	/**
	 * Saves the beans items into the currently selected bean
	 */
	private void propertyChanged() 
	{
		System.err.println("I was asked to update the beans props");
		
		updateProperties();
		updateStatus(null);
		
		/*
		if(functionIdx != -1)
			functionNames.setItem(
				functionNames.getSelectionIndex(), functionName.getText()
			);
		
		updateProperties();
		updateStatus(null);
		*/
	}
	
	/**
	 * When the name of the property changes, the listing name should change too.
	 * This updates the current name to the bean name. should be called on the name
	 * change event only.
	 */
	private void updatePropertyNameInList(){
		//update the list text, then the bean
		int idx = functionNames.getSelectionIndex();
		functionNames.setItem(idx, functionName.getText());
	}
	
	private void updateProperties()
	{	
		if(currentbean != null)
		{	
			currentbean.setName(functionName.getText());
			currentbean.setDisplayName(functionDisplayName.getText());
			currentbean.setHint(functionHint.getText());
			currentbean.setReturnType(functionReturnType.getText());
			currentbean.setOutput(isOutput.getSelection());
			currentbean.setRoles(functionRoles.getText());
			currentbean.setAccess(functionAccess.getText());
			
			NewCFCWizardArguments page4 = (NewCFCWizardArguments)getNextPage();
			
			page4.setFunctionBeans(functionBeans);
			page4.setFunctionItems(getTagArray());
			
			
		}else{
			System.err.println("Current bean is null");
		}
	}
	
	/**
	 * Remove a function
	 */
	private void handleMinus()
	{
		//this is the bean we are killing...
		int i = functionNames.getSelectionIndex();
	
		//remove the index
		functionNames.remove(i);
			
		//try to set the current bean to the last one or 0
		if(i-1 != 0 && i != 0) {
			functionNames.select(i-1);
			currentbean = (CFCFunctionBean)functionBeans.get(new Integer(i-1));
		} else {
			functionNames.select(0);
			currentbean = (CFCFunctionBean)functionBeans.get(new Integer(0));
		}
		
		resortBeans(i);
		functionIdx--;
		
		loadBeanToEdit(currentbean);
		
		NewCFCWizardArguments page4 = (NewCFCWizardArguments)getNextPage();
		page4.setFunctionItems(getTagArray());
	}
	
	/**
	 * Add a function
	 */
	private void handleAdd() 
	{
		this.functionIdx++;
		CFCFunctionBean bean = new CFCFunctionBean();
		
		enable();
		
		bean.setName("newFunction");
		
		functionBeans.put(new Integer(this.functionIdx), bean);
		
		functionNames.add(bean.getName());
		functionNames.select(functionIdx);
		
		loadBeanToEdit(bean);
		
		//make the current bean this bean
		currentbean = bean;
		
	}
		
	/**
	 * Load a bean into the text boxes for editing
	 * 
	 * @param bean
	 */
	private void loadBeanToEdit(CFCFunctionBean bean){
		//setup the gui to be able to edit the beans current values
		functionName.setText(bean.getName());
		functionDisplayName.setText(bean.getDisplayName());
		functionHint.setText(bean.getHint());
		functionRoles.setText(bean.getRoles());
		isOutput.setSelection(bean.isOutput());
		functionAccess.select(getAccessIndex(bean.getAccess()));
		functionReturnType.select(getTypeIndex(bean.getReturnType()));
	}
	
	/**
	 * When a property is removed, if it was removed from, say, the middle the rest 
	 * of the list has to be reindex to reflect the change... is it really this
	 * hard?
	 * 
	 * @param i
	 */
	private void resortBeans(int i)
	{
		int loopLen = functionBeans.size();
		for(int j = i; j < loopLen; j++)
		{
			if(j+1 != loopLen)
				functionBeans.put(new Integer(j), functionBeans.get(new Integer(j + 1)));
		}
		functionBeans.remove(new Integer(loopLen - 1));
	}
	
	/**
	 * When a selection has changed call the updating function
	 */
	private void selectionChanged()
	{
		//updateProperties();
		//selectProperties();
		
		int idx = functionNames.getSelectionIndex();
		
		System.err.println("loading bean... " + idx);
		System.err.println("current: " + currentbean);
		
		if(idx > -1)
		{
			currentbean = (CFCFunctionBean)functionBeans.get(new Integer(idx));
			System.err.println("new current: " + currentbean);
			
			loadBeanToEdit(currentbean);
		}
	}
	
	/**
	 * Looks up the index of "s" in the list of "access" choices
	 */
	private int getAccessIndex(String s)
	{
		int fac = functionAccess.getItemCount();
		
		for(int i = 0; i < fac; i++)
		{		
			if(functionAccess.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	
	/**
	 * Looks up the index of "s" in the list of "returntype" choices
	 */
	private int getTypeIndex(String s)
	{
		int frtc = functionReturnType.getItemCount();
		for(int i = 0; i < frtc; i++)
		{	
			if(functionReturnType.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	
	/**
	 * Does this page have any functions defined
	 * 
	 * @return
	 */
	public boolean hasFunctions()
	{
		if(functionBeans != null && functionBeans.size() > 0) return true;
		return false;
	}
	
	/**
	 * Gets the CFML code version of the function
	 * NOTE: this requires the user to have gone to the next page to add function
	 * arguments it seems...
	 * 
	 * @return
	 */
	public String getFunctionTags()
	{
		StringBuffer sb = new StringBuffer();
		
		for(Iterator iter = functionBeans.values().iterator(); iter.hasNext();)
		{
			CFCFunctionBean bean = (CFCFunctionBean)iter.next();
			
			sb.append("\n\t");
			sb.append("<cffunction name=\"" + bean.getName() + "\"");
			
			if(bean.getDisplayName().length() > 0)
			sb.append(" displayname=\"" + bean.getDisplayName() + "\"");
			
			if(bean.getHint().length() > 0)
			sb.append(" hint=\"" + bean.getHint() + "\"");
			
			if(bean.getAccess().length() > 0)
			sb.append(" access=\"" + bean.getAccess() + "\"");
			
			
			if(bean.isOutput())
				sb.append(" output=\"true\"");
			else
				sb.append(" output=\"false\"");
			
			if(bean.getReturnType().length() > 0)
			sb.append(" returntype=\"" + bean.getReturnType() + "\"");
			
			if(bean.getRoles().length() > 0)
			sb.append(" roles=\"" + bean.getRoles() + "\"");
			
			sb.append(">");
			
			//now see if there are arguments to this function and write them out
			//if need be
			/*
			if(bean.getArgumentBeans().size() > 0)
			{
				for(Iterator iterator = bean.getArgumentBeans().keySet().iterator(); iterator.hasNext();)
				{	
					CFCArgumentBean argBean = (CFCArgumentBean)bean.getArgumentBeans().get(iterator.next());
					//CFCArgumentBean argBean = (CFCArgumentBean)iterator.next();
					
					sb.append("\n\t\t");
					sb.append("<cfargument name=\"");
					sb.append(argBean.getName() + "\"");
					
					if(argBean.getDisplayName().length() > 0) {
						sb.append(" displayName=\"");
						sb.append(argBean.getDisplayName() + "\"");
					}
					
					if(argBean.getType().length() > 0){
						sb.append(" type=\"");
						sb.append(argBean.getType() + "\"");
					}
					
					if(argBean.getHint().length() > 0){
						sb.append(" hint=\"");
						sb.append(argBean.getHint() + "\"");
					}
					
					if(argBean.getDefaultVal().length() > 0){
						sb.append(" default=\"");
						sb.append(argBean.getDefaultVal() + "\"");
					}
					
					sb.append(" required=\"");
					if(argBean.isRequired())
						sb.append("true" + "\"");
					else
						sb.append("false" + "\"");
					
					sb.append(" />");
				}
			}
			*/	
			
			sb.append("\n\t\t");
			sb.append("<!--- TODO: Implement Method --->");
			sb.append("\n\t\t");
			
			sb.append("<cfreturn />");
			
			sb.append("\n");
			sb.append("\t</cffunction>\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Gets all the functions names, mostly to set the next pages dropdown with the
	 * functions so they can be selected
	 * @return
	 */
	public String[] getTagArray()
	{
		String[] s = new String[functionBeans.size()];
		
		int counter = 0;
		for(Iterator iter = functionBeans.values().iterator(); iter.hasNext();)
		{
			CFCFunctionBean bean = (CFCFunctionBean)iter.next();
			s[counter] = bean.getName();
			counter++;			
		}
		return s;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	/* public CFCFunctionBean getBeanByName(String s)
	{
		for(Iterator iter = functionBeans.values().iterator(); iter.hasNext();)
		{
			CFCFunctionBean bean = (CFCFunctionBean) iter.next();
			if(bean.getName().equalsIgnoreCase(s))
				return bean;
		}
		return null;
	} */
}