/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.preferences;

import org.eclipse.swt.graphics.RGB;


import org.eclipse.jface.preference.IPreferenceStore;


/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HTMLColorsPreferenceConstants {

	/** Preference key identifier for html, javascript, css comments */
	public static final String P_COLOR_HTM_COMMENT = "htmCommentColor";
	
	/** Preference key identifier for html tag color */
	public static final String P_COLOR_HTM_TAG = "htmTagColor";

	/** Preference key identifier for html form tag colors (not used yet) */
	public static final String P_COLOR_HTM_FORM_TAG = "htmFormTagColor";
	
	/** Preference key identifier for html table tag colors (not used yet) */
	public static final String P_COLOR_HTM_TABLE_TAG = "htmTableTagColor";

	/** Preference key identifier for JavaScript  tag color*/
	public static final String P_COLOR_JSCRIPT_TEXT = "javascriptColor";

	/** /** Preference key identifier for JavaScript function color */
	public static final String P_COLOR_JSCRIPT_FUNCTION = "javascriptFunction";

	/** Preference key identifier for css text and tag color*/
	public static final String P_COLOR_CSS = "cssTextColor";
	
	/** Preference key identifier for css value color i.e. the value part of color: red; Not currently implemented. */
	public static final String P_COLOR_CSS_VALUE = "cssTagColor";
	
	/** Preference key identifier for all xml type tags (i.e. not cf and not html */
	public static final String P_COLOR_UNK_TAG = "unknowTagColors";

	/** Preference key identifier for html strings */
	public static final String P_COLOR_STRING = "stringColor";

	/**
	 * Sets up the default values for preferences managed by {@link HTMLColorsPreferencePage} .
	 * <ul>
	 * <li>P_COLOR_HTM_COMMENT - "96,153,96"</li>
	 * <li>P_COLOR_HTM_TAG - "0,0,128"</li>
	 * <li>P_COLOR_HTM_FORM_TAG - "195,136,0"</li>
	 * <li>P_COLOR_HTM_TABLE_TAG - "132,0,215"</li>
	 * <li>P_COLOR_JSCRIPT_TEXT - "51,0,153"</li>
	 * <li>P_COLOR_JSCRIPT_FUNCTION - "0,153,255"</li>
	 * <li>P_COLOR_CSS - "255,0,255"</li>
	 * <li>P_COLOR_CSS_VALUE - "51,0,153"</li>
	 * <li>P_COLOR_UNK_TAG - "0,0,128"</li>
	 * <li>P_COLOR_STRING - "0,0,255"</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_COLOR_HTM_COMMENT,"96,153,96"); 
		store.setDefault(P_COLOR_HTM_TAG,"0,0,128"); 
		store.setDefault(P_COLOR_HTM_FORM_TAG,"195,136,0"); 
		store.setDefault(P_COLOR_HTM_TABLE_TAG,"132,0,215"); 
		store.setDefault(P_COLOR_JSCRIPT_TEXT,"51,0,153"); 
		store.setDefault(P_COLOR_JSCRIPT_FUNCTION,"0,153,255"); 
		store.setDefault(P_COLOR_CSS,"255,0,255");  
		store.setDefault(P_COLOR_CSS_VALUE,"51,0,153"); 
		store.setDefault(P_COLOR_UNK_TAG,"0,0,128");  
		store.setDefault(P_COLOR_STRING,"0,0,255"); 
	}
}
