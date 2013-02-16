package org.cfeclipse.cfml.frameworks.preferences;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.ConfigLoader;
import org.cfeclipse.cfml.frameworks.dialogs.FrameworkEditDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class FrameworksPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	
	
	private Table tableFrameworks;
	private static final int FRAMEWORK_NAME_COL_INDEX = 0;
	private static final int FRAMEWORK_ID_COL_INDEX = 1;
	private Button editButton;
	private Button addButton;
	
	private Log logger = LogFactory.getLog(FrameworksPreferencePage.class);
	

	public FrameworksPreferencePage() {
		// TODO Auto-generated constructor stub
		setDescription("Here is a list of the available frameworks that have been defined. You can add your framwork here or change the existing configuration of a framework.");
		
	}

	public FrameworksPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public FrameworksPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		
		final Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = null;
		int widthHint;

		// Create the composite for the display.
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout());
		
		tableFrameworks = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.H_SCROLL | SWT.V_SCROLL);
		tableFrameworks.setHeaderVisible(true);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 400;
		gridData.horizontalSpan = 2;
		tableFrameworks.setLayoutData(gridData);
		//Create the headers
		
		final TableColumn tableColumnName= new TableColumn(tableFrameworks,
				SWT.NONE, FRAMEWORK_NAME_COL_INDEX);
		tableColumnName.setText("Framework Name");
		
		final TableColumn tableColumnId = new TableColumn(tableFrameworks,
				SWT.NONE, FRAMEWORK_ID_COL_INDEX);
		tableColumnId.setText("Framwork Id");
		
		fillTable();
		
		

		/*  Add the button bar! */
		final Composite buttonBar = new Composite(composite, SWT.NONE);
		buttonBar.setLayout(new GridLayout(2, false));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		buttonBar.setLayoutData(gridData);

		
//		 A button for adding a framework
		addButton = new Button(buttonBar, SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		addButton.setText("Add Framework"); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, addButton.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		addButton.setLayoutData(gridData);
		
		
		addButton.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {
			
				FrameworkEditDialog fed = new FrameworkEditDialog(getShell());
				fed.open();
				
				
			}
			public void mouseUp(MouseEvent e) {}
		});
		
		
		
		// A button for editing the current selection.
		editButton = new Button(buttonBar, SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		editButton.setText("Edit Framework"); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, editButton.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		editButton.setLayoutData(gridData);
		
		editButton.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {
				//Get the selection
				Object data = tableFrameworks.getSelection()[0].getData();

				if (data instanceof Element) {
					Element fwxElement = (Element) data;
					FrameworkEditDialog fed = new FrameworkEditDialog(getShell(), fwxElement);
					fed.open();
				}
				
				
				
			}
			public void mouseUp(MouseEvent e) {}
		});
		
		
		return composite;
	}

	/**
	 * 
	 */
	private void fillTable() {
		Document document = ConfigLoader.loadConfig("frameworks.xml");
		
		
		
		try {
			XPath x = XPath.newInstance("//framework");
			List<?> list = x.selectNodes(document);
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				if (element instanceof Element) {
					Element frameworkElement = (Element) element;
				
					final TableItem tableItem = new TableItem(tableFrameworks,	SWT.NULL);
					tableItem.setData(frameworkElement);
					tableItem.setText(FRAMEWORK_NAME_COL_INDEX, frameworkElement.getAttributeValue("name"));
					tableItem.setText(FRAMEWORK_ID_COL_INDEX, frameworkElement.getAttributeValue("id"));
					
				}
				
			}
			for (int i = 0; i < tableFrameworks.getColumnCount(); i++) {
				tableFrameworks.getColumn(i).pack();
			}
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//We loop through the frameworks and fill the columns, adding the element as the table data
		
		
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

}
