/*
 * Created on Jan 29, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
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
package com.rohanclan.cfml;

import java.io.File;
//import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.PropertyConfigurator;
//import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.editors.text.TextEditorPreferenceConstants;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.editors.actions.LastActionManager;
import com.rohanclan.cfml.editors.contentassist.CFContentAssist;
import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.editors.contentassist.CFMLComponentAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLFunctionAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLScopeAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLTagAssist;
import com.rohanclan.cfml.editors.contentassist.HTMLTagAssistContributor;
import com.rohanclan.cfml.editors.partitioner.scanners.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.properties.CFMLPropertyManager;
import com.rohanclan.cfml.util.CFPluginImages;

/**
 * 
 * The CFEclipse plugin itself.
 * 
 * Also see 'Simple plug-in example' in the Platform Plug-in Developer Guide 
 * that comes with the SDK version of Eclipse.
 * 
 * @see org.eclipse.ui.plugin.AbstractUIPlugin
 * @author Rob
 */
public class CFMLPlugin extends AbstractUIPlugin {
	/** Singleton instance so that everything can access the plugin */
	private static CFMLPlugin plugin;
	
	/** The bundle of resources for the plugin */
	private ResourceBundle resourceBundle;
	
	/** The preferences for the plugin. */
	private PreferenceStore propertyStore; 
	  
	/** Content Assist Manager */
	private CFEContentAssistManager camInstance;
	
	/** Last Encloser Manager */
	private LastActionManager lastEncMgrInstance;
	
	/** Unique ID of the CFENature */
	public static final String NATURE_ID = "com.rohanclan.cfml.CFENature";
	
	/**
	 * Returns the global Content Assist Manager.
	 * 
	 * @see com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager
	 * @return The CAM instance
	 * 
	 */
	public CFEContentAssistManager getGlobalCAM()
	{
		
		if(this.camInstance == null)
		{
			throw new IllegalArgumentException("CFMLPlugin::getGlobalCAM() camInstance is null");
		}
	    return this.camInstance;
	}
	
	/**
	 * Returns the Last Encloser Manager.
	 * 
	 * @see com.rohanclan.cfml.editors.actions.LastActionManager
	 * @return The LastActionManager instance
	 * 
	 */
	public LastActionManager getLastActionManager()
	{
		if(this.camInstance == null)
		{
			throw new IllegalArgumentException("CFMLPlugin::getGlobalCAM() camInstance is null");
		}
	    return this.lastEncMgrInstance;
	}
	
	/**
	 * create a new cfml plugin
	 */
	public CFMLPlugin()
	{
		super();
		plugin = this;
		try 
		{
			this.resourceBundle = ResourceBundle.getBundle("plugin");	
		} 
		catch (MissingResourceException x) 
		{
			x.printStackTrace(System.err);
			this.resourceBundle = null;
		}
	}
	
	/**
	 * This method is called upon plug-in activation. Seems like most startup 
	 * stuff should now go here.
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		/*//System.out.println(
			"Property store file set to " + 
			CFMLPlugin.getDefault().getStateLocation().toString()
			+ "/properties.ini"
		); */
		
		PropertyConfigurator.configure(CFMLPlugin.getDefault().getBundle().getEntry("/lib/log4j.properties"));
		this.propertyStore = new PreferenceStore(
			CFMLPlugin.getDefault().getStateLocation().toString()
			+ "/properties.ini"
		);
		
		String defaultSnippetPath = CFMLPlugin.getDefault().getStateLocation().toString()+"/snippets";
		
		File f = new File(defaultSnippetPath);
		if (!f.exists()) {
		    f.mkdir();
		}
		
		try
		{
			//load all the syntax dictionaries
			DictionaryManager.initDictionaries();
			
			//startup the image registry
			CFPluginImages.initCFPluginImages();
			
			setupCAM();
			setupLastEncMgr();
		}
		catch(Exception e)
		{
			//lots of bad things can happen...
			e.printStackTrace(System.err);
		}
	}

	/**
     * Setups up the Content Assist Manager
     * 
     */
    public void setupCAM() {
        this.camInstance = new CFEContentAssistManager();
        CFMLTagAssist cfmlAssistor = new CFMLTagAssist(DictionaryManager.getDictionary(DictionaryManager.CFDIC));
        HTMLTagAssistContributor htmlAssistor = new HTMLTagAssistContributor(DictionaryManager.getDictionary(DictionaryManager.HTDIC));

        CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);
        
		this.camInstance.registerRootAssist(cfscp);
        this.camInstance.registerRootAssist(new CFContentAssist());
        this.camInstance.registerRootAssist(new CFMLScopeAssist());
        this.camInstance.registerRootAssist(new CFMLFunctionAssist());
        this.camInstance.registerRootAssist(new CFMLComponentAssist());

        this.camInstance.registerTagAssist(cfmlAssistor);
        this.camInstance.registerAttributeAssist(cfmlAssistor);
        this.camInstance.registerValueAssist(cfmlAssistor);

        this.camInstance.registerTagAssist(htmlAssistor);
        this.camInstance.registerAttributeAssist(htmlAssistor);
        this.camInstance.registerValueAssist(htmlAssistor);
        
        
        
        this.camInstance.registerTagAssist(new CFMLScopeAssist());
    }

	/**
     * Sets up the Last Encloser Manager
     * 
     */
    private void setupLastEncMgr() {
        this.lastEncMgrInstance = new LastActionManager();
	}

    /**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
	
	protected void initializeDefaultPluginPreferences() 
	{
	    
        //super.initializeDefaultPluginPreferences();
        CFMLPreferenceManager preferenceManager = new CFMLPreferenceManager();
		preferenceManager.initializeDefaultValues();
		TextEditorPreferenceConstants.initializeDefaultValues(getPreferenceStore());
		try {
			CFMLPropertyManager propertyManager = new CFMLPropertyManager();
		}
		catch (Exception e) {
			// do nothing
		}
    }
	
	/**
	 * Returns the shared instance.
	 */
	public static CFMLPlugin getDefault() 
	{
		return plugin;
	}
	
	public PreferenceStore getPropertyStore() {
		return this.propertyStore;
	}
	

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() 
	{
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) 
	{
		ResourceBundle bundle = CFMLPlugin.getDefault().getResourceBundle();
		try 
		{
			return (bundle!=null ? bundle.getString(key) : key);
		} 
		catch (MissingResourceException e) 
		{
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() 
	{
		return this.resourceBundle;
	}
}
