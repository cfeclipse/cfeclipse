package com.rohanclan.cfml.editors.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Combo;

import com.rohanclan.cfml.editors.actions.LoadScribblePadAction.ScribbleSettings;

public class ScribbleDialog extends Dialog {

	private Text urlInput,pathInput;
	private Combo projectCombo;
	private IWorkspaceRoot root;
	public String url;
	public String path;
	public String project;
	private String message;
	private ScribbleSettings settings;
	
	public ScribbleDialog(Shell parent,String message,ScribbleSettings settings) {
		super(parent);
		this.message = message;
		this.settings = settings;
	}

	protected Control createDialogArea(Composite parent) {


		Composite container = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		
		Label summaryLabel = new Label(container, SWT.LEFT|SWT.WRAP|SWT.BOLD);
		FontData oldFontData[] = parent.getFont().getFontData();
		FontData fontData = new FontData();
		fontData.setStyle(oldFontData[0].getStyle()|SWT.BOLD);
		fontData.setHeight(oldFontData[0].getHeight());
		fontData.setName(oldFontData[0].getName());
		Font font = new Font(container.getDisplay(),fontData);
		summaryLabel.setFont(font);
		GridData summaryLabelData = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL);
		summaryLabelData.horizontalSpan = 2;
		summaryLabel.setLayoutData(summaryLabelData);
		summaryLabel.setText(message);
		summaryLabel.pack();
		
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		
		
		Composite editArea = new Composite(container,SWT.NONE);
		editArea.setLayoutData(gridData);
		GridLayout editLayout = new GridLayout();
		editLayout.numColumns = 2;
		editArea.setLayout(editLayout);

		Label editLabel = new Label(editArea,SWT.RIGHT);
		editLabel.setText("Scribble pad settings:");
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;
		editLabel.setLayoutData(labelData);
		
		

		root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		String[] names = new String[projects.length];
		for (int i=0;i<projects.length;i++) {
			names[i] = projects[i].getName();
		}
		
		projectCombo = createComboControl(editArea,"Project:",names,project,50);
		pathInput = createTextControl(editArea,"Path:",path,50);
		urlInput = createTextControl(editArea,"URL:",url,50);
		
		
		return container;
		
	}

	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		return super.createContents(parent);
	}	
	

	
	private Text createTextControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		Text control = new Text(parent,SWT.LEFT | SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}

	

	
	private Combo createComboControl(Composite parent, String labelText, String[] items,String value, int width) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		Combo control = new Combo(parent,SWT.LEFT | SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setItems(items);
		control.setText(value);
		return control;
	}

	protected void okPressed() {
		settings.path = pathInput.getText();
		settings.project = projectCombo.getText();
		settings.url = urlInput.getText();
		super.okPressed();
	}
	
}
