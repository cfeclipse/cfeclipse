package org.cfeclipse.cfml.views.explorer.vfs.view.menus;

/**
 * Handles a file open request that has been received from an external client.
 * This should normally invoke the implementing plug-in to open the requested
 * file in an editor. The EclipseCall plug-in sends all registered plug-ins that
 * implement the extension point
 * <code>org.cfeclipse.cfeclipsecall.core.openHandler</code> the file open request
 * to the handlerClass-attribute implemented by this interface.
 *
 * @author <a href="mailto:Michael.Schulte@gmx.com">Michael Schulte</a>
 */
public interface IHandleOpenRequest {

	/**
	 * Handles the file open request, sent by the client.
	 *
	 * @param origRequest
	 *            this is the original request as sent by the client. This is
	 *            only useful for debugging purposes or when presenting an error
	 *            message to the user.
	 * @param fileName
	 *            the absolute filename to open in an editor.
	 * @param row
	 *            the row (or line) to mark within the file. If 0, no marking
	 *            will be done.
	 * @param col
	 *            an int-array of the first and last columns to mark, if the row
	 *            was given. If {0,0}, the whole line should be marked,
	 *            depending on row. Else the first (beginning of the marker) and
	 *            last column (end of the marker) are set.
	 * @param focus
	 *            <ul>
	 *            <li><code>true</code>: eclipse or the application should be
	 *            focused after the file has been loaded or</li>
	 *            <li><code>false</code>: there is no focus needed.</li>
	 */
	public abstract void handleOpenRequest(final String origRequest, final String fileName, final int row, final int[] col, final boolean focus);

}