package org.cfeclipse.cfml.snippets;

import java.io.File;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.cfeclipse.cfml.snippets.preferences.CFMLPreferenceManager;
import org.cfeclipse.cfml.snippets.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.snippets.util.CFPluginImages;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SnippetPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.cfeclipse.cfml.snippets";

	// The shared instance
	private static SnippetPlugin plugin;
	/** The preferences for the plugin. */
	private PreferenceStore propertyStore;
	/** The bundle of resources for the plugin */
	private ResourceBundle resourceBundle;

	private CFMLPropertyManager fPropertyManager;

	private CFMLPreferenceManager fPreferenceManager;

	/**
	 * The constructor
	 */
	public SnippetPlugin() {
		super();
		plugin = this;
		try {
			this.resourceBundle = ResourceBundle.getBundle("plugin");
		} catch (MissingResourceException x) {
			x.printStackTrace(System.err);
			this.resourceBundle = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		CFPluginImages.initCFPluginImages();
		this.propertyStore = new PreferenceStore(SnippetPlugin.getDefault().getStateLocation().toString()
				+ "/properties.ini");
		fPreferenceManager = new CFMLPreferenceManager();
		try {
			fPropertyManager = new CFMLPropertyManager();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(fPropertyManager.getListener());

		String defaultSnippetPath = SnippetPlugin.getDefault().getStateLocation().toString() + "/snippets";
		File f = new File(defaultSnippetPath);
		if (!f.exists()) {
			f.mkdir();
		}
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		getWorkbench().getActiveWorkbenchWindow().getSelectionService().removeSelectionListener(fPropertyManager.getListener());
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static SnippetPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public PreferenceStore getPropertyStore() {
		return this.propertyStore;
	}

	public CFMLPropertyManager getPropertyManager() {
		return fPropertyManager;
	}

	public CFMLPreferenceManager getPreferenceManager() {
		return fPreferenceManager;
	}

	protected void initializeDefaultPluginPreferences() {

		fPreferenceManager.initializeDefaultValues();

	}

}
