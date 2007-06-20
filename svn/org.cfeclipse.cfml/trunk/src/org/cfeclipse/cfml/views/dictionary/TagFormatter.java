/*
 * Created on 19-Feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.views.dictionary;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import org.cfeclipse.cfml.dictionary.Tag;


/**
 * @author mdrew
 * This class takes a tag name, you can add attribute/values and it will 
 * produce for you a nicely done tag (in theory)
 */
public class TagFormatter {
	
	private Hashtable attribs = new Hashtable();
	private Tag tagRef;
	private boolean wrapping  = false;
	private ArrayList attributeOrder;
	
	
	/** Pass in a tag, then you can manually add the attributes
	 * @param tag
	 */
	public TagFormatter(Tag tag) {
		this.tagRef = tag;
	}
	
	
	/**
	 * Pass in a tag reference and pass it the attributes that have been filled in (usually from a dialog?)
	 * @param tag
	 * @param attribs
	 */
	public TagFormatter(Tag tag, Hashtable attribs){
		this.tagRef = tag;
		this.attribs = attribs;
	}
	
	public TagFormatter(Tag tagToEdit, Properties fieldStore, ArrayList propOrder) {
		// TODO Auto-generated constructor stub
		this.tagRef = tagToEdit;
		this.attribs = fieldStore;
		this.attributeOrder = propOrder;
		
	}


	/**
	 * @return Returns the attribs.
	 */
	public Hashtable getAttribs() {
		return attribs;
	}
	/**
	 * @param attribs The attribs to set.
	 */
	public void setAttribs(Hashtable attribs) {
		this.attribs = attribs;
	}
	
	/**
	 * Adds an attribute to the mapping
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key, String value){
		this.attribs.put(key, value);
	}
	
	/* returns the tag and overrides the toString() method.
	 * @see java.lang.Object#toString()
	 */
	public String toString(){

		return getTagStart() + getTagEnd();
	}
	public String getTagStart(){
		StringBuffer tag = new StringBuffer();
		String tagname = this.tagRef.getName();
		tag.append("<" + tagname);
		
		//Loop through the attributes in order
		if(this.attributeOrder != null){
			for (Iterator iter = this.attributeOrder.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
	
				String attribute = element;
			 	String value = attribs.get(element).toString();
			    
			 	if(value.length() > 0){
			 	tag.append(" " + attribute);
			    //So we can check for blanks
			    tag.append("=\"" + value + "\"");
			 	}
			}
		}
		else{ //Not being called from edit, and we dont have initial items
			Hashtable attributes = getAttribs();
			
			 for (Enumeration e = attributes.keys (); e.hasMoreElements ();) {
			 	String attribute = e.nextElement ().toString ();
			 	String value = attributes.get(attribute).toString();
			    
			 	if(value.length() > 0){
			 	tag.append(" " + attribute);
			    //So we can check for blanks
			    tag.append("=\"" + value + "\"");
			 	}
			 }
			
		}
		/*//Here we loop through the attributes that have been added
		Hashtable attributes = getAttribs();
		
		 for (Enumeration e = attributes.keys (); e.hasMoreElements ();) {
		 	String attribute = e.nextElement ().toString ();
		 	String value = attributes.get(attribute).toString();
		    
		 	if(value.length() > 0){
		 	tag.append(" " + attribute);
		    //So we can check for blanks
		    tag.append("=\"" + value + "\"");
		 	}
		 }*/
		 //Here we check what ending we have
		 if(this.tagRef.isXMLStyle() && this.tagRef.isSingle() && !isWrapping()){
		 	 tag.append("/>");
		 } else{
		 	 tag.append(">");
		 }
		
		 //TODO: check that there is a string we are enclosing
		 return tag.toString();
	}
	
	public String getTagEnd(){
		StringBuffer tag = new StringBuffer();
		String tagname = this.tagRef.getName();
//		Here we choose how we end the tag
	
			if(this.tagRef.isSingle()){
			 	tag.append("");
				
			 } else{
		 		if(isWrapping()){
		 			tag.append("\n");
		 		}
		 		tag.append("</" + tagname + ">");
			 }
	
			
			return tag.toString();
		
	}
	/**
	 * @return Returns the wrapping.
	 */
	public boolean isWrapping() {
		return wrapping;
	}
	/**
	 * @param wrapping The wrapping to set.
	 */
	public void setWrapping(boolean wrapping) {
		this.wrapping = wrapping;
	}
}
