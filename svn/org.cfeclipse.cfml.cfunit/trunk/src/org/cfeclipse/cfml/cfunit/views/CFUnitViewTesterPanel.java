package org.cfeclipse.cfml.cfunit.views;

import java.util.Observer;
import java.util.Observable;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CFUnitViewTesterPanel extends Canvas implements Observer {
	private final static String NO_FILE_TEXT = new String( "" );
	private final Color BASE_COLOR = new Color(this.getDisplay(), 204, 204, 204);
	private final Color ERROR_COLOR = new Color(this.getDisplay(), 243, 129, 129);
	private final Color FAILURE_COLOR = new Color(this.getDisplay(), 247, 192, 118);
	private final Color SUCCESS_COLOR = new Color(this.getDisplay(), 174, 210, 138);
	
	private Text testName;
	
	public CFUnitViewTesterPanel(Composite parent) {
		super(parent, SWT.NONE );
		GridLayout layout= new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = false;
		setLayout(layout);
				
		testName = new Text(this, SWT.BORDER | SWT.SINGLE);
		testName.setBackground( BASE_COLOR );
		testName.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		
		setFileName( NO_FILE_TEXT );
		
		Button exeButton = new Button(this, SWT.PUSH);
		exeButton.setImage( CFUnitView.getIcon( CFUnitView.ICON_RUN ) );
		exeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CFUnitTestCase.getInstence().run();
			}
		});
		
		// Hande changes to the test name
		testName.addModifyListener( new ModifyListener() {
			public void modifyText(ModifyEvent e)  {
				testNameChanged();
			}
		});
		
		// If the user hit "ENTER" the test will execute
		testName.addKeyListener(
			new org.eclipse.swt.events.KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == SWT.CR) {
						CFUnitTestCase.getInstence().run();
					}
				}
				public void keyReleased(KeyEvent e) {}
			}
		);
		
		// Begin observing the test Model
		CFUnitTestCase.getInstence().addObserver(this); 
	}
	
	public void update(Observable o, Object arg) {
		CFUnitTestCase tc = (CFUnitTestCase)o;
		setFileName( tc.getName() );
		
		if( tc.getErrorCount() > 0 ) {
			testName.setBackground( ERROR_COLOR );
		} else if ( tc.getFailureCount() > 0 ) {
			testName.setBackground( FAILURE_COLOR );
		} else if ( tc.getRunCount() > 0 ) {
			testName.setBackground( SUCCESS_COLOR );
		} else {
			testName.setBackground( BASE_COLOR );
		}
	}
	
	public void resetFile() {
		setFileName( NO_FILE_TEXT );
	}
	
	public void setFileName(String newName) {
		if(!testName.getText().equals(newName)) {
			testName.setText( newName );
			testName.redraw();
		}
	}
	
	public void testNameChanged() {
		CFUnitTestCase.getInstence().setTest( testName.getText() );
		testName.setBackground( BASE_COLOR );
	}
}
