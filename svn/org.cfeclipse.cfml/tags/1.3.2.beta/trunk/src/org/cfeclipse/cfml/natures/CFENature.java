/*
 * Created on 	: 10-Sep-2004
 * Created by 	: Administrator
 * File		  	: CFENature.java
 * Description	:
 * 
 * The MIT License
 * Copyright (c) 2004 Administrator
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
package org.cfeclipse.cfml.natures;

import java.util.Date;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.editors.contentassist.CFContentAssist;
import org.cfeclipse.cfml.editors.contentassist.CFEContentAssistManager;
import org.cfeclipse.cfml.editors.contentassist.CFMLFunctionAssist;
import org.cfeclipse.cfml.editors.contentassist.CFMLScopeAssist;
import org.cfeclipse.cfml.editors.contentassist.CFMLTagAssist;
import org.cfeclipse.cfml.editors.contentassist.HTMLTagAssistContributor;
import org.cfeclipse.cfml.editors.partitioner.scanners.cfscript.CFScriptCompletionProcessor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

// TODO: Use the logging facility instead of println's and showMessage().

/**
 * @author Administrator
 */
public class CFENature implements IContentAssistContributorNature {

	public static final String NATURE_ID = "org.cfeclipse.cfml.CFENature";

	private Date lastUpdate;

	// TODO: Is this method necessary?
	public Date getLastUpdateDate() {
		// Assert.isNotNull(this.lastUpdate,"CFENature::getLastUpdateDate()");
		if (this.lastUpdate == null)
			throw new IllegalArgumentException("CFENature::getLastUpdateDate()");

		return this.lastUpdate;
	}

	/** The project that this nature is associated with */
	private IProject project;

	/** The Content Assist Manager for this project */
	private CFEContentAssistManager camInstance;

	public CFENature() {
		// System.out.println("CFENature::CFENature() - Nature created");
		//setupCAM();
	}

	public CFEContentAssistManager getNatureCAM() {
		return this.camInstance;
	}

	/**
	 * Setup up the Content Assist Manager
	 * 
	 */
	private void setupCAM() {
		this.camInstance = new CFEContentAssistManager();

		CFMLTagAssist cfmlAssistor = new CFMLTagAssist(DictionaryManager
				.getDictionary(DictionaryManager.CFDIC));
		HTMLTagAssistContributor htmlAssistor = new HTMLTagAssistContributor(
				DictionaryManager.getDictionary(DictionaryManager.HTDIC));

		CFScriptCompletionProcessor cfscp = new CFScriptCompletionProcessor();
		cfscp.changeDictionary(DictionaryManager.JSDIC);

		this.camInstance.registerRootAssist(cfscp);
		this.camInstance.registerRootAssist(new CFContentAssist());
		this.camInstance.registerRootAssist(new CFMLScopeAssist());
		this.camInstance.registerRootAssist(new CFMLFunctionAssist());

		this.camInstance.registerTagAssist(cfmlAssistor);
		this.camInstance.registerAttributeAssist(cfmlAssistor);
		this.camInstance.registerValueAssist(cfmlAssistor);

		this.camInstance.registerTagAssist(htmlAssistor);
		this.camInstance.registerAttributeAssist(htmlAssistor);
		this.camInstance.registerValueAssist(htmlAssistor);

		this.camInstance.registerTagAssist(new CFMLScopeAssist());

		this.lastUpdate = new Date();
	}

	public void configure() throws CoreException {
		// Add nature-specific information
		// for the project, such as adding a builder
		// to a project's build spec.
		// System.out.println("CFENature::CFENature() - Nature configured");
	}

	public void deconfigure() throws CoreException {
		// Remove the nature-specific information here.
		// System.out.println("CFENature::CFENature() - Nature deconfigured");
	}

	public IProject getProject() {
		// System.out.println("CFENature::CFENature() - Project retrieved");
		return this.project;
	}

	public void setProject(IProject value) {
		// System.out.println("CFENature::CFENature() - Project set");
		this.project = value;
	}

	/**
	 * Toggles CFENature on a project
	 * 
	 * @param project
	 *            to have sample nature added or removed
	 */
	public static void toggleNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (NATURE_ID.equals(natures[i])) {
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i,
							natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				}
			}

			// Add the nature
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);

		} catch (CoreException e) {
			// This should use the Eclipse log facility
			showMessage("CFEclipse",
					"The following error occured when trying to apply the CFENature: \'"
							+ e.getMessage() + "\'");
			// System.err.println("CFENature::ru() Failed to apply CFENature to
			// project.");
			e.printStackTrace();
		}
	}

	private static void showMessage(String caption, String message) {
		MessageDialog.openInformation(new Shell(), caption, message);
	}
}
