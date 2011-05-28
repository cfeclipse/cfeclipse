/*
 * Created on 16-Jan-2005
 *
 * The MIT License
 * Copyright (c) 2004 Mark Drew
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 **/
package org.cfeclipse.frameworks.fusebox.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * @author Administrator
 * 16-Jan-2005
 * fusebox3cfe2
 * Description:
 */
public class AddCurcuitDialog extends Dialog{

	private String title = "Add a circuit";
	//The Widgets
	private Text circuitname;
	private Text circuitpath;
	private Button circuitFolders;
	
	//the returned values
	private String circuitnamevalue;
	private String circuitpathvalue;
	private boolean createfolders = false;
	
	
	/**
	 * @param parentShell
	 */
	public AddCurcuitDialog(Shell parentShell) {
		super(parentShell);
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
        
        GridLayout gl = new GridLayout();
        gl.numColumns = 2;
        
        container.setLayout(gl);
        gl.makeColumnsEqualWidth = true;
        FontData labelFontData = new FontData();
        labelFontData.setStyle(SWT.BOLD);
        FontData[] containerFontData = container.getFont().getFontData();
        //
        labelFontData.height = (containerFontData[0].height);
        Font labelFont = new Font(parent.getDisplay(), labelFontData);
        
        
        
        Label label = new Label(container,SWT.NONE);
        label.setText("Circuit Name:");
              
        circuitname = new Text(container, SWT.BORDER);
        
        Label pathlabel = new Label(container,SWT.NONE);
        pathlabel.setText("Circuit Path :");
              
        circuitpath = new Text(container, SWT.BORDER);
        
        Label createdir = new Label(container, SWT.NONE);
        createdir.setText("Create directory and circuit files?");
        
        circuitFolders = new Button(container, SWT.CHECK);
        circuitFolders.setSelection(this.isCreatefolders());
        return container;
		
		
	}
	
	protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(this.title);
        
    }
    
    
   protected void createButtonsForButtonBar(Composite parent) {
       createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
               true);
       createButton(parent, IDialogConstants.CANCEL_ID,
               IDialogConstants.CANCEL_LABEL, false);
   }
   protected void buttonPressed(int buttonId) {
       if (buttonId == IDialogConstants.OK_ID) {
       		//the button has been pressed
       		circuitnamevalue = circuitname.getText();
       		circuitpathvalue = circuitpath.getText();
       		createfolders = circuitFolders.getSelection();
        }
       super.buttonPressed(buttonId);
       
   }
	
	/**
	 * @return Returns the circuitnamevalue.
	 */
	public String getCircuitName() {
		return circuitnamevalue;
	}
	/**
	 * @return Returns the circuitpathvalue.
	 */
	public String getCircuitPath() {
		return circuitpathvalue;
	}
	
	/**
	 * @return Returns the createfolders.
	 */
	public boolean isCreatefolders() {
		return createfolders;
	}
	/**
	 * @param createfolders The createfolders to set.
	 */
	public void setCreatefolders(boolean createfolders) {
		this.createfolders = createfolders;
	}
}
