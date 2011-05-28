/*
 * Created on Feb 2, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.editors;

//import org.apache.log4j.Logger;

//import java.util.Iterator;

import org.cfeclipse.cfml.views.browser.CFBrowser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

/**
 * @author Mark Drew
 * 
 * This is the container for the multi-part editor, it has a browser and the CFML EditorPart that is the actual editor
 */
public class CFMLEditor extends MultiPageEditorPart implements IResourceChangeListener {

	/** Editor used in page 1 **/
	private CFMLEditorPart editor;
	public static final String ID = "org.cfeclipse.cfml.editors.CFMLEditor";
	
	public CFMLEditor(){
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}
	public CFMLEditorPart getCFMLEditor(){
		return editor;
	}
	
	/** Create the individual pages **/
	void createEditorPage(){
		editor = new CFMLEditorPart();
		try {
			int index = addPage(editor, getEditorInput());
			setPageText(index,"Editor");
			setPartName(editor.getTitle());
			
		} catch (PartInitException e) {
			ErrorDialog.openError(
					getSite().getShell(),
					"Error creating nested text editor",
					null,
					e.getStatus());
		}
	}
	
	
	
	
	void createBrowserPage(){
		Composite composite = new Composite(getContainer(), SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;

		
		 CFBrowser cfbrowser = new CFBrowser(composite,this);
		 cfbrowser.browser.setUrl("http://www.cfeclipse.org");
		   
		   
		  int index = addPage(composite);
		  setPageText(index, "Browser");
	}
	
	void createCFCDoc(){
		Composite composite = new Composite(getContainer(), SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;
		DocBrowser docbrowser = new DocBrowser(composite,this);
		
		IFileEditorInput fileInput = (IFileEditorInput)getEditorInput();
		IFile file = fileInput.getFile();
		IProject project = file.getProject();
		IPath filePath= fileInput.getFile().getFullPath();
		
		System.out.println(project.getLocation());
		System.out.println(filePath);
		
		
		docbrowser.browser.setUrl("http://dev.local:8888/cfmldoc/gendoc.cfm?project=" + project.getLocation() + "&cfc=" + filePath);
		 int index = addPage(composite);
		 setPageText(index, "CFCDocs");
	}
	

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
	}


	@Override
	protected void createPages() {
		createEditorPage();
		createBrowserPage();


		
		IEditorInput input = getEditorInput();
		String[] split = input.getName().split("\\.");
		String extension = split[split.length-1];
		if(extension.equalsIgnoreCase("cfc")){
			createCFCDoc();
		}
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		editor.doSave(monitor);
	}


	@Override
	public void doSaveAs() {
		editor.doSaveAs();
	}


	@Override
	public boolean isSaveAsAllowed() {
		return editor.isSaveAsAllowed();
	}
		
}