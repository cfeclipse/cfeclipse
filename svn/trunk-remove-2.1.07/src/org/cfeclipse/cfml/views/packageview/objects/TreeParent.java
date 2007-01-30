package org.cfeclipse.cfml.views.packageview.objects;

import java.util.ArrayList;

import org.eclipse.ui.ISharedImages;

/**
 * a single tree node that supports having children
 * @author mnimer
 *
 */
public class TreeParent extends TreeObject 
{
	private ArrayList children;
	public TreeParent(String name) {
		super(name);
		children = new ArrayList();
		setImageKey(ISharedImages.IMG_OBJ_FOLDER); 
	}
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	public TreeObject[] getChildren() {
		return (TreeObject [])children.toArray(new TreeObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
	
	public void clearChildren()
	{
		for (int i = 0; i < children.size(); i++)
		{
			((TreeObject)children.get(i)).setParent(null);
		}
		children.clear();
	}
}

