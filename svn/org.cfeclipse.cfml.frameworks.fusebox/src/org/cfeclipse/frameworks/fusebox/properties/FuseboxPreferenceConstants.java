/*
 * Created on Apr 20, 2004
 *
 */
package org.cfeclipse.frameworks.fusebox.properties;

import org.cfeclipse.frameworks.fusebox.FuseboxPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.cfeclipse.cfml.preferences.AbstractPreferenceConstants;

/**
 * 
 * This interface defines all the preferences that are available in the plugin.
 * Just getting started here, so insight delay is looking a bit lonely :-/
 * 
 * @author Stephen Milligan 
 */
public class FuseboxPreferenceConstants extends AbstractPreferenceConstants {
	
	/** Preference key identifier for path to the directory that contains your snippets */
	public static final String P_FUSEBOX_PATH 	= "fuseboxpath";
	
	
	/** Not currently used. */
	public static final String P_FUSBOX_VERSION = "fbxversion";


	/**
	 * Sets up the default values for preferences managed by {@link CFMLPreferencesPage} .
	 * <ul>
	 * <li>P_FUSEBOX_PATH - FuseboxPlugin.getDefault().getStateLocation().toString())</li>
	 * <li>P_FUSBOX_VERSION - org.cfeclipse.frameworks.fusebox3.parser.FBX3parser</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_FUSEBOX_PATH,FuseboxPlugin.getDefault().getStateLocation().toString());
		store.setDefault(P_FUSBOX_VERSION,"org.cfeclipse.frameworks.fusebox3.parser.FBX3parser");
	}
	
}
