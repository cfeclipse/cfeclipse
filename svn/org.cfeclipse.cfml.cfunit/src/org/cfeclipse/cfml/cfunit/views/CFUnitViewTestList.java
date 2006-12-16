package org.cfeclipse.cfml.cfunit.views;

import java.util.Observable;
import java.util.Observer;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.cfeclipse.cfml.cfunit.CFUnitTestResult;
import org.cfeclipse.cfml.views.explorer.LocalFileSystem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.text.IDocument;

import org.eclipse.core.runtime.Path;

public class CFUnitViewTestList extends Canvas implements Observer {
	
	private List tests;
	private Label title;
	private Label labelIcon;
	private Composite innerScrollpane;
	
	private Label lastDetailsLabel;
	private final Color COLOR_HIGHLIGHT = new Color(this.getDisplay(), 236, 236, 236);
	private final Color COLOR_WHITE = new Color(this.getDisplay(), 255, 255, 255);
	
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
		Composite d = createDetailsPane( this );
		
	    data = new GridData( GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL );
		data.horizontalSpan = 2;
		d.setBackground( COLOR_WHITE );
		d.setLayoutData (data);
		
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
			clearDetails();
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
			
			for(int i = 0; i < d.length; i++ ) {
				Label l = new Label(innerScrollpane, SWT.NONE);
				l.setBackground( COLOR_WHITE );
				l.setText( d[i] );
				l.addMouseListener(new DetailsSelectionListener());
				
			}
			
			innerScrollpane.setSize(innerScrollpane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			innerScrollpane.layout();
			
		} else {
			clearDetails();
		}
	}

	public void clearDetails() {
		labelIcon.setImage( blankIcon );
		title.setText( "" );
		
		try {
			org.eclipse.swt.widgets.Control[] c = innerScrollpane.getChildren();
			
			for(int i = 0; i < c.length; i++ ) {
				c[i].dispose();
			}
		} catch(Throwable e) {
			System.err.println(e);
		}
		
	}
	
	private void disposeIcons() {
		errorIcon.dispose();
		failureIcon.dispose();
	}
	
	private Composite createDetailsPane(Composite parent) {
		final ScrolledComposite scrollpane = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		innerScrollpane = new Composite(scrollpane, SWT.NONE);
	    innerScrollpane.setLayout( new FillLayout( SWT.VERTICAL ) );
		innerScrollpane.setBackground( new Color(getDisplay(), 255, 255, 255) );
		scrollpane.setContent( innerScrollpane );
		
		Label details = new Label(innerScrollpane, SWT.NONE);
		details.setText("");
		details.setBackground( COLOR_WHITE );
		
		innerScrollpane.setSize(innerScrollpane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		return scrollpane;
	}
	
	
	public class DetailsSelectionListener implements MouseListener {
		public DetailsSelectionListener() {}
		
		public void mouseDown(MouseEvent e) {
			Label l = (Label)e.widget;
			String line = l.getText().trim();
			
			// Unhighlight last row			
			if(lastDetailsLabel != null) {
				lastDetailsLabel.setBackground( COLOR_WHITE );
			}
			
			// Get the file name/line number
			int li = line.lastIndexOf(':');
			String fileName = line.substring(0, li);
			Path path = new Path( fileName );
			
			if( path.isValidPath(fileName) ) {
				int lineNumber;
				
				try {
					lineNumber = Integer.parseInt( line.substring(li+1, line.length()) );
				} catch(NumberFormatException nfe) {
					return;
				}
				
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					// Open the file
					LocalFileSystem fs = new LocalFileSystem();
					org.eclipse.ui.IEditorPart editor = page.openEditor(fs.getEditorInput( fileName ),"org.cfeclipse.cfml.editors.CFMLEditor");
		            
					// Select the line number
					ITextEditor textEditor = (ITextEditor)editor;
					lineNumber--; // document is 0 based
					IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
					textEditor.selectAndReveal( document.getLineOffset(lineNumber), document.getLineLength(lineNumber));
					page.activate(textEditor);
					
				} catch (Exception ex) {
		            ex.printStackTrace();
		        }
				
				// Highlight this row
				l.setBackground( COLOR_HIGHLIGHT );
				lastDetailsLabel = l;
			}
		}
		
		public void mouseDoubleClick(MouseEvent e) {}
		public void mouseUp(MouseEvent e) {}
		
	}
}
