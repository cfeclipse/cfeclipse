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
	String P_INSIGHT_DELAY 			= "insightDelay";
	String P_INSERT_SPACES_FOR_TABS 	= "tabsAsSpaces";
	String P_TAB_WIDTH 				= "tabWidth";
	String P_MAX_UNDO_STEPS 				= "maxUndoSteps";
	String P_ENABLE_DW_COMPATIBILITY 	= "dreamweaverCompatibility";
	String P_ENABLE_HS_COMPATIBILITY 	= "homesiteCompatibility";
	String P_SNIPPETS_PATH 			= "snippetPath";
	String P_PROJECT_URL 				= "projectURL";
	
	// Scribble pad stuff
	String P_SCRIBBLE_PROJECT_NAME = "scribbleProjectID";
	String P_SCRIBBLE_PAD_FILE = "scribblePad";
	String P_SCRIBBLE_CLEAR_ON_LOAD = "clearScribblePadOnLoad";
	String P_SCRIBBLE_LOAD_BROWSER = "loadBrowser";
	String P_SCRIBBLE_URL = "scribbleURL";
	
	/** html, javascript, css comments */
	String P_COLOR_HTM_COMMENT 		= "htmCommentColor";
	/** html strings */
	String P_COLOR_STRING				= "stringColor"; 
	/** page default text */
	String P_COLOR_DEFAULT_TEXT		= "defaultTextColor";
	/** html tag color */
	String P_COLOR_HTM_TAG			= "htmTagColor";
	
	/** html form tag colors (not used yet) */
	String P_COLOR_HTM_FORM_TAG 		= "htmFormTagColor";
	/** html table tag colors (not used yet) */
	String P_COLOR_HTM_TABLE_TAG 		= "htmTableTagColor";
	
	/////////////////////////////////////
	/** cfml attribute strings */
	String P_COLOR_CFSTRING			= "cfstringColor";
	/** cfml tags */
	String P_COLOR_CFTAG				= "cftagColor";
	/** cfml keywords (eq, and, or, et cetra) */
	String P_COLOR_CFKEYWORD			= "cfkeywordColor";
	/** cfml numbers */
	String P_COLOR_CFNUMBER    		= "cfnumberColor";
	/** cfml comment */
	String P_COLOR_CFCOMMENT    		= "cfcommentColor";
	
	//////////////////////////////////////
	String P_COLOR_CFSCRIPT_TEXT 		= "cfscriptTextColor";
	String P_COLOR_CFSCRIPT_KEYWORD	= "cfscriptKeywordColor";
	String P_COLOR_CFSCRIPT_FUNCTION	= "cfscriptfunctionColor";
	String P_COLOR_CFSCRIPT_STRING	= "cfscriptStringColor";
	
	/** actually the tag color */
	String P_COLOR_JSCRIPT_TEXT	    = "javascriptColor";
	/** functions in side of js */
	String P_COLOR_JSCRIPT_FUNCTION	= "javascriptFunction";
	/** css tag and text color */
	String P_COLOR_CSS         		= "cssTextColor";
	/** css value color i.e. the value part of color: red; */
	String P_COLOR_CSS_TAG     		= "cssTagColor";
	
	/** all xml type tags (i.e. not cf and not html */
	String P_COLOR_UNK_TAG			= "unknowTagColors";
	
	/** background color of the text editor */
	String P_COLOR_BACKGROUND			= "backgroundColor";
	
	//////////////////////////////////////
	// Parser preferences
	String P_PARSE_DOCFSCRIPT			= "__parseCFScript";
	String P_PARSE_DOCFML				= "__parseCFML";
	String P_PARSE_REPORT_ERRORS		= "__parseReportErrors";
	
	//////////////////////////////////////
	// Auto-insertion prefs
	String	P_AUTOCLOSE_DOUBLE_QUOTES		= "__autoCloseDoubleQuotes";
	String	P_AUTOCLOSE_SINGLE_QUOTES 	= "__autoCloseSingleQuotes";
	String	P_AUTOCLOSE_TAGS				= "__autoCloseTags";
	String 	P_AUTOCLOSE_HASHES			= "__autoCloseHashes"; 
	String 	P_AUTOINSERT_CLOSE_TAGS		= "__autoInsertCloseTags";
	String	P_AUTOINDENT_ONTAGCLOSE		= "__autoIndentOnTagClose";
	
	//////////////////////////////////////
	// bracket matching prefs
	String P_BRACKET_MATCHING_ENABLED = "bracketMatchingEnabled";
	String P_BRACKET_MATCHING_COLOR = "bracketMatchingColor";
	
	//////////////////////////////////////
	// Folding prefs
	String P_ENABLE_CODE_FOLDING = "enableCodeFolding";
	
	
	String P_CFML_DICTIONARY = "__cfmlDictionary";
}
