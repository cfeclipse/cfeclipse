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
import com.rohanclan.cfml.parser.docitems.DocItem;
import com.rohanclan.cfml.parser.docitems.TagItem;

import java.util.Iterator;
import java.util.Collections;
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
	boolean sortItems = false;
	
	public CFCMethodsContentProvider (ICFDocument icfd, boolean sortItems) {
		// By default we create an empty tag
		document = icfd;
		this.sortItems = sortItems;
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
			CFDocument doc = document.getCFDocument();
			if(doc == null) {	// OBT: Added to handle when the parse fatally fails.
				return EMPTY_ARRAY;
			}
			DocItem rootItem = doc.getDocumentRoot();

			//nodes = rootItem.selectNodes("//function[#startpos>=0 and #endpos < 200]");
			nodes = rootItem.selectNodes("//function");
			
			if (sortItems) {
			    CFCMethodsComparator comparator = new CFCMethodsComparator();
				Collections.sort(nodes,comparator);
			}
			
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
			System.err.println("CFCMethodsContentProvider has no elements");
			e.printStackTrace();
			return EMPTY_ARRAY;
		}
	}
}