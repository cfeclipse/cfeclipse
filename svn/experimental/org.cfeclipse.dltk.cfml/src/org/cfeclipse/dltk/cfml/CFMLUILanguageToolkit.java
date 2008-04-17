package org.cfeclipse.dltk.cfml;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class CFMLUILanguageToolkit extends AbstractDLTKUILanguageToolkit {

	protected AbstractUIPlugin getUIPLugin() {
		return CFMLCorePlugin.getDefault();
	}

	public IDLTKLanguageToolkit getCoreToolkit() {
		return CFMLLanguageToolkit.getDefault();
	}

}
