package org.cfeclipse.cfeclipsecall.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.cfeclipse.cfeclipsecall.plugin.CFECallPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;

public class CallServer {

	private static CallServer callServer = null;

	private CallServer() {
	}

	public static CallServer getDefault() {
		if (callServer == null) {
			synchronized (CallServer.class) {
				if (callServer == null) {
					callServer = new CallServer();
				}
			}
		}
		return callServer;
	}

//	private String lastRequest = "";
	void handleClientRequest(String theRequestParam) {
		try {
			CFECallPlugin.log( IStatus.INFO, "handling request: " + theRequestParam );
			String requestParam = theRequestParam;
			if (requestParam == null || requestParam.trim().length() == 0)
				return;
//if (lastRequest.equals(requestParam)) {
//	CFECallPlugin.log( IStatus.INFO, "same request again... " + theRequestParam );
//	return;
//}
//lastRequest = requestParam;

			requestParam = requestParam.replace('?', '|');

			int idx = requestParam.indexOf("://");
			if (idx != -1) {
				boolean removeUrl = true;
				String start = theRequestParam.substring(0, idx);
				for (int i = 0; i < start.length(); i++) {
					if (!Character.isLetter(start.charAt(i))) {
						removeUrl = false;
						break;
					}
				}
				if (removeUrl) {
					requestParam = URLDecoder.decode(requestParam.substring(idx + 3), "UTF-8");
				}
			}
			if (File.separator.equals("/")) {
				requestParam = requestParam.replaceAll("\\\\", "\\/");
			} else {
				requestParam = requestParam.replaceAll("\\/", "\\\\");
			}

			final String request = requestParam;

			final String[] parts = request.split("\\|");
			final int row[] = { 0 };
			final int col[] = { 0, 0 };
			if (parts.length > 1) {
				row[0] = Integer.parseInt(parts[1]);
				if (parts.length > 2) {
					String strCols[] = parts[2].split("\\-");
					for (int i = 0; i < strCols.length; i++) {
						col[i] = Integer.parseInt(strCols[i]);
					}
				}
			}

			boolean focus = false;
			if (this.preferences != null) {
				focus = this.preferences.isFocusRequested();
			}
			if (this.openHandlers != null) {
				CFECallPlugin.log(IStatus.INFO, "calling " + this.openHandlers.size() + " handlers.");
				for (int i = 0; i < this.openHandlers.size(); i++) {
					IHandleOpenRequest handler = (IHandleOpenRequest) this.openHandlers.get(i);
					handler.handleOpenRequest(theRequestParam, parts[0], row[0], col, focus);
				}
			}
		} catch (Throwable T) {
			T.printStackTrace();
		}
	}

	private Thread threadServer = null;
	protected int port;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public synchronized void start() throws Exception {

		initOpenHandler();
		if (this.preferences != null) {
			this.port = this.preferences.getPortNumber();
		}
		this.threadServer = new Thread(new Runnable() {
			final int thePort = CallServer.this.port;
			final ServerSocket server = new ServerSocket(CallServer.this.port);

			private void startServing() {
				CFECallPlugin.log( IStatus.INFO, "CFEclipseCall started listening on: " + this.thePort);

				while (this.thePort == CallServer.this.port) {
					Socket client = null;
					try {
						client = this.server.accept();
						handleConnection(client);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (client != null) {
							try {
								client.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			private void handleConnection(Socket client) throws IOException {
				BufferedInputStream in = new BufferedInputStream(client.getInputStream());
				int c;
				StringBuffer buf = new StringBuffer();
				while ((c = in.read()) != -1) {
					if (c == '\r' || c == '\n') {
						break;
					}
					buf.append((char) c);
				}
				String request = buf.toString().trim();
				if (request.length() > 0) {
					CallServer.this.handleClientRequest(request);
				}
			}

			public void run() {
				try {
					synchronized (this) {
						this.wait(500);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				startServing();
			}
		}, "CallServerThread");
		this.threadServer.start();
	}

	public void stop() {
		if (this.threadServer != null) {
			this.threadServer.interrupt();
			try {
				Socket server = new Socket("localhost", this.port);
				OutputStream out = server.getOutputStream();
				// send a dummy request
				out.write("\r\n".getBytes());
				out.flush();
			} catch (Throwable T) {
				T.printStackTrace();
			}
			synchronized (this.threadServer) {
				try {
					this.threadServer.join(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.threadServer = null;
		}
	}

	private List openHandlers = null;
	protected IEclipseCallPreferences preferences = null;

	private void initOpenHandler() {
		if (this.openHandlers == null) {
			boolean addNew = false;
			synchronized (this) {
				if (this.openHandlers == null) {
					this.openHandlers = new ArrayList();
					addNew = true;
				}
			}
			if (addNew) {
				CFECallPlugin.log(IStatus.INFO, "initializing extensions.");
				IExtensionRegistry reg = Platform.getExtensionRegistry();
				IExtensionPoint extensionPoint = reg.getExtensionPoint("org.cfeclipse.cfeclipsecall.core.openhandler");
				IExtension extensions[] = extensionPoint.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IExtension ext = extensions[i];
					IConfigurationElement[] elements = ext.getConfigurationElements();
					for (int j = 0; j < elements.length; j++) {
						IConfigurationElement el = elements[j];
						String clz = el.getAttribute("handlerClass");
						if (clz != null) {
							try {
								IHandleOpenRequest handleOpenRequest = (IHandleOpenRequest) Class.forName(clz).newInstance();
								this.openHandlers.add(handleOpenRequest);
							} catch (Throwable T) {
								CFECallPlugin.log(T);
							}
						} else {
							String clzPref = el.getAttribute("prefsClass");
							if (clzPref != null) {
								try {
									this.preferences = (IEclipseCallPreferences) Class.forName(clzPref).newInstance();
									this.preferences.addPreferencesChangeListener(new IPreferencesChangeListener() {
										public void preferencesChanged() {
											new Thread(new Runnable() {
												public void run() {
													try {
														if (CallServer.this.port != CallServer.this.preferences.getPortNumber())
														{
															CallServer.this.stop();
															synchronized (this) {
																this.wait(500);
															}
															CallServer.this.start();
														}
													} catch (Throwable T) {
														T.printStackTrace();
													}
												}
											}, "PreferencesChanged").start();
										}
									});
								} catch (Throwable T) {
									CFECallPlugin.log(T);
								}
							}
						}
					}
				}
			}
		}
	}

}
