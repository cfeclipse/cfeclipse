package org.cfeclipse.dltk.cfml.parser;

import org.cfeclipse.dltk.cfml.natures.CFMLNature;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.ISourceElementRequestor.TypeInfo;

import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;

public class CFMLSourceElementParser extends AbstractSourceElementParser {

		public void parseSourceModule(char[] contents, ISourceModuleInfo astCache, char[] filename){
			ISourceElementRequestor requestor = getRequestor();
			
			requestor.enterModule();
			TypeInfo info = new TypeInfo();
			info.name ="CFML type";
			requestor.enterType(info);
			requestor.exitType(0);
			requestor.exitModule(0);
			
			
		}
	protected String getNatureId() {
		return CFMLNature.CFML_NATURE;
	}

}
