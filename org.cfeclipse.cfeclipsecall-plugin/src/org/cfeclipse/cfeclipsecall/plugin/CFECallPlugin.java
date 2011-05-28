package org.cfeclipse.cfeclipsecall.plugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import org.cfeclipse.cfeclipsecall.core.CallServer;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The CFECallPlugin class controls the plug-in life cycle
 */
public class CFECallPlugin extends AbstractUIPlugin implements IStartup  {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.cfeclipse.cfeclipsecall.plugin";

	// The shared instance
	private static CFECallPlugin plugin;

	/**
	 * The constructor
	 */
	public CFECallPlugin() {
	}

	int port;
	boolean focus;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		if (plugin == null) {
			synchronized (getClass()) {
				if (plugin == null) {
					plugin = this;
					new Thread(new Runnable() {
						public void run() {
							try {
								synchronized (this) {
									this.wait(500);
								}
								CallServer.getDefault().start();
							} catch (Throwable T) {
								if (T instanceof java.net.BindException) {
									log(IStatus.ERROR,T.getMessage() + ".  Is another instance already running?");
								}
								//T.printStackTrace();
							}
						}
					}).start();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CFECallPlugin getDefault() {
		return plugin;
	}

	public void earlyStartup() {
		// nothing especially to do
	}

	/** Stores log messages. */
	private static StringBuffer logBuffer = new StringBuffer();

	/**
	 * Gets the stored log messages.
	 *
	 * @return the stored log messages.
	 */
	public static String getLogMessage() {
		return logBuffer.toString();
	}

	/**
	 * Send a logging message to the plugin's log.
	 *
	 * @param severity
	 *            the error severity, used from {@link IStatus}.
	 * @param message
	 *            the message to log.
	 */
	public static void log(int severity, String message) {
		StringBuffer bufMessage = new StringBuffer();
		try {
			Calendar calendar = Calendar.getInstance();
			bufMessage.append(calendar.get(Calendar.YEAR));
			bufMessage.append("/");
			bufMessage.append(calendar.get(Calendar.MONTH));
			bufMessage.append("/");
			bufMessage.append(calendar.get(Calendar.DAY_OF_MONTH));
			bufMessage.append(" ");
			if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
				bufMessage.append("0");
			}
			bufMessage.append(calendar.get(Calendar.HOUR_OF_DAY));
			bufMessage.append(":");
			if (calendar.get(Calendar.MINUTE) < 10) {
				bufMessage.append("0");
			}
			bufMessage.append(calendar.get(Calendar.MINUTE));
			bufMessage.append(":");
			if (calendar.get(Calendar.SECOND) < 10) {
				bufMessage.append("0");
			}
			bufMessage.append(calendar.get(Calendar.SECOND));
			bufMessage.append(" CFEclipseCall");

			bufMessage.append(" - ");
			if (severity == IStatus.ERROR) {
				bufMessage.append("Error");
			} else if (severity == IStatus.WARNING) {
				bufMessage.append("Warning");
			} else {
				bufMessage.append("Info");
			}
			bufMessage.append(":\r\n");
			bufMessage.append(message);
			bufMessage.append("\r\n");

			logBuffer.append(bufMessage);

		} catch (Exception e) {
			// nothing to do
		}
		try {
			// compatibility to Eclipse 3.2...
			Class c = Class.forName("org.eclipse.core.runtime.Status");
			Object status = c.getConstructor(new Class[] { int.class, String.class, String.class }).newInstance(
					new Object[] { new Integer(severity), PLUGIN_ID, message });
			Object log = plugin.getClass().getMethod("getLog", (Class[]) null).invoke(plugin, (Object[]) null);
			log.getClass().getMethod("log", new Class[] { c }).invoke(log, new Object[] { status });
		} catch (Throwable T) {
			System.out.println(bufMessage.toString());
		}
	}

	/**
	 * Logs exception.
	 *
	 * @param T
	 *            the exception to log. The stacktrace will be added to the log.
	 */
	public static void log(Throwable T) {
		Throwable cause = T;
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		while (cause != null) {
			cause.printStackTrace(p);
			cause = cause.getCause();
		}
		p.close();
		log(IStatus.ERROR, s.getBuffer().toString());
	}

}
