/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
//import com.rohanclan.cfml.ftp.FtpConnection;



class ComboContentProvider implements IStructuredContentProvider {
    
    
    public Object[] getElements(Object inputElement) {
        return new Object[] { new LocalFileSystem()};
    }
    public void dispose() {
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput != null) {
            //System.out.println("Explorer combo-box input changed");
        }
    }
}