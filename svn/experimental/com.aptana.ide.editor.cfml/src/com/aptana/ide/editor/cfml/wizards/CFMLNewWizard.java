package com.aptana.ide.editor.cfml.wizards;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.aptana.ide.editors.wizards.SimpleNewFileWizard;
import com.aptana.ide.editors.wizards.SimpleNewWizardPage;
/**
 * 
 * @author markd
 *	Wizard creates a new empty CFML file
 */
public class CFMLNewWizard extends SimpleNewFileWizard {

	
	/**
	 * Constructor for CFMLNewWizard
	 */
	public CFMLNewWizard() {
		super();
		setWindowTitle("New CFML File");
	}

	/**
	 * @see com.aptana.ide.editors.wizards.SimpleNewFileWizard#createNewFilePage(org.eclipse.jface.viewers.ISelection)
	 */
	protected SimpleNewWizardPage createNewFilePage(ISelection selection)
	{
		SimpleNewWizardPage page = new SimpleNewWizardPage(selection);
			
		page.setRequiredFileExtensions(new String[] { "cfm" });
		page.setTitle("CFML File");
		page.setDescription("Create a CFML File");
		page.setDefaultFileName("new_file.cfm");
			
		return page;
	}
	
	/**
	 * @see com.aptana.ide.editors.wizards.SimpleNewFileWizard#getInitialFileContents()
	 */
	protected String getInitialFileContents()
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
			
		pw.println("<!--- --->");
		pw.close();
			
		return sw.toString();
	}

}
