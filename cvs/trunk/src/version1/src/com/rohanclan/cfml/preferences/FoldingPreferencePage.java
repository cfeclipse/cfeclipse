package com.rohanclan.cfml.preferences;

//import java.util.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.help.WorkbenchHelp;

import com.rohanclan.cfml.CFMLPlugin;

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
	private List fAppearanceColorList = null;
	private ColorEditor fAppearanceColorEditor = null;
	private Button fAppearanceColorDefault = null;
	private boolean fFieldsInitialized = false;
	private ArrayList fMasterSlaveListeners = null;

	public FoldingPreferencePage()
	{
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
				Text text = (Text)e.widget;
				fOverlayStore.setValue((String)fTextFields.get(text), text.getText());
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
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, ICFMLPreferenceConstants.P_ENABLE_CODE_FOLDING));
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

	private void updateAppearanceColorWidgets(String systemDefaultKey)
	{
		if(systemDefaultKey == null)
		{
			fAppearanceColorDefault.setSelection(false);
			fAppearanceColorDefault.setVisible(false);
			fAppearanceColorEditor.getButton().setEnabled(true);
		} else
		{
			boolean systemDefault = fOverlayStore.getBoolean(systemDefaultKey);
			fAppearanceColorDefault.setSelection(systemDefault);
			fAppearanceColorDefault.setVisible(true);
			fAppearanceColorEditor.getButton().setEnabled(!systemDefault);
		}
	}

	private Control createAppearancePage(Composite parent)
	{
		Composite appearanceComposite = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		appearanceComposite.setLayout(layout);
		
		// Enable/Disable Code Folding
		String label = "Enable code folding:";
		addCheckBox(appearanceComposite, label, ICFMLPreferenceConstants.P_ENABLE_CODE_FOLDING, 4);
		label = "TODO: Still need to add the individual preferences for the items to fold. \n";
		label += "The folding implementation currently adds folding markers to comments, \n"; 
		label += "cffunction tags, cfquery tags, cfscript tags, script tags, and style tags. \n";
		label += "* Comments are automatically collapsed. \n";
		label += "* Folding markers are only added if the block is 3 or more lines in length. \n";
		addLabel(appearanceComposite,label,4);
		
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

	private Control addLabel(Composite composite, String label, int indentation)
	{
		Label labelControl = new Label(composite, 0);
		labelControl.setText(label);
		GridData gd = new GridData(32);
		gd.horizontalIndent = indentation;
		labelControl.setLayoutData(gd);
		
		return labelControl;
	}

	private void createDependency(final Button master, String masterKey, final Control slave)
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
	}

	private static void indent(Control control)
	{
		GridData gridData = new GridData();
		gridData.horizontalIndent = 20;
		control.setLayoutData(gridData);
	}

	private void numberFieldChanged(Text textControl)
	{
		String number = textControl.getText();
		IStatus status = validatePositiveNumber(number);
		if(!status.matches(4))
			fOverlayStore.setValue((String)fTextFields.get(textControl), number);
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