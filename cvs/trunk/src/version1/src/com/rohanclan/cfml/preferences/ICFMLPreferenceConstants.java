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
	String P_HTM_COMMENT_COLOR 	= "htmCommentColor";
	/** html strings */
	String P_STRING_COLOR		= "stringColor"; 
	/** page default text */
	String P_DEFAULT_TEXT_COLOR	= "defaultTextColor";
	/** html tag color */
	String P_HTM_TAG_COLOR		= "htmTagColor";
	
	/** html form tag colors (not used yet) */
	String P_HTM_FORM_TAG_COLOR = "htmFormTagColor";
	/** html form tag colors (not used yet) */
	String P_HTM_TABLE_TAG_COLOR = "htmFormTagColor";
	
	/////////////////////////////////////
	/** coldfusion attribute strings */
	String P_CFSTRING_COLOR		= "cfstringColor";
	/** coldfusion tags */
	String P_CFTAG_COLOR		= "cftagColor";
	/** coldfusion keywords (eq, and, or, et cetra) */
	String P_CFKEYWORD_COLOR	= "cfkeywordColor";
	/** coldfusion numbers */
	String P_CFNUMBER_COLOR    	= "cfnumberColor";
	
	//////////////////////////////////////
	String P_CFSCRIPT_TEXT_COLOR 		= "cfscriptTextColor";
	String P_CFSCRIPT_KEYWORD_COLOR		= "cfscriptKeywordColor";
	String P_CFSCRIPT_FUNCTION_COLOR	= "cfscriptfunctionColor";
	String P_CFSCRIPT_STRING_COLOR		= "cfscriptStringColor";
	
	String P_JSCRIPT_TEXT_COLOR	    = "javascriptColor";
	String P_JSCRIPT_FUNCTION_COLOR	= "javascriptFunction";
	String P_CSS_COLOR         		= "cssTextColor";
	String P_CSS_TAG_COLOR     		= "cssTagColor";
	
	/** all xml type tags (i.e. not cf and not html */
	String P_UNK_TAG_COLOR			= "unknowTagColors";
	
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
