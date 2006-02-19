/*
 * Created on Aug 2, 2004
 *
 * TODO: Make the parsing make a flat tree ( as it is in the Java navigation)
 */
package com.rohanclan.cfml.views.packageview;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.views.packageview.objects.IComponentViewObject;
import com.rohanclan.cfml.views.packageview.objects.ProjectNode;
import com.rohanclan.cfml.views.packageview.objects.RootNode;



class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private RootNode invisibleRoot;
		private String project;	
		
		public ViewContentProvider(String project){
			this.project = project;
		}
		
		/*
		 *  (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
//			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			//}
			//return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof IComponentViewObject) {
				return ((IComponentViewObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			
			return ((IComponentViewObject)parent).getChildren().toArray();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		public boolean hasChildren(Object parent) {
			if(parent != null){
			return ((IComponentViewObject)parent).hasChildren();
			}
			return false;
		}
		
		
		/**
		 * 1. This is where we add the root.
		 */
		private void initialize() {
			invisibleRoot = new RootNode();
			
			IProject project = CFMLPlugin.getWorkspace().getRoot().getProject(this.project);
			
			//Create the project
			ProjectNode projectnode  = new ProjectNode(project);
			
			
			invisibleRoot.addChild(projectnode);
			
			/*IProject[] projects =  CFMLPlugin.getWorkspace().getRoot().getProjects();
			for(int i = 0; i < projects.length; i++) {
				invisibleRoot.addChild(addProject(projects[i]));
			}
			*/
		}

	
		
		
	}