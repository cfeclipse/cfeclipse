package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

public class CFMLTemplatePreferencePage extends TemplatePreferencePage
		implements IWorkbenchPreferencePage {

	public CFMLTemplatePreferencePage() {
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setTemplateStore(CFMLPlugin.getDefault().getTemplateStore());
		//setContextTypeRegistry(CFMLPlugin.getDefault().getContextTypeRegistry());
	}

}
