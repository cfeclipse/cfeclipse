/*
 * Created on Jul 12, 2004
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
package org.cfeclipse.cfml.snippets.views.snips;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.cfeclipse.cfml.snippets.SnippetPlugin;
import org.cfeclipse.cfml.snippets.properties.CFMLPropertyManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;


/**
 * @author Stephen Milligan
 */
public class SnipKeyCombos {
    
    private Properties keyCombos = new Properties();
    private String keyComboFilePath = "";
    private String snippetFilePath = "";
    private static String HEADER_TEXT = "These key combos are used by the cfeclipse plugin.";
    
    public SnipKeyCombos() {
		CFMLPropertyManager propertyManager = SnippetPlugin.getDefault().getPropertyManager();
		this.snippetFilePath = new File(propertyManager.getSnippetsPath()).toString();
		this.keyComboFilePath = this.snippetFilePath + "/keyCombos.properties";
       
        loadKeyCombos();
    }
    
    public String getKeyCombosFilePath() {
    	return this.keyComboFilePath;
    }

    private void loadKeyCombos() {
        try {
            FileInputStream input = new FileInputStream(this.keyComboFilePath);
            this.keyCombos.load(input);
        }
        catch (IOException e) {
            try {
	            FileOutputStream output = new FileOutputStream(this.keyComboFilePath);
	            keyCombos.store(output,HEADER_TEXT);
            }
            catch (IOException ex) {
        		MessageBox dialog = new MessageBox(null,SWT.ICON_ERROR);
        		dialog.setMessage("Error opening keyCombos : "+this.keyComboFilePath);
                ex.printStackTrace();
            }
        }
    }
    
    public void setKeyCombo(String sequence, String snippetFile){
        this.keyCombos.setProperty(sequence,snippetFile);
        try {
	        FileOutputStream output = new FileOutputStream(this.keyComboFilePath);
	        keyCombos.store(output,HEADER_TEXT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getKeyCombo(String sequence){
       return this.keyCombos.getProperty(sequence);
    }
    
    public void clearKeyCombo(String sequence){
       this.keyCombos.remove(sequence);
    }

    public String getSnippetFolder() {
        return this.snippetFilePath;
    }
    
    public String getSequence(String fileName) {
        if (this.keyCombos.containsValue(fileName)) {
           Enumeration e = this.keyCombos.propertyNames();
           String sequence;
           while (e.hasMoreElements()){
               sequence = e.nextElement().toString();
               if (this.keyCombos.getProperty(sequence).equals(fileName)) {
                   return sequence;
               }
           }
           
        }
        return null;
    }
    
}
