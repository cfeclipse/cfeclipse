/*
 * Created on 10.04.2004
 *
 * The MIT License
 * Copyright (c) 2004 Chris Queener
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
package org.cfeclipse.cfml.wizards.templatefilewizard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.views.snips.SnipReader;
import org.cfeclipse.cfml.views.snips.SnipVarParser;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;


/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "cfm". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class NewTemplateFileWizard extends Wizard implements INewWizard {
	private NewTemplateFileWizardPage page;
	private ISelection selection;

	/** the root directory */
	protected IPath snipBase;
	
	protected SnipReader snipReader;
	
	private CFMLPropertyManager propertyManager;
	
	protected String contents = "";
	/**
	 * Constructor for NewCfmlWizard.
	 */
	public NewTemplateFileWizard() {
		super();
		setNeedsProgressMonitor(true);
		propertyManager = new CFMLPropertyManager();
		snipReader = new SnipReader();
		
		CFMLPropertyManager propertyManager = new CFMLPropertyManager();
		
		
			snipBase = new Path(propertyManager.defaultSnippetsPath());
			
		
		
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		if(selection != null)
		{
			page = new NewTemplateFileWizardPage(selection);
			//pageTwo = new NewCfmlWizardPageTwo(selection);
			addPage(page);
			
			//addPage(pageTwo);
		}
		else
		{
			System.err.println("selection is null?");
		}
	}

	private IResource getContainingResource() {
		final String containerName = page.getContainerName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.findMember(new Path(containerName));
	}
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
		IResource resource = getContainingResource();
		
		if (!resource.exists() || !(resource instanceof IContainer)) 
		{
			MessageDialog.openError(getShell(), "Error", "Container \"" + containerName + "\" does not exist.");
		}
		
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		
		if (file.exists()) {
		    boolean overwrite = MessageDialog.openQuestion(this.getShell(), "File exists", "File already exists. Do you want to overwrite the existing file?");
			if (!overwrite) {
			    return false;
			}
		}

		// Parse the snippet and prompt for variables
		String startBlock = SnipVarParser.parse(snipReader.getSnipStartBlock(),null,getShell());
		String endBlock = SnipVarParser.parse(snipReader.getSnipEndBlock(),null,getShell());
		contents = startBlock + endBlock;
	    
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(String containerName,String fileName,IProgressMonitor monitor)
		throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		
		if (!resource.exists() || !(resource instanceof IContainer)) 
		{
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		
		try {
			InputStream stream = openContentStream();
			if (file.exists()) {
			    file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e) {
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}
	
	/**
	 * We will initialize file contents with a sample text.
	 */
	private InputStream openContentStream()
	{

	    //This is the initial file contents for the file that 
		return new ByteArrayInputStream(contents.getBytes());
	}

	private void throwCoreException(String message) throws CoreException 
	{
		IStatus status =
			new Status(IStatus.ERROR, "org.cfeclipse.cfml", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}