/*
 * Created on 	: 05-Nov-2004
 * Created by 	: Mark Drew
 * File		  	: FunctionItem.java
 * Description	:
 * 
 */
package org.cfeclipse.cfml.views.dictionary;

import org.cfeclipse.cfml.dictionary.Function;


class FunctionItem extends DictionaryItem{
  
    private Function function;

    public FunctionItem(Function currFunc){
        super(currFunc.getName());
        this.function = currFunc;
    } 
    
    public String getHelp(){

      return function.getHelp();
    }
   
    public Function getFunction(){
    	
    	return function;
    }
}