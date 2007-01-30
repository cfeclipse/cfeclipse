package org.cfeclipse.cfml.dialogs.objects;

import java.util.ArrayList;

import org.cfeclipse.cfml.dictionary.Parameter;

public class Category {
	private ArrayList params;
	private String name;
	
	public Category(String name){
		setName(name);
		this.params = new ArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addParameter(Parameter param){
		this.params.add(param);
	}
	
	public String toString(){
		return getName();
		
	}

	public ArrayList getParams() {
		return params;
	}
	
	
}
