package org.cfeclipse.cfml.cflint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import com.cflint.config.CFLintConfig;
import com.cflint.config.CFLintPluginInfo;
import com.cflint.config.ConfigRuntime;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule.PluginMessage;
import com.cflint.config.ConfigUtils;

public class CFLintConfigUI {

	private ArrayList<RuleEditor> ruleEditors;
	private CFLintConfig cflintConfig;
	private static String cflintVersion = com.cflint.Version.getVersion();
	private static CFMLPropertyManager propertyManager = new CFMLPropertyManager();
	private static final CFLintPluginInfo pluginInfo = ConfigUtils.loadDefaultPluginInfo();
	private Composite composite;
	private IProject iProject;

	public void buildGUI(Composite composite, IProject iProject) {
		this.composite = composite;
		this.iProject = iProject;
		setConfig(getProjectCFLintConfig(iProject));
	}

	public void buildRulesGUI(Composite composite, IProject iProject) {
		List<PluginInfoRule> enabledRules = getConfig().getRules();
		List<PluginMessage> excludeMessages = getConfig().getExcludes();
		Button enabledCheckbox = new Button(composite, SWT.CHECK);
		enabledCheckbox.setText("Enable/Disable All Rules for CFLint " + cflintVersion);
		enabledCheckbox.setSelection(false);
		enabledCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Button btn = (Button) event.getSource();
				boolean enableAll = btn.getSelection();
				for (RuleEditor ruleEditor : ruleEditors) {
					ruleEditor.setRuleEnabled(enableAll);
				}
			}
		});
		ruleEditors = new ArrayList<RuleEditor>();
		HashMap<String, String> descriptions = ConfigUtils.loadDescriptions();
		for (PluginInfoRule rule : pluginInfo.getRules()) {
			RuleEditor ruleEdit;
			if(enabledRules.contains(rule)){
				rule = enabledRules.get(enabledRules.indexOf(rule));
				ruleEdit = new RuleEditor(composite, SWT.NONE, rule, true, descriptions, excludeMessages);
			} else {
				ruleEdit = new RuleEditor(composite, SWT.NONE, rule, true, descriptions, excludeMessages);
			}
			ruleEdit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			ruleEdit.setLayout(new GridLayout(1, false));
			ruleEditors.add(ruleEdit);
		}
	}
	
	private void setConfig(CFLintConfig projectCFLintConfig) {
		this.cflintConfig = projectCFLintConfig;
		buildRulesGUI(composite, iProject);
	}
	private CFLintConfig getConfig() {
		return cflintConfig;
	}
	
	public static CFLintConfig getProjectCFLintConfig(IProject iProject) {
		CFLintConfig currentConfig = null;
		File configFile = getConfigFile(iProject);
		if (configFile.exists()) {
			try {
				currentConfig = ConfigUtils.unmarshalJson(new FileInputStream(configFile),
						CFLintConfig.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			currentConfig = new ConfigRuntime(null,pluginInfo);
		}
		return currentConfig;
	}
	
	public static File getConfigFile(IProject iProject) {
		File configFile;
		if(!propertyManager.getCFLintStoreConfigInProject(iProject)) {
			configFile = iProject.getWorkingLocation(CFMLPlugin.PLUGIN_ID).append("cflint.definition.json").toFile();
		} else {
			configFile = iProject.getProject().getLocation().append("cflint.definition.json").toFile();
		}
		return configFile;
	}
	
	public void setProjectRules(IProject iProject) {
		File configFile = getConfigFile(iProject);
		ArrayList<PluginInfoRule> rules = new ArrayList<PluginInfoRule>();
		ArrayList<PluginMessage> excludes = new ArrayList<PluginMessage>();
		for (RuleEditor ruleEditor : ruleEditors) {
			rules.add(ruleEditor.getRule());
			excludes.addAll(ruleEditor.getExcludes());
		}
		try {
			String config = ConfigUtils.marshalJson(rules);
			String excludesStr = ConfigUtils.marshalJson(excludes);
			PrintWriter writer;
			writer = new PrintWriter(new BufferedWriter(new FileWriter(configFile)));
			writer.write("{\"rule\":" + config + ", \"excludes\":"+excludesStr+"}");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			iProject.build(IncrementalProjectBuilder.CLEAN_BUILD,null);
			iProject.build(IncrementalProjectBuilder.FULL_BUILD,null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetProjectRules() {
		setConfig(new ConfigRuntime(null,pluginInfo));
	}

}
