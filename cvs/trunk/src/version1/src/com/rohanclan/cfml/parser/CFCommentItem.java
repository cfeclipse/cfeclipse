/*
 * Created on Jul 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFCommentItem extends DocItem {
    
    private String contents = "";
    
    CFCommentItem (int lineNumber, int start, int end, String contents) {

        super(lineNumber,start,end,"comment");
        this.contents = contents;
        
    }
    
    
    public String getContents() {
        return contents;
    }
    
    
    
}
