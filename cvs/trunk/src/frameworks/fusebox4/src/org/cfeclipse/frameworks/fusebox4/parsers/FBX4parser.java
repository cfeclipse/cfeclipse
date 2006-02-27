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
		fl.getFilePath(this.fuseboxFile, this.altFuseboxFile);
		
		//IFile cirFile = project.getFile(fbxpath + fbxcircuits);
		//The finder should do the functions below
		
		if(!cirFile.exists()){ /* if we dont have an upper case */
			cirFile = project.getFile(fbxpath + fbxcircuits.toLowerCase());
			if(!cirFile.exists()){
				cirFile = null;
				Utils.println("FBX4parser:getCircuits: circuit file not found");
			}
		} 
		//Dont we get the root?
		//Create the application
		FBXApplication app = new FBXApplication(project.getProjectRelativePath().toString(), this.rootItem);
			if(cirFile != null){
				this.circuitFile = cirFile;
				app.setCircuitFile(cirFile);
				app.setChildren(getCircuits(project, cirFile));
			} else{
				app.setError("Circuits file not found");
			}
		app.setVersion(this.version);
		return app;
		
	}
	
	
	public FBXApplication parse(IProject project, IFile cirFile) {
		this.project = project;
		
		if(!cirFile.exists()){ /* if we dont have an upper case */
			cirFile = project.getFile(fbxpath + fbxcircuits.toLowerCase());
			if(!cirFile.exists()){
				cirFile = null;
				Utils.println("FBX4parser:getCircuits: circuit file not found");
			}
		} 
	
		//Create the application
			if(cirFile != null){
				this.circuitFile = cirFile;
				app.setCircuitFile(cirFile);
				app.setChildren(getCircuits(project, cirFile));
			} else{
				app.setError("Circuits file not found");
			}
		app.setVersion(this.version);
		return app;
		
	}
	
	
	/* 
	 * This method gets the child circuits from the main fusebox file.
	 * Before we go into the children, we should add the "suspected" path to the circuits.xml (.cfm)
	 * file
	 */
	public ArrayList getCircuits(IProject project, IFile cirFile) {
		//Go and get the file so that we can parse the doobie
		
		ArrayList circuits = new ArrayList();
		String pathToFile  = cirFile.getLocation().toString();
		read(pathToFile);
		
		String fullroot  = cirFile.getFullPath().toString();
		String fbxname = cirFile.getName().toString();
		String projectname = project.getName();

		int startAt = fullroot.length() - fbxname.length();
		String rootpath = fullroot.substring(projectname.length() + 1, startAt);
		
		Utils.println("new root path: "  + rootpath);
		
		
		
		
		
		if (document == null) {
			return circuits;
		}
		
		Element root = document.getDocumentElement();
		
		NodeList children = root.getElementsByTagName("circuit");
		Element circElement;
		//This loops through the circuits
		for (int i = 0; i < children.getLength(); i++)
	   {
			
			circElement = (Element)children.item(i);
			Utils.println("the element is" + circElement);
			FBXCircuit circ = new FBXCircuit(circElement.getAttribute("alias"), circElement.getAttribute("path"));
			
			//Get the swicth file (circuits.xml)'s Path
			
			String circPath = circElement.getAttribute("path");
			Utils.println("the circuit path" + rootpath);
			
			if (circPath.charAt(0)  == '/') circPath = circPath.substring(1,circPath.length());
			FileReader fl = new FileReader(rootpath + circPath, project);
			
			IFile switchFile = fl.getFilePath(fbxswitch, altfbxswitch);
			
			circuits.add(circ);
			circ.setVersion(this.version);
			circ.setProject(this.project);
     		circ.setCircuitFile(cirFile);
     		circ.setSwitchFile(switchFile);
     		circ.setChildren(getFuseactions(circ, project));
	    }
	   
	
		return circuits;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox3.views.application.IFBXparser#getFuseactions(org.cfeclipse.frameworks.fusebox3.views.application.FBXCircuit, org.eclipse.core.resources.IProject)
	 */
	public ArrayList getFuseactions(FBXCircuit circuit, IProject project) {
		ArrayList fuseactions = new ArrayList();
		//we know the path to the circuit file
		
		Utils.println("getFuseactions: " + circuit.getCircuitValue() +" "+ circuit.getSwitchFile());
		
		String circuitFile  = circuit.getCircuitFile().toString();
		String thepath = circuit.getCircuitValue();
		
		//Have to check what this file is, might be .cfm instead of just .xml
		circuitFile = circuitFile.substring(1, circuitFile.lastIndexOf("/") + 1) + thepath + "circuit.xml";
		
		//Since we previously added it we can get it instead
		
		
		
		//We create a resource that points to the file
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFile(circuit.getSwitchFile().getFullPath());
		
		//Go get the document
		//TODO: The problem here is that documents with 
		//
		Document switchdoc = readXML(file.getLocation().toFile());
		
		System.out.println("The switch file is : " + file.getLocation() + " and its " + readXML(file.getLocation().toFile()));
		
		//Here we now parse the XML, rather than by item.. we just do the nodes
		
		
		if (switchdoc == null) {
			return fuseactions;
		}
		ArrayList fuseacts = parseCircuits(switchdoc.getDocumentElement(), circuit); 
		return fuseacts;
		
	//	Element switchroot = switchdoc.getDocumentElement();
		
		/*
		
		NodeList children = switchroot.getElementsByTagName("fuseaction");
		Element switchElement;
		//Now we try and find the circuits!
		for (int s = 0; s < children.getLength(); s++)
	   {
			
			switchElement = (Element)children.item(s);
			FBXFuseAction fuseaction = new FBXFuseAction(switchElement.getAttribute("name"));
			
			fuseaction.setSwichFile(file);
			ArrayList fuses  = new ArrayList();
			
			NodeList includes = switchElement.getElementsByTagName("include");
			Element include;
			for(int i = 0; i < includes.getLength(); i++ ){
				include = (Element)includes.item(i);
				
				FBXFuse fuse = new FBXFuse(include.getAttribute("template"));
				fuse.setFusetype(fuse.getName().substring(0,3));
				fuse.setVersion(this.version);
				fuse.setParent(fuseaction);
				fuse.setSwitchFile(fuseaction.getSwichFile());
				fuse.setVersion(4);
				fuses.add(fuse);

			}
			
			NodeList dos = switchElement.getElementsByTagName("do");
			Element dose;
			for(int d = 0; d < dos.getLength(); d++ ){
				dose = (Element)dos.item(d);
				
				FBXDo doitem = new FBXDo(dose.getAttribute("action"));
				doitem.setVersion(this.version);
				doitem.setParent(fuseaction);
				doitem.setSwitchFile(fuseaction.getSwichFile());
				fuses.add(doitem);

			}
			NodeList ifs = switchElement.getElementsByTagName("if");
			Element ifel;
			for(int i = 0; i<ifs.getLength(); i++){
				ifel = (Element)ifs.item(i);
				FBXIf ifitem = new FBXIf("if:" + ifel.getAttribute("condition"));
				ifitem.setVersion(this.version);
				ifitem.setParent(fuseaction);
				ifitem.setSwitchFile(fuseaction.getSwichFile());
				fuses.add(ifitem);
				
			}
			
			
			
			fuseaction.setVersion(this.version);
			fuseaction.setChildren(fuses);
			fuseaction.setCircuit(circuit);
			//fuseaction.setTagStart(tag.getStartPosition());
			//fuseaction.setTagEnd(tag.getMatchingItem().getEndPosition());
			fuseactions.add(fuseaction);
			
			
	    }
		
		
		
		return fuseactions;*/
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox3.views.application.IFBXparser#getXFAs(org.cfeclipse.frameworks.fusebox3.views.application.FBXCircuit, org.eclipse.core.resources.IProject)
	 */
	
	public ArrayList getXFAs(FBXCircuit circuit, IProject project) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox3.views.application.IFBXparser#parse()
	 */
	public FBXApplication parse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox3.views.application.IFBXparser#parseCircuits(java.lang.String)
	 */
	public ArrayList parseCircuits(Element  fbxcircuits, FBXCircuit parent) {
		ArrayList fuseitems = null;
		NodeList nodes = fbxcircuits.getChildNodes();
		fuseitems = circuitIterator(nodes, parent);
		return fuseitems;
		
	}
	
	
	public ArrayList circuitIterator(NodeList nodes, IFBXObject parent){
		//New Array List to return
		ArrayList items = new ArrayList();
		FBXFactory factory = new FBXFactory();
		
		for(int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getNodeType() == Document.ELEMENT_NODE){
				//Find out what this item is and return it
				System.out.println(nodes.item(i).getNodeName());
				IFBXObject fbxo = factory.getObject(nodes.item(i).getNodeName());
				fbxo.setParent(parent);
				fbxo.setXmlNode(nodes.item(i));
					if(nodes.item(i).hasChildNodes()){
						fbxo.setChildren(circuitIterator(nodes.item(i).getChildNodes(), fbxo));
					}
				items.add(fbxo);
			}
		}
		
		return items;
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cfeclipse.frameworks.fusebox3.views.application.IFBXparser#parseXFA(java.lang.String)
	 */
	public ArrayList parseXFA(String xfafile) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Read a circuits file (might be used for other files too, we shall see
	 */
	public void read(String fileName) {
		this.circuitFile2 = new File(fileName);
		
		if (circuitFile2.exists()) {

			try {
				FileInputStream fis = new FileInputStream(circuitFile2);
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				// Clear up to the first '<'
				int t,p,q;
				bis.mark(0);
				if( ((char)bis.read()) != '<'){
					bis.reset();
					t = bis.read();
					p = bis.read();
					q = bis.read();	
				}else{
					bis.reset();
				}
				
				bis.mark(0);
				try {
					document = builder.parse(bis);
					//parseDocument();
				}
				catch (SAXException saxx){
					saxx.printStackTrace(System.err);
					
				}
				if(bis != null){
					bis.close();	
				}
				
			}
			catch (IOException iox) {
				iox.printStackTrace(System.err);
			}
			
		}
	}
	public Document readXML(File file){
		Document doc = null;
		File inFile =file;
		
		System.out.println("The file " + file + " is available?" + inFile.exists());
		if (inFile.exists()) {

			try {
				FileInputStream fis = new FileInputStream(inFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				// Clear up to the first '<'
				int t,p,q;
				bis.mark(0);
				if( ((char)bis.read()) != '<'){
					bis.reset();
					t = bis.read();
					p = bis.read();
					q = bis.read();	
				}else{
					bis.reset();
				}
				
				bis.mark(0);
				
				//Utils.println(Utils.getStringFromInputStream(bis));
				
				
				try {
					doc = builder.parse(bis);
					//parseDocument();
				}
				catch (SAXException saxx){
					Utils.println("Cant Parse the following" + Utils.getStringFromInputStream(bis));
					//saxx.printStackTrace(System.err);
					
				}
				if(bis != null){
					bis.close();
				}
			}
			catch (IOException iox) {
				iox.printStackTrace(System.err);
			}
			
		}
		
		return doc;
		
	}
		
}
