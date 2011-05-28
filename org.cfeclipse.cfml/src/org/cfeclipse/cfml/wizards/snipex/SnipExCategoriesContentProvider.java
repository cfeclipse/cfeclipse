/**
 * 
 */
package org.cfeclipse.cfml.wizards.snipex;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.snipex.Library;
import org.cfeclipse.snipex.SnipEx;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author markdrew
 *
 */
public class SnipExCategoriesContentProvider implements ITreeContentProvider {

	private String server;
	private TreeViewer viewer;
	private SnipEx snipexServer;
	
	//The logger for this class
	private Log logger = LogFactory.getLog(SnipExCategoriesContentProvider.class);
	/**
	 * @param server
	 */
	public SnipExCategoriesContentProvider(String server, boolean fullReload) {
		this.server = server;
		
		try {
			snipexServer  = new SnipEx(new URL(server), fullReload);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public SnipExCategoriesContentProvider() {
		// TODO Auto-generated constructor stub
	}

	
	public Object[] getChildren(Object parentElement) 
	{
		if(parentElement == "root" && snipexServer != null){
			return new Object[]{snipexServer};
			
		}
		else if(parentElement instanceof Library || parentElement instanceof SnipEx) {
			Library lib = (Library)parentElement;
			return lib.getLibraries().toArray();
		}
		
		
		
		return new Object[0];
	}
	
	public Object[] getElements(Object inputElement)
	{
		return getChildren(inputElement);
	}
	
	public Object getParent(Object element) 
	{		
		return null;
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
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		//	this.viewer = (TreeViewer)viewer;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		if(element instanceof SnipEx || element instanceof Library) {
			return true;
		} 
		return getChildren(element).length > 0;
	}

}
