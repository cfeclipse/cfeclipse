package org.cfeclipse.cfml.views.dictionary;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DictionaryViewFilter extends ViewerFilter {
	
	public String match;

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean match = true;
		
		if(element instanceof TreeParent){
			
			boolean found = element.toString().indexOf(this.match) > 0;
			System.out.println("Found Tree Parent " + found);
			return found;
		}	
		if(element instanceof TagItem){
			System.out.println("Tag Item Match");
			return ((TagItem)element).getName().indexOf(this.match) > 0;
			
		}
		if(element instanceof FunctionItem){
			System.out.println("Function Item");
			return ((FunctionItem)element).getName().indexOf(this.match) > 0;
		}
		
		
		return match;
	}

}
