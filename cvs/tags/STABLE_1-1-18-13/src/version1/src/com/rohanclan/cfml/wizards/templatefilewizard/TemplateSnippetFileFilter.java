/*
 * Created on Feb 12, 2005
 * by Christopher Bradford
 *
 */
package com.rohanclan.cfml.wizards.templatefilewizard;

import java.io.File;
import java.io.FileFilter;

import com.rohanclan.cfml.views.snips.SnipReader;

/**
 * @author Christopher Bradford
 *
 */
public class TemplateSnippetFileFilter implements FileFilter {

    private SnipReader snipReader;
    private boolean showDirectories;

    /**
     * 
     */
    public TemplateSnippetFileFilter() {
        this(true);
    }

    public TemplateSnippetFileFilter(boolean showDirectories) {
        super();
        this.snipReader = new SnipReader();
        this.showDirectories = showDirectories;
    }
    
    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
		String sflower = f.getAbsoluteFile().toString().toLowerCase(); 
		if(sflower.endsWith(".xml")) {
		    // Check to see if it's a file template
		    snipReader.read((f).getAbsolutePath());
		    if (snipReader.isFileTemplate()) {
		        return true;
		    }
		}
		if (f.isDirectory() && showDirectories) {
		    return true;
		}
		return false;
    }
	
	public String getDescription() {
		return "A snippet file filter; returns only template snippets";
	}

}
