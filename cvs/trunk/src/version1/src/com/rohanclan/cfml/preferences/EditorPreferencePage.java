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
import org.eclipse.swt.graphics.Color;
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

public class EditorPreferencePage extends PreferencePage
	implements IWorkbenchPreferencePage
{

	private final String fAppearanceColorListModel[][] = {
		{
			"Line numbers", "lineNumberColor" , "AbstractTextEditor.Color.SelectionForeground.SystemDefault"
		}, {
			"Current line highlight", "currentLineColor", "AbstractTextEditor.Color.SelectionForeground.SystemDefault"
		}, {
			"Bracket highlighting", "bracketMatchingColor", "AbstractTextEditor.Color.SelectionForeground.SystemDefault"
		}, {
			"Print margin", "printMarginColor", "AbstractTextEditor.Color.SelectionBackground.SystemDefault"
		}, {
			"Selection foreground color", "AbstractTextEditor.Color.SelectionForeground", "AbstractTextEditor.Color.SelectionForeground.SystemDefault"
		}, {
			"Selection background color", "AbstractTextEditor.Color.SelectionBackground", "AbstractTextEditor.Color.SelectionBackground.SystemDefault"
		}
	};
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

	public EditorPreferencePage()
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
		setDescription("Editor Settings");
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		fOverlayStore = createOverlayStore();
	   
	}

	private OverlayPreferenceStore createOverlayStore()
	{
		ArrayList overlayKeys = new ArrayList();
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, "currentLineColor"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "currentLine"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "bracketMatchingEnabled"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "rTrimOnSave"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, "tabWidth"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, "insightDelay"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "tabsAsSpaces"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, "printMarginColor"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, "printMarginColumn"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "printMargin"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "overviewRuler"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, "lineNumberColor"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "lineNumberRuler"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "AbstractTextEditor.Accessibility.UseCustomCarets"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "AbstractTextEditor.Accessibility.WideCaret"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, "AbstractTextEditor.Color.SelectionForeground"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, "bracketMatchingColor"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "AbstractTextEditor.Color.SelectionForeground.SystemDefault"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, "AbstractTextEditor.Color.SelectionBackground"));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, "AbstractTextEditor.Color.SelectionBackground.SystemDefault"));
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

	private void handleAppearanceColorListSelection()
	{
		int i = fAppearanceColorList.getSelectionIndex();
		String key = fAppearanceColorListModel[i][1];
		org.eclipse.swt.graphics.RGB rgb = PreferenceConverter.getColor(fOverlayStore, key);
		fAppearanceColorEditor.setColorValue(rgb);
		updateAppearanceColorWidgets(fAppearanceColorListModel[i][2]);
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
		String label = "&Print margin column:";
		addTextField(appearanceComposite, label, "printMarginColumn", 3, 0, true);
		label = "Displayed &tab width:";
		addTextField(appearanceComposite, label, "tabWidth", 3, 0, true);
		label = "Insight Delay:";
		addTextField(appearanceComposite, label, "insightDelay", 4, 0, true);
		label = "Use spaces for tabs";
		addCheckBox(appearanceComposite, label, "tabsAsSpaces", 0);
		label = "Trim trailing spaces before saving";
		addCheckBox(appearanceComposite, label, "rTrimOnSave", 0);
		label = "Enable bracket highlighting";
		addCheckBox(appearanceComposite, label, "bracketMatchingEnabled", 0);
		label = "Show overview &ruler";
		addCheckBox(appearanceComposite, label, "overviewRuler", 0);
		label = "Show lin&e numbers";
		addCheckBox(appearanceComposite, label, "lineNumberRuler", 0);
		label = "Hi&ghlight current line";
		addCheckBox(appearanceComposite, label, "currentLine", 0);
		label = "Sho&w print margin";
		addCheckBox(appearanceComposite, label, "printMargin", 0);
		label = "Use c&ustom caret";
		Button master = addCheckBox(appearanceComposite, label, "AbstractTextEditor.Accessibility.UseCustomCarets", 0);
		label = "Ena&ble thick caret";
		Button slave = addCheckBox(appearanceComposite, label, "AbstractTextEditor.Accessibility.WideCaret", 0);
		createDependency(master, "AbstractTextEditor.Accessibility.UseCustomCarets", slave);
		Label l = new Label(appearanceComposite, 16384);
		GridData gd = new GridData(256);
		gd.horizontalSpan = 2;
		gd.heightHint = convertHeightInCharsToPixels(1) / 2;
		l.setLayoutData(gd);
		l = new Label(appearanceComposite, 16384);
		l.setText("Appearance co&lor options:");
		gd = new GridData(256);
		gd.horizontalSpan = 2;
		l.setLayoutData(gd);
		Composite editorComposite = new Composite(appearanceComposite, 0);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		editorComposite.setLayout(layout);
		gd = new GridData(1296);
		gd.horizontalSpan = 2;
		editorComposite.setLayoutData(gd);
		fAppearanceColorList = new List(editorComposite, 2564);
		gd = new GridData(770);
		gd.heightHint = convertHeightInCharsToPixels(6);
		fAppearanceColorList.setLayoutData(gd);
		Composite stylesComposite = new Composite(editorComposite, 0);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		stylesComposite.setLayout(layout);
		stylesComposite.setLayoutData(new GridData(1808));
		l = new Label(stylesComposite, 16384);
		l.setText("C&olor:");
		gd = new GridData();
		gd.horizontalAlignment = 1;
		l.setLayoutData(gd);
		fAppearanceColorEditor = new ColorEditor(stylesComposite);
		Button foregroundColorButton = fAppearanceColorEditor.getButton();
		gd = new GridData(768);
		gd.horizontalAlignment = 1;
		foregroundColorButton.setLayoutData(gd);
		SelectionListener colorDefaultSelectionListener = new SelectionListener() {

			public void widgetSelected(SelectionEvent e)
			{
				boolean systemDefault = fAppearanceColorDefault.getSelection();
				fAppearanceColorEditor.getButton().setEnabled(!systemDefault);
				int i = fAppearanceColorList.getSelectionIndex();
				String key = fAppearanceColorListModel[i][2];
				if(key != null)
					fOverlayStore.setValue(key, systemDefault);
			}

			public void widgetDefaultSelected(SelectionEvent selectionevent)
			{
			}

		};
		fAppearanceColorDefault = new Button(stylesComposite, 32);
		fAppearanceColorDefault.setText("System De&fault");
		gd = new GridData(768);
		gd.horizontalAlignment = 1;
		gd.horizontalSpan = 2;
		fAppearanceColorDefault.setLayoutData(gd);
		fAppearanceColorDefault.setVisible(false);
		fAppearanceColorDefault.addSelectionListener(colorDefaultSelectionListener);
		fAppearanceColorList.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent selectionevent)
			{
			}

			public void widgetSelected(SelectionEvent e)
			{
				handleAppearanceColorListSelection();
			}

		});
		foregroundColorButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent selectionevent)
			{
			}

			public void widgetSelected(SelectionEvent e)
			{
				int i = fAppearanceColorList.getSelectionIndex();
				String key = fAppearanceColorListModel[i][1];
				PreferenceConverter.setValue(fOverlayStore, key, fAppearanceColorEditor.getColorValue());
			}

		});
		
		return appearanceComposite;
	}

	protected Control createContents(Composite parent)
	{
	   
		initializeDefaultColors();
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
		for(int i = 0; i < fAppearanceColorListModel.length; i++)
			fAppearanceColorList.add(fAppearanceColorListModel[i][0]);

		fAppearanceColorList.getDisplay().asyncExec(new Runnable() {

			public void run()
			{
				if(fAppearanceColorList != null && !fAppearanceColorList.isDisposed())
				{
					fAppearanceColorList.select(0);
					handleAppearanceColorListSelection();
				}
			}

		});
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

	private void initializeDefaultColors()
	{
		if(!getPreferenceStore().contains("AbstractTextEditor.Color.SelectionBackground"))
		{
			org.eclipse.swt.graphics.RGB rgb = getControl().getDisplay().getSystemColor(26).getRGB();
			PreferenceConverter.setDefault(fOverlayStore, "AbstractTextEditor.Color.SelectionBackground", rgb);
			PreferenceConverter.setDefault(getPreferenceStore(), "AbstractTextEditor.Color.SelectionBackground", rgb);
		}
		if(!getPreferenceStore().contains("AbstractTextEditor.Color.SelectionForeground"))
		{
			org.eclipse.swt.graphics.RGB rgb = getControl().getDisplay().getSystemColor(27).getRGB();
			PreferenceConverter.setDefault(fOverlayStore, "AbstractTextEditor.Color.SelectionForeground", rgb);
			PreferenceConverter.setDefault(getPreferenceStore(), "AbstractTextEditor.Color.SelectionForeground", rgb);
		}
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
		handleAppearanceColorListSelection();
	    
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