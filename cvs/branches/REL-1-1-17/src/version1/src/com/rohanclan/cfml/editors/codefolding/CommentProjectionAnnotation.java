/*
 * Created on Oct 19, 2004
 *
 */
package com.rohanclan.cfml.editors.codefolding;


/**
 * @author Stephen Milligan
 *
 * This represents an annotation that was automatically contributed by the workspace. 
 * It should be safe to remove it when recalculating the annotation positions.
 */
public class CommentProjectionAnnotation extends CFEProjectionAnnotation {

    /**
     * 
     */
    public CommentProjectionAnnotation(String commentType) {
        super(commentType);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param isCollapsed
     */
    public CommentProjectionAnnotation(boolean isCollapsed,String commentType) {
        super(isCollapsed,commentType);
        // TODO Auto-generated constructor stub
    }

}
