package org.cfeclipse.cfml;

import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.TemplateContextType;

public class CFMContextType extends TemplateContextType{
	/** This context's id */
	public static final String CFM_CONTEXT_TYPE= "org.cfeclipse.cfml.templateeditor.cfm"; //$NON-NLS-1$

	/**
	 * Creates a new XML context type. 
	 */
	public CFMContextType() {
		addGlobalResolvers();
	}

	private void addGlobalResolvers() {
		addResolver(new GlobalTemplateVariables.Cursor());
		addResolver(new GlobalTemplateVariables.WordSelection());
		addResolver(new GlobalTemplateVariables.LineSelection());
		addResolver(new GlobalTemplateVariables.Dollar());
		addResolver(new GlobalTemplateVariables.Date());
		addResolver(new GlobalTemplateVariables.Year());
		addResolver(new GlobalTemplateVariables.Time());
		addResolver(new GlobalTemplateVariables.User());
	}
}
