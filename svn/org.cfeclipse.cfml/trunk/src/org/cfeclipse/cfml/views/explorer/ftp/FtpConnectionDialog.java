/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.cfeclipse.cfml.views.explorer.ftp;

import java.io.File;

import org.cfeclipse.cfml.net.FTPConnectionProperties;
import org.cfeclipse.cfml.preferences.BrowserPreferenceConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
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
public class FtpConnectionDialog extends Dialog  implements ISelectionChangedListener {


	public FTPConnectionProperties connectionProperties;
	private Text host,path,username,password,connectionid,port;
	private Button passive,sftp,userDirIsRoot;
	private int DELETE_ID = 3242;
	private Button deleteButton = null;
	private Button okButton = null;
	private TableViewer connectionTable = null;
	private Label errorMessageLabel = null;
	private String[] connectionTypes = {"file", "ftp", "sftp"};
	private Combo connectionType = null;
	private boolean isDirty = false;
	private Button openDirButton;
	
	/**
	 * @param parent
	 */
	public FtpConnectionDialog(Shell parent,String connectionId) {
		super(parent);
		// TODO Auto-generated constructor stub
		
		connectionProperties = new FTPConnectionProperties(connectionId);
		
	}
	
	protected Control createContents(Composite parent) {
	    Control contents = super.createContents(parent);
	    deleteButton = createButton((Composite)buttonBar, DELETE_ID, "Delete",false);
		okButton = getButton(IDialogConstants.OK_ID);
		okButton.setText("Create");
		redraw();
		
	    return contents;
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
	    connectionid.setText(connectionProperties.getConnectionid());
	    connectionType.setText(connectionProperties.getType());
	    host.setText(connectionProperties.getHost());
	    path.setText(connectionProperties.getPath());
	    port.setText(String.valueOf(connectionProperties.getPort()));
	    username.setText(connectionProperties.getUsername());
	    password.setText(connectionProperties.getPassword());
	    if (connectionProperties.getConnectionid().length() == 0) {
	        
			okButton.setText("Create");
			connectionid.setEditable(true);
			deleteButton.setEnabled(false);
			
	    }
	    else {
	        
			okButton.setText("Save");
			connectionid.setEditable(false);
			deleteButton.setEnabled(true);
	    }
	}
	
	
	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);

		
		Label summaryLabel = new Label(container, SWT.LEFT|SWT.WRAP|SWT.BOLD);
		FontData oldFontData[] = parent.getFont().getFontData();
		FontData fontData = new FontData();
		fontData.setStyle(oldFontData[0].getStyle()|SWT.BOLD);
		fontData.setHeight(oldFontData[0].getHeight());
		fontData.setName(oldFontData[0].getName());
		Font font = new Font(container.getDisplay(),fontData);
		summaryLabel.setFont(font);
		GridData summaryLabelData = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL);
		summaryLabelData.horizontalSpan = 2;
		summaryLabel.setLayoutData(summaryLabelData);
		//summaryLabel.setText("NOTE: Passive mode is currently forced because active mode causes the workspace to hang if the \nconnection hangs.");
		summaryLabel.pack();
		

		final GridData tableData = new GridData(GridData.FILL_BOTH);
		tableData.widthHint = 200;
	
		Composite tableArea = new Composite(container,SWT.NONE);
		tableArea.setLayoutData(tableData);
		GridLayout tableLayout = new GridLayout();
		tableLayout.numColumns = 1;
		tableArea.setLayout(tableLayout);

		final GridData gridData = new GridData(GridData.FILL_BOTH);
		Label connectionLabel = new Label(tableArea,SWT.RIGHT);
		connectionLabel.setText("Connections");
		
		connectionTable = new TableViewer(tableArea,SWT.SINGLE|SWT.BORDER);
		final Table table = connectionTable.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
		connectionTable.setContentProvider( new ConnectionsContentProvider());
		connectionTable.addSelectionChangedListener(this);
		
		connectionTable.setInput(new Object());
		
		

		
		
		Composite editArea = new Composite(container,SWT.NONE);
		editArea.setLayoutData(gridData);
		GridLayout editLayout = new GridLayout();
		editLayout.numColumns = 3;
		editArea.setLayout(editLayout);

		Label editLabel = new Label(editArea,SWT.RIGHT);
		editLabel.setText("Edit connection:");
		GridData labelData = new GridData();
		labelData.horizontalSpan = 3;
		editLabel.setLayoutData(labelData);

		// Connectionid
		connectionid = createTextControl(editArea,"Connection Name:",connectionProperties.getHost(),50);

		
		//Type of Connection
		connectionType = createComboControl(editArea, "Connection Type", connectionTypes, 50);
		connectionType.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(connectionType.getText());
				if(connectionType.getText().equalsIgnoreCase("file")){
					host.setEnabled(false);
					port.setEnabled(false);
					passive.setEnabled(false);
					passive.setSelection(false);
					userDirIsRoot.setEnabled(false);
					userDirIsRoot.setSelection(false);
					username.setEnabled(false);
					password.setEnabled(false);
					openDirButton.setEnabled(true);
				}
				else if(connectionType.getText().equalsIgnoreCase("ftp")){
					host.setEnabled(true);
					port.setEnabled(true);
					passive.setEnabled(true);
					passive.setSelection(connectionProperties.getPassive());
					userDirIsRoot.setEnabled(true);
					userDirIsRoot.setSelection(connectionProperties.getUserDirIsRoot());
					username.setEnabled(true);
					password.setEnabled(true);
					openDirButton.setEnabled(false);
					port.setText("21");
				}
				else{ //sftp
					host.setEnabled(true);
					port.setEnabled(true);
					openDirButton.setEnabled(false);
					port.setText("22");					
					passive.setEnabled(false);
					passive.setSelection(false);
					userDirIsRoot.setEnabled(false);
					userDirIsRoot.setSelection(false);
					username.setEnabled(true);
					password.setEnabled(true);
				}
				validateInput();
			}
			
			
		});
		// Sftp or not
		//sftp = createCheckboxControl(editArea,"SFTP:",connectionProperties.getSecure());
		
		// Host name
		host = createTextControl(editArea,"Host Name:",connectionProperties.getHost(),50);
		
		// Port
		port = createNumberControl(editArea,"Port:",connectionProperties.getPort(),5);
		
		// Path
		//path = createTextControl(editArea,"Path:",connectionProperties.getPath(),20);

		
		
		Label label = new Label(editArea,SWT.RIGHT );
		label.setText("Path:");
		path = new Text(editArea,SWT.LEFT | SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(20);
		path.setLayoutData(data);
		path.setText(connectionProperties.getPath());
		
		
		
		//Add a button to this control
		
		openDirButton = new Button(editArea,SWT.NONE);
		openDirButton.setText("Browse:");
		
		openDirButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog fileDialog = new DirectoryDialog(getShell(), SWT.OPEN);
				
				 if (path != null && path.getText().trim().length() > 0) {
						fileDialog.setFilterPath(path.getText());
					}
				String dir = fileDialog.open();
				
				 if (dir != null) {
			            dir = dir.trim();
			            if (dir.length() > 0) {
							path.setText(dir);
						}
			        }
			}
			
			
		});
		
	
		
		// Passive mode
		passive = createCheckboxControl(editArea,"Passive mode:",connectionProperties.getPassive());
		// userDirIsRoot
		userDirIsRoot = createCheckboxControl(editArea,"User Dir is Root Folder:",connectionProperties.getUserDirIsRoot());
		// Username
		username = createTextControl(editArea,"Username:",connectionProperties.getUsername(),20);
		
		// Password
		password = createPasswordControl(editArea,"Password:",connectionProperties.getPassword(),20);


		
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
		
		return container;
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
	
	

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			try {
			connectionProperties.setHost(host.getText());
			connectionProperties.setPath(path.getText());
			connectionProperties.setUsername(username.getText());
			connectionProperties.setPassword(password.getText());
			connectionProperties.setConnectionid(connectionid.getText());
			connectionProperties.setPort(Integer.parseInt(port.getText()));
			connectionProperties.setType(connectionType.getText());
			connectionProperties.setPassive(passive.getSelection());
			connectionProperties.setUserDirIsRoot(userDirIsRoot.getSelection());
			//connectionProperties.setSecure(sftp.getSelection());
			connectionProperties.save();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else if (buttonId == DELETE_ID) {
		    MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		    confirm.setMessage("Are you sure you want to delete this connection?");
		    if (confirm.open() == SWT.YES) {
				FTPConnectionProperties.deleteConnection(connectionProperties.getConnectionid());
				connectionTable.setInput(new Object());
		    }
		}
		super.buttonPressed(buttonId);
	}

	
	
	protected void validateInput() {
		

		String errorMessage = null;
		
		String test = connectionid.getText();
		if (!test.matches(".*[\\S]+.*")) {
		    errorMessage = "You must specify a connection name.";
		}
		else if (!connectionType.getText().equalsIgnoreCase("file")){
			if(!host.getText().matches(".*[\\S]+.*")){
				  errorMessage = "You must specify a host name";
			}
			else if (!port.getText().matches("[0-9]+")) {
			    errorMessage = "You must specify a port number";
			}
		}
				
				
				
		
		errorMessageLabel.setText(errorMessage == null ? "" : errorMessage); //$NON-NLS-1$
	    
		okButton.setEnabled(errorMessage == null);
	
		errorMessageLabel.getParent().update();
		
	}
	
	
	
}
