/*
 * Created on Sep 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.swt.widgets.*;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SnipVarItem {
    
    private String name;
    private String[] values;
    private String replacement;
    private String original;
    private Combo combo;
    private Text text;
    
    SnipVarItem(String name, String[] values, String original) {
        this.name = name;
        this.values = values;
        this.original = original;
        this.replacement = original;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getValues() {
        return this.values;
    }
    
    public String getOriginal() {
        return this.original;
    }
    
    public String getReplacement() {
        return this.replacement;
    }
    
    public void setCombo(Combo combo) {
        this.combo = combo;
    }
    
    public void setText(Text text) {
        this.text = text;
    }
    
    public void setReplacement() {
        if (combo != null) {
            this.replacement = combo.getText();
        }
        else if (text != null) {
            this.replacement = text.getText();
        }
    }
    
}
