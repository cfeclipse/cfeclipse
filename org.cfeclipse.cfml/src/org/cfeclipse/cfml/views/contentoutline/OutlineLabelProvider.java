/*
 * Created on Feb 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package org.cfeclipse.cfml.views.contentoutline;

/**
 * @author Rob
 *
 * Provide icons and labels for the snip viewer tree
 * 
 * This class was influenced by the aricle:
 * How to use the JFace Tree Viewer
 * By Chris Grindstaff, Applied Reasoning (chrisg at appliedReasoning.com)
 * May 5, 2002
 */
import java.util.Iterator;
import java.util.Set;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Parameter;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.cfmltagitems.CfmlComment;
import org.cfeclipse.cfml.parser.cfscript.ASTFunctionCallNode;
import org.cfeclipse.cfml.parser.cfscript.ASTFunctionDeclaration;
import org.cfeclipse.cfml.parser.cfscript.ASTId;
import org.cfeclipse.cfml.parser.cfscript.ASTIfStatement;
import org.cfeclipse.cfml.parser.cfscript.ASTParameterList;
import org.cfeclipse.cfml.parser.cfscript.ASTStatementExpression;
import org.cfeclipse.cfml.parser.cfscript.SimpleNode;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

//import org.cfeclipse.cfml.parser.*;

public class OutlineLabelProvider extends LabelProvider {
	
	private SyntaxDictionary syntax = 
		DictionaryManager.getDictionary(DictionaryManager.CFDIC);
	
	private Parameter p;
	
	/**
	 * Get the image for element - this isnt the best looking method I have ever
	 * written but it works. TODO we may want to add this kind of info to the
	 * dictionary?
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element) 
	{
		if(element instanceof CfmlTagItem)
		{
			String tname = ((CfmlTagItem)element).getName().toLowerCase();
			
			if(tname.equals("cfcomponent"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_CLASS);
			}
			if(tname.equals("cfobject"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_BEAN);
			}
			else if(tname.equals("cffunction"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_FUNC);
			}
			else if(tname.equals("cfdump"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_DUMP);
			}
			else if(tname.equals("cfscript"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_SCRIPT);
			}
			else if(tname.equals("cfloop"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_REFRESH);
			}
			else if(tname.equals("cfbreak"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_DELETE);
			}
			else if(tname.equals("cfreturn"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_BACK);
			}
			else if(tname.equals("cfquery"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_TABLE);
			}
			else if(tname.equals("cfset"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_ADD);
			}
			else if(tname.equals("cfdirectory"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_FOLDER);
			}
			else if(tname.equals("cffile"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_FILE);
			}
			else if(tname.equals("cfinvoke"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_PROCESS);
			}
			else if(tname.equals("cfabort"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_STOP);
			}
			else if(tname.equals("cfftp") || tname.equals("cfhttp") 
				|| tname.equals("cfldap"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_SERVER);
			}
			else if(tname.equals("cfinclude") || tname.equals("cfmodule"))
			{
				return CFPluginImages.get(CFPluginImages.ICON_IMPORT);
			}
			else
			{
				return CFPluginImages.get(CFPluginImages.ICON_TAG);
			}
		}else if(element instanceof CfmlComment){
			//TODO this icon should be something else at some point
			return CFPluginImages.get(CFPluginImages.ICON_TOOLS);
		}
 else if (element instanceof ScriptItem)
		{
			if (((ScriptItem) element).getName().endsWith("ASTFunctionDeclaration"))
		    {
		        return CFPluginImages.get(CFPluginImages.ICON_FUNC);
		    }
			if (((ScriptItem) element).getName().endsWith("ASTFunctionParameter"))
		    {
				return CFPluginImages.get(CFPluginImages.ICON_PARAM);
		    }
			if (((ScriptItem) element).getName().endsWith("ASTVarDeclaration")) {
				return CFPluginImages.get(CFPluginImages.ICON_ADD);
			}
			if (((ScriptItem) element).getName().endsWith("ASTAssignment")) {
				return CFPluginImages.get(CFPluginImages.ICON_ADD);
			}
			if (((ScriptItem) element).getName().endsWith("ASTPropertyStatement")) {
				return CFPluginImages.get(CFPluginImages.ICON_METHOD_PUBLIC);
			}
			if (((ScriptItem) element).getName().endsWith("cfcomponent")) {
				return CFPluginImages.get(CFPluginImages.ICON_CLASS);
			}
	        return CFPluginImages.get(CFPluginImages.ICON_TOOLS);
		}
		
		return CFPluginImages.get(CFPluginImages.ICON_ALERT);
	}
	
	/**
	 * Get the text display for element
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) 
	{	
		if(element instanceof TagItem) //CfmlTagItem)
		{
			StringBuffer sb = new StringBuffer("");			
			String tname = ((TagItem)element).getName(); 
			
			//Added this as we dont need to display the function since it already has an icon
			if(!tname.equalsIgnoreCase("cffunction")){
				sb.append( tname );
			}
			
			//this is a yucky hack... some items look stupid if not kid gloved
			if(hasSpecialNeeds(tname))
			{
				String data = ((TagItem)element).getItemData();
				data = data.replaceAll("<"+tname,"");
				data = data.replaceAll("/>","");
				data = data.replaceAll(">","");
				sb.append( data );
			}
			else if (!tname.equalsIgnoreCase("cffunction"))
			{
				//synchronized(syntax)
				//{
					Set st = syntax.getElementAttributes(tname);
					
					//they could use undefined custom tags or made up tags
					if(st != null)
					{
						Iterator i = st.iterator();
						while(i.hasNext())
						{
							p = (Parameter)i.next();
							if(p.isRequired())
							{
								String aval = ((TagItem)element).getAttributeValue(p.getName());
								if(aval != null && aval.length() > 0)
								{
										sb.append(" " + p.getName() + ": " + aval);	
								}
							}
						}
					}
				//}
			}
			else if(tname.equalsIgnoreCase("cffunction")){
				TagItem tagItem = (TagItem)element;
				String name = tagItem.getAttributeValue("name");
				String returnType = tagItem.getAttributeValue("returntype");
				sb.append(name+" (");
				Iterator kids = tagItem.getChildNodes().iterator();
				while(kids.hasNext()) {
					DocItem kid = (DocItem) kids.next();
					if(kid instanceof TagItem && kid.getName().equals("cfargument")) {						
						String argName = ((TagItem)kid).getAttributeValue("name");
						String argType = ((TagItem)kid).getAttributeValue("type");
						if(argType != null) {
							sb.append(argType.replaceAll("^string$", "String").replaceAll("^any$", "Any").replaceAll("^struct$", "Struct"));
						} else {
							sb.append(argName);							
						}
						sb.append(", ");							
					}
				}
				if(sb.charAt(sb.length()-2) == ',') {					
					sb.setLength(sb.length()-2);
				}
				sb.append(")");
				if(returnType != null) {
					sb.append(" : "+returnType.replaceAll("^string$", "String").replaceAll("^void$", "Void").replaceAll("^any$", "Any").replaceAll("^struct$", "Struct"));
				}
			}
			
			return sb.toString();
		}
		else if(element instanceof CfmlComment)
		{
			String commentData = ((CfmlComment)element).getItemData();
			
			//this kind of sucks...
			commentData = commentData.replace('\n',' ');
			commentData = commentData.replaceAll("<!---","");
			commentData = commentData.replaceAll("--->","");
			
			//keep comments to label status...
			if(commentData.trim().length() > 40)
			{
				commentData = commentData.trim().substring(0,40) + "...";
			}
			
			return commentData;
		}
		else if(element instanceof ScriptItem)
		{
			String commentData = ((ScriptItem)element).getItemData();
			if(((ScriptItem) element).getName().endsWith("ASTFunctionDeclaration")){
		        //return getCFScriptFunctionName(element);				
			}
			
			return commentData;
		}
		else if(element instanceof SimpleNode)
		{
		    if(element instanceof ASTFunctionDeclaration)
		    {
		        return ((SimpleNode)element).getItemData();
		    }
		    else if(element instanceof ASTFunctionCallNode)
		    {
		        return ((SimpleNode)element).getItemData();
		    }
		    else if(element instanceof ASTIfStatement)
		    {
		        return ((SimpleNode)element).getItemData();
		    }
		    else if(element instanceof ASTStatementExpression)
		    {
		        return ((SimpleNode)element).getItemData();
		    }		    
			return element.getClass().getSimpleName() + ":" + ((SimpleNode)element).getItemData();
		}
		
		return element.getClass().getSimpleName() + ": unknown (add to user.xml if custom)";
	}
	
	/**
	 * Gets the name of a CFScript function.
	 * 
     * @param element The element that (should be) the CFScript function
     * @return The formatted name of the funciton + parameters
     */
    private String getCFScriptFunctionName(Object element) {
        ASTFunctionDeclaration function = (ASTFunctionDeclaration)element;
        CFNodeList children = function.getChildNodes();
        StringBuffer nameBuffer = new StringBuffer();
        
        if(children.size() > 0)
        {
            DocItem firstChild = (DocItem)children.get(0);
            if(firstChild instanceof ASTParameterList)
            {
                CFNodeList params = ((ASTParameterList)firstChild).getChildNodes();
                Iterator paramIter = params.iterator();
                while(paramIter.hasNext())
                {
                    Object currentParam = paramIter.next();
                    if(!(currentParam instanceof ASTId))
                    {
                        continue;
                    }
                    if(nameBuffer.length() > 0)
                    {
                        nameBuffer.append(", ");
                    }
                    nameBuffer.append(((ASTId)currentParam).toString());
                    
                }
            }
        }
        nameBuffer.insert(0, function.getItemData() + "(");
        nameBuffer.append(")");

        return nameBuffer.toString();
    }


    /**
	 * this is part of the formatting hack. This is a list of items that
	 * should just show their contents, or they have no possible attributes
	 * so it jacks up the display
	 * @param item
	 * @return
	 */
	private boolean hasSpecialNeeds(String item)
	{
	    item = item.toLowerCase();
		if(item.equals("cfif") || item.equals("cfset") || item.equals("cfloop")
			|| item.equals("cfelse") || item.equals("cfelseif") || item.equals("cfbreak")
			|| item.equals("cfreturn") || item.equals("cfdefaultcase") || item.equals("cftry")
			|| item.equals("cfrethrow")
		){
			return true;
		}
		return false;
	}
	
	protected RuntimeException unknownElement(Object element) 
	{
		return new RuntimeException(
			"Unknown type of element in tree of type " 
			+ element.getClass().getName()
		);
	}
	
	public void dispose(){;}
}

