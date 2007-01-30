package org.cfeclipse.cfml.views.dictionary;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DictionaryViewFilter extends ViewerFilter {
	
	public String stringmatch;

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean match = true;
		if(element instanceof TagItem){
			TagItem tag = (TagItem)element;
			return tag.getName().indexOf(stringmatch.toLowerCase()) != -1;
		} else if (element instanceof FunctionItem){
			FunctionItem item = (FunctionItem)element;
			return item.getName().indexOf(stringmatch.toLowerCase()) != -1;
		} else if (element instanceof TreeParent){
			return true;
		}
		return match;
	}

	public String getMatch() {
		return stringmatch;
	}

	public void setMatch(String match) {
		this.stringmatch = match;
	}

}
