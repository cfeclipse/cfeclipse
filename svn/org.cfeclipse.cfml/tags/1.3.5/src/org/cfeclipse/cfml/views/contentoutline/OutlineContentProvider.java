/*
 * Created on Feb 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.views.contentoutline;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 * 
 * This class was influenced by the aricle:
 * How to use the JFace Tree Viewer
 * By Chris Grindstaff, Applied Reasoning (chrisg at appliedReasoning.com)
 * May 5, 2002
 */

//import org.eclipse.core.internal.runtime.Assert;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class OutlineContentProvider implements ITreeContentProvider { //, IDeltaListener {
	/** scratch pad */
	private static Object[] EMPTY_ARRAY = new Object[0];
	protected TreeViewer viewer;
	protected DocItem rootdir;
	
	public OutlineContentProvider(DocItem root)
	{
		rootdir = root;
	}
	
	/**
	 * Notifies this content provider that the given viewer's input
	 * has been switched to a different element.
	 * <p>
	 * A typical use for this method is registering the content provider as a listener
	 * to changes on the new input (using model-specific means), and deregistering the viewer 
	 * from the old input. In response to these change notifications, the content provider
	 * propagates the changes to the viewer.
	 * </p>
	 * 
	 * @see IContentProvider#inputChanged(Viewer, Object, Object)
	 *
	 * @param viewer the viewer
	 * @param oldInput the old input element, or <code>null</code> if the viewer
	 *   did not previously have an input
	 * @param newInput the new input element, or <code>null</code> if the viewer
	 *   does not have an input
	 */
	public void inputChanged(Viewer viewer2, Object oldInput, Object newInput) 
	{	
		if(newInput instanceof DocItem)
		{
			if(viewer != null)
			{
				((TreeViewer)viewer2).setExpandedElements(
					((TreeViewer)viewer).getExpandedElements()
				);
			}
			viewer = (TreeViewer)viewer2;
			rootdir = (DocItem)newInput;
			//viewer.expandAll();
		}
	}
	
	/**
	 * Gets all the children from a parent
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object parentElement) 
	{
		if(parentElement instanceof DocItem)
		{
			return ((DocItem)parentElement).getChildNodes().toArray();
		}
		System.err.println("not a doc item");
		return EMPTY_ARRAY;
	}
	
	/**
	 * Get the parent of element
	 * @see ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object element) 
	{
		if(element instanceof DocItem && ((DocItem)element).getParent() != rootdir)
		{
		    DocItem item = (DocItem)element;
		    if(element == null)
		    {
		        return null;
		    }
		    else if(item.getParent() == null)
		    {
		        return null;
		    }
		    //Assert.isNotNull(item.getParent().getName());
		    if(item.getParent().getName() == null)
		    		throw new IllegalArgumentException("item parent getname is null");
		    
			return item.getParent();
		}
		
		return null;
	}

	/**
	 * does element have any children?
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) 
	{
		return ((DocItem)element).hasChildren();
	}

	/**
	 * get inputelements children
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object inputElement)
	{
		return getChildren(inputElement);
	}
	
	/**
	 * @see IContentProvider#dispose()
	 */
	public void dispose(){;}
}

