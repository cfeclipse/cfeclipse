/*
 * Created on May 6, 2004
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.eclipse.core.runtime.IPath;
/**
 * @author Stephen Milligan
 */
public class SnipWriter {
	
	File snippetFile, parentFolder;
	String snippetType;
	String fileExtension;
	IPath snipBase;
	/**
	 * 
	 */
	public SnipWriter(File parentFolder, String snippetType, IPath snipBase) {
		super();
		
		//if the parentFolder is null, then we are at the root surely?
		if(parentFolder == null){
			this.parentFolder = (File)snipBase;
		}else{
			this.parentFolder = parentFolder;
		}
		this.snippetType = snippetType;
		this.snipBase = snipBase;
		if (snippetType == SnipTreeView.DREAMWEAVER_SNIP_TYPE) {
			fileExtension = "." + SnipTreeView.DW_SNIP_EXT;
		}
		else {
			fileExtension = "." + SnipTreeView.CFE_SNIP_EXT;
		}
	}
	
	
	public void writeSnippet(String snippetName, String snippetKeyCombo, String snippetDescription, String startText, String endText, boolean useAsTemplate, String templateExtension) {
		
		
		File snippetFile = new File(parentFolder.toString() + File.separator + snippetName + fileExtension);
		String snippetContents = createFormattedSnip(snippetName,snippetDescription, startText,endText,useAsTemplate,templateExtension);
		try {
		    
			if (!snippetFile.isFile()) {
				snippetFile.createNewFile();
			}
			FileWriter writer = new FileWriter(snippetFile);
			writer.write(snippetContents);
			writer.close();
			if (snippetKeyCombo.length() > 0) {
			    SnipKeyCombos snipKeyCombos = new SnipKeyCombos();

				String filepath = snippetFile.getAbsolutePath().replaceAll("\\\\","/");
				String basePath = snipBase.toString();
				
				String relativePath = filepath.replaceFirst(basePath,"");
				
			    String sequence = snipKeyCombos.getSequence(relativePath);
			    
			    if (sequence != null){
			        snipKeyCombos.clearKeyCombo(sequence);
			    }
			    snipKeyCombos.setKeyCombo(snippetKeyCombo,relativePath);
			}
		}
		catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	
	
	private String createFormattedSnip(String snippetName, String snippetDescription, String startText, String endText, boolean useAsTemplate, String templateExtension) {
		String snippetContents;
		String useSnippetAsTemplate = useAsTemplate ? "true" : "false";
		if (snippetType == SnipTreeView.CFECLIPSE_SNIP_TYPE) {
			snippetContents = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
			snippetContents += "<snippet filetemplate=\"" + useSnippetAsTemplate + "\" extension=\"" + templateExtension + "\">\n";
			snippetContents += "<name>"+snippetName+"</name>\n";
			snippetContents += "<help>"+snippetDescription+"</help>\n"; 
			snippetContents += "<starttext><![CDATA["+startText+"]]></starttext>\n";
			snippetContents += "<endtext><![CDATA["+endText+"]]></endtext>\n";
			snippetContents += "</snippet>";
		}
		else {
			snippetContents = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
			snippetContents += "<snippet name = \""+snippetName+"\" description = \""+snippetDescription+"\" preview=\"html\" type=\"block\">\n";
			snippetContents += "<insertText location=\"beforeSelection\"><![CDATA["+startText+"]]></insertText>\n";
			snippetContents += "<insertText location=\"afterSelection\"><![CDATA["+endText+"]]></insertText>\n";
			snippetContents += "</snippet>\n";

		}
		return snippetContents;
	}
	
	public void writeFolder (String folderName) {
		/**
		 * This bit of code is throwing an exception when you first install CFE. This cases a NPE.
		 */
		File newFolder = new File(parentFolder.toString() + File.separator + folderName);
		newFolder.mkdir();
	}
	
}
