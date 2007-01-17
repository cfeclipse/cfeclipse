/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.eclipse.jface.viewers.IElementComparer;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FileComparer implements IElementComparer {

    /**
     * 
     */
    public FileComparer() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IElementComparer#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(Object a, Object b) {
        //System.out.println("Comparing " + a.getClass().getName() + " to " + b.getClass().getName());
        if (a.getClass().getName().equals(b.getClass().getName())) {
            return a.equals(b);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IElementComparer#hashCode(java.lang.Object)
     */
    public int hashCode(Object element) {
        // TODO Auto-generated method stub
        return element.hashCode();
    }

}
