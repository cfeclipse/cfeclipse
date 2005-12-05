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
 * This will be the class that builds the toolbar in the CFEditor dialog.
 * 
 * @author mdrew
 *
 */
public class CFMLEditorToolbar {

	
	public CFMLEditorToolbar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Composite getTabs(Composite cfeditor){
		//Make a new composite
		//Display display = Display.getDefault();
		//Shell shell = new Shell(display);
		//Composite tabbedisp = new Composite(shell, SWT.NONE);
		
		GridLayout thisLayout = new GridLayout();
		thisLayout.makeColumnsEqualWidth = true;
		cfeditor.setLayout(thisLayout);
		
		Composite child = new Composite(cfeditor, 0);
       	child.setLayoutData(new GridData(4, 4, true, false, 1, 1));
       	GridLayout clayout = new GridLayout();
       	clayout.numColumns = 1;
	        clayout.horizontalSpacing = 0;
	        clayout.marginHeight = 0;
	        clayout.marginWidth = 0;
	        clayout.verticalSpacing = 0;
	        child.setLayout(clayout);
	        child =  makeTabs(child);
		
		
		/*
		{
			GridData cfeditorLData = new GridData();
			cfeditorLData.verticalSpan = 2;
			cfeditorLData.grabExcessHorizontalSpace = true;
			cfeditorLData.grabExcessVerticalSpace = true;
			cfeditor.setLayoutData(cfeditorLData);
		}
		*/
	      
	      //makeTabs(parent);
		return cfeditor;
	}

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
			return parent;
	}
	
}
