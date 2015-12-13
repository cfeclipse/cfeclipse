/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Denny Valliant
 * 
 */
public class CFLintPreferenceConstants extends AbstractPreferenceConstants {
	
	/** enable cflint builder **/
	public static final String P_CFLINT_ENABLED = "__cflint_enabled";
	

	/**
	 * Sets up the default values for preferences managed by {@link FoldingPreferencePage} .
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_CFLINT_ENABLED, false);
	}

}