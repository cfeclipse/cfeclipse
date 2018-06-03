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

package org.cfeclipse.cfml.wizards.cfmlwizard;



import java.io.File;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;



/**

 * The "New" wizard page allows setting the container for

 * the new file as well as the file name. The page

 * will only accept file name without the extension OR

 * with the extension that matches the expected one (cfm).

 */



public class NewCFMLWizardPage extends WizardPage {

	private Text containerText;

	private Text fileText;

	private ISelection selection;

	private Combo fileType;

	private IEditorPart editor = null;

	private Log logger = LogFactory.getLog(NewCFMLWizardPage.class);
	
	/**

	 * Constructor for SampleNewWizardPage.

	 * @param pageName

	 */

	public NewCFMLWizardPage(ISelection selection) {

		super("wizardPage");

		setTitle("New CFML Template");

		setDescription("New CFML Template wizard.");

		this.selection = selection;

		

	}

	

	public NewCFMLWizardPage(IEditorPart editorPart) {

		super("wizardPage");

		setTitle("New CFML Template");

		setDescription("New CFML Template wizard.");

		this.editor = editorPart;

		

	}



	/**

	 * @see IDialogPage#createControl(Composite)

	 */

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();

		container.setLayout(layout);

		layout.numColumns = 3;

		layout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);

		label.setText("&Path:");



		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		containerText.setLayoutData(gd);

		containerText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();

			}

		});


		
		Button button = new Button(container, SWT.PUSH);

		button.setText("Browse...");

		button.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				handleBrowse();

			}

		});

		label = new Label(container, SWT.NULL);

		label.setText("&File name:");



		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		fileText.setLayoutData(gd);

		fileText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();

			}

		});

		
		/*
		
		//TODO: Add Template stuff here (from the new Template thing)
		Label templateLabel = new Label(container, SWT.NONE);
		templateLabel.setText("Select Template:");
		
		Combo templateList = new Combo(container, SWT.None | SWT.READ_ONLY);
		
		getTemplates();
		*/
		initialize();

		dialogChanged();

		setControl(container);

		setFilenameFocus();

		

		//System.out.println("the class that called me is: " + parent.getClass().getName());

		

		

	}
	
	private ArrayList getTemplates(){
		ArrayList templates = new ArrayList();
		CFMLPropertyManager propertyManager = new CFMLPropertyManager();
		
		Path snipBase = new Path(propertyManager.defaultSnippetsPath());
		File file = snipBase.toFile();
		logger.debug("Getting snippets from " + file.getAbsolutePath()); 
		
		
		//Now we iterate over the items I guess
		
		
		
		return null;
	}
	
	/**
	 * Recursive function
	 */
	private void getTempaltes2(){
		
	}

	/**

	 * 

	 * Mark D  I added this as I am trying to make it focus on the right item when you open the dialog

	 *

	 */

	private void setFilenameFocus(){

		//		TODO: this works if you do a right click but not if you come here from a select your page type page

		fileText.setFocus();

		int textLen = fileText.getText().length() - 4;

		fileText.setSelection(0,textLen);

		

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

		} else if (this.editor != null){

		    if (editor.getEditorInput() instanceof FileEditorInput) {

		        FileEditorInput input = (FileEditorInput)editor.getEditorInput();

		        containerText.setText(input.getFile().getParent().getFullPath().toString());

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

		
		// TODO: This might turn into an error. It allows you to create files that dont end in .cfm 
		
		int dotLoc = fileName.lastIndexOf('.');

		if (dotLoc != -1) {

			String ext = fileName.substring(dotLoc + 1);

			if (ext.equalsIgnoreCase("cfm") == false) {

				setMessage("File extension for ColdFusion files should be \"cfm\"", 2); //File extension does NOT have to be cfm

				return;

			}

		}

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

}