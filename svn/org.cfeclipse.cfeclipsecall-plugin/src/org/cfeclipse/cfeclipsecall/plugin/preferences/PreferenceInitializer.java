package org.cfeclipse.cfeclipsecall.plugin.preferences;

import org.cfeclipse.cfeclipsecall.plugin.CFECallPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = CFECallPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_PORT, 2342);
		store.setDefault(PreferenceConstants.P_FOCUS, true);
	}

}
