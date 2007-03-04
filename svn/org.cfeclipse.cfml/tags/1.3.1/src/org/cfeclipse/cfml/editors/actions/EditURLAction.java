package org.cfeclipse.cfml.editors.actions;

import java.util.Iterator;

import org.cfeclipse.cfml.dialogs.EditURLDialog;
import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class EditURLAction implements IObjectActionDelegate {

	private IWorkbenchPart targetpart;
	private IAction action;
	private IResource resource;

	public EditURLAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetpart = targetPart;
		this.action = action;
		
	}

	public void run(IAction action) {
			EditURLDialog eud = new EditURLDialog(targetpart.getSite().getShell());
			String url = "";
				try {
					url = resource.getPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL));
					if(url != null && url.trim().length() > 0){
						eud.setUrl(url);
						
					} 
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			if(eud.open() == IDialogConstants.OK_ID){
				//lets save this
					try {
						resource.setPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL), eud.getUrl());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		 Iterator selectionIter = ((StructuredSelection)selection).iterator();
		 while(selectionIter.hasNext()){
			 this.resource = (IResource)selectionIter.next();
		 }
	}
	
	private String getURL(IResource resource, String pathSoFar){
		try {
			String persistentProperty = resource.getPersistentProperty(new QualifiedName("", "cfe_resource_url"));
			
			if(persistentProperty == null || persistentProperty.trim().length() == 0){
			}
			else{
				return persistentProperty;
			}
		
		
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
