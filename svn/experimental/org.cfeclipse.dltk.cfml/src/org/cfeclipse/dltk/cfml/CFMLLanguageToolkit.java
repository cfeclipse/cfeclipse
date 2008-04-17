package org.cfeclipse.dltk.cfml;

import org.cfeclipse.dltk.cfml.natures.CFMLNature;
import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;

public class CFMLLanguageToolkit extends AbstractLanguageToolkit {

		public static CFMLLanguageToolkit toolkit;
		
		
	public static IDLTKLanguageToolkit getDefault(){
		if(toolkit == null){
			toolkit = new CFMLLanguageToolkit();
		}
		return toolkit;
	}
	
	public String getLanguageName() {
		return "CFML";
	}

	public String getNatureId() {
		return CFMLNature.CFML_NATURE;
	}
	
	
	public String getLanguageContentType() {
		return "org.cfeclipse.dltk.cfml.content-type";
	}

	

	

}
