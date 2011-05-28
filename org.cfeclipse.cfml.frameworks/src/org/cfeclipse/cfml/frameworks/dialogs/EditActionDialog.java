/*
 * Created on Mar 30, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
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
package org.cfeclipse.cfml.frameworks.dialogs;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jdom.Attribute;
import org.jdom.Element;

/**
 * @author markdrew
 *
 */
public class EditActionDialog extends Dialog {

	private Element selElement;
	private Log logger = LogFactory.getLog(EditActionDialog.class);
	private String[] actionClasses = {"GoToFile","InsertText","AddChild"};
	private String[] actionTypes = {"right_click", "left_click"};
	
	//the fields, can we get them anyway?
	private Combo classCombo;
	private Text frameworkText;
	private Text nodeText;
	private Text labelText;
	private Combo typeCombo;
	private Text parentText;
	private Text actionText;
		protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 2;
		
		//Add Items for editing... 
	
		
		GridData entryBoxGD = new GridData();
		entryBoxGD.widthHint = 200;
	
		
		//Action Drop down
		final Label actionLabel = new Label(container, SWT.NONE);
			actionLabel.setText("Action :");
			classCombo = new Combo(container, SWT.NONE|SWT.READ_ONLY);
			classCombo.setItems(actionClasses);
			//set the selection
			String[] strings = selElement.getAttributeValue("class").split("\\.");
			classCombo.setText(strings[strings.length-1]);

		
		
		//Framework Attribute
		final Label frameworkLabel = new Label(container, SWT.NONE);
			frameworkLabel.setText("Framework ID:");
			frameworkText = new Text(container, SWT.BORDER);
			frameworkText.setLayoutData(entryBoxGD);
			frameworkText.setText(selElement.getAttributeValue("framework"));
	
//		Node Attribute
			final Label nodeLabel = new Label(container, SWT.NONE);
			nodeLabel.setText("Node Name:");
				nodeText = new Text(container, SWT.BORDER);
				nodeText.setLayoutData(entryBoxGD);
				nodeText.setText(selElement.getAttributeValue("node"));
		
	//	Label Attribute
			final Label labelLabel = new Label(container, SWT.NONE);
			labelLabel.setText("Display Label:");
				labelText = new Text(container, SWT.BORDER);
				labelText.setLayoutData(entryBoxGD);
				labelText.setText(selElement.getAttributeValue("label"));
				
//		Action Type Combo		
			final Label actionType = new Label(container, SWT.NONE);
				actionType.setText("Event to trigger :");
				typeCombo = new Combo(container, SWT.NONE|SWT.READ_ONLY);
				typeCombo.setItems(actionTypes);
				typeCombo.setText(selElement.getAttributeValue("type"));	
				
//	Parent Attribute
			final Label parentLabel = new Label(container, SWT.NONE);
				parentLabel.setText("Node Parent:");
			
				parentText = new Text(container, SWT.BORDER);
				parentText.setLayoutData(entryBoxGD);
				String parentValue = selElement.getAttributeValue("parent");
				if(parentValue == null){
					parentValue = "";
				}
				parentText.setText(parentValue);				
				
// The action text				
			final Label actionLable = new Label(container, SWT.NONE|SWT.WRAP);
				actionLable.setText("Action Function: ");
				
				GridData actionGD = new GridData();
				actionGD.widthHint = 200;
				actionGD.heightHint = 100;
				actionText = new Text(container, SWT.BORDER);
				actionText.setLayoutData(actionGD);
				actionText.setText(selElement.getTextNormalize());
		
		//Loop through the attributes and create a text box
		
		return super.createDialogArea(parent);
	}
	
	
	private void createEntryBox(Composite container, String label, String value){
		
		final Label nodeLabel = new Label(container, SWT.NONE);
		nodeLabel.setText(label + " :");
		
		GridData entryBoxGD = new GridData();
		entryBoxGD.widthHint = 200;
		final Text nodeText = new Text(container, SWT.BORDER);
		nodeText.setLayoutData(entryBoxGD);
		nodeText.setText(value);
	}

	/**
	 * @param parentShell
	 */
	public EditActionDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	public void create() {
		// TODO Auto-generated method stub
		super.create();
	}

	/**
	 * @param element
	 */
	public void setSelectedElement(Element element) {
		this.selElement = element;
		
	}


	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			//Set the values back to the element
			
			
			
			//get all the values from the dialogs and treat the accordingly by setting them to the element
			logger.debug(this.dialogArea.getData());
		}
		super.buttonPressed(buttonId);
	}

}
