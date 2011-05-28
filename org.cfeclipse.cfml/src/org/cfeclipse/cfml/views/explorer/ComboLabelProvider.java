/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cfeclipse.cfml.views.explorer;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


class ComboLabelProvider extends LabelProvider {
    public String getText(Object element) {
        return element.toString();
    }
    public Image getImage(Object element) {
        return null;
    }
}