/*
 * Created on May 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SnipDoubleClickListener 
implements IDoubleClickListener {
	
	SnipTreeView snipView;
	/**
	 * 
	 */
	public SnipDoubleClickListener(SnipTreeView snipView) {
		super();
		
		this.snipView = snipView;
	}
	
	public void doubleClick(DoubleClickEvent e) {
		snipView.insertItem();
		
	}
	
}
