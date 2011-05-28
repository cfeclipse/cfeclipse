/*
 * Created on Dec 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox.views;

import org.cfeclipse.frameworks.fusebox.FuseboxPlugin;
import org.cfeclipse.frameworks.fusebox.objects.*;
import org.cfeclipse.frameworks.fusebox.parsers.FBX3parser;
import org.cfeclipse.frameworks.fusebox.parsers.FBX4parser;
import org.cfeclipse.frameworks.fusebox.properties.FuseboxPreferenceConstants;
import org.cfeclipse.frameworks.fusebox.util.FileReader;
import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


class FBXViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
	private FBXRoot invisibleRoot;
	private String project;
	private String extension = ".cfm";
	private String fbxswitch = "fbx_Switch" + extension;
	private String fbx3circuitroot = "fbx_Circuits" + extension;
	private String fbx4circuitroot = "fusebox.xml";
	private String fbx4altcirciutroot = "fusebox.xml" + extension;
	
	
	
	/**
		 * Pass in the Project that we are in
		 */
		public FBXViewContentProvider(String project) {
			this.project = project;
		}
		

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			//if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
			
				return getChildren(invisibleRoot);
			//}
			//return getChildren(parent);
		}
		public Object getParent(Object child) {
			
			if (child instanceof FBXFuse) {
				return ((FBXFuse)child).getParent();
			} 
			else if(child instanceof FBXIf){
				return((FBXIf)child).getParent();
			}
			else if(child instanceof FBXFuseAction){
				return((FBXFuseAction)child).getParent();
			} else if (child instanceof FBXCircuit){
				return((FBXCircuit)child).getParent();
			} else if (child instanceof FBXApplication){
				return((FBXApplication)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			System.out.println("Getting children of: " + parent.getClass().toString());
			if (parent instanceof FBXRoot) {
				return ((FBXRoot)parent).getChildren();
			}
			else if (parent instanceof FBXApplication) {
				return ((FBXApplication)parent).getChildren();
			}
			else if (parent instanceof FBXCircuit) {
				return ((FBXCircuit)parent).getChildren();
			} 
			else if (parent instanceof FBXFuseAction){
				return((FBXFuseAction)parent).getChildren();
			}
			else if (parent instanceof FBXIf){
				return((FBXIf)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof FBXApplication){
				return ((FBXApplication)parent).hasChildren();
			}
			else if (parent instanceof FBXCircuit){
				return ((FBXCircuit)parent).hasChildren();
			} 
			else if (parent instanceof FBXFuseAction){
				return ((FBXFuseAction)parent).hasChildren();
			}
			else if (parent instanceof FBXIf){
				return ((FBXIf)parent).hasChildren();
			}
			return false;
		}

		private void initialize(){
			//Gets the selected project
			// TODO: remember the project selection
			IProject project =  FuseboxPlugin.getWorkspace().getRoot().getProject(this.project);
			//Convert this to a factory. so you would have a base class FBXParser
			FBX4parser parser4 = new FBX4parser();
			FBX3parser parser3 = new FBX3parser(); 
			invisibleRoot = new FBXRoot("");
			FBXApplication app = null;
			boolean version3 = true;
			boolean version4 = false;
			
			//Get the root of the project
			//This also happens within the parser, so we might want to send it
			String rootpath = "/";  //default root of the fusebox application
			QualifiedName qname = new QualifiedName("", FuseboxPreferenceConstants.P_FUSEBOX_PATH);
			try 
			{
				
				String thispath =  project.getPersistentProperty(qname);
				if(thispath != null){ 
					if(!thispath.equals(rootpath)){
						rootpath = thispath + "/";
					}
				}
			
			} catch (CoreException e) {
					e.printStackTrace();
			}
			
			/**
			 * TODO: Make this into a factory. That is how we find out what version of Fusebox
			 * we are running
			 */
			//Here we shall get either fbx_circuits.cfm or fusebox.xml.cfm
			//Lets try first version 3
			IFile cirFile = project.getFile(rootpath + fbx3circuitroot);
			Utils.println("fusebox 3 root" + rootpath + fbx3circuitroot);
			Utils.println("does the fb3 exist?" + cirFile.exists());
			
				if(!cirFile.exists()){ /* if we dont have an upper case */
					cirFile = project.getFile(rootpath + fbx3circuitroot.toLowerCase());
					if(!cirFile.exists()){
						cirFile = null;
						version3 = false;
					} else {
						version4 = false;
						version3 = true;
					}
					
				} 
				
				FileReader fb4reader = new FileReader(rootpath, project);
				IFile fuseboxFile = fb4reader.getFilePath(this.fbx4circuitroot, this.fbx4altcirciutroot);
				
				if(!version3){
						if((fuseboxFile !=null) && fuseboxFile.exists()){
						/* if we dont find it (lowercase) */
							version4 = true;
						}
				}
				
				
		
			if(version4){
				app = parser4.parse(project, fuseboxFile);
				
			}
			else{
				app = parser3.parse(project, cirFile);	
				
			}
		
			//An FBXApplication is a a tree, the parser returns this tree from a project (drop down right?) 
			
			if(app != null){
			invisibleRoot.addChild(app);
			}
			
			
		}
	
}