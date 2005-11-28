package com.rohanclan.cfml.dialogs;

import java.util.Enumeration;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.rohanclan.cfml.dictionary.Tag;


public class EditCustomTagDialog extends Dialog {

		private String title;
		private Tag tag;
		private Set attributes;
		private Shell dialogShell;
		private Label tagNameLabel;
		private Button removeAttribute;
		private Button addAttribute;
		private Composite attributesButtons;
		private Group TagInfo;
		private Composite attributeProperties;
		private Group attrubutesGroup;
		private Label attributeName;
		private Button addAtrib;
		private Text attribName;
		private Button isXMLcheck;
		private Button isSingleCheck;
	
		private List attributesList;
		private Text tagName;
		
		
		public EditCustomTagDialog(Shell parentShell){
			super(parentShell);
			this.title = "New Custom Taggger";
		}
		
		public EditCustomTagDialog(Shell parentShell, Tag tag){
			super(parentShell);
			this.tag = tag;
			this.title = tag.getName();
			Set attribs = tag.getParameters();
			this.attributes = attribs;
		}
		
		
		public Control createDialogArea(Composite parent) {

			Composite container = new Composite(parent, SWT.NULL);
			FillLayout fl = new FillLayout();
			container.setLayout(fl);
			TabFolder tabFolder = new TabFolder(container, SWT.HORIZONTAL);
			
			//Here we addd the main 
			tabFolder = createMainLayout(tabFolder);
			
			//Create the help Tab
			TabItem tabHelp = new TabItem(tabFolder, SWT.NONE);
			tabHelp.setText("Help");
			GridLayout gl = new GridLayout();
			gl.numColumns = 1;

			Composite helpContents = new Composite(tabFolder, SWT.NONE);
			helpContents.setLayout(gl);
			Text helpDesc = new Text(helpContents, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE);
			helpDesc.setLayoutData(new GridData(GridData.FILL_BOTH));
			helpDesc.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
			
			if(this.tag != null){
				helpDesc.setText(this.tag.getHelp());
			}
			else {
				helpDesc.setText("help to go here");	
			}

			tabHelp.setControl(helpContents);
			
			
			return container;
		}
			
		
		private void addAttributeMouseDoubleClick(MouseEvent evt) {
			System.out.println("addAttribute.mouseDoubleClick, event=" + evt);
			//TODO add your code for addAttribute.mouseDoubleClick
		}
		
		private TabFolder createMainLayout(TabFolder tabFolder){
		
			TabItem mainTab = new TabItem(tabFolder, SWT.NONE);
			mainTab.setText("Tag");
			
			GridLayout gl = new GridLayout();
			gl.numColumns = 2;
			
			Composite mainContents = new Composite(tabFolder, SWT.NONE);
			mainContents.setLayout(gl);
			
			//Set the tag name
			displayTagName(mainContents);
			displayTagInfo(mainContents);
			//displayAttributes(mainContents);
			
			
			mainTab.setControl(mainContents);
			return tabFolder;
		}

		/**
		 * @param mainContents
		 */
		private void displayAttributes(Composite mainContents) {
			{
				Composite attribGroup = new Composite(mainContents, SWT.NONE);
				GridLayout attribGroupLayout = new GridLayout();
				attribGroupLayout.makeColumnsEqualWidth = true;
				attribGroupLayout.numColumns = 2;
				GridData attribGroupLData = new GridData();
				attribGroupLData.verticalAlignment = GridData.FILL;
				attribGroupLData.widthHint = 264;
				attribGroupLData.horizontalSpan = 2;
				attribGroup.setLayoutData(attribGroupLData);
				attribGroup.setLayout(attribGroupLayout);
			}
			
			{
				attrubutesGroup = new Group(mainContents, SWT.NONE);
				GridLayout attrubutesGroupLayout = new GridLayout();
				attrubutesGroupLayout.makeColumnsEqualWidth = true;
				attrubutesGroupLayout.numColumns = 2;
				attrubutesGroup.setLayout(attrubutesGroupLayout);
				GridData attrubutesGroupLData = new GridData();
				attrubutesGroupLData.widthHint = 490;
				attrubutesGroupLData.verticalAlignment = GridData.FILL;
				attrubutesGroup.setLayoutData(attrubutesGroupLData);
				attrubutesGroup.setText("Attributes:");
				{
					GridData attributesListLData = new GridData();
					attributesListLData.widthHint = 170;
					attributesListLData.verticalAlignment = GridData.FILL;
					attributesList = new List(attrubutesGroup, SWT.NONE|SWT.BORDER);
					attributesList.setLayoutData(attributesListLData);
					attributesList.add("anrgument");
					attributesList.add("anrgument1");
					attributesList.add("anrgument2");
					attributesList.add("anrgument3");
					attributesList.add("anrgument4");
					attributesList.add("anrgument5");
					attributesList.add("anrgument6");
					attributesList.addMouseListener(new MouseAdapter() {
						public void mouseDown(MouseEvent evt) {
							attributesListMouseDown(evt);
						}
					});
						
					
				}
				{
					attributeProperties = new Composite(
						attrubutesGroup,
						SWT.NONE);
					{
						attributeName = new Label(attributeProperties, SWT.NONE);
						attributeName.setText("Attribute Name:");
					}
					{
						attribName = new Text(attributeProperties, SWT.NONE|SWT.BORDER);
						attribName.setText("Attribute Name");
					}
					GridLayout attributePropertiesLayout = new GridLayout();
					attributePropertiesLayout.makeColumnsEqualWidth = true;
					attributePropertiesLayout.numColumns = 2;
					GridData attributePropertiesLData = new GridData();
					attributePropertiesLData.widthHint = 202;
					attributePropertiesLData.verticalAlignment = GridData.FILL;
					attributeProperties.setLayoutData(attributePropertiesLData);
					attributeProperties.setLayout(attributePropertiesLayout);
				}
				{
					attributesButtons = new Composite(attrubutesGroup, SWT.NONE);
					GridLayout attributesButtonsLayout = new GridLayout();
					attributesButtonsLayout.makeColumnsEqualWidth = true;
					attributesButtonsLayout.numColumns = 2;
					attributesButtons.setLayout(attributesButtonsLayout);
					{
						addAttribute = new Button(attributesButtons, SWT.PUSH
							| SWT.CENTER);
						addAttribute.setText(" + ");
						addAttribute.addMouseListener(new MouseAdapter() {
							public void mouseDoubleClick(MouseEvent evt) {
								addAttributeMouseDoubleClick(evt);
							}
						});
					}
					{
						removeAttribute = new Button(
							attributesButtons,
							SWT.PUSH | SWT.CENTER);
						removeAttribute.setText(" - ");
					}
				}

			}
		}

		/**
		 * @param mainContents
		 */
		private void displayTagInfo(Composite mainContents) {
			{
				TagInfo = new Group(mainContents, SWT.NONE);
				GridLayout TagInfoLayout = new GridLayout();
				TagInfoLayout.makeColumnsEqualWidth = true;
				TagInfoLayout.numColumns = 2;
				TagInfo.setLayout(TagInfoLayout);
				GridData TagInfoLData = new GridData();
				TagInfo.setLayoutData(TagInfoLData);
				TagInfo.setText("Tag Info:");
				
				{
					isXMLcheck = new Button(TagInfo, SWT.CHECK | SWT.LEFT);
					isXMLcheck.setText("Is XML Style?");
				}
				
				{
					isSingleCheck = new Button(TagInfo, SWT.CHECK | SWT.LEFT);
					isSingleCheck.setText("Is Single?");
				}
				
			}
		}

		/**
		 * @param mainContents
		 */
		private void displayTagName(Composite mainContents) {
			{
			//	tagNameLabel = new Label(mainContents, SWT.NONE);
			//	tagNameLabel.setText("Tag Name:");
			}
			{
				tagName = new Text(mainContents, SWT.NONE|SWT.BORDER);
				GridData tagNameLData = new GridData();
				tagNameLData.widthHint = 400;
				tagName.setLayoutData(tagNameLData);
				tagName.setText("Enter Tag Name");
			}
		}
			
		protected void configureShell(Shell newShell){
			super.configureShell(newShell);
			newShell.setText(this.title);
			
		}
		protected void createButtonsForButtonBar(Composite parent) {
			createButton(parent, IDialogConstants.OK_ID, "OK", true);
			createButton(parent, IDialogConstants.CANCEL_ID,
					IDialogConstants.CANCEL_LABEL, false);
		}

		protected void buttonPressed(int buttonId) {
			if (buttonId == IDialogConstants.OK_ID) {
				// Grab the values from all the text fields and stick them in the fieldStore
				this.close();
			}
			super.buttonPressed(buttonId);

		}
		
		private void attributesListMouseDown(MouseEvent evt) {
			System.out.println("attributesList.mouseDown, event=" + evt + " " + attributesList.getItem(attributesList.getSelectionIndex()));
			
			//TODO add your code for attributesList.mouseDown
		}
	}
