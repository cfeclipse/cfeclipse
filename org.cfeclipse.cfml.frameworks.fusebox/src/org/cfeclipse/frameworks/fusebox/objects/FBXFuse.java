/*
 * Created on Dec 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox.objects;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;
import org.cfeclipse.frameworks.fusebox.util.Utils;
import org.eclipse.core.resources.IFile;

/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXFuse extends FBXObject {
	private String name;
	private String fusetype;
	private FBXFuseAction parent;
	private boolean isModule = false;
	private IFile switchFile;
	private String icon = PluginImages.ICON_FBX4_FUSE;

	
	
	/**
	 * @return Returns the isModule.
	 */
	public boolean isModule() {
		return isModule;
	}
	/**
	 * @param isModule The isModule to set.
	 */
	public void setIsModule(boolean isModule) {
		this.isModule = isModule;
	}
	public FBXFuse(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public String getIcon() {
		return this.icon;
	}
	public void setParent(FBXFuseAction parent) {
		this.parent = parent;
	}
	public String toString() {
		return getName();
	}
	public Object getAdapter(Class key) {
		return null;
	}
	/**
	 * @return Returns the fusetype.
	 */
	public String getFusetype() {
		return fusetype;
	}
	/**
	 * @param fusetype The fusetype to set.
	 */
	public void setFusetype(String fusetype) {
		this.fusetype = fusetype;
	}
	/**
	 * @return Returns the switchFile.
	 */
	public IFile getSwitchFile() {
		return switchFile;
	}
	/**
	 * @param switchFile The switchFile to set.
	 */
	public void setSwitchFile(IFile switchFile) {
		this.switchFile = switchFile;
	}
	
	/** 
	 * This function returns the path of this item relative to the path of the switch file that is called from
	 * 
	 * @return
	 */
	public String getPath(){
		//This seems to work for fusebox 3
		String switchpath = "";
		switchpath = getSwitchFile().getProjectRelativePath().toString();
		int lastslash = switchpath.lastIndexOf("/");
		if ( lastslash == -1 ) {
			switchpath = "";
		} else {
			switchpath = switchpath.substring(0, lastslash) + "/";
		}
	
		if(this.getVersion() == 3){
			switchpath = switchpath + name;
		}
		else{
			//check it doesnt already end with .cfm
			String fileending = name.substring(name.length()-4, name.length());
			Utils.println("The extension is = " + fileending);
			if(fileending.equalsIgnoreCase(".cfm")){
				switchpath = switchpath + name;
			} else{
				switchpath = switchpath + name + "." + getExtension();
			}
			
			
			
		}
		
		return switchpath;
	}
	
}