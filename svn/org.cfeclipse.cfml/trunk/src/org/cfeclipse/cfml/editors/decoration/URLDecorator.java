package org.cfeclipse.cfml.editors.decoration;

import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class URLDecorator implements ILabelDecorator {

	private static Image linkImage = CFPluginImages.get(CFPluginImages.ICON_DECORATOR_LINK); 
	
	
	public Image decorateImage(Image image, Object element) {
		// TODO Auto-generated method stub
		if (element instanceof IResource) {
			IResource selResource = (IResource) element;
			try {
				String persistentProperty = selResource.getPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL));
				//image.getImageData().
				System.out.println("URL for image " + image.getImageData());
				
				
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		return null;
	}

	public String decorateText(String text, Object element) {
		// TODO Auto-generated method stub
		System.out.println("label decorator decorateText");
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		System.out.println("label decorator addListener" + listener);

	}

	public void dispose() {
		// TODO Auto-generated method stub
		System.out.println("label decorator dispose");
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		System.out.println("label decorator isLabelProperty");
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		System.out.println("label decorator removeListener");

	}

}
