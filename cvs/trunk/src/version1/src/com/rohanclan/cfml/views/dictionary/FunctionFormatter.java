package com.rohanclan.cfml.views.dictionary;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import com.rohanclan.cfml.dictionary.Function;
import com.rohanclan.cfml.dictionary.Parameter;

public class FunctionFormatter {
	private Properties attribs; //not sure why this is a hash table since its a Properties.
	private Function funcRef;
	private boolean wrapping = false;
	
	public FunctionFormatter(Function func){
		this.funcRef = func;
		this.attribs = new Properties();
	}
	
	public FunctionFormatter(Function func, Properties attribs){
		this.funcRef = func;
		this.attribs = attribs;
	}

	public void addAttribute(String key, String value){
		this.attribs.put(key, value);
		
	}
	
	public String getFunction(){
		StringBuffer function = new StringBuffer();
		//add the function start and end
		
		function.append(this.funcRef.getName() + "(");
		
		Iterator iter = this.funcRef.getParameters().iterator();
		//Enumeration attiterator = this.attribs.keys();
		
		//TODO: make sure this doesnt go backwards!
		while(iter.hasNext()){
			Parameter param = (Parameter)iter.next();
			
			//Find the value in the attribs
			//this.attribs.getProperty(param.getName());
			String filledProp = this.attribs.getProperty(param.getName());
			if(filledProp.length() > 0){
				function.append(filledProp);
			}
			else{
				
					if(param.getType().equals("String")){
						function.append("\"" + param.getName() + "\"");
						
					} else {
						function.append(param.getName());
					}
					
				
			}
			
			
			if(iter.hasNext()){
				function.append(", ");
			}
			
			
			
		}
		
		//now loop through the items
		
		
		function.append(")");
		return function.toString();
	}
	
	
	
	
	/*
	 * Getters and setters
	 * 
	 */
	
	public Properties getAttribs() {
		return attribs;
	}

	public void setAttribs(Properties attribs) {
		this.attribs = attribs;
	}

	public Function getFuncRef() {
		return funcRef;
	}

	public void setFuncRef(Function funcRef) {
		this.funcRef = funcRef;
	}

	public boolean isWrapping() {
		return wrapping;
	}

	public void setWrapping(boolean wrapping) {
		this.wrapping = wrapping;
	}
	
}
