/*
 * Created on May 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.cfcmethods;

import com.rohanclan.cfml.parser.*;
import com.rohanclan.cfml.parser.docitems.TagItem;

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
		String returnType;

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
