/*
 * Created on 	: 08-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: TagEditDialog.java
 * Description	:
 * 
 */
package org.cfeclipse.cfml.dialogs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dialogs.objects.Category;
import org.cfeclipse.cfml.dialogs.objects.CategoryList;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.dictionary.Tag;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Mark Drew
 * 
 * This is the tag Editor Dialog. 
 * 
 * Rather complicated as it gets a tag, 
 * Gets the layout file for it (if it has one)
 * TODO: If no layout file has been found, use the categories, currently only for HTML tags as they have a number of scary attributes
 * Does the layout of it. 
 */
public class TagEditDialog extends Dialog {
	protected String title;
	private Tag tag;
	private Set attributes;
	private Map selectedattributes;
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
	public TagEditDialog(Shell parentShell) {
		super(parentShell);
		comboFields = new Properties();
		textFields = new Properties();
	}
	
	public TagEditDialog(Shell parentShell, Tag tag){
		super(parentShell);
		comboFields = new Properties();
		textFields = new Properties();
		this.setTitle(tag.getName());
		this.setTag(tag);
		Set attribs = tag.getParameters();
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
		
		//Go get the layout file
		Document layoutDoc = this.loadLayoutFile(this.title);
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout fl = new FillLayout();
		container.setLayout(fl);
		TabFolder tabFolder = new TabFolder(container, SWT.HORIZONTAL);
		
		if(layoutDoc != null){
			//do the default layout
			tabFolder = parseCategories(tabFolder, layoutDoc); 			
		} else {
			//do the tab layouts
			//Since we havent found it.. see if the tag has categories in itself!
			tabFolder = parseTagCategories(tabFolder);
		//	tabFolder = parseTag(tabFolder);
			
		}
		
		
	    //The Help Tab
	    TabItem tabHelp = new TabItem(tabFolder, SWT.NONE);
	    tabHelp.setText("Help");
	    GridLayout gl = new GridLayout();
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
	
	//Debugging class
		private static void stepThrough (Node start) {
			//String name = start.getAttributes().getNamedItem("name").toString();
			NamedNodeMap attribs = start.getAttributes();
			System.out.println(start.getNodeName() + " " + attribs.getNamedItem("name") + " default:" +  attribs.getNamedItem("value"));
			
			for (Node child = start.getFirstChild(); child != null; child = child.getNextSibling()){
				if(child.getNodeType() == 1){
					stepThrough(child);
				}
			}
		}

	private TabFolder parseCategories(TabFolder tabFolder, Document layout){
		//This seems to be looping strangely. so maybe get something esle.
		NodeList tabs = layout.getElementsByTagName("tab");
		
		//layout stuff
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		
		
		Element dialog = layout.getDocumentElement();
		
		stepThrough(dialog);
		
		 if(this.attributes != null){
			//Get the tabs
			for(int t=0; t < tabs.getLength(); t++){
				Node layouttabs = tabs.item(t);
				
				TabItem thisTab = new TabItem(tabFolder, SWT.NONE);
				thisTab.setText(layouttabs.getAttributes().getNamedItem("name").getNodeValue());
				Composite tagContents = new Composite(tabFolder, SWT.NONE);
			    tagContents.setLayout(gl);
			    
			    
			    
			    
			     //Get the fields
			    NodeList fields = layouttabs.getChildNodes();
	
			    
			    for(int f=1; f < fields.getLength(); f = f + 2){
			    	// here we get the ACTUAL fields. The logic is that we go and get a field as defined from the xml file. and display it in the right format
			    	// If it is not defined, it isnt displayed.
			    	
			    	
			    	Iterator i = this.attributes.iterator();
			    	while(i.hasNext()){
			    		Parameter pr = (Parameter)i.next();
			    		//System.out.println("\t\t" + fields.item(f).getAttributes().getNamedItem("name").getNodeValue());
			    		
			    	
			    		String maskField = fields.item(f).getAttributes().getNamedItem("name").getNodeValue();
			    		String defaultValue = "";
			    		if(fields.item(f).getAttributes().getNamedItem("value") != null){
			    			defaultValue = fields.item(f).getAttributes().getNamedItem("value").getNodeValue();
			    			
			    		}
			    		
			    		if(pr.getName().equals(maskField)){
			    			String labelname = pr.getName() + " : ";
							if(pr.isRequired()){
								labelname = pr.getName() + " *: ";
								
							}
							
							Label label = new Label(tagContents, SWT.HORIZONTAL);
							label.setText(labelname);
							label.setToolTipText(pr.getHelp());
							GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
							gridData.widthHint = 200;

							if (!pr.getValues().isEmpty()) {
								addComboField(tagContents, pr.getValues(), gridData, pr.getName(), defaultValue);
							} else {
								addTextField(tagContents, gridData, pr.getName(), defaultValue);
							}
			    			
			    		}
			    	}
			    	/*
			    	Label label = new Label(tagContents, SWT.HORIZONTAL);
					label.setText(fields.item(f).getAttributes().getNamedItem("name").getNodeValue());
	
					Text text = new Text(tagContents, SWT.BORDER);
					text.setText("");
					*/
					
			    }
			    thisTab.setControl(tagContents);
			    
			    
			}
		}
		
		return tabFolder;
	}
	
	private TabFolder parseTag(TabFolder tabFolder){
		
		//Create the main tab
		TabItem thisTab = new TabItem(tabFolder, SWT.NONE);
		thisTab.setText("General");
		 
		GridLayout gl = new GridLayout();
	    	gl.numColumns = 2;
	    
	    	Composite mainContents = new Composite(tabFolder, SWT.NONE);
	    	mainContents.setLayout(gl);
		
		
		if (this.attributes != null) {
			Iterator i = this.attributes.iterator();
			while (i.hasNext()) {
				Parameter pr = (Parameter) i.next();
				
				String labelname = pr.getName() + " : ";
				if(pr.isRequired()){
					labelname = pr.getName() + " *: ";
					
				}
				
				Label label = new Label(mainContents, SWT.HORIZONTAL);
				label.setText(labelname + "category" + pr.getCategory());
				label.setToolTipText(pr.getHelp());
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridData.widthHint = 200;

				if (!pr.getValues().isEmpty()) {
					addComboField(mainContents, pr.getValues(), gridData, pr
							.getName(), "");
				} else {
					addTextField(mainContents, gridData, pr.getName(), "");
				}
			}
			Label reqlabel = new Label(mainContents, SWT.HORIZONTAL);
			reqlabel.setText("Labels marked with * are required.");
			GridData labgridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			labgridData.horizontalSpan = 2;
			reqlabel.setLayoutData(labgridData);
			
		}
		thisTab.setControl(mainContents);
	    
		return tabFolder;
	}
	
	private TabFolder parseTagCategories(TabFolder tabFolder){
			    CategoryList cl = getParameterCategories();
		
			    
			  
			    
			//Now create the tabs initially:
		    Iterator tabIter = cl.getCategories().iterator();
		    while(tabIter.hasNext()){
		    	Category cat = (Category)tabIter.next();
		    	TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		    	tabItem.setText(cat.getName());
			    
		    	//Layout for the fields in this tab
		    	GridLayout gl = new GridLayout();
				gl.numColumns = 2;
				
				//composite, field and label
				Composite mainContents = new Composite(tabFolder, SWT.NONE);
		    	mainContents.setLayout(gl);    
		    	
		    	//Loop through the category getting the parameters
		    	Iterator tagIter = cat.getParams().iterator();
		    	while(tagIter.hasNext()){
		    		Parameter pr = (Parameter)tagIter.next();
		    		String labelname = pr.getName() + " : ";
					if(pr.isRequired()){
						labelname = pr.getName() + " *: ";
						
					}
					Label label = new Label(mainContents, SWT.HORIZONTAL);
					label.setText(labelname);
					label.setToolTipText(pr.getHelp());
					GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					gridData.widthHint = 200;

					if (!pr.getValues().isEmpty()) {
						addComboField(mainContents, pr.getValues(), gridData, pr
								.getName(), "");
					} else {
						addTextField(mainContents, gridData, pr.getName(), "");
					}
		    		
		    	}
		    	
				
				tabItem.setControl(mainContents);
		    	
		    }
		    
		return tabFolder;
	}
	
	private CategoryList getParameterCategories(){
		CategoryList cl = new CategoryList();
		
		
		if (this.attributes != null) {
		
			Iterator i = this.attributes.iterator();
			while(i.hasNext()){
				Parameter pr = (Parameter) i.next();
				Category cat = null;
				
				if(!cl.hasCategory(pr.getCategory())){
					cat = new Category(pr.getCategory());
					
					//add the Parameter
					cat.addParameter(pr);
					cl.addCategory(cat);
				}
				else {
					cat = cl.getCategory(pr.getCategory());
					cat.addParameter(pr);
				}
				
			}
		}
		System.out.println(cl);
		return cl;
	}
	
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(this.title);
	}
	
	//Extended methods, that help with the creation of the dialog
	
	/**
	 * This function returns the layout file, null if it isnt there
	 * @param tagname
	 * @return Document
	 */
	private Document loadLayoutFile(String tagname){
		Document layoutDoc = null;
	
		URL layoutConfigURL = null;
		try 
		{
			layoutConfigURL = new URL(
				CFMLPlugin.getDefault().getBundle().getEntry("/"),
				"dictionary/layouts/"
			);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setCoalescing(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			URL local = FileLocator.toFileURL(layoutConfigURL);
			
			URL configurl = FileLocator.resolve(new URL(local, tagname + ".xml"));
						
			layoutDoc = builder.parse(configurl.getFile());
			
			
		} 
		catch (Exception e) 
		{
			System.out.println("---- Error Loading " + tagname + ".xml ----");
			layoutDoc = null;
		}
		
		
		
		
		
		return  layoutDoc;
	}
	
	private void addComboField(Composite parent, Set items, GridData gridData,
		String field, String defaultVal) {
		Iterator i = items.iterator();
		Combo combo = new Combo(parent, SWT.DROP_DOWN);
		combo.setLayoutData(gridData);
		int selected = -1;
		int itemcounter = 0;
		while (i.hasNext()) {
			Object val = (Object) i.next();
			combo.add(val.toString());
			if(val.toString().equals(defaultVal)){
				selected = itemcounter;
			}
			itemcounter++;
		}

		//Not sure what this is doing.
		if(selectedattributes != null && selectedattributes.containsKey(field)){
			combo.setText(selectedattributes.get(field).toString());
		}
		combo.select(selected);
		//combo.select(defaultitem);
		// Add the combo and the attribute name to the combo fields properties
		comboFields.put(field, combo);

	}

	private void addTextField(Composite parent, GridData gridData, String field, String defaultVal) {

		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(gridData);
		text.setText(defaultVal);
		// Add the text and the attribute name to the combo fields properties
		//Now go and find if there is an attribute for it...
		
		if(selectedattributes != null && selectedattributes.containsKey(field)){
			text.setText(selectedattributes.get(field).toString());
		}
		
		
		textFields.put(field, text);

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 * This creates the buttons at the bottom (Insert /Cancel)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
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
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	/**
	 * @param atributes
	 *            The atributes to set.
	 */
	public void setAtributes(Set attributes) {
		this.attributes = attributes;
	}

	public Properties getFieldStore() {
		return fieldStore;
	}

	public Map getSelectedattributes() {
		return selectedattributes;
	}

	public void setSelectedattributes(Map selectedattributes) {
		this.selectedattributes = selectedattributes;
	}

}