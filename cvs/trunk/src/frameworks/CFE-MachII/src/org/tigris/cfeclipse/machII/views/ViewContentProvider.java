/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.tigris.cfeclipse.machII.views;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.rohanclan.cfml.dictionary.Function;
import com.rohanclan.cfml.dictionary.Parameter;
import com.rohanclan.cfml.dictionary.Procedure;
import org.tigris.cfeclipse.machII.views.model.AnnounceNode;
import org.tigris.cfeclipse.machII.views.model.EventArgNode;
import org.tigris.cfeclipse.machII.views.model.EventBeanNode;
import org.tigris.cfeclipse.machII.views.model.EventHandlerNode;
import org.tigris.cfeclipse.machII.views.model.EventMappingNode;
import org.tigris.cfeclipse.machII.views.model.InvokerNode;
import org.tigris.cfeclipse.machII.views.model.ListenerNode;
import org.tigris.cfeclipse.machII.views.model.ListenersNode;
import org.tigris.cfeclipse.machII.views.model.MachIIRoot;
import org.tigris.cfeclipse.machII.views.model.NameValueNode;
import org.tigris.cfeclipse.machII.views.model.NotifyNode;
import org.tigris.cfeclipse.machII.views.model.PageViewNode;
import org.tigris.cfeclipse.machII.views.model.PluginNode;
import org.tigris.cfeclipse.machII.views.model.PropertiesNode;
import org.tigris.cfeclipse.machII.views.model.PropertyNode;
import org.tigris.cfeclipse.machII.views.model.TreeObject;
import org.tigris.cfeclipse.machII.views.model.TreeParent;
import org.tigris.cfeclipse.machII.views.model.ViewPageNode;
import org.tigris.cfeclipse.machII.views.model.EventFilterNode;

class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider,
										   ContentHandler {
	
	//keep track of the location
	private Locator locator;
	//private Map namespaceMappings;
	
	private Map dtags;
	private Map dfunctions;	
	private Map dscopeVars;
	
	/** used to mark which part of the xml doc we are in */
	private String currenttag = "";
	/** current tag/function being built */
	private Procedure currentitem = null;
	private Parameter paramitem = null;
	private Function methoditem = null;	

	private TreeParent invisibleRoot;
	
	private IFile sourceFile = null;

	public ViewContentProvider(IFile filename)
	{
		super();
		this.sourceFile = filename;
	}
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
	public void dispose() {
	}
	public Object[] getElements(Object parent) {
//			if (parent.equals(getViewSite())) {
			if (invisibleRoot==null) initialize();
			return getChildren(invisibleRoot);
		//}
		//return getChildren(parent);
	}
	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		}
		return null;
	}
	public Object [] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).getChildren();
		}
		return new Object[0];
	}
	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent)parent).hasChildren();
		return false;
	}

	
	private void loadXml(String filename) throws IOException, SAXException, ParserConfigurationException
	{
		// "file:///C:/mach-ii.xml"
		URLConnection urlcon = new URL("file:///" + filename).openConnection();
		BufferedInputStream xml = new BufferedInputStream(
			urlcon.getInputStream()
		);
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		
		//setup the content handler and give it the maps for tags and functions
		xmlReader.setContentHandler(
			this
		);
		
		InputSource input = new InputSource(xml);
		xmlReader.parse(input);		
	}
	
	private void initialize() {
		invisibleRoot = new TreeParent("Test");
		try {
			this.loadXml(this.sourceFile.getLocation().toOSString());
			this.propsNode = new PropertiesNode("Properties");
			this.listenersNode = new ListenersNode("Listeners");
			invisibleRoot.addChild(this.root);
			//this.invisibleRoot = this.root;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/** process the end prefix */
	public void endPrefixMapping(String str) throws SAXException {;}
		
	/** process characters */
	public void characters(char[] values, int start, int length) throws SAXException {
			
		if(currenttag.equals("help"))
		{
			StringBuffer resvalue = new StringBuffer();
			
			for(int x=start; x<(start+length); x++){
				resvalue.append(values[x]);
			}
			
			//if the current item is not null and the prams are its help for the
			//current item
			if(currentitem != null && paramitem == null)
			{
				//for some reason M8 calls this a bunch of times, but only with
				//the cfml dictionary. So this is kind of a hack to get it to
				//load the help right... this slows it down a bit
				//TODO figure out whats up
				if(resvalue.toString().trim().length() > 0)
				{
					currentitem.setHelp(
						currentitem.getHelp() + " " +
						resvalue.toString().trim().replace('\t',' ') + "\n"
					);
				}
			}
			//if the param is not null its help for the param
			else if(currentitem != null && paramitem != null)
			{
				//TODO here too
				//paramitem.setHelp(resvalue.toString().trim().replace('\t',' '));
				if(resvalue.toString().trim().length() > 0)
				{
					paramitem.setHelp(
						paramitem.getHelp() + " " +
						resvalue.toString().trim().replace('\t',' ') + "\n"
					);
				}
			}
		}
	}		
	

	/** sets the documnet locator
	 * @param locator the document locator
	 */	
	public void setDocumentLocator(Locator locator) 
	{
		this.locator = locator;
	}
	
	private Stack tagStack = new Stack();
	private Stack nodeStack = new Stack();
	private MachIIRoot root = new MachIIRoot();
	private PropertiesNode propsNode = new PropertiesNode("Properties");
	private ListenersNode listenersNode = new ListenersNode("Listener");
	
	private TreeObject currNode = null;
	
	/** process a start element */
	public void startElement(String namespace, String localName, String tagName, org.xml.sax.Attributes attributes) 
		throws SAXException 
	{
		//save the current tag so we can see where we were
		this.currenttag = tagName;
		
		this.tagStack.push(tagName);
		System.out.println("ViewContentProvider::startElement() - Got a \'" + tagName + "\'");
		if(tagName.equals("properties")){

		} else if(tagName.equals("property"))	{
			handlePropertyStart(attributes);
		} else if(tagName.equals("listener"))	{
			handleListnerStart(attributes);
		} else if(tagName.equals("invoker"))	{
			handleInvokerStart(attributes);
		} else if(tagName.equals("event-filter"))	{
			handleEFilterStart(attributes);
		} else if(tagName.equals("event-handler")) {
			handleEHandlerStart(attributes);
		} else if(tagName.equals("parameter")) {
			handleParamStart(attributes);
		} else if(tagName.equals("filter")) {
			handleFilterStart(attributes);
		} else if(tagName.equals("notify")) {
			handleNotifyStart(attributes);
		} else if(tagName.equals("view-page"))	{
			handleViewPageStart(attributes);
		} else if(tagName.equals("announce")) {
			handleAnnounceStart(attributes);
		} else if(tagName.equals("event-args")) {
			handleEventArgStart(attributes);			
		} else if(tagName.equals("plugin")) {
			handlePluginStart(attributes);
		} else if(tagName.equals("page-view")) {
			handlePageViewStart(attributes);
		} else if(tagName.equals("event-mapping"))	{
			handleEventMapping(attributes);
		} else if(tagName.equals("event-bean")) {
			handleEventBean(attributes);
		}
	}
	
	private void handleEventBean(Attributes attribs)
	{
		EventBeanNode newNode = new EventBeanNode(attribs.getValue("name"));
		newNode.setType(attribs.getValue("type"));
		if(attribs.getValue("fields") != null) {
			newNode.setFields(attribs.getValue("fields"));
		}
	}
	
	private void handleEventMapping(Attributes attribs)
	{
		EventMappingNode newNode = new EventMappingNode();
		newNode.setEvent(attribs.getValue("event"));
		newNode.setEvent(attribs.getValue("mapping"));
		
		((EventHandlerNode)this.currNode).addChild(newNode);
	}
	
	private void handleEventArgStart(Attributes attribs)
	{
		EventArgNode newNode = new EventArgNode(attribs.getValue("name"));
		newNode.setValue(attribs.getValue("value"));		 
		newNode.setVariableName(attribs.getValue("variable"));
		
		((TreeParent)this.currNode).addChild(newNode);
	}
	
	private void handlePageViewStart(Attributes attribs)
	{
		this.root.addChild(new PageViewNode(attribs.getValue("name"), attribs.getValue("page")));
	}
	
	private void handlePluginStart(Attributes attribs)
	{
		PluginNode newNode = new PluginNode(attribs.getValue("name"), attribs.getValue("type"));
		this.currNode = newNode;
	}
	
	private void handleAnnounceStart(Attributes attribs)
	{
		AnnounceNode newNode = new AnnounceNode(attribs.getValue("event"));
		if(attribs.getValue("copyEventArgs") != null)
		{
			newNode.setCopyEventArgs(attribs.getValue("copyEventArgs"));
		}
		((TreeParent)this.currNode).addChild(newNode);
	}
	
	private void handleViewPageStart(Attributes attribs)
	{
		ViewPageNode newNode = new ViewPageNode();
		newNode.setName(attribs.getValue("name"));
		newNode.setContentKey(attribs.getValue("contentKey"));
		((TreeParent)this.currNode).addChild(newNode);
	}
	
	private void handleNotifyStart(org.xml.sax.Attributes attribs)
	{
		NotifyNode newNode = new NotifyNode();
		newNode.setListener(attribs.getValue("listener"));
		newNode.setMethod(attribs.getValue("method"));
		newNode.setResultKey(attribs.getValue("resultKey"));
		
		((TreeParent)this.currNode).addChild(newNode);
	} 
	
	private void handleFilterStart(org.xml.sax.Attributes attribs)
	{
		System.out.println("ViewContentProvider::handleFilterStart() - Got a filter");
		TreeParent newNode = new TreeParent("Filter");
		this.currNode = newNode;
		this.nodeStack.push(newNode);
	}
	
	private void handleEHandlerStart(org.xml.sax.Attributes attribs)
	{
		System.out.println("ViewContentProvider::handleEHandlerStart() - Handling event-handler");
		String access = attribs.getValue("access");
		access = (access == null) ? "" : access;
		
		EventHandlerNode newNode = new EventHandlerNode(attribs.getValue("event"), access);
		this.currNode = newNode;
		this.nodeStack.push(newNode);
	}
	
	private void handleEFilterStart(org.xml.sax.Attributes attribs)
	{
		this.root.addEventFilter(new EventFilterNode(attribs.getValue("name"), attribs.getValue("type")));
	}
	
	private void handleParamStart(org.xml.sax.Attributes attribs)
	{
		if(this.currNode instanceof ListenerNode)
			((ListenerNode)this.currNode).addParam(attribs.getValue("name"), attribs.getValue("value"));
		else if(this.currNode instanceof TreeParent)
			((TreeParent)this.currNode).addChild(new NameValueNode(attribs.getValue("name"), attribs.getValue("value")));
		
	}
	
	private void handleInvokerStart(org.xml.sax.Attributes attribs)
	{
		InvokerNode newNode = new InvokerNode();
		newNode.setType(attribs.getValue("type"));
		((ListenerNode)this.currNode).addInvoker(newNode);
	}
	
	private void handleListnerStart(org.xml.sax.Attributes attribs)
	{
		ListenerNode newNode = new ListenerNode(attribs.getValue("name"));
		newNode.setType(attribs.getValue("type"));
		
		System.out.println("ViewContentProvider::handlerListnerStart() - Got listener \'" + newNode.getName() + "\'");
		this.currNode = newNode;
	}
	
	private void handlePropertyStart(org.xml.sax.Attributes attrs)
	{
		
		PropertyNode propNode = new PropertyNode();
		for(int i = 0; i < attrs.getLength(); i++)
		{
			if(attrs.getQName(i).equals("name"))
			{
				propNode.setName(attrs.getValue(i));
			}
			else if(attrs.getQName(i).equals("value"))
			{
				propNode.setValue(attrs.getValue(i));
			}
		}
		//this.propsNode.addChild(propNode);
		//this.root.addChild(propNode);
		//this.propsNode.addChild(propNode);
		this.root.addProperty(propNode);
	}
	
	/** process an end element */
	public void endElement(String str, String str1, String tagName) throws SAXException {

		if(this.tagStack.size() == 0)
			currenttag = "";
		else
			currenttag = (String)this.tagStack.pop();
		System.out.println("ViewContentProvider::endElement() - Got a closing \'" + tagName + "\'");
		if(tagName.equals("properties"))
		{
			
		}
		else if(tagName.equals("listener"))
		{
			this.listenersNode.addChild((ListenerNode)this.currNode);
			System.out.println("ViewContentProvider::endElement() - Got listener end");
		}
		else if(tagName.equals("event-handler"))
		{
			if(this.currNode instanceof EventHandlerNode)
			{
				this.root.addChild(this.currNode);
				
			}
			else
			{
				System.err.println("Closing event-handler tag found that does not match an opener!");
			}
		}
		else if(tagName.equals("filter"))
		{
			handleFilterEnd();
		}
		else if(tagName.equals("plugin"))
		{
			handlePluginEnd();
		}
	}
	
	private void handlePluginEnd()
	{
		if(this.currNode instanceof PluginNode)
		{
			this.root.addChild(this.currNode);
		}
	}
	
	private void handleFilterEnd()
	{
		//TreeParent filterNode = (TreeParent)this.currNode;
		TreeParent filterNode = (TreeParent)this.nodeStack.pop();
		this.currNode = (TreeObject)this.nodeStack.pop();
		if(!(this.currNode instanceof EventHandlerNode))
		{
			System.err.println("\nViewContentProvider::handleFilterEnd() - Current stack item isn\'t an event handler");
			System.err.println("\nIt is a \'" + this.currNode.getClass().toString() + "\'");
			return;
		}
		((EventHandlerNode)this.currNode).addChild(filterNode);
	}
	
	/** process the start prefix */	
	public void startPrefixMapping(String str, String str1) throws SAXException {
	}
	
	
	
	public void startDocument() throws SAXException {;}
	/** process the end of the document */
	public void endDocument() throws SAXException {
		this.root.addChild(this.listenersNode);
		this.root.setName("Mach II Project");
		System.out.println("ViewContentProvider::endDocument() - Got end of document");
		//this.invisibleRoot = root;
	}
	public void ignorableWhitespace(char[] values, int param, int param2) throws SAXException {;}
	/** process the processing instructions */
	public void processingInstruction(String str, String str1) throws SAXException {;}
	public void skippedEntity(String str) throws SAXException {;}		
		
	}