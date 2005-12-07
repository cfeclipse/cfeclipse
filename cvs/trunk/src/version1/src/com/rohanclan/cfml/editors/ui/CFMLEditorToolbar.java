package com.rohanclan.cfml.editors.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * This  class builds the toolbar in the CFEditor dialog.
 * 
 * It has been externalised from CFMLEditor so that it is only called if a preference is set
 * 
 * @author mdrew
 *
 */
public class CFMLEditorToolbar {

	
	public CFMLEditorToolbar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * this method sets the layout, with the toolbar at the top, and the editor at the bottom
	 * @param cfeditor
	 * @return
	 */
	public Composite getTabs(Composite cfeditor){
		
		GridLayout parentLayout = new GridLayout();
		parentLayout.makeColumnsEqualWidth = true;
		parentLayout.numColumns=1;
		cfeditor.getParent().setLayout(parentLayout);
		
		//Get the toolbars
		cfeditor =  makeTabs(cfeditor);
			
		GridData cfeditorLData = new GridData();
		cfeditorLData.grabExcessHorizontalSpace = true;
		cfeditorLData.grabExcessVerticalSpace = true;
		cfeditor.setLayoutData(cfeditorLData);

		return cfeditor;
	}

	/**
	 * This method builds the top navigation, it will be from an XML file so that the actions can be modified
	 * @param parent
	 * @return
	 */
	private Composite makeTabs(Composite parent) {
		CTabFolder cTabFolder1 = new CTabFolder(parent, SWT.NONE);
			{
				
				for(int j=0;j<5;j++){
					CTabItem cTabItem1 = new CTabItem(cTabFolder1, SWT.NONE);
					cTabItem1.setText("CFML Tab " + j);
					{
						ToolBar toolBar1 = new ToolBar(cTabFolder1, SWT.NONE);
						cTabItem1.setControl(toolBar1);
						{
							for(int i=0;i<10;i++){
								ToolItem toolItem1 = new ToolItem(toolBar1, SWT.NONE);
								toolItem1.setText("butt" + i);
							}
						}
						
					}
				}
			}
			cTabFolder1.setSelection(0);
			return parent;
	}
	
}
