/*
 * Created on Oct 19, 2004
 *
 */
package org.cfeclipse.cfml.editors.codefolding;


/**
 * @author Stephen Milligan
 *
 * This represents an annotation that was automatically contributed by the workspace. 
 * It should be safe to remove it when recalculating the annotation positions.
 */
public class TagProjectionAnnotation extends CFEProjectionAnnotation {

    
    /**
     * 
     */
    public TagProjectionAnnotation(String tagName) {
        super(tagName);
    }

    /**
     * @param isCollapsed
     */
    public TagProjectionAnnotation(boolean isCollapsed, String tagName) {
        super(isCollapsed,tagName);
    }
    
    
}
