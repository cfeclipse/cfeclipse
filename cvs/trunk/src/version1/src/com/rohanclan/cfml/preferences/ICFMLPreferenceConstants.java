/*
 * Created on Apr 20, 2004
 *
 */
package com.rohanclan.cfml.preferences;

/**
 * 
 * This interface defines all the preferences that are available in the plugin.
 * Just getting started here, so insight delay is looking a bit lonely :-/
 * 
 * @author Stephen Milligan 
 */
public interface ICFMLPreferenceConstants {
	String P_INSIGHT_DELAY = "insightDelay";
	String P_INSERT_SPACES_FOR_TABS = "tabsAsSpaces";
	String P_TAB_WIDTH = "tabWidth";
	String P_ENABLE_DW_COMPATIBILITY = "dreamweaverCompatibility";
	String P_ENABLE_HS_COMPATIBILITY = "homesiteCompatibility";
	String P_SNIPPETS_PATH = "snippetPath";
	String P_PROJECT_URL = "projectURL";
	
	/** all comments */
	String P_COLOR_HTM_COMMENT 	= "htmCommentColor";
	/** html strings */
	String P_COLOR_STRING		= "stringColor"; 
	/** page default text */
	String P_COLOR_DEFAULT_TEXT	= "defaultTextColor";
	/** html tag color */
	String P_COLOR_HTM_TAG		= "htmTagColor";
	
	/** html form tag colors (not used yet) */
	String P_COLOR_HTM_FORM_TAG = "htmFormTagColor";
	/** html form tag colors (not used yet) */
	String P_COLOR_HTM_TABLE_TAG = "htmFormTagColor";
	
	/////////////////////////////////////
	/** coldfusion attribute strings */
	String P_COLOR_CFSTRING		= "cfstringColor";
	/** coldfusion tags */
	String P_COLOR_CFTAG		= "cftagColor";
	/** coldfusion keywords (eq, and, or, et cetra) */
	String P_COLOR_CFKEYWORD	= "cfkeywordColor";
	/** coldfusion numbers */
	String P_COLOR_CFNUMBER    	= "cfnumberColor";
	
	//////////////////////////////////////
	String P_COLOR_CFSCRIPT_TEXT 		= "cfscriptTextColor";
	String P_COLOR_CFSCRIPT_KEYWORD		= "cfscriptKeywordColor";
	String P_COLOR_CFSCRIPT_FUNCTION	= "cfscriptfunctionColor";
	String P_COLOR_CFSCRIPT_STRING		= "cfscriptStringColor";
	
	String P_COLOR_JSCRIPT_TEXT	    = "javascriptColor";
	String P_COLOR_JSCRIPT_FUNCTION	= "javascriptFunction";
	String P_COLOR_CSS         		= "cssTextColor";
	String P_COLOR_CSS_TAG     		= "cssTagColor";
	
	/** all xml type tags (i.e. not cf and not html */
	String P_COLOR_UNK_TAG			= "unknowTagColors";
	
	//////////////////////////////////////
	// Parser preferences
	
	String P_PARSE_DOCFSCRIPT		= "__parseCFScript";
	String P_PARSE_DOCFML			= "__parseCFML";
	String P_PARSE_REPORT_ERRORS	= "__parseReportErrors";
	
	//////////////////////////////////////
	// Auto-insertion prefs
	String	P_AUTOCLOSE_DOUBLE_QUOTES	= "__autoCloseDoubleQuotes";
	String	P_AUTOCLOSE_SINGLE_QUOTES 	= "__autoCloseSingleQuotes";
	String	P_AUTOCLOSE_TAGS			= "__autoCloseTags";
	String 	P_AUTOCLOSE_HASHES			= "__autoCloseHashes"; 
	String 	P_AUTOINSERT_CLOSE_TAGS		= "__autoInsertCloseTags";
	String	P_AUTOINDENT_ONTAGCLOSE		= "__autoIndentOnTagClose";
	
}
