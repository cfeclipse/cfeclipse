package org.cfeclipse.cfml.properties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.cfeclipse.cfml.util.CFMappings;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;

public class MappingsPropertyPage extends PropertyPage {

	private static final String PATH_MAPPINGS = "PATH_MAPPINGS";

	private static final int TEXT_FIELD_WIDTH = 20;
	private Text ownerText;
	
	private Text mappingName;
	private Combo projectPaths;
	private Text mappingPath;
	private Table mappingTable;
	
	private CFMappings pathData = new CFMappings();
	
	

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public MappingsPropertyPage() {
		super();
		
	}

	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);
		
		GridLayout maingd = new GridLayout();
		maingd.numColumns = 4;
		composite.setLayout(maingd);
		
		//Label for path field
		Label mapLabel = new Label(composite, SWT.NONE);
		mapLabel.setText("Mapping:");

		// Path text field
		//((IResource) getElement()).getFullPath().toString()
//		 Owner text field
		mappingName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		mappingName.setLayoutData(gd);
		
		
		mappingPath = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		GridData gd2 = new GridData();
		gd2.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		mappingPath.setLayoutData(gd2);
		
		
		Button selectPath = new Button(composite, SWT.NONE);
		selectPath.setText("Select:");
		selectPath.addMouseListener(new MouseListener() {
		
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}
		
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				ContainerSelectionDialog select = new ContainerSelectionDialog(getShell(), (IContainer)getElement(), false, "Select a path for this mapping");
				select.open();
				Object[] result = select.getResult();
				if(result.length >0 || result !=null){
					GridData layoutData = (GridData)mappingPath.getLayoutData();
					layoutData.widthHint = convertWidthInCharsToPixels(result[0].toString().length());
					mappingPath.setLayoutData(layoutData);
					mappingPath.setText(result[0].toString());
				}
				System.out.println(result[0]);
			}
		
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}
		
		});
		
		
		GridData btnLayout = new GridData();
		btnLayout.widthHint = 200;
		btnLayout.horizontalSpan = 5;
		btnLayout.horizontalAlignment = SWT.RIGHT;
		//Add button
		Button addPath = new Button(composite, SWT.NONE);
		addPath.setText("Add Mapping");
		addPath.setLayoutData(btnLayout);
		addPath.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent e) {
				addMapping();
				
			}

		

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
	}


	protected void addMapping() {
		if(mappingName.getText().length()>0 && mappingPath.getText().length() >0){
		pathData.put(mappingName.getText(), mappingPath.getText());
		fillTable();
		mappingName.setText("");
		mappingPath.setText("");
		}
		

		
	}
	
	private void removeMapping() {
		System.out.println(pathData);
		TableItem[] selection = mappingTable.getSelection();
		for (int i = 0; i < selection.length; i++) {
			TableItem item = selection[i];
			pathData.remove(item.getText(0));
		}
		fillTable();
		//table.remove (table.getSelectionIndices ());
	}
	
	
	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addSecondSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		
		composite.setLayout(gl);
		
		GridData gd = new GridData();
		gd.widthHint = 400;
		gd.heightHint = 200;
		gd.grabExcessHorizontalSpace = true;
		
		
		mappingTable = new Table(composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		
		mappingTable.setLinesVisible (true);
		mappingTable.setHeaderVisible (true);
		
		mappingTable.setLayoutData(gd);
		
		
		TableColumn colMapping = new TableColumn(mappingTable, SWT.LEFT);
		colMapping.setText("Mapping");
		colMapping.setWidth(150);
		TableColumn colPath = new TableColumn(mappingTable, SWT.LEFT);
		colPath.setText("Path");
		colPath.setWidth(150);
		
		
		fillTable();
		
		//mappingTable.add
		GridData btnLayout = new GridData();
		btnLayout.widthHint = 200;
		btnLayout.horizontalAlignment = SWT.RIGHT;
		
		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setText("Delete Mapping");
		btnDelete.setLayoutData(btnLayout);
		btnDelete.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent e) {
				removeMapping();
				
			}

			

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}
	
	
	
	
	private void fillTable() {
		//clear the table first
		mappingTable.removeAll();
		Set set = pathData.entrySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			Entry mappingKey = (Entry) iter.next();
			TableItem item= new TableItem(mappingTable, SWT.NONE);
			
			item.setText(0, mappingKey.getKey().toString());
			item.setText(1, mappingKey.getValue().toString());
			
			
		}
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);
		
		
		loadMappings();

		addFirstSection(composite);
		addSeparator(composite);
		addSecondSection(composite);
		return composite;
	}

	private void loadMappings() {
		String storedPaths = ""; 
		try {
			 storedPaths = ((IResource) getElement()).getPersistentProperty(
					new QualifiedName("", PATH_MAPPINGS));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pathData = new CFMappings(storedPaths);
		
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		//the default is {/=/<project name>}
		CFMappings map  = new CFMappings("{/=/" + ((IResource) getElement()).getName() + "}");
		pathData = map;
		fillTable();
	
	}
	
	public boolean performOk() {
		// store the value in the owner text field
		try {
			
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", PATH_MAPPINGS),
				pathData.toString());
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}