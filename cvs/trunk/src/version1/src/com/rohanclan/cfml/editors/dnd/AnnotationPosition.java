/*
 * Created on Oct 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.dnd;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.Position;

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
