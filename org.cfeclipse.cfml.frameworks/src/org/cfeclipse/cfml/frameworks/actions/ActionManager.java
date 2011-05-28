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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.Activator;
import org.cfeclipse.cfml.frameworks.views.TreeNode;
import org.cfeclipse.cfml.util.FileLocator;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * @author markdrew
 * Gets the actions for an Tree element
 */
public class ActionManager {
	
	private Document actionDefinitions;
	private Project project;
	private Log logger = LogFactory.getLog(ActionManager.class);
	
	public ActionManager() {
		super();
		//Load up the action XML
		try {
			URL actionConfigURL = new URL(
					Activator.getDefault().getBundle().getEntry("/"),
					"config/actions.xml"
				);
			
		//TODO: This is throwing an error for some reason
			//URL configurl = FileLocator.LocateURL(labelConfigURL, "actions.xml");
			
			
			SAXBuilder builder = new SAXBuilder();
			actionDefinitions = builder.build(actionConfigURL);
			logger.debug("Loaded up the action file");
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public Object[] getRightClickActions(TreeNode node, IProject currentProject, Document virtualDocument){
		return getActionsForNode(node, currentProject, "right_click", virtualDocument);
	}

	public Object[] getLeftClickAction(TreeNode node, IProject currentProject, Document virtualDocument){
		
		//We try to get the actions, otherwise we create a new one
		
		Object[] actionsForNode = getActionsForNode(node, currentProject, "left_click", virtualDocument);
		
		if(actionsForNode.length == 0){
			//create a new action, to open a file
			GoToFile gtf = new GoToFile();
			gtf.setNode(node);
			Object[] actions = new Object[]{gtf};
			return actions;
		}
		else{
			return actionsForNode;
		}
	}

	/**
	 * @param node
	 * @param currentProject
	 */
	private Object[] getActionsForNode(TreeNode node, IProject currentProject, String action, Document virtualDocument) {
		this.project = (Project) currentProject;
		
		ArrayList actions = new ArrayList(); 
		
		XPath x;
		try {
			x = XPath.newInstance("/actions/action[@framework='" + node.getFrameworkType() + "' and @node='"+ node.getName().toLowerCase() +"' and @type='"+ action + "']");
			List list = x.selectNodes(actionDefinitions);
			
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				
				String insertSnippet = element.getText().trim();
				
				
				IBaseAction object = (IBaseAction)createAction(element, node);
				object.setElement(element);
				object.setNode(node);
				object.setProject(project);
				object.setVirtualDocument(virtualDocument);
				
				if(insertSnippet != null){
					object.setInsertSnippet(insertSnippet);
					logger.debug("Created the action : "  +  insertSnippet);
				}
				
				if(object !=null){
					actions.add(object);
				}
				//Create the actual action
			}
			//loop through the list creating the actions
			return actions.toArray();
			
		} catch (JDOMException e) {
			logger.error(e);
		}
		return null;
	}
	
	
	
	private Object createAction(Element element, TreeNode node){
		try {
				ClassLoader classLoader = this.getClass().getClassLoader();
				Class name = classLoader.loadClass(element.getAttributeValue("class"));
				logger.debug("created: " + name);
				Object object = name.newInstance();
				//Method setNode = name.getMethod("setNode", TreeNode.class);
				
			//	setNode.invoke(object, new Object[]{element});
				return object;
				
				    
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
		return null;
	}
	
	public Document getActions(){
		return actionDefinitions;
	}
	
	public List getActionNodes(){
		XPath x;
		
		try {
			x = XPath.newInstance("/actions/action");
			List list = x.selectNodes(actionDefinitions);
			return list;
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Maybe put the normal parsing here, rather than all the actions doing it?
	
	
}
