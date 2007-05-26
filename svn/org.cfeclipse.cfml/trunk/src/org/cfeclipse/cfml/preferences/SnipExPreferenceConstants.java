package org.cfeclipse.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

public class SnipExPreferenceConstants {
	public static final String P_SNIPEX_URL1 = "snipExURL1";
	public static final String P_SNIPEX_URL2 = "snipExURL2";
	public static final String P_SNIPEX_URL3 = "snipExURL3";
	public static final String P_SNIPEX_URL4 = "snipExURL4";
	public static final String P_SNIPEX_URL5 = "snipExURL5";
	public static final String P_SNIPEX_URL6 = "snipExURL6";
	public static final String P_SNIPEX_URL7 = "snipExURL7";
	public static final String P_SNIPEX_URL8 = "snipExURL8";
	
	public static final String DEFAULT_SNIPEX_URL1 = "http://www.cfeclipse.org/snipex/web/SnipEx.cfc";
	public static final String DEFAULT_SNIPEX_URL2 = "http://cfunit.sourceforge.net/snipex";
	public static final String DEFAULT_SNIPEX_URL3 = "";
	public static final String DEFAULT_SNIPEX_URL4 = "";
	public static final String DEFAULT_SNIPEX_URL5 = "";
	public static final String DEFAULT_SNIPEX_URL6 = "";
	public static final String DEFAULT_SNIPEX_URL7 = "";
	public static final String DEFAULT_SNIPEX_URL8 = "";
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_SNIPEX_URL1,DEFAULT_SNIPEX_URL1);
		store.setDefault(P_SNIPEX_URL2,DEFAULT_SNIPEX_URL2); 
		store.setDefault(P_SNIPEX_URL3,DEFAULT_SNIPEX_URL3); 
		store.setDefault(P_SNIPEX_URL4,DEFAULT_SNIPEX_URL4); 
		store.setDefault(P_SNIPEX_URL5,DEFAULT_SNIPEX_URL5); 
		store.setDefault(P_SNIPEX_URL5,DEFAULT_SNIPEX_URL6); 
		store.setDefault(P_SNIPEX_URL5,DEFAULT_SNIPEX_URL7); 
		store.setDefault(P_SNIPEX_URL5,DEFAULT_SNIPEX_URL8); 
	}
	
}
