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
			fileExtension = ".csn";
		}
		else {
			fileExtension = ".xml";
		}
	}
	
	
	public void writeSnippet(String snippetName, String startText, String endText) {
		
		
		//TODO: Need to find out how the cfeclipse snippet file format works.
		if (snippetType != SnipTreeView.DREAMWEAVER_SNIP_TYPE) {
			return;
		}
		
		File snippetFile = new File(parentFolder.toString() + File.separator + snippetName + fileExtension);
		String snippetContents = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		snippetContents += "<snippet name = \""+snippetName+"\" description = \"\" preview=\"html\" type=\"block\">\n";
		snippetContents += "<insertText location=\"beforeSelection\">\n";
		snippetContents += "<![CDATA["+startText+"]]>\n";
		snippetContents += "</insertText>\n";
		snippetContents += "<insertText location=\"afterSelection\">\n";
		snippetContents += "<![CDATA["+endText+"]]>\n";
		snippetContents += "</insertText>\n";
		snippetContents += "</snippet>\n";
		System.out.println(snippetFile.getAbsolutePath());
		try {
			if (!snippetFile.isFile()) {
				snippetFile.createNewFile();
			}
			FileWriter writer = new FileWriter(snippetFile);
			writer.write(snippetContents);
			writer.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	public void writeFolder (String folderName) {
		File newFolder = new File(parentFolder.toString() + File.separator + folderName);
		newFolder.mkdir();
	}
	
	
}
