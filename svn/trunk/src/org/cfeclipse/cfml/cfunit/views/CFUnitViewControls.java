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
				testcase.setTest( CFUnitTestCase.getResourceFullName( resource ) );
			}
		}
	}
}
