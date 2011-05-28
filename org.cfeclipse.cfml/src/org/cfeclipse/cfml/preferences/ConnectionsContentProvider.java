/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;



class ConnectionsContentProvider implements IStructuredContentProvider {
	
    protected static String NEW_CONNECTION = "New Location...";
	
    
    public Object[] getElements(Object element) {
        String[] connections = FTPConnectionProperties.getConnectionIds();
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