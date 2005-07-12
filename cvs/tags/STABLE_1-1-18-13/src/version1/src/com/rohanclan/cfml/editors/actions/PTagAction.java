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
public class PTagAction extends GenericEncloserAction {

    /**
     *
     * Encloses highlighted text in <code><p>...</p></code> tags 
     */
    public PTagAction() {
        super("<p>","</p>");
    }

}
