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
package org.cfeclipse.cfml.frameworks.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

public class EditorEventListener implements IPartListener {

	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		//Try creating a default editor EditorPart
		if(part instanceof EditorPart){
			EditorPart epart = (EditorPart)part;
			IEditorInput editorInput = epart.getEditorInput();
			   if (editorInput instanceof IFileEditorInput) {
		            IFile file = ((IFileEditorInput) editorInput).getFile();
		            IProject project = file.getProject();
		            System.out.println("pEditor's site" + project);
		            //tell this to the ModelGlue View
		            
			   }
			
		}
		
	}

	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		partBroughtToTop(part);
		
	}

	
}
