/*
 * Created on Oct 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.custom.StyledText;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WidgetPositionTracker {

    StyledText textWidget = null;
    /**
     * 
     */
    public WidgetPositionTracker(StyledText textWidget) {
        this.textWidget = textWidget;
    }
    /**
	 * Gets the character offset of the point in widget co-ordinates
	 * 
	 * 
	 * @param pt - Point in wiget co-ordinates
	 * @return
	 */
	public int getWidgetOffset(Point pt) {
	    int offset = -1;
	    try {
	        offset = textWidget.getOffsetAtLocation(pt);
	    }
	    catch (IllegalArgumentException e) {
	        // Check if the cursor is past the end of the line
	        Point startPt = new Point(0,pt.y);
	        return getLineStart(startPt);
	    }
	    
	    return offset;
	}
	
	
	/**
	 * Returns the character offset of the line 
	 * that corresponds to the given point in 
	 * widget co-ordinates.
	 * 
	 * @param pt
	 * @return
	 */
	public int getLineStart(Point pt) {
	    int offset = -1;
	    try {
	        offset = textWidget.getOffsetAtLocation(pt);
	        int lineNumber = textWidget.getContent().getLineAtOffset(offset);
	        offset += textWidget.getContent().getLine(lineNumber).length();
	    }
	    catch (IllegalArgumentException e) {
	        // Assume we're past the end of the doc
	        return textWidget.getCharCount();
	    }
	    
	    return offset;
	}

	
	/**
	 * Scrolls the document in the direction required if the mouse 
	 * is within 15 pixels of the edge of the text widget.
	 * @param mousePosition
	 */
	
	protected void doScroll(Point mousePosition) {
	    Point widgetSize = textWidget.getSize();
	    // The number of pixels from the edge before scrolling starts
	    int buffer = 15;
	    int horizontalBarHeight = textWidget.getHorizontalBar().getSize().y;
	    int verticalBarWidth = textWidget.getVerticalBar().getSize().x;

	    
	    
	    // Scroll down
	    if (widgetSize.y - buffer -horizontalBarHeight < mousePosition.y) {
	        int newPixel = textWidget.getTopPixel() + buffer;
	        textWidget.setTopPixel(newPixel);
	    }
	    
	    // Scroll up
	    if (mousePosition.y-buffer < 0) {
	        int newPixel = textWidget.getTopPixel() - buffer;
	        textWidget.setTopPixel(newPixel);
	    }
	    
	    
	    // Scroll right
	    if (widgetSize.x - buffer - verticalBarWidth < mousePosition.x) {
	        int newPixel = textWidget.getHorizontalPixel() + buffer;
	        textWidget.setHorizontalPixel(newPixel);
	    }
	    
	    // Scroll left
	    if (mousePosition.x-buffer < 0) {
	        int newPixel = textWidget.getHorizontalPixel() - buffer;
	        textWidget.setHorizontalPixel(newPixel);
	    }
	    
	    
	}
	
}
