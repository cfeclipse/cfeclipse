/*
 * Created on Jan 23, 2005
 * by Christopher Bradford
 *
 */
package com.rohanclan.cfml.editors.actions;

/**
 * @author Christopher Bradford
 *
 */
public class StrongTagAction extends GenericEncloserAction {

    /**
     * 
     * Encloses highlighted text in <code><strong>...</strong></code> tags
     */
    public StrongTagAction() {
        super("<strong>", "</strong>");
    }

}
