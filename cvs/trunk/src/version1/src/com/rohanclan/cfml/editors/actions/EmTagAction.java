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
public class EmTagAction extends GenericEncloserAction {

    /**
     *
     * Encloses highlighted text in <code><em>...</em></code> tags 
     */
    public EmTagAction() {
        super("<em>","</em>");
    }

}
