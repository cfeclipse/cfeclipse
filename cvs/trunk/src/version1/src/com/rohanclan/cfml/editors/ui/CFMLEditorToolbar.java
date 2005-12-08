package com.rohanclan.cfml.editors.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.dictionary.Tag;
import com.rohanclan.cfml.editors.actions.EditCustomTagAction;
import com.rohanclan.cfml.editors.actions.EditTagAction;
import com.rohanclan.cfml.views.dictionary.TagItem;

/**
 * This  class builds the toolbar in the CFEditor dialog.
 * 
 * It has been externalised from CFMLEditor so that it is only called if a preference is set
 * 
 * @author mdrew
 *
 */
public class CFMLEditorToolbar {
	private Shell shell;
	
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
		
		Composite parent = cfeditor.getParent();

		this.shell = cfeditor.getShell();
		
		
		GridLayout parentLayout = new GridLayout();
		parentLayout.makeColumnsEqualWidth = true;
		parentLayout.numColumns=1;
		parentLayout.marginLeft = 0;
		parentLayout.marginRight = 0;
		
		
		parent.setLayout(parentLayout);
		
		GridData cfeditorLData = new GridData(GridData.FILL);
		cfeditorLData.grabExcessHorizontalSpace = true;
		cfeditorLData.grabExcessVerticalSpace = true;
		cfeditorLData.horizontalAlignment = GridData.FILL;
		cfeditorLData.verticalAlignment = GridData.FILL;
		cfeditorLData.horizontalIndent = 0;
		cfeditor.setLayoutData(cfeditorLData);
		FillLayout cfeditorLayout = (FillLayout)cfeditor.getLayout();
		cfeditorLayout.marginHeight = 0;
		cfeditorLayout.marginWidth = 0;
		
		parent = makeTabs(parent, cfeditor);
			
		return cfeditor;
	}

	/**
	 * This method builds the top navigation, it will be from an XML file so that the actions can be modified
	 * @param parent
	 * @return
	 */
	private Composite makeTabs(Composite parent, Composite editor) {
		
		CTabFolder cTabFolder1 = new CTabFolder(parent, SWT.NONE);
			
		//Manually Create the tabs to get them working
		
		//First tab
		{
		CTabItem cTabItem1 = new CTabItem(cTabFolder1, SWT.FILL);
		cTabItem1.setText("CFML Basic");
		
			ToolBar toolBar1 = new ToolBar(cTabFolder1, SWT.FILL|SWT.HORIZONTAL);
			cTabItem1.setControl(toolBar1);
		
			ToolItem toolItem1 = new ToolItem(toolBar1, SWT.NONE);
			toolItem1.setToolTipText("cfoutput");
			toolItem1.setImage(CFPluginImages.get(CFPluginImages.ICON_TAG));
			
			
			toolItem1.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					EditTagAction ecta = new EditTagAction("cfoutput", shell);
					ecta.run();
				}
			});
		
		}
		/*
		
		{
				
				for(int j=0;j<5;j++){
					CTabItem cTabItem1 = new CTabItem(cTabFolder1, SWT.FILL);
					cTabItem1.setText("CFML Tab " + j);
					{
						ToolBar toolBar1 = new ToolBar(cTabFolder1, SWT.FILL|SWT.HORIZONTAL);
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
		 */
		
			cTabFolder1.setSelection(0);
			cTabFolder1.moveAbove(editor);
			return parent;
	}
	
}
