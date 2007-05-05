/*
 * Created on Mar 27, 2007 by markdrew
 *
 * The MIT License
 * Copyright (c) 2007 CFEclipse Project
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
package org.cfeclipse.cfml.frameworks.util;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class ImageDecorator extends CompositeImageDescriptor {

	private Image baseImage;
	private Image overLayImage;
	
	public ImageDecorator(Image baseImage, Image overLayImage) {
		super();
		this.baseImage = baseImage;
		this.overLayImage = overLayImage;
	}

	protected void drawCompositeImage(int width, int height) {
		 drawImage(this.baseImage.getImageData(), 0, 0); 

		   // Method to create the overlay image data 
		   // Get the image data from the Image store or by other means
		   ImageData overlayImageData = this.overLayImage.getImageData();

		   // Overlaying the icon in the top left corner i.e. x and y 
		   // coordinates are both zero
		   int xValue = 0;
		   int yValue = 0;
		drawImage (overlayImageData, xValue, yValue);
	}

	protected Point getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
