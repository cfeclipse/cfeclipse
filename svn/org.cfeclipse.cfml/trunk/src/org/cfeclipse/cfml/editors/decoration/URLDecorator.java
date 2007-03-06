package org.cfeclipse.cfml.editors.decoration;

import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class URLDecorator extends LabelProvider 
implements ILightweightLabelDecorator  {

	public Image decorateImage(Image image, Object element) {
		System.out.println("returning the image for element " + element);
		if (element instanceof IResource) {
			IResource res = (IResource) element;
			try {
				String persistentProperty = res.getPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL));
				if(persistentProperty!= null && persistentProperty.length() > 0){
					
					Image overlayImage = CFPluginImages.get(CFPluginImages.ICON_DECORATOR_LINK);
					
					URLDecoratorImageDescriptor imgDesc = new URLDecoratorImageDescriptor(image, overlayImage);
					imgDesc.drawCompositeImage(0,0);
					
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
	public void decorate(Object element, IDecoration decoration) {
		// TODO Auto-generated method stub
		
	}

}
