package org.cfeclipse.cfeclipsecall.core;

/**
 * Interface for storing / retrieving the settings for the EclipseCall Plug-in.
 * If no extension implementing this interface is present, the EclipseCall
 * Plug-in will use the default settings.
 *
 * @author <a href="mailto:Michael.Schulte@gmx.com">Michael Schulte</a>
 */
public interface IEclipseCallPreferences {
	/**
	 * The socket port number used for communication. Default is 2342.
	 *
	 * @return the stored port number used for communication to the EclipseCall
	 *         client.
	 */
	public abstract int getPortNumber();

	/**
	 * only relevant for gui applications. Default is false.
	 *
	 * @return <code>true</code>: the gui application should be focused after
	 *         opening a file, <code>false</code>: there is no focus required.
	 */
	public abstract boolean isFocusRequested();

	/**
	 * A listener that informs that preferences have changed.
	 *
	 * @param listener
	 *            Will be added by the EclipseCall Plug-in and should be called
	 *            when the preferences have changed.
	 */
	public void addPreferencesChangeListener(IPreferencesChangeListener listener);
}
