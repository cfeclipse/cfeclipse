/*
 * Created on Mar 27, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
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
package org.cfeclipse.cfml.frameworks.actions;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.views.TreeNode;
import org.cfeclipse.cfml.views.snips.SnipSmartDialog;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.xpath.XPath;
import sun.misc.Regexp;

/**
 * @author markdrew
 *
 */
public class BaseAction extends Action implements IBaseAction{
	
	private String insertSnippet;
	private Element actionElement; 	//The xml node from the actions.xml File
	private TreeNode treeNode; 		//The treenode, with framework type
	private Element nodeElement;	//The element from the treenode, required to parse some items
	private Project project;
	private Document virtualDocument;
	
	private Log logger = LogFactory.getLog(BaseAction.class);
	
	public Element getElement() {
		return actionElement;
	}


	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.frameworks.actions.IBaseAction#setElement(org.jdom.Element)
	 */
	public void setElement(Element actionElement) {
		this.actionElement = actionElement;
	}


	public TreeNode getNode() {
		return treeNode;
	}



	/* (non-Javadoc)
	 * @see org.cfeclipse.cfml.frameworks.actions.IBaseAction#setNode(org.cfeclipse.cfml.frameworks.views.TreeNode)
	 */
	public void setNode(TreeNode treeNode) {
		this.treeNode = treeNode;
		if(treeNode.getElement() !=null){
			this.nodeElement = treeNode.getElement();
		}
	}


	public String getInsertSnippet() {
		//parse the snippet? or would the action manager do this?
		
		
		return insertSnippet;
	}


	public String getParsedAction(){
		if(actionElement != null){
		String actionText = actionElement.getTextTrim();
		
		actionText = doAttributeReplace(actionText);
		actionText = doXPathFunctions(actionText);
		actionText = doFwFilePath(actionText);
		//actionText = doListFileFunctions(actionText);
		return actionText;
		}
		return null;
	}


	/**
	 * @param actionText
	 * @return
	 */
	private String doFwFilePath(String actionText) {
		String functionMatch = "fwfilepath\\(\\)";
		Pattern pattern = Pattern.compile(functionMatch);
		Matcher matcher = pattern.matcher(actionText);
		
		IPath projectRelativePath = this.treeNode.getFrameworkFile().getFile().getProjectRelativePath();
		IPath path2 = projectRelativePath.removeLastSegments(1);
		
		while(matcher.find()){	
			String path = matcher.group(0);
			actionText = matcher.replaceAll(path2.toOSString());
		}
		return actionText;
	}


	/**
	 * Parses listfiles("path","relative or absolute", "delimiter");
	 * @param actionText
	 * @return
	 */
	private String doListFileFunctions(String actionText) {
		String functionMatch  = "listfiles\\s*\\(\\s*\"([^\"]*)\"\\s*,\\s*\"([^\"]*)\",\\s*\"([^\"]*)\"\\)"; //listfiles\s*\(\s*"([^"]*)"\s*,\s*"([^"]*)",\s*"([^"]*)"\)
		Pattern pattern = Pattern.compile(functionMatch);
		Matcher matcher = pattern.matcher(actionText);
		
		while(matcher.find()){
			
			String path = matcher.group(1);
			String type = matcher.group(2);
			String delimiter = matcher.group(3);
			String files  = doListFile(path, type, delimiter);
			actionText = matcher.replaceAll(files);
		}
		
		return null;
	}


	/**
	 * This function actually goes looking for the right folder, and returns the files
	 * @param path
	 * @param type
	 * @param delimiter
	 * @return
	 */
	private String doListFile(String path, String type, String delimiter) {
		// TODO Auto-generated method stub
		return path;
	}


	/**
	 * @param actionText
	 * @return
	 */
	private String doXPathFunctions(String actionText) {
		String xpathfunctions = "xpath\\s*\\(\\s*\"([^\"]*)\"\\s*,\\s*\"([^\"]*)\"\\)"; //for xpath .. working regEx xpath\s*\(\s*"([^"]*)"\s*,\s*"([^"]*)"\)

		
		
		
		Pattern stage2Pattern = Pattern.compile(xpathfunctions);
		Matcher stage2Matcher = stage2Pattern.matcher(actionText);
		
		while(stage2Matcher.find()){
			String string = doXPath(stage2Matcher.group(1), stage2Matcher.group(2)); 
			actionText = stage2Matcher.replaceAll(string);
		}
		return actionText;
	}
	
	public String doAttributeReplace(String actionString){
			String parsedSnippet = actionString;
			Pattern pattern = Pattern.compile("(?<!\\$\\$)\\{[a-zA-Z0-9-\\s]*\\}");
			 Matcher matcher = pattern.matcher(parsedSnippet);
			 while (matcher.find()) {
				 String fullMatch = matcher.group();

				 String attrName = fullMatch.substring(1, fullMatch.length()-1);
				 
				 String attributeValue = this.treeNode.getElement().getAttributeValue(attrName.toLowerCase());
				 if(attributeValue != null){
					 //logger.debug("attribuve value - " + attributeValue);
					 parsedSnippet = parsedSnippet.replaceAll("." + attrName.toLowerCase() + ".", attributeValue);
				 }
				 else{
					 //logger.debug("no  value - ");
					 parsedSnippet = parsedSnippet.replaceAll("." + attrName.toLowerCase() + ".", "");
				 }
				 
			  }
	
		//By default return the unparsed snippet
		return parsedSnippet;
	}
	
	
	
	private String doXPath(String xpath, String delimiter){
		
		StringBuffer buffer = new StringBuffer();
		
		try {
			XPath x = XPath.newInstance(xpath);
			List list = x.selectNodes(this.virtualDocument.getRootElement());
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object listItem = (Object)iter.next();
				if (listItem instanceof Attribute) {
					Attribute attrib = (Attribute) listItem;
					buffer.append(attrib.getValue());
					
				}
				else if (listItem instanceof Element) {
					Element element = (Element) listItem;
					buffer.append(element.getName());
				}
				else if (listItem instanceof Text) {
					Text textelem = (Text)listItem;
					buffer.append(textelem.getTextTrim());
					
				}
				else {
					buffer.append(listItem.toString());
				}
				if(iter.hasNext()){
					buffer.append(delimiter);
				}
			}
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	
	
	
	
	public void setInsertSnippet(String insertSnippet) {
		this.insertSnippet = insertSnippet;
	}


	public String getText() {
		// TODO Auto-generated method stub
		return actionElement.getAttributeValue("label");
	}


	public void run() {
		showMessage("Call from Base Action");
	}
	
	
	public void showMessage(String message) {
		MessageDialog.openInformation(
			null,
			"Frameworks",
			message);
	}
	public void showError(String message) {
		MessageDialog.openError(
			null,
			"Error:",
			message);
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public Project getProject() {
		return project;
	}


	public void setVirtualDocument(Document virtualDocument) {
		this.virtualDocument = virtualDocument;
	}
}
