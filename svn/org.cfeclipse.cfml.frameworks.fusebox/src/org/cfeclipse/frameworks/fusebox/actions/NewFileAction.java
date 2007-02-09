/*
 * Created on 	: 27-Aug-2004
 * Created by 	: Administrator
 * File		  	: NewFileAction.java
 * Description	:
 * 
 */
package org.cfeclipse.frameworks.fusebox.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.core.runtime.IPath;


import org.cfeclipse.cfml.editors.CFMLEditor;

/**
 * @author Administrator
 *
 * This action should create a new file and you can name it and put whatever you want in it.
 * this means that you can use templates in future to fill up the file.
 * an example idea I had for this was to create a file from a <cfmodule call
 * <cfmodule template="bob.cfm" var="something" var2="something" /> would create a file called bob.cfm with 
 * <cfparam name="attributes.var" default="" type="string">
 * <cfparam name="attributes.var2" default="" type="string">
 * 
 */
public class NewFileAction  implements IEditorActionDelegate{
    protected ITextEditor editor = null;
    protected IFile file;
	protected String filename = "untitled.cfm";
	protected String contents = "";
    boolean isCreated = false;
    public boolean openAfterOpen = true; 

    
    
    
    /**
     * The default constructor
     */
    public NewFileAction() {
        super();
    }
 
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        if(targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor)
		{
			editor = (ITextEditor)targetEditor;
		}

    }
 
    public void selectionChanged(IAction action, ISelection selection) {;}
    
    public boolean isCreated(){
        return this.isCreated;
    }
    /**
     * @param contents The contents to set.
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
    /**
     * @param editor The editor to set.
     */
    public void setEditor(ITextEditor editor) {
        this.editor = editor;
    }
    
  
    /**
     * @param filename The filename to set.
     */
    public void setFilename(String filename) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		this.file = root.getFile(new Path(filename));
        this.filename = filename;
       }
    
    public void setFile(IFile file){
         this.file = file;
    }
   
    public void run()
	{
		run(null);
	}
    /*I would like this method to create a file and that we can change the name etc.*/
    public void run(IAction action) {
        
        createFile(this.filename);
       
	}
        
        
    private void createFile(String filename){
        //I try to create a file
// System.out.println("I shall create a file called " + this.filename);
       // IPath path = new Path("untitled.cfm");
// System.out.println("Path is valid?" + path.isValidPath("untitled.cfm"));
       
		try {
			InputStream stream = openContentStream();
			file.create(stream, true, null);
			stream.close();
			this.isCreated = true;
		} catch (IOException e) {
// System.out.println("There has been an error in " +  e.getMessage());
		    this.isCreated = false;
		} catch (CoreException co){
// System.out.println("There has been an error in " +  co.getMessage());
		    this.isCreated = false;
		}
		
		if(this.isCreated && this.openAfterOpen){
			//once we have created it.. lets try and open it
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try
			{
				IDE.openEditor(page, file, true);
			}
			catch (PartInitException e) 
			{
			  
				e.printStackTrace(System.err);
			}
		}
		
		
		
    }
    
   
	private InputStream openContentStream()
	{
		//This is the initial file contents for *.cfm file that should be 
		//word-sorted in the Preview page of the multi-page editor
		String contents = this.contents;
		return new ByteArrayInputStream(contents.getBytes());
	}
	
	

   
}
