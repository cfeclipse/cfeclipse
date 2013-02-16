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
package org.cfeclipse.cfml.frameworks.views;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.frameworks.Activator;
import org.cfeclipse.cfml.frameworks.util.FWXImages;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * @author markdrew
 *
 */
public class ViewLabelProvider extends LabelProvider {

	private Document labelDocument;
	private ImageRegistry cacheImages = new ImageRegistry();
	
	private Log logger = LogFactory.getLog(ViewLabelProvider.class);
	
	public ViewLabelProvider() {
		super();
		
		//Load the XML labels and do whatever parsing required for later lookup
		
		try {
			URL labelConfigURL = new URL(
					Activator.getDefault().getBundle().getEntry("/"),
					"config/labels.xml"
				);
			
			//URL configurl = FileLocator.LocateURL(labelConfigURL, "labels.xml");
			SAXBuilder builder = new SAXBuilder();
			labelDocument = builder.build(labelConfigURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getText(Object obj) {
		if(obj instanceof TreeNode && ((TreeNode)obj).getFrameworkType() !=null){
			
			
			TreeNode node = (TreeNode)obj;
			
			return getLabelForNode(node);
			
		}
		//logger.warn("Cant find label for " + obj);
		return obj.toString();
	}
	public Image getImage(Object obj) {
		
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof TreeNode){
			TreeNode node = (TreeNode)obj;
				return getImageForNode(node);
		}
		
		//logger.warn("Cant find image for " + obj);
		  
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
	
	private String getLabelForNode(TreeNode node){
		//TODO: display path for root Items, using the parser, so if we have {FWX_FILENAME}
		//logger.debug("getting label for node " + node);
		Element treeElement = node.getElement();
		try {
			
			XPath	x = XPath.newInstance("//framework[@id='" + node.getFrameworkType() + "']/node[@name='"+ node.getName().toLowerCase() +"']");
			List<?> list = x.selectNodes(labelDocument);
			
			
			//If there is no element, just return something
			if(treeElement == null){
				//if this is null then its a base file!
				return node.getName();
			}
			
			//loop through the nodes we found
			/*
			
			if(list.size() == 1){
				Element label = (Element)list.get(0);
				String textAttribute = label.getAttributeValue("text");
				logger.debug("text attribute " + textAttribute);
				
				if(textAttribute!=null && textAttribute.equals("{FWX_FILENAME}")){
					return node.getFrameworkFile().getFile().getFullPath().toOSString();
				}
				else if(textAttribute != null){
					//Extract this as a function
					Pattern pattern = Pattern.compile("\\{[A-Za-z0-9-\\s]*\\}");
					 Matcher matcher = pattern.matcher(textAttribute);
					 logger.debug("text attribute " + matcher);
					 String outputString = "";
					 while (matcher.find()) {
						  
						 String fullMatch = matcher.group();
						 logger.debug("text attribute2 " + fullMatch);
						 String attrName = fullMatch.substring(1, fullMatch.length()-1);
						 String attributeValue = treeElement.getAttributeValue(attrName.toLowerCase());
						 if(attributeValue != null){
							 logger.debug("attribuve value - " + attributeValue);
							 textAttribute = textAttribute.replaceAll("." + attrName.toLowerCase() + ".", attributeValue);
						 }
						 else{
							 logger.debug("no  value - ");
							 textAttribute = textAttribute.replaceAll("." + attrName.toLowerCase() + ".", "");
						 }
						 
					  }
						return textAttribute;
				}
				
			}
			else{
				//we have more than one node
				logger.debug("we need to check all the nodes");*/
				for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
					Element labelDefElement = (Element) iter.next();
					//the variables we are checking
					String labelDefParent = labelDefElement.getAttributeValue("parent");
					Element parent = treeElement.getParentElement();
					
					if(labelDefParent == null){
						//logger.debug("NO PARENT  node " + node.getElement().getAttributes());
						return parseLabel(node, labelDefElement.getAttributeValue("text"));
					}
					else if(labelDefParent != null 
							&& parent !=null
							&& labelDefParent.equalsIgnoreCase(parent.getName())){
						//logger.debug("PARENT  node " + node.getElement().getAttributes());
						return parseLabel(node, labelDefElement.getAttributeValue("text")) ;
					}
					
				}
			//}
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		;
		//go and find it .. 
		
		
		
		
		
		return node.getName();
	}
	
	

	/**
	 * This function gets a TreeNode and applies the label rules
	 * @param node
	 * @param labelRules
	 * @return
	 */
	private String parseLabel(TreeNode node, String labelRules){
		
		String textAttribute = labelRules;
		
		if(textAttribute != null){
			//Extract this as a function
			Pattern pattern = Pattern.compile("\\{[A-Za-z0-9-\\s]*\\}");
			 Matcher matcher = pattern.matcher(textAttribute);
			 //logger.debug("text attribute " + matcher);
			 String outputString = "";
			 while (matcher.find()) {
				  
				 String fullMatch = matcher.group();
				 //logger.debug("text attribute2 " + fullMatch);
				 String attrName = fullMatch.substring(1, fullMatch.length()-1);
				 String attributeValue = node.getElement().getAttributeValue(attrName.toLowerCase());
				 if(attributeValue != null){
					 //logger.debug("attribuve value - " + attributeValue);
					 textAttribute = textAttribute.replaceAll("." + attrName.toLowerCase() + ".", attributeValue);
				 }
				 else{
					 //logger.debug("no  value - ");
					 textAttribute = textAttribute.replaceAll("." + attrName.toLowerCase() + ".", "");
				 }
				 
			  }
				return textAttribute;
		}
		return node.getElement().getName();
	}
	
	
	private Image getImageForNode(TreeNode node) {
		//Use XPATH to find the right node
		try {
			XPath	 x = XPath.newInstance("//framework[@id='" +  node.getFrameworkType().toLowerCase() + "']/node[@name='"+ node.getName().toLowerCase() +"']");
			 List<?> list = x.selectNodes(labelDocument);
			 
			 if(list.size() == 1){
				 Element label = (Element)list.get(0);
				 String imagePath = label.getAttributeValue("image");
				 if(imagePath != null){

					 return FWXImages.get(imagePath);
				 }
				 
			 }
			 else if(list.size() > 1){	
				 for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
						Element labelDefElement = (Element) iter.next();
						//the variables we are checking
						String labelDefParent = labelDefElement.getAttributeValue("parent");
						Element parent = node.getElement().getParentElement();
						
						if(labelDefParent == null){
							 String imagePath = labelDefElement.getAttributeValue("image");
							 if(imagePath != null){
								 return FWXImages.get(imagePath);
							 }
						}
						else if(labelDefParent != null 
								&& parent !=null
								&& labelDefParent.equalsIgnoreCase(parent.getName())){
							 String imagePath = labelDefElement.getAttributeValue("image");
							 if(imagePath != null){
								 return FWXImages.get(imagePath);
							 }
						}
						
					}
			 }
			 else if (node.getFrameworkType().equalsIgnoreCase("framework_root")){
				 return FWXImages.get(FWXImages.ICON_PROJECT); 
			 }
			 else if(node.getFrameworkFile()!=null){
				 //one final try, just get the node for the framework since this has failed
				XPath x2 = XPath.newInstance("/labels/framework[@id='"+node.getFrameworkFile().getFrameworkType() +"']/node[@name='"+ node.getFrameworkFile().getFrameworkType() + "']");
				Object object = x2.selectSingleNode(labelDocument);
				if(object != null && object instanceof Element){
					Element lblNode = (Element) object;
					String imagePath = lblNode.getAttributeValue("image");
					 if(imagePath != null){
						 return FWXImages.get(imagePath);
					 }
				}
				else{
					return PlatformUI.getWorkbench().getSharedImages().getImage( ISharedImages.IMG_OBJ_ELEMENT);
				}
				 
			 }
			
			 
			 
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		//if it is a root
		if(node.getFrameworkType().equalsIgnoreCase("framework_root")){
			return FWXImages.get(FWXImages.ICON_PROJECT);
		}
		
		
		// logger.warn("No image found for "  + node.getName() + "("+ node.getFrameworkType() +")");
		return PlatformUI.getWorkbench().getSharedImages().getImage( ISharedImages.IMG_OBJ_ELEMENT);
	}
	
}