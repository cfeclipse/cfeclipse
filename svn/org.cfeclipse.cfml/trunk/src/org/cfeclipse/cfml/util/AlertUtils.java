/*
 * Created on Nov 10, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
package org.cfeclipse.cfml.util;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;

/**
 * A series of helper functions to make life easier for us to tell
 * the user about anything that we feel like. Helper functions are
 * available for message boxes and for displaying information on
 * the status bar.
 * 
 * @author Stephen Milligan
 */
public class AlertUtils {

    /**
     * 
     */
    public AlertUtils() {
        super();
    }
    
    /**
     * Alerts the user to an exception. No formatting is applied to the incoming
     * exception so what you give is what the user gets.
     * 
     * This method also logs the exception to the console in case a developer is
     * a-watching and wishes to view the log. 
     * 
     * @param e The exception to display to the user.
     */
    public static void alertUser(Exception e) {
        MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_ERROR);
        String message = e.getMessage();
        if (e.getMessage() == null) {
            message = e.toString();
        }
        if (e.getCause() != null) {
        	message += " Cause: " + e.getCause().getMessage();
            if (e.getCause().getCause() != null) {            	
            	message += " Cause: " + e.getCause().getCause().getMessage();
            }
        }
        e.printStackTrace();
        msg.setText("Error!");
        msg.setMessage(message);
        msg.open();
    }
    
    /**
     * Alerts the user as to an error message.
     * The message will be displayed with the title "Error!".
     * 
     * @param message The message to display.
     */
    public static int alertUser(String message) {
        MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION);
        msg.setText("Error!");
        msg.setMessage(message);
        return msg.open();
    }
    
    /**
     * Sets the status bar's message for the user for an input. 
     * 
     * @param message The message to display
     * @param editorPart The editor part that wishes to display the message.
     */
    public static void showStatusMessage(String message,IEditorPart editorPart) {
        try {
         IStatusLineManager statusManager = editorPart.getEditorSite().getActionBars().getStatusLineManager();
         statusManager.setMessage(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets an error status bar message for the user for an input. 
     * 
     * @param message The message to display
     * @param viewPart The view part that wishes to display the message
     */
    public static void showStatusErrorMessage(String message,IViewPart viewPart) {
        try {
         IStatusLineManager statusManager = viewPart.getViewSite().getActionBars().getStatusLineManager();
         statusManager.setErrorMessage(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the status bar's message for the user for an input. 
     * 
     * @param message The message to display
     * @param viewPart The view part that wishes to display the message.
     */
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
