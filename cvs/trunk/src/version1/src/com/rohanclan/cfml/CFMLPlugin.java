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
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.dictionary.DictionaryManager;
import org.eclipse.jface.preference.PreferenceStore;

import com.rohanclan.cfml.parser.CFParser;
import com.rohanclan.cfml.preferences.*;
import com.rohanclan.cfml.properties.CFMLPropertyManager;

/**
 * @author Rob
 * (actually the wizard made this file)
 * The main plugin class
 */
public class CFMLPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static CFMLPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	private static CFParser cfparserAction = null;
	private PreferenceStore propertyStore; 
	
	//private CFMLPreferenceManager preferenceManager;
	/**
	 * The constructor. This is supposedly replaced with
	 * public CFMLPlugin()
	 * {
	 *	 super();
	 * ...
	 * but if I try to do it that way I get errors and the editor
	 * wont work
	 */
	public CFMLPlugin(IPluginDescriptor descriptor)
	{
		super(descriptor);
	
		plugin = this;
		
		System.out.println(
			"Property store file set to " + 
			CFMLPlugin.getDefault().getStateLocation().toString()
			+ "/properties.ini"
		);
		propertyStore = new PreferenceStore(
			CFMLPlugin.getDefault().getStateLocation().toString()
			+ "/properties.ini"
		);
		
		
		try 
		{
			resourceBundle = ResourceBundle.getBundle(
				//"com.rohanclan.cfml.CFMLPluginResources"
				"plugin"
			);	
		} 
		catch (MissingResourceException x) 
		{
			x.printStackTrace(System.err);
			resourceBundle = null;
		}
		
		try
		{
			//load all the syntax dictionaries (they dont really load right now)
			DictionaryManager.initDictionaries();
			
			//startup the image registry
			CFPluginImages.initCFPluginImages();
		}
		catch(Exception e)
		{
			//lots of bad things can happen...
			e.printStackTrace(System.err);
		}
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
