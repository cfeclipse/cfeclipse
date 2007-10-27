package org.cfeclipse.cfml.dialogs.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoryList {
	private ArrayList categories;
	
	public CategoryList() {
		this.categories = new ArrayList();
	}
	
	public boolean hasCategory(String item){
		//loop through the array list and if we find the item string, then it has it
		//throwing errrs..
		Iterator iter = this.categories.iterator();
		while(iter.hasNext()){
			Category cat = (Category)iter.next();
			if(cat.getName().equals(item)){
				return true;
			}
		}
		return false;
	}
	
	public void addCategory(Category cat){
		this.categories.add(cat);
	}
	public Category getCategory(String catName){
		Iterator iter = this.categories.iterator();
		while(iter.hasNext()){
			Category cat = (Category)iter.next();
			if(cat.getName().equals(catName)){
				return cat;
			}
		}
		return null;
	}
	
	public String toString(){
		String output = "";
		Iterator iter = categories.iterator();
		while(iter.hasNext()){
			Category cat = (Category)iter.next();
			output += cat + "\n";
		}
		
		
		return output;
	}

	public ArrayList getCategories() {
		return categories;
	}

	
	
}
