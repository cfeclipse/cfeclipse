/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cfeclipse.cfeclipsecall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a Mac OS helper class to assist with handling of MAC OS events using
 * the <code>com.apple.eawt.Application</code> class. This class will not work
 * on a platform other than Mac OS.
 * <p/>
 * It's important to note, that in this class, we use classes from the
 * <code>com.apple.eawt</code> package, but since no such package exist on a
 * non-Mac platform, we must use reflection API to create objects and invoke
 * methods of those Mac-specific classes.
 * <p/>
 * Created date: Jan 22, 2008
 * 
 * @author Yevgeny Nyden
 */
public class MacOsHandler {

	/** Private class logger. */
	private static Logger log = Logger.getLogger(MacOsHandler.class.toString());

	/** Flag to indicate that this handler has been initialized. */
	private static boolean isInitialized = false;

	/**
	 * Method to initialized Mac OS application handler, which functionality is
	 * based upone the <code>com.apple.eawt.Application</code> class. Here we
	 * add custom "about" and "quit" handlers to the Mac application menu bar. <br />
	 * <br />
	 * This method should be called once per application run - successive calls
	 * will have no effect.
	 */
	public static synchronized void initializeMacOsHandler() {
		if (isInitialized) {
			return;
		}
		try {
			// creating an Application object
			Class<?> appClass = Class.forName("com.apple.eawt.Application");
			Object application = appClass.newInstance();

			// geting the Application#addApplicationListener() method
			Class<?> listClass = Class.forName("com.apple.eawt.ApplicationListener");
			Method addAppListmethod = appClass.getDeclaredMethod("addApplicationListener", new Class[] { listClass });

			// creating and adding a custom adapter/listener to the Application
			Class<?> adapterClass = Class.forName("com.apple.eawt.ApplicationAdapter");
			Object listener = ListenerProxy.newInstance(adapterClass.newInstance());
			addAppListmethod.invoke(application, new Object[] { listener });

			isInitialized = true;

		} catch (Exception e) {
			log.log(Level.WARNING, "Exception is thrown when using reflection API on the classes "
					+ "of the com.apple.eawt package! Are we on Mac OS? Exception: ", e);
		}
	}

}

/**
 * Class to assist with intercepting calls to the handleAbout() and handleQuit()
 * methods of the <code>com.apple.eawt.ApplicationAdapter</code> class and
 * triggering appropriate 'about' and 'quit' actions for these events.
 */
class ListenerProxy implements InvocationHandler {

	/** Private class logger. */
	private static Logger log = Logger.getLogger(MacOsHandler.class.toString());

	/** Reference to the proxied object. */
	private Object object;

	/**
	 * Method to create a new proxy for the given object.
	 * 
	 * @param obj
	 *            Object to proxy.
	 * @return Reference to the new proxy.
	 */
	public static Object newInstance(Object obj) {
		return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				obj.getClass().getInterfaces(), new ListenerProxy(obj));
	}

	/**
	 * Constructor that sets the reference to the proxied object.
	 * 
	 * @param obj
	 *            Reference to the proxied object to set.
	 */
	private ListenerProxy(Object obj) {
		this.object = obj;
	}

	private String getBundleRoot() {
		String bundleRoot = "";
		try {
			Class<?> fileManagerC = Class.forName("com.apple.eio.FileManager");
			Object fileManager = fileManagerC.newInstance();
			bundleRoot = (String) fileManager.getClass().getDeclaredMethod("getPathToApplicationBundle",new Class[]{}).invoke(fileManager,new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bundleRoot;
	}

	private void importProperties(String filename) {
		Properties properties = new Properties();
		String bundleRoot = getBundleRoot();
			try {
				properties.load(new FileInputStream(filename));
		        properties.store(new FileOutputStream(bundleRoot + "/eclipsecall.properties"), "Imported");
				System.out.println("Imported properties to: " + bundleRoot + "/eclipsecall.properties");
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

	private void setProperties() {
	String appBundleRoot = getBundleRoot();
	Properties properties = new Properties();
		try {
			File propFile = new File(appBundleRoot + "/eclipsecall.properties");
			if(!propFile.exists()) {
				properties.setProperty(CallClient.ENV_SOCKET, Integer.toString(CallClient.DEFAULT_SOCKET));
				properties.setProperty(CallClient.ENV_CALL, "denstest");
				System.out.println("New properties: " + appBundleRoot + "/eclipsecall.properties");
				System.out.println(properties.toString());
		        properties.store(new FileOutputStream(appBundleRoot + "/eclipsecall.properties"), null);
			} else {				
				properties.load(new FileInputStream(appBundleRoot + "/eclipsecall.properties"));
			}
			System.setProperty(CallClient.ENV_SOCKET,properties.getProperty(CallClient.ENV_SOCKET));
			System.setProperty(CallClient.ENV_CALL,properties.getProperty(CallClient.ENV_CALL));
			System.out.println("Loaded properties: " + appBundleRoot + "/eclipsecall.properties");
			System.out.println(properties.toString());
			System.out.println(System.getProperty(CallClient.ENV_CALL));
			System.out.println(System.getProperty(CallClient.ENV_SOCKET));
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	/**
	 * <p/>
	 * Triggers appropriate events for the "handleAbout" and "handleQuit"
	 * methods. Executes default (proxied) code for other methods.
	 * </p> {@inheritDoc}
	 */
	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		Object result = null;
		String eclipse = null;
		try {
			if ("handleAbout".equals(m.getName())) {
				// handling about action
				log.fine("Processing handleAbout() method...");
				// PrefCountRegistry.getInstance().getMainWindow().showAboutInfo();
				Object event = args[0];
				Method eventSetter = Class.forName("com.apple.eawt.ApplicationEvent").getDeclaredMethod("setHandled",
						new Class[] { Boolean.TYPE });
				eventSetter.invoke(event, new Object[] { Boolean.TRUE });

			} else if ("handleOpenFile".equals(m.getName())) {
				// handling quit action
				Object event = args[0];
				String appBundleRoot = getBundleRoot();
				Method eventGetFile = Class.forName("com.apple.eawt.ApplicationEvent").getDeclaredMethod("getFilename",
						new Class[] {});
				String fileName = (String) eventGetFile.invoke(event, new Object[] {});
				System.out.println(appBundleRoot+" Processing handleOpenFile() method for " + fileName);
				if(fileName.indexOf("properties.cfeclipsecall") != -1) {
					System.out.println(appBundleRoot+"FSCK " + fileName);
					importProperties(fileName);
				} else {
					setProperties();
					if(System.getProperty(CallClient.ENV_CALL).length() != 0){
						eclipse = System.getProperty(CallClient.ENV_CALL);
					}
					System.out.println();
					CallClient.doOpen(null, eclipse, fileName);					
				}

			} else {
				// for now, we don't care about other methods
				result = m.invoke(object, args);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, "Unexpected invocation exception!", e);
		}
		return result;
	}

}
