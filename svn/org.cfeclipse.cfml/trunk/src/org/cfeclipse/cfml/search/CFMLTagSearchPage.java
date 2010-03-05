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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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
			            if (file.getLocation().getFileExtension() != null && file.getLocation().getFileExtension().matches("xml|html|js|css")) {
			            	files.add(file);
			            }
			         }
			         return true;
				}
			}, IResource.DEPTH_INFINITE);
			NewSearchUI.runQueryInBackground(TextSearchQueryProvider.getPreferred().createQuery(idText.getText(), (IResource[]) files.toArray(new IResource[0])));
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
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;
		parent.setLayout(layout);
		new Label(parent, 0).setText("Containing text:");
		idText = new Text(parent, SWT.BORDER);
		idText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (selected != null) {
			idText.setText(selected);
			idText.setSelection(0, selected.length());
		}
		setControl(parent);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		idText.setFocus();
	}

}