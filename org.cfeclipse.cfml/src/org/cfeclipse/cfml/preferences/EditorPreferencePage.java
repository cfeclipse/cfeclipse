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
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class EditorPreferencePage extends PreferencePage
	implements IWorkbenchPreferencePage
{

	private final String fAppearanceColorListModel[][] = {
		{
			"Editor Background", EditorPreferenceConstants.P_COLOR_BACKGROUND, null
		}, /*{
			"Line numbers",  EditorPreferenceConstants.P_LINE_NUMBER_COLOR, null
		},*/{
			"Editor toolbars",  EditorPreferenceConstants.P_SHOW_EDITOR_TOOLBAR, null
		}, {
			"Current line highlight",  EditorPreferenceConstants.P_CURRENT_LINE_COLOR, null
		}, {
			"Bracket highlighting",  EditorPreferenceConstants.P_BRACKET_MATCHING_COLOR, null
		}, {
			"Print margin",  EditorPreferenceConstants.P_PRINT_MARGIN_COLOR, null
		}, {
			"Selection foreground color",  EditorPreferenceConstants.P_SELECTION_FOREGROUND_COLOR, EditorPreferenceConstants.P_SELECTION_FOREGROUND_SYSTEM_DEFAULT
		}, {
			"Selection background color",  EditorPreferenceConstants.P_SELECTION_BACKGROUND_COLOR, EditorPreferenceConstants.P_SELECTION_BACKGROUND_SYSTEM_DEFAULT
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
	private Map fComboBoxes = null;
	private SelectionListener fComboBoxListener = null;
	
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
		fComboBoxes = new HashMap();
		fComboBoxListener = new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e)
			{
				CCombo combo = (CCombo)e.widget;
				fOverlayStore.setValue((String)fComboBoxes.get(combo), combo.getSelectionIndex());
			}

			public void widgetSelected(SelectionEvent e)
			{
				CCombo combo = (CCombo)e.widget;
				fOverlayStore.setValue((String)fComboBoxes.get(combo), combo.getSelectionIndex());
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
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_COLOR_BACKGROUND));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_CURRENT_LINE_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_CURRENT_LINE_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_BRACKET_MATCHING_ENABLED));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, EditorPreferenceConstants.P_BRACKET_MATCHING_STYLE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_HIGHLIGHT_CURRENT_LINE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_TAB_INDENTS_CURRENT_LINE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_RTRIM_ON_SAVE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, EditorPreferenceConstants.P_TAB_WIDTH));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, EditorPreferenceConstants.P_INSIGHT_DELAY));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_PRINT_MARGIN_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.INT, EditorPreferenceConstants.P_PRINT_MARGIN_SIZE));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_SHOW_PRINT_MARGIN));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_SHOW_OVERVIEW_RULER));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_LINE_NUMBER_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_SHOW_EDITOR_TOOLBAR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_ENABLE_CUSTOM_CARETS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_USE_WIDE_CARET));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_SELECTION_FOREGROUND_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_BRACKET_MATCHING_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_SELECTION_FOREGROUND_SYSTEM_DEFAULT));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, EditorPreferenceConstants.P_SELECTION_BACKGROUND_COLOR));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_SELECTION_BACKGROUND_SYSTEM_DEFAULT));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_WARN_READ_ONLY_FILES));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, EditorPreferenceConstants.P_NAVIGATION_SMART_HOME_END));
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
		addTextField(appearanceComposite, label, EditorPreferenceConstants.P_PRINT_MARGIN_SIZE, 3, 0, true);
		label = "Displayed &tab width:";
		addTextField(appearanceComposite, label, EditorPreferenceConstants.P_TAB_WIDTH, 3, 0, true);
		label = "Insight Delay:";
		addTextField(appearanceComposite, label, EditorPreferenceConstants.P_INSIGHT_DELAY, 4, 0, true);
		label = "Use spaces for tabs";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS, 0);
		label = "Trim trailing spaces before saving";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_RTRIM_ON_SAVE, 0);
		
		
		label = "Show overview &ruler";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_SHOW_OVERVIEW_RULER, 0);
		label = "Show Editor Toolbar";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_SHOW_EDITOR_TOOLBAR, 0);
		label = "Hi&ghlight current line";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_HIGHLIGHT_CURRENT_LINE, 0);
		label = "Tab indents current line";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_TAB_INDENTS_CURRENT_LINE, 0);
		label = "Sho&w print margin";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_SHOW_PRINT_MARGIN, 0);
		label = "Warn when opening read only files";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_WARN_READ_ONLY_FILES, 0);
		label = "Use c&ustom caret";
		Button master = addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_ENABLE_CUSTOM_CARETS, 0);
		label = "Ena&ble thick caret";
		Button slave = addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_USE_WIDE_CARET, 0);
		createDependency(master, EditorPreferenceConstants.P_ENABLE_CUSTOM_CARETS, slave);
		

		
		label = "Enable bracket highlighting";
		Button bracketMatchingBox = addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_BRACKET_MATCHING_ENABLED, 0);
		String items[] = new String[] {"Outline box", "Solid box", "Bold text"};
		CCombo bracketStyleCombo  = addComboList(appearanceComposite,"Bracket matching style",EditorPreferenceConstants.P_BRACKET_MATCHING_STYLE,items);
		createDependency(bracketMatchingBox, EditorPreferenceConstants.P_BRACKET_MATCHING_ENABLED, bracketStyleCombo);
		
		label = "Enable smart Home and End";
		addCheckBox(appearanceComposite, label, EditorPreferenceConstants.P_NAVIGATION_SMART_HOME_END, 0);
		
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
		for(int i = 0; i < fAppearanceColorListModel.length; i++) {
			fAppearanceColorList.add(fAppearanceColorListModel[i][0]);
		}
		
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

		CCombo c;
		for(Iterator e = fComboBoxes.keySet().iterator(); e.hasNext(); c.select(fOverlayStore.getInt(key)))
		{
			c = (CCombo)e.next();
			key = (String)fComboBoxes.get(c);
		}

		fFieldsInitialized = true;
		updateStatus(validatePositiveNumber("0"));
		SelectionListener listener;
		for(Iterator iter = fMasterSlaveListeners.iterator(); iter.hasNext(); listener.widgetSelected(null))
			listener = (SelectionListener)iter.next();
		
	    

	}

	private void initializeDefaultColors()
	{
		if(!getPreferenceStore().contains(EditorPreferenceConstants.P_SELECTION_BACKGROUND_COLOR))
		{
			org.eclipse.swt.graphics.RGB rgb = getControl().getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION).getRGB();
			PreferenceConverter.setDefault(fOverlayStore, EditorPreferenceConstants.P_SELECTION_BACKGROUND_COLOR, rgb);
			PreferenceConverter.setDefault(getPreferenceStore(), EditorPreferenceConstants.P_SELECTION_BACKGROUND_COLOR, rgb);
		}
		if(!getPreferenceStore().contains(EditorPreferenceConstants.P_SELECTION_FOREGROUND_COLOR))
		{
			org.eclipse.swt.graphics.RGB rgb = getControl().getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT).getRGB();
			PreferenceConverter.setDefault(fOverlayStore, EditorPreferenceConstants.P_SELECTION_FOREGROUND_COLOR, rgb);
			PreferenceConverter.setDefault(getPreferenceStore(), EditorPreferenceConstants.P_SELECTION_FOREGROUND_COLOR, rgb);
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

	private CCombo addComboList(Composite parent, String label, String key, String[] items) {
		
		Label labelControl = new Label(parent,SWT.NONE);
		labelControl.setText(label);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = 0;
		
		labelControl.setLayoutData(gd);
		CCombo combo = new CCombo(parent,SWT.BORDER|SWT.SINGLE|SWT.READ_ONLY);
		for (int i=0;i<items.length;i++) {
			combo.add(items[i]);
		}
		combo.setBackground(new Color(parent.getDisplay(),255,255,255));
		
		combo.addSelectionListener(fComboBoxListener);
		fComboBoxes.put(combo,key);
		
		return combo;
	}
	
	private Button addCheckBox(Composite parent, String label, String key, int indentation)
	{
		Button checkBox = new Button(parent, SWT.CHECK);
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
		Label labelControl = new Label(composite, SWT.NONE);
		labelControl.setText(label);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indentation;
		labelControl.setLayoutData(gd);
		Text textControl = new Text(composite, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
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