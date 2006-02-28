/*
 * Created on 10-Jan-2005
 *
 */
package org.cfeclipse.frameworks.fusebox4.parsers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.cfeclipse.frameworks.fusebox4.objects.*;
import org.cfeclipse.frameworks.fusebox4.util.FileReader;
import org.cfeclipse.frameworks.fusebox4.util.Utils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @author Mark Drew
 * TODO: Use http://java.sun.com/j2se/1.4.2/docs/api/org/xml/sax/Locator.html
 *       So that we can find the file location. 
 * 
 */
public class FBX4parser {

	protected javax.xml.parsers.DocumentBuilderFactory factory;
	protected javax.xml.parsers.DocumentBuilder builder;

	private Document document=null;
	private IProject project;
	private FBXRoot rootItem;

	//The files below sometimes are called without the .cfm extension. Try using the file reader for the options
	private String scriptFileDelimiter = "cfm";
	private String fuseboxFile = "fusebox.xml";
	private String circuitFile = "circuit.xml";
	private String altFuseboxFile = "fusebox.xml" + "." + scriptFileDelimiter;
	private String altCircuitFile = "circuit.xml" + "." + scriptFileDelimiter;
	
	private String fbxpath = "/";
    private int version = 4;
    //These are properties from the fusebox.xml.cfm file
    //I need to add a check for different versions of the fusebox.xml.cfm file
    
   File snippetFile;
	
	/**
	 * The constructor
	 */
	public FBX4parser() {
		super();
		//Set up the xml factory and document builder
		try
		{
		factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setCoalescing(true);
		builder = factory.newDocumentBuilder();	
		}catch(ParserConfigurationException pce)
		{
			//bah!
			pce.printStackTrace(System.err);
		}
	}
	
	public FBXApplication parse(IProject project, FBXRoot rootItem) {
		this.project = project;
		this.rootItem = rootItem;
		
		//Get the assigned root path
		QualifiedName qname = new QualifiedName("", "FBXROOTPATH");
		try 
			{
			String thispath =  project.getPersistentProperty(qname);
			if(thispath != null){
				this.fbxpath = thispath + "/";
			}
		} catch (CoreException e) {
			this.fbxpath = "/";
			e.printStackTrace();
		}
		
		//Get the root fusebox.xml.cfm file
		//This is where we call the file reader.
		
		FileReader fl = new FileReader(fbxpath, project);
		IFile actFuseboxFile = fl.getFilePath(this.fuseboxFile, this.altFuseboxFile);
		Document doc = builder.newDocument();
		String fuseboxContents = "";

		try {
			fuseboxContents = Utils.getStringFromInputStream(actFuseboxFile.getContents());
			doc = builder.parse(actFuseboxFile.getContents());
			
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException io){
			io.printStackTrace();
		} catch (SAXException se){
			se.printStackTrace();
		}
		
		//Get the file contents
		String fbxContents = "";
		FBXApplication app = new FBXApplication(this.fbxpath, rootItem);
		Element rootElement = doc.getDocumentElement();
		NodeList nodes = rootElement.getChildNodes();
		app.setChildren(parseXML(nodes, app));
		
		//Return a token FBXApplication
		app.setName(this.project.getName());
		
		return app;
		
	}
	
	private ArrayList parseXML(NodeList nodes, IFBXObject parent){
		ArrayList children = new ArrayList();
		//here we recurse
		for(int i = 0; i < nodes.getLength(); i++){
			//Call the factory to create a new object, which will be the parent when called
			Node thisnode =(Node)nodes.item(i); 
			
			
			
			if(thisnode.getNodeType() == 1){
				Utils.println(thisnode.getNodeName() + " " + thisnode + " " + thisnode.getNodeType());
				Utils.println("---------");
				FBXFactory fbxfactory = new FBXFactory();
				
				IFBXObject newParent = fbxfactory.getObject(thisnode.getNodeName());
							
				newParent.setName(thisnode.getNodeName());
				newParent.setParent(parent);
				newParent.setXmlNode(thisnode);
				
				if(thisnode.getNodeName().equalsIgnoreCase("circuit")){
					//--------------------------------
					//Go get the circuitfile
					//Determine the path 
					String circuitPath = thisnode.getAttributes().getNamedItem("path").getNodeValue();
					circuitPath = fbxpath + circuitPath;
					FileReader fl = new FileReader(circuitPath, project);
					IFile actFuseboxFile = fl.getFilePath(this.circuitFile, this.altCircuitFile);
					Document doc = builder.newDocument();
					String circuitContents = "";
					try {
						circuitContents = Utils.getStringFromInputStream(actFuseboxFile.getContents());
						doc = builder.parse(actFuseboxFile.getContents());
					} catch (CoreException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ArrayList circuitChildren = new ArrayList();
					circuitChildren = parseXML(doc.getDocumentElement().getChildNodes(), newParent);
					newParent.setChildren(circuitChildren);
					//---------------------------------
				}
				
				
				
				
				if(thisnode.hasChildNodes()){
					//Create a new object generic objects.
					ArrayList newChildren = new ArrayList();
					//If we are a circuit, we can get a new document circuit.xml.cfm and parse those.
					
						newChildren = parseXML(thisnode.getChildNodes(), newParent);
					newParent.setChildren(newChildren);
				}
				children.add(newParent);
			}
		}
		
		return children;
	}
	
	
}
