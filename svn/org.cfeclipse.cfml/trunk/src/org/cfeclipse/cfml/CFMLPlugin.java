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
package org.cfeclipse.cfml;

import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.PropertyConfigurator;
import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.editors.actions.LastActionManager;
import org.cfeclipse.cfml.editors.contentassist.CFContentAssist;
import org.cfeclipse.cfml.editors.contentassist.CFEContentAssistManager;
import org.cfeclipse.cfml.editors.contentassist.CFMLFunctionAssist;
import org.cfeclipse.cfml.editors.contentassist.CFMLScopeAssist;
import org.cfeclipse.cfml.editors.contentassist.CFMLTagAssist;
import org.cfeclipse.cfml.editors.contentassist.CFMLVariableAssist;
import org.cfeclipse.cfml.editors.contentassist.HTMLTagAssistContributor;
import org.cfeclipse.cfml.editors.contentassist.TemplateAssist;
import org.cfeclipse.cfml.editors.partitioner.scanners.cfscript.CFScriptCompletionProcessor;
import org.cfeclipse.cfml.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.templates.template.CFScriptTemplateContextType;
import org.cfeclipse.cfml.templates.template.CFTemplateContextType;
import org.cfeclipse.cfml.util.CFPluginImages;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

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

	public static final String PLUGIN_ID = "org.cfeclipse.cfml";

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

	/** Storage for the Templates */
	private static final String CUSTOM_TEMPLATES_KEY = "org.cfeclipse.cfml.customtemplates"; //$NON-NLS-1$
	private TemplateStore fStore;
	private ContributionContextTypeRegistry fRegistry;

	/**
	 * Returns the global Content Assist Manager.
	 * 
	 * @see org.cfeclipse.cfml.editors.contentassist.CFEContentAssistManager
	 * @return The CAM instance
	 * 
	 */
	public CFEContentAssistManager getGlobalCAM() {

		if (this.camInstance == null) {
			throw new IllegalArgumentException(
					"CFMLPlugin::getGlobalCAM() camInstance is null");
		}
		return this.camInstance;
	}

	/**
	 * Returns the Last Encloser Manager.
	 * 
	 * @see org.cfeclipse.cfml.editors.actions.LastActionManager
	 * @return The LastActionManager instance
	 * 
	 */
	public LastActionManager getLastActionManager() {
		if (this.camInstance == null) {
			throw new IllegalArgumentException(
					"CFMLPlugin::getGlobalCAM() camInstance is null");
		}
		return this.lastEncMgrInstance;
	}

	/**
	 * create a new cfml plugin
	 */
	public CFMLPlugin() {
		super();
		plugin = this;
		try {
			this.resourceBundle = ResourceBundle.getBundle("plugin");
		} catch (MissingResourceException x) {
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

		/*
		 * //System.out.println( "Property store file set to " +
		 * CFMLPlugin.getDefault().getStateLocation().toString() +
		 * "/properties.ini" );
		 */

		PropertyConfigurator.configure(CFMLPlugin.getDefault().getBundle()
				.getEntry("/lib/log4j.properties"));
		this.propertyStore = new PreferenceStore(CFMLPlugin.getDefault()
				.getStateLocation().toString()
				+ "/properties.ini");

		String defaultSnippetPath = CFMLPlugin.getDefault().getStateLocation()
				.toString()
				+ "/snippets";

		File f = new File(defaultSnippetPath);
		if (!f.exists()) {
			f.mkdir();
		}

		try {
			// load all the syntax dictionaries
			DictionaryManager.initDictionaries();

			// startup the image registry
			CFPluginImages.initCFPluginImages();

			setupCAM();
			setupLastEncMgr();
		} catch (Exception e) {
			// lots of bad things can happen...
			e.printStackTrace(System.err);
		}

		EditorPartListener editorListener = new EditorPartListener();
		//doesn't seem to be needed and is causing errors.  Leaving in case 3.4 needs it-- if so, rethink
		//this.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(editorListener);

	}

	/**
	 * Setups up the Content Assist Manager
	 * 
	 */
	public void setupCAM() {
		this.camInstance = newCAM();
	}

	/**
	 * Returns a new Content Assist Manager
	 * 
	 */
	public CFEContentAssistManager newCAM() {
		CFEContentAssistManager camInstance = new CFEContentAssistManager();
		CFMLTagAssist cfmlAssistor = new CFMLTagAssist(DictionaryManager
				.getDictionary(DictionaryManager.CFDIC));
		HTMLTagAssistContributor htmlAssistor = new HTMLTagAssistContributor(
				DictionaryManager.getDictionary(DictionaryManager.HTDIC));

		CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);

		camInstance.registerRootAssist(new TemplateAssist());
		camInstance.registerRootAssist(cfscp);
		camInstance.registerRootAssist(new CFContentAssist());
		camInstance.registerRootAssist(new CFMLScopeAssist());
		camInstance.registerRootAssist(new CFMLFunctionAssist());

		// camInstance.registerRootAssist(new CFMLComponentAssist());
		// //finds the components in a project, removed as we might use a new
		// CFML Varscope parser
		// finds the arguments in a cfc that you are talking about
		camInstance.registerRootAssist(new CFMLVariableAssist());

		camInstance.registerTagAssist(cfmlAssistor);
		camInstance.registerAttributeAssist(cfmlAssistor);
		camInstance.registerValueAssist(cfmlAssistor);

		camInstance.registerTagAssist(htmlAssistor);
		camInstance.registerAttributeAssist(htmlAssistor);
		camInstance.registerValueAssist(htmlAssistor);

		camInstance.registerTagAssist(new CFMLScopeAssist());
		return camInstance;
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

	protected void initializeDefaultPluginPreferences() {

		// super.initializeDefaultPluginPreferences();
		CFMLPreferenceManager preferenceManager = new CFMLPreferenceManager();
		preferenceManager.initializeDefaultValues();
		
		try {
			CFMLPropertyManager propertyManager = new CFMLPropertyManager();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static CFMLPlugin getDefault() {
		return plugin;
	}

	public PreferenceStore getPropertyStore() {
		return this.propertyStore;
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = CFMLPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null ? bundle.getString(key) : key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return this.resourceBundle;
	}

	public TemplateStore getTemplateStore() {
		if (fStore == null) {
			fStore = new ContributionTemplateStore(null, CFMLPlugin
					.getDefault().getPreferenceStore(), CUSTOM_TEMPLATES_KEY);
			try {
				fStore.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fStore;
	}

	public ContextTypeRegistry getContextTypeRegistry() {
		if (fRegistry == null) {
			// create an configure the contexts available in the template editor
			fRegistry = new ContributionContextTypeRegistry();
			fRegistry.addContextType(CFTemplateContextType.XML_CONTEXT_TYPE);
			fRegistry.addContextType(CFScriptTemplateContextType.CFSCRIPT_CONTEXT_TYPE);
		}
		return fRegistry;
	}

}
