package org.cfeclipse.cfml.cflint;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule.PluginMessage;

public class MessageEditor extends Group {
	private Text messageText;
	private PluginMessage message;
	private Combo severityCombo;
	private Button enabledCheckbox;
	private boolean messageEnabled;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param message 
	 * @param descriptions 
	 * @param b 
	 */
	public MessageEditor(Composite parent, int style, PluginMessage message, HashMap<String, String> descriptions, boolean enabled) {
		super(parent, style);
		this.message = message;
		messageEnabled = enabled;
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		this.setLayout(new GridLayout(2, false));

		enabledCheckbox = new Button(this, SWT.CHECK);
		GridData gdata = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gdata.horizontalSpan = 2;
		enabledCheckbox.setLayoutData(gdata);
		enabledCheckbox.setText("Code: " + message.getCode() + "   --   Description: " + descriptions.get(message.getCode()));
		enabledCheckbox.setSelection(enabled);
		enabledCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Button btn = (Button) event.getSource();
				setMessageEnabled(btn.getSelection());
			}
		});
				
		severityCombo = new Combo(this, SWT.NONE);
		severityCombo.setItems(new String[] {"INFO", "WARN", "ERROR"});
		severityCombo.setText(message.getSeverity());
		
		messageText = new Text(this, SWT.BORDER);
		messageText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		messageText.setText(message.getMessageText() == null ? "" : message.getMessageText());
		
		setMessageEnabled(enabled);
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public boolean getMessageEnabled() {
		return messageEnabled;
	}
	public void setMessageEnabled(boolean enabled) {
		messageEnabled = enabled;
		enabledCheckbox.setSelection(enabled);
		severityCombo.setEnabled(enabled);
		messageText.setEnabled(enabled);
	}

	public PluginMessage getMessage() {
		message.setMessageText(messageText.getText());
		message.setSeverity(severityCombo.getText());
		return message;
	}

}
