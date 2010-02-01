package org.cfeclipse.cfml.views.dictionary;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DictionaryViewFilter extends ViewerFilter {
	
	public String stringmatch;

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean match = true;
		
		
		if(element instanceof TagItem){
			if(stringmatch==null || stringmatch.length() == 0){
				return false;
			}
			else{
			TagItem tag = (TagItem)element;
			return tag.getName().toLowerCase().indexOf(stringmatch.toLowerCase()) != -1;
			}
			
		} else if (element instanceof FunctionItem){
			if(stringmatch==null || stringmatch.length() == 0){
				return true;
			}else{
				FunctionItem item = (FunctionItem)element;
				return item.getName().toLowerCase().indexOf(stringmatch.toLowerCase()) != -1;
			}
			
		}else if (element instanceof ScopeItem){
			if(stringmatch==null || stringmatch.length() == 0){
				return true;
			}else{
				ScopeItem item = (ScopeItem)element;
				return item.getName().toLowerCase().indexOf(stringmatch.toLowerCase()) != -1;
			}
			
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
