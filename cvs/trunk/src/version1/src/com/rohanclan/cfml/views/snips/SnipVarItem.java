/*
 * Created on Sep 2, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.swt.widgets.*;

/**
 * @author Stephen Milligan
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
