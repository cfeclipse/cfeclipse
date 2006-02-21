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
import org.w3c.dom.Node;

import com.rohanclan.cfml.editors.actions.GenericEncloserAction;

/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXApplication implements IFBXObject{
	private ArrayList children;
	private String name;
	private IFile fuseboxFile;
	private String appRootPath;
	private String icon =  PluginImages.ICON_FUSEBOX4;
	private IFBXObject parent;
	
	public FBXApplication(String name, IFBXObject parent) {
		this.name = name;
		children = new ArrayList();
		initFusebox();
	}
	/**
	 * This method goes and gets the actual fusebox.xml.cfm or fusebox.xml file and parses it, creating children on the first level
	 */
	private void initFusebox(){
		
		
	}
	
	/**
	 * This method gets the right fusebox file
	 */
	private IFile getFuseboxFile(){
		
		return null;
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
	
	

	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setVersion(int version) {
		// TODO Auto-generated method stub
		
	}

	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setError(String error) {
		// TODO Auto-generated method stub
		
	}

	public void setError(int errorid) {
		// TODO Auto-generated method stub
		
	}

	public int getErrorid() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setErrorid(int errorid) {
		// TODO Auto-generated method stub
		
	}

	public String getFusedoc() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFusedoc(String fusedoc) {
		// TODO Auto-generated method stub
		
	}

	public String getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setExtension(String extension) {
		// TODO Auto-generated method stub
		
	}

	public void addListener(IDeltaListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void removeListener(IDeltaListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void addChild(FBXFuse child) {
		// TODO Auto-generated method stub
		
	}

	public void removeChild(FBXFuse child) {
		// TODO Auto-generated method stub
		
	}

	public Node getXmlNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setXmlNode(Node xmlNode) {
		// TODO Auto-generated method stub
		
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		// TODO Auto-generated method stub
		
	}

	public IFBXObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParent(IFBXObject parent) {
		// TODO Auto-generated method stub
		
	}

	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
