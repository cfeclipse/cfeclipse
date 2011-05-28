/*
 * Created on Oct 29, 2004
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
package org.cfeclipse.cfml.editors.dnd;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

/**
 * @author Stephen Milligan
 */
public class WidgetPositionTracker {

    StyledText textWidget = null;
    int lastGoodOffset;
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
	        lastGoodOffset = offset;
	    }
	    catch (IllegalArgumentException e) {
	        // Check if the cursor is past the end of the line
	        //Point startPt = new Point(0,pt.y);
	        //return getLineStart(startPt);
	    	return lastGoodOffset;
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
