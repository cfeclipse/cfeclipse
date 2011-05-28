/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: TagItem.java
 * Description	:
 * 
 */
package org.cfeclipse.cfml.views.dictionary;

import org.cfeclipse.cfml.dictionary.Tag;


/**
 * @author mdrew
 * 
 * This class helps the viewer to create tag items and get their help info (currently)
 * As a test I am adding some more functions so that we can actually generate some more methods to create the 
 * String that will be used to paste into the editor
 */
public class TagItem extends DictionaryItem {
    
    private Tag tag;
    /**
     * @param currTag
     */
    public TagItem(Tag currTag) {
        super(currTag.getName());
        this.tag = currTag;
    }
    
    public String getHelp(){
    
        return tag.getHelp();
    }
   
    public Tag getTag(){
    	return tag;
    }
}