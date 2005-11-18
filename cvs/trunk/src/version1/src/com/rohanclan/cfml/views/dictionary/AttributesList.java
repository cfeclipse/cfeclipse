package com.rohanclan.cfml.views.dictionary;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class AttributesList {
	
	private final int COUNT = 10;
	private Vector attribs = new Vector(COUNT);
	private Set changeListeners = new HashSet();
	
	//Combo box Choices ** this would depend on the tag I presume
	static final String[] OWNERS_ARRAY = {"?", "sid", "nancy"};
	
	public AttributesList() {
		super();
		this.initData();
	}
	
	private void initData(){
		
		//Create some sample attributes. This would actually require a tag and then we set up the attribute list etc.
		AttributeItem item;
		
		for(int i=0; i < COUNT; i++){
			item = new AttributeItem("someattrib_" + i, "somevalue");
			attribs.add(item);
		}
	}
	
	public Vector getAttributes(){
		return attribs;
		
	}
	
	public void attributeChanged(AttributeItem item){
		Iterator iterator = changeListeners.iterator();
			while(iterator.hasNext())
					((IAttributeListViewer)iterator.next()).updateAttribute(item);
	
	}
	
	public void removeChangeListner(IAttributeListViewer viewer){
		changeListeners.remove(viewer);
	}
	
	public void addChangeListener(IAttributeListViewer viewer){
		changeListeners.add(viewer);
	}
	

}
