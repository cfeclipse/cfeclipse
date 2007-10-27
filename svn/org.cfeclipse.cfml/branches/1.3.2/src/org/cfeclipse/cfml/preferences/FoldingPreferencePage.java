package org.cfeclipse.cfml.preferences;

//import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.help.WorkbenchHelp;


// Referenced classes of package org.eclipse.ui.internal.editors.text:
//			TextEditorMessages, EditorsPlugin, OverlayPreferenceStore, ColorEditor, 
//			StatusInfo

public class FoldingPreferencePage extends PreferencePage
	implements IWorkbenchPreferencePage
{

	private OverlayPreferenceStore fOverlayStore = null;
	private Map fCheckBoxes = null;
	private SelectionListener fCheckBoxListener = null;
	private Map fTextFields = null;
	private ModifyListener fTextFieldListener = null;
	private ArrayList fNumberFields = null;
	private ModifyListener fNumberFieldListener = null;
	//private List fAppearanceColorList = null;
	//private ColorEditor fAppearanceColorEditor = null;
	//private Button fAppearanceColorDefault = null;
	private boolean fFieldsInitialized = false;
	private ArrayList fMasterSlaveListeners = null;
	//private CFMLPreferenceManager preferenceManager = null;

	public FoldingPreferencePage()
	{
	    //preferenceManager = new CFMLPreferenceManager();
		fCheckBoxes = new HashMap();
		fCheckBoxListener = new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent selectionevent)
			{
			}

			public void widgetSelected(SelectionEvent e)
			{
				Button button = (Button)e.widget;
				fOverlayStore.setValue((String)fCheckBoxes.get(button), button.getSelection());
			}

		};
		fTextFields = new HashMap();
		fTextFieldListener = new ModifyListener() {

			public void modifyText(ModifyEvent e)
			{
			    textFieldChanged((Text)e.widget);
				
			}

		};
		fNumberFields = new ArrayList();
		fNumberFieldListener = new ModifyListener() {

			public void modifyText(ModifyEvent e)
			{
				numberFieldChanged((Text)e.widget);
			}

		};
		fFieldsInitialized = false;
		fMasterSlaveListeners = new ArrayList();
		setDescription("Folding Settings");
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		fOverlayStore = createOverlayStore();
	   
	}

	private OverlayPreferenceStore createOverlayStore()
	{
		ArrayList overlayKeys = new ArrayList();
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_ENABLE_CODE_FOLDING));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, FoldingPreferenceConstants.P_MINIMUM_CODE_FOLDING_LINES));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_CFMLCOMMENTS_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_CFMLCOMMENTS_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_HTMLCOMMENTS_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_HTMLCOMMENTS_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG1_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG1_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG1_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG2_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG2_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG2_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG3_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG3_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG3_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG4_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG4_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG4_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG5_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG5_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG5_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG6_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG6_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG6_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG7_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG7_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG7_NAME));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG8_COLLAPSE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, FoldingPreferenceConstants.P_FOLDING_TAG8_FOLD));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, FoldingPreferenceConstants.P_FOLDING_TAG8_NAME));
		OverlayPreferenceStore.OverlayKey keys[] = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return new OverlayPreferenceStore(getPreferenceStore(), keys);
	}

	public void init(IWorkbench iworkbench)
	{
	    
	}

	public void createControl(Composite parent)
	{
		super.createControl(parent);
		WorkbenchHelp.setHelp(getControl(), "org.eclipse.ui.text_editor_preference_page_context");
	}
	
	
	
	
	private Control createAppearancePage(Composite parent)
	{
		Composite appearanceComposite = new Composite(parent, 0);
		GridLayout layout = new GridLayout(1, true);
		layout.verticalSpacing = 5;
		layout.marginWidth = 5;
		appearanceComposite.setLayout(layout);
		
		// Enable/Disable Code Folding
		String label = "Enable code folding:";
		addCheckBox(appearanceComposite, label, FoldingPreferenceConstants.P_ENABLE_CODE_FOLDING, 4);

		createTable(appearanceComposite);
		

		label = "Minimum number of lines for folding:";
		addTextField(appearanceComposite, label, FoldingPreferenceConstants.P_MINIMUM_CODE_FOLDING_LINES, 2, 4, true);
		
		return appearanceComposite;
	}

	protected Control createContents(Composite parent)
	{
	   
		fOverlayStore.load();
		fOverlayStore.start();
		Control control = createAppearancePage(parent);
		
		initialize();
		Dialog.applyDialogFont(control);

		return control;
	}

	
	
	
	private Table createTable(Composite parent) {
		Table table = new Table(parent, SWT.MULTI | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Enable folding");

		TableColumn column2 = new TableColumn(table, SWT.NONE);
		column2.setText("Initially Collapse");

	
		
		TableColumn column3 = new TableColumn(table, SWT.NONE);
		column3.setText("Tag name");

		createTableItems(table, "folding.cfmlcomments", false);
		createTableItems(table, "folding.htmlcomments", false);
		createTableItems(table, "folding.tag1", true);
		createTableItems(table, "folding.tag2", true);
		createTableItems(table, "folding.tag3", true);
		createTableItems(table, "folding.tag4", true);
		createTableItems(table, "folding.tag5", true);
		createTableItems(table, "folding.tag6", true);
		createTableItems(table, "folding.tag7", true);
		createTableItems(table, "folding.tag8", true);
		
		

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).pack();
		}
		table.setSize(table.computeSize(SWT.DEFAULT, 300));

		return table;

	}
	
	private void createTableItems(Table table, String key, boolean editable) {

		TableItem item = new TableItem(table, SWT.NONE);
		String labelText = key;

		TableEditor foldEditor = new TableEditor(table);
		final Button foldCheckBox = new Button(table, SWT.CHECK);
		foldCheckBox.pack();
		foldEditor.minimumWidth = foldCheckBox.getSize().x;
		foldEditor.horizontalAlignment = SWT.CENTER;
		foldEditor.setEditor(foldCheckBox, item, 0);
		fCheckBoxes.put(foldCheckBox,key+".fold");
		foldCheckBox.addSelectionListener(fCheckBoxListener);

		TableEditor collapseEditor = new TableEditor(table);
		final Button initialCheckBox = new Button(table, SWT.CHECK);
		initialCheckBox.pack();
		collapseEditor.minimumWidth = initialCheckBox.getSize().x;
		collapseEditor.horizontalAlignment = SWT.CENTER;
		collapseEditor.setEditor(initialCheckBox, item, 1);
		fCheckBoxes.put(initialCheckBox,key+".collapse");
		initialCheckBox.addSelectionListener(fCheckBoxListener);

		if (editable) {
		    TableEditor regionEditor = new TableEditor(table);
		    
			final Text regionText = new Text(table,SWT.SINGLE);
			regionText.setText(key);
			regionText.pack();
			
			regionEditor.minimumWidth = 90;
			regionEditor.horizontalAlignment = SWT.LEFT;
			regionEditor.setEditor(regionText, item, 2);
			fTextFields.put(regionText,key+".name");
			regionText.addModifyListener(fTextFieldListener);
			
			regionText.addListener(SWT.Modify, new Listener() {

				public void handleEvent(Event event) {
					foldCheckBox.setEnabled(regionText.getText().length() > 0);
					initialCheckBox.setEnabled(regionText.getText().length() > 0);
				}

			});
			
		}
		else {
		    if (key.equalsIgnoreCase("folding.CFMLComments")) {
		        labelText = "CFML Comments";
		    }
		    else {
		        labelText = "HTML Comments";
		    }
		    item.setText(2, labelText);
		}
		foldCheckBox.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				initialCheckBox.setEnabled(foldCheckBox.getSelection());
			}

		});
		
		

	}
	
	
	
	

	
	
	
	
	
	private void initialize()
	{
		initializeFields();
		
	}

	private void initializeFields()
	{
		Button b;
		String key;
		for(Iterator e = fCheckBoxes.keySet().iterator(); e.hasNext(); b.setSelection(fOverlayStore.getBoolean(key)))
		{
			b = (Button)e.next();
			key = (String)fCheckBoxes.get(b);
			
		}

		Text t;
		for(Iterator e = fTextFields.keySet().iterator(); e.hasNext(); t.setText(fOverlayStore.getString(key)))
		{
			t = (Text)e.next();
			key = (String)fTextFields.get(t);
			
		}

		fFieldsInitialized = true;
		updateStatus(validatePositiveNumber("0"));
		SelectionListener listener;
		for(Iterator iter = fMasterSlaveListeners.iterator(); iter.hasNext(); listener.widgetSelected(null))
			listener = (SelectionListener)iter.next();
		
	    

	}


	public boolean performOk()
	{
		fOverlayStore.propagate();
		CFMLPlugin.getDefault().savePluginPreferences();
		return true;
	}

	protected void performDefaults()
	{
		fOverlayStore.loadDefaults();
		initializeFields();
	    
		super.performDefaults();
	}

	public void dispose()
	{
		if(fOverlayStore != null)
		{
			fOverlayStore.stop();
			fOverlayStore = null;
		}
		super.dispose();
	}

	private Button addCheckBox(Composite parent, String label, String key, int indentation)
	{
		Button checkBox = new Button(parent, 32);
		checkBox.setText(label);
		GridData gd = new GridData(32);
		gd.horizontalIndent = indentation;
		gd.horizontalSpan = 2;
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(fCheckBoxListener);
		fCheckBoxes.put(checkBox, key);
		return checkBox;
	}

	private Control addTextField(Composite composite, String label, String key, int textLimit, int indentation, boolean isNumber)
	{
		Label labelControl = new Label(composite, 0);
		labelControl.setText(label);
		GridData gd = new GridData(32);
		gd.horizontalIndent = indentation;
		labelControl.setLayoutData(gd);
		Text textControl = new Text(composite, 2052);
		gd = new GridData(32);
		gd.widthHint = convertWidthInCharsToPixels(textLimit + 1);
		textControl.setLayoutData(gd);
		textControl.setTextLimit(textLimit);
		fTextFields.put(textControl, key);
		if(isNumber)
		{
			fNumberFields.add(textControl);
			textControl.addModifyListener(fNumberFieldListener);
		} else
		{
			textControl.addModifyListener(fTextFieldListener);
		}
		return textControl;
	}

	/* private Control addLabel(Composite composite, String label, int indentation)
	{
		Label labelControl = new Label(composite, 0);
		labelControl.setText(label);
		GridData gd = new GridData(32);
		gd.horizontalIndent = indentation;
		labelControl.setLayoutData(gd);
		
		return labelControl;
	} */

	/* private void createDependency(final Button master, String masterKey, final Control slave)
	{
		indent(slave);
		boolean masterState = fOverlayStore.getBoolean(masterKey);
		slave.setEnabled(masterState);
		SelectionListener listener = new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				slave.setEnabled(master.getSelection());
			}

			public void widgetDefaultSelected(SelectionEvent selectionevent)
			{
			}

		};
		master.addSelectionListener(listener);
		fMasterSlaveListeners.add(listener);
	} */

	/* private static void indent(Control control)
	{
		GridData gridData = new GridData();
		gridData.horizontalIndent = 20;
		control.setLayoutData(gridData);
	} */

	private void numberFieldChanged(Text textControl)
	{
		String number = textControl.getText();
		IStatus status = validatePositiveNumber(number);
		if(!status.matches(4))
			fOverlayStore.setValue((String)fTextFields.get(textControl), number);
		updateStatus(status);
	}

	private void textFieldChanged(Text textControl)
	{
		String tagName = textControl.getText();
		IStatus status = validateCFTagName(tagName);
		if(!status.matches(4))
		    fOverlayStore.setValue((String)fTextFields.get(textControl), textControl.getText());
		updateStatus(status);
	}

	private IStatus validatePositiveNumber(String number)
	{
		StatusInfo status = new StatusInfo();
		if(number.length() == 0)
			status.setError("Empty input");
		else
			try
			{
				int value = Integer.parseInt(number);
				if(value < 0)
					status.setError(number + " is not a valid input.");
			}
			catch(NumberFormatException _ex)
			{
			    status.setError(number + " is not a valid input.");
			}
		return status;
	}


	private IStatus validateCFTagName(String tagName)
	{
		StatusInfo status = new StatusInfo();
		if(!tagName.toLowerCase().startsWith("cf") 
		        && tagName.trim().length() > 0) {
		    status.setError("Automatic code folding currently only supports ColdFusion tags.");
		}

		setValid(false);
		applyToStatusLine(this, status);
		return status;
	}

	void updateStatus(IStatus status)
	{
		if(!fFieldsInitialized)
			return;
		if(!status.matches(4))
		{
			for(int i = 0; i < fNumberFields.size(); i++)
			{
				Text text = (Text)fNumberFields.get(i);
				IStatus s = validatePositiveNumber(text.getText());
				status = s.getSeverity() <= status.getSeverity() ? status : s;
			}

		}
		setValid(!status.matches(4));
		applyToStatusLine(this, status);
	}

	public void applyToStatusLine(DialogPage page, IStatus status)
	{
		String message = status.getMessage();
		switch(status.getSeverity())
		{
		case 0: // '\0'
			page.setMessage(message, 0);
			page.setErrorMessage(null);
			break;

		case 2: // '\002'
			page.setMessage(message, 2);
			page.setErrorMessage(null);
			break;

		case 1: // '\001'
			page.setMessage(message, 1);
			page.setErrorMessage(null);
			break;

		default:
			if(message.length() == 0)
				message = null;
			page.setMessage(null);
			page.setErrorMessage(message);
			break;
		}
	}

}