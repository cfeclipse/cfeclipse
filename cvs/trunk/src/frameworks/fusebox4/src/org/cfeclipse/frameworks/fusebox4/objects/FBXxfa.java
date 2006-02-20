/*
 * Created on 21-Dec-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FBXxfa extends FBXObject {

	private String name;

	private String value;
	private XFAFolder parent;

	private int tagStart = 0;

	private int tagEnd = 0;
	private String icon = PluginImages.ICON_FBX_XFA;

	/**
	 * @param name
	 */
	public FBXxfa(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public FBXxfa(){
		
	}

	public String toString(){
		String label = this.name + ":" + this.value;
		return label;
	}
	/**
	 * @param parent
	 *            The parent to set.
	 */
	public void setParent(XFAFolder parent) {
		this.parent = parent;
	}

	/**
	 * @return Returns the tagEnd.
	 */
	public int getTagEnd() {
		return tagEnd;
	}

	/**
	 * @param tagEnd
	 *            The tagEnd to set.
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
	 * @param tagStart
	 *            The tagStart to set.
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

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}