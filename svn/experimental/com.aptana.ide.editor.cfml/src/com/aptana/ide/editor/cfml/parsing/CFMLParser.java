package com.aptana.ide.editor.cfml.parsing;


import java.util.Arrays;
import java.util.Stack;

import com.aptana.ide.editors.unified.parsing.UnifiedParser;
import com.aptana.ide.parsing.IParser;
import com.aptana.ide.parsing.ParserInitializationException;
import com.aptana.ide.parsing.nodes.IParseNode;

public class CFMLParser extends UnifiedParser implements IParser { 

	
	private static String NESTED_LANGUAGE_ID = "nested_languages"; //$NON-NLS-1$
	private static String ATTRIBUTE_LANGUAGE_GROUP = "attribute-language"; //$NON-NLS-1$
	private static String DOUBLE_QUOTED_ATTRIBUTE_DELIMITER_GROUP = "double-quoted-attribute-delimiter"; //$NON-NLS-1$
	private static String SINGLE_QUOTED_ATTRIBUTE_DELIMITER_GROUP = "single-quoted-attribute-delimiter"; //$NON-NLS-1$

	
	/**
	 * Language Registry
	 */
	protected CFMLLanguageRegistry languageRegistry;
	
	private Stack<IParseNode> _elementStack = new Stack<IParseNode>();
	private IParseNode _currentElement;
	
	private static final int[] elementEndSet = new int[] { CFMLTokenTypes.GREATER_THAN,
		CFMLTokenTypes.SLASH_GREATER_THAN };
	
	
	/**
	 * Static constructor
	 */
	static
	{
		// make sure all of our sets are sorted so that inSet will work properly
		// (that method uses a binary search to test existence of members in the
		// set)
		Arrays.sort(elementEndSet);
	}
	
	public CFMLParser(String language) throws ParserInitializationException {
		super(language);
		// TODO Auto-generated constructor stub
	}

	

}
