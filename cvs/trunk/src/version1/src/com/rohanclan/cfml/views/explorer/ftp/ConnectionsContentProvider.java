/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer.ftp;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.TableItem;

import com.rohanclan.cfml.ftp.FtpConnectionProperties;


class ConnectionsContentProvider implements IStructuredContentProvider {
	
    protected static String NEW_CONNECTION = "New Connection...";
	
    
    public Object[] getElements(Object element) {
        String[] connections = FtpConnectionProperties.getConnectionIds();
        String[] result = new String[connections.length+1];
        for (int i=0;i<connections.length;i++) {
            result[i] = connections[i];
        }
        result[result.length-1] = NEW_CONNECTION;
        
        return result;
    }

    public void inputChanged(Object oldInput, Object newInput) {


		
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        
    }

    public void dispose() {
        
    }
}