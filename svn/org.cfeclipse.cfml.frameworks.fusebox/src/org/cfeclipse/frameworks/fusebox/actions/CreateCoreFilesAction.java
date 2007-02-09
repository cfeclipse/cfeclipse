/*
 * Created on 10-Jan-2005
 *
 * The MIT License
 * Copyright (c) 2004 Mark Drew
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
 **/
package org.cfeclipse.frameworks.fusebox.actions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.frameworks.fusebox.FuseboxPlugin;
import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Administrator
 * 10-Jan-2005
 * fusebox3cfe2
 * Description:
 */
public class CreateCoreFilesAction implements IObjectActionDelegate{

	private IFolder folder;
	private IProject project;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		

	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		//Here we will create a new file
		ArrayList circuitFiles = new ArrayList();
		NewFileAction newFileAction = new NewFileAction();
		String pathSuffix = "templates/fusebox3/";
		
		circuitFiles.add("fbx_Circuits.cfm");
		circuitFiles.add("fbx_fusebox30_CF40.cfm");
		circuitFiles.add("fbx_fusebox30_CF45.cfm");
		circuitFiles.add("fbx_fusebox30_CF45_nix.cfm");
		circuitFiles.add("fbx_fusebox30_CF50.cfm");
		circuitFiles.add("fbx_fusebox30_CF50_nix.cfm");
		circuitFiles.add("fbx_Layouts.cfm");
		circuitFiles.add("fbx_savecontent.cfm");
		circuitFiles.add("fbx_Settings.cfm");
		circuitFiles.add("fbx_Switch.cfm");
		circuitFiles.add("index.cfm");
		
		
		
		URL templates = null;
		
		try {
			templates = new URL(FuseboxPlugin.getDefault().getBundle().getEntry("/"),pathSuffix);
				
			Iterator iter = circuitFiles.iterator();
			while(iter.hasNext()){
				String filename = (String)iter.next();
				URL file = new URL(templates + "/" + filename);
				URLConnection urlcon = file.openConnection();
				String contents = Utils.getStringFromInputStream(urlcon.getInputStream());
				
				String thepath ="";
				if(this.folder != null){
					thepath = this.folder.getFullPath().toString();
				} else {
					thepath = "/" + this.project.getName();
					
				}
				Utils.println(thepath);
				newFileAction.setFilename( thepath + "/" + filename);
				newFileAction.setContents(contents);
				newFileAction.openAfterOpen = false;
				newFileAction.run();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		Iterator selectionIter = ((StructuredSelection)selection).iterator();

		  while(selectionIter.hasNext())
		    {
		        Object obj = selectionIter.next();
		        if(obj instanceof IFolder)
		        {
		           	IFolder fold = (IFolder)obj;
		        	this.folder = fold;
		        }
		        else if (obj instanceof IProject){
		        	this.project = (IProject)obj;
		        }
		    }

	}
}

