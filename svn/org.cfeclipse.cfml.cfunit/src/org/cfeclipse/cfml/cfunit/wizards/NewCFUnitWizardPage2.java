package org.cfeclipse.cfml.cfunit.wizards;

import java.util.Iterator;

import org.cfeclipse.cfml.parser.CFDocument;
import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.editors.ICFDocument;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.TagItem;
import org.cfeclipse.cfml.views.cfcmethods.CFCMethodViewItem;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.cfeclipse.cfml.util.CFPluginImages;

/**
 * The second CFUnit wizard page. This page is used to ask the user which 
 * method stubs they would liek created for the selected ColdFusion resource
 * 
 * @author Robert Blackburn
 */
public class NewCFUnitWizardPage2 extends WizardPage {
	
	private Table methodList;
	/**
	 * Constructor for NewCFUnitWizardPage2.
	 */
	public NewCFUnitWizardPage2(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}
	
	/**
	 * Creates the top level control for this dialog page under the given parent composite.
	 * @param parent The parent composite
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 1;
		
		CFCMethodViewItem[] methods = getCFDocumentMethods( getCFFile( getFile() ) );
		
		if( methods.length > 0) {
			createCaption( container );
			createMethodControls( container, methods );
			createSelectionControls( container );
			createExtraConrols( container );	
		} else {	
			createNoMethodsPanel( container );
		}
		
		setControl( container );
	}

	/**
	 * Creates the caption at the top of the panel
	 * @param parent The parent composite
	 */
	public void createCaption( Composite parent ) {
		Label label = new Label(parent, SWT.NULL);
		GridData gd = new GridData ( GridData.FILL_HORIZONTAL );
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
		label.setText("Which functions would you like to create test stubs for:");
	}
	
	/**
	 * Creates the "no functions found" panel
	 * @param parent The parent composite
	 */
	public void createNoMethodsPanel( Composite parent ) {
		Canvas panel = new Canvas(parent, SWT.NONE );
		panel.setLayout(new GridLayout(1, false));
		GridData gd = new GridData ( GridData.FILL_BOTH | GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_CENTER );
		panel.setLayoutData(gd);
		
		Label label = new Label(panel, SWT.NULL);
		label.setText("No functions found for this file.\n\nYou may either click \"Finish\" to create the test case or \"Back\" to change the selected\nfile under test.");
	}
	
	/**
	 * Creates the methods table with checkboxes
	 * @param parent The parent composite
	 */
	public void createMethodControls( Composite parent, CFCMethodViewItem[] methods ) {
		
		methodList = new Table(parent, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		
		GridData gd = new GridData ( GridData.FILL_BOTH );
		methodList.setLayoutData(gd);
		methodList.setData( methods );
		
		for (int i = 0; i < methods.length; i++) {
			final TableItem item1 = new TableItem(methodList, SWT.NONE);
			
			Image img;
			
			if(methods[i].getAccess().equals("private" ) ) {
				System.out.println( "PRIVATE:"+methods[i].getAccess().trim() );
				img = CFPluginImages.get( CFPluginImages.ICON_METHOD_PRIVATE );			
				
			} else if(methods[i].getAccess().equals( "package" ) ) {
				System.out.println( "PACKAGE:"+methods[i].getAccess().trim() );
				img = CFPluginImages.get( CFPluginImages.ICON_METHOD_PACKAGE );
				
			} else if(methods[i].getAccess().equals( "remote" ) ) {
				System.out.println( "REMOTE:"+methods[i].getAccess().trim() );
				img = CFPluginImages.get( CFPluginImages.ICON_METHOD_REMOTE );
				
			} else {
				System.out.println( "PUBLIC:"+methods[i].getAccess().trim() );
				img = CFPluginImages.get( CFPluginImages.ICON_METHOD_PUBLIC );
			}
			
		    item1.setImage( img );
		    item1.setText( methods[i].toString() );		
		}
		
	}
	
	/**
	 * Creates selection controls (Select All/Deselect All)
	 * @param parent The parent composite
	 */
	public void createSelectionControls( Composite parent ) {
		Canvas panel = new Canvas(parent, SWT.NONE);
		panel.setLayout(new GridLayout(1, false));
		GridData gd = new GridData ( GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_BEGINNING );
		panel.setLayoutData(gd);
		
		Button button1 = new Button(panel, SWT.PUSH );
		button1.setText("  Select All  ");
		button1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] controls = methodList.getItems();
				for (int i = 0; i < controls.length; i++) {
					controls[i].setChecked( true );
				}
			}
		});	
		
		Button button2 = new Button(panel, SWT.PUSH );
		button2.setText(" Deselect All ");
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] controls = methodList.getItems();
				for (int i = 0; i < controls.length; i++) {
					controls[i].setChecked( false );
				}
			}
		});
	}
	
	/**
	 * Creates the extra controls for filtering the methods list
	 * @param parent The parent composite
	 */
	public void createExtraConrols( Composite parent ) {
		// TODO: Add options panel for controls like "Hide private methods"	
	}
	
	
	/**
	 * Gets the selected file form the previouse page
	 * @return IFile The selected file
	 */
	private IFile getFile() {
		NewCFUnitWizardPage1 firstPage = (NewCFUnitWizardPage1)getWizard().getPage( NewCFUnitWizard.PREFERENCES_PAGE_NAME );
		IFile file = (IFile)firstPage.getFutResource();
		
		if(file.getType() != IResource.FILE) {
			throw new java.lang.Error("Selected Resource Not a IFile");
		}
		
		return file;
	}
	
	/**
	 * Gets the ColdFusion document from a file
	 * @param f The file to get a CFDocument from
	 * @return CFDocument The ColdFusion document
	 */
	private CFDocument getCFFile(IFile f) {
		ICFDocument idoc = new ICFDocument();
		
		try {
			String contents = "";
			String line;
			
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader( f.getContents() ));
			
			line = in.readLine();
			while(line != null) {
				contents += line;
				line = in.readLine();
			}
			
			idoc.setParserResource( f );
			idoc.set( contents );
			idoc.parseDocument();
			
		} catch(org.eclipse.core.runtime.CoreException e) {
			System.out.println("ERROR {org.cfeclipse.cfml.wizards.cfunit.getCFFile():CoreException}: "+e.toString());
		} catch(java.io.IOException e) {
			System.out.println("ERROR {org.cfeclipse.cfml.wizards.cfunit.getCFFile():IOException}: "+e.toString());
		}
		
		return idoc.getCFDocument();
	}

	public CFCMethodViewItem[] getCFDocumentMethods( CFDocument doc ) {
		
		try {

			if(doc == null) {	// OBT: Added to handle when the parse fatally fails.
				return null;
			}
			DocItem rootItem = doc.getDocumentRoot();

			CFNodeList nodes = rootItem.selectNodes("//cffunction");
			
			Iterator i = nodes.iterator();
			CFCMethodViewItem[] methods = new CFCMethodViewItem[nodes.size()];
			int index = 0;
			while(i.hasNext())
			{
				try {
					TagItem thisTag = (TagItem)i.next();
					
					CFCMethodViewItem item = new CFCMethodViewItem(thisTag);
					
					boolean addItem = true; // Can later use this to filter methods
					
					if (addItem) {
						methods[index] = item;
						index++;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		return methods;
		}
		catch (Exception e){
			System.err.println("CFCMethodsContentProvider has no elements");
			e.printStackTrace();
			return  null;
		}
	}
	
	/**
	 * Returns the selected methods
	 */
	public CFCMethodViewItem[] getSelectedMethods() {
		if(methodList == null) {
			return null;
		}
		
		try {
			TableItem[] controls = methodList.getItems();
			CFCMethodViewItem[] allMethods = (CFCMethodViewItem[])methodList.getData();
			
			// Count the number of selected methods
			int c = 0;
			for (int i = 0; i < controls.length; i++) {
				if( controls[i].getChecked() ) {
					c++;
				}
			}

			// Create a new array of just the selected methods
			int x = 0;
			CFCMethodViewItem[] selectedMethods = new CFCMethodViewItem[c];
			for (int i = 0; i < controls.length; i++) {
				if( controls[i].getChecked() ) {
					selectedMethods[x] = allMethods[i];
					x++;
				}
			}
			
			return selectedMethods;
			
		} catch(java.lang.Exception e) {
			System.err.println("NewCFUnitWizardPage2.getSelectedMethods():"+e);
			return null;
		}
	}
}
