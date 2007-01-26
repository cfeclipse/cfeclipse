/*
 * Created on 10.04.2004
 *
 * The MIT License
 * Copyright (c) 2004 Chris Queener
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.wizards.templatefilewizard;

import java.io.File;
import java.io.FileFilter;

import org.cfeclipse.cfml.editors.actions.GenericEncloserAction;
import org.cfeclipse.cfml.views.snips.SnipTreeViewLabelProvider;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;


/**
 * The "New" wizard page allows setting the container for
 * the new file as well as the file name. The page
 * will only accept file name without the extension OR
 * with the extension that matches the expected one (cfm).
 */

public class NewTemplateFileWizardPage extends WizardPage {
	private Text containerText;
	private Text fileText;
	private ISelection selection;
	private Combo fileType;
	protected TreeViewer treeViewer;
	protected LabelProvider labelProvider;
	
	private FileFilter templateSnippetFileFilter;

    /** used as a proxy action to add snips to the editor */
	private static GenericEncloserAction tmpAction;


	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public NewTemplateFileWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("New CFML Template");
		setDescription("New CFML Template wizard.");
		this.selection = selection;
		this.templateSnippetFileFilter = new TemplateSnippetFileFilter();
		
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		layout.numColumns = 3;
		//layout.verticalSpacing = 9;
		
	    Label label = new Label(parent, SWT.NULL);
		label.setText("&Path:");

		containerText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		//gd.horizontalSpan = 2;
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(parent, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Label treeLabel = new Label(parent, SWT.NULL);
		treeLabel.setText("Select &template:");
		gd = new GridData();
		gd.horizontalSpan = 3;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		treeLabel.setLayoutData(gd);

		File snippetRootFolder = ((NewTemplateFileWizard)getWizard()).snipBase.toFile();
		
		//Create the tree viewer as a child of the composite parent
		treeViewer = new TreeViewer(parent);
		TemplateSnipTreeViewContentProvider contProv = new TemplateSnipTreeViewContentProvider(snippetRootFolder);
		treeViewer.setContentProvider(contProv);
		labelProvider = new SnipTreeViewLabelProvider();
		treeViewer.setLabelProvider(labelProvider);
		
		treeViewer.setUseHashlookup(true);
		
		//layout the tree viewer below the text field
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 3;
		gd.heightHint = 250;
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		treeViewer.getControl().setLayoutData(gd);
		treeViewer.setInput(snippetRootFolder);
		// treeViewer.expandAll();

		initialize();
		dialogChanged();
		setControl(parent);
		setFilenameFocus();
		hookListeners();
		
		//System.out.println("the class that called me is: " + parent.getClass().getName());
		
		
	}
	/**
	 * 
	 * Mark D  I added this as I am trying to make it focus on the right item when you open the dialog
	 *
	 */
	private void setFilenameFocus(){
		//		TODO: this works if you do a right click but not if you come here from a select your page type page
		// System.out.println("setting the focus");
		int textLen = fileText.getText().lastIndexOf(".");
		fileText.setSelection(0,textLen);
		fileText.setFocus();
	}
	/**
	 * Tests if the current workbench selection is a suitable
	 * container to use.
	 */
	
	private void initialize() {
		if (selection!=null && selection.isEmpty()==false && selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection)selection;
			if (ssel.size()>1) return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer)obj;
				else
					container = ((IResource)obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		
		//TODO: this works if you do a right click but not if you come here from a select your page type page
		fileText.setText("untitled.cfm");
		setFilenameFocus();
	}
	
	/**
	 * Uses the standard container selection dialog to
	 * choose the new value for the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog =
			new ContainerSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path)result[0]).toOSString());
			}
		}
	}
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		String container = getContainerName();
		String fileName = getFileName();

		if (container.length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		
		/* Handle this on doFinish instead
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(container));
		IContainer fileContainer = (IContainer) resource;
		IFile file = fileContainer.getFile(new Path(fileName));
		if (file.exists()) {
		    updateStatus("File already exists.");
		    return;
		} */
		
		updateStatus(null);
		
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		
	}

	public String getContainerName() {
		return containerText.getText();
	}
	public String getFileName() {
		return fileText.getText();
	}

    /*
    public void mouseEnter(MouseEvent e) {
    //System.out.println("Mouse entered viewer");
    }
    
    public void mouseHover(MouseEvent e) {
    //System.out.println("Mouse hovered over viewer");
    }
    
    public void mouseExit(MouseEvent e) {
    //System.out.println("Mouse exited viewer");
    }
    */
    
    
    protected void hookListeners() 
    {
    	//add a selection listener so we can look at the selected file and
    	//get the help information out
    	treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
    		public void selectionChanged(SelectionChangedEvent event) 
    		{
    			if(event.getSelection() instanceof IStructuredSelection) 
    			{
    				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
    				
    				//IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
    				File selectedfile = (File)selection.getFirstElement();
    				
    				//get the full path to the file
    				String f = selectedfile.getAbsolutePath();
    				
    				// System.out.println("Selection changed: " + f);
    				
    				try
    				{
    				    // Read snip contents and set them in the wizard
    				    ((NewTemplateFileWizard)getWizard()).snipReader.read(f);

    		    		// Update the extension in the filename field
    					String currentFilename = fileText.getText();
    					String fileExtension = ((NewTemplateFileWizard)getWizard()).snipReader.getTemplateExtension();
    					String newFilename = currentFilename.replaceFirst("\\..+$", "." + fileExtension);
    					fileText.setText(newFilename);
    					fileText.setSelection(0,newFilename.lastIndexOf("."));
    					fileText.setFocus();
    				}
    				catch(Exception e) {
    					e.printStackTrace(System.err);
    				}
    			} 
    		}
    	});
    	
    	// treeViewer.addDoubleClickListener(new SnipDoubleClickListener(this));
    	/*
    	try {
    		this.getViewSite().getShell().addMouseTrackListener(this);
    	}
    	catch (Exception e) {
    		e.printStackTrace(System.err);
    	}
    	*/
    }

}