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

import org.eclipse.ui.plugin.AbstractUIPlugin;
//import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.internal.utils.Assert;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import com.rohanclan.cfml.editors.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.editors.contentassist.CFContentAssist;
import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.editors.contentassist.CFMLScopeAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLTagAssist;
import com.rohanclan.cfml.editors.contentassist.HTMLTagAssistContributor;

import org.eclipse.jface.preference.PreferenceStore;
import org.osgi.framework.BundleContext;

import com.rohanclan.cfml.preferences.*;
import com.rohanclan.cfml.properties.CFMLPropertyManager;

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
	
	/**
	 * Returns the global Content Assist Manager.
	 * 
	 * @see com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager
	 * @return The CAM instance
	 * 
	 */
	public CFEContentAssistManager getGlobalCAM()
	{
	    Assert.isNotNull(this.camInstance);
	    return this.camInstance;
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
			resourceBundle = ResourceBundle.getBundle("plugin");	
		} 
		catch (MissingResourceException x) 
		{
			x.printStackTrace(System.err);
			resourceBundle = null;
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
		
		propertyStore = new PreferenceStore(
			CFMLPlugin.getDefault().getStateLocation().toString()
			+ "/properties.ini"
		);
		
		try
		{
			//load all the syntax dictionaries
			DictionaryManager.initDictionaries();
			
			//startup the image registry
			CFPluginImages.initCFPluginImages();
			
			setupCAM();
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
    private void setupCAM() {
        this.camInstance = new CFEContentAssistManager();
        
        CFMLTagAssist cfmlAssistor = new CFMLTagAssist(DictionaryManager.getDictionary(DictionaryManager.CFDIC));
        HTMLTagAssistContributor htmlAssistor = new HTMLTagAssistContributor(DictionaryManager.getDictionary(DictionaryManager.HTDIC));

        CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);
        
		this.camInstance.registerRootAssist(cfscp);
        this.camInstance.registerRootAssist(new CFContentAssist());
        this.camInstance.registerRootAssist(new CFMLScopeAssist());

        this.camInstance.registerTagAssist(cfmlAssistor);
        this.camInstance.registerAttributeAssist(cfmlAssistor);
        this.camInstance.registerValueAssist(cfmlAssistor);

        this.camInstance.registerTagAssist(htmlAssistor);
        this.camInstance.registerAttributeAssist(htmlAssistor);
        this.camInstance.registerValueAssist(htmlAssistor);
        
        
        
        this.camInstance.registerTagAssist(new CFMLScopeAssist());
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
        CFMLPropertyManager propertyManager = new CFMLPropertyManager();
        propertyManager.initializeDefaultValues();
    }
	
	/**
	 * Returns the shared instance.
	 */
	public static CFMLPlugin getDefault() 
	{
		return plugin;
	}
	
	public PreferenceStore getPropertyStore() {
		return propertyStore;
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
		return resourceBundle;
	}
}
