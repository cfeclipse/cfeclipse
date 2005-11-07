package com.rohanclan.cfml.views.dictionary;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;

public final class DoubleClickAction implements IDoubleClickListener {
	private Action doubleClickAction;
	
	public void doubleClick(DoubleClickEvent event) {
		doubleClickAction.run();
	}
}
