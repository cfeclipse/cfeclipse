/*
 * Created on 28-Sep-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.crimson.tree.XmlDocument;
import org.apache.crimson.tree.XmlDocumentBuilder;
import org.eclipse.core.internal.resources.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.tigris.cfeclipse.machII.views.model.AnnounceNode;
import org.tigris.cfeclipse.machII.views.model.EventArgNode;
import org.tigris.cfeclipse.machII.views.model.EventFiltersNode;
import org.tigris.cfeclipse.machII.views.model.EventHandlerNode;
import org.tigris.cfeclipse.machII.views.model.InvokerNode;
import org.tigris.cfeclipse.machII.views.model.ListenerNode;
import org.tigris.cfeclipse.machII.views.model.ListenersNode;
import org.tigris.cfeclipse.machII.views.model.MachIIRoot;
import org.tigris.cfeclipse.machII.views.model.NameValueNode;
import org.tigris.cfeclipse.machII.views.model.NotifyNode;
import org.tigris.cfeclipse.machII.views.model.PropertyNode;
import org.tigris.cfeclipse.machII.views.model.TreeObject;
import org.tigris.cfeclipse.machII.views.model.TreeParent;
import org.tigris.cfeclipse.machII.views.model.ViewPageNode;

/**
 * @author OLIVER
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MachIIWriter {
	
	private Document xmlDoc; 

	public MachIIWriter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void saveXml(String pathName, MachIIRoot root)
	{
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fact.newDocumentBuilder();
			this.xmlDoc =  builder.newDocument();
			
			Element rootNode;
			Element propsNode;
			Element listenersNode;
			Element eventFiltersNode;
			Element eventHandlersNode;
			Element pageViewsNode;
			Element pluginsNode;
			
			rootNode = xmlDoc.createElement("mach-ii");
			rootNode.setAttribute("version", "1.0");
			
			propsNode = outputProps(root);
			listenersNode = outputListeners(root);
			eventFiltersNode = outputFilters(root);
			eventHandlersNode = outputHandlers(root);
			
			rootNode.appendChild(propsNode);
			rootNode.appendChild(listenersNode);
			rootNode.appendChild(eventFiltersNode);
			rootNode.appendChild(eventHandlersNode);
			
			xmlDoc.appendChild(rootNode);
			writeXmlFile(xmlDoc, pathName);
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	 // This method writes a DOM document to a file
    public static void writeXmlFile(Document doc, String filename) {
        try {
            Transformer xFormer = TransformerFactory.newInstance().newTransformer();
            xFormer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(filename)));
            
        } catch (TransformerConfigurationException e) {
        	e.printStackTrace();
        } catch (TransformerException e) {
        	e.printStackTrace();
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
    }
	
	public Element outputProps(MachIIRoot root)
	{
		Element propsNode = xmlDoc.createElement("properties");
		TreeObject props [] = root.getProperties().getChildren();
		for(int i = 0; i < props.length; i++)
		{
			PropertyNode node = (PropertyNode)props[i];
			
			Element prop = xmlDoc.createElement("property");
			prop.setAttribute("name", node.getName());
			prop.setAttribute("value", node.getValue());
			propsNode.appendChild(prop);
		}
		return propsNode;
	}
	
	private Element createInvokerNode(InvokerNode node)
	{
		Element invokerNode = xmlDoc.createElement("invoker");
		invokerNode.setAttribute("type", node.getType());
		return invokerNode;
	}
	
	private Element createParamNode(NameValueNode paramNode)
	{
		Element newNode = this.xmlDoc.createElement("parameter");
		newNode.setAttribute("name", paramNode.getName());
		newNode.setAttribute("value", paramNode.getValue());
		return newNode;
	}
	
	private Element createParametersNode(TreeParent paramsNode)
	{
		Element newNode = this.xmlDoc.createElement("parameters");
		
		TreeObject children [] = paramsNode.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			newNode.appendChild(createParamNode((NameValueNode)children[i]));
		}
		return newNode;
	}
	
	public Element createListnerFromTreeNode(ListenerNode node)
	{
		Element listenerNode = xmlDoc.createElement("listener");
		listenerNode.setAttribute("name", node.getName());
		listenerNode.setAttribute("type", node.getType());
		
		TreeObject children[] = node.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			if(children[i] instanceof InvokerNode)
			{
				listenerNode.appendChild(createInvokerNode((InvokerNode)children[i]));
			}
			else if(children[i] instanceof TreeParent)
			{
				if(children[i].getName().equals("Parameters"))
				{
					listenerNode.appendChild(createParametersNode((TreeParent)children[i]));
				}
			}
		}
		
		return listenerNode;
	}
	
	public Element outputListeners(MachIIRoot root)
	{
		Element listNodes = this.xmlDoc.createElement("listeners");
		TreeObject children [] = root.getListeners().getChildren();
		for(int i = 0; i < children.length; i++)
		{
			listNodes.appendChild(createListnerFromTreeNode((ListenerNode)children[i]));
		}
		return listNodes;
	}
		
	public Element outputFilters(MachIIRoot root)
	{
		Element filters = this.xmlDoc.createElement("event-filters");
		TreeObject children[] = root.getEventFilters().getChildren();
		for(int i = 0; i < children.length; i++)
		{
			NameValueNode filter = (NameValueNode)children[i];
			Element xFilter = this.xmlDoc.createElement("event-filter");
			xFilter.setAttribute("name", filter.getName());
			xFilter.setAttribute("type", filter.getValue());
			filters.appendChild(xFilter);
		}
		return filters;
	}

	private Element createNotifyNode(NotifyNode node)
	{
		Element xNode = this.xmlDoc.createElement("notify");
		xNode.setAttribute("listener", node.getListener());
		xNode.setAttribute("method", node.getMethod());
		xNode.setAttribute("resultKey", node.getResultKey());
		return xNode;
	}
	
	private Element createViewPageNode(ViewPageNode node)
	{
		Element xNode = this.xmlDoc.createElement("view-page");
		xNode.setAttribute("name", node.getName());
		if(node.getContentKey() != null)
			xNode.setAttribute("contentKey", node.getContentKey());
		return xNode;
	}
	
	private Element createFilterNode(TreeParent node)
	{
		Element xNode = this.xmlDoc.createElement("filter");
		xNode.setAttribute("name", node.getName());
		TreeObject children[] = node.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			xNode.appendChild(createParamNode((NameValueNode)children[i]));
		}
		return xNode;
	}
	
	private Element createEventArgNode(EventArgNode node)
	{
		Element xNode = this.xmlDoc.createElement("event-arg");
		xNode.setAttribute("name", node.getName());
		if(node.getValue() != null)
			xNode.setAttribute("value", node.getValue());
		if(node.getVariableName() != null)
			xNode.setAttribute("variable", node.getVariableName());
		
		return xNode;
	}
	
	private Element createAnnounceNode(AnnounceNode node)
	{
		Element xNode = this.xmlDoc.createElement("announce");
		xNode.setAttribute("event", node.getEvent());
		if(node.getCopyEventArgs() != null)
		{
			xNode.setAttribute("copyEventArgs", node.getCopyEventArgs());
		}
		return xNode;
	}
	
	private Element createEventHandler(EventHandlerNode handler)
	{
		Element xNode = this.xmlDoc.createElement("event-handler");
		
		if(handler.getAccess() != null)
			xNode.setAttribute("access", handler.getAccess());
		
		TreeObject children[] = handler.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			if(children[i] instanceof NotifyNode)
			{
				xNode.appendChild(createNotifyNode((NotifyNode)children[i]));
			}
			else if(children[i] instanceof ViewPageNode)
			{
				xNode.appendChild(createViewPageNode((ViewPageNode)children[i]));
			}
			else if(children[i] instanceof TreeParent)
			{
				if(children[i].getName().equals("Filter"))
				{
					xNode.appendChild(createFilterNode((TreeParent)children[i]));
				}
			}
			else if(children[i] instanceof EventArgNode)
			{
				xNode.appendChild(createEventArgNode((EventArgNode)children[i]));
			}
			else if(children[i] instanceof AnnounceNode)
			{
				xNode.appendChild(createAnnounceNode((AnnounceNode)children[i]));
			}
			
		}
		return xNode;
	}
	
	private Element outputHandlers(MachIIRoot root)
	{
		Element handlersNode = this.xmlDoc.createElement("event-handlers");
		TreeObject children[] = root.getEventHandlers().getChildren();
		for(int i = 0; i < children.length; i++)
		{
			handlersNode.appendChild(createEventHandler((EventHandlerNode)children[i]));
		}
		return handlersNode;
	}
	
	
	public void outputProps(MachIIRoot root, Element parent, XmlDocument doc)
	{
		Element propsNode = doc.createElement("properties");
		TreeObject props [] = root.getProperties().getChildren();
		for(int i = 0; i < props.length; i++)
		{
			PropertyNode node = (PropertyNode)props[i];
			
			Element prop = doc.createElement("property");
			prop.setAttribute("name", node.getName());
			prop.setAttribute("value", node.getValue());
			propsNode.appendChild(prop);
		}
		parent.appendChild(propsNode);
	}
	
}
