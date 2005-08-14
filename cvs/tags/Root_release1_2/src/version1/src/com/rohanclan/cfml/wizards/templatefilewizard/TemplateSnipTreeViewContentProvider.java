/*
 * Created on Feb 12, 2005
 * by Christopher Bradford
 *
 */
package com.rohanclan.cfml.wizards.templatefilewizard;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import com.rohanclan.cfml.views.snips.SnipTreeViewContentProvider;

/**
 * @author Christopher Bradford
 *
 */
public class TemplateSnipTreeViewContentProvider extends SnipTreeViewContentProvider {

	protected static FileFilter snippetfilter = new TemplateSnippetFileFilter(false);

	/**
     * @param root
     */
    public TemplateSnipTreeViewContentProvider(File root) {
        super(root);
    }

    public Object[] getElements(Object inputElement) {
        // return super.getElements(inputElement);
		if(inputElement instanceof File)
		{
			if(((File)inputElement).isDirectory())
				return getFlattenedElements((File)inputElement).toArray();
		}
		
		return EMPTY_ARRAY;
    }

    private ArrayList getFlattenedElements(Object inputElement) {
        ArrayList allFiles = new ArrayList();
        if (inputElement instanceof File) {
            File currentFile = ((File)inputElement);
            if (currentFile.isDirectory()) {
                File[] templateSnips = currentFile.listFiles(snippetfilter);
                for (int i=0;i<templateSnips.length;i++) {
                    allFiles.add(templateSnips[i]);
                }
                File[] childDirs = currentFile.listFiles(new FileFilter() {
                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        }
                        return false;
                    }
                });
                for (int i=0;i<childDirs.length;i++) {
                    allFiles.addAll(getFlattenedElements(childDirs[i]));
            }
        }
            //return getFlattenedElements
        }
        return allFiles; // temporary
    }
}
