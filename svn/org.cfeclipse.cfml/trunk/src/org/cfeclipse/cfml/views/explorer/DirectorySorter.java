/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


class DirectorySorter extends ViewerSorter {
    public int compare(Viewer viewer, Object e1, Object e2) {
    	String[] item1 = e1.toString().split("/");
        String[]  item2 = e2.toString().split("/");
        String dir1 = item1[item1.length-1];
        String dir2 = item2[item2.length-1];
        return dir1.compareToIgnoreCase(dir2);
    }
}