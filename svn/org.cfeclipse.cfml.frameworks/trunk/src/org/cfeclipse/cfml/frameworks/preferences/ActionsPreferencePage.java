package org.cfeclipse.cfml.frameworks.preferences;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.util.Util;
import org.jdom.Document;
import org.jdom.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.Activator;
import org.cfeclipse.cfml.frameworks.actions.ActionManager;
import org.cfeclipse.cfml.frameworks.dialogs.EditActionDialog;
import org.cfeclipse.cfml.frameworks.views.TreeParentNode;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class ActionsPreferencePage extends PreferencePage implements
IWorkbenchPreferencePage {
	private Table tableActions;
	
	private static final int ACTION_FRAMEWORK_COLUMN_INDEX = 0;
	private static final int ACTION_NODE_COLUMN_INDEX = 1;
	private static final int ACTION_CLASS_COLUMN_INDEX = 2;
	private static final int ACTION_LABEL_COLUMN_INDEX = 3;
	private Document actionsDocument ;
	private ActionManager actionManager = new ActionManager();
	private TreeParentNode filterElement;
	private Log logger = LogFactory.getLog(ActionsPreferencePage.class);

	private Button editButton;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		GridData gridData = null;
		int widthHint;

		// Create the composite for the display.
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout());
		
		tableActions = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.H_SCROLL | SWT.V_SCROLL);
		tableActions.setHeaderVisible(true);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 400;
		gridData.horizontalSpan = 2;
		tableActions.setLayoutData(gridData);
		//Create the headers
		
		final TableColumn tableColumnFramework = new TableColumn(tableActions,
				SWT.NONE, ACTION_FRAMEWORK_COLUMN_INDEX);
		tableColumnFramework.setText("Framework");
		
		
		final TableColumn tableColumnNode = new TableColumn(tableActions,
				SWT.NONE, ACTION_NODE_COLUMN_INDEX);
		tableColumnNode.setText("Node");
		

		final TableColumn tableColumnClass = new TableColumn(tableActions,
				SWT.NONE, ACTION_CLASS_COLUMN_INDEX);
		tableColumnClass.setText("Action");
		
		final TableColumn tableColumnLabel = new TableColumn(tableActions,
				SWT.NONE, ACTION_LABEL_COLUMN_INDEX);
		tableColumnLabel.setText("Label");
		
		
		tableActions.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				editButton.setEnabled(true);
			}

			public final void widgetDefaultSelected(final SelectionEvent e) {
				editButton.setEnabled(true);
			
			}
		});
		
		

		//Need to fill it to see what is in it!
//		 A composite for the buttons.
		final Composite buttonBar = new Composite(composite, SWT.NONE);
		buttonBar.setLayout(new GridLayout(2, false));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		buttonBar.setLayoutData(gridData);

		// A button for editing the current selection.
		editButton = new Button(buttonBar, SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		editButton.setText("Edit"); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, editButton.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		editButton.setLayoutData(gridData);
		editButton.addSelectionListener(new SelectionListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public final void widgetDefaultSelected(final SelectionEvent event) {
				//selectedTableKeyBindings();
				//logger.debug("Selected something");
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
				
				//Open the dialog, we shall try and get whatever it is we are getting...
				
				Object selElement = tableActions.getSelection()[0].getData();
				if (selElement instanceof Element) {
					Element new_name = (Element) selElement;
					EditActionDialog ead = new EditActionDialog(tableActions.getShell());
					ead.setSelectedElement((Element)selElement);
					ead.open();
					
				}
				
				
			}
		});
		editButton.setEnabled(false);
		
		updateActionList();
		
		return parent;
	}

	/**
	 * Populates the tree with the current actions
	 */
	private void updateActionList() {
		//Load the XML
		List actionNodes = actionManager.getActionNodes();

		for (Iterator iter = actionNodes.iterator(); iter.hasNext();) {
			Element actionElement = (Element) iter.next();
			if(filterElement != null && filterElement.getElement() != null && 
				actionElement.getAttributeValue("node").equalsIgnoreCase(filterElement.getElement().getName())
				&& actionElement.getAttributeValue("framework").equalsIgnoreCase(filterElement.getFrameworkType()))
			{					
				final TableItem tableItem = new TableItem(tableActions,	SWT.NULL);
				tableItem.setData(actionElement);
				tableItem.setText(ACTION_FRAMEWORK_COLUMN_INDEX, actionElement.getAttributeValue("framework"));
				tableItem.setText(ACTION_CLASS_COLUMN_INDEX, actionElement.getAttributeValue("class"));
				tableItem.setText(ACTION_LABEL_COLUMN_INDEX, actionElement.getAttributeValue("label"));
				tableItem.setText(ACTION_NODE_COLUMN_INDEX, actionElement.getAttributeValue("node"));
			}
			else if( filterElement == null || filterElement.getElement() == null ){
				final TableItem tableItem = new TableItem(tableActions,	SWT.NULL);
				tableItem.setData(actionElement);
				tableItem.setText(ACTION_FRAMEWORK_COLUMN_INDEX, actionElement.getAttributeValue("framework"));
				tableItem.setText(ACTION_CLASS_COLUMN_INDEX, actionElement.getAttributeValue("class"));
				tableItem.setText(ACTION_LABEL_COLUMN_INDEX, actionElement.getAttributeValue("label"));
				tableItem.setText(ACTION_NODE_COLUMN_INDEX, actionElement.getAttributeValue("node"));
			}
			
		}
		//Using XPath get the actions
		for (int i = 0; i < tableActions.getColumnCount(); i++) {
			tableActions.getColumn(i).pack();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param selNode
	 */
	public void setFilter(TreeParentNode selNode) {
		this.filterElement = selNode;
		
	}

	
}