/*
 * Created on Nov 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.util;


import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import com.rohanclan.cfml.CFMLPlugin;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.ui.*;
import org.eclipse.jface.action.IStatusLineManager;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AlertUtils {

    /**
     * 
     */
    public AlertUtils() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static void alertUser(Exception e) {
        MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_ERROR);
        String message = e.getMessage();
        if (e.getMessage() == null) {
            message = e.toString();
        }
        msg.setText("Error!");
        msg.setMessage(message);
        msg.open();
    }
    
    public static void alertUser(String message) {
        MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION);
        msg.setText("Error!");
        msg.setMessage(message);
        msg.open();
    }
    
    public static void showStatusMessage(String message,IEditorPart editorPart) {
        try {
         IStatusLineManager statusManager = editorPart.getEditorSite().getActionBars().getStatusLineManager();
         statusManager.setMessage(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showStatusErrorMessage(String message,IViewPart viewPart) {
        try {
         IStatusLineManager statusManager = viewPart.getViewSite().getActionBars().getStatusLineManager();
         statusManager.setErrorMessage(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showStatusMessage(String message,IViewPart viewPart) {
        try {
         IStatusLineManager statusManager = viewPart.getViewSite().getActionBars().getStatusLineManager();
         statusManager.setMessage(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
