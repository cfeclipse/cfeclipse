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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFMLPropertyManager {
	/**
	 * 
	 */
	
	private PreferenceStore store;
	private CFMLPreferenceManager  preferenceManager;
	
	public CFMLPropertyManager() {
		super();
		this.store = CFMLPlugin.getDefault().getPropertyStore();
		this.preferenceManager = new CFMLPreferenceManager();
	}
	
	
	public void initializeDefaultValues() {
        store.setDefault(ICFMLPreferenceConstants.P_SNIPPETS_PATH, preferenceManager.snippetsPath());
	}
	
	
	public String snippetsPath() {
		System.out.println("Snippet path retrieved from property manager " + store.getString(store.getString(ICFMLPreferenceConstants.P_SNIPPETS_PATH).trim()));
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
}
