/*
 * Created on May 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.cfcmethods;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import com.rohanclan.cfml.util.CFPluginImages;

/**
 * @author Stephen Milligan
 *
 */
public class CFCMethodsLabelProvider  extends LabelProvider implements ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		return getText(obj);
	}
	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}
	public Image getImage(Object obj) {

		return CFPluginImages.get(CFPluginImages.ICON_FUNC);

	}
}