/*
 * Created on 17-Feb-2006
 *
 * The MIT License
 * Copyright (c) 2006 markd
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
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;
import java.util.Iterator;

import com.rohanclan.cfml.parser.CFNodeList;
import com.rohanclan.cfml.parser.docitems.CfmlTagItem;
import com.rohanclan.cfml.parser.docitems.TagItem;
import com.rohanclan.cfml.util.CFPluginImages;

/**
 * @author markd
 *
 */
public class FunctionNode implements IComponentViewObject{
	private ArrayList children;
	private TagItem functionTag;
	private String functionName;
	private String accessType = "public";
	private IComponentViewObject parent;
	
	//Images for this item
	private String imgRemote = CFPluginImages.ICON_METHOD_REMOTE;
	private String imgPublic = CFPluginImages.ICON_METHOD_PUBLIC;
	private String imgPackage = CFPluginImages.ICON_METHOD_PACKAGE;
	private String imgPrivate = CFPluginImages.ICON_METHOD_PRIVATE;
	
	
	
	
	public FunctionNode(TagItem function){
		this.functionTag = function;
		this.functionName = this.functionTag.getAttributeValue("name");
		this.accessType = function.getAttributeValue("access", "public");
		this.children = new ArrayList();
		initAttributes(function);
	}
	
	private void initAttributes(TagItem function){
		CFNodeList args = function.selectNodes("//cfargument");
		Iterator argiter = args.iterator();
		while(argiter.hasNext()){
			CfmlTagItem argTag = (CfmlTagItem) argiter.next();
			ArgumentNode argNode = new ArgumentNode(argTag);
			argNode.setParent(this);
			this.children.add(argNode);
		}
	}
	
	public ArrayList getChildren() {
		return children;
	}

	public String getImage() {
		
		//here we decide which image we return
		 if (accessType.toLowerCase().equals("private")){
			return imgPrivate;
		}
		else if (accessType.toLowerCase().equals("package")){
			return imgPackage;
		}
		else if(accessType.toLowerCase().equals("remote")){
			return imgRemote;
		}
		else if (accessType.toLowerCase().equals("public")){
				return imgPublic;
		}
		 //Some error
		return CFPluginImages.ICON_ALERT;
	}

	public String getName() {
		return this.functionName;
	}

	public String getPackageName() {
		return parent.getPackageName();
	}

	public IComponentViewObject getParent() {
		return this.parent;
	}

	public boolean hasChildren() {
		return this.children.size()>0;
	}

	public void setChildren(ArrayList children) {
		this.children = children;
	}

	public void setImage(String image) {
		// TODO Auto-generated method stub
	}

	public void setName(String name) {
		this.functionName = name;
	}

	public void setParent(IComponentViewObject parent) {
		this.parent = parent;
	}
	public String toString(){
		String functionNameString = this.functionName + "(";
		for(int i = 0; i < this.children.size(); i++){
			functionNameString += ((ArgumentNode)this.children.get(i)).getName();
			if((i+1) < this.children.size()){
				functionNameString += ",";
			}
		}
		functionNameString += ")";
		return functionNameString;
	}
	
	/**
	 * This function returns the string creates a cfinvoke item
	 * 	<cfinvoke 
		 component="ggcc7.controller.mailer"
		 method="ExistsInCache"
		 returnvariable="ExistsInCacheRet">
			<cfinvokeargument name="name" value="enter_value_here"/>
		</cfinvoke>
	 * @return
	 */
	public String getInvokeSnippet(){
		String snippet = "<cfinvoke component=\"" + getPackageName() + "\" \n";
			  snippet += "\tmethod=\"" + getName() +"\" \n";
			  snippet += "\treturnvariable=\"ret" + getName() +"\"> \n";
		//Loop through the arguments
			  for(int i = 0; i < this.children.size(); i++){
				  snippet += "\t\t<cfinvokeargument name=\"" + ((ArgumentNode)this.children.get(i)).getName()  +"\" value=\"enter_value_here\"/>\n";
				}
			  snippet += "</cfinvoke>";
		return snippet;
	}
	
	public String getCreateObjectSnippet(){
		String snippet = "CreateObject(\"component\", \""+ getPackageName() +"\")." + getName() + "(";
		for(int i = 0; i < this.children.size(); i++){
			  snippet += ((ArgumentNode)this.children.get(i)).getName();
			  if((i+1) < this.children.size()){
				  snippet+=", ";
			  }
			}
		snippet += ")";
		
		return snippet;
		
	}
	
	public String getDetails(){
		String details = "Details for " + toString() +"\n\n";
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
