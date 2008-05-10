package org.cfeclipse.cfml.wizards;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.natures.CFENature;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class NewCFEclipseProject extends BasicNewProjectResourceWizard {

	private static final String PAGE_ICON = "icons/icons/64x64.gif";
	private static final String TITLE = "CFEclipse Project";
	private static final String DESCRIPTION = "Create a CFEclipse project resource.";
	
	@Override
	protected void initializeDefaultPageImageDescriptor() {
		ImageDescriptor desc = AbstractUIPlugin.
				imageDescriptorFromPlugin(CFMLPlugin.PLUGIN_ID, PAGE_ICON);
	    setDefaultPageImageDescriptor(desc);
	}
	
	@Override
	public void addPages() {
		super.addPages();
		IWizardPage startPage = getStartingPage();
		startPage.setTitle(TITLE);
		startPage.setDescription(DESCRIPTION);
	}
	
	@Override
	public boolean performFinish() {
		Boolean finished = super.performFinish();
		
		if (finished) {
			CFENature.toggleNature(getNewProject());
		}
		
		return finished;
	}
}
