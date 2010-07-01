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
public class FoldingPreferenceConstants extends AbstractPreferenceConstants {
	//////////////////////////////////////
	// Folding prefs
	public static final String P_ENABLE_CODE_FOLDING = "enableCodeFolding";

	public static final String P_MINIMUM_CODE_FOLDING_LINES = "minimumCodeFoldingLines";

	// CFML Comments
	public static final String P_FOLDING_CFMLCOMMENTS_FOLD = "folding.cfmlcomments.fold";

	public static final String P_FOLDING_CFMLCOMMENTS_COLLAPSE = "folding.cfmlcomments.collapse";

	// HTML Comments
	public static final String P_FOLDING_HTMLCOMMENTS_FOLD = "folding.htmlcomments.fold";

	public static final String P_FOLDING_HTMLCOMMENTS_COLLAPSE = "folding.htmlcomments.collapse";

	// tag1
	public static final String P_FOLDING_TAG1_FOLD = "folding.tag1.fold";

	public static final String P_FOLDING_TAG1_COLLAPSE = "folding.tag1.collapse";

	public static final String P_FOLDING_TAG1_NAME = "folding.tag1.name";

	// tag2
	public static final String P_FOLDING_TAG2_FOLD = "folding.tag2.fold";

	public static final String P_FOLDING_TAG2_COLLAPSE = "folding.tag2.collapse";

	public static final String P_FOLDING_TAG2_NAME = "folding.tag2.name";

	// tag3
	public static final String P_FOLDING_TAG3_FOLD = "folding.tag3.fold";

	public static final String P_FOLDING_TAG3_COLLAPSE = "folding.tag3.collapse";

	public static final String P_FOLDING_TAG3_NAME = "folding.tag3.name";

	// tag4
	public static final String P_FOLDING_TAG4_FOLD = "folding.tag4.fold";

	public static final String P_FOLDING_TAG4_COLLAPSE = "folding.tag4.collapse";

	public static final String P_FOLDING_TAG4_NAME = "folding.tag4.name";

	// tag5
	public static final String P_FOLDING_TAG5_FOLD = "folding.tag5.fold";

	public static final String P_FOLDING_TAG5_COLLAPSE = "folding.tag5.collapse";

	public static final String P_FOLDING_TAG5_NAME = "folding.tag5.name";

	// tag6
	public static final String P_FOLDING_TAG6_FOLD = "folding.tag6.fold";

	public static final String P_FOLDING_TAG6_COLLAPSE = "folding.tag6.collapse";

	public static final String P_FOLDING_TAG6_NAME = "folding.tag6.name";

	// tag7
	public static final String P_FOLDING_TAG7_FOLD = "folding.tag7.fold";

	public static final String P_FOLDING_TAG7_COLLAPSE = "folding.tag7.collapse";

	public static final String P_FOLDING_TAG7_NAME = "folding.tag7.name";

	// tag8
	public static final String P_FOLDING_TAG8_FOLD = "folding.tag8.fold";

	public static final String P_FOLDING_TAG8_COLLAPSE = "folding.tag8.collapse";

	public static final String P_FOLDING_TAG8_NAME = "folding.tag8.name";

	public static final String P_PERSIST_FOLD_STATE = "persistFoldState";

	/**
	 * Sets up the default values for preferences managed by {@link FoldingPreferencePage} .
	 * <ul>
	 * <li>P_ENABLE_CODE_FOLDING - true</li>
	 * <li>P_MINIMUM_CODE_FOLDING_LINES - 3</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_ENABLE_CODE_FOLDING,true);
		store.setDefault(P_PERSIST_FOLD_STATE,false);
		store.setDefault(P_MINIMUM_CODE_FOLDING_LINES,3);
		store.setDefault(P_FOLDING_CFMLCOMMENTS_FOLD,true);
		store.setDefault(P_FOLDING_CFMLCOMMENTS_COLLAPSE,true);
		store.setDefault(P_FOLDING_HTMLCOMMENTS_FOLD,true);
		store.setDefault(P_FOLDING_HTMLCOMMENTS_COLLAPSE,true);
		store.setDefault(P_FOLDING_TAG1_NAME,"cffunction");
		store.setDefault(P_FOLDING_TAG1_FOLD,true);
		store.setDefault(P_FOLDING_TAG1_COLLAPSE,true);
		store.setDefault(P_FOLDING_TAG2_NAME,"cfquery");
		store.setDefault(P_FOLDING_TAG2_FOLD,true);
		store.setDefault(P_FOLDING_TAG2_COLLAPSE,false);
		store.setDefault(P_FOLDING_TAG3_NAME,"cfscript");
		store.setDefault(P_FOLDING_TAG3_FOLD,true);
		store.setDefault(P_FOLDING_TAG3_COLLAPSE,false);
		store.setDefault(P_FOLDING_TAG4_NAME,"cfloop");
		store.setDefault(P_FOLDING_TAG4_FOLD,true);
		store.setDefault(P_FOLDING_TAG4_COLLAPSE,false);
		store.setDefault(P_FOLDING_TAG5_NAME,"cfscript");
		store.setDefault(P_FOLDING_TAG5_FOLD,true);
		store.setDefault(P_FOLDING_TAG5_COLLAPSE,false);
		store.setDefault(P_FOLDING_TAG6_NAME,"cfif");
		store.setDefault(P_FOLDING_TAG6_FOLD,true);
		store.setDefault(P_FOLDING_TAG6_COLLAPSE,false);
		store.setDefault(P_FOLDING_TAG7_NAME,"cfswitch");
		store.setDefault(P_FOLDING_TAG7_FOLD,true);
		store.setDefault(P_FOLDING_TAG7_COLLAPSE,false);
		store.setDefault(P_FOLDING_TAG8_NAME,"cfcase");
		store.setDefault(P_FOLDING_TAG8_FOLD,true);
		store.setDefault(P_FOLDING_TAG8_COLLAPSE,false);
	}
}