package org.cfeclipse.cfml.cfunit.views;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

public class CFUnitViewControls extends Canvas {
	
	public CFUnitViewControls(Composite parent) {
		super(parent, SWT.NONE );
		
		FillLayout layout= new FillLayout();
		setLayout(layout);	
		
		Button button = new Button(this, SWT.PUSH);
		button.setText("  Execute Test  ");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				executeRun();
			}
		});
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */
	private void executeRun() {
		CFUnitTestCase plugin = CFUnitTestCase.getInstence();
		plugin.run();
	}
}
