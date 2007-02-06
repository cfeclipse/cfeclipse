package org.cfeclipse.cfml.cfunit.views;

import java.util.Observable;
import java.util.Observer;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.cfeclipse.cfml.cfunit.CFUnitTestResult;
import org.cfeclipse.cfml.cfunit.CFUnitTestSuite;

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
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.core.runtime.Path;

public class CFUnitViewTestList extends Canvas implements Observer {
	
	private TreeViewer tests;
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
		Composite treePanel = new Composite(this, SWT.BORDER);
		treePanel.setLayout( new FillLayout() );
		GridData gd = new GridData( GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL );
		gd.horizontalSpan = 2;
		treePanel.setLayoutData( gd );
		
		tests = new TreeViewer(treePanel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		DrillDownAdapter drillDownAdapter = new DrillDownAdapter( tests );
		tests.setContentProvider(new CFUnitViewTestListContent());
		tests.setLabelProvider(new CFUnitViewTestListLabels());
		tests.setInput( CFUnitTestSuite.getInstence() );

		tests.addSelectionChangedListener(
			new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					String n = event.getSelection().toString();
					handleSelection( n.substring(1, n.length()-1 ) );
				}
			}
		);
		
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
		
		CFUnitTestSuite.getInstence().addObserver(this); // Begin observing the test Model
	}
	
	public void update(Observable o, Object arg) {
		tests.refresh();
	}
	
	private void handleSelection( String name ) {
		
		CFUnitTestResult result = CFUnitTestSuite.getTestResult( name );
				
		if(result != null) {
			
			clearDetails();

			title.setText( name );
			
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
