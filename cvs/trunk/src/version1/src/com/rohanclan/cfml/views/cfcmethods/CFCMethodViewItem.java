/*
 * Created on May 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.cfcmethods;

import com.rohanclan.cfml.parser.*;
import java.util.Iterator;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFCMethodViewItem  {

	TagItem functionTag;
	
	public CFCMethodViewItem(TagItem item) {
		functionTag = item;
	}

	
	public String toString() {
		try {
		String method = "  ";
		method += functionTag.getAttribute("name");
		method += " (";
		CFNodeList args = functionTag.selectNodes("//argument");
		Iterator j = args.iterator();
		while(j.hasNext()) {
			try {
				TagItem thisArg = (TagItem)j.next();
				method += thisArg.getAttribute("type") + " " + thisArg.getAttribute("name");
				if (j.hasNext()) {
					method += ", ";
				}
			}
			catch (Exception e) {
				//System.err.println(e.getMessage());
			}
		}
		method += " )";
		return method;
		}
		catch (Exception e) {
			return "Couldn't get valid information for method";
		}
	}
	
	public String getReturnType() {
		String returnType;

		try {
			return functionTag.getAttribute("returntype");
		}
		catch (Exception e) {
			return "void";
		}
	}
	
	public String getAccess() {
		try {
			return functionTag.getAttribute("access");
		}
		catch (Exception e) {
			return "public";
		}
	}
	
	public int getDocumentOffset() {
		try {
			return functionTag.getStartPosition();
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	public int getSize(ITextEditor editor) {
		try {
			String documentContents = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
			int endPosition = functionTag.getStartPosition();
			try {
				if (documentContents.toLowerCase().indexOf("</cffunction",functionTag.getStartPosition()) > 0) {
					endPosition = documentContents.toLowerCase().indexOf("</cffunction",functionTag.getStartPosition()) + 13;
				}
			}
			catch (Exception e){
				//e.printStackTrace(System.err);
			}
			return endPosition - functionTag.getStartPosition();
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	public String getCFML() {
		try {
			return functionTag.getItemData();
		}
		catch (Exception e) {
			return "";
		}
	}
	
}
