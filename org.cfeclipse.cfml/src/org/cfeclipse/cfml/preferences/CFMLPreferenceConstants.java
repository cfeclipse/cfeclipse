/*
 * Created on Apr 20, 2004
 *
 */
package org.cfeclipse.cfml.preferences;

import java.io.IOException;

import org.cfeclipse.cflint.store.CFLintPreferenceConstants;
import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;


/**
 * 
 * This interface defines all the preferences that are available in the plugin.
 * Just getting started here, so insight delay is looking a bit lonely :-/
 *  TODO: This seems to be a per-project (or should be). Check out this is working correctly.
 *  
 *  
 * @author Stephen Milligan 
 */
public class CFMLPreferenceConstants extends AbstractPreferenceConstants {
	
	/** Preference key identifier for path to the directory that contains your snippets */
	public static final String P_SNIPPETS_PATH 			= "snippetPath";
	public static final String P_DICTIONARIES_PATH = "dictionariesPath";
	
	/** Preference key identifier for the project to use to store TemplateProkects */
	public static final String P_TEMPLATE_PROJECT_PATH 	= "templateProjectsPath";
	
	/** Preference key identifier for the url of the project. */
	public static final String P_PROJECT_URL 				= "projectURL";
	
	/** Preference key identifier for tabbed browsing */
	public static final String P_TABBED_BROWSER			= "tabbedBrowsing";
	
	/** this is the per project setting for their dictionary selection */
    public static final String P_CFML_DICTIONARY = "cfmlDictionary";
    
    /** the default key to the dictionary to use in this project.
     * @see DictionaryManager 
     */
    public static final String P_CFML_DICTIONARY_DEFAULT = "ColdFusion9";

    /** the default key to the dictionary to use in this project.
     * @see DictionaryManager 
     */
    public static final String P_IMAGE_TOOLTIPS = "showImageTooltips";

    /**
	 * this is public because the browser uses it on errors 
	 */
	public static final String DEFAULT_PROJECT_URL = "https://www.cfeclipse.org";
	
	/** Preference key identifier for the help url. */
	public static final String P_DEFAULT_HELP_URL 				= "_helpURL";
	
    /**
	 * the default help url 
	 */
	public static final String DEFAULT_HELP_URL = "http://cfdocs.org/";
	
    /**
	 * the default help url 
	 */
	public static final String P_HELP_URL_USE_EXTERNAL_BROWSER = "_helpURLUseExternalBrowser";
	
	/**
	 * the CFLint defaults 
	 */
	public static final String P_CFLINT_ENABLED_PROJECT = "_cflint_enabled_project";
	public static final String P_CFLINT_STOREINPROJECT_PROJECT = "_cflint_storeinproject_project";
	public static final String EDITOR_TEXT_HOVER_MODIFIERS = "_cfml_text_hover_modifers";
	public static final String EDITOR_TEXT_HOVER_MODIFIER_MASKS = "_cfml_text_hover_modifer_mask";
	public static final String ID_BESTMATCH_HOVER = "org.cfeclipse.cfml.BestMatchHover";
	
	/**
	 * Sets up the default values for preferences managed by {@link CFMLPreferencesPage} .
	 * <ul>
	 * <li>P_SNIPPETS_PATH - CFMLPlugin.getDefault().getStateLocation().toString()</li>
	 * <li>P_PROJECT_URL - DEFAULT_PROJECT_URL</li>
	 * <li>P_TABBED_BROWSER - false</li>
	 * <li>P_CFML_DICTIONARY - "cfml.xml"</li>
	 * </ul> 
	 */
	
	public static void setDefaults(IPreferenceStore store) { 
		store.setDefault(P_SNIPPETS_PATH,CFMLPlugin.getDefault().getStateLocation().toString()+"/snippets");
		String dicts;
		try {
			dicts = FileLocator.toFileURL(FileLocator.find(Platform.getBundle(CFMLPlugin.PLUGIN_ID), new Path("dictionary"), null)).toString().replace("file:", "");
		} catch (IOException e) {
			dicts = CFMLPlugin.getDefault().getBundle().getLocation().replace("reference:file:", "") + "dictionary";
			e.printStackTrace();
		}
		store.setDefault(P_DICTIONARIES_PATH, dicts);
		store.setDefault(P_PROJECT_URL,DEFAULT_PROJECT_URL);
		store.setDefault(P_DEFAULT_HELP_URL,DEFAULT_HELP_URL);
		store.setDefault(P_HELP_URL_USE_EXTERNAL_BROWSER,false);
		store.setDefault(P_TABBED_BROWSER,false);
		store.setDefault(P_IMAGE_TOOLTIPS,true);
		store.setDefault(P_CFML_DICTIONARY, P_CFML_DICTIONARY_DEFAULT);
		store.setDefault(P_TEMPLATE_PROJECT_PATH, "");
		store.setDefault(P_CFLINT_ENABLED_PROJECT, store.getBoolean(CFLintPreferenceConstants.P_CFLINT_ENABLED));
		store.setDefault(P_CFLINT_STOREINPROJECT_PROJECT, store.getBoolean(CFLintPreferenceConstants.P_CFLINT_STOREINPROJECT));

		store.setDefault(P_CFLINT_STOREINPROJECT_PROJECT, store.getBoolean(CFLintPreferenceConstants.P_CFLINT_STOREINPROJECT));

//		int sourceHoverModifier= SWT.MOD2;
		int sourceHoverModifier= 0;
		String sourceHoverModifierName= Action.findModifierString(sourceHoverModifier);	// Shift
		int nlsHoverModifier= SWT.MOD1 + SWT.MOD3;
		String nlsHoverModifierName= Action.findModifierString(SWT.MOD1) + "+" + Action.findModifierString(SWT.MOD3);	// Ctrl + Alt //$NON-NLS-1$
		int javadocHoverModifier= SWT.MOD1 + SWT.MOD2;
		String javadocHoverModifierName= Action.findModifierString(SWT.MOD1) + "+" + Action.findModifierString(SWT.MOD2); // Ctrl + Shift //$NON-NLS-1$
		store.setDefault(EDITOR_TEXT_HOVER_MODIFIERS, "org.cfeclipse.cfml.BestMatchHover;0;org.cfeclipse.cfml.JavaSourceHover;" + sourceHoverModifierName + ";org.eclipse.jdt.ui.NLSStringHover;" + nlsHoverModifierName + ";org.cfeclipse.cfml.JavadocHover;" + javadocHoverModifierName); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		store.setDefault(EDITOR_TEXT_HOVER_MODIFIER_MASKS, "org.cfeclipse.cfml.BestMatchHover;0;org.cfeclipse.cfml.JavaSourceHover;" + sourceHoverModifier + ";org.eclipse.jdt.ui.NLSStringHover;" + nlsHoverModifier + ";org.cfeclipse.cfml.JavadocHover;" + javadocHoverModifier); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	}
	
}
