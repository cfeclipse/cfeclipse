/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.packageview;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.rohanclan.cfml.CFMLPlugin;


class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private TreeParent invisibleRoot;

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
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */
		private TreeObject addFile(IFile currFile) {
			//TreeObject file = new TreeObject(currFile.getName());
			FileNode file = new FileNode(currFile);
			
			return file;
		}
		
		private TreeObject addFolder(IFolder currFolder) {
			//TreeParent folder = new TreeParent(currFolder.getName());
			FolderNode folder = new FolderNode(currFolder);
			try {
				IResource children[] = currFolder.members();
				for(int i = 0; i < children.length; i++) {
					folder.addChild(addResource(children[i]));
				}
			} catch(CoreException ex) {
				ex.printStackTrace();
			}
			
			return folder;
		}
		
		private TreeObject addResource(IResource currRes) {
			if(currRes instanceof IFile)
				return addFile((IFile)currRes);
			else if(currRes instanceof IFolder)
				return addFolder((IFolder)currRes);
			
			return new TreeObject(currRes.getName());
		}
		
		private TreeObject addProject(IProject currProject) {
			//TreeParent projNode = new TreeParent(currProject.getName());
			ProjectNode projNode = new ProjectNode(currProject);
			
			if(!currProject.isOpen())
				return projNode;
			
			try {
				IResource children[] = currProject.members();
				for(int i = 0; i < children.length; i++) {
					projNode.addChild(addResource(children[i]));
				}
			
			} catch(CoreException ex) {
				ex.printStackTrace();
			}
			return projNode;
		}
		
		private void initialize() {
			invisibleRoot = new TreeParent("");
			
			IProject[] projects =  CFMLPlugin.getWorkspace().getRoot().getProjects();
			for(int i = 0; i < projects.length; i++) {
				invisibleRoot.addChild(addProject(projects[i]));
			}
		}
	}