/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
/**
 * @author Stephen Milligan
 *
 * Preference constants for {@link com.rohanclan.cfml.preferences.AutoIndentPreferencePage}
 */
public class AutoIndentPreferenceConstants extends AbstractPreferenceConstants {

	/** Preference key identifier for closing double quotes */
	public static final String P_AUTOCLOSE_DOUBLE_QUOTES = "__autoCloseDoubleQuotes";

	/** Preference key identifier for closing single quotes */
	public static final String P_AUTOCLOSE_SINGLE_QUOTES = "__autoCloseSingleQuotes";

	/** Preference key identifier for inserting a closing > when the user types a < */
	public static final String P_AUTOCLOSE_TAGS = "__autoCloseTags";

	/** Preference key identifier for closing "#" */
	public static final String P_AUTOCLOSE_HASHES = "__autoCloseHashes";

	/** Preference key identifier for closing "[" */
	public static final String P_AUTOCLOSE_BRACKETS = "__autoCloseBrackets";

	/** Preference key identifier for closing "(" */
	public static final String P_AUTOCLOSE_PARENS = "__autoCloseParens";

	/** Preference key identifier for inserting a closing tag */
	public static final String P_AUTOINSERT_CLOSE_TAGS = "__autoInsertCloseTags";

	/** Preference key identifier for inserting a closing tag */
	public static final String P_USE_SMART_INDENT = "__useSmartIndent";

	/**
	 * Preference key identifier for auto-indenting tags. 
	 * That is, inserting the closing tag on a new line and putting the cursor below the opening tag indented one unit.
	 */
	public static final String P_AUTOINDENT_ONTAGCLOSE = "__autoIndentOnTagClose";

	/**
	 * Sets the default values for the preferences managed by {@link AutoIndentPreferencePage}:
	 * <ul>
	 * <li>P_AUTOCLOSE_DOUBLE_QUOTES - true</li>
	 * <li>P_AUTOCLOSE_SINGLE_QUOTES - true</li>
	 * <li>P_AUTOCLOSE_TAGS - true</li>
	 * <li>P_AUTOCLOSE_HASHES - true</li>
	 * <li>P_AUTOCLOSE_BRACKETS - true</li>
	 * <li>P_AUTOCLOSE_PARENS - true</li>
	 * <li>P_AUTOINSERT_CLOSE_TAGS - true</li>
	 * <li>P_AUTOINDENT_ONTAGCLOSE - false</li>
	 * </ul>
	 * 
	 */
	public static void setDefaults(IPreferenceStore store) {
		store.setDefault(P_AUTOCLOSE_DOUBLE_QUOTES, true);
		store.setDefault(P_AUTOCLOSE_SINGLE_QUOTES, true);
		store.setDefault(P_AUTOCLOSE_TAGS, true);
		store.setDefault(P_AUTOCLOSE_HASHES, true);
		store.setDefault(P_AUTOCLOSE_BRACKETS, true);
		store.setDefault(P_AUTOCLOSE_PARENS, true);
		store.setDefault(P_AUTOINSERT_CLOSE_TAGS, true);
		store.setDefault(P_AUTOINDENT_ONTAGCLOSE,false);
		store.setDefault(P_USE_SMART_INDENT,true);
	}


}