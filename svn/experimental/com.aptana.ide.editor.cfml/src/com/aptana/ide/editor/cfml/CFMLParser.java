package com.aptana.ide.editor.cfml;

import com.aptana.ide.editor.cfml.parsing.CFMLMimeType;
import com.aptana.ide.editors.unified.parsing.UnifiedParser;
import com.aptana.ide.parsing.ParserInitializationException;

public class CFMLParser extends UnifiedParser {

	public CFMLParser() throws ParserInitializationException {
		this(CFMLMimeType.MimeType);
	}
	
	public CFMLParser(String mimeType) throws ParserInitializationException
	{
		super(mimeType);
	}

	
}
