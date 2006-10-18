package org.cfeclipse.cfml.views.dictionary;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DictionaryViewFilter extends ViewerFilter {
	
	public String matchstring;

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean match = true;
		if(element instanceof TagItem){
			TagItem item = (TagItem)element;
			return item.getName().indexOf(matchstring) != -1;
		}
		else if(element instanceof FunctionItem){
			FunctionItem item = (FunctionItem)element;
			return item.getName().indexOf(matchstring) != -1;
		}
		
		return match;
	}

}
