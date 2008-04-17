package com.aptana.ide.editor.cfml.wizards;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.aptana.ide.editors.wizards.UntitledTextFileWizard;

public class CFMLUnitledTextFileWizard extends UntitledTextFileWizard {

	/**
	 * @see UntitledTextFileWizard#getFriendlyName()
	 */
	protected String getFriendlyName()
	{
		return "CFML";
	}
	
	
	protected String getEditorId(File file)
	{
		return super.getEditorId(file);
	}
	
	/**
	 * @see com.aptana.ide.editors.wizards.UntitledTextFileWizard#getFileExtension()
	 */
	protected String getFileExtension()
	{
		return ".cfm";
	}
	
	
	protected String getInitialFileContents()
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
			
		pw.println("<!--- --->");
		pw.close();
			
		return sw.toString();
	}


}
