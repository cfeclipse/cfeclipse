/*
 * Created on 28-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views.forms;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rohanclan.cfml.views.snips.SnipVarItem;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PropertyForm extends Dialog {
	public boolean close() {
		this.name = this.textText.getText();
		this.value = this.valueText.getText();
		return super.close();
	}
	protected void okPressed() {
		// TODO Auto-generated method stub
		super.okPressed();
	}
	private FontData labelFontData;
	private Font labelFont;
	private Text textText;
	private Text valueText;
	
	private String name = "";
	private String value = "";
	
	public String getName()
	{
		return this.name;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	private void setupFonts(Composite container, Composite parent)
	{
		this.labelFontData  = new FontData();
		
		labelFontData.setStyle(SWT.BOLD);
        FontData[] containerFontData = container.getFont().getFontData();
        labelFontData.setHeight(containerFontData[0].height);
        this.labelFont = new Font(parent.getDisplay(), labelFontData);        
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		setupFonts(container, parent);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		
		GridLayout gl = new GridLayout();
        gl.numColumns = 2;
		
        Label nameLabel = new Label(parent, SWT.HORIZONTAL);
        nameLabel.setText("Name");
        nameLabel.setFont(this.labelFont);
        
        this.valueText = new Text(parent, SWT.BORDER);
        this.valueText.setText("");
        
        Label valueLabel = new Label(parent, SWT.HORIZONTAL);
        valueLabel.setText("Value");
        valueLabel.setFont(this.labelFont);
        this.textText = new Text(parent, SWT.BORDER);
        this.textText.setText("");
                
        return container;
	}
	/**
	 * @param parentShell
	 */
	public PropertyForm(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

}
