package com.rohanclan.cfml.views.dictionary;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

class DictionaryViewContentProvider implements IStructuredContentProvider,ITreeContentProvider {
	
	private TreeParent invisibleRoot;

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		//if (parent.equals(getViewSite())) {
			if (invisibleRoot == null)
				initialize();
			return getChildren(invisibleRoot);
		//}
		//return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject) child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent) parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent) parent).hasChildren();
		return false;
	}

	/*
	 * 
	 * This could now be changed and be driven by the documentation. So we can
	 * categorise them. since its a per dictionary thing, I shall try and get
	 * the structure of the dictionary, and get all the items
	 * 
	 */
	private void initialize() {
		CategoryLoader catloader = new CategoryLoader("root");

		TreeParent root = catloader.getUnsortedCategories();
		invisibleRoot = new TreeParent("");
		invisibleRoot.addChild(root);

	}
}
