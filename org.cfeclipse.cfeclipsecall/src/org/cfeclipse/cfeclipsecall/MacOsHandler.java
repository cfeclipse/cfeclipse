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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

final class SimpleAboutDialog extends JDialog {
	private static final long serialVersionUID = -2397156282450447293L;

	public SimpleAboutDialog() {
	    super(new JFrame(), "About Dialog", true);

	    Box b = Box.createVerticalBox();
	    b.add(Box.createGlue());
	    String labelText =
	        "<html>CFEclipseCall is a program for opening cfml files " +
	        "from the operating system into eclipse." +
	        "<P>" +
	        "<DL>" +
	        "  <DT>port</DT><DD>this port should matche the cfeclipse cfeclipsecall preference port<br/></DD>" +
	        "  <DT>eclipse</DT><DD>this is the path to eclipse.  It should be eclipse, and NOT eclipse.app!</DD>" +
	        "</DL> see: http://cfeclipse.org/wiki/CFEclipseCall";
	      JLabel fancyLabel =
	        new JLabel(labelText,JLabel.CENTER);
	      fancyLabel.setBorder
	        (BorderFactory.createTitledBorder("About CFEclipseCall"));   
	    b.add(fancyLabel);
	    b.add(Box.createGlue());
	    getContentPane().add(b, "Center");

	    JPanel p2 = new JPanel();
	    JButton ok = new JButton("Ok");
	    p2.add(ok);
	    getContentPane().add(p2, "South");

	    ok.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	        setVisible(false);
	      }
	    });

	    setSize(460, 270);
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
	private static String appBundleRoot = getBundleRoot();
	private static String propertiesFile = appBundleRoot + "/properties.cfeclipsecall";
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

	private static String getBundleRoot() {
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
			try {
				properties.load(new FileInputStream(filename));
		        properties.store(new FileOutputStream(propertiesFile), "Imported");
				System.out.println("Imported properties to: " + propertiesFile);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

	private void setProperties() {
	Properties properties = new Properties();
		try {
			File propFile = new File(propertiesFile);
			if(!propFile.exists()) {
				System.out.println("No existing property file, opening editor!");
				System.setProperty(CallClient.ENV_SOCKET,String.valueOf(CallClient.DEFAULT_SOCKET));
				System.setProperty(CallClient.ENV_CALL,"eclipse");
				CFEclipseCallPropertyEditor editor = new CFEclipseCallPropertyEditor(propFile.getPath());
				editor.setVisible(true);
			} else {				
				System.out.println("Using property file: " + propertiesFile);
				properties.load(new FileInputStream(propertiesFile));
				System.setProperty(CallClient.ENV_SOCKET,properties.getProperty(CallClient.ENV_SOCKET));
				System.setProperty(CallClient.ENV_CALL,properties.getProperty(CallClient.ENV_CALL));
			}
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
				SimpleAboutDialog about = new SimpleAboutDialog();
				about.setVisible(true);
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
					CFEclipseCallPropertyEditor editor = new CFEclipseCallPropertyEditor(propertiesFile);
					editor.setVisible(true);
				} else {
					setProperties();
					if(System.getProperty(CallClient.ENV_CALL).length() != 0){
						eclipse = System.getProperty(CallClient.ENV_CALL);
					}
					CallClient.doOpen(null, eclipse, fileName);					
				}

			} else if ("handleOpenApplication".equals(m.getName())) {
				// gets called every time file is opened, so we have to ignore
			} else if ("handleQuit".equals(m.getName())) {
				System.exit(0);
			} else {
				// for now, we don't care about other methods
				System.out.println("handling:"+ m.getName());
				result = m.invoke(object, args);
			}
		} catch (Exception e) {
			log.log(Level.WARNING, "Unexpected invocation exception!", e);
		}
		return result;
	}

}
