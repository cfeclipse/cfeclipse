package org.cfeclipse.frameworks.fusebox;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.cfeclipse.frameworks.fusebox.util.PluginImages;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class FuseboxPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static FuseboxPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;

	/** The preferences for the plugin. */
	private PreferenceStore propertyStore; 
	
	/**
	 * The constructor.
	 */
	public FuseboxPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("plugin");
		} catch (MissingResourceException x) {
			x.printStackTrace();
			resourceBundle = null;
		}
		
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		//		startup the image registry
		
		propertyStore = new PreferenceStore(
				FuseboxPlugin.getDefault().getStateLocation().toString()
				+ "/properties.ini"
		);
		
		try {
			PluginImages.initPluginImages();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public static FuseboxPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = FuseboxPlugin.getDefault().getResourceBundle();
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
	
	public static IWorkspace getWorkspace() 
	{
		return ResourcesPlugin.getWorkspace();
	}
	public PreferenceStore getPropertyStore() {
		return propertyStore;
	}
}
