/*
 * Created on Jul 12, 2004
 *
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
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SnipKeyCombos {
    
    private Properties keyCombos = new Properties();
    private String keyComboFilePath = "";
    private String snippetFilePath = "";
    private static String HEADER_TEXT = "These key combos are used by the cfeclipse plugin.";
    
    public SnipKeyCombos() {
        CFMLPropertyManager propertyManager = new CFMLPropertyManager(); 
        this.snippetFilePath = propertyManager.snippetsPath();
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
