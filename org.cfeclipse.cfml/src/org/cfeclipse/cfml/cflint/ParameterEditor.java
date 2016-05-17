package org.cfeclipse.cfml.cflint;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import com.cflint.config.CFLintPluginInfo.PluginInfoRule.PluginParameter;

public class ParameterEditor extends Group {
	private Text parameterText;
	private PluginParameter parameter;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param parameter 
	 */
	public ParameterEditor(Composite parent, int style, PluginParameter parameter) {
		super(parent, style);
		this.parameter = parameter;
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		this.setLayout(new GridLayout(3, false));
		
		Label parameterLabel = new Label(this, SWT.NONE);
		parameterLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		parameterLabel.setText("Parameter");
		
		Label parameterNameLabel = new Label(this, SWT.NONE);
		parameterNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		parameterNameLabel.setText(parameter.getName());
		
		parameterText = new Text(this, SWT.BORDER);
		parameterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		parameterText.setText(parameter.getValue());

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public PluginParameter getParameter() {
		parameter.setValue(parameterText.getText());
		return parameter;
	}

}
