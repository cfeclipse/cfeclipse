package org.cfeclipse.cfml.editors.decoration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.internal.decorators.DecoratorManager;


public class URLDecorator extends LabelProvider implements ILightweightLabelDecorator  { 

	private final List listeners = new ArrayList();
	public URLDecorator(){
		System.out.println("Creating the decorator");
		DecoratorManager decoratorManager = (DecoratorManager)CFMLPlugin.getDefault().getWorkbench().getDecoratorManager();
		addListener(decoratorManager);
	}

	public static URLDecorator getURLDecorator()
	{
    IDecoratorManager decoratorManager =
    	CFMLPlugin.getDefault().getWorkbench().getDecoratorManager();
  
	  if (decoratorManager
		.getEnabled("org.cfeclipse.cfml.editors.decoration.URLDecorator"))
	  {
		return (URLDecorator) decoratorManager.getBaseLabelProvider("org.cfeclipse.cfml.editors.decoration.URLDecorator");
	  }
	  return null;
	}
	
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IResource) {
			IResource selResource = (IResource) element;
			String urlProperty = null;
			try {
				urlProperty = selResource.getPersistentProperty(new QualifiedName("", CFMLPreferenceConstants.P_PROJECT_URL));
				
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
			ImageDescriptor linkImage = CFPluginImages.getImageDescriptor(CFPluginImages.MODEL_OBJECTS, CFPluginImages.ICON_DECORATOR_LINK);
			if(urlProperty !=null && urlProperty.length() > 0){
				if(linkImage != null){
					decoration.addOverlay(linkImage);
				}
			}
			
		}
		
	}

	public void addListener(ILabelProviderListener listener) {
		System.out.println("adding a listener" + listener);
		if(!listeners.contains(listener)){
			listeners.add(listener);
		}
		
		
	}

	public void dispose() {
		listeners.clear();
		
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		System.out.println("removing a listener" + listener);
		listeners.remove(listener);
	}

	public void refresh(){
	 URLDecorator decorator = getURLDecorator();
	  fireLabelEvent(new LabelProviderChangedEvent(decorator));
		
	}
	 private void fireLabelEvent(final LabelProviderChangedEvent event)
	    {
	    // We need to get the thread of execution to fire the label provider
	    // changed event , else WSWB complains of thread exception. 
	    Display.getDefault().asyncExec(new Runnable()
	    {
	      public void run()
	      {
	        fireLabelProviderChanged(event);
	      }
	    });
	    }
	
	
}
