/*
 * Created on May 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.cfcmethods;

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.editors.CFDocumentProvider;
import com.rohanclan.cfml.editors.ICFDocument;
import org.eclipse.jface.viewers.*;
import com.rohanclan.cfml.parser.*;
import java.util.Iterator;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFCMethodsContentProvider implements IStructuredContentProvider {
	private static Object[] EMPTY_ARRAY = new Object[0];
	ICFDocument document;
	CFMLEditor editor; 
	CFDocumentProvider provider;
	CFNodeList nodes;
	TableViewer viewer;
	
	public CFCMethodsContentProvider (ICFDocument icfd) {
		// By default we create an empty tag
		document = icfd;
	}
	
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		if (newInput instanceof ICFDocument) {
			document = (ICFDocument)newInput; 
		}
		
	}
	
	
	public void dispose() {
	}
	
	
	public Object[] getElements(Object parent) {
		
		try {
			DocItem rootItem = document.getCFDocument().getDocumentRoot();

			nodes = rootItem.selectNodes("//function");

			Iterator i = nodes.iterator();
			CFCMethodViewItem[] methods = new CFCMethodViewItem[nodes.size()];
			int index = 0;
			while(i.hasNext())
			{
				try {
					TagItem thisTag = (TagItem)i.next();
					CFCMethodViewItem item = new CFCMethodViewItem(thisTag);
					methods[index] = item;
					index++;
				}
				catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		return methods;
		}
		catch (Exception e){
			//System.err.println("CFCMethodsContentProvider has no elements");
			return EMPTY_ARRAY;
		}
	}
}