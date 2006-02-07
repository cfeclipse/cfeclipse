package com.rohanclan.cfml.editors.ui;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.*;
import com.rohanclan.cfml.util.CFPluginImages;

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
	private Document toolbarXML = null;
	
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
		//load the XML and lets see what it is
		loadToolbars();
		
		//Create the root tabfolder
		CTabFolder cTabFolder1 = new CTabFolder(parent, SWT.NONE);
		
		NodeList tabs = toolbarXML.getElementsByTagName("tab");
		
		//Loop through the items making some nice tabs
		for(int t=0;t<tabs.getLength(); t++){
			Node tabgroup = tabs.item(t);
			String tabname = tabgroup.getAttributes().getNamedItem("name").getNodeValue();
		
			
			//TODO: Before we add a tab, we would need to check that it has children
			//This would help so that we have a snippets tab with no children at first.
			CTabItem cTabItem1 = new CTabItem(cTabFolder1, SWT.FILL);
			cTabItem1.setText(tabname);
			
			ToolBar toolBar1 = new ToolBar(cTabFolder1, SWT.FILL|SWT.HORIZONTAL);
			cTabItem1.setControl(toolBar1);
			
			for (Node child = tabgroup.getFirstChild(); child != null; child = child.getNextSibling()){
				if(child.getNodeType() == 1){
					NamedNodeMap attribs = child.getAttributes();
					String buttonName = attribs.getNamedItem("name").getNodeValue();
					String buttonType = attribs.getNamedItem("type").getNodeValue();
					String buttonValue = attribs.getNamedItem("value").getNodeValue();
					
					ToolItem toolItem1 = new ToolItem(toolBar1, SWT.NONE);
					toolItem1.setData(buttonValue);
					
					
					if(attribs.getNamedItem("image") !=null){
						toolItem1.setToolTipText(buttonName);
						toolItem1.setImage(CFPluginImages.get(attribs.getNamedItem("image").getNodeValue()));
						
					} else {
						toolItem1.setText(buttonName);
					}
					//toolItem1.setToolTipText("cfoutput");
					//
					
					if(buttonType.equals("cftag")){
						toolItem1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								ToolItem item = (ToolItem)evt.getSource();
								EditTagAction ecta = new EditTagAction(item.getData().toString(), shell);
								ecta.run();
							}
						});
					}
					else if(buttonType.equals("custom")){
						
						 toolItem1.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									ToolItem item = (ToolItem)evt.getSource();
									DynamicActionRunner darunner = new DynamicActionRunner(item.getData().toString());
								}
							});
					}
					else if(buttonType.equals("snippet")){
						
						 toolItem1.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									ToolItem item = (ToolItem)evt.getSource();
									InsertSnippetAction iSA = new InsertSnippetAction(item.getData().toString(), shell);
									iSA.run();
								}
							});
					}
				}
			}
		}
		
		
			cTabFolder1.setSelection(0);
			cTabFolder1.moveAbove(editor);
			return parent;
	}
	
	private void loadToolbars(){
		URL toolbarConfigURL = null;
		try{
			toolbarConfigURL = new URL(
					CFMLPlugin.getDefault().getBundle().getEntry("/"),
					"dictionary/"
				);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setCoalescing(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			URL local = Platform.asLocalURL(toolbarConfigURL);
			
			URL configurl = Platform.resolve(new URL(local, "toolbars/cfmltoolbar.xml"));
						
			toolbarXML = builder.parse(configurl.getFile());
		}
		catch(Exception e){
			System.out.println("---- Error Loading the CFML Toolbar ----");
			e.printStackTrace(System.err);
			
		}
	
	}
	
}
