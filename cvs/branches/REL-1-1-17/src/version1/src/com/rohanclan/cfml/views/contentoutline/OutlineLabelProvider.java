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
package com.rohanclan.cfml.views.contentoutline;

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
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.rohanclan.cfml.util.CFPluginImages;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.dictionary.SyntaxDictionary;
import com.rohanclan.cfml.dictionary.Parameter;

import java.util.Set;

import java.util.Iterator;

//import com.rohanclan.cfml.parser.*;
import com.rohanclan.cfml.parser.cfmltagitems.CfmlComment;
import com.rohanclan.cfml.parser.docitems.CfmlTagItem;

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
		
		return CFPluginImages.get(CFPluginImages.ICON_ALERT);
	}

	
	/**
	 * Get the text display for element
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) 
	{	
		if(element instanceof CfmlTagItem)
		{
//			StringBuffer sb = new StringBuffer("cf");
			StringBuffer sb = new StringBuffer("");			
			String tname = ((CfmlTagItem)element).getName(); 
			sb.append( tname );
			
			//this is a yucky hack... some items look stupid if not kid gloved
			if(hasSpecialNeeds(tname))
			{
				String data = ((CfmlTagItem)element).getItemData();
//				data = data.replaceAll("<cf"+tname,"");
//				data = data.replaceAll("<CF"+tname,"");
				data = data.replaceAll("<cf"+tname,"");
				data = data.replaceAll("<CF"+tname,"");
				
				data = data.replaceAll(">","");
				sb.append( data );
			}
			else
			{
				synchronized(syntax)
				{
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
								String aval = ((CfmlTagItem)element).getAttributeValue(p.getName());
								if(aval != null && aval.length() > 0)
								{
									sb.append(" " + p.getName() + ": " + aval);
								}
							}
						}
					}
				}
			}
			
			//sb.append( ((CfmlTagItem)element).getItemData() );
						
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
		
		return "unknown (add to user.xml if custom)";
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
		if(
			item.equals("if") || item.equals("set") || item.equals("loop")
			|| item.equals("else") || item.equals("elseif") || item.equals("break")
			|| item.equals("return") || item.equals("defaultcase") || item.equals("try")
			|| item.equals("rethrow")
		)
		{
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

