package org.cfeclipse.cfml.snippets.wizards.snipex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class ExportApplicationWizard extends Wizard implements IExportWizard {

	
	private SelectAppPage appPage;
	private SelectFilesPage filePage;
	private EnterDescriptionPage descPage;
	private Log logger = LogFactory.getLog(ExportApplicationWizard.class);
	private boolean isFinished = false;
	private SnipExExportBean exportBean = new SnipExExportBean();
	
	public ExportApplicationWizard() {
		super();
		setNeedsProgressMonitor(true);
		
	}

	
	public void addPages() {
		appPage= new SelectAppPage("Select Application to Export", exportBean);
		filePage = new SelectFilesPage("Select Files to Export", exportBean);
		descPage = new EnterDescriptionPage("Enter Description", exportBean);

		addPage(appPage);
		addPage(filePage);
		addPage(descPage);
	
	}
	
	public boolean performFinish() {
		logger.debug("Performing Finish!" + exportBean.getFiles());
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
	
		logger.debug("The thing that we have chosen is : " + firstElement);
		if (firstElement instanceof IProject) {
			exportBean.setProject((IProject)firstElement);
			
		} 
		else if (firstElement instanceof IFolder) {
			IFolder folderItem = (IFolder) firstElement;
			exportBean.setProject(folderItem.getProject());
		}
		else if (firstElement instanceof IFile) {
			IFile fileItem = (IFile) firstElement;
			exportBean.setProject(fileItem.getProject());
			
		}

	}


	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	public boolean canFinish() {
		return this.isFinished;
	}

}
