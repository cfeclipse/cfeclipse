/*
 * Created on Apr 20, 2004
 *
 */
package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import com.rohanclan.cfml.CFMLPlugin;

/**
 * 
 * This interface defines all the preferences that are available in the plugin.
 * Just getting started here, so insight delay is looking a bit lonely :-/
 * 
 * @author Stephen Milligan 
 */
public class CFMLPreferenceConstants extends AbstractPreferenceConstants {
	
	/** Preference key identifier for path to the directory that contains your snippets */
	public static final String P_SNIPPETS_PATH 			= "snippetPath";
	
	
	/** Preference key identifier for the url of the project. */
	public static final String P_PROJECT_URL 				= "projectURL";
	
	
	/** Preference key identifier for tabbed browsing */
	public static final String P_TABBED_BROWSER			= "tabbedBrowsing";
	
	
	/** Not currently used. */
	public static final String P_CFML_DICTIONARY = "__cfmlDictionary";


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
		store.setDefault(P_SNIPPETS_PATH,CFMLPlugin.getDefault().getStateLocation().toString());
		store.setDefault(P_PROJECT_URL,DEFAULT_PROJECT_URL);
		store.setDefault(P_TABBED_BROWSER,false);
		store.setDefault(P_CFML_DICTIONARY,"cfml.xml");
	}
	
}
