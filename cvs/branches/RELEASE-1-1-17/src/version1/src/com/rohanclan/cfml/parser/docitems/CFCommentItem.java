/*
 * Created on Jul 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser.docitems;

/**
 * Represents a comment within the document.
 * 
 * @author Stephen Milligan
 */
public class CFCommentItem extends DocItem {
	/**
	 * The contents of the comment.
	 */
    private String contents = "";
    
    public CFCommentItem (int lineNumber, int start, int end, String contents) {

        super(lineNumber,start,end,"comment");
        this.contents = contents;
    }
    
    /**
     * Gets the contents of the comment from the document.
     * TODO: Does this return the contents plus the start/end characters?
     * @return
     */
    public String getContents() {
        return contents;
    }
    
    
    
}
