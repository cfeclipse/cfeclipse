/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.frameworks.fusebox4.views;

import org.cfeclipse.frameworks.fusebox4.objects.FBXRoot;
import org.cfeclipse.frameworks.fusebox4.objects.IFBXObject;
import org.cfeclipse.frameworks.fusebox4.parsers.FBXParser;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
    
    private FBXRoot invisibleRoot;
	private String projectname;
	
		public ViewContentProvider(String project){
			this.projectname = project;
		}
    
    
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
			if (child instanceof IFBXObject) {
				return ((IFBXObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof IFBXObject) {
				return ((IFBXObject)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof IFBXObject)
				return ((IFBXObject)parent).hasChildren();
			return false;
		}

		
		private void initialize() {
			invisibleRoot = new FBXRoot("");
			FBXParser parser = new FBXParser(this.projectname, invisibleRoot);
			invisibleRoot.addChild(parser.parse());
		}
	}