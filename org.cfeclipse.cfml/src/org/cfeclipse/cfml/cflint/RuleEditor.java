package org.cfeclipse.cfml.cflint;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import com.cflint.config.CFLintPluginInfo.PluginInfoRule;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule.PluginMessage;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule.PluginParameter;

public class RuleEditor extends Group {
	private PluginInfoRule rule;
	private ArrayList<MessageEditor> messageEditors;
	private ArrayList<ParameterEditor> parameterEditors;
	private boolean ruleEnabled;
	private Button enabledCheckbox;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param rule
	 * @param descriptions 
	 * @param excludes2 
	 */
	public RuleEditor(Composite parent, int style, PluginInfoRule rule, boolean enabled, HashMap<String, String> descriptions, List<PluginMessage> excludeMessages) {
		super(parent, style);
		this.rule = rule;
		setLayout(new GridLayout(1, false));
		enabledCheckbox = new Button(this, SWT.CHECK);
		enabledCheckbox.setFont(SWTResourceManager.getFont("Noto Sans [monotype]", 9, SWT.BOLD));
		enabledCheckbox.setText(rule.getName());
		enabledCheckbox.setSelection(enabled);
		enabledCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Button btn = (Button) event.getSource();
				setRuleEnabled(btn.getSelection());
			}
		});
		messageEditors = new ArrayList<MessageEditor>();
		parameterEditors = new ArrayList<ParameterEditor>();
		boolean noMessagesEnabled = true;
		if (!rule.getMessages().isEmpty()) {
			for (PluginMessage message : rule.getMessages()) {
				boolean messageEnabled = !excludeMessages.contains(message);
				if(messageEnabled) {
					noMessagesEnabled = false;
				}
				MessageEditor messageGroup = new MessageEditor(this, SWT.NONE, message, descriptions, messageEnabled);
				messageEditors.add(messageGroup);
			}
		}
		if(noMessagesEnabled) {
			enabledCheckbox.setSelection(false);
			ruleEnabled = false;
		}
		if (!rule.getParameters().isEmpty()) {
			for (PluginParameter parameter : rule.getParameters()) {
				ParameterEditor parameterEditor = new ParameterEditor(this, SWT.NONE, parameter);
				parameterEditors.add(parameterEditor);
			}
		}
//		setRuleEnabled(enabled);
	}

	public ArrayList<PluginMessage> getMessages() {
		ArrayList<PluginMessage> messages = new ArrayList<PluginMessage>();
		for (MessageEditor editor : messageEditors) {
			messages.add(editor.getMessage());
		}
		return messages;
	}
	
	public ArrayList<PluginMessage> getExcludes() {
		ArrayList<PluginMessage> excludes = new ArrayList<PluginMessage>();
		for (MessageEditor editor : messageEditors) {
			if(!editor.getMessageEnabled()) {
				excludes.add(editor.getMessage());
			}
		}
		return excludes;
	}

	public ArrayList<PluginParameter> getParameters() {
		ArrayList<PluginParameter> parameters = new ArrayList<PluginParameter>();
		for (ParameterEditor editor : parameterEditors) {
			parameters.add(editor.getParameter());
		}
		return parameters;
	}

	public PluginInfoRule getRule() {
		rule.setParameters(getParameters());
		rule.setMessages(getMessages());
		return rule;
	}

	public boolean getRuleEnabled() {
		return ruleEnabled;
	}

	public void setRuleEnabled(boolean enabled) {
		ruleEnabled = enabled;
		enabledCheckbox.setSelection(enabled);
		for (MessageEditor editor : messageEditors) {
			recursiveSetEnabled(editor, ruleEnabled);
			editor.setMessageEnabled(enabled);
		}
		for (ParameterEditor editor : parameterEditors) {
			recursiveSetEnabled(editor, ruleEnabled);
		}
	}

	public static void recursiveSetEnabled(Control ctrl, boolean enabled) {
		if (ctrl instanceof Composite) {
			Composite comp = (Composite) ctrl;
			comp.setEnabled(enabled);
			for (Control c : comp.getChildren())
				recursiveSetEnabled(c, enabled);
		} else {
			if (ctrl instanceof Button) {
				((Button)ctrl).setSelection(enabled);
			}
			ctrl.setEnabled(enabled);
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
