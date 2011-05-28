package org.cfeclipse.cfml.editors.decoration;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class URLDecoratorDescriptor extends CompositeImageDescriptor {

	private Image baseImage;
	private Image overlayImage;
	
	public URLDecoratorDescriptor(Image baseImage, Image overlayImage) {
		super();
		this.baseImage = baseImage;
		this.overlayImage = overlayImage;
	}

	protected void drawCompositeImage(int width, int height) {
		// TODO Auto-generated method stub
		drawImage(baseImage.getImageData(), 0, 0);
		ImageData overlayImageData = overlayImage.getImageData();
		 int xValue = 0; 
		 int yValue = 0; 
		 drawImage (overlayImageData, xValue, yValue);
	}

	protected Point getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
