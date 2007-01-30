/*
 * Created on Apr 20, 2004
 *
 */
package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.eclipse.jface.preference.IPreferenceStore;


/**
 * 
 * This interface defines all the preferences that are available in the plugin.
 * Just getting started here, so insight delay is looking a bit lonely :-/
 *  TODO: This seems to be a per-project (or should be). Check out this is working correctly.
 *  
 *  
 * @author Stephen Milligan 
 */
public class CFMLPreferenceConstants extends AbstractPreferenceConstants {
	
	/** Preference key identifier for path to the directory that contains your snippets */
	public static final String P_SNIPPETS_PATH 			= "snippetPath";
	
	/** Preference key identifier for the project to use to store TemplateProkects */
	public static final String P_TEMPLATE_PROJECT_PATH 	= "templateProjectsPath";
	
	/** Preference key identifier for the url of the project. */
	public static final String P_PROJECT_URL 				= "projectURL";
	
	
	/** Preference key identifier for tabbed browsing */
	public static final String P_TABBED_BROWSER			= "tabbedBrowsing";
	
	/** this is the per project setting for their dictionary selection */
    public static final String P_CFML_DICTIONARY = "cfmlDictionary";
    
    /** the default key to the dictionary to use in this project.
     * @see DictionaryManager 
     */
    public static final String P_CFML_DICTIONARY_DEFAULT = "cfmx701";

	/**
	 * this is public because the browser uses it on errors 
	 */
	public static final String DEFAULT_PROJECT_URL = "http://livedocs.macromedia.com";
	
	
	
	/**
	 * Sets up the default values for preferences managed by {@link CFMLPreferencesPage} .
	 * <ul>
	 * <li>P_SNIPPETS_PATH - CFMLPlugin.getDefault().getStateLocation().toString()</li>
	 * <li>P_PROJECT_URL - DEFAULT_PROJECT_URL</li>
	 * <li>P_TABBED_BROWSER - false</li>
	 * <li>P_CFML_DICTIONARY - "cfml.xml"</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_SNIPPETS_PATH,CFMLPlugin.getDefault().getStateLocation().toString()+"/snippets");
		store.setDefault(P_PROJECT_URL,DEFAULT_PROJECT_URL);
		store.setDefault(P_TABBED_BROWSER,false);
		store.setDefault(P_CFML_DICTIONARY,"cfml.xml");
		store.setDefault(P_TEMPLATE_PROJECT_PATH, "");
		
	}
	
}
