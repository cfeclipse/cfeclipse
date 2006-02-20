/*
 * Created on Dec 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox4.actions.OpenFileAction;
import org.cfeclipse.frameworks.fusebox4.util.PluginImages;
import org.cfeclipse.frameworks.fusebox4.util.Utils;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.PlatformUI;

import com.rohanclan.cfml.editors.actions.GenericEncloserAction;

/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXApplication extends FBXObject{
	private ArrayList children;
	private String name;
	private IFile circuitFile;
	private String appRootPath;
	private String icon =  PluginImages.ICON_FUSEBOX4;
	
	
	
	public FBXApplication(String name) {
		this.name = name;
		children = new ArrayList();
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
	
	
	
	public void addChild(FBXCircuit child) {
		children.add(child);
		child.setParent(this);
	}
	public void addCircuit(FBXCircuit child){
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(FBXCircuit child) {
		children.remove(child);
		child.setParent(null);
	}
	
	public Object [] getChildren() {
		return (Object [])children.toArray(new FBXCircuit[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
	/**
	 * @param children The children to set.
	 */
	public void setChildren(ArrayList children) {
		this.children = children;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	public String toString(){
		if(this.hasError()){
			return this.getError();
		} else {
			return getName() + " (FBX Ver: " + this.getVersion() + ")";
		}
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the appRootPath.
	 */
	public String getAppRootPath() {
		return appRootPath;
	}
	/**
	 * @param appRootPath The appRootPath to set.
	 */
	public void setAppRootPath(String appRootPath) {
		this.appRootPath = appRootPath;
	}
	
	/**
	 * This function adds the right files to the file system and whould
	 * "pre-render" it to add an item
	 * @param circuit
	 */
	public void addCircuit(String name, String path, boolean createFolders){
		/*
		 * Actions:
		 *  1. Check there is a Circuits file
		 * 	2. Add entry to circuits file
		 *  3. Create subfolder with default files 
		 * 
		 */
//		1.Check there is a Circtuis file
		if(getCircuitFile() !=null){
			//We have a circuits file, lets open it, and add something along the lines of
			// <cfset f
			OpenFileAction ofa = new OpenFileAction();
			ofa.setFile(getCircuitFile());
			ofa.run();
			
			//Need to insert something at the END of the document
			GenericEncloserAction gea = new GenericEncloserAction();
			gea.setActiveEditor(null, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor());
			gea.setEnclosingStrings("\n<cfset fusebox.circuits." + name + " = \"" + path + "\">\n"," ");
			gea.run();
			
			//Here we could parse it then
			
		Utils.println("<cfset fusebox.circuits." + name + " = \"" + path + "\">");
		Utils.println("create circuit: " + name + " in " + path + " and create circuit files?"  + createFolders);
		
		
		}
		
	}
}
