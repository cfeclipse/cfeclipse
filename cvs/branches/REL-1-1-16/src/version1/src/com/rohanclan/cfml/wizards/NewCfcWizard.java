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
package com.rohanclan.cfml.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import java.io.*;
import org.eclipse.ui.*;
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

public class NewCfcWizard extends Wizard implements INewWizard {
	private NewCfcWizardPage page;
	private NewCfcWizardPageTwo pageTwo;
	private NewCfcWizardPageThree pageThree;
	private NewCfcWizardPageFour pageFour;
	private IStructuredSelection selection;

	/**
	 * Constructor for NewCfmlWizard.
	 */
	public NewCfcWizard() {
		
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the pages to the wizard.
	 */

	public void addPages() {
		
		page = new NewCfcWizardPage(selection);
		pageTwo = new NewCfcWizardPageTwo(selection);
		pageThree = new NewCfcWizardPageThree(selection);
		pageFour = new NewCfcWizardPageFour(selection);
		addPage(page);
		addPage(pageTwo);
		addPage(pageThree);
		addPage(pageFour);
		
		
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getCfcBean().getPath();
		final String fileName = page.getCfcBean().getName();
		final StringBuffer sb = createStringBuffer();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor, sb);
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

	private void doFinish(
		String containerName,
		String fileName,
		IProgressMonitor monitor,
		StringBuffer sb)
		throws CoreException 
	{
		// create a sample file
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		
		
		
		
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName + ".cfc"));
		try {
			InputStream stream = new ByteArrayInputStream(sb.toString().getBytes());
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
	
	private StringBuffer createStringBuffer() {		
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("<cfcomponent name=\"");
		sb.append(page.getCfcBean().getName() + "\" ");
		sb.append("displayname=\"" + page.getCfcBean().getDisplayName().trim() + "\"");
		sb.append(" hint=\"" + page.getCfcBean().getHint().trim() + "\"");
		sb.append(" extends=\"" + page.getCfcBean().getExtendCfc().trim() + "\">");
		sb.append("\n\n");
		if(pageTwo.hasProperties())
			sb.append(pageTwo.getPropertiesAsTags());
		if(pageThree.hasFunctions())
			sb.append(pageThree.getFunctionTags());
		sb.append("</cfcomponent>");
		return sb;
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "com.rohanclan.cfml", IStatus.OK, message, null);
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