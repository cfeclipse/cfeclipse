package org.cfeclipse.cfml.views.packageview.objects;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.ISharedImages;


/*
 * The content provider class is responsible for
 * providing objects to the view. It can wrap
 * existing objects in adapters or simply return
 * objects as-is. These objects may be sensitive
 * to the current input of the view, or ignore
 * it and always show the same content 
 * (like Task List, for example).
 */
 
public class TreeObject implements IAdaptable {
	private String name;
	private TreeParent parent;
	private String imageKey;
	private String insertName;
	
	public TreeObject(String name) {
		this.name = name;
		imageKey = ISharedImages.IMG_OBJ_ELEMENT;
	}
	public String getName() {
		return name;
	}
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	public TreeParent getParent() {
		return parent;
	}
	 public String getPackageName()
	    {
		 System.out.println("doing the package in TreeObject");
	    	String packageName = "";
	    	packageName = parent.getPackageName() + getName(); 
	        return packageName;
	    }
	 
	public String toString() {
		return getName();
	}
	public Object getAdapter(Class key) {
		return null;
	}
	
	public void setImageKey(String key) {
		imageKey = key;
	}
	
	public String getImageKey(){
		return imageKey;
	}
	
	public void setName(String value)
	{
		this.name = value;
	}
	
	public String getInsertName()
	{
		if (insertName == null)
			return name;
		else return insertName;
	}
	
	public void setInsertName(String insertName)
	{
		this.insertName = insertName;
	}
}
