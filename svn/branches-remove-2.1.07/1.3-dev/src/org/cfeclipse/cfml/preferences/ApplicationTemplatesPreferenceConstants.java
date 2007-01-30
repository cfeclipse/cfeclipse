package org.cfeclipse.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

public class ApplicationTemplatesPreferenceConstants {
	public static final String P_APPLICATION_TEMPLATE_PROJECT = "applicationTemplateProject";
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_APPLICATION_TEMPLATE_PROJECT,""); 
		
	}
}
