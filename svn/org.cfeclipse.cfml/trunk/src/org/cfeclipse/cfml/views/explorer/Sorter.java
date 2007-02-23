/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


/**
 * This class sorts the files in order
 * 
 * @author mark
 *
 */
class Sorter extends ViewerSorter {
 
 /**
   * Returns a negative, zero, or positive number depending on whether
   * the first element is less than, equal to, or greater than
   	* the second element.
 */
public int compare(Viewer viewer, Object e1, Object e2) {
	   Object[] e1l = (Object[])e1;
	   Object[] e2l = (Object[])e2;
	   
	   Object Item1 = e1l[0];
	   Object Item2 = e2l[0];
	   String[] Itemsplit1 = Item1.toString().split("/");
	   String[] Itemsplit2 = Item2.toString().split("/");
	   String firstChar = Itemsplit1[Itemsplit1.length-1];
	   String secondChar = Itemsplit2[Itemsplit2.length-1];
       return firstChar.compareToIgnoreCase(secondChar);
    }
}