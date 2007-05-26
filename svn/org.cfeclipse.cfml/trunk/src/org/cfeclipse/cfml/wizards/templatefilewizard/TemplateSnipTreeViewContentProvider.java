/*
 * Created on Feb 12, 2005
 * by Christopher Bradford
 *
 */
package org.cfeclipse.cfml.wizards.templatefilewizard;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.cfeclipse.cfml.views.snips.SnipTreeViewContentProvider;
import org.cfeclipse.snipex.SnipEx;
import org.cfeclipse.snipex.Library;
import org.cfeclipse.snipex.Snippet;

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
		if(inputElement instanceof File) {
			if(((File)inputElement).isDirectory()) {
				//return getFlattenedElements((File)inputElement).toArray();
				Object[] files = getFlattenedElements((File)inputElement).toArray();
				
				// Also get snippets from snipex locations
				/* TODO: When we can handle file snippets, put this back in
				try {
					URL url = new URL("http://localhost/eclipse/snipex/web/SnipEx.cfc");
					files = appendArrays(files, getFlattenedElements( new SnipEx( url ) ).toArray());	
				} catch(MalformedURLException e) {
					System.err.println("Snipex URL failed:"+e);
					return files;
				} catch(Exception e) {
					System.err.println("Snipex failed to load:"+e);
					return files;
				}
				*/
				
				return files;	
			}
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
        } else if(inputElement instanceof SnipEx || inputElement instanceof Library) {
        	Library lib = ((Library)inputElement);
        	Iterator it = lib.getSnippets().iterator();
        	
        	while( it.hasNext() ) {
        		Snippet snip = (Snippet)it.next();
        		if( snip.isTemplate() ) {
        			allFiles.add(snip);
        		}
        	}
        	
        }
        return allFiles; // temporary
    }

    /** 
     * Utility method to append two arrays
     * @param array1 First array to append
     * @param array2 Second array to append
     * @return A new array which is a combination of the two arrays
     */
	public static Object[] appendArrays(Object[] array1, Object[] array2) {
		Object[] newArray = new Object[array1.length + array2.length];
		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);
		return newArray;
	}
}
