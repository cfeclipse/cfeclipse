package org.cfeclipse.cfml.frameworks.decorators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.decoration.URLDecorator;
import org.cfeclipse.cfml.frameworks.Activator;
import org.cfeclipse.cfml.frameworks.util.FWXImages;
import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IDecoratorManager;

public class ConfigFileDecorator extends LabelProvider implements
ILightweightLabelDecorator {
	
	private final List listeners = new ArrayList();
	public final static String ID = "org.cfeclipse.cfml.frameworks.decorator";
	
	private Log logger = LogFactory.getLog(ConfigFileDecorator.class);
	
	public static ConfigFileDecorator getConfigFileDecorator() {
		IDecoratorManager decoratorManager = Activator.getDefault().getWorkbench().getDecoratorManager();

		if (decoratorManager.getEnabled(ID)) {
			return (ConfigFileDecorator) decoratorManager.getBaseLabelProvider(ID);
		}
		return null;
	}
	
	
	
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IResource) {
			IResource selResource = (IResource) element;
			String isConfig = "false";
			try {
				isConfig = selResource.getPersistentProperty(new QualifiedName("", "isFrameworkConfig"));

			} catch (CoreException e) {
				e.printStackTrace();
			}
			ImageDescriptor linkImage = FWXImages.getImageDescriptor(FWXImages.GENERAL_OBJECTS, FWXImages.DEC_CONFIG);
			
			
			if (isConfig != null && isConfig.equals("true")) {
				if (linkImage != null) {
					decoration.addOverlay(linkImage);
				}
			}

		}
		
	}

	public void addListener(ILabelProviderListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void dispose() {
		listeners.clear();

	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
	}

	public void refresh() {
		logger.debug("Being called to refrehs, I shall do my duty!");
		ConfigFileDecorator decorator = getConfigFileDecorator();
		Iterator iterator = listeners.iterator();
		while (iterator.hasNext()) {
			ILabelProviderListener listener = (ILabelProviderListener) iterator.next();
			listener.labelProviderChanged(new LabelProviderChangedEvent(decorator));
		}
	}

}
