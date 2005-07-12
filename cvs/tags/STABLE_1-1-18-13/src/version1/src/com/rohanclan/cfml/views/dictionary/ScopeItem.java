/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: ScopeItem.java
 * Description	:
 * 
 */
package com.rohanclan.cfml.views.dictionary;


class ScopeItem extends DictionaryItem {
   
    public ScopeItem(String name){
        super(name);
    } 
    public String getHelp(){
        return super.getName();
    }
   
}