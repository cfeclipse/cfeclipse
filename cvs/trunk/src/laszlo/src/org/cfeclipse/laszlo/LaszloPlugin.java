/*
 * Created on Nov 14, 2004
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
package org.cfeclipse.laszlo;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.cfeclipse.laszlo.editors.LaszloSyntaxDictionary;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.rohanclan.cfml.dictionary.DictionaryManager;
//import com.rohanclan.cfml.editors.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.editors.partitioner.scanners.cfscript.CFScriptCompletionProcessor;
import com.rohanclan.cfml.editors.contentassist.CFContentAssist;
import com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager;
import com.rohanclan.cfml.editors.contentassist.CFMLFunctionAssist;
import com.rohanclan.cfml.editors.contentassist.CFMLScopeAssist;
import com.rohanclan.cfml.editors.contentassist.HTMLTagAssistContributor;

/**
 * The main plugin class to be used in the desktop.
 */
public class LaszloPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static LaszloPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	/** Content Assist Manager */
	private CFEContentAssistManager camInstance;
	
	/**
	 * The constructor.
	 */
	public LaszloPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("plugin");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		try
		{
			//load all the syntax dictionaries
			//we have to do it the hard way because we need to change the
			//base dir
			LaszloSyntaxDictionary laszlodic = new LaszloSyntaxDictionary();
			laszlodic.setDictionaryBaseURL(new URL(
				LaszloPlugin.getDefault().getBundle().getEntry("/"), "dictionary/"
			));
			//this should be a setting or something at some point. the base
			//one holds the lowest level laszlo objects only hard core people use
			//that stuff I think not us newbies :)
			//laszlodic.loadDictionary("laszlo_base.xml");
			laszlodic.loadDictionary("laszlo.xml");
			
			DictionaryManager.addDictionary(
				LaszloSyntaxDictionary.LASDIC, laszlodic
			);
			
			LaszloSyntaxDictionary laszloscript = new LaszloSyntaxDictionary();
			laszloscript.setDictionaryBaseURL(new URL(
				LaszloPlugin.getDefault().getBundle().getEntry("/"), "dictionary/"
			));
			laszloscript.loadDictionary("laszlo_script.xml");
			
			DictionaryManager.addDictionary(
				LaszloSyntaxDictionary.LASSCRIPT, laszloscript
			);
			
			//startup the image registry
			//CFPluginImages.initCFPluginImages();
			
			setupCAM();
		}
		catch(Exception e)
		{
			//lots of bad things can happen...
			e.printStackTrace(System.err);
		}
		
	}
	
	/**
	 * Returns the global Content Assist Manager.
	 * 
	 * @see com.rohanclan.cfml.editors.contentassist.CFEContentAssistManager
	 * @return The CAM instance
	 */
	public CFEContentAssistManager getGlobalCAM()
	{
	    return this.camInstance;
	}
	
	/**
     * Setups up the Content Assist Manager
     */
	private void setupCAM() {
		camInstance = new CFEContentAssistManager();
        
		HTMLTagAssistContributor laszloAssistor = new HTMLTagAssistContributor(
			DictionaryManager.getDictionary(LaszloSyntaxDictionary.LASDIC)
		);
		
		CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		//cfscp.changeDictionary(DictionaryManager.JSDIC);
		cfscp.changeDictionary(LaszloSyntaxDictionary.LASSCRIPT);
		
		//thses are kind of like the rules on how to do content assist
		camInstance.registerRootAssist(cfscp);
		camInstance.registerRootAssist(new CFContentAssist());
		camInstance.registerRootAssist(new CFMLScopeAssist());
		camInstance.registerRootAssist(new CFMLFunctionAssist());
		
		//this is the data
		camInstance.registerTagAssist(laszloAssistor);
		camInstance.registerAttributeAssist(laszloAssistor);
		camInstance.registerValueAssist(laszloAssistor);
	}
	
	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static LaszloPlugin getDefault() {
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
	public static String getResourceString(String key) {
		ResourceBundle bundle = LaszloPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
