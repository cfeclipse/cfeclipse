/* 
 * $Id: ExternalBrowserAction.java,v 1.3 2005-06-14 21:36:11 smilligan Exp $
 * $Revision: 1.3 $
 * $Date: 2005-06-14 21:36:11 $
 * 
 * Created Mar 5, 2005 1:45:48 AM
 *
 *
 * Copyright (c) 2005 Stephen Milligan.  All rights reserved.
 *
 */
package com.rohanclan.cfml.editors.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import com.rohanclan.cfml.util.AlertUtils;
/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.3 $
 */
public class ExternalBrowserAction implements IEditorActionDelegate {
    
    IEditorPart editor = null;
    String externalBrowser = "";
    String rootURL = "";
    String URLMask = ""; 
    /**
     * 
     */
    public ExternalBrowserAction() {
       
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
     */
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        this.editor = targetEditor;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        try {
            
            IEditorInput input = this.editor.getEditorInput();
            String filePath = "";
            if (input instanceof FileEditorInput) {
                
                FileEditorInput fileInput = (FileEditorInput)input;
                FileInputStream in;
                try {
                    in = new FileInputStream(fileInput.getFile().getProject().getLocation().toFile()+"/cfeclipse.properties");
                } catch (FileNotFoundException e) {
                	MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION | SWT.YES | SWT.NO);
                    msg.setText("Oops!");
                    msg.setMessage("There is no cfeclipse.properties file in the root of this project.\nWould you like to have one automatically created now?");
                    int result = msg.open();
                    if (result == SWT.YES) {
                    	FileOutputStream out = new FileOutputStream(fileInput.getFile().getProject().getLocation().toFile()+"/cfeclipse.properties");
                    	out.write(getDefaultProperties(fileInput.getFile().getProject()).getBytes());
                    	out.close();
                    	fileInput.getFile().getProject().refreshLocal(IProject.DEPTH_INFINITE,null);
                    	IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),fileInput.getFile().getProject().getFile("cfeclipse.properties"));
                    }
                    return;
                }
                Properties p = new Properties();
                p.load(in);
                if (p.getProperty("BrowserPath") != null) {
                    externalBrowser = p.getProperty("BrowserPath");
                }
                if (p.getProperty("RootURL") != null) {
                    rootURL = p.getProperty("RootURL");
                }
                if (p.getProperty("URLMask") != null) {
                    URLMask = p.getProperty("URLMask");
                }
                
                filePath = fileInput.getFile().getFullPath().toString();
                
                if (!filePath.startsWith(URLMask)) {
                	
                	AlertUtils.alertUser("The current file is not beneath the webroot for the project. You may need to edit cfeclipse.properties in the root of the project and change the value of the URLMask property.");
                    
                    return;
                }
                filePath = filePath.substring(URLMask.length());
                
            }
            String[] cmd = new String[] {
               externalBrowser
                ,rootURL+filePath
            }; 
            
           Process p =  Runtime.getRuntime().exec(cmd);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        // TODO Auto-generated method stub

    }
    
    private String getDefaultProperties(IProject project) {
    	StringBuffer properties = new StringBuffer();
    	
    	properties.append("#This file is used by CFEclipse to locate and open an external browser on a given project file.\n");
    	properties.append("#The path in the file system to the browser executable\n");
    	properties.append("BrowserPath=C:/Program Files/Mozilla Firefox/firefox.exe\n");
    	properties.append("#The URL to the project webroot directory\n");
    	properties.append("RootURL=http://localhost" + project.getFullPath() +"/\n");
    	properties.append("#The path in the current project to the webroot directory. Including the project name itself.\n");
    	properties.append("URLMask=" + project.getFullPath() + "/\n");
    	
    	return properties.toString();
    }

}


/* 
 * CVS LOG
 * ====================================================================
 *
 * $Log: not supported by cvs2svn $
 * Revision 1.2  2005/05/12 22:34:06  smilligan
 * Made a few changes so that external files are now opened in their project context if they are part of a project.
 *
 * Revision 1.1  2005/03/15 04:50:44  smilligan
 * Added open in external browser action.
 *
 * Revision 1.2  2005/03/08 04:32:57  smilligan
 * *** empty log message ***
 *
 * Revision 1.1  2005/03/08 04:21:34  smilligan
 * *** empty log message ***
 *
 * Revision 1.1  2005/03/05 19:15:27  smilligan
 * *** empty log message ***
 *
 */