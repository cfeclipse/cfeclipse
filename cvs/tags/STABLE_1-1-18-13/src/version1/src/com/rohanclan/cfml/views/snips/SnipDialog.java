/*
 * Created on 	: 01-Sep-2004
 * Created by 	: Administrator
 * File		  	: SnipDialog.java
 * Description	:
 * 
 * The MIT License
 * Copyright (c) 2004 Administrator
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
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import java.util.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;



/**
 * @author Administrator
 */
public class SnipDialog extends Dialog{
    protected String title;
    
    private ArrayList itemList;

    private Combo combo;
    private Text text;
    private ComboViewer comboViewer;
    private int selectedID;
    /**
     * Constructor for this class
     */
    public SnipDialog(Shell parentShell) {
        super(parentShell);
    }
    
    /**
     * Actually creates the dialog area
     */
    
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        
        
        
        String options[];

        GridLayout gl = new GridLayout();
        gl.numColumns = 2;
        
        container.setLayout(gl);
        FontData labelFontData = new FontData();
        labelFontData.setStyle(SWT.BOLD);
        FontData[] containerFontData = container.getFont().getFontData();
        labelFontData.setHeight(containerFontData[0].height);
        Font labelFont = new Font(parent.getDisplay(), labelFontData);
        
        Iterator i = itemList.iterator();
        SnipVarItem item;
        while (i.hasNext()) {
            item = (SnipVarItem)i.next();
            
            
            Label label = new Label(container,SWT.HORIZONTAL);
            label.setText(item.getName() + ":");
            label.setFont(labelFont);

            GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
            
            gridData.widthHint = 200;
            if (item.getValues().length > 1) {
                addComboField(container,item, gridData);
            }
            else {
                addTextField(container,item, gridData);
            }
        }

        return container;
    }
    
    
   private void addComboField(Composite parent, SnipVarItem item, GridData gridData) {

       String[] items = item.getValues();
       
       combo = new Combo(parent, SWT.DROP_DOWN);

       combo.setLayoutData(gridData);
       
       ComboModifyListener modifyListener = new ComboModifyListener(combo);
       
       combo.addModifyListener(modifyListener);
       combo.addKeyListener(modifyListener);
       item.setCombo(combo);
       combo.setItems(items);
       combo.select(0);
   }
   
   
  private void addTextField(Composite parent, SnipVarItem item, GridData gridData) {

      String[] items = item.getValues();
      
      text = new Text(parent, SWT.BORDER);

      text.setLayoutData(gridData);
      
      item.setText(text);
      text.setText(items[0]);
     
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
           Iterator i = itemList.iterator();
          
           while (i.hasNext()) {
               ((SnipVarItem)i.next()).setReplacement();
           }
        }
       super.buttonPressed(buttonId);
       
   }
    
    
    
    
    
    
    public void setItemList(ArrayList itemList) {
        this.itemList = itemList;
    }
    
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
