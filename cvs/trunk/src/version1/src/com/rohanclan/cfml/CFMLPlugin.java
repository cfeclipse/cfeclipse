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

import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.resources.*;
import java.util.*;

//import com.rohanclan.coldfusionmx.editors.CFSyntaxDictionary;
//import com.rohanclan.coldfusionmx.editors.actions.SnippetActionLoader;
//import com.rohanclan.coldfusionmx.editors.script.JSSyntaxDictionary;
import com.rohanclan.cfml.util.*;
import com.rohanclan.cfml.dictionary.DictionaryManager;

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
	
	/**
	 * The constructor.
	 */
	public CFMLPlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		plugin = this;
		
		try 
		{
			resourceBundle = ResourceBundle.getBundle(
				"com.rohanclan.coldfusionmx.ColdfusionMXPluginResources"
			);
		} 
		catch (MissingResourceException x) 
		{
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

	/**
	 * Returns the shared instance.
	 */
	public static CFMLPlugin getDefault() 
	{
		return plugin;
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
		ResourceBundle bundle= CFMLPlugin.getDefault().getResourceBundle();
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
