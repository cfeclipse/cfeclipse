/*
 * Created on Oct 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.dnd;

import java.util.Iterator;
import java.util.ArrayList;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

/**
 * @author Stephen Milligan
 *
 * Tracks and rebuilds annotations that are affected
 * based on offsets in viewer co-ordinates.
 * 
 * 
 */
public class AnnotationTracker {
    /**
     * The viewer who's co-ordinates will be used
     */
    private ProjectionViewer viewer = null;
    /**
     *  A list of all <code>AnnotationPosition</code> to apply after a change
     */
    private ArrayList annotationList = null;
    
    /**
     * This constructor creates the viewer so that all methods
     * have access to it.
     */
    public AnnotationTracker(ProjectionViewer viewer) {
        this.viewer = viewer;
    }

    

	/**
	 * Returns an array of AnnotationPositions containing each of the annotatations that 
	 * will be affected by performing the drag operation.
	 * 
	 * Removes all affected annotations from the model.
	 * 
	 * Annotations that are only partly inside the selection are not returned, 
	 * but they are removed.
	 * 
	 * The positions in each of the returned AnnotationPosition are relative 
	 * to the selection offset.
	 * 
	 * @param selectionOffset
	 * @param length
	 * @return
	 */
	public void createAnnotationList(int selectionOffset, int length) {
	    annotationList = new ArrayList();
	    // Projection Annotations
	    ProjectionAnnotationModel pModel = viewer.getProjectionAnnotationModel();
        Iterator i = pModel.getAnnotationIterator();
        
        
        while (i.hasNext()) {
            Object o = i.next();
            if (o instanceof ProjectionAnnotation) {
                ProjectionAnnotation annotation = (ProjectionAnnotation)o;
                Position pos = pModel.getPosition(annotation);
                
                // The annotation is completely inside the selection
                if (pos.offset >= selectionOffset 
                        && pos.offset <= selectionOffset + length) {
                    boolean collapsed = annotation.isCollapsed();
                    pModel.expand(annotation);
                    Position newPos = new Position(pos.offset-selectionOffset,pos.length);
                    annotationList.add(new AnnotationPosition(annotation,newPos,collapsed));
                    
                    // Remove the annotation for now.
                    pModel.removeAnnotation(annotation);
                }
                // The annotation starts in the selection, but doesn't end in it.
                else if(pos.offset >= selectionOffset 
                        && pos.offset <= selectionOffset + length
                        && pos.offset + pos.length >= selectionOffset + length) {
                    pModel.removeAnnotation(annotation);
                }
                // The annotation ends in the selection, but doesn't start in it
                else if (pos.offset + pos.length >= selectionOffset
                        && pos.offset + pos.length <= selectionOffset + length) {
                    pModel.removeAnnotation(annotation);
                }
                
            }
        }

	    
	    // Regular Annotations
	    IAnnotationModel aModel = viewer.getAnnotationModel();
        i = aModel.getAnnotationIterator();
        
        
        while (i.hasNext()) {
            Object o = i.next();
            if (o instanceof Annotation) {
                Annotation annotation = (Annotation)o;
                Position pos = aModel.getPosition(annotation);
                // The annotation is completely inside the selection
                if (pos.offset >= selectionOffset 
                        && pos.offset <= selectionOffset + length) {
                    Position newPos = new Position(pos.offset-selectionOffset,pos.length);
                    
                    annotationList.add(new AnnotationPosition(annotation,newPos));
                    // Remove the annotation for now.
                    aModel.removeAnnotation(annotation);
                }
                // The annotation starts in the selection, but doesn't end in it.
                else if(pos.offset >= selectionOffset 
                        && pos.offset <= selectionOffset + length
                        && pos.offset + pos.length > selectionOffset + length) {
                    aModel.removeAnnotation(annotation);
                }
                // The annotation ends in the selection, but doesn't start in it
                else if (pos.offset + pos.length >= selectionOffset
                        && pos.offset + pos.length <= selectionOffset + length) {
                    aModel.removeAnnotation(annotation);
                }
                
            }
        }
     
        
	}
	
	
	/**
	 * The annotations are added to the model relative to the offset provided
	 * 
	 * The ArrayList must only contain AnnotationPositions.
	 * 
	 * 
	 * @param offset
	 * @param annotationList
	 */
	public void applyAnnotations(int offset) {
	    if (annotationList == null) {
	        return;
	    }
	    Object[] allAnnotations = annotationList.toArray();
	    ArrayList collapsedAnnotations = new ArrayList();
	    try {
	    ProjectionAnnotationModel pModel = viewer.getProjectionAnnotationModel();
	    IAnnotationModel aModel = viewer.getAnnotationModel();
	    for(int i=0;i<allAnnotations.length;i++) {
	        AnnotationPosition ap = (AnnotationPosition)allAnnotations[i]; 
	        Position p = new Position(ap.position().offset+offset,ap.position().length);
	        if (ap.annotation() instanceof ProjectionAnnotation) {
	            pModel.addAnnotation(ap.annotation(),p);
	           
	            // Don't collapse this in here in case there are 
	            // non-collapsed nested annotations
	            // that haven't been added to the model yet.
	            if (ap.collapsed()) {
	                collapsedAnnotations.add(ap.annotation());
	            }
	            
	        }
	        else {
	            aModel.addAnnotation(ap.annotation(),p);
	        }
	    }
	    
	    // Now collapse the annotations that were collapsed before
	    Object[] annotations = collapsedAnnotations.toArray();
	    for (int x =0;x<annotations.length;x++) {
	        pModel.collapse((ProjectionAnnotation)annotations[x]);
	    }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
    
}
