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

//import org.eclipse.core.resources.IContainer;
//import org.eclipse.core.resources.IResource;
//import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.graphics.Font;
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
 * The "New" wizard page allows setting the container for the new file as well as the 
 * file name. The page will only accept file name without the extension OR with the 
 * extension that matches the expected one (cfm).
 * @author cq
 */
public class NewCFCWizardArguments extends WizardPage {
	private Text argumentName;
	private Text argumentDisplayName;
	private Combo argumentType;
	private Text argumentHint;
	private Text argumentDefault;
	private String[] argumentNames;
	private Combo funcList;
	private Button argumentIsRequired;
	private List argumentList;
	
	private Button plus;
	private Button minus;
	
    private Text widgetClicked = null;

    private ISelection selection;
	
	private NewCFCWizardFunctions functPage;

	private int argIdx = -1;
	
	/** this is all the functions, the last page should have set these up else they
	 * are null and we should error
	 */
	private static LinkedHashMap functionBeans;
	
	/** the currently loaded (editing function bean) */
	private static CFCFunctionBean currentFunctionBean;
	
	/** the currently loaded (editing argument bean) */
	private static CFCArgumentBean currentArgumentBean;
	
	/** all the argument beans */
	//private Hashtable argumentBeans;

	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewCFCWizardArguments(ISelection selection) {
		super("wizardPage");

		// System.err.println("constructor");

		setTitle("New CF Component");
		setDescription("New CF Component Arguments wizard.");
		this.selection = selection;
	}

	// Listener to select all text when focus passes to a Text field
	private MouseListener mListener = new MouseListener() {
	    public void mouseDown(MouseEvent e) {}
	    public void mouseDoubleClick(MouseEvent e) {}
	    public void mouseUp(MouseEvent e) {
	        Text item = (Text)e.widget;
	        if (!item.equals(widgetClicked)) {
	            item.selectAll();
		        widgetClicked = item;
	        }
	    }
	};
	
	private FocusListener fListener = new FocusListener() {
	    public void focusGained(FocusEvent e) {
	        ((Text)e.widget).selectAll();
	    }
	    public void focusLost(FocusEvent e) {
	        widgetClicked = null;
	    }
	};
	
	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		// System.err.println("create control");

		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		
		
		Composite buttons = createFunctionDropdown(container);
		
		createAddButton(buttons);
		createRemoveButton(buttons);
		createArgumentLIst(container);
		createNameTextbox(container);
		createDisplayNameTextbox(container);
		createHintTextbox(container);
		createTypeDropDown(container);
		createRequiredCheckbox(container);
		createDefaultTextbox(container);

		//initialize();
		
		dialogChanged();
		setControl(container);
	}

	private Composite createFunctionDropdown(Composite container) {
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
		funcList.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				loadFunction();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		/* funcList.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				loadFunction();
			}
		}); */
		
		
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
		return buttons;
	}

	private void createAddButton(Composite buttons) {
		GridData data;
		plus = new Button(buttons, SWT.PUSH);
		plus.setText(" + ");
		//plus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		plus.setEnabled(false);
		plus.setLayoutData(data);
		plus.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAdd();
			}
		});
	}

	private void createDefaultTextbox(Composite container) {
		GridData data;
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
		argumentDefault.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
		
		argumentName.addFocusListener(fListener);
		argumentDisplayName.addFocusListener(fListener);
		argumentHint.addFocusListener(fListener);
		argumentDefault.addFocusListener(fListener);

		argumentName.addMouseListener(mListener);
		argumentDisplayName.addMouseListener(mListener);
		argumentHint.addMouseListener(mListener);
		argumentDefault.addMouseListener(mListener);
	}

	private void createRequiredCheckbox(Composite container) {
		GridData data;
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
		argumentIsRequired.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e){
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				propertyChanged();
			}
		});
	}

	private void createTypeDropDown(Composite container) {
		GridData data;
		Label accessLabel = new Label (container, SWT.NONE);
		accessLabel.setText ("&Type");
		data = new GridData();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.END;
		accessLabel.setLayoutData(data);
		
		argumentType = new Combo(container, SWT.BORDER);
		
		//get the proper return types
		CFSyntaxDictionary cfmldic = (CFSyntaxDictionary)DictionaryManager.getDictionary(
			DictionaryManager.CFDIC
		);
		TreeSet cfitems = new TreeSet(
			cfmldic.getFilteredAttributeValues("cfargument", "type", "")
		);
		Iterator i = cfitems.iterator();
		String[] str = new String[cfitems.size()];
		int q=0;
		while(i.hasNext()){
			str[q++] = ((Value)i.next()).getValue();
		}
		argumentType.setItems(str);
		//argumentType.setItems(new String[]{"any", "array", "struct", "query"});
		
		data = new GridData ();
		data.horizontalIndent = 5;
		data.horizontalAlignment = GridData.BEGINNING;
		data.verticalAlignment = GridData.BEGINNING;
		data.widthHint = 295;
		argumentType.setLayoutData(data);
		argumentType.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propertyChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){;}
		});
		
		argumentType.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
	}

	private void createHintTextbox(Composite container) {
		GridData data;
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
		argumentHint.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
	}

	private void createDisplayNameTextbox(Composite container) {
		GridData data;
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
		argumentDisplayName.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();
			}
		});
	}

	private void createNameTextbox(Composite container) {
		GridData data;
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
				updatePropertyNameInList();
				checkForDuplicateProperty();
			}

		});
		//but only update the bean we we lose focus
		argumentName.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){	;}
			public void focusLost(FocusEvent e){
				propertyChanged();	
			}
		});
		/* argumentName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		}); */
	}

	private void createArgumentLIst(Composite container) {
		GridData data;
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
		argumentList.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				selectionChanged();
			}
			public void widgetDefaultSelected(SelectionEvent e){
				//selectionChanged();
			}
		});
	}

	private void createRemoveButton(Composite buttons) {
		GridData data;
		minus = new Button(buttons, SWT.PUSH);
		minus.setText(" - ");
		//minus.setFont(new Font(plus.getDisplay(),"arial", 12, java.awt.Font.BOLD));
		data = new GridData ();
		data.horizontalAlignment = GridData.BEGINNING;
		minus.setEnabled(false);
		minus.setLayoutData(data);		
		minus.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {;}
			public void mouseDown(MouseEvent e) {
				handleMinus();
			}
			public void mouseUp(MouseEvent e) {;}
		});
	}
	
	/**
	 * disables all the controls
	 */
	private void disable()
	{

		// System.err.println("disable");

		setControlsEnabled(false);
	}
	
	/**
	 * enables all the controls
	 */
	private void enable()
	{

		// System.err.println("enable");

		setControlsEnabled(true);
	}
	
	/**
	 * Turn on and off the controls on this page
	 * @param to
	 */
	public void setControlsEnabled(boolean to){

		// System.err.println("setControlsEnabled");

		argumentName.setEnabled(to);
		argumentDisplayName.setEnabled(to);
		argumentHint.setEnabled(to);
		argumentDefault.setEnabled(to);
		argumentIsRequired.setEnabled(to);
		argumentType.setEnabled(to);
		argumentList.setEnabled(to);
	}
	
	/**
	 * When the name of the property changes, the listing name should change too.
	 * This updates the current name to the bean name. should be called on the name
	 * change event only.
	 */
	private void updatePropertyNameInList(){

		// System.err.println("updateProperyNameInList");

		//update the list text, then the bean
		int idx = argumentList.getSelectionIndex();
		if (idx >= 0) {
		    argumentList.setItem(idx, argumentName.getText());
		}
	}

	/**
	 * Saves the beans items into the currently selected bean
	 */
	private void propertyChanged() 
	{
		//// System.err.println("I was asked to update the beans props");
		

		// System.err.println("propertyChanged");

		updateProperties();
		updateStatus(null);
		checkForDuplicateProperty();
	}
	
	/**
	 * Load up the currently selected function so we can add arguments 
	 */
	private void loadFunction(){

		// System.err.println("loadFunction");

	    plus.setEnabled(true);
	    updateProperties();
		int index = 0;
		
		Iterator i = functionBeans.keySet().iterator();
		while(i.hasNext())
		{
			Object cfcb = i.next();
			if(index++ == funcList.getSelectionIndex())
			{
				// // System.err.println("finding bean: " + cfcb);
				currentFunctionBean = (CFCFunctionBean)functionBeans.get(cfcb);
				currentArgumentBean = null;
				break;
			}
		}
		argIdx = -1;
		loadCurrentArguments();
		clearAllFields();
	}
	
	/**
     * When switching functions, need to clear info in all fields
     */
    private void clearAllFields() {

		// System.err.println("clearAllFields");

		argumentName.setText("");
		argumentDisplayName.setText("");
		argumentHint.setText("");
		argumentType.setText("");
		argumentDefault.setText("");
		argumentIsRequired.setSelection(false);
    }

    /**
	 * When a selection has changed call the updating function
	 */
	private void selectionChanged()
	{

		// System.err.println("selectionChanged");

		updateProperties();
		//selectProperties();
		
		int idx = argumentList.getSelectionIndex();
		
		// // System.err.println("loading argument bean... " + idx);
		// // System.err.println("current: " + currentArgumentBean);
		
		if(idx > -1)
		{
			currentArgumentBean = (CFCArgumentBean)currentFunctionBean.getArgumentBeans().get(new Integer(idx));
			// // System.err.println("new current: " + currentArgumentBean);
			
			loadBeanToEdit(currentArgumentBean);
		}
	}
	
	/**
	 * Load all the arguments from the current function bean 
	 */
	private void loadCurrentArguments(){

		// System.err.println("loadCurrentArguments");

		argumentList.removeAll();
		
		Iterator i = currentFunctionBean.getArgumentBeans().keySet().iterator();
		while(i.hasNext())
		{
			Object cab = i.next();
			// // System.err.println("finding bean: " + cab);
			CFCArgumentBean cfab = (CFCArgumentBean)currentFunctionBean.getArgumentBeans().get(cab);
			argumentList.add( cfab.getName() );
			argIdx++;
			// currentArgumentBean = cfab;
		}
		if (argIdx >= 0) {
		    minus.setEnabled(true);
		}
	}
	
	/**
	 * Sets the functions names in the functions drop down
	 * @param s
	 */
	public void setFunctionItems(String[] s)
	{

		// System.err.println("setFunctionItems");

		this.funcList.setItems(s);
		//this.funcList.select(0);
	}
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		// System.err.println("dialogChanged");

		updateStatus(null);
		checkForDuplicateProperty();
	}
	
	/**
	 * shows a little message on the wizards status bar
	 * @param message
	 */
	private void updateStatus(String message) {

		// System.err.println("updateStatus");

		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	/**
	 * "saves" the beans set items
	 */
	private void updateProperties()
	{

		// System.err.println("updateProperties");

		if(currentArgumentBean != null)
		{	
			currentArgumentBean.setName(argumentName.getText());
			currentArgumentBean.setDisplayName(argumentDisplayName.getText());
			currentArgumentBean.setHint(argumentHint.getText());
			currentArgumentBean.setType(argumentType.getText());
			currentArgumentBean.setDefaultVal(argumentDefault.getText());
			currentArgumentBean.setRequired(argumentIsRequired.getSelection());
		}
		else {
			// System.err.println("Current bean is null");
		}
	}
	
	/**
	 * Add an argument
	 */
	private void handleAdd() 
	{

		// System.err.println("handleAdd");

		this.argIdx++;
		CFCArgumentBean bean = new CFCArgumentBean();
		
		enable();
		
		if (currentArgumentBean != null) {
		    updateProperties();
		}
		
		bean.setName("newArgument");
				
		currentFunctionBean.addArgumentBean(new Integer(argIdx), bean);
				
		argumentList.add(bean.getName());
		argumentList.select(argIdx);
		
		loadBeanToEdit(bean);
		
		//make the current argument bean this bean
		currentArgumentBean = bean;
		
		minus.setEnabled(true);
	}
	
	/**
	 * Remove a function
	 */
	private void handleMinus()
	{

		// System.err.println("handleMinus");

		//this is the bean we are killing...
		int i = argumentList.getSelectionIndex();
		
		if (i < 0) {
		    return;
		}
	
		//remove the index
		argumentList.remove(i);
			
		//try to set the current bean to the last one or 0
		if(i-1 != 0 && i != 0) {
			argumentList.select(i-1);
			currentArgumentBean = (CFCArgumentBean)currentFunctionBean.getArgumentBeans().get(new Integer(i-1));
		} else {
			argumentList.select(0);
			currentArgumentBean = (CFCArgumentBean)currentFunctionBean.getArgumentBeans().get(new Integer(0));
		}
		
		resortBeans(i);
		argIdx--;
		
		loadBeanToEdit(currentArgumentBean);
	}
	
	
	/**
	 * Load a bean into the text boxes for editing
	 * 
	 * @param bean
	 */
	private void loadBeanToEdit(CFCArgumentBean bean){

		// System.err.println("loadBeanToEdit");

		//setup the gui to be able to edit the beans current values
		argumentName.setText(bean.getName());
		argumentDisplayName.setText(bean.getDisplayName());
		argumentHint.setText(bean.getHint());
		argumentDefault.setText(bean.getDefaultVal());
		argumentIsRequired.setSelection(bean.isRequired());
		argumentType.select(getTypeIndex(bean.getType()));
	}
	
	/**
	 * Looks up the index of "s" in the list of "returntype" choices
	 */
	private int getTypeIndex(String s)
	{

		// System.err.println("getTypeIndex");

		int frtc = argumentType.getItemCount();
		for(int i = 0; i < frtc; i++)
		{	
			if(argumentType.getItem(i).equalsIgnoreCase(s))
				return i;
		}
		return 0;
	}
	
	/**
	 * setup all the fuction beans so arguments can be added to them
	 * @return
	 */
	public LinkedHashMap getFunctionBeans() {

		// System.err.println("getFunctionBeans");

		return functionBeans;
	}
	
	/**
	 * the function beans
	 * @param functionBeans
	 */
	public void setFunctionBeans(LinkedHashMap functionBeans) {

		// System.err.println("setFunctionBeans");

		NewCFCWizardArguments.functionBeans = functionBeans;
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

		// System.err.println("resortBeans");

		int loopLen = currentFunctionBean.getArgumentBeans().size();
		//int loopLen = functionBeans.size();
		for(int j = i; j < loopLen; j++)
		{
			if(j+1 != loopLen)
			{
				currentFunctionBean.getArgumentBeans().put(
				//functionBeans.put(
					new Integer(j), 
					currentFunctionBean.getArgumentBeans().get(new Integer(j + 1))
				);
			}
		}
		currentFunctionBean.getArgumentBeans().remove(new Integer(loopLen - 1));
	}

	private void checkForDuplicateProperty() {

		// System.err.println("checkForDuplicateProperty");

	    String currentName =  argumentName.getText();
        for (int i=0; i<argumentList.getItemCount(); i++) {
            if (i != argumentList.getSelectionIndex() && argumentList.getItem(i).equalsIgnoreCase(currentName)) {
                updateStatus("Duplicate argument names not allowed.");
            }
            else {
                updateStatus(null);
            }
        }
        
    }

}