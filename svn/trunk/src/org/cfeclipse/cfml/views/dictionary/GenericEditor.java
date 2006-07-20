/*
 * Created on 	: 24-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: GenericEditor.java
 * Description	:
 * 
 */
package org.cfeclipse.cfml.views.dictionary;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Mark Drew 
 *
 */
public class GenericEditor extends Dialog {

    public GenericEditor(Shell parentShell) {
        super(parentShell);
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);

        final Label label = new Label(container, SWT.NONE);
        label.setText("label");

        final Combo combo = new Combo(container, SWT.NONE);
        combo.setText("hello");
        //
        return container;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("test");
    }
}
