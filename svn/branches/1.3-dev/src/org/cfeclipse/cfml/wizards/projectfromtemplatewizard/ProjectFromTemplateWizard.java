package org.cfeclipse.cfml.wizards.projectfromtemplatewizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class ProjectFromTemplateWizard extends Wizard implements INewWizard {

	private IWorkbench thisWorkbench;
	private IStructuredSelection thisSelection;
	private WizardNewProjectCreationPage projectPage;
	private Page1 selectionPage;
	private Page2 replacementPage;
	public ProjectFromTemplateWizard(){
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		thisWorkbench = workbench;
		thisSelection = selection;
	}
	public void addPages(){
		
		projectPage = new WizardNewProjectCreationPage("projectPage");
		projectPage.setTitle("Project Location");
		addPage(projectPage);
		
		selectionPage = new Page1(thisSelection);
		selectionPage.setTitle("Select Application");
		addPage(selectionPage);
		
		replacementPage = new Page2(thisSelection);
		replacementPage.setTitle("Replace Strings");
		addPage(replacementPage);
		
	}

	public boolean performFinish() {
		return false;
	}
}
