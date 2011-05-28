package org.cfeclipse.cfml.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.TextSearchQueryProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;

public class CFMLTagSearchPage extends DialogPage implements ISearchPage {

	private String selected;
	private Text idText;

	public CFMLTagSearchPage() {
		super();
	}

	public CFMLTagSearchPage(String title) {
		super(title);
	}

	public boolean performAction() {
		if (idText.getText().length() == 0)
			return false;
		try {
			final List files = new ArrayList();
			ResourcesPlugin.getWorkspace().getRoot().accept(new IResourceProxyVisitor() {
				public boolean visit(IResourceProxy proxy) throws CoreException {
					if (proxy.getType() == IResource.FILE) {
						IFile file = (IFile) proxy.requestResource();
						if (file.getLocation().getFileExtension() != null
								&& file.getLocation().getFileExtension().matches("xml|html|js|css")) {
							files.add(file);
						}
					}
					return true;
				}
			}, IResource.DEPTH_INFINITE);
			NewSearchUI.runQueryInBackground(TextSearchQueryProvider.getPreferred().createQuery(idText.getText(),
					(IResource[]) files.toArray(new IResource[0])));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	public void setContainer(ISearchPageContainer container) {
		if (container.getSelection() instanceof TextSelection) {
			selected = ((TextSelection) container.getSelection()).getText();
		}
	}

	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());

		PShelf shelf = new PShelf(parent, SWT.NONE);
		shelf.setRenderer(new RedmondShelfRenderer());

		PShelfItem item1 = new PShelfItem(shelf, SWT.NONE);
		item1.setText("Tag Search");

		item1.getBody().setLayout(new FillLayout());

		final Table table = new Table(item1.getBody(), SWT.NONE);
		TableColumn col1 = new TableColumn(table, SWT.NONE);
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col1.setWidth(125);
		col2.setWidth(120);
		col1.setText("Criteria");
		String[] criterias = { "Tag Name", "Tag Attributes" };
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "Tag Name", "Enter tag name to search" });
		item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "Tag body contains", "Enter values to search tag body for" });
		item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "Attributes", "Enter attributes to search" });
		item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "Attribute values contain", "Enter attributes values to search for" });
		col1.pack();
		col2.pack();
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column
		final int EDITABLECOLUMN = 1;
		table.setHeaderVisible(true);
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TableItem item = (TableItem) e.item;
				if (item == null)
					return;

				// The control that will be the editor must be a child of the
				// Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent me) {
						Text text = (Text) editor.getEditor();
						editor.getItem().setText(EDITABLECOLUMN, text.getText());
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);
			}
		});

		PShelfItem item2 = new PShelfItem(shelf, SWT.NONE);
		item2.setText("CFScript Search");

		item2.getBody().setLayout(new FillLayout());

		Text text = new Text(item2.getBody(), SWT.WRAP);
		text.setText("Not implemented");

		setControl(parent);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// idText.setFocus();
	}

}