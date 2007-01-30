/*
 * Created on Oct 15, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
package org.cfeclipse.cfml.preferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

class ColorEditor
{

	private Point fExtent = null;
	private Image fImage = null;
	private RGB fColorValue = null;
	private Color fColor = null;
	private Button fButton = null;

	public ColorEditor(Composite parent)
	{
		fButton = new Button(parent, 8);
		fExtent = computeImageSize(parent);
		fImage = new Image(parent.getDisplay(), fExtent.x, fExtent.y);
		GC gc = new GC(fImage);
		gc.setBackground(fButton.getBackground());
		gc.fillRectangle(0, 0, fExtent.x, fExtent.y);
		gc.dispose();
		fButton.setImage(fImage);
		fButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event)
			{
				ColorDialog colorDialog = new ColorDialog(fButton.getShell());
				colorDialog.setRGB(fColorValue);
				RGB newColor = colorDialog.open();
				if(newColor != null)
				{
					fColorValue = newColor;
					updateColorImage();
				}
			}

		});
		fButton.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent event)
			{
				if(fImage != null)
				{
					fImage.dispose();
					fImage = null;
				}
				if(fColor != null)
				{
					fColor.dispose();
					fColor = null;
				}
			}

		});
	}

	public RGB getColorValue()
	{
		return fColorValue;
	}

	public void setColorValue(RGB rgb)
	{
		fColorValue = rgb;
		updateColorImage();
	}

	public Button getButton()
	{
		return fButton;
	}

	protected void updateColorImage()
	{
		Display display = fButton.getDisplay();
		GC gc = new GC(fImage);
		gc.setForeground(display.getSystemColor(2));
		gc.drawRectangle(0, 2, fExtent.x - 1, fExtent.y - 4);
		if(fColor != null)
			fColor.dispose();
		fColor = new Color(display, fColorValue);
		gc.setBackground(fColor);
		gc.fillRectangle(1, 3, fExtent.x - 2, fExtent.y - 5);
		gc.dispose();
		fButton.setImage(fImage);
	}

	protected Point computeImageSize(Control window)
	{
		GC gc = new GC(window);
		org.eclipse.swt.graphics.Font f = JFaceResources.getFontRegistry().get("org.eclipse.jface.defaultfont");
		gc.setFont(f);
		int height = gc.getFontMetrics().getHeight();
		gc.dispose();
		Point p = new Point(height * 3 - 6, height);
		return p;
	}







}