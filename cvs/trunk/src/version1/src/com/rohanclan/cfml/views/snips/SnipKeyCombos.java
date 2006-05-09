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
package com.rohanclan.cfml.views.snips;


import java.util.Properties;
import java.util.Enumeration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.rohanclan.cfml.properties.CFMLPropertyManager;

/**
 * @author Stephen Milligan
 */
public class SnipKeyCombos {
    
    private Properties keyCombos = new Properties();
    private String keyComboFilePath = "";
    private String snippetFilePath = "";
    private static String HEADER_TEXT = "These key combos are used by the cfeclipse plugin.";
    
    public SnipKeyCombos() {
        CFMLPropertyManager propertyManager = new CFMLPropertyManager(); 
        this.snippetFilePath = propertyManager.defaultSnippetsPath();
        this.keyComboFilePath = this.snippetFilePath + "/keyCombos.properties";
        loadKeyCombos();
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
