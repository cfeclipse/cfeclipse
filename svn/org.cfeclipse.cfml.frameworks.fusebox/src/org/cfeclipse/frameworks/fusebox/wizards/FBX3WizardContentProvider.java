/*
 * Created on 24-Jan-2005
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
package org.cfeclipse.frameworks.fusebox.wizards;

import org.cfeclipse.frameworks.fusebox.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox.objects.FBXCircuit;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuseAction;
import org.cfeclipse.frameworks.fusebox.objects.FBXRoot;
import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;



/**
 * @author Mark Drew
 * 24-Jan-2005
 * fusebox3cfe2
 * Description: This is a blank (ish) tree to create an application from
 */
public class FBX3WizardContentProvider implements IStructuredContentProvider,ITreeContentProvider{

	
	private FBXRoot invisibleRoot;
	private TreeViewer viewer;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		
		//if (invisibleRoot==null) initialize();
		return getChildren(inputElement);
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof FBXRoot) {
			return ((FBXRoot)parentElement).getChildren();
		}
		else if (parentElement instanceof FBXApplication) {
			return ((FBXApplication)parentElement).getChildren();
		}
		else if (parentElement instanceof FBXCircuit) {
			return ((FBXCircuit)parentElement).getChildren();
		} else if (parentElement instanceof FBXFuseAction){
			return((FBXFuseAction)parentElement).getChildren();
		}
		return new Object[0];
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	private void initialize(){
		invisibleRoot = new FBXRoot("");
		FBXApplication app = null;
		app.setName("root");
		
		invisibleRoot.addChild(app);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		/*this.viewer = (TreeViewer)viewer;
		if(oldInput != null) {
			removeListenerFrom((MovingBox)oldInput);
		}
		if(newInput != null) {
			addListenerTo((MovingBox)newInput);
		}*/

		Utils.println("input changed");
	}
}
