package org.cfeclipse.cfml.editors.decoration;

import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;

public class URLImages {

	
	public static final ImageDescriptor linkDescriptor = ImageDescriptor.createFromImage(CFPluginImages.get(CFPluginImages.ICON_DECORATOR_LINK));
	
	public URLImages(){
		super();
	}
	
	public ImageData getLinkImageData(){
		return linkDescriptor.getImageData();
	}
	
	
}
