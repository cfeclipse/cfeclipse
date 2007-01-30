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
package org.cfeclipse.cfml.views.snips;

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

import java.io.File;
import java.io.FileFilter;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

class SnippetFileFilter implements FileFilter {
	public boolean accept(File f)
	{
		String sflower = f.getAbsoluteFile().toString().toLowerCase(); 
		if(sflower.endsWith(SnipTreeView.DW_SNIP_EXT)
			|| sflower.endsWith(SnipTreeView.CFE_SNIP_EXT)
			||  (f.isDirectory() && !f.getName().startsWith(".")))
		{
			return true;
		}
		return false;
	}
	
	public String getDescription()
	{
		return "A snippet file filter";
	}
}

public class SnipTreeViewContentProvider implements ITreeContentProvider { //, IDeltaListener {
	/** scratch pad */
	protected static Object[] EMPTY_ARRAY = new Object[0];
	protected static FileFilter snippetfilter = new SnippetFileFilter();
	protected TreeViewer viewer;
	protected File rootdir;
	
	public SnipTreeViewContentProvider(File root)
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
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		//System.err.println("Doing input changed " + oldInput + " " + newInput);
		this.viewer = (TreeViewer)viewer;
	}
	
	/**
	 * Gets all the children from a parent
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object parentElement) 
	{
		if(parentElement instanceof File)
		{
			if(((File)parentElement).isDirectory())
				return ((File)parentElement).listFiles(snippetfilter);
		}
		
		return EMPTY_ARRAY;
	}
	
	/* protected Object[] concat(Object[] object, Object[] more, Object[] more2) {
		Object[] both = new Object[object.length + more.length + more2.length];
		System.arraycopy(object, 0, both, 0, object.length);
		System.arraycopy(more, 0, both, object.length, more.length);
		System.arraycopy(more2, 0, both, object.length + more.length, more2.length);		
		return both;
	} */

	/**
	 * Get the parent of element
	 * @see ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object element) 
	{		
		if(element instanceof File && !element.equals(rootdir))
		{
			return ((File)element).getParentFile();
		}
		
		return null;
	}

	/**
	 * does element have any children?
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
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

