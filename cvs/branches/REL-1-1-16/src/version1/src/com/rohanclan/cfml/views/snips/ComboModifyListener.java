/*
 * Created on Sep 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ComboModifyListener implements ModifyListener, KeyListener {
    
    Combo combo;
    int lastKey = -1;
    
    
    ComboModifyListener(Combo combo) {
        this.combo = combo;
    }
    
    
    public void modifyText(ModifyEvent event) {
        try {
        
        if (this.lastKey == SWT.DEL || this.lastKey == SWT.BS) {
            return;
        }
            
            
        String[] items = combo.getItems();
        Point p = combo.getSize();
        int x = combo.getSelection().x;
        int y = combo.getSize().y;
       
        
        
        for (int i=0;i<items.length;i++) {
            if (items[i].startsWith(combo.getText())) {
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
