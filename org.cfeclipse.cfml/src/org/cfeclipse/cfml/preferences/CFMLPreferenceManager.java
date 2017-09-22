/*
 * Created on Apr 22, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.preferences;

//import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.cfeclipse.cflint.store.CFLintPreferenceConstants;
import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;


/**
 * @author Stephen Milligan
 */
public class CFMLPreferenceManager {

	private IPreferenceStore store;
	
	
	public CFMLPreferenceManager() {
		store = CFMLPlugin.getDefault().getPreferenceStore();
	}

	public boolean getBooleanPref(String prefKey) {
		return store.getBoolean(prefKey);
	}

	public String getStringPref(String prefKey) {
		return store.getString(prefKey);
	}
	
	/* private String getColorString(RGB color) {
	    return color.red + "," + color.green + "," + color.blue;
	} */
	
	public void initializeDefaultValues() {
		
		EditorPreferenceConstants.setDefaults(store);
		AutoIndentPreferenceConstants.setDefaults(store);
		FoldingPreferenceConstants.setDefaults(store);
		HTMLColorsPreferenceConstants.setDefaults(store);
		CFMLColorsPreferenceConstants.setDefaults(store);
		ParserPreferenceConstants.setDefaults(store);
		ScribblePadPreferenceConstants.setDefaults(store);
		BrowserPreferenceConstants.setDefaults(store);
		ApplicationTemplatesPreferenceConstants.setDefaults(store);
		SnipExPreferenceConstants.setDefaults(store);
		TextSelectionPreferenceConstants.setDefaults(store);
		CFMLPreferenceConstants.setDefaults(store);
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
				// Force a stack trace to see what called this.
				try {
					rgbString = null;
					System.out.println(rgbString.length());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
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

	/**
	 * Returns an actual indent as either a tab or spaces depending on preferences
	 * 
	 * @return String indent
	 */
	public String getCanonicalIndent() {
		String canonicalIndent;
		if (!insertSpacesForTabs()) {
			canonicalIndent = "\t"; //$NON-NLS-1$
		} else {
			String tab = ""; //$NON-NLS-1$
			for (int i = 0; i < tabWidth(); i++) {
				tab = tab.concat(" "); //$NON-NLS-1$
			}
			canonicalIndent = tab;
		}

		return canonicalIndent;
	}

	public int tabWidth() {
	    //System.out.println("Tab width retrieved as: " + Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_TAB_WIDTH).trim()));
		return Integer.parseInt(store.getString(EditorPreferenceConstants.P_TAB_WIDTH).trim());
	}
	
	public int defaultTabWidth() {
		return store.getDefaultInt(EditorPreferenceConstants.P_TAB_WIDTH);
	}
	
	
	
	public boolean insertSpacesForTabs() {
		return store.getString(EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultSpacesForTabs() {
		return store.getDefaultBoolean(EditorPreferenceConstants.P_INSERT_SPACES_FOR_TABS);
	}
	
	
	
	public int insightDelay() {
		return Integer.parseInt(store.getString(EditorPreferenceConstants.P_INSIGHT_DELAY).trim());
	}
	
	public int defaultInsightDelay() {
		return store.getDefaultInt(EditorPreferenceConstants.P_INSIGHT_DELAY);
	}
	
	
	
	public boolean tabIndentSingleLine() {
		return store.getString(EditorPreferenceConstants.P_TAB_INDENTS_CURRENT_LINE).trim().equalsIgnoreCase("true");
	}
	
	
	public boolean imageTooltips() {
	    return store.getBoolean(CFMLPreferenceConstants.P_IMAGE_TOOLTIPS);
	}
	
	public String snippetsPath() {
		return store.getString(CFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}

	
	public int maxUndoSteps() {
		return Integer.parseInt(store.getString(EditorPreferenceConstants.P_MAX_UNDO_STEPS).trim());
	}
	
	public String defaultSnippetsPath() {
		return CFMLPlugin.getDefault().getStateLocation().toString();
	}
	
	public String defaultProjectURL() {
		return store.getDefaultString(CFMLPreferenceConstants.P_PROJECT_URL);
	}
	
	public String defaultDictionaryDir() {
		String dictDir = store.getDefaultString(CFMLPreferenceConstants.P_DICTIONARIES_PATH);
		File dictConfig = new File(dictDir + "/dictionaryconfig.xml");
		if (!dictConfig.exists()) {
			CFMLPlugin.logError("Configured preference for dictionary directory does not exist:" + dictDir);
			try {
				dictDir = FileLocator.toFileURL(FileLocator.find(Platform.getBundle(CFMLPlugin.PLUGIN_ID), new Path("dictionary"), null)).toString().replace("file:", "");
			} catch (IOException e) {
				e.printStackTrace();
			}
			CFMLPlugin.logError("Setting dictionary dir to default:" + dictDir);
			store.setValue(CFMLPreferenceConstants.P_DICTIONARIES_PATH, dictDir);
//			throw new IllegalArgumentException("Problem loading dictionaryconfig.xml (" + dictConfig.getPath() + ")");
		}
		return dictDir;
	}

	public String dictionaryDir() {
		String dictDir = store.getString(CFMLPreferenceConstants.P_DICTIONARIES_PATH);
		File dictConfig = new File(dictDir + "/dictionaryconfig.xml");
		if (!dictConfig.exists()) {
			dictDir = defaultDictionaryDir();
		}
		return dictDir;
	}

	public String defaultHelpURL() {
		return store.getString(CFMLPreferenceConstants.P_DEFAULT_HELP_URL);
	}

	public boolean helpUrlUseExternalBrowser() {
		return store.getBoolean(CFMLPreferenceConstants.P_HELP_URL_USE_EXTERNAL_BROWSER);
	}

	public boolean defaultTabbedBrowser() {
	    return store.getDefaultBoolean(CFMLPreferenceConstants.P_TABBED_BROWSER);
	}
	
	public boolean tabbedBrowser() {
	    return store.getBoolean(CFMLPreferenceConstants.P_TABBED_BROWSER);
	}

	
	public boolean enableFolding() {
		return store.getBoolean(FoldingPreferenceConstants.P_ENABLE_CODE_FOLDING);
	}
	
	public int minimumFoldingLines() {
		return store.getInt(FoldingPreferenceConstants.P_MINIMUM_CODE_FOLDING_LINES);
	}


	public boolean foldCFMLComments() {
		return store.getBoolean(FoldingPreferenceConstants.P_FOLDING_CFMLCOMMENTS_FOLD);
	}
	
	public boolean collapseCFMLComments() {
		return store.getBoolean(FoldingPreferenceConstants.P_FOLDING_CFMLCOMMENTS_COLLAPSE);
	}


	public boolean foldHTMLComments() {
		return store.getBoolean(FoldingPreferenceConstants.P_FOLDING_HTMLCOMMENTS_FOLD);
	}
	
	public boolean collapseHTMLComments() {
		return store.getBoolean(FoldingPreferenceConstants.P_FOLDING_HTMLCOMMENTS_COLLAPSE);
	}

	public boolean foldTag(int tagNumber) {
	    boolean val = false;
		switch (tagNumber) {
			case 1: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG1_FOLD); 
			break;
			case 2: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG2_FOLD); 
			break; 
			case 3: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG3_FOLD); 
			break;
			case 4: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG4_FOLD); 
			break;
			case 5: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG5_FOLD); 
			break;
			case 6: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG6_FOLD); 
			break;
			case 7: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG7_FOLD); 
			break;
			case 8: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG8_FOLD); 
			break;
		}
	    return val;
	}

	public boolean collapseTag(int tagNumber) {
	    boolean val = false;
		switch (tagNumber) {
			case 1: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG1_COLLAPSE);
			break;
			case 2: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG2_COLLAPSE);
			break;
			case 3: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG3_COLLAPSE);
			break;
			case 4: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG4_COLLAPSE);
			break;
			case 5: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG5_COLLAPSE);
			break;
			case 6: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG6_COLLAPSE);
			break;
			case 7: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG7_COLLAPSE);
			break;
			case 8: val = store.getBoolean(FoldingPreferenceConstants.P_FOLDING_TAG8_COLLAPSE);
			break;
		}
	    return val;
	}

	public String foldingTagName(int tagNumber) {
	    //System.out.println("TEST : " + store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG1_NAME));
	    String val = "";
		switch (tagNumber) {
			case 1: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG1_NAME);
			break;
			case 2: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG2_NAME);
			break;
			case 3: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG3_NAME);
			break;
			case 4: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG4_NAME);
			break;
			case 5: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG5_NAME);
			break;
			case 6: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG6_NAME);
			break;
			case 7: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG7_NAME);
			break;
			case 8: val = store.getString(FoldingPreferenceConstants.P_FOLDING_TAG8_NAME);
			break;
		}
	    return val;
	}
	
	public boolean parseCFScriptCFCs() {
		return store.getString(ParserPreferenceConstants.PARSE_CFSCRIPT_CFCS).trim().equalsIgnoreCase("true");
	}
	
	
	public boolean useFunkyContentAssist() {
		return true;
		//return true;
	}
	
	public String templateProjectsPath(){
		return store.getString(CFMLPreferenceConstants.P_TEMPLATE_PROJECT_PATH);
	}

	public boolean persistFoldState() {
		return store.getBoolean(FoldingPreferenceConstants.P_PERSIST_FOLD_STATE);
	}

	public Boolean CFLintEnabled() {
		return store.getBoolean(CFLintPreferenceConstants.P_CFLINT_ENABLED);
	}
		
	
}
