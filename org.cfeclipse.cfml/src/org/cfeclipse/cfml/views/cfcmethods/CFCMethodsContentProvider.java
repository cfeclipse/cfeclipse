/*
 * Created on May 11, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.views.cfcmethods;

import java.util.Collections;
import java.util.Iterator;

import org.cfeclipse.cfml.editors.CFDocumentProvider;
import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.FunctionInfo;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
/**
 * @author Stephen Milligan
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
			if (nodes.size() == 0) {
				nodes = rootItem.selectNodes("//ASTFunctionDeclaration");
			}
			return getMethods(nodes);
		}
		catch (Exception e){
			System.err.println("CFCMethodsContentProvider has no elements");
			e.printStackTrace();
			return EMPTY_ARRAY;
		}
	}

	private MethodViewItem[] getMethods(CFNodeList nodes) {
		if (sortItems) {
			CFCMethodsComparator comparator = new CFCMethodsComparator();
			Collections.sort(nodes, comparator);
		}
		Iterator i = nodes.iterator();
		MethodViewItem[] methods = new MethodViewItem[nodes.size()];
		int index = 0;
		while (i.hasNext()) {
			try {
				Object itemThing = i.next();
				DocItem thisTag = (DocItem) itemThing;
				MethodViewItem item;
				if (itemThing instanceof FunctionInfo) {
					item = new CFCMethodViewScriptItem((FunctionInfo) itemThing);

				} else {
					item = new CFCMethodViewItem((TagItem) thisTag);
				}

				boolean addItem = true;

				if (item.getAccess().toLowerCase().equals("remote") && !this.showRemote) {
					addItem = false;
				}

				if (item.getAccess().toLowerCase().equals("public") && !this.showPublic) {
					addItem = false;
				}

				if (item.getAccess().toLowerCase().equals("package") && !this.showPackage) {
					addItem = false;
				}

				if (item.getAccess().toLowerCase().equals("private") && !this.showPrivate) {
					addItem = false;
				}

				if (addItem) {
					methods[index] = item;
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MethodViewItem[] finalMethods = new MethodViewItem[index];

		for (int x = 0; x < finalMethods.length; x++) {
			finalMethods[x] = methods[x];
		}
		return finalMethods;
	}
}