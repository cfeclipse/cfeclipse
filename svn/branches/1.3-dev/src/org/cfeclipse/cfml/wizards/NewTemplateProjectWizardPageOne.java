package org.cfeclipse.cfml.wizards;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class NewTemplateProjectWizardPageOne extends WizardPage{

	
	public NewTemplateProjectWizardPageOne(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	public NewTemplateProjectWizardPageOne(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	//@Override
	public boolean canFlipToNextPage() {
		// TODO Auto-generated method stub
		return super.canFlipToNextPage();
	}

	//@Override
	protected IWizardContainer getContainer() {
		// TODO Auto-generated method stub
		return super.getContainer();
	}

	//@Override
	protected IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return super.getDialogSettings();
	}

	//@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return super.getImage();
	}

	//@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	//@Override
	public IWizardPage getNextPage() {
		// TODO Auto-generated method stub
		return super.getNextPage();
	}

	//@Override
	public IWizardPage getPreviousPage() {
		// TODO Auto-generated method stub
		return super.getPreviousPage();
	}

	//@Override
	public Shell getShell() {
		// TODO Auto-generated method stub
		return super.getShell();
	}

	//@Override
	public IWizard getWizard() {
		// TODO Auto-generated method stub
		return super.getWizard();
	}

	//@Override
	protected boolean isCurrentPage() {
		// TODO Auto-generated method stub
		return super.isCurrentPage();
	}

	//@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return super.isPageComplete();
	}

	//@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		super.setDescription(description);
	}

	//@Override
	public void setErrorMessage(String newMessage) {
		// TODO Auto-generated method stub
		super.setErrorMessage(newMessage);
	}

	//@Override
	public void setImageDescriptor(ImageDescriptor image) {
		// TODO Auto-generated method stub
		super.setImageDescriptor(image);
	}

	//@Override
	public void setMessage(String newMessage, int newType) {
		// TODO Auto-generated method stub
		super.setMessage(newMessage, newType);
	}

	//@Override
	public void setPageComplete(boolean complete) {
		// TODO Auto-generated method stub
		super.setPageComplete(complete);
	}

	//@Override
	public void setPreviousPage(IWizardPage page) {
		// TODO Auto-generated method stub
		super.setPreviousPage(page);
	}

	//@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
	}

	//@Override
	public void setWizard(IWizard newWizard) {
		// TODO Auto-generated method stub
		super.setWizard(newWizard);
	}

	//@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}

}
