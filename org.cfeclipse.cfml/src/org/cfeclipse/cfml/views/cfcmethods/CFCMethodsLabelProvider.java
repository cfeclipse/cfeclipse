/*
 * Created on May 11, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.views.cfcmethods;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Stephen Milligan
 */
public class CFCMethodsLabelProvider  extends LabelProvider implements ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		MethodViewItem item = (MethodViewItem) obj;
		return item.toString();

	}
	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}
	public Image getImage(Object obj) {
	    try {
			MethodViewItem item = (MethodViewItem) obj;
		    
		    if (item.getAccess().toLowerCase().equals("remote")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_REMOTE);
		    } else if (item.getAccess().toLowerCase().equals("public")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PUBLIC);
		    } else if (item.getAccess().toLowerCase().equals("package")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PACKAGE);
		    } else if (item.getAccess().toLowerCase().equals("private")) {
		        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PRIVATE);
		    }
		    return CFPluginImages.get(CFPluginImages.ICON_ALERT);
	    }
	    catch (Exception e) {
	        //e.printStackTrace();
	        return CFPluginImages.get(CFPluginImages.ICON_METHOD_PUBLIC);
	    }

	}
}