/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: DictionaryItem.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.views.dictionary;

import com.rohanclan.cfml.dictionary.SyntaxDictionary;

/**
 * @author Mark Drew 
 *
 */
class DictionaryItem extends TreeParent{
    private SyntaxDictionary dictionary;
    
  
    /**
     * @param name
     */
    public DictionaryItem(String name) {
        super(name);
       
    }
    /**
     * @return Returns the dictionary.
     */
    public SyntaxDictionary getDictionary() {
        return dictionary;
    }
    /**
     * @param dictionary The dictionary to set.
     */
    public void setDictionary(SyntaxDictionary dictionary) {
        this.dictionary = dictionary;
    }
    
   
}
