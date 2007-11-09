/*
 * Created on May 12, 2004
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


import java.util.Iterator;

import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Stephen Milligan
 */
public class CFCMethodViewItem  {

	TagItem functionTag;
	
	public CFCMethodViewItem(TagItem item) {
		functionTag = item;
	}

	
	public String toString() {
		try {
			StringBuffer method = new StringBuffer(" ");
			method.append(functionTag.getAttributeValue("name"));
			method.append(" ( ");
			CFNodeList args = functionTag.selectNodes("//cfargument");
			Iterator j = args.iterator();
			while(j.hasNext()) {
				try {
					TagItem thisArg = (TagItem)j.next();
					method.append(thisArg.getAttributeValue("type") + " " + thisArg.getAttributeValue("name"));
					if (j.hasNext()) {
						method.append(", ");
					}
				}
				catch (Exception e) {
					//System.err.println(e.getMessage());
				}
			}
			method.append(" )");
			
			String returntype = functionTag.getAttributeValue("returntype");
			if(returntype != null)
			{
				method.append(" - " + returntype); 
			}
			
			String hint = functionTag.getAttributeValue("hint");
			if (hint != null) 
			{
			    method.append("  '"+hint+"'");
			}
			
			return method.toString();
		}
		catch (Exception e) {
			return "Couldn't get valid information for method";
		}
	}


	
	public String getInsertString() {
		try {
			StringBuffer method = new StringBuffer("");
			
			method.append(functionTag.getAttributeValue("name"));
			method.append("(");
			CFNodeList args = functionTag.selectNodes("//argument");
			Iterator j = args.iterator();
			while(j.hasNext()) {
				try {
					TagItem thisArg = (TagItem)j.next();
					method.append(thisArg.getAttributeValue("type") + " " + thisArg.getAttributeValue("name"));
					if (j.hasNext()) {
						method.append(", ");
					}
				}
				catch (Exception e) {
					//System.err.println(e.getMessage());
				}
			}
			method.append(")");
			
			/*
			String returntype = functionTag.getAttribute("returntype");
			if(returntype != null)
			{
				method.append(" - " + returntype); 
			}
			
			String hint = functionTag.getAttribute("hint");
			if (hint != null) 
			{
			    method.append("  '"+hint+"'");
			}
			*/
			return method.toString();
		}
		catch (Exception e) {
			return "Couldn't get valid information for method";
		}
	}
	
	public String getReturnType() {
		//String returnType;

		try {
			return functionTag.getAttributeValue("returntype");
		}
		catch (Exception e) {
			return "void";
		}
	}
	
	public String getAccess() {
		try {
		    if (functionTag.getAttributeValue("access") != null) {
		        return functionTag.getAttributeValue("access");
		    }
		}
		catch (Exception e) {
		}
		return "public";
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
					if(functionTag.getMatchingItem() == null) // Just in case . . . 
						endPosition = documentContents.toLowerCase().indexOf("</cffunction",functionTag.getStartPosition()) + 13;
					else
						endPosition = functionTag.getMatchingItem().getEndPosition();
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
	
	
	
	public String getInsightXML() {
	    String insightXML = "";
	    
	    insightXML += "\t\t\t<function creator=\"8\" name=\""+functionTag.getAttributeValue("name")+"\" returns=\""+functionTag.getAttributeValue("returnType")+"\">\n";
		insightXML += "\t\t\\t\t<help><![CDATA[\n";
		insightXML += "\t\t\t\t\t \n";
		insightXML += "\t\t\t\t]]></help>\n";
		CFNodeList args = functionTag.selectNodes("//argument");
		Iterator j = args.iterator();
		while(j.hasNext()) {
			try {
				TagItem thisArg = (TagItem)j.next();
				insightXML += "\t\t\t\t<parameter name=\""+thisArg.getAttributeValue("name") + "\" type=\"" + thisArg.getAttributeValue("type") + "\" required=\"" + thisArg.getAttributeValue("required") +"\">\n";
				insightXML += "\t\t\t\t\t<help><![CDATA[\n";
				insightXML += "\t\t\t\t\t\t" + thisArg.getAttributeValue("hint")+ "\n";
				insightXML += "\t\t\t\t\t]]></help>\n";
				insightXML += "\t\t\t\t</parameter>\n\n";
			}
			catch (Exception e) {
				//System.err.println(e.getMessage());
			}
		}

		insightXML += "\t\t\t</function>\n\n";

	    
	    return insightXML;
	}
	
}
