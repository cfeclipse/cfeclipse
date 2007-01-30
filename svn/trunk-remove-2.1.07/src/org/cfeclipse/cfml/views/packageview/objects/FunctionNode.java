/*
 * Created on 17-Feb-2006
 * 
 * The MIT License Copyright (c) 2006 markd
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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
package org.cfeclipse.cfml.views.packageview.objects;

import java.util.Iterator;

import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.util.CFPluginImages;


/**
 * This node represents an CFC <cffunction> tag.
 * @author markd
 * @author mike nimer
 */
public class FunctionNode extends TreeParent
{
    private TagItem functionTag;   
    private String accessType = "public";
    // Images for this item
    private String imgRemote = CFPluginImages.ICON_METHOD_REMOTE;
    private String imgPublic = CFPluginImages.ICON_METHOD_PUBLIC;
    private String imgPackage = CFPluginImages.ICON_METHOD_PACKAGE;
    private String imgPrivate = CFPluginImages.ICON_METHOD_PRIVATE;

    public FunctionNode(TagItem function)
    {
        super(function.getAttributeValue("name"));

        this.functionTag = function;
        this.accessType = function.getAttributeValue("access", "public");
        expand(function);
    }

    
    private void expand(TagItem function)
    {
        this.clearChildren();
        
        CFNodeList args = function.selectNodes("//cfargument");
        Iterator argiter = args.iterator();
        while (argiter.hasNext())
        {
            CfmlTagItem argTag = (CfmlTagItem) argiter.next();
            ArgumentNode argNode = new ArgumentNode(argTag);
            argNode.setParent(this);
            this.addChild(argNode);
        }
    }
    

    public String getImage()
    {
        // here we decide which image we return
        if (accessType.toLowerCase().equals("private"))
        {
            return imgPrivate;
        }
        else if (accessType.toLowerCase().equals("package"))
        {
            return imgPackage;
        }
        else if (accessType.toLowerCase().equals("remote"))
        {
            return imgRemote;
        }
        else if (accessType.toLowerCase().equals("public")) 
        { 
            return imgPublic; 
        }
        // Some error
        return CFPluginImages.ICON_ALERT;
    }

    private String getPackageName()
    {
        TreeParent parent = this.getParent();
        while( parent != null && !(parent instanceof PackageNode))
        {
            parent = parent.getParent();
        }
        if( parent == null )
        {
            return null;
        }
        
        return ((PackageNode) parent).getName();
    }
    
    public String toString()
    {
        String result = getName();
        if ( this.getChildren().length > 0 )
        {
            result += "( ";
            for( int i=0; i < this.getChildren().length; i++ )
            {
                if( i != 0 ) result += ", ";
                
                if( this.getChildren()[i] instanceof ArgumentNode )
                {
                    result += ((ArgumentNode)this.getChildren()[i]).getName();
                }
                else
                {
                    result += this.getChildren()[i].toString();
                }
            }
            result += " )";
        }
        else
        {
            result += "()";
        }
        
        if( this.functionTag.hasAttribute( "RETURNTYPE") )
        {
            result += " : " +this.functionTag.getAttributeValue("RETURNTYPE").toUpperCase();
        }
        else
        {
            result += " : VOID";
        }
        return result;
    
    }

    /**
     * This function returns the string creates a cfinvoke item <cfinvoke
     * component="ggcc7.controller.mailer" method="ExistsInCache"
     * returnvariable="ExistsInCacheRet"> <cfinvokeargument name="name"
     * value="enter_value_here"/> </cfinvoke>
     * 
     * @return
     */
    public String getInvokeSnippet()
    {
        String snippet = "<cfinvoke component=\"" + getPackageName() + "\" \n";
        snippet += "\tmethod=\"" + getName() + "\" \n";
        snippet += "\treturnvariable=\"ret" + getName() + "\"> \n";
        // Loop through the arguments
        for (int i = 0; i < this.getChildren().length; i++)
        {
            TreeObject item = this.getChildren()[i];
            snippet += "\t\t<cfinvokeargument name=\"" + ((ArgumentNode)item).getName() + "\" value=\"\"/>\n";
        }
        snippet += "</cfinvoke>";
        return snippet;
    }
    

    public String getCreateObjectSnippet()
    {
        String snippet = "CreateObject(\"component\", \"" + getPackageName() + "\")." + getName() + "(";
        for (int i = 0; i < this.getChildren().length; i++)
        {
            TreeObject item = this.getChildren()[i];
            snippet += ((ArgumentNode)item).getName();
            if ((i + 1) < this.getChildren().length)
            {
                snippet += ", ";
            }
        }
        snippet += ")";

        return snippet;

    }

    public String getDetails()
    {
        String details = "Details for " + toString() + "\n\n";
        details += "\nName: " + getName();
        details += "\nAccess: " + this.accessType;
        details += "\nReturn Type: " + this.functionTag.getAttributeValue("returntype", "any");
        details += "\nOutput Allowed: " + this.functionTag.getAttributeValue("output");
        details += "\nRoles: " + this.functionTag.getAttributeValue("roles");
        /*
         Details for return funcname(attribs)
         
         Name: name
         Access: acces
         Return Type:
         Output Allowed:
         Roles: 
         
         Implemented in
         Inherited: 
         
         */

        return details;
    }

}
