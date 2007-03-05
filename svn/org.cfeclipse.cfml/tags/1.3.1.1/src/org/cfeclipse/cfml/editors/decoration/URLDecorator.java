package org.cfeclipse.cfml.editors.decoration;

import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.OverlayIcon;

public class URLDecorator implements ILabelDecorator {

	public Image decorateImage(Image image, Object element) {
		if (element instanceof IResource) {
			IResource res = (IResource) element;
			try {
				String persistentProperty = res.getPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL));
				if(persistentProperty!= null && persistentProperty.length() > 0){
					
					Image overlayImage = CFPluginImages.get(CFPluginImages.ICON_DECORATOR_LINK);
					
					
					
					return image;
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String decorateText(String text, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

}
