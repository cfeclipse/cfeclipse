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
	protected CFBrowser instance = null;

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
			    TabFolder folder = new TabFolder(frame,SWT.TOP);
			    TabItem item = new TabItem(folder,SWT.NONE);
			    item.setText("Project browser");
			    Composite container = new Composite(folder,SWT.NONE);
			    item.setControl(container);
				instance = new CFBrowser(container,this);
				
				item = new TabItem(folder,SWT.NONE);
				item.setText("General Browser");
				container = new Composite(folder,SWT.NONE);
			    item.setControl(container);
			    CFBrowser test = new CFBrowser(container,this);
		    }
		    else {
		        instance = new CFBrowser(frame,this);
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
		if(instance != null)
			instance.setFocus();
	}
	
	/**
	 * Called when the View is to be disposed
	 */
	public void dispose() 
	{
		if(instance != null)
		{
			instance.dispose();
			instance = null;
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
		if(instance != null)
			instance.browser.setUrl(url);
	}
	
	/**
	 * does a page refresh externally. This checks to see if the 
	 * browser exists first so you can assume the browser is there and
	 * just call it
	 */
	public void refresh() 
	{
		if(instance != null)
			instance.browser.refresh();
	}
	
	
	
    
	
}
