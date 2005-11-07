/*
 * Created on 	: 08-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: TagViewer.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.views.dictionary;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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

import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.views.browser.CFBrowser;

/**
 * @author Mark Drew
 * 
 * This is the tag Editor Dialog. Should be renamed somehow
 *  
 */
public class TagViewer extends Dialog {
	protected String title;

	private TagItem tag;

	private Set attributes;

	private Properties fieldStore;

	private Properties comboFields;

	private Properties textFields;

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	protected void setFieldStore(Properties fieldStore) {
		this.fieldStore = fieldStore;
	}

	/**
	 * @param parentShell
	 */
	public TagViewer(Shell parentShell) {
		super(parentShell);
		comboFields = new Properties();
		textFields = new Properties();
	}
	
	public TagViewer(Shell parentShell, TagItem tag){
		super(parentShell);
		comboFields = new Properties();
		textFields = new Properties();
		/*
		 * This was originally in the view. no idea why. but here it is. Now the action needs to be removed from the view
		 * 
		 */
		this.setTitle(tag.getName());
		this.setTag(tag);
		Set attribs = tag.getDictionary().getElementAttributes(tag.getName());
		this.setAtributes(attribs);
		this.setFieldStore(new Properties());

	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 * This method will create the dialog. 
	 * The process will be, 
	 * 	1) Check to see if there is an xml file layout for this tag
	 *  2) if no layout, then go and do a default layout
	 *  3) if there is a layout go and parse the layout, doing a tabbed interface (maybe one there by default with the help comments.
	 */
	protected Control createDialogArea(Composite parent) {
		
		
		//Create the composite
		Composite container = (Composite) super.createDialogArea(parent);
		
		// A Grid layout
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		
		//A Fill layout
		FillLayout fl = new FillLayout();
		container.setLayout(fl);
		TabFolder tabFolder = new TabFolder(container, SWT.HORIZONTAL);
		//The Main Tab
		TabItem tabMain = new TabItem(tabFolder, SWT.NONE);
	    tabMain.setText("Main");
	    
	    Composite mainContents = new Composite(tabFolder, SWT.NONE);
	    mainContents.setLayout(gl);
	    
	    Label label = new Label(mainContents, SWT.HORIZONTAL);
			label.setText("howdy");

		Text text = new Text(mainContents, SWT.BORDER);
			text.setText("");
			
	    
	    
	    
	    tabMain.setControl(mainContents);
	    
	   //WE add a control here which in theory is a new gridlayout? or something??!
	   
	    
	    
	    
	    //The Help Tab
	    TabItem tabHelp = new TabItem(tabFolder, SWT.NONE);
	    tabHelp.setText("Help");
	    	
	    	gl.numColumns = 1;
	    
	    	Composite helpContents = new Composite(tabFolder, SWT.NONE);
	    		helpContents.setLayout(gl);
	    		Text helpDesc = new Text(helpContents, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE);
	    			helpDesc.setLayoutData(new GridData(GridData.FILL_BOTH));
	    			helpDesc.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
	    			
	    			helpDesc.setText(this.tag.getHelp());
	    
	   tabHelp.setControl(helpContents);
	  
	    
	    
		return container;
	}
	
	protected Control createDialogAreax(Composite parent) {
		
		/* Sample code for a tab thing
		 * 
		 * http://www.java2s.com/ExampleCode/SWT-JFace-Eclipse/CreateaCTabFolderwithminandmaxbuttonsaswellasclosebuttonand.htm
		 * 
		 * Samples can also be found here
		 * http://www.eclipse.org/swt/snippets/
		 */
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout gl = new GridLayout();
		gl.numColumns = 2;

		container.setLayout(gl);
		FontData labelFontData = new FontData();
		labelFontData.setStyle(SWT.BOLD);
		FontData[] containerFontData = container.getFont().getFontData();
		labelFontData.setHeight(containerFontData[0].height);
		Font labelFont = new Font(parent.getDisplay(), labelFontData);
		
		
		
		
		
		if (this.attributes != null) {
			Iterator i = this.attributes.iterator();
			while (i.hasNext()) {
				Parameter pr = (Parameter) i.next();
				
				String labelname = pr.getName() + " : ";
				if(pr.isRequired()){
					labelname = pr.getName() + " *: ";
					
				}
				
				Label label = new Label(container, SWT.HORIZONTAL);
				
				label.setText(labelname);
				label.setFont(labelFont);
				//label.setToolTipText(pr.getHelp());
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.widthHint = 200;

				if (!pr.getValues().isEmpty()) {
					addComboField(container, pr.getValues(), gridData, pr
							.getName());
				} else {
					addTextField(container, gridData, pr.getName());
				}
			
				
				
			}
			Label reqlabel = new Label(container, SWT.HORIZONTAL);
			reqlabel.setText("Labels marked with * are required.");
			reqlabel.setFont(labelFont);
			GridData labgridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			labgridData.horizontalSpan = 2;
			reqlabel.setLayoutData(labgridData);
			
		}
		return container;
	}

	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(this.title);
	}
	

	private void addComboField(Composite parent, Set items, GridData gridData,
			String field) {
		int defaultitem = 0;
		Iterator i = items.iterator();
		Combo combo = new Combo(parent, SWT.DROP_DOWN);
		combo.setLayoutData(gridData);

		while (i.hasNext()) {
			Object val = (Object) i.next();
			combo.add(val.toString());
		}

		combo.select(defaultitem);
		// Add the combo and the attribute name to the combo fields properties
		comboFields.put(field, combo);

	}

	private void addTextField(Composite parent, GridData gridData, String field) {

		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(gridData);
		text.setText("");
		// Add the text and the attribute name to the combo fields properties
		textFields.put(field, text);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 * This creates the buttons at the bottom (Insert /Cancel)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Insert", true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
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

	/**
	 * @param tag
	 *            The tag to set.
	 */
	public void setTag(TagItem tag) {
		this.tag = tag;
	}

	/**
	 * @param atributes
	 *            The atributes to set.
	 */
	public void setAtributes(Set attributes) {
		this.attributes = attributes;
	}

}