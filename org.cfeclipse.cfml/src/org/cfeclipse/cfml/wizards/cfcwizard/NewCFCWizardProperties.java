
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


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Value;
import org.cfeclipse.cfml.editors.CFSyntaxDictionary;
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


/**
 * This is the Properties of a CFC management page in the CFC wizard
 * 
 * The "New" wizard page allows setting the container for the new file as well as 
 * the file name. The page will only accept file name without the extension OR
 * with the extension that matches the expected one (cfc).
 */
public class NewCFCWizardProperties extends WizardPage {
	
	private Text propertyName;
	private Text propertyDisplayName;
	private Combo propertyTypes;
	private Text propertyHint;
	private Text propertyDefault;
	private Button propertyWriteGetter;
	private Button propertyWriteSetter;
	private Combo getterAccessValues;
	private Combo setterAccessValues;
	
	/** the current list of properties - this is a list control and used like an 
	 * index to load up properties
	 */
	private List propertyNames;
	
	/** this is the hash table of all the beans */
	LinkedHashMap propertyBeans;

	private ISelection selection;
	
	/** keeps track of what property we are adding (like a primary key) */
	private int propertyIdx = -1;
	
	
	/** the currently loaded (editing prop bean) */
	private static CFCPropertyBean currentbean;
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCFCWizardProperties(ISelection selection) {
		super("wizardPage");
		propertyBeans = new LinkedHashMap();
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
		
		////////////////////////////////////////////////////////////////////ADD PROP
		Button plus = new Button(buttons, SWT.PUSH);
		plus.setText(" + ");
		//plus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		plus.setLayoutData(data);
		plus.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {;}
			public void mouseDown(MouseEvent e) {
				handleAdd();
			}
			public void mouseUp(MouseEvent e) {;}
		});
		
		/////////////////////////////////////////////////////////////////REMOVE PROP
		Button minus = new Button(buttons, SWT.PUSH);
		minus.setText(" - ");
		//minus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		minus.setLayoutData(data);
		minus.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {;}
			public void mouseDown(MouseEvent e) {
				handleMinus();
			}
			public void mouseUp(MouseEvent e) {;}
		});
		
		////////////////////////////////////////////////////////////////// PROP LIST
		Label propLabel = new Label(container, SWT.NULL);
		propLabel.setText("&Properties");
		propLabel.setAlignment(SWT.BEGINNING);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.BEGINNING;
		propLabel.setLayoutData(data);
		
		propertyNames = new List(container, SWT.BORDER | SWT.V_SCROLL);

		data = new GridData();
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 5;
		data.widthHint = 300;
		propertyNames.setLayoutData(data);		
		//set the currently focused on ID
		propertyNames.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				selectionChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				//selectionChanged();
			}
		});
		
		////////////////////////////////////////////////////////////////// NAME TEXT
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
		//with the name we are going to update the list a lot so the data stays
		//constant...
		propertyName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePropertyNameInList();
				checkForDuplicateProperty();
			}
		});
		//but only update the bean we we lose focus
		propertyName.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	
				propertyChanged();
			}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});

		///////////////////////////////////////////////////////////DISPLAY NAME TEXT
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
		propertyDisplayName.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		///////////////////////////////////////////////////////////////////HINT TEXT
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
		propertyHint.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		////////////////////////////////////////////////////////////////DEFAULT TEXT
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
		propertyDefault.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		///////////////////////////////////////////////////////////// TYPE SELECTION
		Label typeLabel = new Label (container, SWT.NONE | SWT.READ_ONLY);
		typeLabel.setText ("&Type");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		typeLabel.setLayoutData(data);
		
		propertyTypes = new Combo(container, SWT.BORDER);
		
		//get the proper properties types
		CFSyntaxDictionary cfmldic = (CFSyntaxDictionary)DictionaryManager.getDictionary(
			DictionaryManager.CFDIC
		);
		TreeSet cftypes = new TreeSet(cfmldic.getFilteredAttributeValues("cfproperty", "type", ""));
		Iterator i = cftypes.iterator();
		String[] str = new String[cftypes.size()];
		int q=0;
		while(i.hasNext()){
			str[q++] = ((Value)i.next()).getValue();
		}
		
		propertyTypes.setItems(str);
		
		propertyTypes.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		propertyTypes.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		propertyTypes.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		propertyTypes.setLayoutData(data);
				
		///////////////////////////////////////////////////////////// WRITE GETTER
		Label blankLabel2 = new Label(container, SWT.NULL);
		blankLabel2.setText("");
		data = new GridData();
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		blankLabel2.setLayoutData(data);
		
		propertyWriteGetter = new Button(container, SWT.CHECK);
		propertyWriteGetter.setText("Write &Getter");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		propertyWriteGetter.setLayoutData(data);
		
		propertyWriteGetter.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    setGetterAccessEnabled(propertyWriteGetter.getSelection());
			    propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {;}
		});
		
		Label getterAccessLabel = new Label (container, SWT.NONE | SWT.READ_ONLY);
		getterAccessLabel.setText ("&access:");
		data = new GridData();
		data.horizontalIndent = 5;
		data.verticalAlignment = GridData.BEGINNING;
		data.horizontalAlignment = GridData.END;
		getterAccessLabel.setLayoutData(data);
		
		getterAccessValues = new Combo(container, SWT.BORDER);
		
		//get the proper access types
		TreeSet accessTypes = new TreeSet(cfmldic.getFilteredAttributeValues("cffunction", "access", ""));
		i = accessTypes.iterator();
		str = new String[accessTypes.size()];
		q=0;
		while(i.hasNext()){
			str[q++] = ((Value)i.next()).getValue();
		}
		
		getterAccessValues.setItems(str);
		
		getterAccessValues.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		getterAccessValues.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		getterAccessValues.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		getterAccessValues.setLayoutData(data);
				
		///////////////////////////////////////////////////////////// WRITE SETTER
		Label blankLabel3 = new Label(container, SWT.NULL);
		blankLabel3.setText("");
		data = new GridData();
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		blankLabel3.setLayoutData(data);
		
		propertyWriteSetter = new Button(container, SWT.CHECK);
		propertyWriteSetter.setText("Write &Setter");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		propertyWriteSetter.setLayoutData(data);
		
		propertyWriteSetter.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    setSetterAccessEnabled(propertyWriteSetter.getSelection());
			    propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e) {;}
		});
		
		Label setterAccessLabel = new Label (container, SWT.NONE | SWT.READ_ONLY);
		setterAccessLabel.setText ("a&ccess:");
		data = new GridData();
		data.horizontalIndent = 5;
		data.verticalAlignment = GridData.BEGINNING;
		data.horizontalAlignment = GridData.END;
		setterAccessLabel.setLayoutData(data);
		
		setterAccessValues = new Combo(container, SWT.BORDER);
		
		//get the proper access types
		i = accessTypes.iterator();
		str = new String[accessTypes.size()];
		q=0;
		while(i.hasNext()){
			str[q++] = ((Value)i.next()).getValue();
		}
		
		setterAccessValues.setItems(str);
		
		setterAccessValues.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		setterAccessValues.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		setterAccessValues.setEnabled(false);
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		setterAccessValues.setLayoutData(data);
				
		dialogChanged();
		setControl(container);
	}
	
	/**
	 * disable all the controls
	 */
	private void disable()
	{
		setControlsEnabled(false);
	}
	
	/**
	 * Enable all the controls
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
		propertyName.setEnabled(to);
		propertyDefault.setEnabled(to);
		propertyDisplayName.setEnabled(to);
		propertyHint.setEnabled(to);
		propertyTypes.setEnabled(to);
	}
	
	
	/**
	 * Turn on and off the getter access values on this page
	 * @param to
	 */
	public void setGetterAccessEnabled(boolean to){
		getterAccessValues.setEnabled(to);
	}
	
	
	/**
	 * Turn on and off the setter access values on this page
	 * @param to
	 */
	public void setSetterAccessEnabled(boolean to){
		setterAccessValues.setEnabled(to);
	}
	
	
	private void dialogChanged()
	{		
		updateStatus(null);
		checkForDuplicateProperty();
	}
	
	/**
	 * Uses the string of a type "string", "any", "numeric", etc and finds the 
	 * proper index in the type drop down
	 * @param s
	 * @return
	 */
	private int getTypeIndex(String s)
	{
		int pic = propertyTypes.getItemCount();
		
		for(int i = 0; i < pic; i++)
		{
			if(propertyTypes.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	
	/**
	 * Uses the string of a type "public", "package", "private", etc and finds the 
	 * proper index in the type drop down
	 * @param s
	 * @return
	 */
	private int getAccessIndex(String s)
	{
		int pic = getterAccessValues.getItemCount();
		
		for(int i = 0; i < pic; i++)
		{
			if(getterAccessValues.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
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
	 * called when a property is selected (i.e. this needs to set the local primary
	 * key for the current property), and loads a bean into the current bean
	 */
	private void selectionChanged() {
		int idx = propertyNames.getSelectionIndex();
		
		System.err.println("loading bean... " + idx);
		System.err.println("current: " + currentbean);
		
		if(idx > -1)
		{
			currentbean = (CFCPropertyBean)propertyBeans.get(new Integer(idx));
			System.err.println("new current: " + currentbean);
			
			loadBeanToEdit(currentbean);
		}
	}
	
	/**
	 * Saves the beans items into the currently selected bean
	 */	
	private void propertyChanged() 
	{
		//System.err.println("I was asked to update the beans props");
		
		updateProperties();
		updateStatus(null);
		checkForDuplicateProperty();
	}
	
	/**
	 * When the name of the property changes, the listing name should change too.
	 * This updates the current name to the bean name. should be called on the name
	 * change event only.
	 */
	private void updatePropertyNameInList(){
		//update the list text, then the bean
		int idx = this.propertyNames.getSelectionIndex();
		propertyNames.setItem(idx, propertyName.getText());
	}
	
	
	/**
	 * "saves" the beans set items
	 */
	private void updateProperties()
	{
		if(currentbean != null)
		{	
			currentbean.setName(propertyName.getText());
			currentbean.setDisplayName(propertyDisplayName.getText());
			currentbean.setHint(propertyHint.getText());
			currentbean.setType(propertyTypes.getText());
			currentbean.setDefaultVal(propertyDefault.getText());
			currentbean.setShouldWriteGetter(propertyWriteGetter.getSelection());
			currentbean.setGetterAccess(getterAccessValues.getText());
			currentbean.setShouldWriteSetter(propertyWriteSetter.getSelection());
			currentbean.setSetterAccess(setterAccessValues.getText());
		}else{
			System.err.println("Current bean is null");
		}
	}
	
	/**
	 * Remove a property from the list, and if needed load one of the other beans
	 * behind it
	 */
	private void handleMinus()
	{
		//this is the bean we are killing...
		int i = propertyNames.getSelectionIndex();
	
		//remove the index
		propertyNames.remove(i);
			
		//try to set the current bean to the last one or 0
		if(i-1 != 0 && i != 0) {
			propertyNames.select(i-1);
			currentbean = (CFCPropertyBean)propertyBeans.get(new Integer(i-1));
		} else {
			propertyNames.select(0);
			currentbean = (CFCPropertyBean)propertyBeans.get(new Integer(0));
		}
		
		resortBeans(i);
		propertyIdx--;
		
		loadBeanToEdit(currentbean);
	}
	
	/**
	 * Gets the GUIs data into the beans structure
	 */
	private void handleAdd() 
	{
		propertyIdx++;
		
		CFCPropertyBean bean = new CFCPropertyBean();
		
		enable();		
		bean.setName("newProperty");
		
		//save this bean with an index so we can get it again
		propertyBeans.put(new Integer(propertyIdx), bean);
		
		//set up the index
		propertyNames.add(bean.getName());
		propertyNames.select(propertyIdx);
		
		loadBeanToEdit(bean);
		
		//make the current bean this bean
		currentbean = bean;
	}
	
	/**
	 * Load a bean into the text boxes for editing
	 * 
	 * @param bean
	 */
	private void loadBeanToEdit(CFCPropertyBean bean){
		//setup the gui to be able to edit the beans current values
		propertyName.setText(bean.getName());
		propertyDisplayName.setText(bean.getDisplayName());
		propertyHint.setText(bean.getHint());
		propertyDefault.setText(bean.getDefaultVal());
		propertyTypes.select(getTypeIndex(bean.getType()));
		propertyWriteGetter.setSelection(bean.shouldWriteGetter());
		getterAccessValues.select(getAccessIndex(bean.getGetterAccess()));
		propertyWriteSetter.setSelection(bean.shouldWriteSetter());
		setterAccessValues.select(getAccessIndex(bean.getSetterAccess()));
		setGetterAccessEnabled(propertyWriteGetter.getSelection());
		setSetterAccessEnabled(propertyWriteSetter.getSelection());
	}
	
	
	public Collection getProperties()
	{
		return propertyBeans.values();
	}
	
	/**
	 * When a property is removed, if it was removed from, say, the middle the rest 
	 * of the list has to be reindex to reflect the change... is it really this
	 * hard?
	 * 
	 * @param i
	 */
	private void resortBeans(int i) {
		int loopLen = this.propertyBeans.size();
		for(int j = i; j < loopLen; j++)
		{
			if(j+1 != loopLen)
			{
				propertyBeans.put(
					new Integer(j), propertyBeans.get(new Integer(j + 1))
				);
			}
		}
		propertyBeans.remove(new Integer(loopLen - 1));
	}
	
	/**
	 * This is used to get the CFML representation of the properties that were
	 * collected on this page
	 * 
	 * @return the CFML code
	 */
	public String getPropertiesAsTags()
	{
		StringBuffer sb = new StringBuffer();
			
		for(Iterator iter = this.propertyBeans.values().iterator(); iter.hasNext();)
		{
			CFCPropertyBean bean = (CFCPropertyBean)iter.next();
			
			sb.append("\t");
			sb.append("<cfproperty name=\"");
			sb.append(bean.getName() + "\"");
			
			if(bean.getDisplayName().length() > 0)
			sb.append(" displayname=\"" + bean.getDisplayName() + "\"");
			
			if(bean.getHint().length() > 0)
			sb.append(" hint=\"" + bean.getHint() + "\"");
			
			if(bean.getType().length() > 0)
			sb.append(" type=\"" + bean.getType() + "\"");
			
			if(bean.getDefaultVal().length() > 0)
			sb.append(" default=\"" + bean.getDefaultVal() + "\"");
			
			sb.append(" />");
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * This is used to get the CFML representation of the properties that were
	 * collected on this page
	 * 
	 * @return the CFML code
	 */
	public String getPropertyGettersAndSetters()
	{
		StringBuffer sb = new StringBuffer();
			
		for(Iterator iter = this.propertyBeans.values().iterator(); iter.hasNext();)
		{
			CFCPropertyBean bean = (CFCPropertyBean)iter.next();
			
			if (bean.shouldWriteGetter()) {
			    sb.append("\n\t");
			    sb.append("<cffunction name=\"get");
			    sb.append(bean.getName().substring(0,1).toUpperCase());
			    sb.append(bean.getName().substring(1));
			    sb.append("\" access=\"");
			    sb.append(bean.getGetterAccess());
			    sb.append("\"");
			    sb.append(" output=\"false\"");
			    sb.append(" returntype=\"");
			    sb.append(bean.getType());
			    sb.append("\"");
			    sb.append(">");
			    sb.append("\n\t\t");
			    sb.append("<cfreturn this.");
			    sb.append(bean.getName());
			    sb.append(" />");
			    sb.append("\n\t");
			    sb.append("</cffunction>");
			    sb.append("\n");
			}
			
			if (bean.shouldWriteSetter()) {
			    sb.append("\n\t");
			    sb.append("<cffunction name=\"set");
			    sb.append(bean.getName().substring(0,1).toUpperCase());
			    sb.append(bean.getName().substring(1));
			    sb.append("\" access=\"");
			    sb.append(bean.getSetterAccess());
			    sb.append("\"");
			    sb.append(" output=\"false\"");
			    sb.append(" returntype=\"void\"");
			    sb.append(">");
			    sb.append("\n\t\t");
			    sb.append("<cfargument name=\"");
			    sb.append(bean.getName());
			    sb.append("\" type=\"");
			    sb.append(bean.getType());
			    sb.append("\" required=\"true\" />");
			    sb.append("\n\t\t");
			    sb.append("<cfset this.");
			    sb.append(bean.getName());
			    sb.append(" = arguments.");
			    sb.append(bean.getName());
			    sb.append(" />");
			    sb.append("\n\t\t");
			    sb.append("<cfreturn />");
			    sb.append("\n\t");
			    sb.append("</cffunction>");
			    sb.append("\n");
			}
			
		}
		
		return sb.toString();
	}
	
	/**
	 * Tell if there are even properties added
	 * @return
	 */
	public boolean hasProperties()
	{
		if(this.propertyBeans != null && this.propertyBeans.size() > 0)
			return true;
		else
			return false;
	}

	private void checkForDuplicateProperty() {
	    String currentName =  propertyName.getText();
        for (int i=0; i<propertyNames.getItemCount(); i++) {
            if (i != propertyNames.getSelectionIndex() && propertyNames.getItem(i).equalsIgnoreCase(currentName)) {
                updateStatus("Duplicate property names not allowed.");
            }
            else {
                updateStatus(null);
            }
        }
        
    }

}