/*
 * Created on 	: 23-Sep-2004
 * Created by 	: Mark Drew
 * File		  	: FBX3parser.java
 * Description	: I am a util class that simply parses strings passed to it and returns some hashmaps
 * 
 * The regEx here was created by simeon@simb.net, Much KUDOS!
 */
//I couldnt make it work with the packages.
//So i comemented it out.
//packageorg.cfeclipse.cfml.views.fusebox3;

package org.cfeclipse.frameworks.fusebox.parsers;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cfeclipse.frameworks.fusebox.objects.FBXApplication;
import org.cfeclipse.frameworks.fusebox.objects.FBXCircuit;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuse;
import org.cfeclipse.frameworks.fusebox.objects.FBXFuseAction;
import org.cfeclipse.frameworks.fusebox.objects.FBXxfa;
import org.cfeclipse.frameworks.fusebox.objects.XFAFolder;
import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.CFParser;
import org.cfeclipse.cfml.parser.docitems.CfmlTagItem;
import org.cfeclipse.cfml.parser.docitems.DocItem;

public class FBX3parser {
	
    public HashMap circuits;
    public HashMap fuseactions;
    private IProject project;
    private IFile circuitFile;
    private static Pattern pattern;
    private static Matcher matcher;
    private static boolean found;
    private FBXApplication fbxapp;
    private String extension = ".cfm";
	private String fbxswitch = "fbx_Switch" + extension;
	private String fbxcircuits = "fbx_Circuits" + extension;
    private String fbxpath = "/";
    private int version = 3;
    /**
	 * Blank Constructor Use this if you will define the project later when you parse
	 */
	public FBX3parser() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * You can define the project in this constructor and then you just parse it normally
	 * @param project
	 */
	public FBX3parser(IProject project) {
		super();
		this.project = project;
		//set the switch and circuit defaults
		try {
			this.fbxswitch = project.getPersistentProperty(new QualifiedName("", "FBXSWITCHFILE"));
			this.fbxcircuits = project.getPersistentProperty(new QualifiedName("", "FBXCIRCUITFILE"));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
	
	/*public FBXApplication parse(){
		//We start here, it returns you a full FBX Application, somehow
		//some of the crappy - pre new preferences panel code
		QualifiedName qname = new QualifiedName("", "FBXROOTPATH");
		try {
			Utils.println("FBX3parser:: Setting the path");
			this.fbxpath = project.getPersistentProperty(qname) + "/";
		} catch (CoreException e) {
			this.fbxpath = "/";
			e.printStackTrace();
		}
		
		//Create a blank application obj
		FBXApplication app = new FBXApplication(this.project.getName());
		
		app.setChildren(getCircuits(this.project));
		this.fbxapp = app;
		return app;
	}*/
	
	
	
	
	/**
	 * Pass in the project for which you want to get an application for
	 * @param project
	 * @return
	 */
	public FBXApplication parse(IProject project){
		this.project = project;
		
		QualifiedName qname = new QualifiedName("", "FBXROOTPATH");
		try 
			{
			String thispath =  project.getPersistentProperty(qname);
			if(thispath != null){
				if(!thispath.equals("/")){
					this.fbxpath = thispath + "/";
				}
			}
			Utils.println("FBX3parser:parse: Setting the path to " + this.fbxpath);
		} catch (CoreException e) {
			this.fbxpath = "/";
			e.printStackTrace();
		}
		
		IFile cirFile = project.getFile(fbxpath + fbxcircuits);
		
			if(!cirFile.exists()){ /* if we dont have an upper case */
				cirFile = project.getFile(fbxpath + fbxcircuits.toLowerCase());
				if(!cirFile.exists()){
					cirFile = null;
					Utils.println("FBX3parser:getCircuits: circuit file not found");
				}
			} 
		
		FBXApplication app = new FBXApplication(project.getName());
		this.fbxapp = app;
		if(cirFile != null){
			setCircuitFile(cirFile);
			app.setCircuitFile(cirFile);
		//get the children/Circuits 
			ArrayList circuits = getCircuits(project, cirFile);
			String fusedoc = FusedocParser.getFusedoc(project, cirFile);
		
		app.setChildren(circuits);
		} else{
			app.setError(1);
		}
		return app;
	}
	
	public FBXApplication parse(IProject project, IFile cirFile){
		this.project = project;
		FBXApplication app = new FBXApplication(project.getName());
		//Set the root path for this app so we can do something like parent.getrootpath()
		//get the path from the file
		
		
		
		if(cirFile != null){
			this.fbxpath = cirFile.getParent().getProjectRelativePath().toString();
			app.setAppRootPath(fbxpath);
			setCircuitFile(cirFile);
			app.setCircuitFile(cirFile);
			//Just call it for this.. as we have to click into the rest
			app.setFusedoc(FusedocParser.getFusedoc(project, cirFile));
			//get the children/Circuits 
			app.setChildren(getCircuits(project, cirFile));
		} else{
			app.setError(1);
		}
		return app;
	}
	
	public ArrayList getCircuits(IProject project, IFile cirFile){
		
		ArrayList circuits = new ArrayList();
		String circuitFile = "";
		InputStream is = null;
		StringBuffer cleanCircuits = new StringBuffer();
		//Here we go and get the circuit file
				
		
		/*sanity check if the circuit file exits */
		if(cirFile.exists()){
			try {
				is = cirFile.getContents();
				circuitFile = Utils.getStringFromInputStream(is);
			} catch (CoreException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
			
			//Now lets parse it
			CFParser parser = new CFParser(circuitFile, cirFile);
			
			
			try{
				CFDocument elparso =  parser.parseDoc(circuitFile);
			
			//CFDocument parsedDoc = parser.getParseResult();
			
			if(elparso != null){
				DocItem rootItem = elparso.getDocumentRoot();
				//Find the CFScript Items 
				CFNodeList circuitscripts = rootItem.selectNodes("//cfscript");
				if(circuitscripts != null){
					Iterator scriptIter = circuitscripts.iterator();
					
					while(scriptIter.hasNext()){
						CfmlTagItem tag = (CfmlTagItem)scriptIter.next();
						//Get the full contents, 
						String tagContents = circuitFile.substring(tag.getStartPosition(), tag.getEndPosition());
						cleanCircuits.append(tagContents);
						
					}
				}
				
				//Now add all the cfset items
				CFNodeList circuitsets = rootItem.selectNodes("//cfset");
				if(circuitsets != null){
					Iterator setIter = circuitsets.iterator();
					
					while(setIter.hasNext()){
						CfmlTagItem tag = (CfmlTagItem)setIter.next();
						//Get the full contents, 
						String tagContents =  tag.getItemData();
						cleanCircuits.append(tagContents);
						
					}
				}
				//Now that we have them all cleaned up we can parse it and return the array list
				circuits = parseCircuits(cleanCircuits.toString());
				
				
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		try {
			if(is != null){
			is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return circuits;
	}
	
	
    /**
     * This method returns a HashMap of key/value pairs of the circuits used so if we pass in 
     * <code>
     * <cfscript>
     * 	fusebox.circuit.home 		= "home";
     *  fusebox.circuit.services 	= "home/services";
     * 	fusebox.curcuit.products	= "home/products";
     * </cfscript>
     * </code>
     * or
     * <code>
     * <cfset fusebox.circuit.home = "home">
     * <cfset fusebox.circuit.services 	= "home/services">
     * <cfset fusebox.curcuit.products	= "home/products">
     * </code>
     *  it should return
     * 	key			value
     * 	home		home
     * 	services	/services
     * 	producrs	/products
     * @param fbxcircuits
     * @return 
     */
	
    public ArrayList parseCircuits(String fbxcircuits){
        
         /* Do some RegEx to get the circuits.. shoudld be as "automatic" as possible in the sense that it deals with 
          <cfset> or <cfscript> versions
          */
    	
    	/* 
    	 * Not sure a HashMap is the best.. maybe a set or an array 
    	 */
    		ArrayList arrlist = new ArrayList();
    		String rootcirc = "";
    		pattern = Pattern.compile(".([\\w])*(\\s)*=(\\s)*\"((([^/\"]*)/)*([^/\"]*))\"");
    		matcher = pattern.matcher(fbxcircuits);
        
    		//This is a fake.. but we shall make the first item a root
    		boolean itemRoot = true;
        while (matcher.find() ) {
        		
        		StringTokenizer st = new StringTokenizer(matcher.group(),"=");
        		//assume we get .circuitname = "path/to/circuit" back from our RE
        		String circuitName = st.nextToken().substring(1).trim(); //Store first have returned from FE
         	String fullPath = st.nextToken().trim(); //Store path with "'s around
         	String circuitPath = fullPath.substring(1,fullPath.length()-1); //get path without "'s
        	
         	String root;
         	if(itemRoot){
         		root = " [root]";
         		rootcirc = circuitPath;
         		
         	} else{
         		root = "";
         	}
         	
         	
         	FBXCircuit circ = new FBXCircuit(circuitName + root, circuitPath.replaceFirst(rootcirc, ""));
         		
         		
         		circ.setProject(this.project);
         		circ.setCircuitFile(getCircuitFile());
         	
         		// this should work?! circ.setParent(this.fbxapp);
         		//Before we add any XFA's we should check if there are some, if so we add a folder
         		
         		XFAFolder xfas = new XFAFolder();
         			FBXxfa myxfa = new FBXxfa("Test", "contiky");
         			myxfa.setParent(xfas);

         		//xfas.addChild(myxfa);
         		    		
         		//xfas.addChildren(getXFAs(circ, this.project));
         		
         		//circ.addChild(xfas);   
         		circ.addChildren(getFuseactions(circ, this.project));
         		circ.setRoot(itemRoot);
         	
         		
         	arrlist.add(circ);
         		
        		//circuits.put(circuitName.toString(),circuitPath.toString() );
         	//I got an error when I use the one you defined. not sure what I did wrong.
         	itemRoot = false;
         	found = true;
        }
        return arrlist;
        
    }
    public ArrayList getXFAs(FBXCircuit circuit, IProject project){
    	
    	ArrayList xfas = new ArrayList();
    	IFile switchFile = project.getFile(fbxpath + circuit.getCircuitValue() + "/" + fbxswitch);
    	
    	if(!switchFile.exists()){
    		switchFile = project.getFile(fbxpath + circuit.getCircuitValue() + "/" + fbxswitch.toLowerCase());
    	}
    	
    	
    	
    	
    	String switchContents = "";
    	InputStream is= null;
    	
    	if(switchFile.exists()){
    		
    		
    		try {
				is = switchFile.getContents();
				switchContents = Utils.getStringFromInputStream(is);
				
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			xfas = parseXFA(switchContents);
			Utils.println(xfas);
    	}
    	
    	return xfas;
    }
    
    public ArrayList parseXFA(String xfafile){
        
         /* Do some RegEx to get the XFAs
          * .. shoudld be as "automatic" as possible in the sense that it deals with cfset and cfscript
          * 
          */
    	
    		ArrayList arrlist = new ArrayList();
    		String rootcirc = "";
    		pattern = Pattern.compile("XFA.([\\w])*(\\s)*=(\\s)*\"((([^/\"]*)/)*([^/\"]*))\"");
    		matcher = pattern.matcher(xfafile);
        
    		//This is a fake.. but we shall make the first item a root
    	
        while (matcher.find() ) {
        		
        		StringTokenizer st = new StringTokenizer(matcher.group(),"=");
        		//assume we get .circuitname = "path/to/circuit" back from our RE
        		String xfaName = st.nextToken().substring(1).trim(); //Store first have returned from FE
        		String fullPath = st.nextToken().trim(); //Store path with "'s around
        		String circuitPath = fullPath.substring(1,fullPath.length()-1); //get path without "'s
        	
        		
        		FBXxfa xfa = new FBXxfa(xfaName, circuitPath);
         	
        		arrlist.add(xfa);
        		
         	found = true;
        }
        return arrlist;
        
    }
    
    
    public ArrayList getFuseactions(FBXCircuit circuit, IProject project){
    	ArrayList fuseactions = new ArrayList();
    	IFile switchFile = project.getFile(fbxpath + circuit.getCircuitValue() + "/" + fbxswitch);
    	
    	if(!switchFile.exists()){
    		switchFile = project.getFile(fbxpath + circuit.getCircuitValue() + "/" + fbxswitch.toLowerCase());
    		if(!switchFile.exists()){
    			circuit.setError(2);
    			circuit.setError("Switch File Not Found" + switchFile.getLocation().toString());
    			
    		}
    	}
    	
    	String switchContents = "";
    	InputStream is= null;
    	
    	if(switchFile.exists()){
    		circuit.setSwitchFile(switchFile);
    		try {
				is = switchFile.getContents();
				switchContents = Utils.getStringFromInputStream(is);
				
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//Now lets parse it
			CFParser parser = new CFParser(switchContents, switchFile);
			
			try{
				CFDocument swtichDoc =  parser.parseDoc(switchContents);
				
				if(swtichDoc != null){
					DocItem rootItem = swtichDoc.getDocumentRoot();
					CFNodeList switchItems = rootItem.selectNodes("//cfcase");
					Iterator switchIter = switchItems.iterator();
					
					while(switchIter.hasNext()){
						CfmlTagItem tag = (CfmlTagItem)switchIter.next();
						if(tag != null){
							FBXFuseAction fuseaction = new FBXFuseAction(tag.getAttributeValue("value"));
							fuseaction.setSwichFile(switchFile);
							ArrayList fuses = getFuses(tag, fuseaction);
							fuseaction.setChildren(fuses);
							fuseaction.setCircuit(circuit);
							fuseaction.setTagStart(tag.getStartPosition());
							fuseaction.setTagEnd(tag.getMatchingItem().getEndPosition());
							fuseactions.add(fuseaction);
						}
					}
				}
			} catch(Exception e){
				Utils.println("There has been an error parsing the switch file");
				e.printStackTrace();
			}
    		
    	}
    	else{
    		Utils.println("not got the switch");
    		
    	}
    	
    	try {
    		if(is != null){
    			is.close();
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return fuseactions;
    }
    public ArrayList getFuses(CfmlTagItem tag, FBXFuseAction fuseaction){
    	ArrayList fuses = new ArrayList();
    	CFNodeList nodes = tag.getChildNodes();
    	
    	Iterator nodeIter = nodes.iterator();
    	
    	while(nodeIter.hasNext()){
    		Object childObj = nodeIter.next();
    		if(childObj instanceof CfmlTagItem){
    			CfmlTagItem childTag = (CfmlTagItem)childObj;
    			if(childTag.getName().equals("cfinclude")){
    				
    				FBXFuse fuse = new FBXFuse(childTag.getAttributeValue("template"));
    				fuse.setFusetype(fuse.getName().substring(0,3));
    				fuse.setParent(fuseaction);
    				fuse.setSwitchFile(fuseaction.getSwichFile());
    				fuses.add(fuse);
    				
    			} else if (childTag.getName().equals("cfmodule")){
    				
    				FBXFuse fuse = new FBXFuse(childTag.getAttributeValue("template"));
    				fuse.setFusetype(fuse.getName().substring(0,3));
    				fuse.setIsModule(true);
    				fuse.setParent(fuseaction);
    				fuse.setSwitchFile(fuseaction.getSwichFile());
    				fuses.add(fuse);
    				
    			}
    		}
    		
    	}
        /*This returns a list of the included files. somewhere we work out the path*/
        return fuses;
    }
    
    
       
	/**
	 * @return Returns the circuitFile.
	 */
	public IFile getCircuitFile() {
		return circuitFile;
	}
	/**
	 * @param circuitFile The circuitFile to set.
	 */
	public void setCircuitFile(IFile circuitFile) {
		this.circuitFile = circuitFile;
	}
	
	/**
	 * @return Returns the fbxpath.
	 */
	public String getFbxpath() {
		return fbxpath;
	}
	/**
	 * @param fbxpath The fbxpath to set.
	 */
	public void setFbxpath(String fbxpath) {
		this.fbxpath = fbxpath;
	}
}