package org.cfeclipse.cfml.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/*
 * The page to configure the code formatter options.
 */
public class TaskTagPreferencePage extends AbstractCFEditorPreferencePage {

	// Table column names/properties
	public static final String TASKTAG = "Task Tag";

	public static final String PRIORITY = "Priority";

	public static final String[] PROPS = { TASKTAG, PRIORITY };

	public List fTaskTags = null;
	public Table fTable;
	private static final int TASKNAMECOLUMN = 0;
	private static final int PRIORITYCOLUMN = 1;

	@SuppressWarnings("unchecked")
	protected OverlayPreferenceStore createOverlayStore() {
		List overlayKeys = new ArrayList();
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING,
				EditorPreferenceConstants.TASK_TAGS));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING,
				EditorPreferenceConstants.TASK_TAGS_PRIORTIES));

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return new OverlayPreferenceStore(getPreferenceStore(), keys);
	}

	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		unpackTaskTags();
		super.createControl(parent);
	}

	protected Control createContents(Composite parent) {

		initializeDialogUnits(parent);
		getOverlayStore().load();
		getOverlayStore().start();

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Group buttonGroup = createGroup(2,composite,"");
		// Add a button to create the new task tag
		Button newTaskTag = new Button(buttonGroup, SWT.PUSH);
		newTaskTag.setText("Create New Task Tag");

		// Add a button to remove the task tag
		Button removeTaskTag = new Button(buttonGroup, SWT.PUSH);
		removeTaskTag.setText("Remove Task Tag");

		Label label = new Label(composite, SWT.NONE);
		label.setText("Task Tags use this regular expression: Task Tag + [^A-Za-z]");
		label = new Label(composite, SWT.NONE);
		label.setText("To escape characters, use \"\\\"  --   Ex: \\[fun\\]");

		// Add the TableViewer
		final TableViewer tv = new TableViewer(composite, SWT.FULL_SELECTION);
		tv.setContentProvider(new TaskTagContentProvider());
		tv.setLabelProvider(new TaskTagLabelProvider());
		tv.setInput(fTaskTags);
		
		// Set up the table
		fTable = tv.getTable();
		fTable.setLayoutData(new GridData(GridData.FILL_BOTH));

		TableColumn col0 = new TableColumn(fTable, SWT.LEFT);
		TableColumn col1 = new TableColumn(fTable, SWT.LEFT);
		col0.setWidth(170);
		col1.setWidth(170);
		col0.setText(TASKTAG);
		col1.setText(PRIORITY);

		for (int i = 0, n = fTable.getColumnCount(); i < n; i++) {
			fTable.getColumn(i).pack();
		}

		fTable.setHeaderVisible(true);
		fTable.setLinesVisible(true);

		
		// Create the cell editors
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(fTable);
		editors[1] = new ComboBoxCellEditor(fTable, Priorities.INSTANCES, SWT.READ_ONLY);

		// Add a new task tag when the user clicks button
		newTaskTag.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TaskTag p = new TaskTag();
				p.name = "New";
				p.priority = Integer.valueOf("1");
				fTaskTags.add(p);
				tv.refresh();
			}
		});

		// remove a new task tag when the user clicks button
		removeTaskTag.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TableItem[] selection = fTable.getSelection();
				for(int x=0; x < selection.length; x++) {
					fTaskTags.remove(fTaskTags.indexOf(selection[x].getData()));
				}
				tv.refresh();
			}
		});
		
		// Set the editors, cell modifier, and column properties
		tv.setColumnProperties(PROPS);
		tv.setCellModifier(new TaskTagCellModifier(tv));
		tv.setCellEditors(editors);
		col0.setWidth(120);
		col1.setWidth(90);
		tv.refresh();

		return composite;
	}

	private int[] toIntArray(String[] stringArray) {
		int[] intArray = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			if (stringArray[i].length() == 0) {
				intArray[i] = 0;
			} else {
				intArray[i] = Integer.parseInt(stringArray[i]);
			}
		}
		return intArray;
	}

	/**
	 * loads the strings stored in prefs into tasktag objects
	 */
	private void unpackTaskTags() {
		getOverlayStore().load();
		String[] tags = getOverlayStore().getString(EditorPreferenceConstants.TASK_TAGS).split(","); //$NON-NLS-1$
		int[] prios = toIntArray(getOverlayStore().getString(EditorPreferenceConstants.TASK_TAGS_PRIORTIES).split(","));
		if (tags[0].length() == 0) {
			tags = new String[] { "TODO","FIXME","XXX" };
			prios = new int[] { 1,2,1 };
		}
		ArrayList elements = new ArrayList(tags.length);
		for (int i = 0; i < tags.length; i++) {
			TaskTag task = new TaskTag();
			task.name = tags[i].trim();
			task.priority = prios[i];
			elements.add(task);
		}
		fTaskTags = elements;
	}

	@Override
	public boolean performOk() {
		String tags = "";
		String priorities = "";
		// applies ui changes
		fTable.forceFocus();
		if(fTaskTags.size() != 0){			
			// convert the tasktags into flat strings to store in prefs
			for (int i = 0; i < fTaskTags.size(); i++) {
				tags = tags.concat(((TaskTag) fTaskTags.get(i)).name) + ",";
				priorities = priorities.concat(String.valueOf(((TaskTag) fTaskTags.get(i)).priority)) + ",";
			}
			tags = tags.substring(0, tags.length() - 1);
			priorities = priorities.substring(0, priorities.length() - 1);
		}
		getOverlayStore().setValue(EditorPreferenceConstants.TASK_TAGS, tags);
		getOverlayStore().setValue(EditorPreferenceConstants.TASK_TAGS_PRIORTIES, priorities);
		return super.performOk();
	}

	/**
	 * Convenience method to create a group
	 */
	private Group createGroup(int numColumns, Composite parent, String text ) {
		final Group group= new Group(parent, SWT.NONE);
		GridData gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan= numColumns;
		gd.widthHint= 0;
		group.setLayoutData(gd);
		group.setFont(parent.getFont());
		
		final GridLayout layout= new GridLayout(numColumns, false);
		group.setLayout(layout);
		group.setText(text);
		return group;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cfeclipse.cfml.preferences.AbstractAntEditorPreferencePage#handleDefaults
	 * ()
	 */
	protected void handleDefaults() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		super.dispose();
	}

}

/**
 * This class provides the content for the task tag table
 */

class TaskTagContentProvider implements IStructuredContentProvider {
	/**
	 * Returns the task tag objects
	 */
	public Object[] getElements(Object inputElement) {
		return ((List) inputElement).toArray();
	}

	/**
	 * Disposes any created resources
	 */
	public void dispose() {
		// Do nothing
	}

	/**
	 * Called when the input changes
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Ignore
	}
}

class TaskTagLabelProvider implements ITableLabelProvider {
	/**
	 * Returns the image
	 * 
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return Image
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * Returns the column text
	 * 
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return String
	 */
	public String getColumnText(Object element, int columnIndex) {
		TaskTag tasktag = (TaskTag) element;
		switch (columnIndex) {
		case 0:
			return tasktag.name;
		case 1:
			return Priorities.INSTANCES[tasktag.priority];
		}
		return null;
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(ILabelProviderListener listener) {
		// Ignore it
	}

	/**
	 * Disposes any created resources
	 */
	public void dispose() {
		// Nothing to dispose
	}

	/**
	 * Returns whether altering this property on this element will affect the
	 * label
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(ILabelProviderListener listener) {
		// Ignore
	}
}

/**
 * This class represents the cell modifier
 */

class TaskTagCellModifier implements ICellModifier {
	private Viewer viewer;

	public TaskTagCellModifier(Viewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * Returns whether the property can be modified
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return boolean
	 */
	public boolean canModify(Object element, String property) {
		// Allow editing of all values
		return true;
	}

	/**
	 * Returns the value for the property
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return Object
	 */
	public Object getValue(Object element, String property) {
		TaskTag p = (TaskTag) element;
		if (TaskTagPreferencePage.TASKTAG.equals(property))
			return p.name;
		else if (TaskTagPreferencePage.PRIORITY.equals(property))
			return p.priority;
		else
			return null;
	}

	/**
	 * Modifies the element
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @param value
	 *            the value
	 */
	public void modify(Object element, String property, Object value) {
		if (element instanceof Item)
			element = ((Item) element).getData();

		TaskTag p = (TaskTag) element;
		if (TaskTagPreferencePage.TASKTAG.equals(property))
			p.name = (String) value;
		else if (TaskTagPreferencePage.PRIORITY.equals(property))
			p.priority = (Integer) value;
		// Force the viewer to refresh
		viewer.refresh();
	}
}

class TaskTag {
	public String name;
	public int priority;
}

/**
 * This class encapsulates options
 */

class Priorities {
	public static final String LOW = "Low";
	public static final String NORMAL = "Normal";
	public static final String HIGH = "High";
	public static final String[] INSTANCES = { LOW, NORMAL, HIGH };
}
