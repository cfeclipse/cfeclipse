/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LogViewer extends ApplicationWindow {

    private StyledText styledText;
    public LogViewer() {
        super(null);
        createActions();
        addToolBar(SWT.NONE);
        addMenuBar();
        addStatusLine();
    }

    protected Control createContents(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FillLayout());

        StyledText text = new StyledText(container, SWT.BORDER|SWT.MULTI);
        text.setLayout(new FillLayout());
        //
        return container;
    }

    private void createActions() {
    }

    protected MenuManager createMenuManager() {
        MenuManager result = new MenuManager("menu");
        return result;
    }

    protected ToolBarManager createToolBarManager(int arg) {
        ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT | SWT.WRAP);
        return toolBarManager;
    }

    protected StatusLineManager createStatusLineManager() {
        StatusLineManager statusLineManager = new StatusLineManager();
        statusLineManager.setMessage(null, "");
        return statusLineManager;
    }

    public static void main(String args[]) {
        LogViewer window = new LogViewer();
        window.setBlockOnOpen(true);
        window.open();
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("New Application");
    }

    protected Point getInitialSize() {
        return new Point(500, 375);
    }
}
