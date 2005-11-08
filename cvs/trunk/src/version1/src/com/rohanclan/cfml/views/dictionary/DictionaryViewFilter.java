package com.rohanclan.cfml.views.dictionary;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import com.rohanclan.cfml.views.dictionary.TagItem;
import com.rohanclan.cfml.views.dictionary.FunctionItem;

public class DictionaryViewFilter extends ViewerFilter {
	
	public String match;

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean match = true;
		
		if(element instanceof TreeParent){
			
			
			System.out.println("Found Tree Parent " + element.toString().contains(this.match));
			return element.toString().contains(this.match);
		}	
		if(element instanceof TagItem){
			System.out.println("Tag Item Match");
			return ((TagItem)element).getName().contains(this.match);
			
		}
		if(element instanceof FunctionItem){
			System.out.println("Function Item");
			return ((FunctionItem)element).getName().contains(this.match);
		}
		
		
		return match;
	}

}
