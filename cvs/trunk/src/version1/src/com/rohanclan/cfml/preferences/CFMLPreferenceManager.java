/*
 * Created on Apr 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.preferences;

import java.net.URL;

import org.eclipse.jface.preference.IPreferenceStore;
import com.rohanclan.cfml.CFMLPlugin;
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
	private static final boolean DEFAULT_INSERT_SPACES_FOR_TABS = false;
	private static final boolean DEFAULT_ENABLE_HS_COMPATIBILITY = false;
	private static final boolean DEFAULT_ENABLE_DW_COMPATIBILITY = false;

	// Parser prefs
	private static final boolean	DEFAULT_PARSE_DOCFSCRIPT 	= false;
	private static final boolean	DEFAULT_PARSE_DOCFML	 	= true;
	private static final boolean	DEFAULT_PARSE_REPORT_ERRORS = true; 
	
	// Tag indent prefs
	private static final boolean	DEFAULT_AUTOCLOSE_DOUBLEQUOTES	= true;
	private static final boolean	DEFAULT_AUTOCLOSE_SINGLEQUOTES	= true;
	private static final boolean	DEFAULT_AUTOCLOSE_TAGS			= true;
	private static final boolean	DEFAULT_AUTOCLOSE_HASHES		= true;
	private static final boolean	DEFAULT_AUTOINSERT_TAGS			= true;
	
	
	/** this is public because the browser uses it on errors */
	public static final String DEFAULT_PROJECT_URL = "http://livedocs.macromedia.com";
	
	public CFMLPreferenceManager() {
		store = CFMLPlugin.getDefault().getPreferenceStore();
	}
	
	public void initializeDefaultValues() 
	{
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
        
        //store.setDefault(P_CFTAG_COLOR,ICFColorConstants.CFTAG.toString());
	}
	
	public RGB getColor(String key)
	{
		String rgbString = store.getString(key);

		if (rgbString.length() <= 0)
		{
			rgbString = store.getDefaultString(key);
			if(rgbString.length() <= 0) 
			{
				rgbString = "0,0,0";
			}
		}
		return StringConverter.asRGB(rgbString);
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
