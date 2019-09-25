package org.cfeclipse.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

public class BrowserPreferenceConstants {
	public static final String P_PRIMARY_BROWSER_PATH = "primaryBrowserPath";
	public static final String P_SECONDARY_BROWSER_PATH = "secondaryBrowserPath";
	public static final String P_TESTCASE_QUERYSTRING = "testCaseQueryString";

	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_PRIMARY_BROWSER_PATH, "");
		store.setDefault(P_SECONDARY_BROWSER_PATH, "");
		store.setDefault(P_TESTCASE_QUERYSTRING,"?method=runRemote");
	}
}
