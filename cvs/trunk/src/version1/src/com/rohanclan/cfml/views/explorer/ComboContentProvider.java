/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.util.ArrayList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import com.rohanclan.cfml.ftp.FtpConnectionProperties;



class ComboContentProvider implements IStructuredContentProvider {
    
    
    public Object[] getElements(Object inputElement) {
    	
    	String[] connections = FtpConnectionProperties.getConnectionIds(); 
    
    	ArrayList items = new ArrayList();
    	
    	items.add(new LocalFileSystem());
    	
		for (int i=0;i<connections.length;i++) {						
			FtpConnectionProperties connectionProperties = new FtpConnectionProperties(connections[i]);
			items.add(connectionProperties);
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