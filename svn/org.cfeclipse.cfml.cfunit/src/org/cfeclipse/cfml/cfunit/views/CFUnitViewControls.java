package org.cfeclipse.cfml.cfunit.views;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

import org.eclipse.jface.dialogs.MessageDialog;

public class CFUnitViewControls extends Canvas {
	
	public CFUnitViewControls(Composite parent) {
		super(parent, SWT.NONE );
		
		GridLayout layout= new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = false;
		setLayout(layout);
		
		Label x = new Label(this, SWT.NONE);
		GridData data = new GridData( GridData.FILL_HORIZONTAL );
		x.setLayoutData( data );
		
		
		Button browsebutton = new Button(this, SWT.PUSH);
		browsebutton.setText("  Browse...  ");
		browsebutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				browseFiles();
			}
		});
		
		Button exeButton = new Button(this, SWT.PUSH);
		exeButton.setText("  Run Test  ");
		exeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				executeTest();
			}
		});

	}
	
	/**
	 * Runs the current test case
	 */
	private void executeTest() {
		CFUnitTestCase testcase = CFUnitTestCase.getInstence();
		testcase.run();
	}
	
	/**
	 * Opens a dialog to assist the user find their unit test. If the user 
	 * selects a file it will update the global instance of TestCase.
	 */
	private void browseFiles() {
		CFUnitTestCase testcase = CFUnitTestCase.getInstence();
		
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
		
		listSelection.setTitle("Find CFUnit Test");
		
		if(listSelection.open() == ResourceListSelectionDialog.OK) {
			Object[] result = listSelection.getResult();
			
			if(result.length == 1) {
				IResource resource = (IResource)result[0];
				testcase.setTest( getResourceFullName( resource ) );
			}
		}
	}
	
	/**
	 * Get the full path based name of a resource based on its name and 
	 * location. For example, if a resource was 
	 * "/myProject/subfolder/SomeObject.cfc", this method would return 
	 * "myProject.subfolder.SomeObject"
	 * 
	 * @param resource The resource to get the name from
	 * @return The full path based name of the resource
	 */
	private String getResourceFullName(IResource resource) {
		if(!resource.getFileExtension().equals("cfc")) {
			MessageDialog.openError(getShell(), "Selected file not a CFC", "The selected file was not a ColdFusion Component (CFC). A unit test must be a CFC that extends a TestCase. Please refer to the CFUnit web site (http://cfunit.sourceforge.net/) for help documents and information on how to set up unit tests.");
			return "";
			
		} else {
			String path = resource.getName();
			
			org.eclipse.core.resources.IContainer pathPart = resource.getParent();
			while(pathPart != null) {
				if(!pathPart.getName().trim().equals("")) {
					path = pathPart.getName()+'.'+path;
				}
				pathPart = pathPart.getParent();
			}
			
			// Remove the file extention
			path = path.substring(0, path.indexOf( resource.getFileExtension() )-1);
			
			return path;
		}
	}
}
