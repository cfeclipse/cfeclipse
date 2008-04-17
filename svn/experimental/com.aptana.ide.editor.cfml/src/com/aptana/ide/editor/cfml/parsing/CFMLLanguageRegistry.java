package com.aptana.ide.editor.cfml.parsing;

import java.util.HashMap;

import com.aptana.ide.editor.html.parsing.HTMLLanguageRegistry;

public class CFMLLanguageRegistry extends HTMLLanguageRegistry{
	private static String TAG_PARSER = "parser"; //$NON-NLS-1$
	private static String TAG_ELEMENT_LANGUAGE = "element-language"; //$NON-NLS-1$
	private static String TAG_PI_LANGUAGE = "pi-language"; //$NON-NLS-1$
	private static String TAG_ATTRIBUTE_LANGUAGE = "attribute-language"; //$NON-NLS-1$
	private static String TAG_VALUE = "value"; //$NON-NLS-1$

	// parser attribute
	private static String ATTR_LANGUAGE = "language"; //$NON-NLS-1$
	//private static String ATTR_PARSER = "parser"; //$NON-NLS-1$

	// element-language attributes
	private static String ATTR_ELEMENT_NAME = "element-name"; //$NON-NLS-1$
	private static String ATTR_ATTRIBUTE_NAME = "attribute-name"; //$NON-NLS-1$

	// pi-language attribute
	private static String ATTR_PI_NAME = "pi-name"; //$NON-NLS-1$
	private static String ATTR_HANDLES_EOF = "handles-eof"; //$NON-NLS-1$

	// value attribute
	private static String ATTR_VALUE = "value"; //$NON-NLS-1$

	private HashMap _parsers;
	private HashMap _handlesEOF;
}
