/*
 * Created on Oct 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.preferences;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

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