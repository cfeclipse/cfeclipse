/*
 * Created on May 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.snips;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Stephen Milligan
 *
 */
public class SnipWriter {
	
	File snippetFile, parentFolder;
	String snippetType;
	String fileExtension;
	/**
	 * 
	 */
	public SnipWriter(File parentFolder, String snippetType) {
		super();
		this.parentFolder = parentFolder;
		this.snippetType = snippetType;
		if (snippetType == SnipTreeView.DREAMWEAVER_SNIP_TYPE) {
			fileExtension = "." + SnipTreeView.DW_SNIP_EXT;
		}
		else {
			fileExtension = "." + SnipTreeView.CFE_SNIP_EXT;
		}
	}
	
	
	public void writeSnippet(String snippetName, String snippetDescription, String startText, String endText) {
		
		
		File snippetFile = new File(parentFolder.toString() + File.separator + snippetName + fileExtension);
		String snippetContents = createFormattedSnip(snippetName,snippetDescription, startText,endText);
		try {
			if (!snippetFile.isFile()) {
				snippetFile.createNewFile();
			}
			FileWriter writer = new FileWriter(snippetFile);
			writer.write(snippetContents);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	
	
	private String createFormattedSnip(String snippetName, String snippetDescription, String startText, String endText) {
		String snippetContents;
		if (snippetType == SnipTreeView.CFECLIPSE_SNIP_TYPE) {
			snippetContents = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
			snippetContents += "<snippet>\n";
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
		File newFolder = new File(parentFolder.toString() + File.separator + folderName);
		newFolder.mkdir();
	}
	
}
