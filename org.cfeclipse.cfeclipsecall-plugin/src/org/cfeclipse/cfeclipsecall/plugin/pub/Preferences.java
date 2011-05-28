package org.cfeclipse.cfeclipsecall.plugin.pub;

import org.cfeclipse.cfeclipsecall.core.IEclipseCallPreferences;
import org.cfeclipse.cfeclipsecall.core.IPreferencesChangeListener;
import org.cfeclipse.cfeclipsecall.plugin.CFECallPlugin;
import org.cfeclipse.cfeclipsecall.plugin.preferences.EclipseCallPreferencePage;
import org.cfeclipse.cfeclipsecall.plugin.preferences.PreferenceConstants;

public class Preferences implements IEclipseCallPreferences {

	public int getPortNumber() {
		return CFECallPlugin.getDefault().getPreferenceStore().getInt(PreferenceConstants.P_PORT);
	}

	public boolean isFocusRequested() {
		return CFECallPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_FOCUS);
	}

	public void addPreferencesChangeListener(IPreferencesChangeListener listener) {
		EclipseCallPreferencePage.addPreferencesChangeListener(listener);
	}
}
