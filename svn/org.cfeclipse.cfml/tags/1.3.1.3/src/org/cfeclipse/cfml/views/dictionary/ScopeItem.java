/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: ScopeItem.java
 * Description	:
 * 
 */
package org.cfeclipse.cfml.views.dictionary;

import java.util.Set;

import org.cfeclipse.cfml.dictionary.ScopeVar;


class ScopeItem extends DictionaryItem {
   private ScopeVar scopevar;
    public ScopeItem(ScopeVar scope){
        super(scope.getValue());
        this.scopevar = scope;
    } 
    public String getHelp(){
    	return scopevar.getHelp();
    }
   
}