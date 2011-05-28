/*
 * Created on 06-Jan-2005
 *
  */
package org.cfeclipse.frameworks.fusebox.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.frameworks.fusebox.properties.FuseboxPreferenceConstants;
import org.cfeclipse.frameworks.fusebox.views.FBX3View;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * @author Mark Drew
 */
public class SetFBXRootAction  implements IObjectActionDelegate{

	private ArrayList selections;
	private IFolder folderRoot;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	

	}
	
	public SetFBXRootAction(){
		super();
		this.selections = new ArrayList();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
			
		
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		   
			Iterator prjIter = this.selections.iterator();
	        while(prjIter.hasNext())
	        {
	        	
	        	IProject currPrj = (IProject)prjIter.next();
	           
	            QualifiedName qname = new QualifiedName("", FuseboxPreferenceConstants.P_FUSEBOX_PATH);
	    		
	    		try {
	    			if(folderRoot != null){
	    			currPrj.setPersistentProperty(qname, "/" + folderRoot.getProjectRelativePath().toString());
	    			} else {
	    				//We assume we are at the project level
	    				currPrj.setPersistentProperty(qname, "/");
	    			}
	    			
	    			 try {
	    				//Now we should refresh the view too
	    				   FBX3View fbxview = (FBX3View)page.showView(FBX3View.ID_FBXVIEW);
	    				   //need to set the project too
	    				   fbxview.setProject(currPrj.getName());
	    				   fbxview.refresh();
	    				   fbxview.setFocus();
	    		        } catch (PartInitException e) {
	    			
	    		        	e.printStackTrace();
	    		        }
	    			
	    		} catch (CoreException e) {
	    			e.printStackTrace();
	    		}
	    		
	        }
		 

		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
		Iterator selectionIter = ((StructuredSelection)selection).iterator();

		 this.selections.clear();
		   
		  while(selectionIter.hasNext())
		    {
		        Object obj = selectionIter.next();
		        if(obj instanceof IFolder)
		        {
		        	IFolder fold = (IFolder)obj;
		        	this.folderRoot = fold;
		            this.selections.add(fold.getProject());

		        } 
		        else if (obj instanceof IProject) {
		        	this.selections.add(obj);
		        }
		    }
		 
	}
}
