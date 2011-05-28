/*
 * Created on Mar 27, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
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
package org.cfeclipse.cfml.frameworks.popup.actions;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.editors.decoration.URLDecorator;
import org.cfeclipse.cfml.frameworks.decorators.ConfigFileDecorator;
import org.cfeclipse.cfml.frameworks.dialogs.SetResourceAsFramework;
import org.cfeclipse.cfml.frameworks.views.FrameworksView;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class SetFrameworkFile implements IObjectActionDelegate {
	
	private IResource resource;
	private Shell shell;
	private Log logger = LogFactory.getLog(SetFrameworkFile.class);
	/**
	 * Constructor for Action1.
	 */
	public SetFrameworkFile() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		SetResourceAsFramework seaf = new SetResourceAsFramework(this.shell, this.resource);
		if(seaf.open() == IDialogConstants.OK_ID){
				String selectedFramework = seaf.getSelectedFramework();
				try {
					
					if(selectedFramework != null){
						ConfigFileDecorator configFileDecorator = ConfigFileDecorator.getConfigFileDecorator();
						this.resource.setPersistentProperty(new QualifiedName("", "isFrameworkConfig"), "true");
						this.resource.setPersistentProperty(new QualifiedName("", "FrameworkType"), selectedFramework);
						ConfigFileDecorator.getConfigFileDecorator().refresh();
					}
					else{
						this.resource.setPersistentProperty(new QualifiedName("", "isFrameworkConfig"), null);
						this.resource.setPersistentProperty(new QualifiedName("", "FrameworkType"), null);
						ConfigFileDecorator.getConfigFileDecorator().refresh();
					}
					
				} catch (CoreException e) {
					logger.error(e);
					//e.printStackTrace();
				}
				
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow[] workbenchWindows = wb.getWorkbenchWindows();
				
				for (int i = 0; i < workbenchWindows.length; i++) {
					IWorkbenchWindow window = workbenchWindows[i];
					IWorkbenchPage[] pages = window.getPages();
					for (int j = 0; j < pages.length; j++) {
						IViewReference[] viewReferences = pages[i].getViewReferences();
						for (int k = 0; k < viewReferences.length; k++) {
							if(viewReferences[k].getId().equalsIgnoreCase(FrameworksView.ID)){
								FrameworksView fw_view = (FrameworksView)viewReferences[k].getView(false);
								fw_view.refreshFrameworkTree();
							}
						}
					}
				}
				ConfigFileDecorator.getConfigFileDecorator().refresh();
				
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		 Iterator selectionIter = ((StructuredSelection)selection).iterator();
		 while(selectionIter.hasNext()){
			 this.resource = (IResource)selectionIter.next();
		 }
		
	}

}
