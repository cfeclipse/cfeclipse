package org.cfeclipse.cfeclipsecall.core;

/**
 * Keeps the EclipseCall Plug-in informed about changes of the associated
 * {@link IPreferencesChangeListener}. This is important for the correct
 * port number to be used for communicating with the clients.
 *
 * @author <a href="mailto:Michael.Schulte@gmx.com">Michael Schulte</a>
 */
public interface IPreferencesChangeListener {
	/**
	 * Sent from the implementor of {@link IPreferencesChangeListener} on
	 * changed settings.
	 */
	public void preferencesChanged();
}
