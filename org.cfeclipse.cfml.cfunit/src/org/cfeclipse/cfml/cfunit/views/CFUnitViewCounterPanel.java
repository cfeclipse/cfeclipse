package org.cfeclipse.cfml.cfunit.views;

import java.util.Observable;
import java.util.Observer;

import org.cfeclipse.cfml.cfunit.CFUnitTestSuite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

public class CFUnitViewCounterPanel extends Canvas implements Observer {
	protected Text fNumberOfErrors;
	protected Text fNumberOfFailures;
	protected Text fNumberOfRuns;
	
	private final Image errorIcon = CFUnitView.getIcon( CFUnitView.ICON_ERROR );
	private final Image failureIcon= CFUnitView.getIcon( CFUnitView.ICON_FAILURE );
	
	public CFUnitViewCounterPanel(Composite parent) {
		super(parent, SWT.NONE);
		setLayout( new FillLayout( SWT.HORIZONTAL ) );
 		
		createRunCountPanel( this );
		createFailureCountPanel( this );
		createErrorCountPanel( this );
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeIcons();
			}
		});
		
		CFUnitTestSuite.getInstence().addObserver(this); // Begin observing the test Model
	}
	
	private void createRunCountPanel(Composite parent) {
		Canvas c = new Canvas(parent, SWT.NONE );
		
		GridLayout layout= new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		c.setLayout(layout);
		
		Label label = new Label(c, SWT.NONE);
		label.setText("Runs:");
		
		fNumberOfRuns = new Text(c, SWT.READ_ONLY);
		fNumberOfRuns.setText("0/0");
		fNumberOfRuns.setLayoutData(new GridData(GridData.BEGINNING));
	}
	
	private void createErrorCountPanel(Composite parent) {
		Canvas c = new Canvas(parent, SWT.NONE );
		
		GridLayout layout= new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		c.setLayout(layout);
		
		Label label = new Label(c, SWT.NONE);
		label.setImage(errorIcon);
		label.setLayoutData(new GridData(GridData.END));
		
		label = new Label(c, SWT.NONE);
		label.setText("Errors:");
		
		fNumberOfErrors = new Text(c, SWT.READ_ONLY);
		fNumberOfErrors.setText("0");
		fNumberOfErrors.setLayoutData(new GridData(GridData.BEGINNING));
	}
	
	private void createFailureCountPanel(Composite parent) {
		Canvas c = new Canvas(parent, SWT.NONE );
		
		GridLayout layout= new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		c.setLayout(layout);
		
		Label label= new Label(c, SWT.NONE);
		label.setImage(failureIcon);
		
		label = new Label(c, SWT.NONE);
		label.setText("Failures:");
		
		fNumberOfFailures = new Text(c, SWT.READ_ONLY);
		fNumberOfFailures.setText("0");
	}
	
	public void update(Observable o, Object arg) {
		setRunValue( CFUnitTestSuite.getRunCount() );
		setErrorValue( CFUnitTestSuite.getErrorCount() );
		setFailureValue( CFUnitTestSuite.getFailureCount() );
	}
	
	private void disposeIcons() {
		errorIcon.dispose();
		failureIcon.dispose();
	}
	
	public void setRunValue(int value) {
		if(value > 0) {
			fNumberOfRuns.setText( String.valueOf( value )+'/'+String.valueOf( CFUnitTestSuite.getTestCount() ) );
		} else {
			fNumberOfRuns.setText("---/---");
		}
	}
	 
	public void setErrorValue(int value) {
	     fNumberOfErrors.setText( String.valueOf( value ) );
	}
	 
	public void setFailureValue(int value) {
	     fNumberOfFailures.setText( String.valueOf( value ) );
	}
}
