/*
 * Created on Feb 27, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package com.rohanclan.cfml.views;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

class PictureLabelLayout extends Layout {
	Point iExtent, tExtent; // the cached sizes

	protected Point computeSize(Composite composite, int wHint, int hHint,
			boolean changed) 
	{
		Control [] children = composite.getChildren();
		
		if (changed || iExtent == null || tExtent == null) 
		{
			iExtent = children[0].computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
			tExtent = children[1].computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		}
		int width = iExtent.x + 5 + tExtent.x;
		int height = Math.max(iExtent.y, tExtent.y);
		return new Point(width + 2, height + 2);
	}

	protected void layout(Composite composite, boolean changed) 
	{
		Control [] children = composite.getChildren();
		if (changed || iExtent == null || tExtent == null) 
		{
			iExtent = children[0].computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
			tExtent = children[1].computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
		}
		children[0].setBounds(1, 1, iExtent.x, iExtent.y);
		children[1].setBounds(iExtent.x + 5, 1, tExtent.x, tExtent.y);
	}
}

public class PictureLabel extends Composite {
	Label image, text;
	Color white;

	PictureLabel(Composite parent, int style) 
	{
		super(parent, style);
		white = new Color(null, 255, 255, 255);
		image = new Label(this, 0);
		text = new Label(this, 0);
		setBackground(white);
		text.setBackground(white);
		image.setBackground(white);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				PictureLabel.this.widgetDisposed(e);
			}
		});
		setLayout(new PictureLabelLayout());
	}

	void widgetDisposed(DisposeEvent e) 
	{
		white.dispose();
	}

	public Image getImage() 
	{
		return image.getImage();
	}

	public void setImage(Image image) 
	{
		this.image.setImage(image);
		layout(true);
	}

	public String getText() 
	{
		return text.getText();
	}

	public void setText(String text) 
	{
		this.text.setText(text);
		layout(true);
	}
	
	public String toString()
	{
		return this.text.getText();
	}
	
}