/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 ******************************************************************************/

/*******************************************************************************
 * This class was taken from the PHPEclipse project and modified to fit into 
 * this project. There shouldn't be a license problem
 * http://phpeclipse.sf.net
 */

package com.rohanclan.cfml.views.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;

import com.rohanclan.cfml.preferences.CFMLPreferenceManager;

/**
 * <code>BrowserView</code> is a simple demonstration of the SWT Browser
 * widget. It consists of a workbench view and tab folder where each tab in the
 * folder allows the user to interact with a control.
 * 
 * @see ViewPart
 */
public class BrowserView extends ViewPart {
	public final static String ID_BROWSER = "com.rohanclan.cfml.views.browser";
	public final static int PROJECT_TAB = 0;
	public final static int HELP_TAB = 2;
	
	protected CFBrowser projectInstance = null;
	protected CFBrowser helpInstance = null;
	
	protected TabFolder folder = null;

	/**
	 * Create the example
	 * @see ViewPart#createPartControl
	 */
	public void createPartControl(Composite frame)
	{
	    CFMLPreferenceManager preferenceManager = new CFMLPreferenceManager();
		try
		{
		    if (preferenceManager.tabbedBrowser()) {
			    folder = new TabFolder(frame,SWT.TOP);
			    TabItem item = new TabItem(folder,SWT.NONE);
			    item.setText("Project browser");
			    Composite container = new Composite(folder,SWT.NONE);
			    item.setControl(container);
				projectInstance = new CFBrowser(container,this);
				
				item = new TabItem(folder,SWT.NONE);
				item.setText("General Browser");
				container = new Composite(folder,SWT.NONE);
			    item.setControl(container);
			    CFBrowser test = new CFBrowser(container,this);
			    test.browser.setUrl("about:blank");
			    
			    item = new TabItem(folder,SWT.NONE);
			    item.setText("Help browser");
			    container = new Composite(folder,SWT.NONE);
			    item.setControl(container);
			    helpInstance = new CFBrowser(container,this);
			    helpInstance.browser.setUrl("http://cfdocs.org/cfeclipse");
			    
			    setFocus();
		    }
		    else {
		        projectInstance = new CFBrowser(frame,this);
		    }
		    
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Called when we must grab focus.
	 * @see org.eclipse.ui.part.ViewPart#setFocus
	 */
	public void setFocus()
	{
		if(projectInstance != null)
		{	
			if(folder != null &&	folder.getTabList().length >= BrowserView.PROJECT_TAB)
				folder.setSelection(BrowserView.PROJECT_TAB);
			
			projectInstance.setFocus();
		}
	}
	
	/**
	 * Called when we must grab focus.
	 * @param whichTab the tab to set focus to
	 * @see org.eclipse.ui.part.ViewPart#setFocus
	 */
	public void setFocus(int whichTab)
	{
		if (BrowserView.HELP_TAB == whichTab && helpInstance != null) {
	        folder.setSelection(BrowserView.HELP_TAB);
			helpInstance.setFocus();
	    }
	    else {
	        setFocus();
	    }
	}
	
	/**
	 * Called when the View is to be disposed
	 */
	public void dispose() 
	{
		if(projectInstance != null)
		{
			projectInstance.dispose();
			projectInstance = null;
		}
		if (helpInstance != null) {
		    helpInstance.dispose();
		    helpInstance = null;
		}
		super.dispose();
	}
	
	/**
	 * sets the browsers url externally. This checks to see if the 
	 * browser exists first so you can assume the browser is there and
	 * just call it
	 * @param url
	 */
	public void setUrl(String url) 
	{
		if(projectInstance != null)
			projectInstance.browser.setUrl(url);
	}
	
	/**
	 * sets the browsers url externally. This checks to see if the 
	 * browser exists first so you can assume the browser is there and
	 * just call it
	 * @param url the URL to go to
	 * @param whichTab the tab to set
	 */
	public void setUrl(String url, int whichTab) 
	{
		if (BrowserView.HELP_TAB == whichTab && helpInstance != null) {
	        helpInstance.browser.setUrl(url);
	    } else  {
	        setUrl(url);
	    }
	}
	
	/**
	 * does a page refresh externally. This checks to see if the 
	 * browser exists first so you can assume the browser is there and
	 * just call it
	 */
	public void refresh() 
	{
		if(projectInstance != null)
			projectInstance.browser.refresh();
	}
	
	// The refresh(whichTab) may be unnecessary. Are we ever going to externally refresh the help view?
	
	/**
	 * does a page refresh externally. This checks to see if the 
	 * browser exists first so you can assume the browser is there and
	 * just call it
	 * @param whichTab the tab to refresh
	 */
	public void refresh(int whichTab) 
	{
	    if (BrowserView.HELP_TAB == whichTab && helpInstance != null) {
	        helpInstance.browser.refresh();
	    } else  {
	        refresh();
	    }
	}
}
