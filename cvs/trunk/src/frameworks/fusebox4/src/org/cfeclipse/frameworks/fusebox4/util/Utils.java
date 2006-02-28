/*
 * Created on Dec 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;

/**
 * @author markd
 *
 * This is a utility class for random snippets
 */
public class Utils {
	private static boolean printoutput = true;
	
	/**
	 * This method just prints
	 * @param obj
	 */
	public static void println(Object obj){
		if(printoutput){
			System.out.println(obj.toString());
		}
	}
	
	 /**
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromInputStream(InputStream is){
		try {
			int k;
			InputStream in = is;
			StringBuffer stringFromIS = new StringBuffer();
			int aBuffSize = 1123123;     		
			
			byte buff[] = new byte[aBuffSize]; 
			OutputStream xOutputStream = new ByteArrayOutputStream(aBuffSize); 
			while ( (k=in.read(buff) ) != -1){
				xOutputStream.write(buff,0,k);
			}
			stringFromIS.append(xOutputStream.toString()); 
			return stringFromIS.toString();
		} catch (IOException e) {
			return "";
		}
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
	
}
