/*
 * Created on Oct 19, 2004
 *
 */
package com.rohanclan.cfml.editors.codefolding;


import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import com.rohanclan.cfml.util.CFPluginImages;

/**
 * Annotation used to represent the projection of a master document onto a
 * {@link org.eclipse.jface.text.projection.ProjectionDocument}. A projection
 * annotation can be either expanded or collapsed. If expanded it corresponds to
 * a segment of the projection document. If collapsed, it represents a region of
 * the master document that does not have a corresponding segment in the
 * projection document.
 * <p>
 * Clients may subclass or use as is.
 * 
 * @since 3.0
 */
public class CFEProjectionAnnotation extends ProjectionAnnotation {
	
	private static class DisplayDisposeRunnable implements Runnable {

		public void run() {
			if (fgCollapsedImage != null) {
				fgCollapsedImage.dispose();
				fgCollapsedImage= null;
			}
			if (fgExpandedImage != null) {
				fgExpandedImage.dispose();
				fgExpandedImage= null;
			}
		}
	}

	/**
	 * The type of projection annotations.
	 */
	public static final String TYPE= "org.eclipse.projection"; //$NON-NLS-1$
		
	
	private static final int COLOR= SWT.COLOR_DARK_GRAY;
	private static Image fgCollapsedImage;
	private static Image fgExpandedImage;
	
	private String regionType;
	
	/** The state of this annotation */
	private boolean fIsCollapsed= false;
	/** Indicates whether this annotation should be painted as range */
	private boolean fIsRangeIndication= false;
	
	/** 
	 * Creates a new expanded projection annotation.
	 */
	public CFEProjectionAnnotation(String regionType) {
		this(false,regionType);
		this.regionType = regionType;
	}
	
	/**
	 * Creates a new projection annotation. When <code>isCollapsed</code>
	 * is <code>true</code> the annotation is initially collapsed.
	 * 
	 * @param isCollapsed <code>true</code> if the annotation should initially be collapsed, <code>false</code> otherwise
	 */
	public CFEProjectionAnnotation(boolean isCollapsed, String regionType) {
		//super(TYPE, false, null);
		fIsCollapsed= isCollapsed;
		this.regionType = regionType;
	}
	
	/**
	 * Enables and disables the range indication for this annotation.
	 * 
	 * @param rangeIndication the enable state for the range indication
	 */
	public void setRangeIndication(boolean rangeIndication) {
		fIsRangeIndication= rangeIndication;
	}
			
	private void drawRangeIndication(GC gc, Canvas canvas, Rectangle r) {
		final int MARGIN= 3;
		Color fg= gc.getForeground();
		gc.setForeground(canvas.getDisplay().getSystemColor(COLOR));
		
		gc.setLineWidth(1);
		gc.drawLine(r.x + 4, r.y + 12, r.x + 4, r.y + r.height - MARGIN);
		gc.drawLine(r.x + 4, r.y + r.height - MARGIN, r.x + r.width - MARGIN, r.y + r.height - MARGIN);
		gc.setForeground(fg);
	}
	
	/*
	 * @see org.eclipse.jface.text.source.IAnnotationPresentation#paint(org.eclipse.swt.graphics.GC, org.eclipse.swt.widgets.Canvas, org.eclipse.swt.graphics.Rectangle)
	 */
	public void paint(GC gc, Canvas canvas, Rectangle rectangle) {
		Image image= getImage(canvas.getDisplay());
		if (image != null) {
			ImageUtilities.drawImage(image, gc, canvas, rectangle, SWT.CENTER, SWT.TOP);
			if (fIsRangeIndication)
				drawRangeIndication(gc, canvas, rectangle);
		}
	}
	
	/*
	 * @see org.eclipse.jface.text.source.IAnnotationPresentation#getLayer()
	 */
	public int getLayer() {
		return IAnnotationPresentation.DEFAULT_LAYER;
	}
	
	private Image getImage(Display display) {
		initializeImages(display);
		return isCollapsed() ? fgCollapsedImage : fgExpandedImage;
	}
	
	private void initializeImages(Display display) {
		if (fgCollapsedImage == null) {
			
			fgCollapsedImage= CFPluginImages.get(CFPluginImages.ICON_COLLAPSE);
			fgExpandedImage= CFPluginImages.get(CFPluginImages.ICON_EXPAND);
			
			display.disposeExec(new DisplayDisposeRunnable());
		}
	}
	
	/**
	 * Returns the state of this annotation.
	 * 
	 * @return <code>true</code> if collapsed 
	 */
	public boolean isCollapsed() {
		return fIsCollapsed;
	}

	/**
	 * Marks this annotation as being collapsed.
	 */
	public void markCollapsed() {
		fIsCollapsed= true;
	}

	/**
	 * Marks this annotation as being unfolded.
	 */
	public void markExpanded() {
		fIsCollapsed= false;
	}
	

    public String getRegionType() {
        return this.regionType;
    }
	
}