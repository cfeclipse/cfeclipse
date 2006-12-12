package org.cfeclipse.cfml.cfunit.views;

import java.util.Observable;
import java.util.Observer;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.cfeclipse.cfml.cfunit.CFUnitTestResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class CFUnitViewTestList extends Canvas implements Observer {
	
	private List tests;
	private Label details;
	private Label title;
	private Label labelIcon;
	
	private final Image blankIcon = CFUnitView.getIcon( CFUnitView.ICON_NONE );
	private final Image errorIcon = CFUnitView.getIcon( CFUnitView.ICON_ERROR );
	private final Image failureIcon = CFUnitView.getIcon( CFUnitView.ICON_FAILURE );
	private final Image nwErrorIcon = CFUnitView.getIcon( CFUnitView.ICON_NWERROR );
	
	public CFUnitViewTestList(Composite parent) {
		super(parent, SWT.BORDER );
		
		GridLayout gridLayout= new GridLayout();
		gridLayout.numColumns = 2;
		setLayout( gridLayout );
		
		GridData data;
		
		// Test List 
		tests = new List(this, SWT.BORDER | SWT.SINGLE);
		data = new GridData( GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL );
		data.horizontalSpan = 2;
		tests.setLayoutData( data );
		
		tests.addSelectionListener( new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleSelection();
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Title Bar
		labelIcon = new Label(this, SWT.NONE);
		labelIcon.setImage( blankIcon );
		title = new Label(this, SWT.NONE);
		title.setLayoutData ( new GridData( GridData.FILL_HORIZONTAL ) );
		
		// Test Details
		details = new Label(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		details.setText ("");
		data = new GridData( GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL );
		data.horizontalSpan = 2;
		details.setBackground( new Color(this.getDisplay(), 255, 255, 255) );
		details.setLayoutData (data);
		
		// 
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeIcons();
			}
		});
		
		CFUnitTestCase.getInstence().addObserver(this); // Begin observing the test Model
	}
	
	public void update(Observable o, Object arg) {
		int selection = -1;
		CFUnitTestCase tc = (CFUnitTestCase)o;
		
		clearDetails();
		CFUnitTestResult[] results = tc.getResults();
		String[] list = new String[ results.length ];
		
		if(results != null) {
			for(int i = 0; i < results.length; i++ ) {
				
				String state;
				
				switch( results[i].getType() ) {
					case CFUnitTestResult.SUCCESS:
						state = "Success";
						break;
					case CFUnitTestResult.ERROR:
						state = "Error";
						if(selection == -1) selection = i;
						break;
					case CFUnitTestResult.FAILURE:
						state = "Failure";
						if(selection == -1) selection = i;
						break;
					case CFUnitTestResult.NWERROR:
						state = "Network Error";
						if(selection == -1) selection = i;
						break;
					default:
						state = "Unknown";
						if(selection == -1) selection = i;
				}
				
				list[i] = results[i].getName() + " - " + state;
			}
		}
		
		tests.setItems( list );
		tests.select( selection );
		handleSelection();
	}
	
	private void handleSelection() {
		CFUnitTestResult[] results = CFUnitTestCase.getInstence().getResults();
		
		int index = tests.getSelectionIndex();
		
		if(index != -1) {
			CFUnitTestResult result = results[ index ];
			
			String[] selection = tests.getSelection();
			title.setText( selection[0] );
			
			switch( result.getType() ) {
				case CFUnitTestResult.ERROR:
					labelIcon.setImage( errorIcon );
					break;
				case CFUnitTestResult.FAILURE:
					labelIcon.setImage( failureIcon );
					break;
				case CFUnitTestResult.NWERROR:
					labelIcon.setImage( nwErrorIcon );
					break;
				default:
					labelIcon.setImage( blankIcon );
			}
			
			String[] d = result.getDetails();
			String output = "";
					
			for(int i = 0; i < d.length; i++ ) {
				output = output+ d[i] + '\n';
			}
			
			details.setText( output );
		} else {
			clearDetails();
		}
	}

	public void clearDetails() {
		labelIcon.setImage( blankIcon );
		title.setText( "" );
		details.setText( "" );
	}
	
	private void disposeIcons() {
		errorIcon.dispose();
		failureIcon.dispose();
	}
}
