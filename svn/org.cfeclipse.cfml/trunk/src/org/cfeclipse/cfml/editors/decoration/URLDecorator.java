package org.cfeclipse.cfml.editors.decoration;

import java.util.List;
import java.util.Vector;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;


public class URLDecorator extends LabelProvider 
implements ILabelDecorator {

	
	@Override
	protected void fireLabelProviderChanged(LabelProviderChangedEvent event) {
		// TODO Auto-generated method stub
		super.fireLabelProviderChanged(event);
	}


	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return super.getImage(element);
	}


	@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		return super.getText(element);
	}
	private static URLImages urlImage_ = new URLImages();
	
	private static boolean decorateTextLabels_ = false;
	private static boolean decorateResources_ = true;
	
	private static List initialDecoratorList_ = new Vector();
	
	private static URLDecorator getURLDecorator(){
		IDecoratorManager decoratorManager = CFMLPlugin.getDefault().getWorkbench().getDecoratorManager();
		
		if(decoratorManager.getEnabled("org.cfeclipse.cfml.ui.decorators.urlLinkDecorator")){
			return (URLDecorator) decoratorManager.getBaseLabelProvider("org.cfeclipse.cfml.ui.decorators.urlLinkDecorator");
		}
		return null;
	}
	
	
	public void decorate(Object object, IDecoration decoration){
		IResource objectResource;
		objectResource = getResource(object);
		
		if(objectResource == null){
			return;
		}
		
		System.out.println(decoration);
		//NOW decorate the images
		decoration.addOverlay(URLImages.linkDescriptor);
		
		
	}
	
	public void refresh(){
		List resourcesToBeUpdated;
		URLDecorator urlDecorator = getURLDecorator();
		if(urlDecorator == null){
			return;
		}
		else {
			resourcesToBeUpdated = URLDecoratorManager.getSuccessResources();
			
			urlDecorator.fireLabelEvent(new LabelProviderChangedEvent(urlDecorator, resourcesToBeUpdated.toArray()));
		}
		
	}
	public void refreshAll(boolean displayTextLabel, boolean displayProject){
		decorateTextLabels_ = displayTextLabel;
		decorateResources_ = displayProject;
	
		URLDecorator urlDecorator = getURLDecorator();
		if(urlDecorator == null){
			return;
		}
		else{
			urlDecorator.fireLabelEvent(new LabelProviderChangedEvent(urlDecorator));
		}
		
	}
	
	public void refresh(List resourcesToBeUpdated)
    {
     // newDecorationRequest_ = true;
      initialDecoratorList_ = null;

      URLDecorator urlDecorator = getURLDecorator();
      if (urlDecorator == null)
      {
        return;
      }
      else
      {
        // Fire a label provider changed event to decorate the 
        // resources whose image needs to be updated
        fireLabelEvent(
          new LabelProviderChangedEvent(
        		  urlDecorator, resourcesToBeUpdated.toArray()));
      }
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
	 private IResource getResource(Object object) 
   {
     if (object instanceof IResource) 
     {
       return (IResource) object;
     }
     if (object instanceof IAdaptable) 
     {
       return (IResource) ((IAdaptable) object).getAdapter(IResource.class);
     }
     return null;
   }


	public Image decorateImage(Image image, Object element) {
		// TODO Auto-generated method stub
		
		System.out.println("Being Run" + element + " " + image);
		return null;
	}

}
