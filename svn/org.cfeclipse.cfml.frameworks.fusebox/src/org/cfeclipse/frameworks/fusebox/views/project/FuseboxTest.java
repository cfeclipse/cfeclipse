/*
 * Created on 	: 16-Sep-2004
 * Created by 	: Administrator
 * File		  	: FuseboxTest.java
 * Description	:
 * 
 */
package org.cfeclipse.frameworks.fusebox.views.project;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FuseboxTest extends Dialog {

    public FuseboxTest(Shell parentShell) {
        super(parentShell);
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);

        final TableViewer tableViewer = new TableViewer(container, SWT.BORDER);
        tableViewer.setInput(new Object());

        final TreeViewer treeViewer = new TreeViewer(container, SWT.BORDER);
        final Tree tree = treeViewer.getTree();
        final GridData gridData = new GridData();
        gridData.heightHint = 205;
        gridData.widthHint = 228;
        tree.setLayoutData(gridData);
        treeViewer.setInput(new Object());
        //
        return container;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }
}
