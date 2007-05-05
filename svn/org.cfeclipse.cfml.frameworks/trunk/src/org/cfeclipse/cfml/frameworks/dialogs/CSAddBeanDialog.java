package org.cfeclipse.cfml.frameworks.dialogs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

public class CSAddBeanDialog extends Dialog {
	
	private Text cfcName;
	private Text cfcExtends;
	private Text cfcPath;
	private Text cfcHint;
	private Text cfcDisplayName;
	private Text cfcOutput;
	
	public  Control  createDialogArea(Composite parent) {
		
		
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		container.setLayout(layout);
		layout.numColumns = 3;
		
		
		GridData gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		
		Label label = new Label(container, SWT.NULL);
		label.setText("&Component:");
		this.cfcExtends = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		this.cfcExtends.setLayoutData(gd);
		this.cfcExtends.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				//dialogChanged();
			}
		});
		Button extButton = new Button(container, SWT.PUSH);
		extButton.setText("Browse...");
		extButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleExtendBrowse();
			}
		});
		
		label = new Label(container, SWT.NULL);
		label.setText("&Bean Id:");
		this.cfcName = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 150;
		gd.horizontalSpan = 2;
		this.cfcName.setLayoutData(gd);
		
		
		
		
		return container;
	}

	public CSAddBeanDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	private void handleExtendBrowse() {
		ResourceListSelectionDialog listSelection = null;
		
		try {
			listSelection = new ResourceListSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), 
				IResource.FILE
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(listSelection.open() == ResourceListSelectionDialog.OK)
		{
			Object[] result = listSelection.getResult();
			
			if(result.length == 1)
			{
				IResource resource = (IResource)result[0];
				String s = resource.getProjectRelativePath().toString();
				s = s.replaceAll("/", ".").replaceAll("." + resource.getFileExtension(), "");
				
				//this.cfcBean.setExtendCFC(s);
				this.cfcExtends.setText(s);
			}
		}
	}

	protected void okPressed() {
		// TODO Auto-generated method stub
		super.okPressed();
	}

	public Text getCfcExtends() {
		return cfcExtends;
	}

	public void setCfcExtends(Text cfcExtends) {
		this.cfcExtends = cfcExtends;
	}

	public Text getCfcName() {
		return cfcName;
	}

	public void setCfcName(Text cfcName) {
		this.cfcName = cfcName;
	}


}
