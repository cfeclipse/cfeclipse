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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

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
		try
		{
			instance = new CFBrowser(frame);
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
	
	public void setUrl(String url) 
	{
		if(instance != null)
			instance.browser.setUrl(url);
	}
}
