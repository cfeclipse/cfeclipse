package org.tigris.cfeclipse.debugger;

//import org.eclipse.core.runtime.IPluginDescriptor;
//import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.plugin.*;
import org.osgi.framework.BundleContext;

import java.util.*;

/**
 * The main plugin class to be used in the desktop.
 */
public class DebuggerPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static DebuggerPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
		
	/**
	 * The constructor.
	 */
	public DebuggerPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("org.tigris.cfeclipse.debugger.DebuggerPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
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
	public static DebuggerPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = DebuggerPlugin.getDefault().getResourceBundle();
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
