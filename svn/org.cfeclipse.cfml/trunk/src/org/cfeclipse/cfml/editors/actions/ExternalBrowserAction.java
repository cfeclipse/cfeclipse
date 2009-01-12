/* 
 * $Id: ExternalBrowserAction.java,v 1.4 2005/07/25 01:29:38 rohanr2 Exp $
 * $Revision: 1.4 $
 * $Date: 2005/07/25 01:29:38 $
 * 
 * Created Mar 5, 2005 1:45:48 AM
 *
 *
 * Copyright (c) 2005 Stephen Milligan.  All rights reserved.
 *
 */
package org.cfeclipse.cfml.editors.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.BrowserPreferenceConstants;
import org.cfeclipse.cfml.preferences.BrowserPreferencePage;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.properties.ProjectPropertyPage;
import org.cfeclipse.cfml.urls.URLManager;
import org.cfeclipse.cfml.util.AlertUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.dialogs.PropertyDialog;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.4 $
 */
public class ExternalBrowserAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {
    
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
                filePath = fileInput.getFile().getFullPath().toString();
                
                
               //Need to figure out what the url is for this file (do the rest if it isnt defined)
                	
            //Try and get the workbench preferneces and project properties for the url!
                String browserAppPath = "";
                String primaryBrowserPath = CFMLPlugin.getDefault().getPluginPreferences().getString(BrowserPreferenceConstants.P_PRIMARY_BROWSER_PATH);
                String secondaryBrowserPath = CFMLPlugin.getDefault().getPluginPreferences().getString(BrowserPreferenceConstants.P_SECONDARY_BROWSER_PATH);
                
                browserAppPath = primaryBrowserPath;
                if(action.getActionDefinitionId().equals("org.cfeclipse.cfml.editors.actions.ExternalBrowserActionID2")){
                	browserAppPath = secondaryBrowserPath;
                	
                }
                //Check that the application exists
                File BrowserApp = new File(browserAppPath);
                if(!BrowserApp.exists()){
                	MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION | SWT.YES | SWT.NO);
                    msg.setText("Oops!");
                    msg.setMessage("The Browser application cannot be found in " + browserAppPath + " Would you like to edit your preferences now?");
                    int result = msg.open();
                    if (result == SWT.YES) {
                    	IPreferencePage page = new BrowserPreferencePage();
                    	page.setTitle("Browsers");
                    	page.setDescription("You can define the  primary and secondary browsers that are launched when you press F12(primary) and Shift+F12(secondary)");
                    	   PreferenceManager mgr = new PreferenceManager();
                    	   IPreferenceNode node = new PreferenceNode("1", page);
                    	   mgr.addToRoot(node);
                    	   PreferenceDialog dialog = new PreferenceDialog(this.editor.getSite().getShell(), mgr);
                    	   dialog.create();
                    	   dialog.setMessage(page.getTitle());
                    	   dialog.open();
                    }
                    return;
                }
                
                
                IFile file = fileInput.getFile();
                String URLpath = "";
                String url = URLManager.getURL(file, new ArrayList());

                if(url != null){
            		URLpath = url;
            	}
                
                
                
            	else{
            		CFMLPropertyManager manager = new CFMLPropertyManager();
            		URLpath = manager.projectURL(fileInput.getFile().getProject());	
            	}
                
               
                
                //Check that the url exists, have set the default to nothing so that if it is blank we can show em an error
                System.out.println("The Path is |" + URLpath.length() + "|");
                if(URLpath.length() == 0){
                	MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_ERROR | SWT.OK);
                    msg.setText("Oops!");
                    msg.setMessage("The URL for your project has not been defined, edit the CFEclipse properties for this project and enter the URL for this file");
                   int result = msg.open();
                   /* if (result == SWT.YES) {
                    	 IWorkbench wb = PlatformUI.getWorkbench();
                    	 ISelection sel = (ISelection)fileInput.getFile().getProject();
                    	   
                    	   PropertyPage page = new ProjectPropertyPage();
                    	   PreferenceManager mgr = new PreferenceManager();
                    	   IPreferenceNode node = new PreferenceNode("1", page);
                    	   mgr.addToRoot(node);
                    	   PropertyDialog dialog = new PropertyDialog(this.editor.getSite().getShell(), mgr, sel);
                    	   dialog.create();
                    	   dialog.setMessage(page.getTitle());
                    	   dialog.open();
                    }*/
                   return;
                	
                }
                String[] cmd = new String[] {
                		browserAppPath
                         ,URLpath
                     }; 
                
                if(System.getProperty("os.name").equals("Mac OS X")){
                	//Need to get the application etc.
                	String app = browserAppPath.split("/")[browserAppPath.split("/").length-1];
                	 Runtime.getRuntime().exec(new String[]{"open", "-a", app, URLpath}); 
                	
                }
                else {
                	
                	Runtime.getRuntime().exec(cmd);
                }
                
                
                
                
                //
               
            } 
                /*System.out.println(cmd[0] + cmd[1]);
                
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
            
           //Process p =  Runtime.getRuntime().exec(cmd);
        	  Runtime.getRuntime().exec(cmd);
         */  
       } catch (Exception e) {
    	   System.out.println(e);
           e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
	public void selectionChanged(IAction action, ISelection selection){
		if(editor != null){
			setActiveEditor(null,  editor.getSite().getPage().getActiveEditor());
		}
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

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		this.editor = window.getActivePage().getActiveEditor();
	}

}
