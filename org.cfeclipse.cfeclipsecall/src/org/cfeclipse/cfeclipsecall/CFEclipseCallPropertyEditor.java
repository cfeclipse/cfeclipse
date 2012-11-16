package org.cfeclipse.cfeclipsecall;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CFEclipseCallPropertyEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField eclipseTextField;
	private Properties properties = new Properties();
	private JTextField portTextField;
	private String propertiesPath;

	/**
	 * This is the default constructor
	 * @param propfilePath 
	 */
	public CFEclipseCallPropertyEditor(String propfilePath) {
		super();
		File props = new File(propfilePath);
		propertiesPath = propfilePath;
		if(props.exists()){
			properties = getProperties(propfilePath);			
		} else {
	        properties.setProperty(CallClient.ENV_CALL,"eclipse");
	        properties.setProperty(CallClient.ENV_SOCKET,String.valueOf(CallClient.DEFAULT_SOCKET));
		}
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("CFEclipseCall Properties");
	}

	
	// This action creates and shows a modal open-file dialog.
	public class OpenFileAction extends AbstractAction {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JFrame frame;
	    JFileChooser chooser;

	    OpenFileAction(JFrame frame, JFileChooser chooser) {
	        super("Select Eclipse executable...");
	        this.chooser = chooser;
	        this.frame = frame;
	    }

	    public void actionPerformed(ActionEvent evt) {
	        // Show dialog; this method does not return until dialog is closed
	        chooser.showOpenDialog(frame);
	        // Get the selected file
	        File file = chooser.getSelectedFile();
	        eclipseTextField.setText(file.getPath());
	    }
	};
	
	// This action creates and shows a modal save-file dialog.
	public class QuitAction extends AbstractAction {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JFrame frame;

	    QuitAction(JFrame fc) {
	        super("Save & Close");
	        frame = fc;
	    }
	    public void actionPerformed(ActionEvent evt) {
	        properties.setProperty(CallClient.ENV_CALL,eclipseTextField.getText());
	        properties.setProperty(CallClient.ENV_SOCKET,portTextField.getText());
	        System.out.println("Saving properties to:" + propertiesPath);
			try {
				properties.store(new FileOutputStream(propertiesPath), "cfeclipsecall properties");
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage());
				e.printStackTrace();
			}
			System.exit(0);
	    }
	};	

	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			String filename = File.separator+"tmp";
			JFileChooser fc = new JFileChooser(new File(filename));
		    File f;
			try {
				// Set the current directory
				f = new File(new File(".").getCanonicalPath());
				fc.setCurrentDirectory(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Create the actions
			Action openAction = new OpenFileAction(this, fc);
			Action quitAction = new QuitAction(this);

			// Create buttons for the actions
			JButton openButton = new JButton(openAction);
			JButton quitButton = new JButton(quitAction);

			 Box box=new Box(BoxLayout.Y_AXIS);
			
			// Add the buttons to the frame and show the frame

			JPanel eclipsePanel = new JPanel(new BorderLayout());
		    JLabel eclipseLabel = new JLabel("   Eclipse: ");
		    eclipseLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		    eclipseTextField = new JTextField();
		    eclipseLabel.setLabelFor(eclipseTextField);
		    eclipsePanel.add(eclipseLabel, BorderLayout.WEST);
		    eclipsePanel.add(eclipseTextField, BorderLayout.CENTER);
		    box.add(eclipsePanel);
		    box.add(openButton);

		    JPanel portPanel = new JPanel(new BorderLayout());
		    JLabel portLabel = new JLabel("    Port: ");
		    portLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		    portTextField = new JTextField();
		    portLabel.setLabelFor(portTextField);
		    portPanel.add(portLabel, BorderLayout.WEST);
		    portPanel.add(portTextField, BorderLayout.CENTER);
		    box.add(portPanel);			
			box.add(quitButton);

			eclipseTextField.setText(properties.getProperty(CallClient.ENV_CALL));
			portTextField.setText(properties.getProperty(CallClient.ENV_SOCKET));

			jContentPane.add(box, BorderLayout.NORTH);

			this.pack();
			jContentPane.setSize(400, 200);
			this.setSize(400, 200);
		}
		return jContentPane;
	}

	private static Properties getProperties(String filename) {
		Properties properties = new Properties();
		try {
			String str = null;
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader replaceWindowsSlashes = new BufferedReader(new InputStreamReader(fis));
			String replaced = "";
			while (null != ((str = replaceWindowsSlashes.readLine()))) {
				replaced = replaced + str.replace('\\', '/') + System.getProperty("line.separator");
			}
			fis.close();
			replaceWindowsSlashes.close();
			FileOutputStream fos = new FileOutputStream(filename);
			fos.write(replaced.getBytes());
			fos.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			properties.load(new FileInputStream(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}	
	
}
