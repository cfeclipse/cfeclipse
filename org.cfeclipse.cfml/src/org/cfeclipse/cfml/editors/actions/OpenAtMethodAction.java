/*
 * Created on Jun 19, 2004
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
package org.cfeclipse.cfml.editors.actions;

import java.util.Iterator;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * @author Rob
 *
 * This action causes a file to be opened in the editor. The file can either
 * be a string-based path relative to the workspace root, or an IFile.
 * The file will be opened with whatever editor is associated with the file
 * type.
 */
public class OpenAtMethodAction implements IWorkbenchWindowActionDelegate,IEditorActionDelegate {
	protected ITextEditor editor = null;
	protected String filename = "untitled.cfm";
	protected IFile file;
	protected boolean success = true;
	protected Shell shell;
	private String methodName;
	
	public OpenAtMethodAction()
	{
		super();
	}
	
	/**
	 * Creates the open action based upon an IFile
	 * 
	 * @param srcFile The file to open
	 */
	public OpenAtMethodAction(IFile srcFile, String method) {
		this.setFile(srcFile);
		setMethodName(method);
	}

	/**
	 * Creates the action based upon the workspace-relative
	 * filename provided
	 * 
	 * @param filename The file to open, relative to the workspace root.
	 */
	public OpenAtMethodAction(String filename, String method)
	{
		super();
		setFilename(filename);
		setMethodName(method);
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	
	public String getMethodName()
	{
		return this.methodName;
	}
	
	/**
	 * Sets the file to be opened.
	 * 
	 * @param srcFile
	 */
	public void setFile(IFile srcFile) {
		this.file = srcFile;
	}
	
	/**
	 * Sets the filename to be opened.
	 * 
	 * @param filename The file to open, relative to the workspace root.
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		this.file = root.getFile(new Path(filename));
	}
		
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{
		if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
		{
			editor = (ITextEditor)targetEditor;
		}
	}
	
	public void run()
	{
		run(null);
	}
		
	public void run(IAction action) 
	{
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if(!root.exists(file.getFullPath())) {
			//System.err.println("File \'" + filename + "\' does not exist. Stupid user.");
			this.success = false;
			return;
		}else{
		    this.success = true;
		}
		
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		try
		{
			IEditorPart part =  IDE.openEditor(page, file, true);
			if(!(part instanceof CFMLEditor))
				return;
			
			//CFMLEditor cfEditor = (CFMLEditor)part;#
			CFMLEditor cfEditor = (CFMLEditor)part.getSite().getPage().getActiveEditor();
			IEditorInput editorInput = cfEditor.getEditorInput();
			IDocumentProvider docProvider = cfEditor.getDocumentProvider(); 
			IDocument doc = docProvider.getDocument(editorInput);
			if(!(doc instanceof ICFDocument))
				return;
				
			ICFDocument cfDoc = (ICFDocument)doc;
			
			CFDocument parseResult = cfDoc.getParser().parseDoc();
			if(parseResult == null)
				return;
			
			CFNodeList methods = parseResult.getDocumentRoot().selectNodes("//cffunction");
			Iterator methodIter = methods.iterator();
			while(methodIter.hasNext())
			{
				CfmlTagItem item = (CfmlTagItem)methodIter.next();
				
				String methodName = item.getAttributeValue("name");
				if(methodName.toLowerCase().equals(this.methodName.toLowerCase()))
				{
					/*
					 * 	ite.setHighlightRange(selectedMethod.getDocumentOffset(),0,true);
				ite.setFocus();
					 */
					cfEditor.setHighlightRange(item.getStartPosition(), 0, true);
					cfEditor.setFocus();
				}
			}
		}
		catch (PartInitException e) 
		{
		  
			e.printStackTrace(System.err);
		}
		catch(Throwable throwEx)
		{
			throwEx.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection){
		if (editor != null && editor.getSite() != null && editor.getSite().getPage() != null) {
			setActiveEditor(null,  editor.getSite().getPage().getActiveEditor());
		}
	}

	/**
     * @return Returns the success.
     */
    public boolean isSuccess() {
        return this.success;
    }

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if(activeEditor instanceof ITextEditor){
			editor = (ITextEditor)activeEditor;
		}
		
	}
}
