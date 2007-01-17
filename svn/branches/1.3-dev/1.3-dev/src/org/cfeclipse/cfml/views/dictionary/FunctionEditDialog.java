package org.cfeclipse.cfml.views.dictionary;


import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;


public class FunctionEditDialog extends Dialog {
	
	protected String title;
	private Function func;
	private Set attributes;
	private Map selectedattributes;
	
	private Properties fieldStore;
	private Properties comboFields;
	private Properties textFields;
	
	public FunctionEditDialog(Shell parentShell) {
		super(parentShell);
		comboFields = new Properties();
		textFields = new Properties();
	}

	public FunctionEditDialog(Shell parentShell, Function func) {
		super(parentShell);
		comboFields = new Properties();
		textFields = new Properties();
		this.setTitle(func.getName());
		this.setFunction(func);
		this.setAttributes(func.getParameters());
		this.setFieldStore(new Properties());
	}

	/**
	 * Creates the dialog area, does not require a layout
	 */
	protected Control createDialogArea(Composite parent){
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout fl = new FillLayout();
		container.setLayout(fl);
		TabFolder tabFolder = new TabFolder(container, SWT.HORIZONTAL);

		//Go and create the dialog for the Function attributes
		tabFolder = parseFunction(tabFolder);

		
		
		//Help Tab
		TabItem tabHelp = new TabItem(tabFolder, SWT.NONE);
		tabHelp.setText("Help");
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		
		
		
		//The help text field
		Composite helpContents = new Composite(tabFolder, SWT.NONE);
			helpContents.setLayout(gl);
			Text helpDesc = new Text(helpContents, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE);
			helpDesc.setLayoutData(new GridData(GridData.FILL_BOTH));
			helpDesc.setBackground(new Color(Display.getCurrent(), 255,255,255));
			
			helpDesc.setText(this.func.getHelp());
			
		tabHelp.setControl(helpContents);
		
		
		return container;
		
	}
	
	
	private TabFolder parseFunction(TabFolder folder){
		//Create the main tag
		TabItem thisTab = new TabItem(folder, SWT.NONE);
		thisTab.setText("General");
		
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		
		Composite mainContents  = new Composite(folder, SWT.NONE);
		mainContents.setLayout(gl);
		
		if(this.attributes != null){
			Iterator i = this.attributes.iterator();
			while(i.hasNext()){
				Parameter pr = (Parameter)i.next();
				
				String labelname = pr.getName() + " : ";
				if(pr.isRequired()){
					labelname = pr.getName() + " *: ";
				}
				
				Label label = new Label(mainContents, SWT.HORIZONTAL);
				label.setText(labelname);
				label.setToolTipText(pr.getHelp());
				
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.widthHint = 200;
				
				//Here we need to check if the attribute has multiple, i.e. for a drop down
				if(!pr.getValues().isEmpty()){
					addComboField(mainContents, gridData, pr);
				} else {
					addTextField(mainContents, gridData, pr);
				}
				
				
			}
			Label reqlabel = new Label(mainContents, SWT.HORIZONTAL);
			reqlabel.setText("Labels marked with * are required.");
			GridData labgridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			labgridData.horizontalSpan = 2;
			reqlabel.setLayoutData(labgridData);
		}
		
		thisTab.setControl(mainContents);
		return folder;
	}
	
	private void addTextField(Composite parent, GridData gridData, Parameter pr) {

		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(gridData);
		text.setText("");
		
		// Add the text and the attribute name to the combo fields properties
		//Now go and find if there is an attribute for it...
		
		if(selectedattributes != null && selectedattributes.containsKey(pr.getName())){
			text.setText(selectedattributes.get(pr.getName()).toString());
		}
		text.setToolTipText(pr.getHelp());
		
		textFields.put(pr.getName(), text);

	}	
	private void addComboField(Composite parent, GridData gridData, Parameter pr) {
		int defaultitem = 0;
		Iterator i = pr.getValues().iterator();
		Combo combo = new Combo(parent, SWT.DROP_DOWN);
		combo.setLayoutData(gridData);

		//WE add double quotes until someone says no ?
		while (i.hasNext()) {
			Object val = (Object) i.next();
			combo.add("\"" + val.toString() + "\"");
		}

		if(selectedattributes != null && selectedattributes.containsKey(pr.getName())){
			combo.setText(selectedattributes.get(pr.getName()).toString());
		}
		combo.setToolTipText(pr.getHelp());
		comboFields.put(pr.getName(), combo);

	}
	
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}		
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			// Grab the values from all the text fields and stick them in the fieldStore
			Enumeration e = textFields.keys();
			while (e.hasMoreElements()) {
				String key = e.nextElement().toString();
				Text t = (Text)textFields.get(key);
				fieldStore.put(key,t.getText());
			}
			
			// Grab the values from all the combo fields and stick them in the fieldStore 
			e = comboFields.keys();
			while (e.hasMoreElements()) {
				String key = e.nextElement().toString();
				Combo c = (Combo)comboFields.get(key);
				fieldStore.put(key,c.getText());
			}
			// We're done. Close the dialog
			this.close();
		}
		super.buttonPressed(buttonId);

	}
	
	
	/*
	 * Getters and Setters go below
	 */
	
	public void setFieldStore(Properties fieldStore) {
		this.fieldStore = fieldStore;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Function getFunction() {
		return func;
	}


	public void setFunction(Function func) {
		this.func = func;
	}


	public Set getAttributes() {
		return attributes;
	}


	public void setAttributes(Set attributes) {
		this.attributes = attributes;
	}

	public Map getSelectedattributes() {
		return selectedattributes;
	}

	public void setSelectedattributes(Map selectedattributes) {
		this.selectedattributes = selectedattributes;
	}

	public Properties getFieldStore() {
		return fieldStore;
	}

}
