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

package org.cfeclipse.cfml.frameworks.actions;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.mappings.MappedPathException;
import org.cfeclipse.cfml.mappings.MappingManager;
import org.eclipse.core.internal.resources.Resource;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.jdom.Element;


@SuppressWarnings("restriction")
public class GoToFile extends BaseAction implements IBaseAction{
	
	private Log logger = LogFactory.getLog(GoToFile.class);
	private IFile fileToOpen;
	

	public void run() {
		Element elem = super.getNode().getElement();
		
		String actionPath = super.getParsedAction();
		//If it has some text, we use that as a path
		if(actionPath != null && actionPath.length() > 0){
			//Use the Mapping Manager to get the right start path

			try {
				IResource resource2 = MappingManager.resolveAbsoluteMapping(super.getProject(), actionPath);
				
				if(resource2.exists() && resource2 instanceof IFile){
					openFile((IFile)resource2,0);
				}
			} catch (MappedPathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		}
		else {
			openFile(super.getNode().getFrameworkFile().getFile(),super.getNode().getLinenumber());
		}
		
		
		   
		 
		
		 //from http://wiki.eclipse.org/index.php/FAQ_How_do_I_open_an_editor_on_a_file_in_the_workspace%3F
		 //to open an editor at a marker/line number
		 //IDE.openEditor(page, marker);
		 
		 
			
		//super.showMessage("Being called from GotoFile" + super.getNode().getFrameworkFile().getFile() + " " + super.getNode().getLinenumber());
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private void openFile(IFile file, int lineNumber) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		
		  
		try {
			HashMap<String, Comparable> map = new HashMap<String, Comparable>();
			   map.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
			   map.put(IDE.EDITOR_ID_ATTR, IDE.getDefaultEditor(file).getId());  
			 IMarker marker = file.createMarker(IMarker.TEXT);
			 marker.setAttributes(map);
			 IDE.openEditor(page,marker); //3.0 API
			 marker.delete();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private IResource findResourceFromURI(Resource resource, String baseURI) {
		
		logger.debug("baseURI=" + baseURI);
		
		return null;
	}

}
