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

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
/**
 * @author Stephen Milligan
 */
public class ComboModifyListener implements ModifyListener, KeyListener {
    
    Combo combo;
    int lastKey = -1;
    
    
    ComboModifyListener(Combo combo) {
        this.combo = combo;
    }
    
    
    public void modifyText(ModifyEvent event) {
        try {
        
        //if (this.lastKey == SWT.DEL || this.lastKey == SWT.BS) {
        if (this.lastKey == SWT.DEL || this.lastKey == SWT.BS || this.lastKey == -1) {
            return;
        }
            
            
        String[] items = combo.getItems();
        Point p = combo.getSize();
        int x = combo.getSelection().x;
        int y = combo.getSize().y;
       
        
        
        for (int i=0;i<items.length;i++) {
            if (items[i].startsWith(combo.getText())) {
            	   this.lastKey = -1;
                combo.select(i);
                combo.setSelection(new Point(x,y));
                
            }
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void keyPressed(KeyEvent event) {
        this.lastKey = event.keyCode;
    }
    
    public void keyReleased(KeyEvent event) {
        this.lastKey = -1;
    }
    
    
}
