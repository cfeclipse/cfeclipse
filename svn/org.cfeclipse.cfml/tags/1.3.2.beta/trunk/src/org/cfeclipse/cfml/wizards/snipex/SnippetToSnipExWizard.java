/**
 * 
 */
package org.cfeclipse.cfml.wizards.snipex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cfeclipse.cfml.net.ClientHttpRequest;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.cfeclipse.cfml.views.snips.SnipReader;
import org.cfeclipse.cfml.views.snips.SnippetObject;
import org.cfeclipse.snipex.Library;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author markdrew
 *
 */
public class SnippetToSnipExWizard extends Wizard implements IExportWizard{

	private Log logger = LogFactory.getLog(SnippetToSnipExWizard.class);
	private File snippetFile;
	//get the object as a snippet?
	
	
	//Create the pages
	
	private SnippetToSnipExDescriptionPage descriptionPage;
	private SnippetToSnipExCategoryPage categoryPage;
	private SnippetObject snippet;
	
	/**
	 * @param selectedfile
	 */
	public SnippetToSnipExWizard(File selectedfile) {
		this.snippetFile = selectedfile;
		
		SnipReader reader = new SnipReader();
			reader.read(snippetFile.getAbsolutePath());
			this.snippet = reader.getSnippet();
		
		logger.debug("The snippet is: " + snippet);
	}


	public void addPages(){
		descriptionPage = new SnippetToSnipExDescriptionPage("Select the SnipEx server", this.snippet);
		addPage(descriptionPage);
		categoryPage = new SnippetToSnipExCategoryPage("Select a category to export your snippet to");
		addPage(categoryPage);
		
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		logger.debug("Performing the finish");
		
		//Get the library we will submit to (its a category alright!)
		ITreeSelection selection = (ITreeSelection)categoryPage.getCategoryTree().getSelection();
		
		
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Library) {
			Library library = (Library) firstElement;
			
			//Get all the values
			String username = descriptionPage.getUserName();
			String useremail = descriptionPage.getUserEmail();
			String snippetName = descriptionPage.getSnippetName();
			String snippetDescription = descriptionPage.getSnippetDescription();
			String snippetStartBlock = snippet.getStartBlock();
			String snippetEndBlock = snippet.getEndBlock();
			String snipExServer = descriptionPage.getServerList().getText() + "?method=submitSnippet";
			String libraryID = categoryPage.getCategoryID();
			
				
				//create the post map
				Map postMap = new HashMap();
				postMap.put("username", username);
				postMap.put("useremail", useremail);
				postMap.put("snippetname", snippetName);
				postMap.put("snippetdescription", snippetDescription);
				postMap.put("snippetstartblock", snippetStartBlock);
				postMap.put("snippetendblock", snippetEndBlock);
				postMap.put("libraryid", libraryID);
				
				String message = "";
				//Actually submit this...
				try {
					ClientHttpRequest request = new ClientHttpRequest(snipExServer);
					InputStream stream = request.post(postMap);
					String stringFromInputStream = ResourceUtils.getStringFromInputStream(stream);
					message = stringFromInputStream;
					
				} catch (IOException e) {
					e.printStackTrace();
					message = e.getMessage();
				}
			
				//Create a generic Dialog
				
				MessageBox msg = new MessageBox(getShell());
				msg.setMessage(message.trim());
				msg.setText("Export Snippet to SnipEx server:");
				msg.open();
		}
		return true;
	}


	public SnippetToSnipExDescriptionPage getDescriptionPage() {
		return descriptionPage;
	}



}
