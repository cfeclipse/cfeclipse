/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import java.util.ArrayList;

import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.ftp.FTPConnection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;



class ComboContentProvider implements IStructuredContentProvider {
    
    
    public Object[] getElements(Object inputElement) {
    	
    	String[] connections = FTPConnectionProperties.getConnectionIds(); 
    
    	ArrayList items = new ArrayList();
    	
    	items.add(new LocalFileSystem());
    	
		for (int i=0;i<connections.length;i++) {
			FTPConnectionProperties connectionProperties = new FTPConnectionProperties(connections[i]);

		    FTPConnection connection = new FTPConnection();
			connection.setConnectionProperties(connectionProperties);
			items.add(connection);
			
		}
	
        return items.toArray();
    }
    public void dispose() {
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput != null) {
            //System.out.println("Explorer combo-box input changed");
            //System.out.println(newInput.toString());
        }
    }
}