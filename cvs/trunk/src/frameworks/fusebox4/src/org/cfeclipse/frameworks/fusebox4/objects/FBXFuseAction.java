/*
 * Created on Dec 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;
import org.eclipse.core.resources.IFile;
import org.w3c.dom.Node;


/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXFuseAction extends FBXObject{
	private ArrayList children;
	private FBXCircuit circuit;
	private String name;
	private int tagStart = 0;
	private int tagEnd = 0;
	private IFile swichFile;
	private String icon = PluginImages.ICON_FBX4_FUSEACTION;
	
	
	public FBXFuseAction(){
		children = new ArrayList();
	}
	
	public FBXFuseAction(String name) {
		this.name = name;
		children = new ArrayList();
	}
	public void addChild(FBXFuse child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(FBXFuse child) {
		children.remove(child);
		child.setParent(null);
	}
	public Object [] getChildren() {
		return (IFBXObject [])children.toArray(new IFBXObject[children.size()]);
	}
	
	public boolean hasChildren() {
		return children.size()>0;
	}

	public void setChildren(ArrayList children) {
		this.children = children;
	}
	
	/**
	 * @return Returns the circuit.
	 */
	public FBXCircuit getCircuit() {
		return circuit;
	}
	/**
	 * @param circuit The circuit to set.
	 */
	public void setCircuit(FBXCircuit circuit) {
		this.circuit = circuit;
	}
	/**
	 * @return Returns the tagEnd.
	 */
	public int getTagEnd() {
		return tagEnd;
	}
	/**
	 * @param tagEnd The tagEnd to set.
	 */
	public void setTagEnd(int tagEnd) {
		this.tagEnd = tagEnd;
	}
	/**
	 * @return Returns the tagStart.
	 */
	public int getTagStart() {
		return tagStart;
	}
	/**
	 * @param tagStart The tagStart to set.
	 */
	public void setTagStart(int tagStart) {
		this.tagStart = tagStart;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		
		return name;
	}
	public String toString(){
		String name = this.getXmlNode().getAttributes().getNamedItem("name").getNodeValue().toString();
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the swichFile.
	 */
	public IFile getSwichFile() {
		return swichFile;
	}
	/**
	 * @param swichFile The swichFile to set.
	 */
	public void setSwichFile(IFile swichFile) {
		this.swichFile = swichFile;
	}

	public String getIcon() {
		
		return this.icon;
	}
	
}
