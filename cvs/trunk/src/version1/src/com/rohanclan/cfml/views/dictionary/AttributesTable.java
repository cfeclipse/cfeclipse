package com.rohanclan.cfml.views.dictionary;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import com.rohanclan.cfml.dictionary.Function;
import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.Tag;

public class AttributesTable {

	public AttributesTable(Composite parent ){
		this.addChildControls(parent);
		

		
	}
	
	private Table table;
	private TableViewer tableViewer;
	private Button closeButton;
	private TableEditor editor;
	
	private  AttributesList attributesList = new AttributesList();
	
	//Sete the table property names
	private final String ATTRIBUTE_COLUMN = "attribute";
	private final String ATTRIBUTE_VALUE = "value";
	private final String ATTRIBUTE_TYPE = "type";
	
	private String[] columnNames = new String[]{
			ATTRIBUTE_COLUMN,
			ATTRIBUTE_VALUE
			};
	
	private void addChildControls(Composite parent){
//		A simple table to get started yes?
		table = new Table(parent, SWT.BORDER|SWT.FULL_SELECTION);
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
			TableColumn tc1 = new TableColumn(table, SWT.LEFT);
			TableColumn tc2 = new TableColumn(table, SWT.LEFT);
			TableColumn tc3 = new TableColumn(table, SWT.LEFT);
			tc1.setText(this.ATTRIBUTE_COLUMN);
			tc2.setText(this.ATTRIBUTE_VALUE);
			tc3.setText(this.ATTRIBUTE_TYPE);
			
			tc1.setWidth(100);
			tc2.setWidth(100);
			tc3.setWidth(100);
			
			table.setHeaderVisible(true);
			
	}
	
	public void setAttributes(Object object){
		
		//you are setting my attributes
		
		System.out.println(object.getClass());
		if(object instanceof TagItem){
			Tag tag = ((TagItem)object).getTag();
			this.fillAttributes(tag.getParameters());
		}
		else if(object instanceof FunctionItem){
			Function func = ((FunctionItem)object).getFunction();
			this.fillAttributes(func.getParameters());
		
		}
		
	}
	private void fillAttributes(Set attribs){
		//We clear them first
		//TableEditor editor = new TableEditor(table);
		// TODO: Clear the table of Editors. Which for some reason dont dispose with the command below
		table.removeAll();
		
		if(attribs != null){
			
			Iterator iter = attribs.iterator();
			while(iter.hasNext()){
				Parameter param = (Parameter)iter.next();
				TableItem tedit =   new TableItem(table, SWT.BORDER); 
				editor = new TableEditor(table);
				//Add a static attribute name label
				Label attName = new Label(table, SWT.NONE);
				attName.setText(param.getName());
				editor.setEditor(attName, tedit, 0);
				
				/*
				Text attName = new Text(table, SWT.NONE|SWT.READ_ONLY);
				attName.setText(param.getName());
				editor.grabHorizontal = true;
				*/
				 
				 
				 editor = new TableEditor(table);
			      CCombo combo = new CCombo(table, SWT.NONE);
			      combo.add("testing");
			      //editor.grabHorizontal = true;
			      editor.setEditor(combo, tedit, 1);
			      
			     //Add static attribute type label
			      Label atttype = new Label(table, SWT.NONE);
			      atttype.setText(param.getType());
					editor.setEditor(atttype, tedit, 2);
			      
			      
			     
				
				//tedit.setText(new String[]{counter + " " + param.getName(),"size" + param.getValues(), param.getType()});
				/* TODO: Use this to populate attribute drop down if required
				if(param.getValues().size() > 0){
					Iterator paramIter = param.getValues().iterator();
					CCombo combo = new CCombo(table, SWT.NONE);
					while(paramIter.hasNext()){
						Object params = paramIter.next();
							combo.add(params.getClass().toString());
					}
					tedit.setData(combo);
				}
				*/
			}
		}
		
	}
	
	
	
	
	
	
}
