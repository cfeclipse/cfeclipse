package org.cfeclipse.dltk.cfml;

import java.io.IOException;
import java.io.Reader;

import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.dltk.core.ScriptContentDescriber;


public class CFMLContentDescriber extends ScriptContentDescriber {

	public CFMLContentDescriber() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describe(Reader contents, IContentDescription description)
			throws IOException {
		
		return ScriptContentDescriber.INDETERMINATE;
	}

}
