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
	private boolean sortItems = false;
	private boolean showRemote = true;
	private boolean showPublic = true;
	private boolean showPackage = true;
	private boolean showPrivate = true;
	
	public CFCMethodsContentProvider (ICFDocument icfd, boolean sortItems, boolean showRemote, boolean showPublic, boolean showPackage, boolean showPrivate) {
		// By default we create an empty tag
		document = icfd;
		
		this.sortItems = sortItems;
		this.showRemote = showRemote;
		this.showPublic = showPublic;
		this.showPackage = showPackage;
		this.showPrivate = showPrivate;
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
			nodes = rootItem.selectNodes("//cffunction");
			
			
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
					
					boolean addItem = true;
					
					if (item.getAccess().toLowerCase().equals("remote")
					        && !this.showRemote) {
					    addItem = false;
					}

					if (item.getAccess().toLowerCase().equals("public")
					        && !this.showPublic) {
					    addItem = false;
					}

					if (item.getAccess().toLowerCase().equals("package")
					        && !this.showPackage) {
					    addItem = false;
					}

					if (item.getAccess().toLowerCase().equals("private")
					        && !this.showPrivate) {
					    addItem = false;
					}
					
					if (addItem) {
						methods[index] = item;
						index++;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			CFCMethodViewItem[] finalMethods = new CFCMethodViewItem[index];
			
			for (int x=0;x<finalMethods.length;x++) {
			 finalMethods[x] = methods[x];   
			}
		return finalMethods;
		}
		catch (Exception e){
			System.err.println("CFCMethodsContentProvider has no elements");
			e.printStackTrace();
			return EMPTY_ARRAY;
		}
	}
}