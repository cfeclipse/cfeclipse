/*
 * Created on Apr 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.preferences;

//import java.net.URL;
import org.eclipse.jface.preference.IPreferenceStore;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.ICFColorConstants;
//import com.rohanclan.cfml.editors.indentstrategies.TagIndentStrategy;
//import com.rohanclan.cfml.editors.ICFColorConstants;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.jface.resource.StringConverter;

/**
 * @author Stephen Milligan
 *
 */
public class CFMLPreferenceManager implements ICFMLPreferenceConstants {

	private IPreferenceStore store;
	
	private static final int DEFAULT_TAB_WIDTH = 4;
	private static final int DEFAULT_INSIGHT_DELAY = 500;
	
	private static final boolean DEFAULT_INSERT_SPACES_FOR_TABS 	= false;
	private static final boolean DEFAULT_ENABLE_HS_COMPATIBILITY 	= false;
	private static final boolean DEFAULT_ENABLE_DW_COMPATIBILITY 	= false;

	// Parser prefs
	private static final boolean	DEFAULT_PARSE_DOCFSCRIPT 			= false;
	private static final boolean	DEFAULT_PARSE_DOCFML	 			= true;
	private static final boolean	DEFAULT_PARSE_REPORT_ERRORS 		= true; 
	
	// Tag indent prefs
	private static final boolean	DEFAULT_AUTOCLOSE_DOUBLEQUOTES	= true;
	private static final boolean	DEFAULT_AUTOCLOSE_SINGLEQUOTES 	= true;
	private static final boolean	DEFAULT_AUTOCLOSE_TAGS			= true;
	private static final boolean	DEFAULT_AUTOCLOSE_HASHES			= true;
	private static final boolean	DEFAULT_AUTOINSERT_TAGS			= true;
	private static final boolean	DEFAULT_AUTOINDENT_ONTAGCLOSE		= false;
	
	// Colour defaults
	//private static final RGB DEFAULT_COLOR_DOCTYPE 		= ICFColorConstants.DOCTYPE;
	private static final RGB DEFAULT_COLOR_CFCOMMENT 		= ICFColorConstants.CF_COMMENT;
	private static final RGB DEFAULT_COLOR_CFKEYWORD 		= ICFColorConstants.CFKEYWORD;
	private static final RGB DEFAULT_COLOR_CFNUMBER 		= ICFColorConstants.CFNUMBER;
	private static final RGB DEFAULT_COLOR_CFSCRIPT 		= ICFColorConstants.CFSCRIPT;
	private static final RGB DEFAULT_COLOR_CFSCRIPTFUNCTION = ICFColorConstants.CFSCRIPT_FUNCTION;
	private static final RGB DEFAULT_COLOR_CFSCRIPT_KEYWORD = ICFColorConstants.CFSCRIPT_KEYWORD;
	private static final RGB DEFAULT_COLOR_CFSCRIPT_STRING 	= ICFColorConstants.CFSCRIPT_STRING;
	private static final RGB DEFAULT_COLOR_CFSTRING 		= ICFColorConstants.CFSTRING;
	private static final RGB DEFAULT_COLOR_CFTAG 			= ICFColorConstants.CFTAG;
	private static final RGB DEFAULT_COLOR_CSS 			= ICFColorConstants.CSS;
	private static final RGB DEFAULT_COLOR_DEFAULT 		= ICFColorConstants.DEFAULT;
	private static final RGB DEFAULT_COLOR_HTMCOMMENT	 	= ICFColorConstants.HTM_COMMENT;
	private static final RGB DEFAULT_COLOR_JSCRIPT 		= ICFColorConstants.JSCRIPT;
	private static final RGB DEFAULT_COLOR_JSCRIPTFUNCTION 	= ICFColorConstants.JSCRIPT_FUNCTION;
	private static final RGB DEFAULT_COLOR_STRING 			= ICFColorConstants.STRING;
	private static final RGB DEFAULT_COLOR_TAG 			= ICFColorConstants.TAG;
	private static final RGB DEFAULT_COLOR_UNKTAG 			= ICFColorConstants.UNK_TAG;
	private static final RGB DEFAULT_COLOR_FORMTAG			= ICFColorConstants.FORM;
	private static final RGB DEFAULT_COLOR_TABLETAG		= ICFColorConstants.TABLE;
	
	/** this is public because the browser uses it on errors */
	public static final String DEFAULT_PROJECT_URL = "http://livedocs.macromedia.com";
	
	public CFMLPreferenceManager() {
		store = CFMLPlugin.getDefault().getPreferenceStore();
	}

	public boolean getBooleanPref(String prefKey) {
		return store.getBoolean(prefKey);
	}

	/**
	 * Sets up all of the colours... Note proper spelling of 'colour' :D
	 * (and initialise not initialize)
	 */
	public void initialiseDefaultColours() {
		store.setDefault(P_COLOR_CFKEYWORD, getColorString(DEFAULT_COLOR_CFKEYWORD));
		//store.setDefault(P_COLOR_, DEFAULT_COLOR_DOCTYPE.toString());
		//store.setDefault(P_COLOR_, DEFAULT_COLOR_CFCOMMENT.toString());
		
		store.setDefault(P_COLOR_CFNUMBER, getColorString(DEFAULT_COLOR_CFNUMBER));
		store.setDefault(P_COLOR_CFCOMMENT, getColorString(DEFAULT_COLOR_CFCOMMENT));
		store.setDefault(P_COLOR_CFSCRIPT_TEXT, getColorString(DEFAULT_COLOR_CFSCRIPT));
		store.setDefault(P_COLOR_CFSCRIPT_FUNCTION, getColorString(DEFAULT_COLOR_CFSCRIPTFUNCTION));
		store.setDefault(P_COLOR_CFSCRIPT_KEYWORD, getColorString(DEFAULT_COLOR_CFSCRIPT_KEYWORD));
		store.setDefault(P_COLOR_CFSCRIPT_STRING, getColorString(DEFAULT_COLOR_CFSCRIPT_STRING));
		store.setDefault(P_COLOR_CFSTRING, getColorString(DEFAULT_COLOR_CFSTRING));
		store.setDefault(P_COLOR_CFTAG, getColorString(DEFAULT_COLOR_CFTAG));
		store.setDefault(P_COLOR_CSS, getColorString(DEFAULT_COLOR_CSS));
		store.setDefault(P_COLOR_DEFAULT_TEXT, getColorString(DEFAULT_COLOR_DEFAULT));
		store.setDefault(P_COLOR_HTM_COMMENT, getColorString(DEFAULT_COLOR_HTMCOMMENT));
		store.setDefault(P_COLOR_JSCRIPT_TEXT, getColorString(DEFAULT_COLOR_JSCRIPT));
		store.setDefault(P_COLOR_JSCRIPT_FUNCTION, getColorString(DEFAULT_COLOR_JSCRIPTFUNCTION));
		store.setDefault(P_COLOR_STRING, getColorString(DEFAULT_COLOR_STRING));
		store.setDefault(P_COLOR_HTM_TAG, getColorString(DEFAULT_COLOR_TAG));
		store.setDefault(P_COLOR_UNK_TAG, getColorString(DEFAULT_COLOR_UNKTAG));
		store.setDefault(P_COLOR_HTM_FORM_TAG, getColorString(DEFAULT_COLOR_FORMTAG));
		store.setDefault(P_COLOR_HTM_TABLE_TAG, getColorString(DEFAULT_COLOR_TABLETAG));
	}
	
	private String getColorString(RGB color) {
	    return color.red + "," + color.green + "," + color.blue;
	}
	
	public void initializeDefaultValues() {
		
		//this should set the default path for snippets to the plugin directory
		//in a sub directory called snippets... I think that makes more sense :)
		String snippath = "";
		try
		{
			//TODO figure out how to get to the snippets absolute path
			/* snippath = new URL(
				CFMLPlugin.getDefault().getBundle().getEntry("/"),
				"/snippets"
			).toString();
			*/
			snippath = CFMLPlugin.getDefault().getStateLocation().toString();
		}
		catch(Exception e)
		{
			//this should never happen.
		}		
		
        store.setDefault(P_INSIGHT_DELAY, DEFAULT_INSIGHT_DELAY); 
        store.setDefault(P_TAB_WIDTH, DEFAULT_TAB_WIDTH);
        store.setDefault(P_INSERT_SPACES_FOR_TABS, DEFAULT_INSERT_SPACES_FOR_TABS);
        store.setDefault(P_ENABLE_HS_COMPATIBILITY, DEFAULT_ENABLE_HS_COMPATIBILITY);
        store.setDefault(P_ENABLE_DW_COMPATIBILITY, DEFAULT_ENABLE_DW_COMPATIBILITY);
        store.setDefault(P_SNIPPETS_PATH, snippath);
        //store.setDefault(P_SNIPPETS_PATH, CFMLPlugin.getDefault().getStateLocation().toString());
        
        // Parser prefs.
        store.setDefault(P_PARSE_DOCFSCRIPT, DEFAULT_PARSE_DOCFSCRIPT);
        store.setDefault(P_PARSE_DOCFML, DEFAULT_PARSE_DOCFML);
        store.setDefault(P_PARSE_REPORT_ERRORS, DEFAULT_PARSE_REPORT_ERRORS);
        
        // Tag indent prefs
        store.setDefault(P_AUTOCLOSE_DOUBLE_QUOTES, DEFAULT_AUTOCLOSE_DOUBLEQUOTES);
        store.setDefault(P_AUTOCLOSE_SINGLE_QUOTES, DEFAULT_AUTOCLOSE_SINGLEQUOTES);
        store.setDefault(P_AUTOCLOSE_TAGS, DEFAULT_AUTOCLOSE_TAGS);
        store.setDefault(P_AUTOCLOSE_HASHES, DEFAULT_AUTOCLOSE_HASHES);
        store.setDefault(P_AUTOINSERT_CLOSE_TAGS, DEFAULT_AUTOINSERT_TAGS);
        store.setDefault(P_AUTOINDENT_ONTAGCLOSE, DEFAULT_AUTOINDENT_ONTAGCLOSE);
        initialiseDefaultColours();
        //store.setDefault(P_CFTAG_COLOR,ICFColorConstants.CFTAG.toString());
	}
	
	/**
	 * Gets an RGB from the preference store using key as the key. If the key
	 * does not exist, it returns 0,0,0
	 * @param key
	 * @return
	 */
	public RGB getColor(String key)
	{
		//try to get the color as a string from the store
		String rgbString = store.getString(key);
		//System.err.println(key + " :: " + rgbString);
		
		//if we didnt get anything back...
		if(rgbString.length() <= 0)
		{
			//try to get it from the default settings
			rgbString = store.getDefaultString(key);
			
			//if we still didnt get anything use black
			if(rgbString.length() <= 0)
			{
				System.err.println("Color key: " + key + " is a no show using black");
				rgbString = "0,0,0";
			}
		}
		
		//make sure we get an ok string
		rgbString = deParen(rgbString);
		
		RGB newcolor = null;
		try
		{
			newcolor = StringConverter.asRGB(deParen(rgbString));
		}
		catch(Exception e)
		{
			System.err.println("Woah... got an odd color passed: " + key);
			e.printStackTrace(System.err);
		}
		
		return newcolor;
	}
	
	/**
	 * for some reason the color can get stored as  {RGB 12, 1, 1} and the rbg maker
	 * thingy expects them in 12,1,1, format so this cleans up the string a bit
	 * @param item
	 * @return
	 */
	private String deParen(String item)
	{
		String d = item.replace('{',' ').replace('}',' '); 
		d = d.replaceAll("[RGB ]","").trim();
		return d;
	}
	
	public int tabWidth() {
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_TAB_WIDTH).trim());
	}
	
	public int defaultTabWidth() {
		return DEFAULT_TAB_WIDTH;
	}
	
	
	
	public boolean insertSpacesForTabs() {
		return store.getString(ICFMLPreferenceConstants.P_INSERT_SPACES_FOR_TABS).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultSpacesForTabs() {
		return DEFAULT_INSERT_SPACES_FOR_TABS;
	}
	
	
	
	public int insightDelay() {
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_INSIGHT_DELAY).trim());
	}
	
	public int defaultInsightDelay() {
		return DEFAULT_INSIGHT_DELAY;
	}
	
	
	
	public boolean homesiteCompatibility() {
		return store.getString(ICFMLPreferenceConstants.P_ENABLE_HS_COMPATIBILITY).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultHomesiteCompatibility() {
		return DEFAULT_ENABLE_HS_COMPATIBILITY;
	}
	
	public boolean dreamweaverCompatibility() {
		return store.getString(ICFMLPreferenceConstants.P_ENABLE_DW_COMPATIBILITY).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultDreamweaverCompatibility() {
		return DEFAULT_ENABLE_DW_COMPATIBILITY;
	}
	
	
	
	public String snippetsPath() {
		return store.getString(ICFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}
	
	public String defaultSnippetsPath() {
		return CFMLPlugin.getDefault().getStateLocation().toString();
	}
	
	public String projectURL() {
		return DEFAULT_PROJECT_URL;
	}
	
}
