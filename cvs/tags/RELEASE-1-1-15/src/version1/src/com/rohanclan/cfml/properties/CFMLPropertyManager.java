/*
 * Created on Apr 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.properties;


import org.eclipse.jface.preference.PreferenceStore;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;
import java.io.IOException;

/**
 * @author Stephen Milligan
 *
 * This controls the properies for the per project settings
 */
public class CFMLPropertyManager implements ICFMLPreferenceConstants {
	/**
	 * 
	 */
	
	private PreferenceStore store;
	private CFMLPreferenceManager  preferenceManager;
	
	public CFMLPropertyManager() {
		super();
		this.store = CFMLPlugin.getDefault().getPropertyStore();
		try {
			store.load();
		}
		catch (Exception e) {
			System.err.println("Couldn't load property store");
		}
		this.preferenceManager = new CFMLPreferenceManager();
	}
	
	
	public void initializeDefaultValues() {
        store.setDefault(P_SNIPPETS_PATH, preferenceManager.snippetsPath());
        store.setDefault(P_PROJECT_URL, preferenceManager.projectURL());
	}
	
	
	public String snippetsPath() {
		return store.getString(ICFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}
	
	public String defaultSnippetsPath() {
		return preferenceManager.snippetsPath();
	}
	
	public void setSnippetsPath(String path) {
		store.setValue(ICFMLPreferenceConstants.P_SNIPPETS_PATH,path);
		try {
			store.save();
		}
		catch (IOException e) {
			System.err.println("Failed to save property store " + e.getMessage());
		}
	}
	
	
	public String projectURL() {
		return store.getString(ICFMLPreferenceConstants.P_PROJECT_URL).trim();
	}
	
	public String defaultProjectURL() {
		return preferenceManager.projectURL();
	}
	
	public void setProjectURL(String path) {
		store.setValue(ICFMLPreferenceConstants.P_PROJECT_URL,path);
		try {
			store.save();
		}
		catch (IOException e) {
			System.err.println("Failed to save property store " + e.getMessage());
		}
	}
}
