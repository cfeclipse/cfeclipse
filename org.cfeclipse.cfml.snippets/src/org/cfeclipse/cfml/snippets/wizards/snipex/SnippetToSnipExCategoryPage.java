/**
 * 
 */
package org.cfeclipse.cfml.snippets.wizards.snipex;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import org.cfeclipse.cfml.snippets.views.snips.SnipTreeViewLabelProvider;
import org.cfeclipse.cfml.snippets.wizards.snipex.SnippetToSnipExWizard;
import org.cfeclipse.snippet.snipex.Library;
import org.cfeclipse.snippet.snipex.SnipEx;

/**
 * @author markdrew
 *
 */
public class SnippetToSnipExCategoryPage extends WizardPage {

	
	
	//The snipEx server to submit this to
	private TreeViewer categoryTree;
	
	private boolean isPageComplete = false;
	
	//logger for this class
	//private Log logger = LogFactory.getLog(SnippetToSnipExCategoryPage.class);
	
	public SnippetToSnipExCategoryPage(String pageName) {
		super(pageName);
		setTitle(pageName);
	}

	/**
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public SnippetToSnipExCategoryPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		
		
		Label lbl_TreeLabel = new Label(container, SWT.NONE);
		lbl_TreeLabel.setText("Select category to export snippet to");

		
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		Tree tree = new Tree(container, SWT.SINGLE);
		tree.setLayoutData(layoutData);
		categoryTree = new TreeViewer(tree);
		categoryTree.setContentProvider(new SnipExCategoriesContentProvider());
		categoryTree.setLabelProvider(new SnipTreeViewLabelProvider());
		categoryTree.setInput("root");
		categoryTree.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				isPageValid();
				
			}
			
		});
		
		
		setControl(container);
	}

	


	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			SnippetToSnipExWizard wizardmain = (SnippetToSnipExWizard)getWizard();
			String server = wizardmain.getDescriptionPage().getServerList().getText();
			categoryTree.setContentProvider(new SnipExCategoriesContentProvider(server, false));
			isPageValid();
			
		}
	}
	
	public boolean canFlipToNextPage() {
		return isPageComplete;
		
	}
	
	public boolean isPageComplete(){
		return isPageComplete;
	}
	
	private boolean isPageValid(){
		
		if(categoryTree.getSelection().isEmpty()){
			setErrorMessage("Select a category to export this snippet to");
			isPageComplete = false;
			getWizard().getContainer().updateButtons();
				
			return false;
		}
		
		getWizard().getContainer().updateButtons();
		isPageComplete = true;
		setErrorMessage(null);
		return true;
	}

	public TreeViewer getCategoryTree() {
		return categoryTree;
	}
	
	public String getCategoryID(){
		ITreeSelection selection = (ITreeSelection)categoryTree.getSelection();
		Object firstElement = selection.getFirstElement();
		
		if (firstElement instanceof Library) {
			Library lib = (Library) firstElement;
			return lib.getID();
			
		}
		else if (firstElement instanceof SnipEx) {
			SnipEx snipEx = (SnipEx) firstElement;
			return snipEx.getID();
			
		}
		return "0";
	}
	
}
