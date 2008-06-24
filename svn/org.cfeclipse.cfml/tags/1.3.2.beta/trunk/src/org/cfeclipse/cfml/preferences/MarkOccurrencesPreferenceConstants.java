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
 * Preference constants for {@link org.cfeclipse.cfml.preferences.MarkOccurrencesPreferencePage}
 */
public class MarkOccurrencesPreferenceConstants extends AbstractPreferenceConstants {

	/**
	 * Preference key identifier for occurrence marking 
	 * That is, highlighting occurrences for selections in the editor
	 */
	public static final String P_MARK_OCCURRENCES = "__markOccurences";
	public static final String P_BREAK_WORD_CHARS = "__breakWordChars";
	public static final String P_PART_OF_WORD_CHARS = "__partOfWordChars";
	public static final String P_PART_OF_WORD_CHARS_ALT = "__partOfWordCharsAlt";
	public static final String P_BREAK_WORD_CHARS_ALT = "__breakWordCharsAlt";
	public static final String P_PART_OF_WORD_CHARS_SHIFT = "__partOfWordCharsShift";
	public static final String P_BREAK_WORD_CHARS_SHIFT = "__breakWordCharsShift";

	/**
	 * Sets the default values for the preferences managed by {@link MarkOccurrencesPreferencePage}:
	 * <ul>
	 * <li>P_MARK_OCCURRENCES - false</li>
	 * </ul>
	 * 
	 */
	public static void setDefaults(IPreferenceStore store) {
		store.setDefault(P_MARK_OCCURRENCES,true);
		store.setDefault(P_PART_OF_WORD_CHARS,"");
		store.setDefault(P_BREAK_WORD_CHARS,"-");
		store.setDefault(P_PART_OF_WORD_CHARS_ALT,".-");
		store.setDefault(P_BREAK_WORD_CHARS_ALT,"_");
		store.setDefault(P_PART_OF_WORD_CHARS_SHIFT,"_");
		store.setDefault(P_BREAK_WORD_CHARS_SHIFT,"");
	}


}