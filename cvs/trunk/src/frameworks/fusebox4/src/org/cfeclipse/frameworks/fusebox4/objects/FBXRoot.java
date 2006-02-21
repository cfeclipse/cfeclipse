/*
 * Created on Dec 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.frameworks.fusebox4.objects;

import java.util.ArrayList;


/**
 * @author markd
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FBXRoot extends FBXObject{
	private ArrayList children;
	public FBXRoot(String name) {
		children = new ArrayList();
	}
	public void addChild(IFBXObject child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(IFBXObject child) {
		children.remove(child);
		child.setParent(null);
	}
	public Object [] getChildren() {
		return (Object [])children.toArray(new IFBXObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
	public void addChildren(ArrayList achildren){
		children = achildren;
	}
	
}
