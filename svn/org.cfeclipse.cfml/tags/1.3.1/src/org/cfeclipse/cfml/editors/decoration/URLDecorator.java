package org.cfeclipse.cfml.editors.decoration;

import java.util.List;
import java.util.Vector;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IDecoratorManager;
/**
 * An example of the implementaiton is found here:
 * 
 * See: {@link http://www.eclipse.org/articles/Article-Decorators/decorators.html}
 */

public class URLDecorator extends LabelProvider implements ILightweightLabelDecorator   {

	 private static List initialDecoratorList_ = new Vector();
	 private static boolean decorateTextLabels_ = false;
	 private static boolean decorateProject_ = true;
	 
	 
	 
	public static URLDecorator getURLDecorator(){
		  IDecoratorManager decoratorManager = CFMLPlugin.getDefault().getWorkbench().getDecoratorManager();
		  if (decoratorManager.getEnabled("org.cfeclipse.cfml.editors.decoration.URLDecorator")){
			  	return (URLDecorator) decoratorManager.getBaseLabelProvider("org.cfeclipse.cfml.editors.decoration.URLDecorator");
		  }
		  return null;
	}
	 
	 
	 
	public void decorate(Object element, IDecoration decoration) {
		IResource objectResource;
		objectResource = getResource(element);
		Vector decoratorImageKeys = new Vector();
		if(objectResource == null){
			return;
		}
		Image image = CFPluginImages.get(CFPluginImages.ICON_DECORATOR_LINK);
		
		decoration.addOverlay(ImageDescriptor.createFromImage(image));
		
		
		
	}
	 private IResource getResource(Object object){
	     if (object instanceof IResource){
	    	 return (IResource) object;
	     }

	     if (object instanceof IAdaptable){
	    	 return (IResource) ((IAdaptable) object).getAdapter(IResource.class);
	     }

	     return null;
	 }

	
	

	

	

	

}
