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

import java.text.MessageFormat;
import java.util.MissingResourceException;
//import java.util.ResourceBundle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.WorkbenchPart; 
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.util.CFPluginImages;
//import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.QualifiedName;
import com.rohanclan.cfml.preferences.CFMLPreferenceConstants;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;

public class CFBrowser{
	protected int index;
	protected boolean busy;
	//protected Image images[];
	protected Text location;
	protected Browser browser;
	private WorkbenchPart container;
	private CFMLPreferenceManager preferenceManager;
	private boolean locationSelectedOnce = false;
	
	/**
	 * Creates an instance of a ControlExample embedded inside the supplied
	 * parent Composite.
	 * @param parent
	 * the container of the example
	 */
	public CFBrowser(Composite parent,WorkbenchPart container) 
	{
		preferenceManager = new CFMLPreferenceManager();
		this.container = container;
		//initResources();
		final Display display = parent.getDisplay();
		FormLayout layout = new FormLayout();
		parent.setLayout(layout);
		
		ToolBar toolbar = new ToolBar(parent, SWT.NONE);
		final ToolItem itemBack = new ToolItem(toolbar, SWT.PUSH);
		//itemBack.setText(getResourceString("Back"));
		itemBack.setImage(CFPluginImages.get(CFPluginImages.ICON_BACK));
		itemBack.setToolTipText(getResourceString("Back"));
		
		final ToolItem itemForward = new ToolItem(toolbar, SWT.PUSH);
		//itemForward.setText(getResourceString("Forward"));
		itemForward.setImage(CFPluginImages.get(CFPluginImages.ICON_FORWARD));
		itemForward.setToolTipText(getResourceString("Forward"));
		
		final ToolItem itemStop = new ToolItem(toolbar, SWT.PUSH);
		//itemStop.setText(getResourceString("Stop"));
		itemStop.setImage(CFPluginImages.get(CFPluginImages.ICON_STOP));
		itemStop.setToolTipText(getResourceString("Stop"));
		
		final ToolItem itemRefresh = new ToolItem(toolbar, SWT.PUSH);
		//itemRefresh.setText(getResourceString("Refresh"));
		itemRefresh.setImage(CFPluginImages.get(CFPluginImages.ICON_REFRESH));
		itemRefresh.setToolTipText(getResourceString("Refresh"));
		
		final ToolItem itemHome = new ToolItem(toolbar, SWT.PUSH);
		//itemGo.setText(getResourceString("Go"));
		itemHome.setImage(CFPluginImages.get(CFPluginImages.ICON_HOME));
		itemHome.setToolTipText(getResourceString("Go to project homepage"));
		
		final ToolItem itemGo = new ToolItem(toolbar, SWT.PUSH);
		//itemGo.setText(getResourceString("Go"));
		itemGo.setImage(CFPluginImages.get(CFPluginImages.ICON_PROCESS));
		itemGo.setToolTipText(getResourceString("Go"));
		
		location = new Text(parent, SWT.BORDER);
		final Canvas canvas = new Canvas(parent, SWT.ICON_INFORMATION);
		
		/* Removed by Spike. It seems to have no effect on the 
		 * operation of the browser and may be the cause of the 
		 * OS X CPU hogging bug.
		//this is supposed to be the working icon but...
		display.asyncExec(new Runnable() {
			public void run() 
			{
				if(canvas.isDisposed())
					return;
				if(busy) 
				{
					 index++;
					if(index == images.length)
					index = 0;
					canvas.redraw();
					
				}
				
				display.timerExec(150, this);
			}
		});
		
		*/
		
		final Label status = new Label(parent, SWT.NONE);
		final ProgressBar progressBar = new ProgressBar(parent, SWT.NONE);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 5);
		toolbar.setLayoutData(data);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(canvas, 5, SWT.DEFAULT);
		data.bottom = new FormAttachment(status, -5, SWT.DEFAULT);
		try 
		{
			browser = new Browser(parent, SWT.NONE);
			
			browser.setLayoutData(data);
		}
		catch(SWTError e)
		{
			// Browser widget could not be instantiated
			Label label = new Label(parent, SWT.CENTER | SWT.WRAP);
			label.setText(getResourceString("Browser Not Created (" + e.toString() + ")" ));
			label.setLayoutData(data);
		}
		
		data = new FormData();
		data.width = 24;
		data.height = 24;
		data.top = new FormAttachment(0, 5);
		data.right = new FormAttachment(100, -5);
		canvas.setLayoutData(data);
		data = new FormData();
		data.top = new FormAttachment(toolbar, 0, SWT.TOP);
		data.left = new FormAttachment(toolbar, 5, SWT.RIGHT);
		data.right = new FormAttachment(canvas, -5, SWT.DEFAULT);
		location.setLayoutData(data);
		data = new FormData();
		data.left = new FormAttachment(0, 5);
		data.right = new FormAttachment(progressBar, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment(100, -5);
		status.setLayoutData(data);
		data = new FormData();
		data.right = new FormAttachment(100, -5);
		data.bottom = new FormAttachment(100, -5);
		progressBar.setLayoutData(data);
		
		location.addFocusListener(new FocusListener() {
	        public void focusGained(FocusEvent e) {}
	        public void focusLost(FocusEvent e) {
	            locationSelectedOnce = false;
	        }
		}
		);
		
		location.addMouseListener(new  MouseListener() {
		    public void mouseUp(MouseEvent e) {
		        if (!locationSelectedOnce) {
		            System.out.println("No text selected; selecting");
		            location.selectAll();
		            locationSelectedOnce = true;
		        }
		    }
		    public void mouseDown(MouseEvent e) {}
		    public void mouseDoubleClick(MouseEvent e) {}
		});
		
		if(browser != null) 
		{
			itemBack.setEnabled(browser.isBackEnabled());
			itemForward.setEnabled(browser.isForwardEnabled());
			Listener listener = new Listener(){
				public void handleEvent(Event event) 
				{
				//System.out.println("Event fired");
					ToolItem item = (ToolItem) event.widget;
					if (item == itemBack) {
					//System.out.println("Back pressed");
						browser.back();
					}
					else if (item == itemForward) {
					//System.out.println("Forward pressed");
						browser.forward();
					}
					else if (item == itemStop){
					//System.out.println("Stop pressed");
						browser.stop();
					}
					else if (item == itemRefresh) {
					//System.out.println("Refresh pressed");
						browser.refresh();
					}
					else if (item == itemGo) {
					//System.out.println("Go pressed");
						browser.setUrl(location.getText());
					}
					else if (item == itemHome) {
					//System.out.println("Home pressed");
						browser.setUrl(getProjectURL());
					}
				}
			};
			
			browser.addLocationListener(new LocationListener(){
				public void changed(LocationEvent event)
				{
					busy = true;
					if (event.top)
					location.setText(event.location);
				}
				public void changing(LocationEvent event) 
				{
				}
			});
			
			browser.addProgressListener(new ProgressListener(){
				public void changed(ProgressEvent event)
				{
					if(event.total == 0)
						return;
					int ratio = event.current * 100 / event.total;
					progressBar.setSelection(ratio);
					busy = event.current != event.total;
					if(!busy)
					{
						index = 0;
						canvas.redraw();
					}
				}
				public void completed(ProgressEvent event)
				{
					itemBack.setEnabled(browser.isBackEnabled());
					itemForward.setEnabled(browser.isForwardEnabled());
					progressBar.setSelection(0);
					busy = false;
					index = 0;
					canvas.redraw();
				}
			});
			
			browser.addStatusTextListener(new StatusTextListener()
			{
				public void changed(StatusTextEvent event)
				{
				    if (!status.isDisposed()) {
				        status.setText(event.text);
				    }
				}
			});
			
			if(parent instanceof Shell)
			{
				final Shell shell = (Shell) parent;
				browser.addTitleListener(new TitleListener(){
					public void changed(TitleEvent event)
					{
						shell.setText(
							event.title + " - " + getResourceString("window.title")
						);
					}
				});
			}
			
			itemBack.addListener(SWT.Selection, listener);
			itemForward.addListener(SWT.Selection, listener);
			itemStop.addListener(SWT.Selection, listener);
			itemRefresh.addListener(SWT.Selection, listener);
			itemGo.addListener(SWT.Selection, listener);
			itemHome.addListener(SWT.Selection, listener);
			
			location.addListener(SWT.DefaultSelection, new Listener() {
				public void handleEvent(Event e)
				{
					browser.setUrl(location.getText());
				}
			});
			
			initialize(display, browser);
			//browser.setUrl(getResourceString("Startup"));
			//for now...
			String url = getProjectURL();
			if(url == null || url.equals(""))
			{
				browser.setUrl(preferenceManager.defaultProjectURL());
			}
			else
			{
				browser.setUrl(url);
			}
		}
	}
	
	private String getProjectURL() {
		String projectURL = preferenceManager.defaultProjectURL();
		
		try 
		{
			IEditorPart editorPart = container.getSite().getWorkbenchWindow().getActivePage().getActiveEditor();

			if(editorPart  != null)
			{
				IFileEditorInput input = (IFileEditorInput)editorPart.getEditorInput() ;
				IFile file = input.getFile();
				IProject activeProject = file.getProject();
				QualifiedName propertyName = new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL);
				projectURL = activeProject.getPersistentProperty(propertyName);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return projectURL;
	}
	
	
	
	
	/**
	 * Gets a string from the resource bundle. We don't want to crash because of
	 * a missing String. Returns the key if not found.
	 */
	static String getResourceString(String key)
	{
		return CFMLPlugin.getResourceString(key);
	}
	
	/**
	 * Gets a string from the resource bundle and binds it with the given
	 * arguments. If the key is not found, return the key.
	 */
	static String getResourceString(String key, Object[] args)
	{
		try
		{
			return MessageFormat.format(getResourceString(key), args);
		} 
		catch(MissingResourceException e) 
		{
			return key;
		}
		catch (NullPointerException e)
		{
			return "!" + key + "!";
		}
	}
	
	static void initialize(final Display display, Browser browser)
	{
		if(browser != null)
		{
			browser.addOpenWindowListener(new OpenWindowListener(){
				public void open(WindowEvent event)
				{
					Shell shell = new Shell(display);
					shell.setLayout(new FillLayout());
					Browser browser = new Browser(shell, SWT.NONE);
					initialize(display, browser);
					event.browser = browser;
				}
			});
			
			browser.addVisibilityWindowListener(new VisibilityWindowListener(){
				public void hide(WindowEvent event)
				{
				}
				
				public void show(WindowEvent event)
				{
					Browser browser = (Browser) event.widget;
					Shell shell = browser.getShell();
					if(event.location != null)
						shell.setLocation(event.location);
					if(event.size != null)
					{
						Point size = event.size;
						shell.setSize(shell.computeSize(size.x, size.y));
					}
					shell.open();
				}
			});
			
			browser.addCloseWindowListener(new CloseWindowListener(){
				public void close(WindowEvent event)
				{
					Browser browser = (Browser) event.widget;
					Shell shell = browser.getShell();
					shell.close();
				}
			});
		}
	}

	/**
	 * Disposes of all resources associated with a particular instance of the
	 * BrowserExample.
	 */
	public void dispose()
	{
		browser.dispose();	
	}
	
	/**
	 * Grabs input focus.
	 */
	public void setFocus()
	{
		if(location != null)
			location.setFocus();
	}
}