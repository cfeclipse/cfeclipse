/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScribblePadPreferenceConstants {
	// Scribble pad stuff
	public static final String P_SCRIBBLE_PROJECT_NAME = "scribbleProjectID";

	public static final String P_SCRIBBLE_PAD_FILE = "scribblePad";

	public static final String P_SCRIBBLE_CLEAR_ON_LOAD = "clearScribblePadOnLoad";

	public static final String P_SCRIBBLE_LOAD_BROWSER = "loadBrowser";

	public static final String P_SCRIBBLE_URL = "scribbleURL";

	// Scribble pad defaults
	public static final String DEFAULT_SCRIBBLE_PAD_FILE = "scribble.cfm";

	public static final boolean DEFAULT_SCRIBBLE_CLEAR_ON_LOAD = false;

	public static final String DEFAULT_SCRIBBLE_PROJECT_ID = "scribble";

	public static final String DEFAULT_SCRIBBLE_URL = "";

	public static final boolean DEFAULT_SCRIBBLE_LOAD_BROWSER = true;


	/**
	 * Sets up the default values for preferences managed by {@link ScribblePadPreferencePage} .
	 * <ul>
	 * <li>P_SCRIBBLE_PROJECT_NAME - ""</li>
	 * <li>P_SCRIBBLE_PAD_FILE - "scribble.cfm"</li>
	 * <li>P_SCRIBBLE_CLEAR_ON_LOAD - false</li>
	 * <li>P_SCRIBBLE_LOAD_BROWSER - true</li>
	 * <li>P_SCRIBBLE_URL - ""</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_SCRIBBLE_PROJECT_NAME,""); 
		store.setDefault(P_SCRIBBLE_PAD_FILE,"scribble.cfm"); 
		store.setDefault(P_SCRIBBLE_CLEAR_ON_LOAD,false); 
		store.setDefault(P_SCRIBBLE_LOAD_BROWSER,true);  
		store.setDefault(P_SCRIBBLE_URL,""); 
	}

}