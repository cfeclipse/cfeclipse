/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.preferences;

import java.io.File;
import java.util.Arrays;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.net.ftp.FTPConnection;
import org.cfeclipse.cfml.views.explorer.vfs.view.VFSView;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;



/**
 * @author spike
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FtpConnectionDialog extends AbstractCFEditorPreferencePage  implements ISelectionChangedListener {


	private static final String USERDIR_TEXT = "User Dir is Root Folder      ";
	public FTPConnectionProperties connectionProperties;
	private Text host,path,username,password,connectionid,port;
	//private Button passive,sftp,userDirIsRoot;
	private int DELETE_ID = 3242;
	private Button deleteButton = null;
	private TableViewer connectionTable = null;
	private Label errorMessageLabel = null;
	private String[] connectionTypes = {"file", "ftp", "sftp", "smb", "webdav", "ram", "zip", "jar", "tar", "tgz", "tbz2", "mime","tmp", "http", "https"};
	private String[] connectionTypeExamples = {
			" file:///home/someuser/somedir \nfile:///C:/Documents and Settings \nfile://///somehost/someshare/afile.txt \n/home/someuser/somedir \n c:\\program files\\some dir \nc:/program files/some dir", 
			" ftp://[ username [: password ]@] hostname [: port ][ absolute-path ] \n ftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz", 
			" sftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz", 
			" smb://somehost/home", 
			" webdav://somehost:8080/dist", 
			" ram:///any/path/to/file.txt", 
			" zip:http://somehost/downloads/somefile.zip!/some%21dir", 
			" jar:zip:outer.zip!/nested.jar!/some%21dir", 
			" tar:gz:http://anyhost/dir/mytar.tar.gz!/mytar.tar!/path/in/tar/README.txt", 
			" tgz:file://anyhost/dir/mytar.tgz!/somepath/somefile", 
			" tbz2:file://anyhost/dir/mytar.tgz!/somepath/somefile", 
			" mime:file:///your/path/mail/anymail.mime!/ \n This filesystem can read mails and its attachements like archives.", 
			" tmp://dir/wee\n Provides access to a temporary file system, or scratchpad, that is deleted when Commons VFS shuts down. The temporary file system is backed by a local file system.", 
			" http://myusername@somehost/index.htm", 
			" https://myusername@somehost/index.htm"};
	private Combo connectionType = null;
	private boolean isDirty = false;
	private Button openDirButton;
	private Button testButton;
	private Button userDirIsRoot;
	//private Button isPassive;
	
	/**
	 * @param parent
	 */
//	public FtpConnectionDialog(Shell parent,String connectionId) {
//		// TODO Auto-generated constructor stub
//		super();
//		setTitle(VFSView.getResourceString("fs.wiz.page.title"));
//		setDescription(VFSView.getResourceString("fs.wiz.desc"));
//		
//		connectionProperties = new FTPConnectionProperties(connectionId);
//		
//	}
	private SelectionListener fDeleteButtonListener = new SelectionListener() {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
			    MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_QUESTION | SWT.YES | SWT.NO);
			    confirm.setMessage("Are you sure you want to delete the " + fCurrentConnectionId + " connection?");
			    if (confirm.open() == SWT.YES) {
					FTPConnectionProperties.deleteConnection(connectionProperties.getConnectionid());
					connectionTable.setInput(new Object());
			    }
		}
	};
	private Button saveButton;
	private SelectionListener fSaveButtonListener = new SelectionListener() {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			try {
				performOk();
				
				selectItem();
				}
				catch (Exception ex) {
					ex.printStackTrace();
			}		
		}
	};	
	private Button copyButton;
	private SelectionListener fCopyButtonListener = new SelectionListener() {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
		    connectionProperties = connectionProperties.copy(fCurrentConnectionId);
		    redraw();
			connectionid.setEditable(true);
			connectionid.setSelection(connectionid.getText().length());
			copyButton.setEnabled(false);
		}
	};
	private String fCurrentConnectionId;
	private Label connectionExampleText;
	private Group editArea;
	private Group hostGroup;
	private Group sftpGroup;
	private Text timeoutSeconds;
	private Button strictHostKeyCheck;
	private Text hostsFile;
	private Button isPublicKeyAuth;
	private Text keyFile;
	private Group saveGroup;	

	public void setCurrentConnectionId(String connectionId) {
		fCurrentConnectionId = connectionId;
		connectionProperties = new FTPConnectionProperties(connectionId);
	    redraw();
		selectItem();
	}
	
	public void createControl(Composite parent) {
		noDefaultAndApplyButton();
		super.createControl(parent);
		
	}

	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);
	    connectionProperties = new FTPConnectionProperties(null);
		Arrays.sort(connectionTypes);
		Arrays.sort(connectionTypeExamples);
//		getOverlayStore().load();
//		getOverlayStore().start();
		Composite result= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		result.setLayout(layout);
		createDialogArea(result);
		redraw();
		
	    return result;
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
	    String connectionId = event.getSelection().toString();
	    if (connectionId.indexOf("[") == 0) {
	        connectionId = connectionId.substring(1,connectionId.length()-1);
        }
	    if (connectionId.equals(ConnectionsContentProvider.NEW_CONNECTION)) {
	        connectionId = null;
	    }
	    
	    connectionProperties = new FTPConnectionProperties(connectionId);
	    redraw();
    }

	private void redraw() {
	    fCurrentConnectionId = connectionProperties.getConnectionid();
	    connectionid.setText(connectionProperties.getConnectionid());
	    connectionType.setText(connectionProperties.getType());
	    host.setText(connectionProperties.getHost());
	    path.setText(connectionProperties.getPath());
	    port.setText(String.valueOf(connectionProperties.getPort()));
	    username.setText(connectionProperties.getUsername());
	    password.setText(connectionProperties.getPassword());
	    timeoutSeconds.setText(String.valueOf(connectionProperties.getTimeoutSeconds()));
	    keyFile.setText(connectionProperties.getKeyFile());
	    keyFile.setEnabled(connectionProperties.getIsPublicKeyAuth());
	    hostsFile.setText(connectionProperties.getHostsFile());
	    hostsFile.setEnabled(connectionProperties.getStrictHostKeyCheck());
	    //passive.setSelection(connectionProperties.getPassive());
	    userDirIsRoot.setSelection(connectionProperties.getUserDirIsRoot());
	    isPublicKeyAuth.setSelection(connectionProperties.getIsPublicKeyAuth());
	    if (connectionProperties.getConnectionid().length() == 0) {
	        
			saveButton.setText("Create Connection");
			connectionid.setEditable(true);
			deleteButton.setEnabled(false);
			testButton.setEnabled(false);
			copyButton.setEnabled(false);
			
	    }
	    else {	        
			saveButton.setText("Save Connection");
			connectionid.setEditable(false);
			connectionid.setEnabled(false);
			deleteButton.setEnabled(true);
	    }
	}
	
	@Override
	public boolean isValid() {
		if(connectionProperties.getConnectionid() == "") {
			validateInput();
			if(errorMessageLabel.getText().length() > 0)
				return false;
		}
		return super.isValid();
	}
	
	protected Control createDialogArea(Composite parent) {

		Composite container = new Composite(parent, SWT.RESIZE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginTop = 0;
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label summaryLabel = new Label(container, SWT.LEFT|SWT.WRAP|SWT.BOLD);
		FontData oldFontData[] = parent.getFont().getFontData();
		FontData fontData = new FontData();
		fontData.setStyle(oldFontData[0].getStyle()|SWT.BOLD);
		fontData.setHeight(oldFontData[0].getHeight());
		fontData.setName(oldFontData[0].getName());
		summaryLabel.setText("Use this panel to create, edit, copy, and delete file explorer connections");
		Font font = new Font(container.getDisplay(),fontData);
		summaryLabel.setFont(font);
		GridData summaryLabelData = new GridData();
		summaryLabelData.horizontalSpan = 2;
		summaryLabel.setLayoutData(summaryLabelData);
		summaryLabel.pack();
		

		final GridData tableData = new GridData(GridData.FILL_VERTICAL | GridData.GRAB_VERTICAL);
		tableData.widthHint = 200;
	
		Group tableArea= createGroup(1, container, "Connections");
		tableArea.setLayoutData(tableData);
		GridLayout tableLayout = new GridLayout();
		tableLayout.numColumns = 1;
		tableArea.setLayout(tableLayout);

		
		connectionTable = new TableViewer(tableArea,SWT.SINGLE|SWT.BORDER| SWT.V_SCROLL | SWT.RESIZE);
		final Table table = connectionTable.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL));
		connectionTable.setContentProvider( new ConnectionsContentProvider());
		connectionTable.addSelectionChangedListener(this);
		
		connectionTable.setInput(new Object());
		
		editArea= createGroup(1, container, "Edit Connection");
		editArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout editLayout = new GridLayout();
		editLayout.numColumns = 3;
		editArea.setLayout(editLayout);

		// Connectionid
		connectionid = createTextControl(editArea,"Connection Name:",connectionProperties.getHost(),50);

		
		//Type of Connection
		connectionType = createComboControl(editArea, "Connection Type", connectionTypes, 50);
		connectionType.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				if(connectionType.getText().equalsIgnoreCase("file") 
						|| connectionType.getText().equalsIgnoreCase("jar")
						|| connectionType.getText().equalsIgnoreCase("zip")
						|| connectionType.getText().equalsIgnoreCase("ram")
						|| connectionType.getText().equalsIgnoreCase("tar")
						|| connectionType.getText().equalsIgnoreCase("tgz")
						|| connectionType.getText().equalsIgnoreCase("tbz2")
						|| connectionType.getText().equalsIgnoreCase("mime")
						|| connectionType.getText().equalsIgnoreCase("tmp")
						){
					setGroupVisible(hostGroup,false);
					host.setEnabled(false);
					port.setText("0");
					port.setEnabled(false);
//					passive.setEnabled(false);
//					passive.setSelection(false);
					userDirIsRoot.setEnabled(false);
					userDirIsRoot.setSelection(false);
					username.setEnabled(false);
					password.setEnabled(false);
					openDirButton.setEnabled(true);
					copyButton.setEnabled(true);
				}
				else if(connectionType.getText().equalsIgnoreCase("ftp")){
					setGroupVisible(hostGroup,true);
					host.setEnabled(true);
					port.setEnabled(true);
//					passive.setEnabled(true);
					userDirIsRoot.setEnabled(true);
					username.setEnabled(true);
					password.setEnabled(true);
					openDirButton.setEnabled(false);
					port.setText("21");
					copyButton.setEnabled(true);
				}
				else{ //sftp, webdav, etc.
					setGroupVisible(hostGroup,true);
					host.setEnabled(true);
					port.setEnabled(true);
					openDirButton.setEnabled(false);
					port.setText("22");					
//					passive.setEnabled(false);
//					passive.setSelection(false);
					userDirIsRoot.setEnabled(true);
					userDirIsRoot.setSelection(true);
					username.setEnabled(true);
					password.setEnabled(true);
					copyButton.setEnabled(true);
				}
				if(connectionType.getText().equalsIgnoreCase("sftp")){
					setGroupVisible(sftpGroup,true);
					userDirIsRoot.setVisible(true);
					userDirIsRoot.setSelection(true);
				}
				else if(connectionType.getText().equalsIgnoreCase("ftp")){
					setGroupVisible(sftpGroup,false);
					userDirIsRoot.setVisible(true);
					userDirIsRoot.setSelection(true);
				} else {
					setGroupVisible(sftpGroup,false);
					userDirIsRoot.setVisible(false);
				}
				isValid();
				updateConnextionExample();
			}
			
			
		});
		
		hostGroup = createGroup(3, editArea, "");
		// Host name
		host = createTextControl(hostGroup,"Host Name:",connectionProperties.getHost(),50);

		Group userGroup = createGroup(6, hostGroup, "");
		username = createTextControl(userGroup,"Username:",connectionProperties.getUsername(),20);		
		password = createPasswordControl(userGroup,"Password:",connectionProperties.getPassword(),20);

		// Port
		Group portGroup = createGroup(9, hostGroup, "");
		// userDirIsRoot
		//userDirIsRoot = createCheckboxControl(portGroup,USERDIR_TEXT,connectionProperties.getUserDirIsRoot());
		userDirIsRoot = addCheckBox(portGroup,USERDIR_TEXT,"userDirIsRoot",0);
		port = createNumberControl(portGroup,"Port:",connectionProperties.getPort(),6);
		
		timeoutSeconds = createNumberControl(portGroup,"Timeout (seconds):",connectionProperties.getTimeoutSeconds(),3);

		// SFTP stuff
		sftpGroup = createGroup(6, hostGroup, "");
		strictHostKeyCheck = createCheckboxControl(sftpGroup,"Verify Host:",connectionProperties.getStrictHostKeyCheck());
		hostsFile = createTextControl(sftpGroup,"Hosts File:",connectionProperties.getHostsFile(),50);
		if(connectionProperties.getStrictHostKeyCheck()) {
			hostsFile.setEnabled(true);
		} else {
			hostsFile.setEnabled(false);			
		}
		strictHostKeyCheck.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			public void widgetSelected(SelectionEvent e) {				
				hostsFile.setEnabled(!hostsFile.isEnabled());
			}
		});
		isPublicKeyAuth = createCheckboxControl(sftpGroup,"Public Key Auth:",connectionProperties.getStrictHostKeyCheck());
		keyFile = createTextControl(sftpGroup,"Key File:",connectionProperties.getKeyFile(),50);
		if(connectionProperties.getIsPublicKeyAuth()) {
			keyFile.setEnabled(true);
		} else {
			keyFile.setEnabled(false);			
		}
		isPublicKeyAuth.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			public void widgetSelected(SelectionEvent e) {				
				keyFile.setEnabled(!keyFile.isEnabled());
			}
		});

		Group pathGroup = createGroup(6, editArea, "");
		Label label = new Label(pathGroup,SWT.RIGHT );
		label.setText("Path:");
		path = new Text(pathGroup,SWT.LEFT | SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(42);
		path.setLayoutData(data);
		path.setText(connectionProperties.getPath());
		//Add a button to this control
		
		openDirButton = new Button(pathGroup,SWT.NONE);
		openDirButton.setText("Browse:");
		
		openDirButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				String connectionTypeStr = connectionType.getText().trim();

				String dir = null;
				if (connectionTypeStr.startsWith("file")) {
					DirectoryDialog fileDialog = new DirectoryDialog(getShell(), SWT.OPEN);
					if (path != null && path.getText().trim().length() > 0) {
						fileDialog.setFilterPath(path.getText());
					}
					dir = fileDialog.open();
				} else {
					FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
					if (path != null && path.getText().trim().length() > 0) {
						fileDialog.setFilterPath(path.getText());
					}
					if (connectionTypeStr.matches("jar|zip")) {
						fileDialog.setFilterExtensions(new String[] { connectionTypeStr });
					}
					dir = fileDialog.open();
				}

				if (dir != null) {
					dir = dir.trim();
					if (dir.length() > 0) {
						path.setText(dir);
					}
				}
			}

		});
		// Passive mode
		//passive = createCheckboxControl(editArea,"Passive mode:",connectionProperties.getPassive());

		errorMessageLabel = new Label(container, SWT.LEFT);
		errorMessageLabel.setFont(parent.getFont());
		Color color = new Color(Display.getCurrent(),255,0,0);
		errorMessageLabel.setForeground(color);
		GridData errorLabelData = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL);
		errorLabelData.horizontalSpan = 2;
		errorMessageLabel.setLayoutData(errorLabelData);
		
		connectionid.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validateInput();
					}
				}
			);
		
		host.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validateInput();
					}
				}
			);
		
		
		selectItem();

		saveGroup= createGroup(4, editArea, "");
	    deleteButton = addButton(saveGroup, "Delete",0,1);
	    deleteButton.addSelectionListener(fDeleteButtonListener);
	    testButton = addButton(saveGroup, "Test",42,1);
		testButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				
				applyProperties();
				FTPConnection ftpCon = new FTPConnection();
				ftpCon.setConnectionProperties(connectionProperties);
				MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION);
				if(ftpCon.isConnectable()) {
				    confirm.setMessage("Connection Successful!");					
				} else {
					if(connectionProperties.getType() == "ftp") {
					    confirm.setMessage("Connection FAILURE! Try toggling the passive/userdir properties if you are sure the rest is correct");						
					} else {
						confirm.setMessage("Connection FAILURE!");
					}
				}
				confirm.open();
			}
			
			
		});
	    saveButton = addButton(saveGroup, "Save Connection",0,1);
	    saveButton.addSelectionListener(fSaveButtonListener);
	    copyButton = addButton(saveGroup, "Copy",0,1);
	    copyButton.addSelectionListener(fCopyButtonListener);
	    
		Group connectionExampleGroup = createGroup(3, editArea, "Connection Example");
		connectionExampleText = new Label(connectionExampleGroup,SWT.LEFT );
		connectionExampleGroup.setVisible(false);
		updateConnextionExample();
		return container;
	}
	
	
	
	private void updateConnextionExample() {
		String connectionTypeVal = connectionType.getText();
		int exampleIndx = Arrays.binarySearch(connectionTypes, connectionTypeVal.trim());
		if(exampleIndx > -1) {			
			String example = connectionTypeExamples[exampleIndx];
			connectionExampleText.setText(example);		
			connectionType.setToolTipText(connectionProperties.getURI());
		}
	}

	private void selectItem() {
	    int selectedItem = -1;
		TableItem[] items = connectionTable.getTable().getItems();
		String connectionId = connectionProperties.getConnectionid();
		if (connectionId.length() == 0) {
		    connectionId = ConnectionsContentProvider.NEW_CONNECTION;
		}
		
		for (int i=0;i<items.length;i++) {
		    if(items[i].getText().equals(connectionId)) {;
		    	selectedItem = i;
		    	break;
		    }
		}
		if (selectedItem >= 0) {
			connectionTable.getTable().setSelection(selectedItem);
		}
	}
	
	private Text createTextControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		Text control = new Text(parent,SWT.LEFT | SWT.BORDER);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}

	private Combo createComboControl(Composite parent, String labelText, String[] items, int width) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		Combo control = new Combo(parent,SWT.LEFT | SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setItems(items);
		return control;
	}
	
	private Text createNumberControl(Composite parent, String labelText, int val, int width) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		Text control = new Text(parent,SWT.LEFT | SWT.BORDER);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(String.valueOf(val));
		return control;
	}

	
	private Button createCheckboxControl(Composite parent, String labelText, boolean checked) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		
		Button control = new Button(parent,SWT.CHECK);
		control.setSelection(checked);
		control.setLayoutData(data);
		return control;
	}
	
	
	private Text createPasswordControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT);
		label.setText(labelText);
		Text control = new Text(parent,SWT.LEFT | SWT.PASSWORD | SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}
	
	private void applyProperties() {
		try {
			connectionProperties.setHost(host.getText());
			connectionProperties.setPath(path.getText());
			connectionProperties.setUsername(username.getText());
			connectionProperties.setPassword(password.getText());
			connectionProperties.setConnectionid(connectionid.getText());
			connectionProperties.setPort(Integer.parseInt(port.getText()));
			connectionProperties.setType(connectionType.getText());
			connectionProperties.setTimeoutSeconds(timeoutSeconds.getText());
			connectionProperties.setIsPublicKeyAuth(isPublicKeyAuth.getSelection());
			connectionProperties.setKeyFile(keyFile.getText());
			connectionProperties.setHostsFile(hostsFile.getText());
//			connectionProperties.setPassive(passive.getSelection());
			connectionProperties.setUserDirIsRoot(userDirIsRoot.getSelection());
			connectionProperties.setStrictHostKeyCheck(strictHostKeyCheck.getSelection());
			}
			catch (Exception e) {
				e.printStackTrace();
			}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		applyProperties();
		connectionProperties.save();
		connectionTable.refresh();
		return true;
	}
		
	
	protected void validateInput() {
		

		String errorMessage = null;
		
		String test = connectionid.getText();
		copyButton.setEnabled(false);
		testButton.setEnabled(false);
		saveButton.setEnabled(false);
		if (!test.matches(".*[\\S]+.*")) {
		    errorMessage = "You must specify a connection name.";
			connectionid.setEnabled(true);
			connectionid.setEditable(true);
		}
		else if(connectionType.getText().equalsIgnoreCase("file") 
				|| connectionType.getText().equalsIgnoreCase("jar")
				|| connectionType.getText().equalsIgnoreCase("zip")
				|| connectionType.getText().equalsIgnoreCase("ram")
				|| connectionType.getText().equalsIgnoreCase("tar")
				|| connectionType.getText().equalsIgnoreCase("tgz")
				|| connectionType.getText().equalsIgnoreCase("tbz2")
				|| connectionType.getText().equalsIgnoreCase("mime")
				|| connectionType.getText().equalsIgnoreCase("tmp"))
		{
			// nothing to validate these yet
		}
		else if (!connectionType.getText().equalsIgnoreCase("file")){
			if(!host.getText().matches(".*[\\S]+.*")){
				  errorMessage = "You must specify a host name";
			}
			else if (!port.getText().matches("[0-9]+")) {
			    errorMessage = "You must specify a port number";
			}
		}
		if(errorMessage == null) {
			testButton.setEnabled(true);
			saveButton.setEnabled(true);
		}
		
		errorMessageLabel.setText(errorMessage == null ? "" : errorMessage); //$NON-NLS-1$
	    
		saveButton.setEnabled(errorMessage == null);
	
		errorMessageLabel.getParent().update();
		
	}


	@Override
	protected OverlayPreferenceStore createOverlayStore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleDefaults() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
