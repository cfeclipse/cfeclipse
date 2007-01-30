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

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;

/**
 * @author Stephen Milligan
 *
 * This class keeps track of the position of an annotation 
 * so that it can be replaced after a document change such
 * as an internal drag and drop event.
 */
public class AnnotationPosition {

    private Annotation annotation = null;
    private Position position = null;
    private boolean collapsed = false;

    /**
     * Creates an instance with the passed annotation and position
     * @param annotation
     * @param position
     * @param collapsed
     */
    public AnnotationPosition(Annotation annotation, Position position, boolean collapsed) {
        this.annotation = annotation;
        this.position = position;
        this.collapsed = collapsed;
    }

    /**
     * Creates an instance with the passed annotation and position
     * @param annotation
     * @param position
     */
    public AnnotationPosition(Annotation annotation, Position position) {
        this.annotation = annotation;
        this.position = position;
    }
    
    /**
     * Returns the annotation
     * @return
     */
    public Annotation annotation() {
        return annotation;
    }

    /**
     * Returns the position
     * @return
     */
    public Position position() {
        return position;
    }
    
    public boolean collapsed() {
        return collapsed;
    }
}
