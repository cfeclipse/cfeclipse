/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: TagItem.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.views.dictionary;


class TagItem extends DictionaryItem {
    
    
    /**
     * @param name
     */
    public TagItem(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
    
    public String getHelp(){
       
        return super.getDictionary().getTag(super.getName()).getHelp();
        
        
    }
   
}